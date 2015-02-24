package recherche.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Corpus {

	/*
	 * Dans cette map, chaque mot est associé à un docPosition, qui contient le
	 * fichier dans lequel ce mot a été trouvé.
	 * TODO : Ajouter la position du mot dans le fichier en question
	 */
	private Map<String, List<DocPosition>> corpus;

	public Corpus(List<Text> listTexts) {
		final Stemmer stemmer = new Stemmer();
		this.corpus = new HashMap<String, List<DocPosition>>();
		Long cpt = 0L;
		for (final Text text : listTexts) {
			final StringTokenizer stopTokenizer = new StringTokenizer(text.getStoppedText());
			while (stopTokenizer.hasMoreTokens()) {
				String token = stopTokenizer.nextToken();
				String stemmedToken = stemmer.stemWord(token);
				if (corpus.containsKey(stemmedToken)) {
					final List<DocPosition> list = corpus.get(stemmedToken);
					boolean find = false;
					for (final DocPosition doc : list) {
						if (doc.getFilePath().equals(text.getTextPath())) {
							find = true;
						}
					}
					if (!find) {
						list.add(new DocPosition(text.getTextPath(), cpt));
						cpt++;
					}
				} else {
					final List<DocPosition> listDoc = new ArrayList<DocPosition>();
					listDoc.add(new DocPosition(text.getTextPath(), cpt));
					cpt++;
					corpus.put(stemmedToken, listDoc);
				}
			}
		}
	}

	public List<String> executeQuery(final String query) {
		// TODO
		return null;
	}

	public String toString() {
		return corpus.toString();
	}
}
