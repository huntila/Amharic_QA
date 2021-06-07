package classify;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Write_Each_Q_File {

	/**
	 * @param args
	 * This class read each line from the questions and create a file for each question in their respective class folder
	 * 
	 */
	public static void main(String[] args) throws IOException{
		System.out.println("ደህና");
		writeToFile();
	}
	public static void writeToFile() throws IOException{

		//
//		String defQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/definition.txt"), "utf-8");
//		String desQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/description.txt"), "utf-8");
//		String bioQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/who.txt"), "utf-8");
		String defQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/trainDef10.txt"), "utf-8");
		String desQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/trainDes10.txt"), "utf-8");
		String bioQ=FileUtils.readFileToString(new File("C:/NQA_Final/NQA_fin/Questions/n fold Test/trainB10.txt"), "utf-8");
		//String a=FileUtils.readFileToString(new File("Libraries/Documents/Doc1.txt"), "utf-16");
		//System.out.println(a);
		String s="",s1="",s2="";
		int f=1;
		Character_Normalization cn=new Character_Normalization();
		//System.out.println(defQ.length());
		for (int i = 0; i < defQ.length(); i++) {
			s+=defQ.charAt(i);
			if (defQ.charAt(i)=='\n')
			{
				//System.out.println(s);
				s=cn.Normalizedoc(s);
				FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Def/"+f+".txt"), s);
				s="";
				f++;
				continue;
			}
			
			//System.out.println("Ã¡â€¹Â±");
		}
		//f=f+1;
		for (int j = 0; j < desQ.length(); j++) {
			s1+=desQ.charAt(j);
			if (desQ.charAt(j)=='\n')
			{
				//System.out.println(s1);
				s1=cn.Normalizedoc(s1);
				FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Des/"+f+".txt"), s1);
				s1="";
				f++;
				continue;
			}
			
		}
		//f=f+1;
		for (int k = 0; k < bioQ.length(); k++) {
			s2+=bioQ.charAt(k);
			if (bioQ.charAt(k)=='\n')
			{
				//System.out.println(s2);
				s2=cn.Normalizedoc(s2);
				FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Bio/"+f+".txt"), s2);
				//System.out.println(s2);
				s2="";
				f++;
				continue;
			}
		}
	}

}
