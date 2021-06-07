package classify;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class Global_Freq_C1VSC2 {

	/**
	 * @param args
	 */
	 
	
	public static void main(String[] args) throws IOException {
		golbalFreq();		
		System.out.println("ደህና");
	}
	public static void golbalFreq() throws IOException{
		String bioVoc="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/bio_TF.txt";
		 String defVoc="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/def_TF.txt";
		 String desVoc="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Each_Cat_TermFreq/des_TF.txt";
		globalVoc(defVoc,bioVoc,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/defVSbio.txt");
		globalVoc(defVoc,desVoc,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/defVSdes.txt");
		globalVoc(desVoc,bioVoc,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/desVSbio.txt");
	}
	public static void globalVoc(String file1,String file2, String write_to_file)throws IOException
	{
		String c1=FileUtils.readFileToString(new File(file1));
		String c2=FileUtils.readFileToString(new File(file2));
		StringBuilder vocbld=new StringBuilder();
		StringTokenizer c1tok= new StringTokenizer(c1);
			while(c1tok.hasMoreElements())
			{
				String c1t=c1tok.nextToken();
				Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
				boolean flag=false;
				StringTokenizer c2tok=new StringTokenizer(c2);
				while(c2tok.hasMoreElements()){//check co-occurence
					String c2t=c2tok.nextToken();
					Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						vocbld.append(c1t+"\t\t\t"+c1val+"\t"+c2val+"\n");
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c1t+"\t\t"+c1val+"\t"+0+"\n");
				
				}
			}
			//If a person term is not in place term.
			StringTokenizer c2tok=new StringTokenizer(c2);
			while(c2tok.hasMoreElements()){
				String c2t=c2tok.nextToken();
				Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
				boolean flag=false;
				c1tok=new StringTokenizer(c1);
				while(c1tok.hasMoreElements()){
					String c1t=c1tok.nextToken();
					//Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c2t+"\t\t"+0+"\t"+c2val+"\n");
				}
			}
		FileUtils.writeStringToFile(new File(write_to_file), vocbld.toString());
				
	}
	// to construct def Vs des
/*	
	public static final void globalVoc2(String file1,String file2)throws IOException
	{
		String c1=FileUtils.readFileToString(new File(file1));
		String c2=FileUtils.readFileToString(new File(file2));
		StringBuilder vocbld=new StringBuilder();
		StringTokenizer c1tok= new StringTokenizer(c1);
			while(c1tok.hasMoreElements())
			{
				String c1t=c1tok.nextToken();
				Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
				boolean flag=false;
				StringTokenizer c2tok=new StringTokenizer(c2);
				while(c2tok.hasMoreElements()){//check co-occurence
					String c2t=c2tok.nextToken();
					Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						vocbld.append(c1t+"\t\t\t"+c1val+"\t"+c2val+"\n");
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c1t+"\t\t"+c1val+"\t"+0+"\n");
				
				}
			}
			//If a person term is not in place term.
			StringTokenizer c2tok=new StringTokenizer(c2);
			while(c2tok.hasMoreElements()){
				String c2t=c2tok.nextToken();
				Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
				boolean flag=false;
				c1tok=new StringTokenizer(c1);
				while(c1tok.hasMoreElements()){
					String c1t=c1tok.nextToken();
					//Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c2t+"\t\t"+0+"\t"+c2val+"\n");
				}
			}
		
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/defVSdes.txt"), vocbld.toString());
				
				
	}
	// to construct des vs bio
	public static final void globalVoc3(String file1,String file2)throws IOException
	{
		String c1=FileUtils.readFileToString(new File(file1));
		String c2=FileUtils.readFileToString(new File(file2));
		StringBuilder vocbld=new StringBuilder();
		StringTokenizer c1tok= new StringTokenizer(c1);
			while(c1tok.hasMoreElements())
			{
				String c1t=c1tok.nextToken();
				Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
				boolean flag=false;
				StringTokenizer c2tok=new StringTokenizer(c2);
				while(c2tok.hasMoreElements()){//check co-occurence
					String c2t=c2tok.nextToken();
					Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						vocbld.append(c1t+"\t\t\t"+c1val+"\t"+c2val+"\n");
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c1t+"\t\t"+c1val+"\t"+0+"\n");
				
				}
			}
			//If a person term is not in place term.
			StringTokenizer c2tok=new StringTokenizer(c2);
			while(c2tok.hasMoreElements()){
				String c2t=c2tok.nextToken();
				Integer c2val=Integer.parseInt(c2tok.nextToken().toString());
				boolean flag=false;
				c1tok=new StringTokenizer(c1);
				while(c1tok.hasMoreElements()){
					String c1t=c1tok.nextToken();
					//Integer c1val=Integer.parseInt(c1tok.nextToken().toString());
					if(c1t.trim().equals(c2t.trim()))
					{
						flag=true;
						break;
					}
				}
				if(!flag){
					vocbld.append(c2t+"\t\t"+0+"\t"+c2val+"\n");
				}
			}
				
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Cat1VsCat2_Freq/desVSbio.txt"), vocbld.toString());
				
	}
	*/

}
