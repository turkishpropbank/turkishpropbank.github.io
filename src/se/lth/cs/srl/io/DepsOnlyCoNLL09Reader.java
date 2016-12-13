package se.lth.cs.srl.io;

import java.io.File;
import java.io.IOException;

import se.lth.cs.srl.corpus.Sentence;

public class DepsOnlyCoNLL09Reader extends AbstractCoNLL09Reader {

	public DepsOnlyCoNLL09Reader(File file, boolean isUD) {
		super(file,isUD);
	}

	@Override
	protected void readNextSentence() throws IOException {
		String str;
		Sentence sen=null;
		StringBuilder senBuffer=new StringBuilder();
		while ((str = in.readLine()) != null) {
			if(!str.trim().equals("")) {
				senBuffer.append(str).append("\n");
			} else {
				sen=Sentence.newDepsOnlySentence(this.isUD, NEWLINE_PATTERN.split(senBuffer.toString()));
				break;
			}
		}
		if(sen==null){
			nextSen=null;
			in.close();
		} else {
			nextSen=sen;
		}
	}

}
