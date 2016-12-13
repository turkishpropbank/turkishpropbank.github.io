package se.lth.cs.srl.preprocessor.tokenization;

import java.nio.charset.Charset;
import java.util.ArrayList;

import se.lth.cs.srl.preprocessor.tokenization.exner.SwedishTokenizer;

public class ExnerSwedishTokenizer implements Tokenizer {

	@Override
	public String[] tokenize(String sentence) {
		SwedishTokenizer swedishTokenizer = new SwedishTokenizer();
		ArrayList<String> tokens = swedishTokenizer.tokenize(sentence,Charset.forName("UTF-8"));
		String[] tok=new String[tokens.size()+1];
		tok[0]=is2.io.CONLLReader09.ROOT;
		int i=1;
		for(String s:tokens)
			tok[i++]=s;
		return tok;
	}
	
	
	public static void main(String[] args){
		String t1="En gul bil körde hundratusen mil.";
		String t2="Leonardos fullständiga namn var Leonardo di ser Piero da Vinci.";
		String t3="Genom skattereformen införs individuell beskattning (särbeskattning) av arbetsinkomster.";
		String t4="Det innebär bl.a. att endast en skatteskala kommer att finnas för beräkning av statlig inkomstskatt.";
		String t5="B-inkomster som för makarna sammanlagt uppgår till högst 2000kr skall dock betraktas som A-inkomst och alltså beskattas individuellt";
		String t6="Grundavdraget blir 2500kr (=4500 minskat med 1/5 av 10000kr).";
		String[] tests={t1,t2,t3,t4,t5,t6};
		ExnerSwedishTokenizer tokenizer=new ExnerSwedishTokenizer();
		for(String test:tests){
			String[] tokens=tokenizer.tokenize(test);
			for(String token:tokens)
				System.out.println(token);
			System.out.println();
		}
	}

}
