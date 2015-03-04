package recherche.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class SearchEngine {

	private static SearchEngine INSTANCE;
	// private static final String QUERY_TEST =
	// "Document will discuss allegations, or measures being taken"
	// +
	// " against, corrupt public officials of any governmental jurisdiction worldwide. ";
	private static final String QUERY_TEST = "darling not rolling";

	private StopWord stopWord;
	private DocParser docParser;
	private Stemmer stemmer;
	private Corpus corpus;
	private boolean DefText;

	private SearchEngine(final boolean test) {
		stopWord = new StopWord();
		stopWord.start();
		docParser = new DocParser();
		stemmer = new Stemmer();
		DefText = test;
		corpus = new Corpus(stopWord);
		getIndex();
	}

	private SearchEngine() {
		this(false);
	}

	public void getIndex() {
		long timeStart = System.currentTimeMillis();
		try {
			if (new File("corpus.ser").exists()) {
				InputStream file = new FileInputStream("corpus.ser");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream(buffer);
				corpus.setCorpus((Map<String, List<DocPosition>>) input.readObject());
				input.close();
				long timeEnd = System.currentTimeMillis();
				long elapsedTime = timeEnd - timeStart;
				System.out.println("Indexation récupéré en " + elapsedTime + " ms");
			} else {
				index();
				long timeEnd = System.currentTimeMillis();
				long elapsedTime = timeEnd - timeStart;
				System.out.println("Indexation récupéré en " + elapsedTime + " ms");
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void index() {
		final List<Text> textsBruts = docParser.getDefaultTexts(DefText);
		corpus.start(textsBruts);
		try {
			OutputStream file = new FileOutputStream("corpus.ser");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(corpus.getCorpus());
			output.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public Solution executeQuery(final String query) {
		final String querySansPonctuation = stopWord.deleteSpecialCharForQuery(query);
		final String queryLowerCase = querySansPonctuation.toLowerCase();
		final Query queryObject = new Query();
		queryObject.fromString(queryLowerCase);

		final List<Solution> filePathsList = new ArrayList<Solution>();
		final List<String> wordsOfQuery = new ArrayList<String>();
		
		for (final String elt : queryObject.getSubQueries()) {
			final String queryFiltre = stopWord.filter(elt);
			final String queryStem = stemmer.stemText(queryFiltre);
			filePathsList.add(corpus.executeQuery(queryStem));
			StringTokenizer stk = new StringTokenizer(queryStem);
			while (stk.hasMoreTokens()) {
				String s = (String) stk.nextElement();
				wordsOfQuery.add(s);
			}
		}

		ArrayList<String> operators = queryObject.getOperators();

		Collections.reverse(filePathsList);
		Collections.reverse(operators);

		for (int i = operators.size() - 1; i >= 0; i--) {
			Solution first = filePathsList.get(i + 1);
			Solution second = filePathsList.get(i);
			filePathsList.remove(i + 1);
			filePathsList.remove(i);

			if (operators.get(i).equals("and")) {
				filePathsList.add(first.retainAll(second));
			} else if (operators.get(i).equals("or")) {
				filePathsList.add(first.union(second));
			} else if (operators.get(i).equals("not")) {
				filePathsList.add(first.diff(second));
			}

		}

		Solution ret = new Solution();
		ret.setWordsOfQuery(wordsOfQuery);
		
		if (filePathsList.size() == 0)
		{
			return ret;
		}
		
		ret = filePathsList.get(0);
		ret.setWordsOfQuery(wordsOfQuery);
		
		return ret;
	}

	public List<Text> getFilesFromFilePaths(final Map<String, List<Integer>> map) {
		return docParser.getTexts(map);
	}

	public String toString() {
		return corpus.toString();
	}

	public StopWord getStopWord() {
		return stopWord;
	}

	public void setStopWord(StopWord stopWord) {
		this.stopWord = stopWord;
	}

	public DocParser getDocParser() {
		return docParser;
	}

	public void setDocParser(DocParser docParser) {
		this.docParser = docParser;
	}

	public Stemmer getStemmer() {
		return stemmer;
	}

	public void setStemmer(Stemmer stemmer) {
		this.stemmer = stemmer;
	}

	public Corpus getCorpus() {
		return corpus;
	}

	public void setCorpus(Corpus corpus) {
		this.corpus = corpus;
	}

	public boolean isDefText() {
		return DefText;
	}

	public void setDefText(boolean defText) {
		DefText = defText;
	}

	public static SearchEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SearchEngine();
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		final SearchEngine searchEngine = SearchEngine.getInstance();
		Solution out = searchEngine.executeQuery(QUERY_TEST);
		System.out.println(out);
	}
}
