package classify;

import index.shortword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class Classify_Q implements Runnable{
	static int termId=0;
	private final InputStream istrm_;
	private final OutputStream ostrm_;
	
	public Classify_Q(InputStream istrm,OutputStream ostrm) {
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
	public static void Train() throws  InterruptedException
	{
		String []sWeights={"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdefVSbio.txt","C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdefVSdes.txt",
		"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdesVSbio.txt"};
		String []models={"C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDefvsBio.model","C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDefvsDes.model",
		"C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDesvsBio.model"};
		String[] command =
	    {
	        "cmd",
	    };
	   for (int i=0;i<sWeights.length;i++){
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		
	    new Thread(new Classify_Q(p.getErrorStream(), System.err)).start();
	    new Thread(new Classify_Q(p.getInputStream(), System.out)).start();
	    PrintWriter stdin = new PrintWriter(p.getOutputStream());
	    //stdin.println("dir c:\\ /A /Q");
	   stdin.println("svm_learn "+sWeights[i]+" "+ models[i]);
	    //stdin.println("svm_learn c:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdesVSbio c:/NQA/a.model");
	    //stdin.println("svm_classify "+swtest[i]+" "+ models[i]+"C:/NQA_Final/NQA_fin/Questions/Test/Question_Type/class"+(i+1));	      
	    // write any other commands you want here
	    stdin.close();
	    int returnCode = p.waitFor();
	   //System.out.println("Return code = " + returnCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	public static void classify()throws InterruptedException{
		String []swtest={"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDefBioSorted.txt","C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDefDesSorted.txt",
		"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDesBioSorted.txt"};
		String []models={"C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDefvsBio.model","C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDefvsDes.model",
		"C:/NQA_Final/NQA_fin/Questions/Training_Models/TrainDesvsBio.model"};
		String[] command =
	    {
	        "cmd",
	    };
	   for (int i=0;i<swtest.length;i++){
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
		
	    new Thread(new Classify_Q(p.getErrorStream(), System.err)).start();
	    new Thread(new Classify_Q(p.getInputStream(), System.out)).start();
	    PrintWriter stdin = new PrintWriter(p.getOutputStream());
	    stdin.println("svm_classify "+swtest[i]+" "+ models[i]+" C:/NQA_Final/NQA_fin/Questions/Test/Question_Type/class"+(i+1));	      
	    // write any other commands you want here
	    stdin.close();
	    //
	    //stdin.close();
	    int returnCode = p.waitFor();
	    //System.out.println("Return code = " + returnCode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	public static void domatrix(File dir,String writeTo) throws IOException{
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			Map<String,Integer> map =new HashMap<String,Integer>();
			StringBuffer sb=new StringBuffer();
			String fl=FileUtils.readFileToString(files[i].getAbsoluteFile());
		StringTokenizer token=new StringTokenizer(fl);
		  while(token.hasMoreElements()){
			  String x=token.nextToken();
			 if(map.containsKey(x))
			 {
				 map.put(x, Integer.parseInt(map.get(x).toString())+1);
			 }
			 else
				 map.put(x,1); 
			  }
		  Iterator<?> it=map.keySet().iterator();
		  while(it.hasNext()){
			  String val=it.next().toString();
			  sb.append(val+"\t"+map.get(val)+"\n"); 
			  } 
			  FileUtils.writeStringToFile(new File(writeTo), sb.toString());// Test question TF file
			  }	
	}
	public static void openFile(String file_to_read,String quesFile,String write_to,int group)throws IOException{
		
		FileReader fr=new FileReader(file_to_read);
		BufferedReader tr= new BufferedReader(fr);
		//String quesFile="C:/NQA_Final/NQA_fin/Questions/Test/Test_TF/t";
		FileReader q=new FileReader(quesFile);
		BufferedReader qbf=new BufferedReader(q);
		int qnLines=readLines(new File(quesFile));
		
		String qTerms[]=new String[qnLines];
				
		int numberOfLines= readLines( new File(file_to_read));
		termId=numberOfLines;
		String[] tda=new String[numberOfLines];
		for (int i = 0; i < numberOfLines; i++) {
			tda[i]=tr.readLine();
			//System.out.println(i+" : "+tda[i]);
		}
		tr.close();			
		for (int i = 0; i < qnLines; i++) {
			qTerms[i]=qbf.readLine();	
			//System.out.println(i+" : "+qTerms[i]);
		}
		qbf.close();
		if(group==1)
		assignWeight(tda, qTerms, write_to, 1);
		else if(group==2)
			assignWeight(tda, qTerms, write_to, 2);
		else if(group==3)
			assignWeight(tda, qTerms, write_to, 3);	
		else
			assignWeight(tda, qTerms, write_to, 4);//Used for the text categorization of the summerizer
				
	}
	public static void assignWeight(String[] tda, String[] qTerms,String write_to,int group) throws IOException//1 is used to indicate the testing question will be prepared by considering defvsbioTDF group
	{
		int N=0;//Total number of questions with in the group
		String r="";
		String s="";
		String train_termId="";
		String train_termdf="";
		String res;
		StringBuilder sb= new StringBuilder();
		sb.append("0 ");
		
		for(int k=0;k<qTerms.length;k++){		
		StringTokenizer qst2= new StringTokenizer(qTerms[k]);
		//System.out.println(qTerms.length);
		if(qst2.hasMoreTokens())
		{
			r=qst2.nextToken();
			s=qst2.nextToken();
		}
	   res=checkTerm(tda, r);
		if(res==null)
		{
			//int termnf=findTDF(r)+Integer.parseInt(s);
			int tf=Integer.parseInt(s);
			int df=1;
			if (group==1)
				N=901;
			else if (group==2)
				N=901;
			else if(group==3)
				N=901;
			else
				N=20;
			double idf=N/df;
			//System.out.println(N);
			termId=termId+1;
			//sb.append(termId+":"+qtok[k+1].toString()+" ");
            double tfidf= Math.log(tf*idf);
			sb.append(termId+":"+tfidf+" ");
		}
		else
		{
			
			StringTokenizer stres= new StringTokenizer(res);
			train_termId=stres.nextToken();
			train_termdf=stres.nextToken();
			int tf=Integer.parseInt(s);
			int df=Integer.parseInt(train_termdf)+1;
			if (group==1)
				N=901;
			else if (group==2)
				N=901;
			else if(group==3)
				N=901;
			else
				N=20;
			double idf=N/df;
			double tfidf= Math.log(tf*idf);
			sb.append(train_termId+":"+tfidf+" ");
		}						
		}
	//System.out.println(sb.toString());
	FileUtils.writeStringToFile(new File(write_to+".txt"), sb.toString());
	//StringBuilder sb_qweight=assignWeight(dir, Termfreq, docs);
	if(group==1)
		sortWeight(sb.toString(),"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDefBioSorted");
	else if(group==2)
		sortWeight(sb.toString(),"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDefDesSorted");
	else if(group==3)
		sortWeight(sb.toString(),"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight_Sorted/testVsDesBioSorted");
	else
		sortWeight(sb.toString(), "C:/NQA_Final/summaryTrainingDocs/TestSummerized/Test_Weight/testSorted");
	}
	public static int findTDF(String r) throws IOException {//I didn't used this function -- the function tries to find term frequency through out the group /terms/files
		int count=0;
		File  qdef_file=new File("C:/NQA_Final/NQA_fin/Questions/Test/Test_Def_TF/");
		File[] files=qdef_file.listFiles();
		for (int i = 0; i < files.length; i++) {
			String f1= FileUtils.readFileToString(files[i].getAbsoluteFile());
			StringTokenizer st= new StringTokenizer(f1);
			if((st.nextToken().toString()).equals(r))
			{
				count=count+Integer.parseInt(st.nextToken().toString());
			}
		}
		return count;
	}
	public static String checkTerm(String []tda,String qt){
		int flag=0;
		String t="";
		String u="";
		String v="";
		String result="";
		for (int l=0;l<tda.length;l++){
			flag=0;			
		StringTokenizer sda=new StringTokenizer(tda[l]);		
		if (sda.hasMoreTokens())
		{
			t=sda.nextToken();
			u=sda.nextToken();
			v=sda.nextToken();
		}			
		if(qt.compareTo(t)==0){
			//sb.append(u+":"+(Integer.parseInt(v.toString())+Integer.parseInt(s.toString()))+" ");
			flag=1;
			break;
		}
		}
		if (flag==1)
			result= u + " "+ v;
		else 
			result=null;
		return result;
	}
	
	public static int readLines(File f ) throws IOException{
		FileReader fr=new FileReader(f);
		BufferedReader bf=new BufferedReader(fr);
		int numberOfLines=0;
		while((bf.readLine())!=null){
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;
	}
	
	public static void sortWeight(String weightfile,String write_to) throws IOException{
		
		StringTokenizer st=new StringTokenizer(weightfile,"\n");
		StringBuilder sb=new StringBuilder();
		while(st.hasMoreElements())	{
			String oneline=st.nextToken();
			if(!oneline.isEmpty()){
				Integer cls=Integer.parseInt(oneline.substring(0, 2).trim());//class of the document
				sb.append(cls);
				oneline=oneline.substring(2, oneline.length());
				StringTokenizer stln=new StringTokenizer(oneline," ");
				Map<Integer,String> feature=new TreeMap<Integer,String>();
				int wordcount=0;
				//StringTokenizer stlnw=stln;
				//double norm=0.0;
//				while(stlnw.hasMoreElements())
//				{
//					String val =stln.nextToken();
//					//Integer ft =Integer.parseInt(val.substring(0, val.indexOf(":")));
//					// Double wet=Double.parseDouble((val.substring(val.indexOf(":")+1, val.length())));
//					 //norm=norm+wet*wet;//w^2 and summation
//				}
				//norm=Math.sqrt(norm);
				stln=new StringTokenizer(oneline," ");
				while(stln.hasMoreElements()){ 
					wordcount++;
					String val =stln.nextToken();
					Integer ft=Integer.parseInt(val.substring(0, val.indexOf(":")));
					 Double wet=Double.parseDouble((val.substring(val.indexOf(":")+1, val.length())));
					 //wet=wet/norm;
			//		val=df.format(wet);//double val
					feature.put(ft,wet.toString());
				}
				Iterator<?> it=feature.keySet().iterator();
				while(it.hasNext())
					{
					Integer key=(Integer) it.next();
					sb.append(" "+key.toString()+":"+feature.get(key).toString());
					}
				sb.append("\n");
			}
			//System.out.println(sb.toString());
		}
		
		FileUtils.writeStringToFile(new File(write_to+".txt"), sb.toString());
		
	}
public static String svmQType(String q) throws IOException{
	Character_Normalization cn= new Character_Normalization();
	shortword sw= new shortword();
	q=cn.Normalizedoc(q);
	//q=sw.Expander(q);
	FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Test/Test_Q/Test_question"), q.toString());
	//prepare the question TF within the question
	File test= new File("C:/NQA_Final/NQA_fin/Questions/Test/Test_Q/");
	domatrix(test,"C:/NQA_Final/NQA_fin/Questions/Test/Test_TF/t");//create frequency of terms with in the question and brings copy of Def_Bio_TF,... to Test_DefBTF respectively  
	//String temp=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/Test/Test_TF/t").getAbsoluteFile());
	//System.out.println(temp);
	
//try {
//		Train();
//	} catch (InterruptedException e1) {			
//		e1.printStackTrace();
//	}
	
	//Reads the test question and compares it with the three groups(defbio,...)and 
	//prepares the test question weight and sort finally write the two files to their respective folder 
	String quesFile="C:/NQA_Final/NQA_fin/Questions/Test/Test_TF/t";
	String defVSbioTDF="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSbioTermDocFreq.txt";
	String defVSdesTDF="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSdesTermDocFreq.txt";
	String desVSbioTDF="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/desVSbioTermDocFreq.txt";
try {
	openFile(defVSbioTDF,quesFile,"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight/testVsDefBio",1);
	openFile(defVSdesTDF,quesFile,"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight/testVsDefDes",2);
	openFile(desVSbioTDF,quesFile,"C:/NQA_Final/NQA_fin/Questions/Test/TestWeight/testVsDesBio",3);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	//System.out.println("á‹°áˆ…áŠ“");
	
	
	//Finding the class of the test question
	try {
		classify(); 
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	///Compute each class' frequency
	String c1=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/Test/Question_Type/class1").getAbsoluteFile());
	String c2=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/Test/Question_Type/class2").getAbsoluteFile());
	String c3=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/Test/Question_Type/class3").getAbsoluteFile());
	
	int def=0,bio=0,des=0;	
	String type="";
	Double x=Double.parseDouble(c1);
	Double y=Double.parseDouble(c2);
	Double z=Double.parseDouble(c3);
//System.out.println(" The first type"+Double.parseDouble(c1)+" The second type"+Double.parseDouble(c2)+" The third type"+Double.parseDouble(c3));
	
if(x>0.5 ) def++; else if(x<-0.5)	bio++;
if(y>0.5) def++;	else if(y<-0.5)	des++;
if(z>0.5) des++;	else if(z<-0.5)	bio++;
System.out.println(def + " "+ des +" "+bio);
if((x>=0.5&&y>=0.5))//||(def>des&&def>bio)) 
	type="Definition";
else if ((y<=-0.5&&z>=0.5))//||(des>def&&des>bio)) 
	type="Description";
else if ((x<=-0.5&&z<=-0.5))//||(bio>def&& bio>des)) 
	type="Biography";
else type="Unknown";
//		if(def>bio && def>=des && Double.parseDouble(c1)>Double.parseDouble(c2))type="Def"; //&& Double.parseDouble(c2)>=0.5 && p>qu)
//	else if(des>def && des>bio) type="Des";// && t>qu)
//	else if(bio>def && bio>des) type="Bio";//Double.parseDouble(c2)<=-0.5 && pl>t && pl>qu)
//	else type="null";
	//System.out.println("The Answer Type by the SVM classifier is: "+type);
	return type; 
}

public static void main(String[] args)throws IOException, InterruptedException {
		
	//String q= "ጥናትና ምርምር ምንድንነው";
	//Train();
	//String q= "ደን ማለት ምን ማለት ነው";//
	//String q="አርቲስት መሀሙድ አህመድ ማነው";
	String q="መለስ ዜናዊ ማነው";
	 q="ሕገ መንግስት ምን ማለት ነው";
//	q="የሕገ መንግስት ጥቅም ምንድንነው";
//	q="የትምህርት አገልግሎቱ ምን እንደሆነ አብራራ";
//	 q="የትምህርት ጥቅሙ ምንድንነው";
//	 q="የውሃ ጥቅም ምንድንነው";
//	 q="ፀሐይ ለእፅዋት እድገት ድርሻዋ ምንድንነው";
//	 q="ሉሲዎች ማናቸው";
	
//		 q="ዳግማዊ ዓፄ ሚኒልክ ማናቸው";
//		 q = "መኮንን ፈንታው ስንት ልጅ ወለደ?";
//		 q = "መኮንን ፈንታው ስንት ልጅ ማነው?";
//	 q="ሥርዓተ ነጥቦች በአማርኛ ቋንቋ ውስጥ ያላቸው ድርሻ ምንድንነው";
//	 q="አትሌት መሠረት ደፋር ማናት";
//	q="የዩኒቨርሲቲ ጥቅሙ ምንድንነው";
//	q="የሰሃን ጥቅም ምንድንነው?";
//	q="የወተት ጥቅም ምንድንነው?";
	 q="አስቴር አወቀ ማናት";
//		q= "ዩኒቨርሲቲ በአንድ ማህበረሰብ ውስጥ ያለው ፋይዳ ምንድንነው";
		 //q="አምስተኛው የኢትዮጵያ ፓትርያርክ የነበሩት ብጹዕ አቡነ ጳውሎስ ማናቸው";
		//Write the question to file
		String type=Classify_Q.svmQType(q);
		System.out.println("The Q Type By SVM is: "+type);
		AnalyzeQuestion anq= new AnalyzeQuestion();
		//System.out.println("The Answer Type by Rule Based classifier is: "+anq.AnalyzedQuery(q));
		if (!type.equals("Unknown") && !anq.QType.equals("Unknown")){
			QueryGenerator qg= new QueryGenerator();
			String[]query=qg.generateQuery(q);
			System.out.println(query[0]);
		}
	}
}
