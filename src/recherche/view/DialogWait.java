package recherche.view;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogWait extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -986237608593755313L;

	public DialogWait() {
		this.setTitle("Message");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setSize(new Dimension(300, 100));
		this.setPreferredSize(new Dimension(300, 100));
		this.pack();
		this.setLocationRelativeTo(MainFrame.getInstance());
		this.setVisible(true);
		
		final JPanel jpanel = new JPanel();
		jpanel.setLayout(new FlowLayout());
		jpanel.add(new JLabel("Please wait..."));
		this.add(jpanel);
	}
}
