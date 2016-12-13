package se.lth.cs.srl.features;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.Parse;
import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.corpus.Word.WordData;
import se.lth.cs.srl.languages.Language;

public class ChildCaseMarkerSetFeature extends SetFeature {
	private static final long serialVersionUID = 1L;

	
	protected ChildCaseMarkerSetFeature(FeatureName name,WordData attr,boolean usedForPredicateIdentification,String POSPrefix) {
		super(name,false,usedForPredicateIdentification,POSPrefix);
	}
	
	@Override
	public String[] getFeatureStrings(Sentence s, int predIndex, int argIndex) {
		return makeFeatureStrings(s.get(predIndex)); 
	}

	@Override
	public String[] getFeatureStrings(Predicate pred, Word arg) {
		return makeFeatureStrings(pred);
	}
	
	private String[] makeFeatureStrings(Word pred){
		String[] ret=new String[pred.getChildren().size()];
		int i=0;
		for(Word child:pred.getChildren())
		{
			String feats[] = Language.getLanguage().getFeatSplitPattern().split(child.getFeats());
			if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
					(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
				ret[i++]=feats[feats.length-1];
			else
			{
				for(String f:feats)
				{
					if(f.contains("Case="))
					{
						ret[i++] = f.substring(f.lastIndexOf("Case=") + "Case=".length());
						break;
					}
				}
			}
		}
		return ret;		
	}

	@Override
	protected void performFeatureExtraction(Sentence s, boolean allWords) {
		if(allWords){
			for(int i=1,size=s.size();i<size;++i){
				if(doExtractFeatures(s.get(i)))
					for(Word child:s.get(i).getChildren())
					{
						String feats[] = Language.getLanguage().getFeatSplitPattern().split(child.getFeats());
						if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
								(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
							addMap(feats[feats.length-1]);
						else
						{
							for(String f:feats)
							{
								if(f.contains("Case="))
								{
									addMap(f.substring(f.lastIndexOf("Case=") + "Case=".length()));
									break;
								}
							}
						}					
					}
			}
		} else {
			for(Predicate pred:s.getPredicates()){
				if(doExtractFeatures(pred))
					for(Word child:pred.getChildren())
					{
						String feats[] = Language.getLanguage().getFeatSplitPattern().split(child.getFeats());
						if((Learn.learnOptions != null && !Learn.learnOptions.udinput)||
								(Parse.parseOptions!= null && !Parse.parseOptions.isUd))
							addMap(feats[feats.length-1]);
						else
						{
							for(String f:feats)
							{
								if(f.contains("Case="))
								{
									addMap(f.substring(f.lastIndexOf("Case=") + "Case=".length()));
									break;
								}
							}
						}
					}
			}
		}
	}
}
