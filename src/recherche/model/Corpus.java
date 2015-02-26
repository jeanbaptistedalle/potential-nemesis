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
	 * fichier dans lequel ce mot a été trouvé.
	 */
	private Map<String, List<DocPosition>> corpus;

	private StopWord stopWord;

	public Corpus(final StopWord stopWord) {
		this.stopWord = stopWord;
	}

	public void start(List<Text> listTexts) {
		final Stemmer stemmer = new Stemmer();
		this.corpus = new HashMap<String, List<DocPosition>>();
		Long cptMot = 0L;
		for (final Text text : listTexts) {
			final StringTokenizer stopTokenizer = new StringTokenizer(
					text.getText());
			while (stopTokenizer.hasMoreTokens()) {
				final String token = stopTokenizer.nextToken();
				final String tokenSansPonctuation = stopWord
						.deleteSpecialChar(token);
				if (tokenSansPonctuation != null
						&& !tokenSansPonctuation.isEmpty()) {
					final String stemmedToken = stemmer
							.stemWord(tokenSansPonctuation);
					if (!stopWord.contains(stemmedToken)) {
						if (corpus.containsKey(stemmedToken)) {
							final List<DocPosition> list = corpus
									.get(stemmedToken);
							boolean find = false;
							for (final DocPosition doc : list) {
								if (doc.getFilePath()
										.equals(text.getTextPath())) {
									find = true;
									doc.getPositions().add(cptMot);
									break;
								}
							}
							if (!find) {
								list.add(new DocPosition(text.getTextPath(),
										cptMot));
							}
						} else {
							final List<DocPosition> listDoc = new ArrayList<DocPosition>();
							listDoc.add(new DocPosition(text.getTextPath(),
									cptMot));
							corpus.put(stemmedToken, listDoc);
						}
					}
				}
				cptMot++;
			}
			cptMot = 0L;
		}
	}

	public Solution executeQuery(final String query) {

		StringTokenizer elements = new StringTokenizer(query);

		Solution filepaths = new Solution();

		// if there is no token, so there is no result

		if (!elements.hasMoreTokens())
			return new Solution();

		String elt1 = (String) elements.nextElement();
		if (corpus.containsKey(elt1)) {
			for (DocPosition dp : corpus.get(elt1)) {
				filepaths.add(dp.getFilePath(), dp.getPositions().size());
			}
		}

		while (elements.hasMoreElements()) {
			String elt = (String) elements.nextElement();
			Solution tmp = new Solution();
			if (corpus.containsKey(elt)) {
				for (DocPosition dp : corpus.get(elt)) {
					tmp.add(dp.getFilePath(), dp.getPositions().size());
				}
			}
			filepaths = filepaths.retainAll(tmp);
		}

		return filepaths;
	}

	public Map<String, List<DocPosition>> getCorpus() {
		return corpus;
	}

	public void setCorpus(Map<String, List<DocPosition>> corpus) {
		this.corpus = corpus;
	}
	
	public Integer getSize(){
		int count = 0;
		for(String key : corpus.keySet()){
			final List<DocPosition> docPositions = corpus.get(key);
			for(final DocPosition docPosition : docPositions){
				count+=docPosition.getSize();
			}
		}
		return count;
	}

	public String toString() {
		return corpus.toString();
	}
}
