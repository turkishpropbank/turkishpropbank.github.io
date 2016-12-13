package se.lth.cs.srl.pipeline;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import se.lth.cs.srl.SemanticRoleLabeler;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.PredicateReference;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.features.Feature;
import se.lth.cs.srl.features.FeatureFile;
import se.lth.cs.srl.features.FeatureGenerator;
import se.lth.cs.srl.features.FeatureSet;
import se.lth.cs.srl.options.LearnOptions;
import se.lth.cs.srl.util.BrownCluster;

public class Pipeline extends SemanticRoleLabeler {

	private PredicateReference predicateReference;
	private List<String> argLabels;
	private FeatureGenerator fg;
	private Map<Step, FeatureSet> featureSets;
	
	List<PipelineStep> steps;
	private Pipeline(FeatureGenerator fg, Map<Step, FeatureSet> featureSets, Iterable<Sentence> sentences){
		
		if(featureSets.containsKey(Step.pd)){
			predicateReference=new PredicateReference(featureSets.get(Step.pd).POSPrefixes);
		}
		if(featureSets.containsKey(Step.ac)){
			argLabels=new ArrayList<String>();
		}
		extractLabels(sentences,predicateReference,argLabels);	
//		extractLabels(sentences, featureSets.get(Step.pd).POSPrefixes); //TODO, sort of nasty.
		this.fg=fg;
		this.featureSets=featureSets;
		setup(featureSets,predicateReference,argLabels);
	}
	private Pipeline(FeatureGenerator fg,PredicateReference predicateReference,List<String> argLabels, Map<Step, FeatureSet> featureSets){
		this.predicateReference=predicateReference;
		this.argLabels=argLabels;
		
		
		this.fg=fg;
		this.featureSets=featureSets;
		setup(featureSets,predicateReference,argLabels);
	}
	
	private void setup(Map<Step,FeatureSet> featureSets,PredicateReference predicateReference,List<String> argLabels){
		steps=new ArrayList<PipelineStep>();
		for(Step step:Step.values()){
			if(featureSets.containsKey(step)){
				switch(step){
				case pi: steps.add(new PredicateIdentifier(featureSets.get(Step.pi))); break;
				case pd: steps.add(new PredicateDisambiguator(featureSets.get(Step.pd),predicateReference)); break;
				case ai: steps.add(new ArgumentIdentifier(featureSets.get(Step.ai))); break;
				case ac: steps.add(new ArgumentClassifier(featureSets.get(Step.ac),argLabels)); break;
				}
			}
		}
	}
	
	@Override
	protected void parse(Sentence s){
		for(PipelineStep step:steps)
			step.parse(s);
	}
	
	
	
//	void extractLabels(Iterable<Sentence> reader,String[] POSPrefixes) {
//		System.out.println("Extracting argument labels and predicate senses.");
//		predicateReference=new PredicateReference(POSPrefixes);
//		Set<String> argLabelSet=new HashSet<String>();
//		for(Sentence s:reader){
//			for(Predicate p:s.getPredicates()){
//				predicateReference.extractSense(p);
//				argLabelSet.addAll(p.getArgMap().values());
//			}
//		}
//		argLabels=new ArrayList<String>(argLabelSet);
//		predicateReference.trim(); //have to trim to reduce number of models to train.
//	}

	public static void extractLabels(Iterable<Sentence> reader,PredicateReference predicateReference,List<String> argLabels) {
		if(predicateReference==null && argLabels==null)
			return;
		System.out.println("Extracting argument labels and predicate senses.");
		Set<String> argLabelSet=new HashSet<String>();
		for(Sentence s:reader){
			for(Predicate p:s.getPredicates()){
				if(predicateReference!=null)
					predicateReference.extractSense(p);
				if(argLabels!=null)
					argLabelSet.addAll(p.getArgMap().values());
			}
		}
		if(argLabels!=null)
			argLabels.addAll(argLabelSet);
		if(predicateReference!=null)
			predicateReference.trim();
	}

	
	private void train(Iterable<Sentence> sentences, ZipOutputStream zos) throws IOException {
		for(PipelineStep step:steps)
			step.prepareLearning();
		System.out.println("Extracting training instances from corpus.");
		int senCount=0;
		for(Sentence s:sentences){
			senCount++;
			if(senCount%1000==0)
				System.out.println("Processing sentence "+senCount);
			for(PipelineStep step:steps)
				step.extractInstances(s);
		}
		for(PipelineStep step:steps)
			step.done();
		System.out.println("Starting training.");
		for(PipelineStep step:steps)
			step.train();
		if(zos!=null){
			System.out.println("Saving classifiers to model.");
			for(PipelineStep step:steps)
				step.writeModels(zos);
		}
	}

	public static Pipeline trainNewPipeline(Iterable<Sentence> sentences,FeatureGenerator fg,ZipOutputStream zos,Map<Step,FeatureSet> featureSets) throws IOException {
		//Then create the arglabel list and predicatereference
		Pipeline pipeline=new Pipeline(fg,featureSets,sentences);
		//pipeline.extractLabels(sentences, featureSets.get(Step.pd).POSPrefixes);

		//Now write stuff to the zip file if it is not null
		if(zos!=null){
			System.out.println("Writing various objects to model file.");
			//Then write the feature files to the model
			for(Step s:Step.values()){
				zos.putNextEntry(new ZipEntry(LearnOptions.featureFileNames.get(s)));
				FeatureFile.writeToOutput(featureSets.get(s),new PrintWriter(zos));
				zos.closeEntry();
			}
			//Then write the predicatereference, the arglabel list and the featuregenerator.
			zos.putNextEntry(new ZipEntry("objects"));
			ObjectOutputStream oos=new ObjectOutputStream(zos);
			oos.writeObject(fg);
			oos.writeObject(pipeline.predicateReference);
			oos.writeObject(pipeline.argLabels);
			oos.flush();
			zos.closeEntry();
		}
		//Now train the models
		pipeline.train(sentences, zos);

		//Then insert the code of writeData(zos);
		return pipeline;		
	}
	
	public static Pipeline trainNewPipeline(Iterable<Sentence> sentences,Map<Step,File> featureFiles,ZipOutputStream zos,BrownCluster bc) throws IOException {
		//Start by creating the feature mappings for all features based on the feature files.
		FeatureGenerator fg=new FeatureGenerator();
		Map<Step,FeatureSet> featureSets=fg.readFeatureFiles(featureFiles,bc);
		fg.buildFeatureMaps(sentences);
		Pipeline pipeline=trainNewPipeline(sentences,fg,zos,featureSets);
		pipeline.fg=fg;
		return pipeline;
	}

	@SuppressWarnings("unchecked")
	public static Pipeline fromZipFile(ZipFile zipFile,Step[] steps) throws IOException, ClassNotFoundException{
		long startTime=System.currentTimeMillis();
		System.out.println("Loading pipeline from "+zipFile.getName());
		//Get objects
		ObjectInputStream ois=new ObjectInputStream(zipFile.getInputStream(zipFile.getEntry("objects")));
		FeatureGenerator fg=(FeatureGenerator) ois.readObject();
		PredicateReference predicateReference=(PredicateReference) ois.readObject();
		List<String> argLabels=(List<String>) ois.readObject();
		ois.close();

		//Then read the featurefiles from the model
		Map<Step,FeatureSet> featureSets=new HashMap<Step,FeatureSet>();
		
		for(Step s:steps){
			BufferedReader in=new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipFile.getEntry(LearnOptions.featureFileNames.get(s)))));
			Map<String,List<String>> names=FeatureFile.readFile(in);
			Map<String,List<Feature>> features=new HashMap<String,List<Feature>>();
			for(String POSPrefix:names.keySet()){
				List<Feature> list=new ArrayList<Feature>();
				for(String name:names.get(POSPrefix))
					list.add(fg.getCachedFeature(name));
				features.put(POSPrefix,list);
			}
			featureSets.put(s,new FeatureSet(features));
		}
		Pipeline pipeline=new Pipeline(fg,predicateReference,argLabels,featureSets);
		//Load models
		for(PipelineStep step:pipeline.steps)
			step.readModels(zipFile);
		pipeline.loadingTime=System.currentTimeMillis()-startTime;
		return pipeline;
	}
	
	public static Pipeline fromZipFile(ZipFile zipFile) throws ZipException, IOException, ClassNotFoundException {
		return fromZipFile(zipFile,new Step[]{Step.pi,Step.pd,Step.ai,Step.ac});
	}


	List<String> getArgLabels() {
		return argLabels;
	}


	FeatureGenerator getFg() {
		return fg;
	}
	Map<Step, FeatureSet> getFeatureSets() {
		return featureSets;
	}
	@Override
	protected String getSubStatus() {
		return "";
	}

}
