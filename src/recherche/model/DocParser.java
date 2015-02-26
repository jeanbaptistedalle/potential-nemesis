package recherche.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	public List<Text> getDefaultTexts(final boolean test) {
		final List<File> dir;
		if (test) {
			dir = Arrays.asList(new File(TESTING_AP_PATH).listFiles());
		} else {
			dir = Arrays.asList(new File(AP_PATH).listFiles());
		}
		return prepareText(dir);
	}
	
	public List<Text> getTexts(final List<String> filePathList){
		final List<File> fileList = new ArrayList<File>();
		for(final String filePath : filePathList){
			final File f = new File(filePath);
			fileList.add(f);
		}
		return prepareText(fileList);
	}

	private List<Text> prepareText(final List<File> fileList) {
		try {
			final List<Text> texts = new ArrayList<Text>();

			for (final File f : fileList) {
				final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				final Document d = dBuilder.parse(f);
				final NodeList nl = d.getElementsByTagName(TEXT_TAG_NAME);
				texts.add(new Text(f.getAbsolutePath(), nl.item(0).getTextContent()));
			}
			return texts;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}