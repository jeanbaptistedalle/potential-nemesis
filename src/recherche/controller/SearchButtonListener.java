package recherche.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		final String query = mainPanel.getSearchPanel().getSearchField().getText();
		if (query == null || query.isEmpty()) {
			mainPanel.getResultPanel().emptyQuery();
		} else {
			final Solution listFilePath = SearchEngine.getInstance().executeQuery(query);
			final SearchEngine searchEngine = SearchEngine.getInstance();
			final Map<String, List<Integer>> sortedSolution = new HashMap<String, List<Integer>>(
					listFilePath.getSortedSolutions());
			final List<Text> listText = searchEngine.getFilesFromFilePaths(sortedSolution);
			mainPanel.getResultPanel().clearResult();
			if (listText == null || listText.size() == 0) {
				mainPanel.getResultPanel().noResult();
			} else {
				for (final Text text : listText) {
					mainPanel.getResultPanel().addTextResult(text);
				}
			}
		}
		mainPanel.getResultPanel().validate();
		mainPanel.getResultPanel().repaint();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainPanel.getScrollPane().getVerticalScrollBar().setValue(0);
			}
		});
	}
}
