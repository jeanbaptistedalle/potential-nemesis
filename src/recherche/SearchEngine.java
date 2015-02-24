package recherche;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

	private static final String QUERY_TEST = "Document will discuss allegations, or measures being taken"
			+ " against, corrupt public officials of any governmental jurisdiction worldwide. ";

	private StopWord stopWord;
	private DocParser docParser;
	private Stemmer stemmer;
	private Corpus corpus;
	private List<String> texts;

	public SearchEngine() {
		stopWord = new StopWord();
		docParser = new DocParser(true);
		stemmer = new Stemmer();
		texts = new ArrayList<String>();
		final List<String> textsBruts = docParser.getListText();
		final List<String> textsFlitres = stopWord.filterTexts(textsBruts);
		for (final String text : textsFlitres) {
			texts.add(stemmer.stemText(text));
		}
		corpus = new Corpus(texts);
	}
	
	public String executeQuery(final String query){
		final String queryFiltre = stopWord.filter(query);
		final String queryStem = stemmer.stemText(queryFiltre);
		final String text = corpus.executeQuery(queryStem);
		return text;
	}

	public static void main(String[] args) {
		final SearchEngine searchEngine = new SearchEngine();
		searchEngine.executeQuery(QUERY_TEST);
	}
}
