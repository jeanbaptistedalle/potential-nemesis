package recherche.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import recherche.model.SearchEngine;
import recherche.model.Solution;
import recherche.model.Text;
import recherche.view.MainFrame;
import recherche.view.MainPanel;

public class SearchButtonListener implements ActionListener {

	public SearchButtonListener() {
	}

	public void actionPerformed(final ActionEvent arg0) {
		final MainPanel mainPanel = MainFrame.getInstance().getMainPanel();
		final Solution listFilePath = SearchEngine.getInstance().executeQuery(
				mainPanel.getSearchPanel().getSearchField().getText());
		final SearchEngine searchEngine = SearchEngine.getInstance();
		final List<Text> listText = searchEngine
				.getFilesFromFilePaths(new ArrayList<String>(listFilePath
						.getSolutions().keySet()));
		// final List<Text> listText =
		// searchEngine.getDocParser().getDefaultTexts(SearchEngine.getInstance().isDefText());
		mainPanel.getResultPanel().clearResult();
		if (listText == null || listText.size() == 0) {
			mainPanel.getResultPanel().noResult();
		} else {
			for (final Text text : listText) {
				mainPanel.getResultPanel().addTextResult(text);
			}
		}
		mainPanel.getResultPanel().validate();
		mainPanel.getResultPanel().repaint();
	}
}
