package recherche.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import recherche.model.SearchEngine;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -796160221438766487L;
	private static final String FRAME_TITLE = "Search engine";
	private static MainFrame INSTANCE;

	private MainPanel mainPanel;
	private JMenuBar menuBar;

	private MainFrame() {
		mainPanel = new MainPanel();

		menuBar = new JMenuBar();
		final JMenuItem item = new JMenuItem("Update index");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				final DialogWait dialogWait = new DialogWait();
//				dialogWait.repaint();
				SearchEngine.getInstance().index();
//				dialogWait.setVisible(false);
				JOptionPane.showMessageDialog(MainFrame.getInstance(), "Index has been updated");
			}
		});
		final JMenu menuIndex = new JMenu("Index");
		menuIndex.add(item);
		menuBar.add(menuIndex);
		this.setJMenuBar(menuBar);

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
