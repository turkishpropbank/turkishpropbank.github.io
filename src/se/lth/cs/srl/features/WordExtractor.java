package se.lth.cs.srl.features;

import java.io.Serializable;

import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;

public abstract class WordExtractor implements Serializable {
	private static final long serialVersionUID = 1L;
	/*
	 * Possibly extractors could be cached, since they are using in multiple places. But I think the penalty is negligible anyway.
	 */
	
	abstract public Word getWord(Sentence s,int predIndex,int argIndex);
	abstract public Word getWord(Word pred,Word arg);
	public static WordExtractor getExtractor(TargetWord tw){
		switch(tw){
		case Pred: 			return new Pred();
		case PredParent:	return new PredParent();
		case Arg:			return new Arg();
		case LeftDep:		return new LeftDep();
		case RightDep:		return new RightDep();
		case LeftSibling:	return new LeftSibling();
		case RightSibling:	return new RightSibling();
		default: throw new Error("You are wrong here, check your code.");
		}
	}
	
	private static class Pred extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(predIndex);
		}
		public Word getWord(Word pred,Word arg){
			return pred;
		}
	}
	private static class PredParent extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(predIndex).getHead();
		}
		public Word getWord(Word pred,Word arg){
			return pred.getHead();
		}
	}
	private static class Arg extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(argIndex);
		}
		public Word getWord(Word pred,Word arg){
			return arg;
		}
	}
	private static class LeftSibling extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(argIndex).getLeftSibling();
		}
		public Word getWord(Word pred,Word arg){
			return arg.getLeftSibling();
		}
	}
	private static class RightSibling extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(argIndex).getRightSibling();
		}
		public Word getWord(Word pred,Word arg){
			return arg.getRightSibling();
		}
	}
	private static class LeftDep extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(argIndex).getLeftmostDep();
		}
		public Word getWord(Word pred,Word arg){
			return arg.getLeftmostDep();
		}
	}
	private static class RightDep extends WordExtractor {
		private static final long serialVersionUID = 1L;
		public Word getWord(Sentence s, int predIndex, int argIndex) {
			return s.get(argIndex).getRightmostDep();
		}
		public Word getWord(Word pred,Word arg){
			return arg.getRightmostDep();
		}
	}
}
