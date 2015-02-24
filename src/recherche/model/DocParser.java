package recherche.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DocParser {

	private static final String AP_PATH = "unifiedDB";
	private static final String TESTING_AP_PATH = "Testing";
	private static final String TEXT_TAG_NAME = "TEXT";

	private boolean test = false;
	private List<Text> texts;

	public DocParser(final boolean test) {
		this.test = test;
	}

	public DocParser() {
	}
	
	public List<Text> start(){
		try {
			texts = new ArrayList<Text>();
			final File[] dir;
			if (test) {
				dir = new File(TESTING_AP_PATH).listFiles();
			} else {
				dir = new File(AP_PATH).listFiles();
			}
			for (final File f : dir) {
				final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				final Document d = dBuilder.parse(f);
				final NodeList nl = d.getElementsByTagName(TEXT_TAG_NAME);
				texts.add(new Text(f.getAbsolutePath(), nl.item(0).getTextContent()));
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return texts;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}
}