package recherche;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DocParser {

	private static final String AP_PATH = "AP";
	private static final String TEXT_TAG_NAME = "TEXT";

	public DocParser() {
		try {
			final List<String> listText = new ArrayList<String>();
			final File[] dir = new File(AP_PATH).listFiles();
			for (File f : dir) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document d = dBuilder.parse(f);
				NodeList nl = d.getElementsByTagName(TEXT_TAG_NAME);
				for (int i = 0; i < nl.getLength(); i++) {
					listText.add(nl.item(i).getTextContent());
				}
			}
			System.out.println(listText);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}