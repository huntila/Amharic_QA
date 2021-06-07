package classify;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import answerExtraction.AnswerExtraction;

public class A_Experiment {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ደህና");
//		prepareForExperiment();
//		try {
//			Classify_Q.Train();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		experimentClassify();
//		testAnswerExtraction();
		testBiography();

	}
	public static void prepareForExperiment() throws IOException{
		Write_Each_Q_File.writeToFile();
		Term_Frequency.termFreq();
		Category_Vocabulary.categoryFreq();
		Global_Freq_C1VSC2.golbalFreq();
		Term_Doc_Freq.documentFreq();
		Weight.computeWeight();
		Sort_Weight.sWeigth();		
	}
	public static void experimentClassify() throws IOException{
		//Write each test question to file
//		String defQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/testDef.txt"),"utf-8");
//		String desQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/testDes.txt"),"utf-8");
//		String bioQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/testBio.txt"),"utf-8");
		String defQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/testDef10.txt"),"utf-8");
		String desQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/testDes10.txt"),"utf-8");
		String bioQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/testB10.txt"),"utf-8");
		//String a=FileUtils.readFileToString(new File("Libraries/Documents/Doc1.txt"), "utf-16");
		//System.out.println(a);
		double wholeCountSVM=0.0;
		double wholeCountRB=0.0;
		StringTokenizer stDef= new StringTokenizer(defQ,"\n");
		StringTokenizer stDes=new StringTokenizer(desQ, "\n");
		StringTokenizer stBio=new StringTokenizer(bioQ, "\n");
		int f=1;
		AnalyzeQuestion an= new AnalyzeQuestion();
		StringBuilder sb= new StringBuilder();
		double count1=0.0,count2=0.0;
		String svmQTypeRes;
		String ruleQTypeRes;
		String ques;
		while (stDef.hasMoreElements()) {
			ques=stDef.nextToken();
			svmQTypeRes=Classify_Q.svmQType(ques);
			ruleQTypeRes=an.AnalyzedQuery(ques);
			if(svmQTypeRes=="Definition")
				count1++;
			if(ruleQTypeRes=="Definition")
				count2++;
			sb.append(f+"\t"+ques+"\tSVM:"+svmQTypeRes+"\tRB:"+ruleQTypeRes+"\n");			
			f++;
		}
		wholeCountSVM=wholeCountSVM+count1;
		wholeCountRB=wholeCountRB+count2;
		double svmEff=count1/50.0;
		double rbEff=count2/50.0;
		//System.out.println(sb.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testDefResult10.txt"), sb.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testDefResult10.txt"), "\nSVM Efficiency = "+svmEff+"\nRB Efficiency = "+rbEff,true);
		StringBuilder sb2=new StringBuilder();
		f=1;
		count1=0.0;
		count2=0.0;
		while (stDes.hasMoreElements()) {
			ques=stDes.nextToken();
			svmQTypeRes=Classify_Q.svmQType(ques);
			ruleQTypeRes=an.AnalyzedQuery(ques);
			if(svmQTypeRes=="Description")
				count1++;
			if(ruleQTypeRes=="Description")
				count2++;
			sb2.append(f+"\t"+ques+"\tSVM:"+svmQTypeRes+"\tRB:"+ruleQTypeRes+"\n");			
			f++;
		}
		//System.out.println(sb2.toString());
		wholeCountSVM=wholeCountSVM+count1;
		wholeCountRB=wholeCountRB+count2;
		svmEff=count1/50.0;
		rbEff=count2/50.0;
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testDesResult10.txt"), sb2.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testDesResult10.txt"), "\nSVM Efficiency = "+svmEff+"\nRB Efficiency = "+rbEff,true);
		StringBuilder sb3=new StringBuilder();
		f=1;
		count1=0.0;
		count2=0.0;
		while (stBio.hasMoreElements()) {
			ques=stBio.nextToken();
			svmQTypeRes=Classify_Q.svmQType(ques);
			ruleQTypeRes=an.AnalyzedQuery(ques);
			if(svmQTypeRes=="Biography")
				count1++;
			if(ruleQTypeRes=="Biography")
				count2++;
			sb3.append(f+"\t"+ques+"\tSVM:"+svmQTypeRes+"\tRB:"+ruleQTypeRes+"\n");			
			f++;
		}
		wholeCountSVM=wholeCountSVM+count1;
		wholeCountRB=wholeCountRB+count2;
		svmEff=count1/50.0;
		rbEff=count2/50.0;
		//System.out.println(sb3.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testBioResult10.txt"), sb3.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testBioResult10.txt"), "\nSVM Efficiency = "+svmEff+"\nRB Efficiency = "+rbEff,true);
		double svmWEfficiency= wholeCountSVM/150.0;
		double rbWEfficiency=wholeCountRB/150.0;
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/SVMTestResult/testResult10.txt"), "\n10. \nSVM Efficiency = "+svmWEfficiency+"\nRB Efficiency = "+rbWEfficiency,true);
		
	}

	public static void testAnswerExtraction() throws Exception{
		String ans = "";
		String question = "";
		String defQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/defQ.txt"),"utf-16");
		String desQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/desQ.txt"),"utf-16");
		String bioQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/bioQ.txt"),"utf-16");
		
		
		StringTokenizer stDef= new StringTokenizer(defQ,"\n");
		StringTokenizer stDes=new StringTokenizer(desQ, "\n");
		StringTokenizer stBio=new StringTokenizer(bioQ, "\n");
		int f=1;
//		StringBuilder sb= new StringBuilder();
//		
//		while (stDef.hasMoreElements()) {
//			question=stDef.nextToken();
//			ans=AnswerExtraction.runEveryThing(question);			
//			sb.append(f+". "+question+"\tQ Type"+AnswerExtraction.QuesType+"\n"+ans+"\n");
//			FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testDefResult.txt"), f+". "+question+"\tQ Type"+AnswerExtraction.QuesType+"\n"+ans+"\n",true);
//			f++;
//		}
//		System.out.println(sb.toString());
//		//FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testDefResult.txt"), sb.toString());
		StringBuilder sb2=new StringBuilder();
		f=1;
		while (stDes.hasMoreElements()) {
			question=stDes.nextToken();
			ans=AnswerExtraction.runEveryThing(question);
			sb2.append(f+". "+question+"\tQ Type"+AnswerExtraction.QuesType+"\n"+ans+"\n");
			FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testDesResult.txt"),f+". "+question+"\tQType: "+AnswerExtraction.QuesType+"\n"+ans+"\n",true );
			f++;
		}
		System.out.println(sb2.toString());
		/*FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testDesResult.txt"), sb2.toString());
		StringBuilder sb3=new StringBuilder();
		f=1;
		while (stBio.hasMoreElements()) {
			question=stBio.nextToken();
			ans=AnswerExtraction.runEveryThing(question);
			sb3.append(f+". "+question+"\tQ Type"+AnswerExtraction.QuesType+"\n"+ans+"\n");			
			f++;
		}
		//System.out.println(sb3.toString());
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/testBioResult.txt"), sb3.toString());
		*/
	}
	public static void testBiography() throws Exception{
		
		File sysR=new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/manBAns/");
		File manR=new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/sysBAns/");
		File [] files=sysR.listFiles();
		//System.out.println(files.length);
		File [] filesMan=manR.listFiles();
		//System.out.println(filesMan.length);
		Character_Normalization cn= new Character_Normalization();
		
		double count=0.0;
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/bioRecallPrecision.txt"),"No \t Recall \t Precision\n");
		for (int i = 0; i < files.length; i++) {
					
			String fs=FileUtils.readFileToString(files[i].getAbsoluteFile(),"utf-16");
			fs=cn.Normalizedoc(fs);
			fs=AnswerExtraction.replaceArat_Hulet(fs);
			System.out.println(fs);
			String[] fs_Sen=StringUtils.split(fs,"፡፡");
			String fm=FileUtils.readFileToString(filesMan[i].getAbsoluteFile(),"utf-16");
			fm=cn.Normalizedoc(fm);
			fm=AnswerExtraction.replaceArat_Hulet(fm);
			String[] fm_Sen=StringUtils.split(fm,"::");
			//System.out.println(fm);
			double sys_length=fs_Sen.length;
			double man_length=fm_Sen.length;
			
			count=0.0;
			for (int j = 0; j < fs_Sen.length; j++) {
				if(StringUtils.contains(fm,fs_Sen[j]))
				{
					count++;
				}
			}
			System.out.println(count);
			System.out.println(man_length);
		  double recall=count/man_length;
		  double precision=count/sys_length;
			
		 FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/AE_Test/bioRecallPrecision.txt"), i+"\t"+recall+"\t"+precision+"\n",true);
		}
	}
}
