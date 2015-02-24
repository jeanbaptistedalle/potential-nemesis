package recherche.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Corpus {

	/*
	 * Dans cette seconde map, chaque mot est associé à une liste dans laquelle
	 * se trouve l'id de tous les textes dans lesquels il apparait.
	 */
	private Map<String, List<DocPosition>> corpus;

	public Corpus(List<Text> listTexts) {
		final Stemmer stemmer = new Stemmer();
		this.corpus = new HashMap<String, List<DocPosition>>();
		for (final Text text : listTexts) {
			final StringTokenizer stopTokenizer = new StringTokenizer(text.getStoppedText());
			while (stopTokenizer.hasMoreTokens()) {
				String token = stopTokenizer.nextToken();
				String stemmedToken = stemmer.stemWord(token);
				if(corpus.containsKey(stemmedToken)){
					final List<DocPosition> list = corpus.get(stemmedToken);
					boolean find = false;
					for(final DocPosition doc : list){
						if(doc.getFilePath().equals(text.getTextPath())){
							find = true;
						}
					}
					if(!find){
						list.add(new DocPosition(text.getTextPath()));
					}
				}else{
					final List<DocPosition> listDoc = new ArrayList<DocPosition>();
					listDoc.add(new DocPosition(text.getTextPath()));
					corpus.put(stemmedToken, listDoc);
				}
			}
		}
	}

	public String executeQuery(final String query) {
		// TODO
		return null;
	}
	
	public String toString(){
		return corpus.toString();
	}
}
