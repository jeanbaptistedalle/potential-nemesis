package recherche.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
				squery = Character.toUpperCase(squery.charAt(0)) + squery.substring(1);
				List<RuleMatch> matches = langTool
						.check(squery);

				for (RuleMatch match : matches) {
					System.out.println("Potential error at line "
							+ match.getLine() + ", column " + match.getColumn()
							+ ": " + match.getMessage());
					System.out.println("Suggested correction: "
							+ match.getSuggestedReplacements());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final Solution listFilePath = SearchEngine.getInstance()
					.executeQuery(query);
			final SearchEngine searchEngine = SearchEngine.getInstance();
			final List<Text> listText = searchEngine
					.getFilesFromFilePaths(new ArrayList<String>(listFilePath
							.getSortedSolutions().keySet()));
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
	}
}
