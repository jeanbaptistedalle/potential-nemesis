package recherche.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4513976360579838591L;

	private JTextField barreRecherche;
	
	private JButton submitButton;
	
	public SearchPanel(){
		barreRecherche = new JTextField();
		barreRecherche.setColumns(75);
		submitButton = new JButton("Rechercher");
		this.add(barreRecherche);
		this.add(submitButton);
	}

}
