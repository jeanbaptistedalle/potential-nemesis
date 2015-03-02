package recherche.view;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import recherche.model.Text;

public class ResultPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3877474126512088645L;

	private List<Component> resultList;

	public ResultPanel() {
		resultList = new ArrayList<Component>();
		this.setLayout(new GridBagLayout());
		this.setAutoscrolls(true);
		JLabel jlabel = new JLabel("Veuillez effectuer une recherche");
		addComponent(jlabel);
	}

	public void noResult() {
		final JLabel noResult = new JLabel("Il n'existe aucun résultat pour votre recherche");
		addComponent(noResult);
	}

	public void emptyQuery() {
		final JLabel emptyQuery = new JLabel("Veuillez entrer au moins un mot dans votre recherche");
		addComponent(emptyQuery);
	}

	public void addTextResult(final Text text) {
		final StringBuilder stringBuilder = new StringBuilder();
		final StringTokenizer stringTokenizer = new StringTokenizer(text.getText());
		int cpt = 0;
		boolean first = true;
		while (stringTokenizer.hasMoreElements()) {
			if (first) {
				first = false;
			} else {
				stringBuilder.append(" ");
			}
			final String s = stringTokenizer.nextToken();
			if (text.getPositions() != null && text.getPositions().contains(cpt)) {
				stringBuilder.append("<strong>");
				stringBuilder.append(s);
				stringBuilder.append("</strong>");
			} else {
				stringBuilder.append(s);
			}
			cpt++;
		}

		final JEditorPane result = new JEditorPane("text/html", stringBuilder.toString());
		result.setContentType("text/html");
		result.setText(stringBuilder.toString());
		result.setEditable(false);
		result.setPreferredSize(new Dimension(500, 300));

		final JButton button = new JButton("Ouvrir");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(new URI("file://" + text.getTextPath()));
					} catch (final Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			}
		});

		GridBagConstraints buttonConstraints = new GridBagConstraints();
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = resultList.size();
		buttonConstraints.insets = new Insets(10, 10, 10, 10);
		addComponent(button, buttonConstraints);

		GridBagConstraints resultConstraints = new GridBagConstraints();
		resultConstraints.gridx = 1;
		resultConstraints.gridy = resultList.size() - 1;
		resultConstraints.insets = new Insets(10, 0, 0, 0);
		addComponent(result, resultConstraints);
	}

	public void addComponent(final Component component, final GridBagConstraints contraint) {
		this.add(component, contraint);
		resultList.add(component);
	}

	public void addComponent(final Component component) {
		this.add(component);
		resultList.add(component);
	}

	public void clearResult() {
		for (final Component component : resultList) {
			this.remove(component);
		}
		resultList.clear();
	}
}
