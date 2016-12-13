package se.lth.cs.srl.preprocessor.tokenization;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.international.french.process.FrenchTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.TokenizerFactory;

public class StanfordFrenchTokenizer implements Tokenizer {

	private final TokenizerFactory<CoreLabel> tokenizerFactory=FrenchTokenizer.ftbFactory();
	
	@Override
	public String[] tokenize(String sentence) {
		Reader r=new StringReader(sentence);
//		PTBTokenizer<Word> tokenizer=PTBTokenizer.newPTBTokenizer(r);
		edu.stanford.nlp.process.Tokenizer<CoreLabel> tokenizer=tokenizerFactory.getTokenizer(r);
		
		List<String> l=new ArrayList<String>();
		while(tokenizer.hasNext())
			l.add(tokenizer.next().word());
		
		String[] tok=new String[l.size()+1];
		tok[0]=is2.io.CONLLReader09.ROOT;
		int i=1;
		for(String s:l)
			tok[i++]=s;
		return tok;
	}
	
	public static void main(String[] args){
//		String test="Les ménages, en particulier les familles aisées, vont supporter l'essentiel des hausses des prélèvements obligatoires.";
		String test2="Tour d'horizon des mesures décidées et de celles à l'étude.";
		String[] tokens=new StanfordFrenchTokenizer().tokenize(test2);
		for(String token:tokens)
			System.out.println(token);
	}

}
