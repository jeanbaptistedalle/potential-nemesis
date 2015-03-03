package recherche.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.similarities.TFIDFSimilarity;

public class ValueComparator implements Comparator<String> {

	private Map<String, List<Integer>> map;
	private List<String> wordsOfQuery;
	private Map<String, Double> tfIdfOfQuery;
	private Solution out;

	public ValueComparator(Map<String, List<Integer>> base,
			List<String> wordsOfQuery) {
		this.map = base;
		this.wordsOfQuery = wordsOfQuery;
		this.out = null;
		computeQueryTFIDF();
	}

	public void setOut(Solution out) {
		this.out = out;
	}

	private void computeQueryTFIDF() {
		tfIdfOfQuery = SearchEngine.getInstance().getCorpus()
				.getTfIdfFromQuery(wordsOfQuery);
	}

	public int compare(String a, String b) {

		Double cosinusA;
		Double cosinusB;
		Map<String, Double> vectorQuery = tfIdfOfQuery;
		// Map<String, Double> vectorDoc = new HashMap<String, Double>();

		Double part1 = 0.d;
		Double part2 = 0.d;
		Double part3 = 0.d;

//		if (!out.getSolutions().containsKey(a)) {
			for (String key : vectorQuery.keySet()) {
				Double value = 0.d;

				// On récupère la liste des DocPosition
				List<DocPosition> dp = SearchEngine.getInstance().getCorpus()
						.getCorpus().get(key);

				for (DocPosition doc : dp) {
					if (doc.getFilePath().equals(a)) {
						value = doc.getTfIdf();
					}
				}

				// vectorDoc.put(key, value);

				Double indQ = vectorQuery.get(key);

				part1 += indQ * value;
				part2 += indQ * indQ;
				part3 += value * value;

			}

			cosinusA = part1 / (Math.sqrt(part2 * part3));
//			out.getCosDoc().put(a, cosinusA);
//		} else {
//			cosinusA = out.getCosDoc().get(a);
//		}

		part1 = 0.d;
		part2 = 0.d;
		part3 = 0.d;

//		if (!out.getCosDoc().containsKey(b)) {
			for (String key : vectorQuery.keySet()) {
				Double value = 0.d;

				// On récupère la liste des DocPosition
				List<DocPosition> dp = SearchEngine.getInstance().getCorpus()
						.getCorpus().get(key);

				for (DocPosition doc : dp) {
					if (doc.getFilePath().equals(b)) {
						value = doc.getTfIdf();
					}
				}

				// vectorDoc.put(key, value);

				Double indQ = vectorQuery.get(key);

				part1 += indQ * value;
				part2 += indQ * indQ;
				part3 += value * value;

			}

			cosinusB = part1 / (Math.sqrt(part2 * part3));
//			out.getCosDoc().put(b, cosinusB);
//		} else {
//			cosinusB = out.getCosDoc().get(b);
//		}

		if (cosinusA <= cosinusB) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}