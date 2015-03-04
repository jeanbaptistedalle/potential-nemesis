package recherche.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import recherche.model.SearchEngine;
import recherche.model.Solution;
import recherche.model.Text;
import recherche.view.MainFrame;
import recherche.view.MainPanel;

public class SearchButtonListener implements ActionListener {

	public SearchButtonListener() {
	}

	public void actionPerformed(final ActionEvent arg0) {
		long timeStart = System.currentTimeMillis();
		final MainPanel mainPanel = MainFrame.getInstance().getMainPanel();
		final String query = mainPanel.getSearchPanel().getSearchField()
				.getText();
		if (query == null || query.isEmpty()) {
			mainPanel.getResultPanel().emptyQuery();
		} else {

			JLanguageTool langTool;

			try {
				langTool = new JLanguageTool(new BritishEnglish());
				langTool.activateDefaultPatternRules();
				String squery = query.trim();
				squery = Character.toUpperCase(squery.charAt(0))
						+ squery.substring(1);
				List<RuleMatch> matches = langTool.check(squery);

				for (RuleMatch match : matches) {
					System.out.println("Potential error at column "
							+ match.getColumn() + ": " + match.getMessage());
					System.out.println("Suggested correction: "
							+ match.getSuggestedReplacements().get(0));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final Solution listFilePath = SearchEngine.getInstance()
					.executeQuery(query);
			final SearchEngine searchEngine = SearchEngine.getInstance();
			final Map<String, List<Integer>> sortedSolution = listFilePath
					.getSortedSolutions();

			System.out.println("\n\n" + sortedSolution + "\n\n");

			final List<Text> listText = searchEngine
					.getFilesFromFilePaths(sortedSolution);
			Collections.reverse( listText);
			mainPanel.getResultPanel().clearResult();
			long timeEnd = System.currentTimeMillis();
			long elapsedTime = timeEnd - timeStart;
			mainPanel.getResultPanel().addInfo(elapsedTime, listText.size());
			if (listText == null || listText.size() == 0) {
				mainPanel.getResultPanel().noResult();
			} else {
				for (final Text text : listText) {
					mainPanel.getResultPanel().addTextResult(text,
							listFilePath.getCosDoc().get(text.getTextPath()));
					System.out.println(text.getTextPath() + " = "
							+ listFilePath.getCosDoc().get(text.getTextPath()));
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
