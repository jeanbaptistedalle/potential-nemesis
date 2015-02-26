package recherche.model;

import java.util.HashMap;
import java.util.Map;

public class Solution {
	
	private Map<String, Integer> solutions;
	
	public Solution() {
		this.solutions = new HashMap<String, Integer>();
	}

	public Map<String, Integer> getSolutions() {
		return solutions;
	}

	public void setSolutions(Map<String, Integer> solutions) {
		this.solutions = solutions;
	}
	
	public void add(String s, Integer value)
	{
		if(this.solutions.containsKey(s))
		{
			solutions.put(s, solutions.get(s)+value);
		}
		else
			solutions.put(s, value);
	}

	public Solution retainAll(Solution tmp) {
		Solution s = new Solution();
		Map<String, Integer> tmpSol = tmp.getSolutions();
		
		for (String key : this.solutions.keySet()) {
			if (tmpSol.containsKey(key))
			{
				s.add(key,tmpSol.get(key));
			}
		}
		return s;
		
	}

	public Solution diff(Solution tmp) {
		Solution s = new Solution();
		Map<String, Integer> tmpSol = tmp.getSolutions();
		
		for (String key : this.solutions.keySet()) {
			if (!tmpSol.containsKey(key))
			{
				s.add(key,this.solutions.get(key));
			}
		}
		return s;
		
	}
	
	@Override
	public String toString() {
		return "Solution [solutions=" + solutions + "]";
	}

	public Solution union(Solution tmp)
	{
		Solution s = new Solution();
		
		for (String key : this.solutions.keySet()) {
			s.add(key,this.solutions.get(key));
		}
		
		Map<String, Integer> second = tmp.getSolutions();
		
		for (String key : second.keySet())
		{
			s.add(key, second.get(key));
		}
		
		return s;
		
	}

}
