package recherche;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StopWord {

	private final static String STOP_WORDS_PATH = "stopwords.txt";

	private List<String> stopWords;

	public StopWord() {
		try {
			stopWords = new ArrayList<String>();
			final BufferedReader f = new BufferedReader(new FileReader(STOP_WORDS_PATH));
			String line = f.readLine();
			while (line != null) {
				stopWords.add(line);
				line = f.readLine();
			}
			f.close();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public StopWord(final List<String> stopWords) {
		this.stopWords = stopWords;
	}

	public boolean contains(final String word) {
		return stopWords.contains(word);
	}
	
	public List<String> filterTexts(final List<String> texts){
		final List<String> returnList = new ArrayList<String>();
		for(String text : texts){
			returnList.add(filter(text));
		}
		return returnList;
	}
	
	private String filter(final String text) {
		final StringBuilder stringBuilder = new StringBuilder();
		String tempText = new String(text);
		tempText = tempText.replaceAll("[^a-zA-Z0-9 ]", "");
		final StringTokenizer st = new StringTokenizer(tempText);
		boolean first = true;
		while (st.hasMoreElements()) {
			String token = st.nextToken();
			if (!contains(token)) {
				if (!first) {
					stringBuilder.append(" ");
				}
				stringBuilder.append(token);
				if (first) {
					first = false;
				}
			}
		}
		return stringBuilder.toString();
	}

	public String toString() {
		return stopWords.toString();
	}
}
