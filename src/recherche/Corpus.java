package recherche;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;	
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Corpus {
	/*
	 * Dans cette map, chaque texte est associé à une clef unique.
	 */
	private Map<Integer, String> texts;

	/*
	 * Dans cette seconde map, chaque mot est associé à une liste dans laquelle
	 * se trouve l'id de tous les textes dans lesquels il apparait.
	 */
	private Map<String, List<Integer>> corpus;

	public Corpus(List<String> listTexts) {
		this.texts = new HashMap<Integer, String>();
		this.corpus = new HashMap<String, List<Integer>>();
		for (int i = 0; i < listTexts.size(); i++) {
			final String text = listTexts.get(i);
			texts.put(i, text);
			final StringTokenizer st = new StringTokenizer(text);
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (corpus.containsKey(token)) {
					final List<Integer> listIdText = corpus.get(token);
					listIdText.add(i);
					Collections.sort(listIdText);
				} else {
					final List<Integer> listIdText = new ArrayList<Integer>();
					listIdText.add(i);
					corpus.put(token, listIdText);
				}

			}

		}
	}

	public Map<Integer, String> getTexts() {
		return texts;
	}

	public void setTexts(Map<Integer, String> texts) {
		this.texts = texts;
	}

	public Map<String, List<Integer>> getCorpus() {
		return corpus;
	}

	public void setCorpus(Map<String, List<Integer>> corpus) {
		this.corpus = corpus;
	}

	public String toString() {
		return texts + "\n" + corpus;
	}

	public String executeQuery(final String query) {
		//TODO
		return null;
	}
}
