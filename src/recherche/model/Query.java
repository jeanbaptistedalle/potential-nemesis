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

	public void fromString(String query) {
		StringTokenizer tokenizer = new StringTokenizer(query);
		String tmp = "";
		while (tokenizer.hasMoreElements()) {
			String token = (String) tokenizer.nextElement();
			if (token.toLowerCase().equals("and")
					|| token.toLowerCase().equals("or")
					|| token.toLowerCase().equals("not")) {
				operators.add(token.toLowerCase());
				subQueries.add(tmp);
				tmp = "";
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
