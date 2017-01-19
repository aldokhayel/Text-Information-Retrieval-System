
public class loadStemmingprocessor {
	
	private LinkedList<String> stem;
	
	public void loadStemming(String stemsFilename) {
		// Loads the stemming list into appropriate data structure.
		try {
			ReadFileByScanner read = new ReadFileByScanner();
			read.openFile(stemsFilename);
			stem=read.readFileByLines();
			read.closeFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BST<String> StemmingArray(String stemFile){
		BST<String> stringArray = new BST<String>();
		
		loadStemming(stemFile);
		stem.findFirst();

		String[] toString;
		
		
		
		while(!stem.last()){
			toString = stem.retrieve().split("[:,]");
			for (int i = 1; i < toString.length; i++) {
				stringArray.insert(toString[i] , toString[0]);
			}
			stem.findNext();
		}
		toString = stem.retrieve().split("[:,]");
		for (int i = 1; i < toString.length; i++) {
			stringArray.insert(toString[i] , toString[0]);
		}
		
		
		
		return stringArray;
	}

}
