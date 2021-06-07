package classify; 

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import com.aliasi.classify.TfIdfClassifierTrainer;

public class Weight {

	/**
	 * @param args
	 */
	
	public static void main(String[] args)throws IOException {
		computeWeight();
		//System.out.println("Ã¡â€¹Â°Ã¡Ë†â€¦Ã¡Å â€œ Ã¡Å ï¿½Ã¡â€¹ï¿½");
		System.out.println("ደህና");
	}
	public static void computeWeight() throws IOException{
		File defDir=new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Def_TF/");
		File bioDir=new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Bio_TF/");
		File desDir=new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Des_TF/");
		//String termdocfreq="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/allDefVSBioTermDocFreq";
		String termdocfreq="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSbioTermDocFreq.txt";
		String termdocfreq1="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/defVSdesTermDocFreq.txt";
		String termdocfreq2="C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Term_Doc_Freq/desVSbioTermDocFreq.txt";
		StringBuilder sb= new StringBuilder();
		sb=assignewt(defDir, termdocfreq, 1);
		sb.append(assignewt(bioDir, termdocfreq, -1));
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdefVSbio.txt"), sb.toString());
		//def vs des
		StringBuilder sb1= new StringBuilder();
		sb1=assignewt1(defDir, termdocfreq1, 1);
		sb1.append(assignewt1(desDir, termdocfreq1, -1));
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdefVSdes.txt"), sb1.toString());
		//des vs bio
		StringBuilder sb2= new StringBuilder();
		sb2=assignewt2(desDir, termdocfreq2, 1);
		sb2.append(assignewt2(bioDir, termdocfreq2, -1));
		FileUtils.writeStringToFile(new File("C:/NQA_Final/NQA_fin/Questions/Term_Frequency/Weight/wdesVSbio.txt"), sb2.toString());
	}
	 public static StringBuilder assignewt(File dir, String Termfreq, int docs) throws IOException{
	        String voc=FileUtils.readFileToString(new File(Termfreq)); //a document with term-number of docu coocurence
			File[] files = dir.listFiles(); 
			//nofDefB=nofDefB+files.length;
			int nofDefB=900;
			
	                StringBuilder sb=new StringBuilder();  
	                int x = 0; double dd=0;
	                for(int i=0;i<files.length;i++){  
	                	x++;
	                	//System.out.println(x);
	                    sb.append(docs+" ");
	                    String terms=FileUtils.readFileToString(files[i].getAbsoluteFile());//one document
	                    StringTokenizer stterm=new StringTokenizer(terms); 
	                    while(stterm.hasMoreElements()){
	                        String term=stterm.nextToken();
	                        Integer odf=Integer.parseInt(stterm.nextToken().toString());//number of occurrences of a feature and documents
	                      StringTokenizer stvoc= new StringTokenizer(voc);  
	                      while(stvoc.hasMoreElements()){
	                          String voct=stvoc.nextToken();
	                          Integer termId=Integer.parseInt(stvoc.nextToken().toString());
	                          Integer termnf=Integer.parseInt(stvoc.nextToken().toString());
	                         if(term.equals(voct)){	                        	 
	                             //if(dir.getName().equals(def));
	                        	 dd=odf*(nofDefB/termnf);
	                        	 double tfidf= Math.log(dd);
	                             sb.append(termId+":"+tfidf+" ");
	                             //System.out.println(termId+ term+" odf: "+odf + "termnf:"+termnf + tfidf + dir.getName() );
	                             break;
	                         } 
	                      }
	                    }   
	                    sb.append("\n\n");
	                   //System.out.println(sb.toString());
	                }
	                //System.out.println("ndfnadj" +nofDefB);
	                return sb;                
	    }

	 public static StringBuilder assignewt1(File dir, String Termfreq, int docs) throws IOException{
	        String voc=FileUtils.readFileToString(new File(Termfreq)); //a document with term-number of docu coocurence
			File[] files = dir.listFiles();  
			//nofDefD=nofDefD+files.length;
			int nofDefD=900;
			
	                StringBuilder sb=new StringBuilder();  
	                int x = 0; double dd=0;
	                for(int i=0;i<files.length;i++){  
	                	x++;
	                	//System.out.println(x);
	                    sb.append(docs+" ");
	                    String terms=FileUtils.readFileToString(files[i].getAbsoluteFile());//one document
	                    StringTokenizer stterm=new StringTokenizer(terms); 
	                    while(stterm.hasMoreElements()){
	                        String term=stterm.nextToken();
	                        Integer odf=Integer.parseInt(stterm.nextToken().toString());//number of occurrences of a feature and documents
	                      StringTokenizer stvoc= new StringTokenizer(voc);  
	                      while(stvoc.hasMoreElements()){
	                          String voct=stvoc.nextToken();
	                          Integer termId=Integer.parseInt(stvoc.nextToken().toString());
	                          Integer termnf=Integer.parseInt(stvoc.nextToken().toString());
	                         if(term.equals(voct)){	                        	 
	                             //if(dir.getName().equals(def));
	                        	 dd=odf*(nofDefD/termnf);
	                        	 
	                        	 double tfidf= Math.log(dd);
	                        	 
	                        	 
	                             sb.append(termId+":"+tfidf+" ");
	                             //System.out.println(termId+ term+" odf: "+odf + "termnf:"+termnf + tfidf + dir.getName() );
	                             break;
	                         } 
	                      }
	                    }   
	                    sb.append("\n\n");
	                  //  System.out.println(sb.toString());
	                }
	                return sb;                
	    }

	 public static StringBuilder assignewt2(File dir, String Termfreq, int docs) throws IOException{
	        String voc=FileUtils.readFileToString(new File(Termfreq)); //a document with term-number of docu coocurence
			File[] files = dir.listFiles(); 
			//nofDesB=nofDesB+files.length;
			int nofDesB=900;
	                StringBuilder sb=new StringBuilder();  
	                int x = 0; double dd=0;
	                for(int i=0;i<files.length;i++){  
	                	x++;
	                	//System.out.println(x);
	                    sb.append(docs+" ");
	                    String terms=FileUtils.readFileToString(files[i].getAbsoluteFile());//one document
	                    StringTokenizer stterm=new StringTokenizer(terms); 
	                    while(stterm.hasMoreElements()){
	                        String term=stterm.nextToken();
	                        Integer odf=Integer.parseInt(stterm.nextToken().toString());//number of occurrences of a feature and documents
	                      StringTokenizer stvoc= new StringTokenizer(voc);  
	                      while(stvoc.hasMoreElements()){
	                          String voct=stvoc.nextToken();
	                          Integer termId=Integer.parseInt(stvoc.nextToken().toString());
	                          Integer termnf=Integer.parseInt(stvoc.nextToken().toString());
	                         if(term.equals(voct)){	                        	 
	                             //if(dir.getName().equals(def));
	                        	 dd=odf*(nofDesB/termnf);
	                        	 double tfidf= Math.log(dd);
	                             sb.append(termId+":"+tfidf+" ");
	                             //System.out.println(termId+ term+" odf: "+odf + "termnf:"+termnf + tfidf + dir.getName() );
	                             break;
	                         } 
	                      }
	                    }   
	                    sb.append("\n\n");
	                  //  System.out.println(sb.toString());
	                }
	                return sb;                
	    }

}
