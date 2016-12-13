package se.lth.cs.srl.pipeline;

import java.util.Collection;
import java.util.TreeSet;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.features.Feature;
import se.lth.cs.srl.features.FeatureSet;
import se.lth.cs.srl.ml.LearningProblem;
import se.lth.cs.srl.ml.Model;

public class PredicateIdentifier extends AbstractStep {

	private static final String FILEPREFIX="pi_";
	
	public PredicateIdentifier(FeatureSet fs) {
		super(fs);
	}

	public void extractInstances(Sentence s){
		/*
		 * We add an instance if it
		 * 1) Is a predicate. Then either to its specific classifier, or the fallback one. (if fallback behavior is specified, i.e. skipNonMatchingPredicates=false
		 * 2) Is not a predicate, but matches the POS-tag
		 */
		for(int i=1,size=s.size();i<size;++i){
			Word potentialPredicate=s.get(i);
			String POS=potentialPredicate.getPOS();
			String POSPrefix=null;
			for(String prefix:featureSet.POSPrefixes){
				if(POS.startsWith(prefix)){
					POSPrefix=prefix;
					break;
				}
			}
			if(POSPrefix==null){ //It matches a prefix, we will use it for sure.
				if(!Learn.learnOptions.skipNonMatchingPredicates && potentialPredicate instanceof Predicate){
					POSPrefix=featureSet.POSPrefixes[0];
				} else {
					continue; //Its just some word we dont care about
				}
			}
			Integer label= potentialPredicate instanceof Predicate ? POSITIVE : NEGATIVE;
			addInstance(s,i,POSPrefix,label);
		}
	}
	
	private void addInstance(Sentence s, int i,String POSPrefix,Integer label) {
		LearningProblem lp=learningProblems.get(POSPrefix);
		Collection<Integer> indices=new TreeSet<Integer>();
		Integer offset=0;
		for(Feature f:featureSet.get(POSPrefix)){
			f.addFeatures(s,indices,i,-1,offset,true);
			offset+=f.size(true);
		} 
		lp.addInstance(label, indices);
	}

	public void parse(Sentence s){
		// trick for copula
		
		for(int i=1,size=s.size();i<size;++i){
			// be or starts with ' like 'm, 's, 're
			if(s.get(i).getLemma().equals("be") || s.get(i).getLemma().startsWith("\'"))
			{
				boolean flag = false;
				// And one of its children have VC tag
				for(Word w:s.get(i).getChildren())
				{
					if(w.getDeprel().equals("VC"))
					{
						flag=true;
					}
				}
				// Change it to become
				if(!flag)
				{
					// Change its lemma to become
					s.get(i).setLemma("become");
				}
			}
		}
		for(int i=1,size=s.size();i<size;++i){
			Integer label=classifyInstance(s,i);
			if(label.equals(POSITIVE))
				s.makePredicate(i);
		}
	}

	private Integer classifyInstance(Sentence s, int i) {
		String POSPrefix=null;
		String POS=s.get(i).getPOS();
		for(String prefix:featureSet.POSPrefixes){
			if(POS.startsWith(prefix)){
				POSPrefix=prefix;
				break;
			}
		}
		if(POSPrefix==null)
			return NEGATIVE;
		Model m=models.get(POSPrefix);
		Collection<Integer> indices=new TreeSet<Integer>();
		Integer offset=0;
		for(Feature f:featureSet.get(POSPrefix)){
			f.addFeatures(s,indices,i,-1,offset,true);
			offset+=f.size(true);
		}
		return m.classify(indices);
	}

	@Override
	public void prepareLearning() {
		super.prepareLearning(FILEPREFIX);
	}

	@Override
	protected String getModelFileName() {
		return FILEPREFIX+".models";
	}
	
}
