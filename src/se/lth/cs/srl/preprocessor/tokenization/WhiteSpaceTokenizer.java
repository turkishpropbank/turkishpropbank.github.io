package se.lth.cs.srl.preprocessor.tokenization;

import java.util.StringTokenizer;

public class WhiteSpaceTokenizer implements Tokenizer {

	@Override
	public String[] tokenize(String sentence) {
		StringTokenizer tokenizer=new StringTokenizer(sentence);
		String[] tokens=new String[tokenizer.countTokens()+1];
		int r=0;
		tokens[r++]=is2.io.CONLLReader09.ROOT;
		while(tokenizer.hasMoreTokens())
			tokens[r++]=tokenizer.nextToken();
		return tokens;
	}
	
	
	public static void main(String[] args){
		String t1="En gul bil körde hundratusen mil.";
		String t2="Leonardos fullständiga namn var Leonardo di ser Piero da Vinci.";
		String t3="Genom skattereformen införs individuell beskattning (särbeskattning) av arbetsinkomster.";
		String t4="\"Oh, no,\" she's saying, \"our $400 blender can't handle something this hard!\"";
		String[] tests={t1,t2,t3,t4};
		for(String test:tests){
			WhiteSpaceTokenizer tokenizer=new WhiteSpaceTokenizer();
			String[] tokens=tokenizer.tokenize(test);
			for(String token:tokens)
				System.out.println(token);
			System.out.println();
		}
	}

}
