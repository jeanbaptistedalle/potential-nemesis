package recherche.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DocParser {

	private static final String AP_PATH = "unifiedDB";
	private static final String TESTING_AP_PATH = "Testing";
	private static final String TEXT_TAG_NAME = "TEXT";

	public DocParser() {

	}
	
	public static List<File> getDirectoryText(final boolean test){
		if (test) {
			return Arrays.asList(new File(TESTING_AP_PATH).listFiles());
		} else {
			return Arrays.asList(new File(AP_PATH).listFiles());
		}
	}

	public List<Text> getDefaultTexts(final boolean test) {
		final List<File> dir = getDirectoryText(test);
		final List<Text> texts = new ArrayList<Text>();
		for (final File file : dir) {
			texts.add(prepareText(file, new ArrayList<Integer>()));
		}
		return texts;
	}

	public List<Text> getTexts(final Map<String, List<Integer>> map) {
		Map<String, List<Integer>> map2 = new HashMap<String, List<Integer>>(map);
		final List<Text> list = new ArrayList<Text>();
		for (final String filePath : map.keySet()) {
			final File f = new File(filePath);
			System.out.println(map2.containsKey(filePath));
			System.out.println(map2);
			final List<Integer> positions = map2.get(filePath);
			final Text text = prepareText(f, positions);
			list.add(text);
		}
		return list;
	}

	private Text prepareText(final File file, final List<Integer> positions) {
		try {
			final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			final Document d = dBuilder.parse(file);
			final NodeList nl = d.getElementsByTagName(TEXT_TAG_NAME);
			final Text text = new Text(file.getAbsolutePath(), nl.item(0).getTextContent(),
					positions);
			return text;
		} catch (final Exception e) {
			return null;
		}
	}
}