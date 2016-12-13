package se.lth.cs.srl.pipeline;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.SemanticRoleLabeler;
import se.lth.cs.srl.corpus.ArgMap;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.features.Feature;
import se.lth.cs.srl.features.FeatureGenerator;
import se.lth.cs.srl.features.FeatureSet;
import se.lth.cs.srl.io.AllCoNLL09Reader;
import se.lth.cs.srl.languages.Language;
import se.lth.cs.srl.ml.LearningProblem;
import se.lth.cs.srl.ml.Model;
import se.lth.cs.srl.ml.liblinear.Label;
import se.lth.cs.srl.ml.liblinear.LibLinearLearningProblem;
import se.lth.cs.srl.options.LearnOptions;
import se.lth.cs.srl.options.ParseOptions;
import se.lth.cs.srl.util.BrownCluster;

public class Reranker extends SemanticRoleLabeler {

	public static final String FILENAME="global";

	
	private final double alfa;
	private final boolean noPI;
	private final int aiBeam;
	private final int acBeam;
	
	private Model model;
	
	private List<String> argLabels;
	
	private List<Feature> aiFeatures;
	private List<Feature> acFeatures;
	private int sizeAIFeatures;
	private int sizeACFeatures;
	private int sizePipelineFeatures;
	
	private Map<String,Integer> calsMap;
	private int calsCounter=1;
	
	private Pipeline pipeline;
	private ArgumentIdentifier aiModule;
	private ArgumentClassifier acModule;
	
	private int[] rankCount;
	private int zeroArgMapCount=0;
	
	@SuppressWarnings("unchecked")
	public Reranker(ParseOptions parseOptions) throws ZipException, IOException, ClassNotFoundException{
		this(parseOptions.global_alfa,parseOptions.skipPI,parseOptions.global_aiBeam,parseOptions.global_acBeam);
		ZipFile zipFile=new ZipFile(parseOptions.modelFile);
		pipeline=noPI ? Pipeline.fromZipFile(zipFile, new Step[]{Step.pd,Step.ai,Step.ac}) : Pipeline.fromZipFile(zipFile);
		System.out.println("Loading reranker from "+zipFile.getName());
		if(noPI) System.out.println("Skipping predicate identification. Input is assumed to have predicates identified.");	
		argLabels=pipeline.getArgLabels();
		populateRerankerFeatureSets(pipeline.getFeatureSets(),pipeline.getFg());
		ObjectInputStream ois=new ObjectInputStream(zipFile.getInputStream(zipFile.getEntry(FILENAME)));
		model=(Model) ois.readObject();
		calsMap=(Map<String,Integer>) ois.readObject();
		ois.close();
		int i= noPI ? 1 : 2;
		aiModule=(ArgumentIdentifier) pipeline.steps.get(i);
		acModule=(ArgumentClassifier) pipeline.steps.get(i+1);
		zipFile.close();
	}
	
//	private static Reranker fromZipFile(ZipFile zipFile,boolean noPI,double alfa,int aiBeam,int acBeam) throws ZipException, IOException, ClassNotFoundException{
//		Pipeline pipeline= noPI ? Pipeline.fromZipFile(zipFile,new Step[]{Step.pd,Step.ai,Step.ac}) : Pipeline.fromZipFile(zipFile);
//		return new Reranker(pipeline,zipFile,noPI,alfa,aiBeam,acBeam);
//	}
	
	private Reranker(double alfa,boolean noPI,int aiBeam,int acBeam){
		this.alfa=alfa;
		this.noPI=noPI;
		this.aiBeam=aiBeam;
		this.acBeam=acBeam;
		rankCount=new int[aiBeam*acBeam];
	}
	
//	@SuppressWarnings("unchecked")
//	private Reranker(Pipeline pipeline,ZipFile zipFile, boolean noPI, double alfa,int aiBeam,int acBeam) throws IOException, ClassNotFoundException {
//		this(alfa,noPI,aiBeam,acBeam);
//		System.out.println("Loading reranker from "+zipFile.getName());
//		if(noPI) System.out.println("Skipping predicate identification. Input is assumed to have predicates identified.");	
//		argLabels=pipeline.getArgLabels();
//		populateRerankerFeatureSets(pipeline.getFeatureSets(),pipeline.getFg());
//		ObjectInputStream ois=new ObjectInputStream(zipFile.getInputStream(zipFile.getEntry(FILENAME)));
//		model=(Model) ois.readObject();
//		calsMap=(Map<String,Integer>) ois.readObject();
//		ois.close();
//		this.pipeline=pipeline;
//		int i= noPI ? 1 : 2;
//		aiModule=(ArgumentIdentifier) pipeline.steps.get(i);
//		acModule=(ArgumentClassifier) pipeline.steps.get(i+1);
//	}
	
	
	public Reranker(LearnOptions learnOptions,ZipOutputStream zos) throws IOException{
		this(1.0,false,learnOptions.global_aiBeam,learnOptions.global_acBeam); //1.0 and false make no difference during training.
		//Start by training a usual pipeline, then drag the featuregenerator and argLabels out of it and reuse them.
		List<Sentence> trainCorpus=new AllCoNLL09Reader(learnOptions.inputCorpus, learnOptions.udinput).readAll();
		//Map<Step,File> featureFiles=options.getFeatureFiles();
		BrownCluster bc=Learn.learnOptions.brownClusterFile==null?null:new BrownCluster(Learn.learnOptions.brownClusterFile);
		Pipeline fullPipeline=Pipeline.trainNewPipeline(trainCorpus, learnOptions.getFeatureFiles(), zos,bc);
		FeatureGenerator fg=fullPipeline.getFg();
		argLabels=fullPipeline.getArgLabels();
		Map<Step,FeatureSet> featureSets=new HashMap<Step,FeatureSet>(fullPipeline.getFeatureSets()); //We copy this and use for the reranker subclassifiers
		featureSets.remove(Step.pi);//This way we will not be training a bunch of superfluous training of the PI module.
		fullPipeline=null; //And we're done with the full pipeline. Let the garbage collector handle it
		
		populateRerankerFeatureSets(featureSets,fg);
		LearningProblem lp=new LibLinearLearningProblem(new File(learnOptions.tempDir,FILENAME),true);
		List<List<Sentence>> subCorpora=partitionCorpus(trainCorpus,learnOptions.global_numberOfCrossTrain);
		calsMap=new HashMap<String,Integer>();
		for(int i=0;i<subCorpora.size();++i){
			List<Sentence> testCorpus=subCorpora.get(i);
			trainCorpus.clear();
			for(int j=0;j<subCorpora.size();++j){
				if(j==i)
					continue;
				trainCorpus.addAll(subCorpora.get(j));
			}
			Pipeline pipeline=Pipeline.trainNewPipeline(trainCorpus,fg, null, featureSets);
			ArgumentIdentifier aiModule=(ArgumentIdentifier) pipeline.steps.get(1); //Note since we have no PI,
			ArgumentClassifier acModule=(ArgumentClassifier) pipeline.steps.get(2); //the AI and AC modules are shifted down one step.
						
//			int predCount=0;
			for(Sentence sen:testCorpus){
				for(Predicate pred:sen.getPredicates()){
					++predCount;
					//Do the beam search
					List<ArgMap> negatives=acModule.beamSearch(pred, aiModule.beamSearch(pred, learnOptions.global_aiBeam), learnOptions.global_acBeam);
					Set<ArgMap> positives=new HashSet<ArgMap>();
					double score=partitionBestArgMaps(negatives,pred.getArgMap(),positives);
					if(learnOptions.global_insertGoldMapForTrain && score!=1){
						positives.add(new ArgMap(pred.getArgMap()));
					}
					//Ok, we now have a number of positive examples in bestArgMaps, and the negative ones in argMaps
					//Now deduce the feature values and add them to the learningproblem
					for(ArgMap am:positives){
						Collection<Integer> indices=collectPipelineFeatureIndices(pred,am,new ArrayList<Integer>());
						addAndCollectGlobalFeatures(pred,am,indices);
						Collections.sort((List<Integer>) indices);
						lp.addInstance(AbstractStep.POSITIVE, indices);
					}
					for(ArgMap am:negatives){
						Collection<Integer> indices=collectPipelineFeatureIndices(pred,am,new ArrayList<Integer>());
						addAndCollectGlobalFeatures(pred,am,indices);
						Collections.sort((List<Integer>) indices);
						lp.addInstance(AbstractStep.NEGATIVE, indices);
					}
				}
			}
		}
		lp.done();

		model=lp.train();
		zos.putNextEntry(new ZipEntry(FILENAME));
		ObjectOutputStream oos=new ObjectOutputStream(zos);
		oos.writeObject(model);
		oos.writeObject(calsMap);
		oos.flush();
	}
	
	@Override
	protected void parse(Sentence sen){
		pipeline.steps.get(0).parse(sen);
		if(!noPI)
			pipeline.steps.get(1).parse(sen);
		for(Predicate pred:sen.getPredicates()){
			List<ArgMap> candidates=acModule.beamSearch(pred,aiModule.beamSearch(pred,aiBeam),acBeam);
			for(ArgMap argMap:candidates){
				ArrayList<Integer> indices=new ArrayList<Integer>();
				collectPipelineFeatureIndices(pred,argMap,indices);
				collectGlobalFeatures(pred,argMap,indices);
				List<Label> labels=model.classifyProb(indices);
				for(Label label:labels){
					if(label.getLabel().equals(AbstractStep.NEGATIVE))
						continue;
					argMap.setRerankProb(label.getProb());
					argMap.resetProb();
				}
			}
			int bestCandidateIndex=softMax(candidates); //Returns the index of the best argmap
			rankCount[bestCandidateIndex]++;
			ArgMap bestCandidate=candidates.get(bestCandidateIndex);
			if(bestCandidate.size()==0)
				zeroArgMapCount++;
			pred.setArgMap(bestCandidate);
		}
	}

	
	
	private int softMax(List<ArgMap> argmaps) {
		//To perform softmax on the reranking probabilities, uncomment the sumRR lines.
//		double sumProbs=0;
		//double sumRR=0;
		for(ArgMap am:argmaps){
			double prob=am.getIdProb();
			if(am.size()!=0) //Empty argmaps have P(Labeling)==1
				prob*=Math.pow(am.getLblProb(),1.0/am.size());
			am.setProb(prob);
//			sumProbs+=prob;
			//sumRR+=am.getRerankProb();
		}
		double bestScore=0;
		int bestIndex=-1;
		for(int i=0,size=argmaps.size();i<size;++i){
			ArgMap am=argmaps.get(i);
			//double localProb=am.getProb()/sumProbs;
			double localProb=am.getProb();    
			//am.setRerankProb(am.getRerankProb()/sumRR);
			double weightedRerankProb=Math.pow(am.getRerankProb(),alfa);
			double score=localProb*weightedRerankProb;
			if(score>bestScore){
				bestIndex=i;
				bestScore=score;
			} else if (score==bestScore) { //TODO remove me
				System.out.println("!same score..");
			}
			
		}
		return bestIndex;
	}


	private Collection<Integer> collectPipelineFeatureIndices(Predicate pred,ArgMap argMap,Collection<Integer> indices) {
		for(Word arg:argMap.keySet()){
			Integer aiOffset=0;
			HashSet<Integer> currentInstance=new HashSet<Integer>(); //I think we need to watch out for doubles here.
			for(Feature f:aiFeatures){
				f.addFeatures(currentInstance, pred, arg, aiOffset, false);
				aiOffset+=f.size(false);
			}
			Integer acOffset=sizeAIFeatures+sizeACFeatures*argLabels.indexOf(argMap.get(arg));
			for(Feature f:acFeatures){
				f.addFeatures(currentInstance, pred, arg, acOffset,false);
				acOffset+=f.size(false);
			}
			indices.addAll(currentInstance);
		}
		return indices;
	}

	/**
	 * This method extracts the one global feature (core argument label sequence).
	 * It also adds new features to the map. The method is meant to be run during training.
	 * 
	 * @param pred the predicate
	 * @param am the argmap
	 * @param indices the container to add the index too
	 */
	private void addAndCollectGlobalFeatures(Predicate pred,ArgMap argMap,Collection<Integer> indices) {
		String cals=Language.getLanguage().getCoreArgumentLabelSequence(pred, argMap);
		Integer index=calsMap.get(cals); //Need to build this beforehand -- can't be done, since we don't know what faulty CALS we will generate during training
		if(index==null){
			calsMap.put(cals,calsCounter);
			index=calsCounter++;
		}
		indices.add(sizePipelineFeatures+index);
	}
	
	/**
	 * This method extract the one global feature (core argument label sequence).
	 * It is meant to be used during parsing and does not add new features.
	 * 
	 * @param pred the predicate
	 * @param argMap the argmap
	 * @param indices the container to add the index too
	 */
	private void collectGlobalFeatures(Predicate pred, ArgMap argMap, Collection<Integer> indices) {
		String cals=Language.getLanguage().getCoreArgumentLabelSequence(pred, argMap);
		Integer index=calsMap.get(cals); //Need to build this beforehand -- can't be done, since we don't know what faulty CALS we will generate during training
		if(index!=null)
			indices.add(sizePipelineFeatures+index);
	}

	private void populateRerankerFeatureSets(Map<Step, FeatureSet> featureSets,FeatureGenerator fg) {
		aiFeatures=new ArrayList<Feature>();
		acFeatures=new ArrayList<Feature>();
		for(Entry<String,List<Feature>> entry:featureSets.get(Step.ai).entrySet())
			aiFeatures.addAll(entry.getValue());
		for(Entry<String,List<Feature>> entry:featureSets.get(Step.ac).entrySet())
			acFeatures.addAll(entry.getValue());
		sizeAIFeatures=0;
		sizeACFeatures=0;
		for(Feature f:aiFeatures)
			sizeAIFeatures+=f.size(false);
		for(Feature f:acFeatures)
			sizeACFeatures+=f.size(false);
		sizePipelineFeatures=sizeAIFeatures+argLabels.size()*sizeACFeatures;
	}

	private static double partitionBestArgMaps(List<ArgMap> candidates, Map<Word, String> goldStandard, Set<ArgMap> bestArgMaps) {
		double bestScore=0;
		for(ArgMap candidate:candidates){
			double curScore=candidate.computeScore(goldStandard);
			if(curScore>bestScore){
				bestScore=curScore;
				bestArgMaps.clear();
				bestArgMaps.add(candidate);
			} else if(curScore==bestScore){
				bestArgMaps.add(candidate);
			}
		}
		candidates.removeAll(bestArgMaps);
		return bestScore;
	}

	private static List<List<Sentence>> partitionCorpus(Iterable<Sentence> sentences,int numberOfPartitions){
		List<List<Sentence>> subCorpora=new ArrayList<List<Sentence>>();
		for(int i=0;i<numberOfPartitions;++i){
			subCorpora.add(new ArrayList<Sentence>());
		}
		if(Learn.learnOptions.deterministicReranker){
			int senCount=0;
			for(Sentence s:sentences){
				subCorpora.get(senCount%numberOfPartitions).add(s);
				senCount++;
			}
		} else {
			for(Sentence s:sentences){
				//			if(s.getPredicates().size()==0) //TODO uncomment this and verify that we get the same figures (roughly, seeing as no two rerankers come out identical anyway)
				//				continue;
				int index=(int) Math.floor(Math.random()*numberOfPartitions);
				subCorpora.get(index).add(s);
			}
		}
		return subCorpora;
	}

	@Override
	protected String getSubStatus() {
		StringBuilder ret=new StringBuilder("Reranker status:\n");
		ret.append("AI beam:\t\t"+aiBeam+"\n");
		ret.append("AC beam:\t\t"+acBeam+"\n");
		ret.append("Alfa:\t\t\t"+alfa+"\n");
		ret.append("\n");
		ret.append("Reranker choices:\n");
		ret.append("Rank\tFrequency\n");
		for(int i=0;i<rankCount.length;++i){
			ret.append((i+1)+"\t"+rankCount[i]+"\n");
		}
		ret.append("\n");
		ret.append("Number of zero size argmaps:\t"+zeroArgMapCount+"\n");
		return ret.toString();
	}
	
}
