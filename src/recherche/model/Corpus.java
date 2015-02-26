package recherche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Corpus {

	/*
	 * Dans cette map, chaque mot est associé à un docPosition, qui contient le
	 * fichier dans lequel ce mot a été trouvé. TODO : Ajouter la position du
	 * mot dans le fichier en question
	 */
	private Map<String, List<DocPosition>> corpus;

	private StopWord stopWord;

	public Corpus(final StopWord stopWord) {
		this.stopWord = stopWord;
	}

	public void start(List<Text> listTexts) {
		final Stemmer stemmer = new Stemmer();
		this.corpus = new HashMap<String, List<DocPosition>>();
		Long cptText = 0L;
		Long cptMot = 0L;
		for (final Text text : listTexts) {
			final StringTokenizer stopTokenizer = new StringTokenizer(text.getText());
			while (stopTokenizer.hasMoreTokens()) {
				final String token = stopTokenizer.nextToken();
				final String tokenSansPonctuation = stopWord.deleteSpecialChar(token);
				if (tokenSansPonctuation != null && !tokenSansPonctuation.isEmpty()) {
					final String stemmedToken = stemmer.stemWord(tokenSansPonctuation);
					if (!stopWord.contains(stemmedToken)) {
						if (corpus.containsKey(stemmedToken)) {
							final List<DocPosition> list = corpus.get(stemmedToken);
							boolean find = false;
							for (final DocPosition doc : list) {
								if (doc.getFilePath().equals(text.getTextPath())) {
									find = true;
									doc.getPositions().add(cptMot);
									break;
								}
							}
							if (!find) {
								list.add(new DocPosition(text.getTextPath(), cptText, cptMot));
							}
						} else {
							final List<DocPosition> listDoc = new ArrayList<DocPosition>();
							listDoc.add(new DocPosition(text.getTextPath(), cptText, cptMot));
							corpus.put(stemmedToken, listDoc);
						}
					}
				}
				cptMot++;
			}
			cptText++;
			cptMot = 0L;
		}
	}

	public List<String> executeQuery(final String query) {

		StringTokenizer elements = new StringTokenizer(query);

		Set<String> filepaths = new HashSet<String>();

		while (elements.hasMoreElements()) {
			String elt = (String) elements.nextElement();
			if (corpus.containsKey(elt)) {
				for (DocPosition dp : corpus.get(elt)) {
					filepaths.add(dp.getFilePath());
				}
			}
		}

		return new ArrayList<String>(filepaths);
	}

	public String toString() {
		return corpus.toString();
	}
}
