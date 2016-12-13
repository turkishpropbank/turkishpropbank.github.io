package se.lth.cs.srl.ml.liblinear;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;

import se.lth.cs.srl.Learn;
import se.lth.cs.srl.ml.LearningProblem;
import se.lth.cs.srl.ml.Model;

public class LibLinearLearningProblem implements LearningProblem {
	
//	private static final boolean nativeLibraryLoaded;
//	private native void trainProblem();
//	static {
//		if(true)
//			nativeLibraryLoaded=false;
//	}
	
	private File trainDataFile;
	private PrintWriter out;
	
	private ProblemWriter problemWriter;
	
	public LibLinearLearningProblem(File trainDataFile,boolean histogram) {
		this.trainDataFile=trainDataFile;
		try {
			this.out=new PrintWriter(new BufferedWriter(new FileWriter(trainDataFile)));
			problemWriter=histogram ? new HistogramProblemWriter(out) : new BinaryProblemWriter(out);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void addInstance(int label, Collection<Integer> indices) {
		out.print(label);
		out.print(' ');
		problemWriter.writeIndices(indices);
		out.println();
	}

	@Override
	public void done() {
		out.close();
	}

	public LibLinearModel train(boolean sparseModel){
		File outputFile=new File(trainDataFile.toString()+".model");
		if(Learn.learnOptions.deleteTrainFiles){
			trainDataFile.deleteOnExit();
			outputFile.deleteOnExit();
		}
		try {
			LibLinearModel.trainModel(trainDataFile, outputFile);
			LibLinearModel ret=new LibLinearModel(outputFile,sparseModel);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null; //Cannot be reached anyway.		
	}
	
	@Override
	public Model train() {
		return train(false);
	}
	
	private static abstract class ProblemWriter {
		protected PrintWriter out;
		protected ProblemWriter(PrintWriter out){
			this.out=out;
		}
		abstract void writeIndices(Collection<Integer> indices);
	}
	private static class HistogramProblemWriter extends ProblemWriter{

		protected HistogramProblemWriter(PrintWriter out) {
			super(out);
		}

		@Override
		void writeIndices(Collection<Integer> indices) {
			Iterator<Integer> it=indices.iterator();
			if(it.hasNext()){
				Integer last=null;
				Integer currentIndex=it.next();
				Integer next=null;
				int count=0;
				do {
					count=1;
					next=null;
					while(it.hasNext() && (next=it.next()).equals(currentIndex)){
						count++;
					}
					out.print(currentIndex);
					out.print(':');
					out.print(count);
					out.print(' ');
					last=currentIndex;
					currentIndex=next;
				} while(it.hasNext());
				if(!last.equals(currentIndex) && currentIndex!=null){
					out.print(currentIndex);
					out.print(':');
					out.print(count);
				}
			}
		}
		
	}
	private static class BinaryProblemWriter extends ProblemWriter{

		protected BinaryProblemWriter(PrintWriter out) {
			super(out);
		}

		@Override
		public void writeIndices(Collection<Integer> indices) {
			for(Integer index:indices){
				out.print(index);
				out.print(":1 ");
			}
		}
		
	}
	
}
