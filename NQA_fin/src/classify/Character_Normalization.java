package classify;


public class Character_Normalization {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String q="ፀሐይ: ለዐዕፅዋት: እድገት ድርሠሻዋ /ምንድነው";
		Character_Normalization cn=new Character_Normalization();
		System.out.println(cn.Normalizedoc(q));
		
	}
	public String Normalizedoc(String doc) {
		doc = doc.replace("ሃ", "ሀ");
		doc = doc.replace("ሐ", "ሀ");
		doc = doc.replace("ሓ", "ሀ");
		doc = doc.replace("ኅ", "ሀ");
		doc = doc.replace("ኃ", "ሀ");
		doc = doc.replace("ኋ", "ኋ");
		doc = doc.replace("ሗ", "ኋ");
		doc = doc.replace("ኁ", "ሁ");
		doc = doc.replace("ኂ", "ሂ");
		doc = doc.replace("ኄ", "ሄ");
		doc = doc.replace("ኅ", "ህ");
		doc = doc.replace("ኆ", "ሆ");
		doc = doc.replace("ሑ", "ሁ");
		doc = doc.replace("ሒ", "ሂ");
		doc = doc.replace("ሔ", "ሄ");
		doc = doc.replace("ሕ", "ህ");
		doc = doc.replace("ሖ", "ሆ");
		doc = doc.replace("ሠ", "ሰ");
		doc = doc.replace("ሡ", "ሱ");
		doc = doc.replace("ሢ", "ሲ");
		doc = doc.replace("ሣ", "ሳ");
		doc = doc.replace("ሤ", "ሴ");
		doc = doc.replace("ሥ", "ስ");
		doc = doc.replace("ሦ", "ሶ");
		doc = doc.replace("ሼ", "ሸ");
		doc = doc.replace("ቼ", "ቸ");
		doc = doc.replace("ዬ", "የ");
		doc = doc.replace("ዲ", "ድ");
		doc = doc.replace("ጄ", "ጀ");
		doc = doc.replace("ጸ", "ፀ");
		doc = doc.replace("ጹ", "ፁ");
		doc = doc.replace("ጺ", "ፂ");
		doc = doc.replace("ጻ", "ፃ");
		doc = doc.replace("ጼ", "ፄ");
		doc = doc.replace("ጽ", "ፅ");
		doc = doc.replace("ጾ", "ፆ");
		doc = doc.replace("ዉ", "ው");
		doc = doc.replace("ዴ", "ደ");
		doc = doc.replace("ቺ", "ች");
		doc = doc.replace("ዪ", "ይ");
		doc = doc.replace("ጪ", "ጭ");
		doc = doc.replace("ዓ", "አ");
		doc = doc.replace("ዑ", "ኡ");
		doc = doc.replace("ዒ", "ኢ");
		doc = doc.replace("ዐ", "አ");
		doc = doc.replace("ኣ", "አ");
		doc = doc.replace("ዔ", "ኤ");
		doc = doc.replace("ዕ", "እ");
		doc = doc.replace("ዖ", "ኦ");
		doc = doc.replace("ኚ", "ኝ");
		doc = doc.replace("ሺ", "ሽ");
		doc=doc.replace("?", "");
		doc=doc.replace("?", "");
//		doc = doc.replace(":", " ");
		doc = doc.replace("፣", "");
//		doc=doc.replace("።"," ");
//		doc=doc.replace("፡፡"," ");
		doc=doc.replace("-"," ");
//		doc=doc.replace(")"," ");
		doc=doc.replace(",","");
//		doc=doc.replace("(","");
		doc=doc.replace("፤","");
		doc=doc.replace(";","");
		doc=doc.replace("፣","");
		doc=doc.replace("#","");
		doc=doc.replace("$","");
		doc=doc.replace("'","");
		//doc=doc.replace("/","");
//		doc=doc.replace("\"","");
		return doc;
	}

}
