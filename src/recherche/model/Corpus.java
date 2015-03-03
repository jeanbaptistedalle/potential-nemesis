package recherche.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Corpus {

	/*
	 * Dans cette map, chaque mot est associé à un docPosition, qui contient le
	 * fichier dans lequel ce mot a été trouvé.
	 */
	private Map<String, List<DocPosition>> corpus;

	private StopWord stopWord;
	private List<Text> listOfTexts;

	public Corpus(final StopWord stopWord) {
		this.stopWord = stopWord;
	}

	public void start(List<Text> listTexts) {
		listOfTexts = listTexts;
		final Stemmer stemmer = new Stemmer();
		this.corpus = new HashMap<String, List<DocPosition>>();
		Integer cptMot = 0;
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
									if (!doc.getOriginWords().contains(tokenSansPonctuation)) {
										doc.getOriginWords().add(tokenSansPonctuation);
									}
									break;
								}
							}
							if (!find) {
								list.add(new DocPosition(text.getTextPath(), cptMot));
							}
						} else {
							final List<DocPosition> listDoc = new ArrayList<DocPosition>();
							final DocPosition docPosition = new DocPosition(text.getTextPath(),
									cptMot);
							docPosition.getOriginWords().add(tokenSansPonctuation);
							listDoc.add(docPosition);
							corpus.put(stemmedToken, listDoc);
						}
					}
				}
				cptMot++;
			}
			cptMot = 0;
		}
		
		for(final String key : corpus.keySet()){
			for(final DocPosition doc : corpus.get(key)){
				final Double tf = 1 + Math.log((double)doc.getSize());
				final Double idf= Math.log(listTexts.size()/(corpus.get(key).size()));
				doc.setTfIdf(tf * idf);
			}
		}
	}
	
	public Map<String, Double> getTfIdfFromQuery(List<String> query)
	{
		Map<String, Double> ret = new HashMap<String, Double>();
		
		for(String key: query){
			
			for(final DocPosition doc : corpus.get(key)){
				final Double tf = 1 + Math.log((double) query.size());
				final Double idf= Math.log(DocParser./(corpus.get(key).size()));
				ret.put(key, tf * idf);
			}
		}
		
		return ret;
	}

	private List<String> regex_corpusContains(String pattern) {

		List<String> ret = new ArrayList<String>();

		String p = pattern.replaceAll("\\.", ".?");
		p = p.replaceAll("\\*", "(.)*");
		Pattern patt = Pattern.compile(p);

		System.out.println(p);

		for (final String key : corpus.keySet()) {
			for (final DocPosition docPosition : corpus.get(key)) {
				for (final String originWord : docPosition.getOriginWords()) {
					if (patt.matcher(originWord).matches()) {
						System.out.println(originWord);
						ret.add(key);
					}
				}
			}

		}

		return ret;
	}

	public Solution executeQuery(final String query) {

		StringTokenizer elements = new StringTokenizer(query);

		Solution filepaths = new Solution();

		// if there is no token, so there is no result

		if (!elements.hasMoreTokens())
			return new Solution();

		String elt1 = (String) elements.nextElement();
		if (!elt1.contains("*") && !elt1.contains(".")) {
			if (corpus.containsKey(elt1)) {
				for (DocPosition dp : corpus.get(elt1)) {
					filepaths.add(dp.getFilePath(), dp.getPositions());
				}
			}
		} else {
			List<String> b = regex_corpusContains(elt1);
			if (b.size() > 0) {
				for (String elt : b) {
					for (DocPosition dp : corpus.get(elt)) {
						filepaths.add(dp.getFilePath(), dp.getPositions());
					}
				}
			}
		}

		while (elements.hasMoreElements()) {
			String elt = (String) elements.nextElement();
			Solution tmp = new Solution();

			if (!elt.contains("*") && !elt.contains(".")) {
				if (corpus.containsKey(elt)) {
					for (DocPosition dp : corpus.get(elt)) {
						tmp.add(dp.getFilePath(), dp.getPositions());
					}
				}
			} else {
				List<String> b = regex_corpusContains(elt1);
				if (b.size() > 0) {
					for (String elt2 : b) {
						for (DocPosition dp : corpus.get(elt2)) {
							filepaths.add(dp.getFilePath(), dp.getPositions());
						}
					}
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

	public Integer getSize() {
		int count = 0;
		for (String key : corpus.keySet()) {
			final List<DocPosition> docPositions = corpus.get(key);
			for (final DocPosition docPosition : docPositions) {
				count += docPosition.getSize();
			}
		}
		return count;
	}

	public String toString() {
		return corpus.toString();
	}
}
