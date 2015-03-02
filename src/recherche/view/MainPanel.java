package recherche.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5389098329101431252L;

	private SearchPanel searchPanel;
	private ResultPanel resultPanel;
	private JScrollPane scrollPane;

	public MainPanel() {
		searchPanel = new SearchPanel();
		resultPanel = new ResultPanel();
		scrollPane = new JScrollPane();

		scrollPane.setViewportView(resultPanel);
		this.setPreferredSize(new Dimension(1000, 500));
		this.setLayout(new BorderLayout());
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public SearchPanel getSearchPanel() {
		return searchPanel;
	}

	public void setSearchPanel(SearchPanel searchPanel) {
		this.searchPanel = searchPanel;
	}

	public ResultPanel getResultPanel() {
		return resultPanel;
	}

	public void setResultPanel(ResultPanel resultPanel) {
		this.resultPanel = resultPanel;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
}
