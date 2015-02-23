package recherche;

public class Main {
	public static void main(String[] args) {
		final StopWord stopWord = new StopWord();
		final DocParser docParser = new DocParser();
		final Corpus corpus = new Corpus(stopWord.filterTexts(docParser.getListText()));
	}
}
