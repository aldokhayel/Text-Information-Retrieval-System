
public class Preprocessor {


	private BST<String>stopWord;
	private LinkedList<LinkedList<String>>corpus;
	private BST<String>stem;
	private double[][] docTfIdf;
	private LinkedList<String> corpusOrder;
	private int numOfTerms ;
	private int nuberOfDoc;


	public Preprocessor() {
		// Initializes the required data structures.

		stopWord=null;
		corpus=null;
		stem=null;
		corpusOrder=null;
		numOfTerms=0;
		nuberOfDoc=0;
	}

	public void loadStopWords(String stopWordsFilename) {
		// Loads the stop words list into appropriate data structure.

		try {
			ReadFileByScanner stop = new ReadFileByScanner();
			stopWord = stop.lodtest(stopWordsFilename);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void loadStemming(String stemsFilename){
		loadStemmingprocessor Stemming = new loadStemmingprocessor();
		stem = Stemming.StemmingArray(stemsFilename);
	}

	public void loadCorpus(String corpusFilename) {
		// Loads the corpus into appropriate data structure.
		try {
			ReadFileByScanner read = new ReadFileByScanner();
			read.openFile(corpusFilename);
			corpus=read.readFileByWords();
			read.closeFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preprocess() {
		// Runs preprocessing on all the documents.
		// Preprocessing includes applying the following on each document:
		// (1) Initial preprocessing (initialPreprocessing method).
		// (2) Stop words removal (removeStopWords method).
		// (3) Stemming (stemming method).
		// After each  of the 3 preprocessing steps, the appropriate data structre(s) should be updated .


		try {
			String /*initial , stop , stemm ,*/ corp;
			corpus.findFirst();

			if (corpus.empty()){
				return;
			}

			while(!corpus.last()){
				if(corpus.retrieve().equals("")){
					corpus.findNext();
				}
				else{
					corp=initialPreprocessing(corpus.retrieve());
					if (stopWord !=null){					
						corp = removeStopWords(toLinkedList(corp));	
					}
					if((stem!=null)){
						corp = stemming(toLinkedList(corp));
					}
					corpus.update(toLinkedList(corp));		
				}
				corpus.findNext();
			}
			if(corpus.retrieve().equals("")){
				corpus.findNext();
			}
			else{
				corp=initialPreprocessing(corpus.retrieve());
				if (stopWord !=null){					
					corp = removeStopWords(toLinkedList(corp));	
				}
				if((stem!=null)){
					corp = stemming(toLinkedList(corp));
				}
				corpus.update(toLinkedList(corp));		
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String initialPreprocessing(LinkedList<String> document) {
		// Runs preprocessing on a single document passed as paramter.
		// The method returns the document as string after initial processing.
		String res;

		res="";
		document.findFirst();
		while(!document.last()){
			res =res+(document.retrieve()+" ");
			document.findNext();
		}
		res =res+(document.retrieve());
		//res=res.replaceAll("\\s*\\bham\\b\\s*", "");
		//res=res.replaceAll("\\s*\\bspam\\b\\s*", "");	
		res=res.replaceAll("[^a-zA-Z]", " ");
		res=res.toLowerCase();
		res=res.replaceAll("\\s+", " ");
		res=res.replaceAll("\\b\\w{1,2}\\b\\s?", "");

		if(res.endsWith(" ")) {

			res= res.substring(0, res.length() - 1);
		}
		return res;
	}

	public String removeStopWords(LinkedList<String> document) {
		// Remove stop words from a single document passed as paramter.
		// The method returns the document as string after stop word removal.
		String res;
		try {

			document.findFirst();
			while(!document.last()){
				if(stopWord.findKey(document.retrieve())){
					document.update("");
				}
				document.findNext();
			}
			if(stopWord.findKey(document.retrieve())){
				document.update("");
			}


			res = "";
			document.findFirst();
			while(!document.last()){
				if (!document.retrieve().equals("")){
					res =res+(document.retrieve()+ " ");
				}
				document.findNext();
			}
			res =res+(document.retrieve());
		} catch (Exception e) {
			res=null;
			e.printStackTrace();
		}
		return res;

	}

	public String stemming(LinkedList<String> document){

		try {
			if (document.empty()){
				return null;
			}

			String res;
			document.findFirst();
			while(!document.last()){
				if (stem.findKey(document.retrieve())){
					document.update(stem.retrieve());
				}
				document.findNext();

			}

			if (stem.findKey(document.retrieve())){
				document.update(stem.retrieve());
			}


			res = "";
			document.findFirst();
			while(!document.last()){
				if (!document.retrieve().equals("")){
					res =res+(document.retrieve()+ " ");
				}
				document.findNext();
			}
			res =res+(document.retrieve()+ "" );
			return res;
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return null;
		}

	}

	public LinkedList<String> getDocument(int i) {
		// Returns document i in the form of a linked list.
		// Each element of the list contains a single word.
		// Spaces are not included. The order of the words must be the same as in the initial text document.

		try {
			int count =0;
			corpus.findFirst();
			LinkedList<String> Document1 ;

			while(!corpus.last()){
				if (count==i){
					break;
				}
				corpus.findNext();
				count++;
			}

			if((i !=count)&&(corpus.last())){
				System.out.println("Out of range");
				return null;//case the LinkedList is empty
			}
			else
			{

				Document1 =corpus.retrieve();
				Document1.findFirst();	
			}

			if (Document1.empty()){
				System.out.print("");
				return Document1;
			}


			while(!Document1.last()){
				Document1.retrieve().replaceAll("\\s+","");
				Document1.findNext();
			}
			Document1.retrieve().replaceAll("\\s+","");

			return Document1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public LinkedList<String> toLinkedList(String document){
		try {
			String[] theDoc;
			LinkedList<String> res =new LinkedList<>();
			theDoc=document.split(" "); //
			for(int i=0 ; i< theDoc.length;i++){				
				res.insert(theDoc[i]);
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}



	public void tfIdf() {
		// Computes the tf-idf representation for all documents in the corpus.
		// The terms must be ordered in alphabetical order.
		//DocTfIdf[d]

		try {
			//
			orderInOrder();
			numOfTerms();
			nuberOfDoc();
			//			


			int n = nuberOfDoc;
			int m = numOfTerms;

			corpus.findFirst();
			docTfIdf = new double[n][m];

			for (int i =0; i<docTfIdf.length ;i++){
				docTfIdf[i]= tfIdfOneDoc(getDocument(i));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(" ERROR");

		}

	}

	public int countRemoveStopWords(LinkedList<String> document) {
		// Returns the total number of key comparisons necessary to remove all stop
		//words from the document.
		try {
			int res=0;
			if (document.empty())
				return 0;
			document.findFirst();
			while(!document.last()){
				res+=stopWord.countPross(document.retrieve());
				document.findNext();
			}
			res+= stopWord.countPross(document.retrieve());

			return res;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("wrong");
			return 0;

		}

	}

	public int countStemming(LinkedList<String> document) {
		// Returns the total number of key comparisons necessary to stem all the
		//words in the document.
		try {
			int res=0;
			if (document.empty())
				return 0;
			document.findFirst();
			while(!document.last()){
				res+=stem.countPross(document.retrieve());
				document.findNext();
			}
			res+= stem.countPross(document.retrieve());

			return res;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("wrong");
			return 0;

		}
	}

	public double[] getDocTfIdf(int i) {
		// Returns document i in the form of a tf-idf array.
		tfIdf();
		if(i<= nuberOfDoc){
			return docTfIdf[i];
		}

		double x[] =new double[1];
		x[0]=0.0;
		return x;


	}
	//new methods	

	public void nuberOfDoc(){
		try {
			if (corpus.empty())
				nuberOfDoc= 0;
			nuberOfDoc =1;
			corpus.findFirst();
			while(!corpus.last()){
				corpus.findNext();
				nuberOfDoc++;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			nuberOfDoc= 0;
		}
	}

	public void numOfTerms(){
		numOfTerms=0;
		try {

			if (corpusOrder.empty())
				numOfTerms=0;

			corpusOrder.findFirst();
			numOfTerms = 1;
			while (!corpusOrder.last()){
				numOfTerms++;
				corpusOrder.findNext();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			numOfTerms=0;
			e.printStackTrace();
		}


	}

	public LinkedList<String> toLink(){
		LinkedList<String>res = new LinkedList<String>();
		LinkedList<String>tmp ;
		corpus.findFirst();
		while (!corpus.last()){
			tmp=corpus.retrieve();
			tmp.findFirst();
			while(!tmp.last()){
				res.insert(tmp.retrieve());
				tmp.findNext();
			}
			res.insert(tmp.retrieve());
			corpus.findNext();
		}
		tmp=corpus.retrieve();
		tmp.findFirst();
		while(!tmp.last()){
			res.insert(tmp.retrieve());
			tmp.findNext();
		}
		res.insert(tmp.retrieve());

		return res;
	}

	public BST<String> corpusBST(LinkedList<String> doc){

		try {
			if (doc.empty())
				return null;

			BST<String>tmp =new BST<String>();

			doc.findFirst();
			while(!doc.last()){
				tmp.insert(doc.retrieve(), "");
				doc.findNext();
			}
			tmp.insert(doc.retrieve(), "");

			return tmp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int documentFrequency(String term){ //wrong
		try {
			if (corpus.empty())
				return 0; 
			int count=0;
			corpus.findFirst();
			while(!corpus.last()){
				BST<String> tmp = corpusBST(corpus.retrieve());
				if (tmp.findKey(term))
					count++;
				corpus.findNext();
			}
			BST<String> tmp = corpusBST(corpus.retrieve());
			if (tmp.findKey(term))
				count++;

			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	public int termFrequency(LinkedList<String> doc , String term){		
		try {
			if(doc.empty())
				return 0;
			int count =0;
			doc.findFirst();
			while(!doc.last()){
				if (doc.retrieve().equals(term)){
					count++;
				}
				doc.findNext();
			}
			if (doc.retrieve().equals(term))
				count++;
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public void orderInOrder() {
		try {
			LinkedList<String> doc = toLink();
			LinkedList<String> order = new LinkedList<String>();
			doc.findFirst();
			BST<String> tmp = corpusBST(doc);
			orderInOrder(tmp.grtRoot(), order);			

			corpusOrder = order;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("orderInOrder Error");
		}
	}
	public void orderInOrder(BSTNode<String> n ,LinkedList<String> order) {// you need to cheek !! the order.
		try {
			if(n != null) {
				orderInOrder(n.left,order);
				order.insert(n.key);
				orderInOrder(n.right ,order);
			}
			else
				return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public double idftForOneTerm(String term){
		if(nuberOfDoc!=0){
			double dft =0.0+ documentFrequency(term);
			double y =0.0+nuberOfDoc;
			if(dft!=0)
				return Math.log((y/dft));
		}
		return 0.0;
	}
	public double wtdForOneTerm(LinkedList<String> doc ,String term  ){
		double res =0.0;
		try {
			double tf =0.0+termFrequency(doc, term);
			double idft = 0.0+idftForOneTerm(term);
			if (tf >0){ //one more cheek !!
				res = ((1+Math.log(tf))*idft);
			}
		} catch (Exception e) {
			System.out.println("wtdForOneTerm");
			e.printStackTrace();
		}

		return res;
	}
	public double[] tfIdfOneDoc(LinkedList<String> notOrderDoc){
		LinkedList<String> doc = corpusOrder;
		LinkedList<String> oldDoc =notOrderDoc;
		double[] res = new double[numOfTerms];
		String tmp ;
		try {
			oldDoc.findFirst();
			doc.findFirst();
			for (int i=0;i<res.length;i++){
				tmp =doc.retrieve();
				res[i]=wtdForOneTerm(oldDoc, tmp);
				if(!doc.last())
					doc.findNext();
			}			
			//res[i]=wtdForOneTerm(oldDoc, doc.retrieve());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("tfIdfOneDoc");
			e.printStackTrace();
		}
		return res;
	}
	//end of new methods


}
