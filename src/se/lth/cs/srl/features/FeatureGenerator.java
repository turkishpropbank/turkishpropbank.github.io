package se.lth.cs.srl.features;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word.WordData;
import se.lth.cs.srl.pipeline.Step;
import se.lth.cs.srl.util.BrownCluster;
import se.lth.cs.srl.util.BrownCluster.ClusterVal;

public class FeatureGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Map<FeatureName,Feature> cache;
	private Map<String,Feature> qcache;
	
	
	public FeatureGenerator(){
		cache=new HashMap<FeatureName,Feature>();
		qcache=new HashMap<String,Feature>();
	}
	
	public Map<Step,FeatureSet> readFeatureFiles(Map<Step,File> files,BrownCluster bc) throws IOException {
		Map<Step,FeatureSet> featureSets=new HashMap<Step,FeatureSet>();
		Map<String,List<String>> piNames=FeatureFile.readFile(files.get(Step.pi));
		featureSets.put(Step.pi,createFeatureSet(piNames,true,bc));
		for(Step s:new Step[]{Step.pd,Step.ai,Step.ac}){
			Map<String,List<String>> names=FeatureFile.readFile(files.get(s));
			featureSets.put(s, createFeatureSet(names,false,bc));
		}
		return featureSets;
	}
	
	private FeatureSet createFeatureSet(Map<String,List<String>> names,boolean includeAllWords,BrownCluster bc){
		Map<String,List<Feature>> fs=new HashMap<String,List<Feature>>();
		for(String POSPrefix:names.keySet()){
			List<Feature> list=new ArrayList<Feature>();
			fs.put(POSPrefix, list);
			for(String featureNameStr:names.get(POSPrefix)){
				if(featureNameStr.contains("+")){
					String[] n=featureNameStr.split("\\+");
					FeatureName fn1=FeatureName.valueOf(n[0]);
					FeatureName fn2=FeatureName.valueOf(n[1]);
					list.add(getQFeature(fn1,fn2,includeAllWords,POSPrefix,bc));
				} else {
					FeatureName fn=FeatureName.valueOf(featureNameStr);
					list.add(getFeature(fn,includeAllWords,POSPrefix,bc));
				}
			}
		}
		return new FeatureSet(fs);
	}
	
	public Feature getFeature(String featureNameString,boolean includeAllWords,String POSPrefix,BrownCluster bc){
		if(featureNameString.contains("+")){
			String[] s=featureNameString.split("\\+");
			FeatureName fn1=FeatureName.valueOf(s[0]);
			FeatureName fn2=FeatureName.valueOf(s[1]);
			return getQFeature(fn1,fn2,includeAllWords,POSPrefix,bc);
		} else {
			FeatureName fn=FeatureName.valueOf(featureNameString);
			return getFeature(fn,includeAllWords,POSPrefix,bc);
		}
	}
	
	public Feature getFeature(FeatureName fn,boolean includeAllWords,String POSPrefix,BrownCluster bc){
		Feature ret;
		if(cache.containsKey(fn)){
			ret=cache.get(fn);
			ret.addPOSPrefix(POSPrefix);
			return ret;
		} else {

			switch(fn){
			case PredWord: 			ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.Pred,includeAllWords,POSPrefix); break;
			case PredLemma:			ret=new PredDependentAttrFeature(fn,WordData.Lemma,TargetWord.Pred,includeAllWords,POSPrefix); break;
			case PredPOS:			ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.Pred,includeAllWords,POSPrefix); break;
			//Gozde: Good for predicate disambiguation
			case PredPPOS:			ret=new PredDependentAttrFeature(fn,WordData.PPOS,TargetWord.Pred,includeAllWords,POSPrefix); break;
			
			case PredDeprel:		ret=new PredDependentAttrFeature(fn,WordData.Deprel,TargetWord.Pred,includeAllWords,POSPrefix); break;
			case PredLemmaSense:	ret=new PredDependentAttrFeature(fn,WordData.Pred,TargetWord.Pred,false,POSPrefix); break;
			case PredFeats:			ret=new PredDependentFeatsFeature(fn,TargetWord.Pred,includeAllWords,POSPrefix); break;
			//Gozde: Predicate valency features + UD Support
			case PredValency:		ret=new PredDependentValsFeature(fn,WordData.Pred,TargetWord.Pred,POSPrefix); break;
			//Gozde: UD only (PredVerbForm feature)
			case PredVerbForm:		ret=new PredDependentVFormFeature(fn,WordData.Pred,TargetWord.Pred,POSPrefix); break;
			
			case PredParentWord:	ret=new PredDependentAttrFeature(fn,WordData.Form,TargetWord.PredParent,includeAllWords,POSPrefix); break;
			case PredParentPOS:		ret=new PredDependentAttrFeature(fn,WordData.POS,TargetWord.PredParent,includeAllWords,POSPrefix); break;
			case PredParentLemma:	ret=new PredDependentAttrFeature(fn,WordData.Lemma,TargetWord.PredParent,includeAllWords,POSPrefix); break;
			case PredParentFeats:	ret=new PredDependentFeatsFeature(fn,TargetWord.PredParent,includeAllWords,POSPrefix); break;
			
			case DepSubCat:			ret=new DepSubCatFeature(includeAllWords,POSPrefix); break;
			case ChildDepSet:		ret=new ChildSetFeature(fn,WordData.Deprel,includeAllWords,POSPrefix); break;
			case ChildWordSet:		ret=new ChildSetFeature(fn,WordData.Form,includeAllWords,POSPrefix); break;
			case ChildLemmaSet:		ret=new ChildSetFeature(fn,WordData.Lemma,includeAllWords,POSPrefix); break;
			case ChildPOSSet:		ret=new ChildSetFeature(fn,WordData.POS,includeAllWords,POSPrefix); break;
			//Gozde: Predicate valency features + UD Support
			case ChildCaseMarkerSet: ret=new ChildCaseMarkerSetFeature(fn,null,includeAllWords,POSPrefix); break;
			
			case ArgWord:			ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.Arg,POSPrefix); break;
			case ArgLemma:			ret=new ArgDependentAttrFeature(fn,WordData.Lemma,TargetWord.Arg,POSPrefix); break;
			case ArgPOS:			ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.Arg,POSPrefix); break;
			case ArgPPOS:			ret=new ArgDependentAttrFeature(fn,WordData.PPOS,TargetWord.Arg,POSPrefix); break;
			case ArgFeats:			ret=new ArgDependentFeatsFeature(fn,TargetWord.Arg,POSPrefix); break;
			// Gozde: Good for argument classification + UD Support
			case ArgCaseMark:		ret=new ArgDependentCaseMarkFeature(fn,WordData.Form,TargetWord.Arg,POSPrefix); break;
			// Gozde: UD Only
			case ArgVerbForm:		ret=new ArgDependentVerbFormFeature(fn,WordData.Form,TargetWord.Arg,POSPrefix); break;
			// Gozde: Good for argument classification + UD Support 
			case ArgMWE:			ret=new ArgDependentMWEFeature(fn,WordData.Deprel,TargetWord.Arg,POSPrefix); break;			
			case ArgDeprel:			ret=new ArgDependentAttrFeature(fn,WordData.Deprel,TargetWord.Arg,POSPrefix); break;
			// Gozde: Good for argument classification + Dont need UD Support
			case ArgFirstPosition:  ret=new ArgDependentIsFirstFeature(fn,WordData.Deprel,TargetWord.Arg,POSPrefix); break;
			
			case LeftWord:			ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftDep,POSPrefix); break;
			case LeftPOS:			ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftDep,POSPrefix); break;
			case LeftPPOS:			ret=new ArgDependentAttrFeature(fn,WordData.PPOS,TargetWord.LeftDep,POSPrefix); break;
			case LeftFeats:			ret=new ArgDependentFeatsFeature(fn,TargetWord.LeftDep,POSPrefix); break;

			case RightWord:			ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightDep,POSPrefix); break;
			case RightPOS:			ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightDep,POSPrefix); break;
			case RightPPOS:			ret=new ArgDependentAttrFeature(fn,WordData.PPOS,TargetWord.RightDep,POSPrefix); break;
			case RightFeats:		ret=new ArgDependentFeatsFeature(fn,TargetWord.RightDep,POSPrefix); break;

			case LeftSiblingWord:	ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.LeftSibling,POSPrefix); break;
			case LeftSiblingPOS:	ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.LeftSibling,POSPrefix); break;
			case LeftSiblingPPOS:	ret=new ArgDependentAttrFeature(fn,WordData.PPOS,TargetWord.LeftSibling,POSPrefix); break;
			case LeftSiblingFeats:	ret=new ArgDependentFeatsFeature(fn,TargetWord.LeftSibling,POSPrefix); break;

			case RightSiblingWord:	ret=new ArgDependentAttrFeature(fn,WordData.Form,TargetWord.RightSibling,POSPrefix); break;
			case RightSiblingPOS:	ret=new ArgDependentAttrFeature(fn,WordData.POS,TargetWord.RightSibling,POSPrefix); break;
			case RightSiblingPPOS:	ret=new ArgDependentAttrFeature(fn,WordData.PPOS,TargetWord.RightSibling,POSPrefix); break;
			case RightSiblingFeats: ret=new ArgDependentFeatsFeature(fn,TargetWord.RightSibling,POSPrefix); break;

			case POSPath:			ret=new PathFeature(fn,WordData.POS,POSPrefix); break;
			case PPOSPath:			ret=new PathFeature(fn,WordData.PPOS,POSPrefix); break;
			case DeprelPath:		ret=new PathFeature(fn,WordData.Deprel,POSPrefix); break;
			case Position:			ret=new PositionFeature(POSPrefix); break;
			default: 
				if(fn.toString().startsWith("Brown")){
					if(bc==null){
						throw new RuntimeException("Cannot use brown cluster features unless a cluster is provided on the cmd line");
					}
					ClusterVal cv=fn.toString().contains("Short")?ClusterVal.SHORT:ClusterVal.LONG;
					TargetWord tw=TargetWord.valueOf(fn.toString().substring("Brown".length()+cv.toString().length()));
					if(fn.toString().contains("Pred")){
						ret=new PredDependentBrown(fn, tw, includeAllWords, POSPrefix, bc, cv);
					} else {
						ret=new ArgDependentBrown(fn, tw, POSPrefix, bc, cv);
					}
				} else
					throw new Error("You are wrong here. Check your implementation.");
			}
			cache.put(fn, ret);
			return ret;
		}
	}

	public Feature getQFeature(FeatureName fn1,FeatureName fn2,boolean includeAllWords,String POSPrefix,BrownCluster bc){
		Feature ret;
		//String fnameStr=fn1.name()+"+"+fn2.name();
		String fnameStr=getCanonicalQFeatureName(fn1,fn2);
		if(qcache.containsKey(fnameStr)){
			ret=qcache.get(fnameStr);
			ret.addPOSPrefix(POSPrefix);
			return ret;
		}
		Feature f1=getFeature(fn1,includeAllWords,null,bc);
		Feature f2=getFeature(fn2,includeAllWords,null,bc);
		if(f1 instanceof SingleFeature){
			if(f2 instanceof SingleFeature){
				ret=new QSingleSingleFeature((SingleFeature) f1,(SingleFeature) f2,includeAllWords,POSPrefix);
			} else {
				ret=new QSingleSetFeature((SingleFeature) f1,(SetFeature) f2,includeAllWords,POSPrefix);
			}
		} else {
			if(f2 instanceof SingleFeature){
				ret=new QSingleSetFeature((SingleFeature) f2,(SetFeature) f1,includeAllWords,POSPrefix);
			} else { //otherwise both features are set features. These can only be combined if theyre both childset features. else its an error.
				if(f1 instanceof ChildSetFeature && f2 instanceof ChildSetFeature){
					ret=new QDoubleChildSetFeature((ChildSetFeature) f1,(ChildSetFeature) f2,includeAllWords,POSPrefix);
				} else {
					throw new IllegalArgumentException("Features "+f1.getName()+" and "+f2.getName()+" can not be combined. Change your feature file");
				}
			}
		}
		qcache.put(getCanonicalQFeatureName(fn1,fn2), ret);
		return ret;
	}
	
	private static String getCanonicalQFeatureName(String featureNameString){
		String[] s=featureNameString.split("\\+");
		FeatureName fn1=FeatureName.valueOf(s[0]);
		FeatureName fn2=FeatureName.valueOf(s[1]);
		return getCanonicalQFeatureName(fn1,fn2);
	}
	private static String getCanonicalQFeatureName(FeatureName f1,FeatureName f2){
		if(f1.compareTo(f2)>0){
			return f1.toString()+"+"+f2.toString();
		} else {
			return f2.toString()+"+"+f1.toString();
		}
	}
	
	public static String getCanonicalName(FeatureName fn1,FeatureName fn2){
		if(fn2==null)
			return fn1.toString();
		else
			return getCanonicalQFeatureName(fn1,fn2);
	}
	
	public Feature getCachedFeature(String featureNameString){
		Feature ret;
		if(featureNameString.contains("+")){
			ret=qcache.get(getCanonicalQFeatureName(featureNameString));
		} else {
			ret=cache.get(FeatureName.valueOf(featureNameString));
		}
		if(ret==null)
			throw new Error("Trying to read a cached feature that doesn't exist. Did you do something nasty with your model? Otherwise the implementation is wrong.");
		return ret;
	}
	

	public void buildFeatureMaps(Iterable<Sentence> sentences) {
		System.out.println("Extracting features (first pass)...");
		buildFeatureMaps(sentences,false);
		for(Feature f:cache.values())
			f.setDoneWithPredFeatureExtraction();
		for(Feature f:qcache.values())
			f.setDoneWithPredFeatureExtraction();
		System.out.println("Extracting features (second pass)...");
		buildFeatureMaps(sentences,true);
		for(Feature f:cache.values())
			System.out.println(f);
		for(Feature f:qcache.values())
			System.out.println(f);
	}
	private void buildFeatureMaps(Iterable<Sentence> sentences,boolean includeAllWords){
		//Start by cleaning out all simple features that are in the cache but without POS prefix. These have been created only for the QFeatures.
		Iterator<FeatureName> it=cache.keySet().iterator();
		while(it.hasNext()){
			if(cache.get(it.next()).POSPrefix==null)
				it.remove();
		}
		//Then extract features.
		for(Sentence s:sentences){
			for(Feature f:cache.values())
				f.extractFeatures(s,includeAllWords);
			for(Feature f:qcache.values())
				f.extractFeatures(s,includeAllWords);
		}		
	}

}
