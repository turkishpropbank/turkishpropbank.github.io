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

public class PredDependentValsFeature extends AttrFeature {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Set<String> vals; 
	
	protected PredDependentValsFeature(FeatureName name, WordData attr, TargetWord tw, String POSPrefix) {
		super(name, attr, tw, false, false, POSPrefix);
		vals = new HashSet<String>(Arrays.asList("Recip", "Caus", "Pass"));
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
			if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
			{
				do
				{
					String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
					for(String f:feats)
					{
						if(vals.contains(f))
						{
							valFeat = valFeat+f;
						}
					}
					w = w.getHead();
				}
				while(w.getDeprel().equals("DERIV"));
			}
			else
			{
				String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
				for(String f:feats)
				{
					if(f.contains("Voice="))
					{
						valFeat = f.substring(f.lastIndexOf("Voice=") + 1);
						break;
					}
				}
				// add misc info if it is not there
				if(!w.getMisc().equals("_"))
				{
					valFeat += w.getMisc();
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
			if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
			{
				do
				{
					String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
					for(String f:feats)
					{
						if(vals.contains(f))
						{
							valFeat = valFeat+f;
						}
					}
					w = w.getHead();
				}
				while(w.getDeprel().equals("DERIV"));
			}
			else
			{
				String feats[] = Language.getLanguage().getFeatSplitPattern().split(w.getFeats());
				for(String f:feats)
				{
					if(f.contains("Voice="))
					{
						valFeat = f.substring(f.lastIndexOf("Voice=") + "Voice=".length());
						break;
					}
				}
				// add misc info if it is not there
				if(!w.getMisc().equals("_"))
				{
					valFeat += w.getMisc();
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
