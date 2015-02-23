package recherche;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		final String queryTest = "Document will discuss allegations, or measures being taken"
				+ " against, corrupt public officials of any governmental jurisdiction worldwide. ";

		final StopWord stopWord = new StopWord();

		final DocParser docParser = new DocParser();
		final Stemmer stemmer = new Stemmer();
		final List<String> texts = new ArrayList<String>();
		for (final String text : stopWord.filterTexts(docParser.getListText())) {
			texts.add(stemmer.stemText(text));
		}
		final Corpus corpus = new Corpus(texts);
		return;
	}
}
