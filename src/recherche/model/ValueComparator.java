package recherche.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ValueComparator implements Comparator<String> {

	@SuppressWarnings("unused")
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
				.getTfIdfFromQueryv2(wordsOfQuery);

		// System.out.println(tfIdfOfQuery);
	}

	public int compare(String a, String b) {

		Double cosinusA;
		Double cosinusB;
		Map<String, Double> vectorQuery = tfIdfOfQuery;
		// Map<String, Double> vectorDoc = new HashMap<String, Double>();

		Double part1 = 0.d;
		Double part2 = 0.d;
		Double part3 = 0.d;

		// System.out.println(a + " " + b);
		Map<String, List<DocPosition>> corpus = SearchEngine.getInstance()
				.getCorpus().getCorpus();

		if (!out.getCosDoc().containsKey(a)) {

			for (String key : corpus.keySet()) {
				Double value = 0.d;

				// On récupère la liste des DocPosition
				List<DocPosition> dp = corpus.get(key);

				for (DocPosition doc : dp) {
					if (doc.getFilePath().equals(a)) {
						value = doc.getTfIdf();
						// System.out.println("value="+value);
					}
				}

				// vectorDoc.put(key, value);

				Double indQ = vectorQuery.get(key);

//				System.out.println("\tindQ=" + indQ);
//				System.out.println("\tvalue=" + value);

				part1 += indQ * value;
				part2 += indQ * indQ;
				part3 += value * value;

			}

			cosinusA = part1 / (Math.sqrt(part2 * part3));
			out.getCosDoc().put(a, cosinusA);
		} else {
			cosinusA = out.getCosDoc().get(a);
		}

		part1 = 0.d;
		part2 = 0.d;
		part3 = 0.d;

		if (!out.getCosDoc().containsKey(b)) {
			for (String key : corpus.keySet()) {
				Double value = 0.d;

				// On récupère la liste des DocPosition
				List<DocPosition> dp = corpus.get(key);

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

				// System.out.println(part1 + " " + part2 + " " + part3);

			}

			cosinusB = part1 / (Math.sqrt(part2 * part3));
			out.getCosDoc().put(b, cosinusB);
		} else {
			cosinusB = out.getCosDoc().get(b);
		}

		System.out.println(a + " == " + cosinusA + b + " == " + cosinusB);

		if (cosinusA <= cosinusB) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}

	public int comparev1(String a, String b) {

		Double cosinusA;
		Double cosinusB;
		Map<String, Double> vectorQuery = tfIdfOfQuery;
		// Map<String, Double> vectorDoc = new HashMap<String, Double>();

		Double part1 = 0.d;
		Double part2 = 0.d;
		Double part3 = 0.d;

		// System.out.println(a + " " + b);

		if (!out.getCosDoc().containsKey(a)) {
			for (String key : vectorQuery.keySet()) {
				Double value = 0.d;

				// On récupère la liste des DocPosition
				List<DocPosition> dp = SearchEngine.getInstance().getCorpus()
						.getCorpus().get(key);

				for (DocPosition doc : dp) {
					if (doc.getFilePath().equals(a)) {
						value = doc.getTfIdf();
						// System.out.println("value="+value);
					}
				}

				// vectorDoc.put(key, value);

				Double indQ = vectorQuery.get(key);

//				System.out.println("\tindQ=" + indQ);
//				System.out.println("\tvalue=" + value);

				part1 += indQ * value;
				part2 += indQ * indQ;
				part3 += value * value;

			}

			cosinusA = part1 / (Math.sqrt(part2 * part3));
			out.getCosDoc().put(a, cosinusA);
		} else {
			System.out.println("Get last A");
			cosinusA = out.getCosDoc().get(a);
		}

		part1 = 0.d;
		part2 = 0.d;
		part3 = 0.d;

		if (!out.getCosDoc().containsKey(b)) {
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

				// System.out.println(part1 + " " + part2 + " " + part3);

			}

			cosinusB = part1 / (Math.sqrt(part2 * part3));
			out.getCosDoc().put(b, cosinusB);
		} else {
			System.out.println("Get last B");
			cosinusB = out.getCosDoc().get(b);
		}

		System.out.println("CosinusA=" + cosinusA + " CosinusB=" + cosinusB);

		if (cosinusA < cosinusB) {
			return 1;
		} else {
			return -1;
		} // returning 0 would merge keys
	}
}