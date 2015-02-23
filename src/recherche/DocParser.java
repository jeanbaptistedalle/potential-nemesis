package recherche;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class DocParser {

	private static final String AP_PATH = "testing";
	private static final String TEXT_TAG_NAME = "TEXT";

	public DocParser() {
		try {
			final List<String> listText = new ArrayList<String>();
			final File[] dir = new File(AP_PATH).listFiles();
			for (File f : dir) {
				FileReader fr = new FileReader(f);

				String line;
				String everything;

				try (BufferedReader br = new BufferedReader(fr)) {
					StringBuilder sb = new StringBuilder();
					line = br.readLine();

					while (line != null) {
						sb.append(line);
						sb.append(System.lineSeparator());
						line = br.readLine();
					}
					everything = sb.toString();
				}

				String xmlstring = "<?xml version=\"1.0\"?>\n<docs>"
						+ everything + "</docs>";

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document d = dBuilder.parse(new InputSource(new StringReader( xmlstring)));
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