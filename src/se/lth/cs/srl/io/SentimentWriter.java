package se.lth.cs.srl.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import se.lth.cs.srl.corpus.Predicate;
import se.lth.cs.srl.corpus.Sentence;
import se.lth.cs.srl.corpus.Word;
import se.lth.cs.srl.corpus.Yield;

public class SentimentWriter implements SentenceWriter {
	
	private BufferedWriter out;
	
	public SentimentWriter(File filename) {
		System.out.println("Writing corpus to "+filename+"...");
	    try {
	    	out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),Charset.forName("UTF-8")));
	        //out = new BufferedWriter(new FileWriter(filename));
	    } catch (IOException e) {
	    	System.out.println("Failed while opening writer...\n"+e.toString());
	    	System.exit(1);
	    }
	}
	
	public void write(Sentence s){
        try {
        	StringBuilder senText = new StringBuilder();
        	for(int i=1;i<s.size();++i){
    			Word w=s.get(i);
    			if(i<(s.size()-1))
    				senText.append(w.getForm()+" ");
    			else
    				senText.append(w.getForm());
        	}
			//out.write(senText+"\n");
			out.write(s.toString()+"\n");
			// format output
			for(Predicate pred:s.getPredicates()){	
				out.write(pred.getSense()+"\t");
				SortedSet<Yield> yields=new TreeSet<Yield>();
				Map<Word,String> argmap=pred.getArgMap();
				for(Word arg:argmap.keySet()){
					yields.addAll(arg.getYield(pred,argmap.get(arg),argmap.keySet()).explode());
				}
				int argSize = yields.size();
				int aNo = 0;
				for(Yield y:yields){
					if(!y.isContinuous()){ //Warn the user if we have discontinuous yields
						System.out.println("((Discontinous yield of argument '"+y+"' of predicate '"+pred.getSense()+"'. Yield contains tokens [");
					}
					StringBuilder span = new StringBuilder();
					int ySize = y.size();
					int yIn = 0;
					for(Word w:y)
					{
						if(yIn<(ySize-1))
							span.append(w.getForm()+" ");
						else
							span.append(w.getForm());
						yIn++;
					}			
					String argLabel=y.getArgLabel();
					
					if(aNo<(argSize-1))
						out.write("(#"+span+"#"+argLabel+"*)\t");
					else
						out.write("(#"+span+"#"+argLabel+"*)");
					aNo++;
				}
			out.write("\n");
			}
			out.write("\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write sentence.");
			System.exit(1);
		}
	}
	
	public void close(){
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to close writer.");
			System.exit(1);
		}
	}
	
}
