package se.lth.cs.srl.languages;

import java.util.Map;
import java.util.regex.Pattern;

import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.options.FullPipelineOptions;

public class Turkish extends Language {

	private static Pattern CALSPattern=Pattern.compile("^A0|A1|A2|A3|A4$");
	@Override
	public String toLangNameString() {
		// TODO Auto-generated method stub
		return "Turkish";
	}

	@Override
	public String getDefaultSense(Predicate pred) {
		// TODO Auto-generated method stub
		return pred.getLemma()+".01";
	}

	@Override
	public String getCoreArgumentLabelSequence(Predicate pred,
			Map<Word, String> proposition) {
		// TODO Auto-generated method stub
		Sentence sen=pred.getMySentence();
		StringBuilder ret=new StringBuilder();
		for(int i=1,size=sen.size();i<size;++i){
			Word w=sen.get(i);
			if(pred==w){
				ret.append(" "+pred.getSense());
			}
			if(proposition.containsKey(w)){
				String label=proposition.get(w);
				if(CALSPattern.matcher(label).matches())
					ret.append(" "+label);
			}
		}
		return ret.toString();
	}

	@Override
	public L getL() {
		// TODO Auto-generated method stub
		return L.tur;
	}

	@Override
	public String getLexiconURL(Predicate pred) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String verifyLanguageSpecificModelFiles(FullPipelineOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

}
