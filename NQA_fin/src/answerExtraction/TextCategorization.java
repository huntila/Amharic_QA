package answerExtraction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import classify.Character_Normalization;
import classify.Classify_Q;
import classify.Global_Freq_C1VSC2;
import classify.Sort_Weight;
import classify.Term_Doc_Freq;
import classify.Weight;

public class TextCategorization implements Runnable {
	public static int  tyRes=0;
	/**
	 * @param args
	 * @throws IOException 
	 */
	private final InputStream istrm_;
	private final OutputStream ostrm_;
	
public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("ደህና");
		svmSummeryCategorization(null);
	}
	
public TextCategorization(InputStream istrm,OutputStream ostrm) {
		 istrm_=istrm;
		 ostrm_=ostrm;
	}
public void run(){
		try {
			final byte[] buffer=new byte[1024];
			for (int length = 0; (length =this.istrm_.read(buffer))!=-1; length++) {
				ostrm_.write(buffer, 0, length);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
public static String svmSummeryCategorization(String text) throws IOException{
	prepare_for_Training();
	try {
		Train();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	prepare_for_classification();
	try {
		classify();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String classfierResult=FileUtils.readFileToString(new File("C:/NQA_Final/summaryTrainingDocs/classfnResult").getAbsoluteFile());
	String type="";
	
	Double x=Double.parseDouble(classfierResult);
	if(x>=0.3){
		type="Good Biography";
		tyRes=1;
	}
		
	else if(x>0 && x<0.3)
		{
		type="Bad Biography";
		tyRes=2;
		}
	else if (x<=0)
		{
		type="Not Biography";
		tyRes=3;
		}
	System.out.println("The Answer Type by the SVM classifier is: "+type);
	return type; 
}
public static void Train() throws  InterruptedException
	{
		String sWeights="C:/NQA_Final/summaryTrainingDocs/Term_Freq/Weight/sortwmVSs.txt";
		String models="C:/NQA_Final/summaryTrainingDocs/TC.model";
		String[] command =
	    {
	        "cmd",
	    };
	   
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		
	    new Thread(new TextCategorization(p.getErrorStream(), System.err)).start();
	    new Thread(new TextCategorization(p.getInputStream(), System.out)).start();
	    PrintWriter stdin = new PrintWriter(p.getOutputStream());
	    //stdin.println("dir c:\\ /A /Q");
	   stdin.println("svm_learn "+sWeights+" "+ models);
	    stdin.close();
	    int returnCode = p.waitFor();
	    System.out.println("Return code = " + returnCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
public static void prepare_for_Training() throws IOException{
	
	//termFreq
	File mannually=new File("C:/NQA_Final/summaryTrainingDocs/manually/");
	File bySum=new File("C:/NQA_Final/summaryTrainingDocs/byMSum/");
	termFreq(mannually,"C:/NQA_Final/summaryTrainingDocs/Term_Freq/man_TF/",1);
	termFreq(bySum,"C:/NQA_Final/summaryTrainingDocs/Term_Freq/byMsum_TF/",2);
	//category Frequency
	File manTF=new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/man_TF/");
	File bySTF=new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/byMsum_TF/");
	categoryFreq(manTF, "C:/NQA_Final/summaryTrainingDocs/Term_Freq/Each_Cat_TermFreq/man_TF.txt");
	categoryFreq(bySTF, "C:/NQA_Final/summaryTrainingDocs/Term_Freq/Each_Cat_TermFreq/byS_TF.txt");
	//global vocabulary frequency
	String manVoc="C:/NQA_Final/summaryTrainingDocs/Term_Freq/Each_Cat_TermFreq/man_TF.txt";
	 String bySVoc="C:/NQA_Final/summaryTrainingDocs/Term_Freq/Each_Cat_TermFreq/byS_TF.txt";
	 Global_Freq_C1VSC2.globalVoc(manVoc,bySVoc,"C:/NQA_Final/summaryTrainingDocs/Term_Freq/MVSS/mVSs.txt");
	//Term Document Frequency
	 String allVocs="C:/NQA_Final/summaryTrainingDocs/Term_Freq/MVSS/mVSs.txt";
		File alldir= new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/Both_TF/");
		Term_Doc_Freq.termDoc(alldir,allVocs,"C:/NQA_Final/summaryTrainingDocs/Term_Freq/TDF/mVSsTDF");
	//compute weight
		File manDir=new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/man_TF/");
		File sDir=new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/byMsum_TF/");
		String termdocfreq="C:/NQA_Final/summaryTrainingDocs/Term_Freq/TDF/mVSsTDF.txt";
		StringBuilder sb= new StringBuilder();
		sb=Weight.assignewt(manDir, termdocfreq, 1);
		sb.append(Weight.assignewt(sDir, termdocfreq, -1));
		FileUtils.writeStringToFile(new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/Weight/wmVSs"), sb.toString());
	//Sort weight
		String weight="C:/NQA_Final/summaryTrainingDocs/Term_Freq/Weight/wmVSs";
		Sort_Weight.sortWeight(weight,"C:/NQA_Final/summaryTrainingDocs/Term_Freq/Weight/sortwmVSs.txt");
}
public static void prepare_for_classification() throws IOException{
		File summerizerResult= new File("C:\\NQA_Final\\files_summerizerResult\\Result\\");
		termFreq(summerizerResult, "C:\\NQA_Final\\summaryTrainingDocs\\TestSummerized\\Test_TF\\testSummerized", 3);
	String summerized_TF="C:\\NQA_Final\\summaryTrainingDocs\\TestSummerized\\Test_TF\\testSummerized1";
	String manVSsum="C:/NQA_Final/summaryTrainingDocs/Term_Freq/TDF/mVSsTDF.txt";
		Classify_Q.openFile(manVSsum,summerized_TF,"C:/NQA_Final/summaryTrainingDocs/TestSummerized/Test_Weight/tWeight",4);
	}
public static void classify()throws InterruptedException{
	String swtest="C:/NQA_Final/summaryTrainingDocs/TestSummerized/Test_Weight/testSorted.txt";
	String models="C:/NQA_Final/summaryTrainingDocs/TC.model";
	String[] command =
    {
        "cmd",
    };
 	Process p;
	try {
		p = Runtime.getRuntime().exec(command);
	
    new Thread(new Classify_Q(p.getErrorStream(), System.err)).start();
    new Thread(new Classify_Q(p.getInputStream(), System.out)).start();
    PrintWriter stdin = new PrintWriter(p.getOutputStream());
    stdin.println("svm_classify "+swtest+" "+ models+" C:/NQA_Final/summaryTrainingDocs/classfnResult");	      
      stdin.close();
    int returnCode = p.waitFor();
    //System.out.println("Return code = " + returnCode);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public static void termFreq(File dir, String fpath,int flag)throws IOException {//Character normalization and stop word filter will be doneon the doc 
	String stoplist=FileUtils.readFileToString(new File("C:/Gazetteers/stoplist.txt"),"utf-16");
	File [] files=dir.listFiles();
	Character_Normalization cn= new Character_Normalization();
	for (int i = 0; i < files.length; i++) {
		Map<String, Integer> map=new HashMap<String, Integer>();
		StringBuffer sb=new StringBuffer();
		String f1=FileUtils.readFileToString(files[i].getAbsoluteFile(),"utf-16");
		f1=cn.Normalizedoc(f1);//Character Normalization
		StringTokenizer token= new StringTokenizer(f1);
		while (token.hasMoreElements()) {
			String x = token.nextToken();
			//x=cn.Normalizedoc(x);
			if(!stopWF(stoplist,x)){
			if (map.containsKey(x)) 
			{
				//map.put(x, Integer.parseInt(map.get(x).toString())+1);
				map.put(x, Integer.parseInt(map.get(x).toString())+1);
			}
			else {
				map.put(x, 1);
			}	
			}
		}
		
		Iterator<String> it= map.keySet().iterator();
		while(it.hasNext()){
			  String val=it.next().toString();
			  //if(!(val.equalsIgnoreCase("1")) )
				//  if(!(val.equalsIgnoreCase("2")))
					  sb.append(val+"\t"+map.get(val)+"\n");				 					 	 
			  } 
		int did=i+1;//document id
			  
				  FileUtils.writeStringToFile(new File( fpath+did), sb.toString());
				  if(flag==1)
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/Both_TF/"+ did), sb.toString());//writing copy of man and byMsum_term frequency in the folder
				  else if(flag==2)
					  FileUtils.writeStringToFile(new File("C:/NQA_Final/summaryTrainingDocs/Term_Freq/Both_TF/"+ did+"(2)"), sb.toString());//writing copy of man and byMsum_term frequency in the folder
//				  else
//					  FileUtils.writeStringToFile(new File("C:\\NQA_Final\\summaryTrainingDocs\\TestSummerized\\Test_TF\\testSummerized"), sb.toString());
	}
}
public static void categoryFreq(File dir,String fpath) throws IOException{
	File[] files =dir.listFiles();
	Map<String,Integer> map=new HashMap<String, Integer>();
	StringBuffer sb= new StringBuffer();
	for (int i = 0; i < files.length; i++) {
		String f1= FileUtils.readFileToString(files[i].getAbsoluteFile());
		StringTokenizer token= new StringTokenizer(f1);
		while (token.hasMoreElements()) { 
			String x = token.nextToken();
			int y=Integer.parseInt((token.nextToken().toString()));
			//System.out.println(y);
			
			if(map.containsKey(x))
			{
				map.put(x, Integer.parseInt(map.get(x).toString())+y);
				//System.out.println(x+Integer.parseInt(map.get(x).toString()));
			}
			else
				{map.put(x,1);}				
		}			
	}
	Iterator<String> it = map.keySet().iterator();
	while (it.hasNext()) {
		String val=it.next().toString();			
//		if(!(val.equalsIgnoreCase("1")) )
//			  if(!(val.equalsIgnoreCase("2")))
				  sb.append(val+"\t"+map.get(val)+"\n");			
	}
	FileUtils.writeStringToFile(new File(fpath), sb.toString());
}
public static boolean stopWF(String stoplist,String word) throws IOException{
	boolean flag=false;
	
	if(StringUtils.contains(stoplist, word))
		flag=true;
	else 
		flag=false;
	return flag;
}

}
