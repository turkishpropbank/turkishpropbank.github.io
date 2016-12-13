package se.lth.cs.srl.io;

import se.lth.cs.srl.corpus.Sentence;

public interface SentenceWriter {

	public void write(Sentence s);
	public void close();

}
