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
			while(line != null){
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
	
	public String filter(final String text){
		StringBuilder stringBuilder = new StringBuilder();
		StringTokenizer st = new StringTokenizer(text);
		while(st.hasMoreElements()){
			String token = st.nextToken();
			if(!contains(token)){
				stringBuilder.append(token);
			}
		}
		return stringBuilder.toString();
	}
	
	public String toString(){
		return stopWords.toString();
	}
}
