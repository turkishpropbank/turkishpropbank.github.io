package se.lth.cs.srl.features;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.Parse;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.corpus.Word.WordData;
import se.lth.cs.srl.languages.Language;

public class PredDependentVFormFeature extends AttrFeature {

	private static final long serialVersionUID = 1L; 
	
	protected PredDependentVFormFeature(FeatureName name, WordData attr, TargetWord tw, String POSPrefix) {
		super(name, attr, tw, false, false, POSPrefix);
	}

	protected void performFeatureExtraction(Sentence s, boolean allWords) {
		if(allWords){
			for(int i=1,size=s.size();i<size;++i){
				if(doExtractFeatures(s.get(i)))
					addMap(getFeatureString(s,i,-1));
			}
		} else {
			for(Predicate pred:s.getPredicates()){
				if(doExtractFeatures(pred))
					addMap(getFeatureString(pred,null));
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
			String valFeat = "";
			if((Learn.learnOptions != null && Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && Parse.parseOptions.isUd))
			{

				String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
				for(String f:feats)
				{
					if(f.contains("VerbForm="))
					{
						valFeat = f.substring(f.lastIndexOf("VerbForm=") + 1);
						break;
					}
				}
			}
			
			if(valFeat.isEmpty())
				return "NOVAL";
			else
				return valFeat;
		}
	}

	@Override
	public String getFeatureString(Predicate pred, Word arg) {
		Word w=wordExtractor.getWord(pred, arg);
		if(w==null)
			return null;
		else
		{
			String valFeat = "";
			if((Learn.learnOptions != null && Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && Parse.parseOptions.isUd))
			{

				String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
				for(String f:feats)
				{
					if(f.contains("VerbForm="))
					{
						valFeat = f.substring(f.lastIndexOf("VerbForm=") + 1);
						break;
					}
				}
			}
			
			if(valFeat.isEmpty())
				return "NOVAL";
			else
				return valFeat;
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
