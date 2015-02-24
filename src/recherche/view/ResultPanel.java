package recherche.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3877474126512088645L;

	private JLabel result;
	
	public ResultPanel(){
		result = new JLabel("blabla");
		this.add(result);
	}
}
