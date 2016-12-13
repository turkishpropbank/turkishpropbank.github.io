package se.lth.cs.srl.corpus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.languages.Language;


public class PredicateReference implements Serializable {
	private static final long serialVersionUID = 1L;

	private Map<String,PredicateMap> map;
	private String[] POSPrefixes;
	
	public PredicateReference(String[] POSPrefixes){
		map=new HashMap<String,PredicateMap>();
		this.POSPrefixes=POSPrefixes;
		for(String prefix:POSPrefixes){
			map.put(prefix, new PredicateMap(prefix));
		}
	}

	public void extractSense(Predicate p) {
		String predPOS=p.getPOS();
		for(String prefix:POSPrefixes){
			if(predPOS.startsWith(prefix)){
				map.get(prefix).add(p.getLemma(),p.getSense());
				return;
			}
		}
		if(Learn.learnOptions.skipNonMatchingPredicates){ 
			//System.out.println("Ignoring predicate\n"+p+"\n because its POS tag is not among the prefixes we are learning.");
		} else {
			//System.out.println("Treating predicate\n"+p+"\n as a "+POSPrefixes[0]+" during predicate sense extraction because its POS-tag doesn't match any of the ones we're learning.");
			map.get(POSPrefixes[0]).add(p.getLemma(), p.getSense());
		}
	}
	
	public void trim() {
		for(PredicateMap pm:map.values())
			pm.trim();
	}
	
	private static class PredicateMap implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Map<String,Value> map;
		private int lemmaCounter=0;
		private String POS;
		
		public PredicateMap(String POS){
			map=new HashMap<String,Value>();
			this.POS=POS;
		}

		private void add(String lemma,String sense){
			if(map.containsKey(lemma)){
				Value v=map.get(lemma);
				if(!v.senses.contains(sense))
					v.senses.add(sense);
			} else {
				Value v=new Value();
				v.filename=POS+(++lemmaCounter);
				v.senses=new ArrayList<String>();
				v.senses.add(sense);
				map.put(lemma, v);
			}
		}

		public void trim(){
			for(Value v:map.values()){
				if(v.senses.size()==1){
					v.filename=null;
				}
				
				v.senses.trimToSize();
			}
		}
	}
	private static class Value implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String filename;
		ArrayList<String> senses;
	}
	public String getFileName(String lemma,String POSPrefix){
		//return map.get(POSPrefix).map.get(lemma).filename;
		Value v=map.get(POSPrefix).map.get(lemma);
		return v==null ? null : v.filename;
	}

	public int getLabel(String lemma,String POSPrefix,String sense){
		return map.get(POSPrefix).map.get(lemma).senses.indexOf(sense);
	}

	public String getSense(String lemma, String POSPrefix, Integer label) {
		return map.get(POSPrefix).map.get(lemma).senses.get(label);
	}

	public String getSimpleSense(Predicate pred, String prefix) {
		String lemma=pred.getLemma();
		PredicateMap predicateMap=map.get(prefix);
		if(predicateMap==null)
			return Language.getLanguage().getDefaultSense(pred);
		Value v=predicateMap.map.get(lemma);
		if(v==null)
			return Language.getLanguage().getDefaultSense(pred);
		else
			return v.senses.get(0);
	}
}