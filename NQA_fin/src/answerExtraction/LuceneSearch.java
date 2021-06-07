package answerExtraction;
//import newproject.TopicLSA;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;

import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
public class LuceneSearch {
	public static String defQ="";
	public static String query="";
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws CorruptIndexException 
	 */
	public static void main(String[] args) throws CorruptIndexException, ParseException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("ዓለማየሁ ");
		query = "ብርቱካን";
        String numH="10";
        int maxHits = Integer.parseInt(numH);
        search(query,maxHits);
	}
	public static Map<String, Double> search(String query, int maxHits) throws ParseException, CorruptIndexException, IOException {
    	File indexDir = new File("C:\\NQA_Final\\corpus\\indexdir");
    	/*x*/        

        System.out.println("Index Dir=" + indexDir.getCanonicalPath());
        System.out.println("query=" + query);
        System.out.println("max hits=" + maxHits);
        System.out.println("Hits (rank,score,file name)");
        
    /*x LuceneSearch.2 */
        Directory fsDir = FSDirectory.open(indexDir);
        IndexReader reader = IndexReader.open(fsDir);
        IndexSearcher searcher = new IndexSearcher(reader);

        String dField = "text";
       // String dField = query;
        Analyzer stdAn 
            = new StandardAnalyzer(Version.LUCENE_36);
        QueryParser parser 
            = new QueryParser(Version.LUCENE_36,dField,stdAn);
    /*x*/

    /*x LuceneSearch.3 */
        Query q = parser.parse(query);
        
        TopDocs hits = searcher.search(q,maxHits);
        ScoreDoc[] scoreDocs = hits.scoreDocs;

        StringBuffer sbu= new StringBuffer();// used to hold file names of the extracted documents 
        Map<String,Double> IR_Result=new HashMap<String, Double>();
        for (int n = 0; n < scoreDocs.length; ++n) {
            ScoreDoc sd = scoreDocs[n];
            double score = sd.score;
            int docId = sd.doc;
            Document d = searcher.doc(docId);
            String fileName = d.get("file");
            IR_Result.put(fileName,score);
            
    /*x*/
    		
            System.out.printf("%3d %4.2f  %s\n",n, score, fileName);
            
            sbu.append(fileName+"\n");// append the name of every retrieved document on new line
            //display(fileName);
            //summerizer("C:\\Users\\Hun Tila\\Documents\\checkSummerizer\\EOC.txt");
            //System.out.println();
            
        }
        reader.close();
        
       // FileUtils.writeStringToFile(new File("C:/NQA_Final/retrievedDocs.txt"), sbu.toString());
        return IR_Result;
        
    }
    public static void display(String fileName) throws IOException {
    	
    	//boolean flag=false;
    	String res=FileUtils.readFileToString(new File("C:/NQA_Final/corpus/data/"+fileName), "utf-16");
    	System.out.println(res);
    	System.out.println("ደህና");
    	
    	//summerizer("C:/NQA_Final/corpus/data/"+fileName);
    }
    public static void summerizer(String filepath) throws IOException{
    	/*
    	int rate=30;
        String txt ="";
        int approach;
        System.out.println(filepath);
                
        //filepath="C:\\Users\\Hun Tila\\Documents\\checkSummerizer\\EOC.txt";                    
              filepath = filepath.replace('\\', '/');
              //File m= new File(filepath);
              String doc = FileUtils.readFileToString(new File(filepath), "utf-16");
              System.out.println(doc);
              
              TopicLSA tp= new TopicLSA ();
                 
               try {
				txt =(tp.topiclsa(new File(filepath), rate));
				if( txt.compareToIgnoreCase("error")==0)
	             {
	                 throw new Exception();
	             }
	             else if( txt.compareToIgnoreCase("smalldocument")==0)
	             {
	                  throw new Exception();
	             }
	             else{
	            	 System.out.println("the summerized text is:");
	            	 System.out.println(txt); 
	                }
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			*/             
    }


}
