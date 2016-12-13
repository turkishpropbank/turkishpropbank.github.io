package se.lth.cs.srl.corpus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import se.lth.cs.srl.Learn;

public class Predicate extends Word {
	private static final long serialVersionUID = 1L;
	
	private Map<Word,String> argmap;
	private String sense; //This is PredLemmaSense in CoNLL2008

	/**
	 * Used to replace an old word with a predicate (updates dependencies). 
	 * Used to make a predicate from a word during predicate identification.
	 * @param w The Word
	 */
	public Predicate(Word w){
		super(w);
		if(Learn.learnOptions!=null && Learn.learnOptions.deterministicPipeline){
			argmap=new TreeMap<Word,String>(mySentence.wordComparator);
		} else {
			argmap=new HashMap<Word,String>();
		}
	}
	/**
	 * Only use this constructor if you manually add the other attributes later on (i.e. in constructor Word(String CoNLL2009String))
	 * @param sense the sense label of the predicate
	 */
	/*
	public Predicate(String[] CoNLL2009Columns,Sentence s,int idx){
		super(CoNLL2009Columns,s,idx);
		if(CoNLL2009Columns.length>13)
			this.sense=CoNLL2009Columns[13];
		if(Learn.learnOptions!=null && Learn.learnOptions.deterministicPipeline){
			argmap=new TreeMap<Word,String>(mySentence.wordComparator);
		} else {
			argmap=new HashMap<Word,String>();
		}
	}
	*/
	/* Update: Added to handle universal dependencies */
	public Predicate(Boolean isUD, String[] CoNLL2009Columns,Sentence s,int idx){
		super(isUD,CoNLL2009Columns,s,idx);
		int offset = 13;
		if(isUD){offset = 11; }
		if(CoNLL2009Columns.length>offset)
			this.sense=CoNLL2009Columns[offset];
		if(Learn.learnOptions!=null && Learn.learnOptions.deterministicPipeline){
			argmap=new TreeMap<Word,String>(mySentence.wordComparator);
		} else {
			argmap=new HashMap<Word,String>();
		}
	}
	
	public Map<Word, String> getArgMap() {
		return argmap;
	}
	public void setArgMap(Map<Word, String> argmap) {
		this.argmap = argmap;
	}
	public void addArgMap(Word w,String label){
		argmap.put(w,label);
	}
	public String getSense() {
		return sense;
	}
	public void setSense(String sense) {
		this.sense = sense;
	}
	public String getAttr(WordData attr){
		if(attr==WordData.Pred)
			return sense;
		else
			return super.getAttr(attr);
	}
	public String getArgumentTag(Word w) {
		return argmap.get(w);
	}
	public String toString(){
		return super.toString()+"\tY\t"+sense;
	}
}
