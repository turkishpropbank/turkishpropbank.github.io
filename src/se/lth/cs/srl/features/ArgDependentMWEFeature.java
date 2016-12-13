package se.lth.cs.srl.features;

import java.util.Collection;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.Parse;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.corpus.Word.WordData;
import se.lth.cs.srl.languages.Language;

public class ArgDependentMWEFeature extends AttrFeature {
	private static final long serialVersionUID = 1L;

	protected ArgDependentMWEFeature(FeatureName name, WordData attr, TargetWord tw, String POSPrefix) {
		super(name, attr, tw, true, false, POSPrefix);
	}

	@Override
	protected void performFeatureExtraction(Sentence s, boolean allWords) {
		for(Predicate p:s.getPredicates()){
			if(doExtractFeatures(p))
				for(Word arg:p.getArgMap().keySet()){
					Word w=wordExtractor.getWord(null, arg);
					if(w==null)
						continue;
					if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
							(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
					{
						int ret= (w.getDeprel()=="MWE")?1:0;
						addMap(Integer.toString(ret));
					}
					else
					{
						int ret= (w.getDeprel()=="compound:lvc")?1:0;
						addMap(Integer.toString(ret));
					}
				}
		}

	}

	@Override
	public String getFeatureString(Sentence s, int predIndex, int argIndex) {
		Word w=wordExtractor.getWord(s, predIndex, argIndex);
		if(w==null)
			return null;
		else
		{
			if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
			{
				int ret= (w.getDeprel()=="MWE")?1:0;
				return Integer.toString(ret);
			}
			else
			{
				int ret= (w.getDeprel()=="compound:lvc")?1:0;
				return Integer.toString(ret);
			}		
		}
	}

	@Override
	public String getFeatureString(Predicate pred, Word arg) {
		Word w=wordExtractor.getWord(pred, arg);
		if(w==null)
			return null;
		else
		{
			if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
			{
				int ret= (w.getDeprel()=="MWE")?1:0;
				return Integer.toString(ret);
			}
			else
			{
				int ret= (w.getDeprel()=="compound:lvc")?1:0;
				return Integer.toString(ret);
			}
		}
	}
	
	@Override
	public void addFeatures(Sentence s, Collection<Integer> indices, int predIndex, int argIndex, Integer offset,boolean allWords) {
		addFeatures(indices,getFeatureString(s,predIndex,argIndex),offset,allWords);

	}
	@Override
	public void addFeatures(Collection<Integer> indices,Predicate pred,Word arg, Integer offset,boolean allWords){
		addFeatures(indices,getFeatureString(pred,arg),offset,allWords);
	}
	
	private void addFeatures(Collection<Integer> indices,String featureString,Integer offset,boolean allWords){
		if(featureString==null)
			return;
		Integer i=indexOf(featureString);
		if(i!=-1 && (allWords || i<predMaxIndex))
			indices.add(i+offset);		
	}

}