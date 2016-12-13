package se.lth.cs.srl.preprocessor.tokenization;

import java.io.File;
import java.util.List;
import java.util.Properties;

import se.lth.cs.srl.util.FileExistenceVerifier;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

/**
 * This is a wrapper for the Stanford Chinese Segmenter, version 3.2.0
 * 
 * @author anders bjorkelund
 *
 */

public class StanfordChineseSegmenterWrapper implements Tokenizer {

	private final CRFClassifier<CoreLabel> classifier;
	/**
	 * Initialize the segmenter
	 * 
	 * @param dataDir this is the 'datadir' from the 2008-05-21 distribution.
	 */
	public StanfordChineseSegmenterWrapper(File dataDir){
		File serDictionaryFile=new File(dataDir,"dict-chris6.ser.gz");
		File ctbFile=new File(dataDir,"ctb.gz");
		String error=FileExistenceVerifier.verifyFiles(serDictionaryFile,ctbFile);
		if(error!=null)
			throw new Error(error);
		
		/*
		 * This is pretty much a copy&paste of the SegDemo.java, with minor edits on the files.
		 * No idea if this is the fastest or best way to do this.
		 */
	    Properties props = new Properties();
	    //props.setProperty("sighanCorporaDict", "data");
	    props.setProperty("sighanCorporaDict", dataDir.toString());
	    // props.setProperty("NormalizationTable", "data/norm.simp.utf8");
	    // props.setProperty("normTableEncoding", "UTF-8");
	    // below is needed because CTBSegDocumentIteratorFactory accesses it
	    //props.setProperty("serDictionary","data/dict-chris6.ser.gz");
	    props.setProperty("serDictionary",serDictionaryFile.toString());
	    //props.setProperty("testFile", args[0]);
	    props.setProperty("inputEncoding", "UTF-8");
	    props.setProperty("sighanPostProcessing", "true");

	    classifier = new CRFClassifier<CoreLabel>(props);
	    //classifier.loadClassifierNoExceptions("data/ctb.gz", props);
	    classifier.loadClassifierNoExceptions(ctbFile.toString(), props);
	    // flags must be re-set after data is loaded
	    classifier.flags.setProperties(props);
	    //classifier.writeAnswers(classifier.test(args[0]));
	    //classifier.testAndWriteAnswers(args[0]);
	}
	
	@Override
	public String[] tokenize(String sentence) {
		String[] tokens=(String[]) classifier.segmentString(sentence).toArray();
		String[] withRoot=new String[tokens.length+1];
		//withRoot[0]="<root>";
		withRoot[0]=is2.io.CONLLReader09.ROOT;
		System.arraycopy(tokens, 0, withRoot, 1, tokens.length);
		return withRoot;
	}

/*
 * Used this to figure out how to invoke the segmenter. I'll leave ith ere for future reference.
 * It's based on the SegDemo.java class provided with the segmenter dist.
 */

	public static void main(String[] args) throws Exception{
		args=new String[]{"chi-sen.deseg"};		
	    Properties props = new Properties();
	    //props.setProperty("sighanCorporaDict", "data");
//	    String dir="/home/users0/anders/storage/backuped/demos/SRLDemos/models/chi/stanford-chinese-segmenter-2008-05-21";
	    String dir="/home/users0/anders/storage/scratch/anders/stanford-segmenter-2013-06-20/";
	    props.setProperty("sighanCorporaDict", dir+"/data");
	    // props.setProperty("NormalizationTable", "data/norm.simp.utf8");
	    // props.setProperty("normTableEncoding", "UTF-8");
	    // below is needed because CTBSegDocumentIteratorFactory accesses it
	    //props.setProperty("serDictionary","data/dict-chris6.ser.gz");
	    props.setProperty("serDictionary",dir+"/data/dict-chris6.ser.gz");
	    //props.setProperty("testFile", args[0]);
	    props.setProperty("inputEncoding", "UTF-8");
	    props.setProperty("sighanPostProcessing", "true");

	    CRFClassifier<CoreLabel> classifier = new CRFClassifier<CoreLabel>(props);
	    //classifier.loadClassifierNoExceptions("data/ctb.gz", props);
	    classifier.loadClassifierNoExceptions(dir+"/data/ctb.gz", props);
	    // flags must be re-set after data is loaded
	    classifier.flags.setProperties(props);
	    //classifier.writeAnswers(classifier.test(args[0]));
	    //classifier.testAndWriteAnswers(args[0]);
	    
	    //ObjectBank<List<CoreLabel>> documents = classifier.makeObjectBank(args[0]);
	    List<String> forms=classifier.segmentString("上海浦东近年来颁布实行了涉及经济、贸易、建设、规划、科技、文教等领域的七十一件法规性文件，确保了浦东开发的有序进行。");
	    for(String form:forms)
	    	System.out.println(form);
	}
	
}
