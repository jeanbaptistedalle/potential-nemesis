package recherche.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchEngine {

	private static SearchEngine INSTANCE;
//	private static final String QUERY_TEST = "Document will discuss allegations, or measures being taken"
//			+ " against, corrupt public officials of any governmental jurisdiction worldwide. ";
	private static final String QUERY_TEST = "darling not rolling";

	private StopWord stopWord;
	private DocParser docParser;
	private Stemmer stemmer;
	private Corpus corpus;

	private SearchEngine(final boolean test) {
		stopWord = new StopWord();
		stopWord.start();
		docParser = new DocParser();
		stemmer = new Stemmer();
		final List<Text> textsBruts = docParser.getDefaultTexts(true);
		corpus = new Corpus(stopWord);
		corpus.start(textsBruts);
	}

	private SearchEngine() {
		this(false);
	}

	public List<String> executeQuery(final String query) {
		final String querySansPonctuation = stopWord.deleteSpecialChar(query);
		final String queryLowerCase = querySansPonctuation.toLowerCase();
		final Query queryObject = new Query();
		queryObject.fromString(queryLowerCase);

		final List<List<String>> filePathsList = new ArrayList<List<String>>();

		for (final String elt : queryObject.getSubQueries()) {
			final String queryFiltre = stopWord.filter(elt);
			final String queryStem = stemmer.stemText(queryFiltre);
			filePathsList.add(corpus.executeQuery(queryStem));
		}

		ArrayList<String> operators = queryObject.getOperators();

		Collections.reverse(filePathsList);
		Collections.reverse(operators);

		for (int i = operators.size() - 1; i >= 0; i--) {
			List<String> first = filePathsList.get(i + 1);
			List<String> second = filePathsList.get(i);
			filePathsList.remove(i + 1);
			filePathsList.remove(i);

			if (operators.get(i).equals("and")) {
				List<String> list = new ArrayList<String>();

				for (String t : first) {
					if (second.contains(t)) {
						list.add(t);
					}
				}
				filePathsList.add(list);

			} else if (operators.get(i).equals("or")) {
				Set<String> set = new HashSet<String>();

				set.addAll(first);
				set.addAll(second);

				filePathsList.add(new ArrayList<String>(set));

			} else if (operators.get(i).equals("not")) {
				List<String> list = new ArrayList<String>();

				for (String t : first) {
					if (!second.contains(t)) {
						list.add(t);
					}
				}
				filePathsList.add(list);
			}

		}
		
		if(filePathsList.size() == 0)
			return new ArrayList<String>();
		List<String> ret = filePathsList.get(0);
		return ret;
	}

	public List<Text> getFilesFromFilePaths(final List<String> listFilePath) {
		return docParser.getTexts(listFilePath);
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

	public static SearchEngine getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SearchEngine();
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		final SearchEngine searchEngine = SearchEngine.getInstance();
		List<String> out = searchEngine.executeQuery(QUERY_TEST);
		System.out.println(out);
	}
}
