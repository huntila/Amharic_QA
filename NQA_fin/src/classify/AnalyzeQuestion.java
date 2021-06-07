package classify;

public class AnalyzeQuestion {
public  String QType = null;

	public  String[] DefQuestinParticles = {"ምን", "ማለት","ነው", "ምንድነው","ምንድንናት","ምንድንናቸው","ምንድን ነው","ምንድን ናት","ምንድን ናቸው" };
	
	
	public  String[] BioQuestionParticles = { "ማነው","ማናት", "ማናቸው","ማን ነው","ማን ናት","ማን ናቸው"};
	
	public  String[] DesQuestionParticles = { "ጥቅም", "ፋይዳ", "አገልግሎት", "ሚና", "ተግባር","ጥቅሟ","ፋይዳዋ", "አገልግሎቷ", "ሚናዋ", "ድርሻዋ", "ተግባሯ",
												"ጥቅማቸው", "ፋይዳቸው", "አገልግሎታቸው", "ሚናቸው", "ድርሻቸው", "ተግባራቸው"};
	//public  String[] DesQuestionParticles = { "ጥቅም", "ፋይዳ", "አገልግሎት", "ሚና", "ተግባር","ድርሻ"};
	
	public  String[] DefQuestionTypes = { "ትርጉም","ፍቺ","ትርጉሙ","ፍቺው"};
	
	public  String[] DesQuestionTypes = {"ለምን ይጠቅማሉ","ለምን ትጠቅማለች","ለምን ይጠቅማል","ምን ጥቅም አለው","ምን ጥቅም አላቸው","ምን ጥቅም አላት","ጥቅሙ ምን እንደሆነ አብራራ","ጥቅሙ ምን እንደሆነ አብራሪ","ጥቅሙ ምን እንደሆነ አብራሩ","ጥቅሙ ምን እንደሆነ ግለፅ", "ጥቅሙ ምን እንደሆነ ግለጭ", "ጥቅሙ ምን እንደሆነ ግለፁ",
				"ፋይዳው ምን እንደሆነ አብራራ","ፋይዳው ምን እንደሆነ አብራሪ","ፋይዳው ምን እንደሆነ አብራሩ","ፋይዳው ምን እንደሆነ ግለፅ", "ፋይዳው ምን እንደሆነ ግለጭ", "ፋይዳው ምን እንደሆነ ግለፁ",
				"አገልግሎቱ ምን እንደሆነ አብራራ","አገልግሎቱ ምን እንደሆነ አብራሪ","አገልግሎቱ ምን እንደሆነ አብራሩ","አገልግሎቱ ምን እንደሆነ ግለፅ", "አገልግሎቱ ምን እንደሆነ ግለጭ", "አገልግሎቱ ምን እንደሆነ ግለፁ",
				"ሚናው ምን እንደሆነ አብራራ","ሚናው ምን እንደሆነ አብራሪ","ሚናው ምን እንደሆነ አብራሩ","ሚናው ምን እንደሆነ ግለፅ", "ሚናው ምን እንደሆነ ግለጭ", "ሚናው ምን እንደሆነ ግለፁ",
				"ተግባሩ ምን እንደሆነ አብራራ","ተግባሩ ምን እንደሆነ አብራሪ","ተግባሩ ምን እንደሆነ አብራሩ","ተግባሩ ምን እንደሆነ ግለፅ", "ተግባሩ ምን እንደሆነ ግለጭ", "ተግባሩ ምን እንደሆነ ግለፁ",
				"ድርሻው ምን እንደሆነ አብራራ","ድርሻው ምን እንደሆነ አብራሪ","ድርሻው ምን እንደሆነ አብራሩ","ድርሻው ምን እንደሆነ ግለፅ", "ድርሻው ምን እንደሆነ ግለጭ", "ድርሻው ምን እንደሆነ ግለፁ"};

	public String AnalyzedQuery(String q) {
		while (true) {
			int flag=0;
			if(q.contains("ማለት ነው"))
				{QType="Definition";
				flag=1;
				return QType;}
			
			// check if the question seeks Biography
			for (int i = 0; i < BioQuestionParticles.length; i++) {
				if (q.contains(BioQuestionParticles[i])) {
					QType = "Biography";
					flag=1;
					break;
					}
			}
			// check if the question seeks Description
			for (int i = 0; i < DesQuestionParticles.length; i++) {
				if (q.contains(DesQuestionParticles[i])) {
					QType = "Description";
					flag=1;
					break;
				}
			}
			// check if the question seeks for definition
			for (int i = 0; i < DefQuestionTypes.length; i++) {
				if (q.contains(DefQuestionTypes[i])) {
					QType = "Definition";
					flag=1;
					break;
				}
			}
			// check if the question is description
			for (int i = 0; i < DesQuestionTypes.length; i++) {
				//String x=DesQuestionTypes[i];
				if (q.contains(DesQuestionTypes[i])) {
					QType = "Description";
					flag=1;
					break;
				}
			}
			if(flag==0&&(q.contains("ምንድነው")||q.contains("ምንድንነው")||q.contains("ምንድንናት")||q.contains("ምንድንናቸው")||q.contains("ምንድን ነው")||q.contains("ምንድን ናት")||q.contains("ምንድን ናቸው")))
				QType="Definition";
			else if(flag==0&&!q.contains("ምንድነው")&&!q.contains("ምንድንነው"))
				QType="Unknown";
			
			return QType;
		}
		
	}

	public static void main(String[] args) {
		AnalyzeQuestion qtype = new AnalyzeQuestion();
		//String Question = "መኮንን ፈንታው ስንት ልጅ ወለደ?";
		//String Question="የኢትዮጵያ ፕሬዚዳንት ማን ይባላሉ";
		//String Question="የኢትዮጵያ ኦሎምፒክ ኮሚቴ መቼ ተመሰረተ";
		//String Question="በዓለም ትልቁ ተራራ የት ይገኛል";
		//String Question="ደን ማለት ምን ማለት ነው";
		//String Question="በኢትዮጵያ ውስጥ ጤና ምንድነው";
		//String Question="ሕገ መንግስት ምንድነው";
		//String Question="አርቲስት መሀሙድ አህመድ ማነው";
		//String Question="አስቴር አወቀ ማናት";
		//String Question="ሉሲዎች ማናቸው";
		String Question="የውሃ ጥቅም ምንድነው";
		//String Question="ፀሐይ ለእፅዋት እድገት ድርሻዋ ምንድነው";
//String Question="የሕይወት ትርጉሙ ምንድነው";
		//String Question="ክትባት ምንድነው";
		//String Question="የትምህርት አገልግሎቱ ምን እንደሆነ አብራራ";
//";
		System.out.println(qtype.AnalyzedQuery(Question));
		QueryGenerator qg = new QueryGenerator();
		String []result=qg.generateQuery(Question);
		System.out.println("QT: " + result[0]+ " and sex: "+ result[1]);
		//System.out.println(qg.generateQuery(Question));
	}

}
