package se.lth.cs.srl.preprocessor.tokenization;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class OpenNLPToolsTokenizerWrapper implements se.lth.cs.srl.preprocessor.tokenization.Tokenizer {
	
	opennlp.tools.tokenize.Tokenizer tokenizer;

	public OpenNLPToolsTokenizerWrapper(opennlp.tools.tokenize.Tokenizer tokenizerImplementation){
		this.tokenizer=tokenizerImplementation;
	}
	
	@Override
	public String[] tokenize(String sentence) {
		String[] tokens=tokenizer.tokenize(sentence);
		String[] withRoot=new String[tokens.length+1];
		//withRoot[0]="<root>";
		withRoot[0]=is2.io.CONLLReader09.ROOT;
		System.arraycopy(tokens, 0, withRoot, 1, tokens.length);
		return withRoot;
	}
	
	public static OpenNLPToolsTokenizerWrapper loadOpenNLPTokenizer(File modelFile) throws IOException{
		BufferedInputStream modelIn = new BufferedInputStream(new FileInputStream(modelFile.toString()));
		opennlp.tools.tokenize.Tokenizer tokenizer = new TokenizerME(new TokenizerModel(modelIn));
		return new OpenNLPToolsTokenizerWrapper(tokenizer);
	}
}
