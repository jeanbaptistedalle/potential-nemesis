package recherche.view;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -796160221438766487L;
	private static final String FRAME_TITLE = "Moteur de recherche";
	private static MainFrame INSTANCE;

	private MainPanel mainPanel;

	private MainFrame() {
		mainPanel = new MainPanel();
		this.setTitle(FRAME_TITLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.add(mainPanel);
		this.pack();
	}

	public static MainFrame getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MainFrame();
		}
		return INSTANCE;
	}

	public MainPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
}
