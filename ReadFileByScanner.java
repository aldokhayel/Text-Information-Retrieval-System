import java.util.*;
import java.io.*;

public class ReadFileByScanner {
	
	private Scanner File;
	private LinkedList<String> tmp;

		public ReadFileByScanner() {
			this.File =null;
			
		}

		public void openFile(String fileName){
			try{
				File = new Scanner(new File(fileName));			
			//	System.out.println("The File Is Open !!");
			}catch(Exception e){
				System.out.println("Problem in openFile method");
			}	
		}
		
		public LinkedList<String> readFileByLines(){
			LinkedList<String> stringByLine;
			try {
				stringByLine = new LinkedList<String>();

				while(File.hasNextLine()){
					stringByLine.insert(File.nextLine());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				stringByLine = null;
				e.printStackTrace();
			}
	//		System.out.println("The File read by line !!");
			
			return stringByLine;
		}
		
		public LinkedList<LinkedList<String>> readFileByWords(){
			try {
				Scanner tmp;
				LinkedList<LinkedList<String>> linkOfStrings = new LinkedList<LinkedList<String>>();
				LinkedList<String> stringByWords;
				while (File.hasNextLine()){
					
					
					String[] x;
					x= File.nextLine().split("\\t", 2);//new update
					tmp = new Scanner(x[1]);
					stringByWords = new LinkedList<String>();
					
					while(tmp.hasNext()){
						stringByWords.insert(tmp.next());
					}
					tmp.close();
					linkOfStrings.insert(stringByWords);
				}
				//System.out.println("The File read by Words!!");
				
				return linkOfStrings;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		public BST<String> lodtest(String stopFilename){
			try {
				BST<String> loder = new BST<String>();
				loadStop(stopFilename);
				tmp.findFirst();
				
				while(!tmp.last()){
					
					loder.insert(tmp.retrieve(), "");
					tmp.findNext();
				}
				loder.insert(tmp.retrieve(), "");
					
				return loder;
	
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
		
		
		public void loadStop (String stopFilename){
			// Loads the stemming list into appropriate data structure.
			try {
				ReadFileByScanner read = new ReadFileByScanner();
				read.openFile(stopFilename);
				tmp=read.readFileByLines();
				read.closeFile();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void closeFile(){
			File.close();
		//	System.out.println("The File Is Closed !!");
		
			
			
			
		}
		
}
