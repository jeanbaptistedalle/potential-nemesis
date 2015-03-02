package recherche.model;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Query {

	private ArrayList<String> subQueries;
	private ArrayList<String> operators;

	public Query() {
		subQueries = new ArrayList<String>();
		operators = new ArrayList<String>();
	}
	
	private String trimOperators(String q)
	{
		StringTokenizer tokenizer = new StringTokenizer(q);
		
		String query = "";
		
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			if(!(token.equals("and") || token.equals("or") || token.equals("not")))
			{
				query = token;
				break;
			}
		}
		
		while(tokenizer.hasMoreTokens())
		{
			query = tokenizer.nextToken() + " " + query;
		}

		StringTokenizer tokenizer2 = new StringTokenizer(query);
		
		String finalQuery = "";
		
		while(tokenizer2.hasMoreTokens())
		{
			String token = tokenizer2.nextToken();
			if(!(token.equals("and") || token.equals("or") || token.equals("not")))
			{
				finalQuery = token;
				break;
			}
		}
		
		while(tokenizer2.hasMoreTokens())
		{
			finalQuery = tokenizer2.nextToken() + " " + finalQuery;
		}
		
		return finalQuery;
		
	}

	public void fromString(String query) {
		query = trimOperators(query);
		StringTokenizer tokenizer = new StringTokenizer(query);
		String tmp = "";
		while (tokenizer.hasMoreElements()) {
			String token = (String) tokenizer.nextElement();
			if (token.toLowerCase().equals("and")
					|| token.toLowerCase().equals("or")
					|| token.toLowerCase().equals("not")) {
				
				if(!tmp.equals(""))
				{
					operators.add(token.toLowerCase());
					subQueries.add(tmp);
					tmp = "";
				}
				
			}
			else
			{
				tmp += " " + token.toLowerCase();
			}
		}
		if(!tmp.equals(""))
			subQueries.add(tmp);
	}

	public ArrayList<String> getSubQueries() {
		return subQueries;
	}

	public void setSubQueries(ArrayList<String> subQueries) {
		this.subQueries = subQueries;
	}

	public ArrayList<String> getOperators() {
		return operators;
	}

	public void setOperators(ArrayList<String> operators) {
		this.operators = operators;
	}
	
	public String toString()
	{
		return "" + subQueries + "\n" + operators;
	}

}
