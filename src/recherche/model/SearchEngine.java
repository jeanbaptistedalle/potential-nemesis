package recherche.model;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {

	private static SearchEngine INSTANCE;
	private static final String QUERY_TEST = "Document will discuss allegations, or measures being taken"
			+ " against, corrupt public officials of any governmental jurisdiction worldwide. ";

	private StopWord stopWord;
	private DocParser docParser;
	private Stemmer stemmer;
	private Corpus corpus;

	private SearchEngine() {
		stopWord = new StopWord();
		stopWord.start();
		docParser = new DocParser(true);
		stemmer = new Stemmer();
		final List<Text> textsBruts = docParser.start();
		final List<Text> textSansPonctuation = stopWord.deleteSpecialChar(textsBruts);
		final List<Text> textsFiltres = stopWord.filterTexts(textSansPonctuation);
		
		corpus = new Corpus(textsFiltres);
		System.out.println(corpus);
	}
	
	public List<String> executeQuery(final String query){
		final String querySansPonctuation = stopWord.deleteSpecialChar(query);
		final String queryLowerCase = querySansPonctuation.toLowerCase();
		//TODO : gérer les opérateurs logique avant leur suppression
		final String queryFiltre = stopWord.filter(queryLowerCase);
		final String queryStem = stemmer.stemText(queryFiltre);
		//TODO while sur chaque sous requete
		final List<String> filePaths = corpus.executeQuery(queryStem);
		return filePaths;
	}
	
	public static SearchEngine getInstance(){
		if(INSTANCE == null){
			INSTANCE = new SearchEngine();
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		final SearchEngine searchEngine = SearchEngine.getInstance();
		searchEngine.executeQuery(QUERY_TEST);
	}
}
