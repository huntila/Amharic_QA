package classify;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Term_Doc_Freq {

	/**
	 * @param args
	 * produce document frequency of terms
	 */
	public static void main(String[] args) throws IOException {
		documentFreq();
		//System.out.println("Ã¡â€¹Â°Ã¡Ë†â€¦Ã¡Å â€œ");
		System.out.println("ደህና");

	}
	public static void documentFreq() throws IOException{
		String allVocs="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/defVSbio.txt";
		String allVocs1="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/defVSdes.txt";
		String allVocs2="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/desVSbio.txt";
		File alldir= new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Bio_TF/");
		File alldir1= new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Def_Des_TF/");
		File alldir2= new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_Des_Bio_TF/Des_Bio_TF/");
		termDoc(alldir,allVocs,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSbioTermDocFreq");
		termDoc(alldir1,allVocs1,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSdesTermDocFreq");
		termDoc(alldir2,allVocs2,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/desVSbioTermDocFreq");
	}
	public static void termDoc(File dir1,String vocab,String write_to_file)throws IOException
	{
		String voc=FileUtils.readFileToString(new File(vocab));
		File[] allfiles = dir1.listFiles();
		StringTokenizer st= new StringTokenizer(voc);
		StringBuilder sb=new StringBuilder();
		int ID=0;//document Id
			while(st.hasMoreElements()){
				int count=0;
				ID++;
				String term=st.nextToken().toString();//a token from all vocabularies
				for(int i=0;i<allfiles.length;i++)
				{
					//String allfile=FileUtils.readFileToString(allfiles[i].getAbsoluteFile());
					//if(allfile.contains(term+"\t"))
						count=count+ findCount(term, allfiles[i]);
					//System.out.println(ID);
			}
				sb.append(term+"\t"+ID+"\t"+count+"\n");
				st.nextToken();
				st.nextToken();
			}
			FileUtils.writeStringToFile(new File(write_to_file+".txt"), sb.toString());
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
	public static int findCount(String term,File allfiles) throws IOException{
		int c=0;
		String x = null;
		//String y=null;	
		FileReader fr=new FileReader(allfiles);
		BufferedReader tr= new BufferedReader(fr);
		int numberOfLines= readLines(allfiles);		
		String[] tda=new String[numberOfLines];
		for (int i = 0; i < numberOfLines; i++) {
			tda[i]=tr.readLine();
			//System.out.println(i+" : "+tda[i]);
		}
		tr.close();
		for(int i=0;i<tda.length;i++)
		{
			StringTokenizer st=new StringTokenizer(tda[i]);
			x=st.nextToken();
			//y=st.nextToken();
			if(x.equals(term))
				c++;
			
		}	
		return c;
	}
	/*
	public static void termDoc1(File dir1,String vocab)throws IOException
	{
		String voc=FileUtils.readFileToString(new File(vocab));
		File[] allfiles = dir1.listFiles();
		StringTokenizer st= new StringTokenizer(voc);
		StringBuilder sb=new StringBuilder();
		int ID=0;//document Id
			while(st.hasMoreElements()){
				int count=0;
				ID++;
				String term=st.nextToken().toString();//a token from all vocabularies
				for(int i=0;i<allfiles.length;i++)
				{
					String allfile=FileUtils.readFileToString(allfiles[i].getAbsoluteFile());
					if(allfile.contains(term+"\t"))
						count++;
					//System.out.println(ID);
			}
				sb.append(term+"\t"+ID+"\t"+count+"\n");
				st.nextToken();
				st.nextToken();
			}
			FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSdesTermDocFreq.txt"), sb.toString());
	}
	public static void termDoc2(File dir1,String vocab)throws IOException
	{
		String voc=FileUtils.readFileToString(new File(vocab));
		File[] allfiles = dir1.listFiles();
		StringTokenizer st= new StringTokenizer(voc);
		StringBuilder sb=new StringBuilder();
		int ID=0;//document Id
			while(st.hasMoreElements()){
				int count=0;
				ID++;
				String term=st.nextToken().toString();//a token from all vocabularies
				for(int i=0;i<allfiles.length;i++)
				{
					String allfile=FileUtils.readFileToString(allfiles[i].getAbsoluteFile());
					if(allfile.contains(term+"\t"))
						count++;
					//System.out.println(ID);
			}
				sb.append(term+"\t"+ID+"\t"+count+"\n");
				st.nextToken();
				st.nextToken();
			}
			FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/desVSbioTermDocFreq.txt"), sb.toString());
	}
	*/
}
