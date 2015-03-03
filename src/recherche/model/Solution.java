package recherche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Solution {

	private Map<String, List<Integer>> solutions;
	private List<String> wordsOfQuery;
	private Map<String, Double> cosDoc;

	public Solution() {
		this.solutions = new HashMap<String, List<Integer>>();
		this.wordsOfQuery = new ArrayList<String>();
		this.cosDoc = new HashMap<String, Double>();
	}

	public List<String> getWordsOfQuery() {
		return wordsOfQuery;
	}

	public void setWordsOfQuery(List<String> wordsOfQuery) {
		this.wordsOfQuery = wordsOfQuery;
	}

	public Map<String, List<Integer>> getSolutions() {
		return solutions;
	}

	public Map<String, List<Integer>> getSortedSolutions() {
		ValueComparator vc = new ValueComparator(solutions, wordsOfQuery);
		vc.setOut(this);

		Map<String, List<Integer>> sol = new TreeMap<String, List<Integer>>(vc);

		sol.putAll(solutions);

		return sol;

	}

	public Map<String, Double> getCosDoc() {
		return cosDoc;
	}

	public void setCosDoc(Map<String, Double> cosDoc) {
		this.cosDoc = cosDoc;
	}

	public void setSolutions(Map<String, List<Integer>> solutions) {
		this.solutions = solutions;
	}

	public void add(String s, List<Integer> value) {
		if (this.solutions.containsKey(s)) {
			solutions.get(s).addAll(value);
		} else {
			final List<Integer> list = new ArrayList<Integer>(value);
			solutions.put(s, list);
		}
	}

	public Solution retainAll(Solution tmp) {
		Solution s = new Solution();
		Map<String, List<Integer>> tmpSol = tmp.getSolutions();

		for (String key : this.solutions.keySet()) {
			if (tmpSol.containsKey(key)) {
				s.add(key, tmpSol.get(key));
			}
		}
		return s;

	}

	public Solution diff(Solution tmp) {
		Solution s = new Solution();
		Map<String, List<Integer>> tmpSol = tmp.getSolutions();

		for (String key : this.solutions.keySet()) {
			if (!tmpSol.containsKey(key)) {
				s.add(key, this.solutions.get(key));
			}
		}
		return s;

	}

	@Override
	public String toString() {
		return "Solution [solutions=" + solutions + "]";
	}

	public Solution union(Solution tmp) {
		Solution s = new Solution();

		for (String key : this.solutions.keySet()) {
			s.add(key, this.solutions.get(key));
		}

		Map<String, List<Integer>> second = tmp.getSolutions();

		for (String key : second.keySet()) {
			s.add(key, second.get(key));
		}

		return s;

	}

}
