package classify;

public class QueryGenerator {
	//public static AnalyzeQuestion anq = new AnalyzeQuestion();
	public static String QT="";
	public String[] generateQuery(String q) {
		String []result={"",""};
		String sex="";
		
		if( q.contains(" ማነው")||q.contains(" ማን ነው"))
				sex="Male";
		else if(q.contains(" ማናት")||q.contains(" ማን ናት"))
			sex="Female";
		else if(q.contains(" ማናቸው")||q.contains("  ማን ናቸው"))
			sex="Both";
		else
			sex="Unknown";
		
		while (true)
		{		
		if(q.contains("ምን እንደሆነ")||q.contains("ለምን ይጠቅማሉ")||q.contains("ለምን ትጠቅማለች")||q.contains("ለምን ይጠቅማል")||q.contains("ምን ጥቅም አለው")||q.contains("ምን ጥቅም አላቸው")||q.contains("ምን ጥቅም አላት")||q.contains("ምን ተግባር አላቸው"))
				{
				q=q.replace("ምን እንደሆነ", "");
				q=q.replace("ለምን ይጠቅማሉ", "");
				q=q.replace("ለምን ትጠቅማለች", "");
				q=q.replace("ለምን ይጠቅማል", "");
				q=q.replace("ምን ጥቅም አለው", "");
				q=q.replace("ምን ጥቅም አላቸው", "");
				q=q.replace("ምን ጥቅም አላት", "");
				q=q.replace("ምን ተግባር አላቸው", "");
				}
		if( q.contains(" ማነው")||q.contains(" ማናት")||q.contains(" ማናቸው")||q.contains(" ማን ነው")||q.contains(" ማን ናት")||q.contains(" ማን ናቸው"))
		{
			q=q.replace(" ማነው", "");
			q=q.replace(" ማናት", "");
			q=q.replace(" ማናቸው", "");
			q=q.replace(" ማን ነው", "");
			q=q.replace(" ማን ናት", "");
			q=q.replace(" ማን ናቸው", "");
		}
		if (q.contains("ምን ማለት ነው")||q.contains("ምንድነው")|| q.contains("ምንድንነው")||q.contains("ምንድንናት")||q.contains("ምንድንናቸው")|| q.contains("ምንድን ነው")||q.contains("ምንድን ናት")||q.contains("ምንድን ናቸው"))
		{
			q=q.replace("ምን ማለት ነው", "");
			q=q.replace("ምንድነው", "");
			q=q.replace("ምንድንነው", "");
			q=q.replace("ምንድንናት", "");
			q=q.replace("ምንድንናቸው", "");
			q=q.replace("ምንድን ነው", "");
			q=q.replace("ምንድን ናት", "");
			q=q.replace("ምንድን ናቸው", "");
		}
		if(q.contains("ማለት "))
		{
			q=q.replace(" ማለት ", "");
		}
//		if(q.contains("እንደሆነ"))
//		{
//			q.replace("እንደሆነ ", " ");
//		}
		if( q.contains("?"))
		q=q.replace("?","");
		q=q.trim();
		result[0]=q;
		result[1]=sex;
		return result;
		}
	}

	public static void main(String[] args) {
		//AnalyzeQuestion anq = new AnalyzeQuestion();
		QueryGenerator qg = new QueryGenerator();
		//String quetion = "ጠቅላይ ሚኒስትር መሆን የሚቻለው ከስንት አመት በላይ ነው";
		String question ="አርቲስት መሀሙድ አህመድ ማናቸው";
		//anq.AnalyzedQuery(quetion);
		//QT=anq.QType;
		//QT=anq.AnalyzedQuery(quetion);
		String []result=qg.generateQuery(question);
		System.out.println("QT: " + result[0]+ " and sex: "+ result[1]);
	}

}
