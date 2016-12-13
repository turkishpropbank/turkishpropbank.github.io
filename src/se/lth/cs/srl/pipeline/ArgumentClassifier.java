package se.lth.cs.srl.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import se.lth.cs.srl.corpus.ArgMap;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.features.FeatureSet;
import se.lth.cs.srl.ml.Model;
import se.lth.cs.srl.ml.liblinear.Label;

public class ArgumentClassifier extends ArgumentStep{

	private static final String FILEPREFIX="ac_";
	
	private List<String> argLabels;
	
	public ArgumentClassifier(FeatureSet fs,List<String> argLabels) {
		super(fs);
		this.argLabels=argLabels;
	}

	@Override
	public void extractInstances(Sentence s) {
		for(Predicate pred:s.getPredicates()){
			for(Word arg:pred.getArgMap().keySet()){
				super.addInstance(pred,arg);
			}
		}
	}

	@Override
	public void parse(Sentence s) {
		for(Predicate pred:s.getPredicates()){
			Map<Word,String> argMap=pred.getArgMap();
			for(Word arg:argMap.keySet()){
				Integer label=super.classifyInstance(pred,arg);
				argMap.put(arg, argLabels.get(label));
			}
		}
	}

	@Override
	protected Integer getLabel(Predicate pred, Word arg) {
		return argLabels.indexOf(pred.getArgMap().get(arg));
	}

	@Override
	public void prepareLearning() {
		super.prepareLearning(FILEPREFIX);
	}

	@Override
	protected String getModelFileName() {
		return FILEPREFIX+".models";
	}

//	List<ArgMap> beamSearch(Predicate pred,List<ArgMap> candidates,int beamSize){
//		String POSPrefix=super.getPOSPrefix(pred.getPOS());
//		if(POSPrefix==null)
//			POSPrefix=super.featureSet.POSPrefixes[0]; //TODO fix me. or discard examples with wrong POS-tags
//		Model model=models.get(POSPrefix);
//		Map<Word,List<Label>> wordLabelMapping=new HashMap<Word,List<Label>>();
//		int minSize=999;
//		int maxSize=-1;
//		for(ArgMap argMap:candidates){ //Start by computing the probabilities for the labels for all arguments involved so we dont do this more than once for the same argument
//			for(Word arg:argMap.keySet()){
//				if(!wordLabelMapping.containsKey(arg)){ //Compute and add the probabilities for this
//					Collection<Integer> indices=super.collectIndices(pred, arg, POSPrefix, new TreeSet<Integer>());
//					List<Label> probs=model.classifyProb(indices);
//					wordLabelMapping.put(arg,probs);
//				}
//			}
//		}
//		ArrayList<ArgMap> ret=new ArrayList<ArgMap>();
//		for(ArgMap argMap:candidates){ //Then iterate over each candidate and generate the beamSize best labelings of this candidate.
//			
//		}
//		return ret;
//	}
	
	List<ArgMap> beamSearch(Predicate pred,List<ArgMap> candidates,int beamSize){
		ArrayList<ArgMap> ret=new ArrayList<ArgMap>();
		String POSPrefix=super.getPOSPrefix(pred.getPOS());
		if(POSPrefix==null)
			POSPrefix=super.featureSet.POSPrefixes[0]; //TODO fix me. or discard examples with wrong POS-tags
		Model model=models.get(POSPrefix);
		for(ArgMap argMap:candidates){ //Candidates from AI module
			ArrayList<ArgMap> branches=new ArrayList<ArgMap>();
			branches.add(argMap);
			SortedSet<ArgMap> newBranches=new TreeSet<ArgMap>(ArgMap.REVERSE_PROB_COMPARATOR);
			for(Word arg:argMap.keySet()){ //TODO we can optimize this severely by not computing the labels for the same arg more than once.
				Collection<Integer> indices=super.collectIndices(pred, arg, POSPrefix, new TreeSet<Integer>());
				List<Label> probs=model.classifyProb(indices);
				for(ArgMap branch:branches){ //Study this branch
					for(int i=0;i<beamSize;++i){ //and create k new branches with current arg
						Label label=probs.get(i);
						ArgMap newBranch=new ArgMap(branch);
						newBranch.put(arg, argLabels.get(label.getLabel()),label.getProb());
						newBranches.add(newBranch);
					}					
				}
				branches.clear();
				Iterator<ArgMap> it=newBranches.iterator();
				for(int i=0;i<beamSize && it.hasNext();++i){
					ArgMap cur=it.next();
					branches.add(cur);
				}
				newBranches.clear();
			}
			//When this loop finishes, we have the best 4 in branches
			for(int i=0,size=branches.size();i<beamSize && i<size;++i){
				ArgMap cur=branches.get(i);
				cur.setLblProb(cur.getProb());
				cur.resetProb();
				ret.add(cur);
			}
		}
		return ret;
	}
}
