package classify;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class Sort_Weight {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		System.out.println("ደህና");
		sWeigth();
		
		System.out.println("sorted successfully");
		
	}
	public static void sWeigth() throws IOException{
		String weightPath="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdefVSbio.txt";
		String weightPath1="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdefVSdes.txt";
		String weightPath2="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdesVSbio.txt";
		sortWeight(weightPath,"C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdefVSbio.txt");
		sortWeight1(weightPath1);
		sortWeight2(weightPath2);
	}
	public static void sortWeight(String weightfile,String write_to_file) throws IOException{
		//Set wt= new HashSet();
		String wtfl=FileUtils.readFileToString(new File(weightfile));
		//System.out.println(wtfl);
		StringTokenizer st=new StringTokenizer(wtfl,"\n");
		StringBuilder sb=new StringBuilder();
		//int line=0;
		//DecimalFormat df = new DecimalFormat("#.######");
		while(st.hasMoreElements())	{
			//System.out.println(line++);
		//	if(line==4) break;
			String oneline=st.nextToken();
			if(!oneline.isEmpty()){
				Integer cls=Integer.parseInt(oneline.substring(0,2).trim());//class of the document
				sb.append(cls);
				oneline=oneline.substring(2, oneline.length());
				StringTokenizer stln=new StringTokenizer(oneline," ");
				Map<Integer,String> feature=new TreeMap<Integer,String>();
				int wordcount=0;
				StringTokenizer stlnw=stln;
				//double norm=0.0;
				//while(stlnw.hasMoreElements())
				//{
					//String val =stln.nextToken();
					//Integer ft =Integer.parseInt(val.substring(0, val.indexOf(":")));
					 //Double wet=Double.parseDouble((val.substring(val.indexOf(":")+1, val.length())));
					 //norm=norm+wet*wet;//w^2 and summation
				//}
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

		FileUtils.writeStringToFile(new File(write_to_file), sb.toString());
		
	}
	public static void sortWeight1(String weightfile) throws IOException{
		//Set wt= new HashSet();
		String wtfl=FileUtils.readFileToString(new File(weightfile));
		StringTokenizer st=new StringTokenizer(wtfl,"\n");
		StringBuilder sb=new StringBuilder();
		//int line=0;
		//DecimalFormat df = new DecimalFormat("#.######");
		while(st.hasMoreElements())	{
			//System.out.println(line++);
		//	if(line==4) break;
			String oneline=st.nextToken();
			if(!oneline.isEmpty()){
				Integer cls=Integer.parseInt(oneline.substring(0, 2).trim());//class of the document
				sb.append(cls);
				oneline=oneline.substring(2, oneline.length());
				StringTokenizer stln=new StringTokenizer(oneline," ");
				Map<Integer,String> feature=new TreeMap<Integer,String>();
				int wordcount=0;
				StringTokenizer stlnw=stln;
				//double norm=0.0;
//				while(stlnw.hasMoreElements())
//				{
					//String val =stln.nextToken();
					//Integer ft=Integer.parseInt(val.substring(0, val.indexOf(":")));
					// Double wet=Double.parseDouble((val.substring(val.indexOf(":")+1, val.length())));
					// norm=norm+wet*wet;//w^2 and summation
				//}
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

		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdefVSdes.txt"), sb.toString());
		
	}
	public static void sortWeight2(String weightfile) throws IOException{
		//Set wt= new HashSet();
		String wtfl=FileUtils.readFileToString(new File(weightfile));
		StringTokenizer st=new StringTokenizer(wtfl,"\n");
		StringBuilder sb=new StringBuilder();
		//int line=0;
		//DecimalFormat df = new DecimalFormat("#.######");
		while(st.hasMoreElements())	{
			//System.out.println(line++);
		//	if(line==4) break;
			String oneline=st.nextToken();
			if(!oneline.isEmpty()){
				Integer cls=Integer.parseInt(oneline.substring(0, 2).trim());//class of the document
				sb.append(cls);
				oneline=oneline.substring(2, oneline.length());
				StringTokenizer stln=new StringTokenizer(oneline," ");
				Map<Integer,String> feature=new TreeMap<Integer,String>();
				int wordcount=0;
				StringTokenizer stlnw=stln;
				//double norm=0.0;
//				while(stlnw.hasMoreElements())
//				{
					//String val =stln.nextToken();
					//Integer ft=Integer.parseInt(val.substring(0, val.indexOf(":")));
					 //Double wet=Double.parseDouble((val.substring(val.indexOf(":")+1, val.length())));
					 //norm=norm+wet*wet;//w^2 and summation
				//}
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

		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/SortedWdesVSbio.txt"), sb.toString());
		
	}
}
