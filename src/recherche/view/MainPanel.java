package recherche.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MainPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5389098329101431252L;
	
	private SearchPanel searchPanel;
	private ResultPanel resultPanel;
	
	public MainPanel(){
		searchPanel = new SearchPanel();
		resultPanel = new ResultPanel();
		this.setLayout(new BorderLayout());
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(resultPanel, BorderLayout.SOUTH);
	}
}
