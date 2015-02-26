package recherche.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import recherche.controller.SearchButtonListener;

public class SearchPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4513976360579838591L;

	private JTextField searchField;
	private JButton submitButton;
	private SearchButtonListener searchButtonListener;

	public SearchPanel() {
		searchField = new JTextField();
		searchField.setColumns(75);
		submitButton = new JButton("Rechercher");
		searchButtonListener = new SearchButtonListener();
		submitButton.addActionListener(searchButtonListener);
		this.add(searchField);
		this.add(submitButton);
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public void setSearchField(JTextField searchField) {
		this.searchField = searchField;
	}

	public JButton getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(JButton submitButton) {
		this.submitButton = submitButton;
	}

	public SearchButtonListener getSearchButtonListener() {
		return searchButtonListener;
	}

	public void setSearchButtonListener(SearchButtonListener searchButtonListener) {
		this.searchButtonListener = searchButtonListener;
	}
}
