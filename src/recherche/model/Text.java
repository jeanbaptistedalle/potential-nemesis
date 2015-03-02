package recherche.model;

import java.util.List;

public class Text {

	private String textPath;

	private String text;

	private List<Integer> positions;

	public Text(final String textPath, final String text, final List<Integer> positions) {
		this.textPath = textPath;
		this.text = text;
		this.positions = positions;
	}

	public String getTextPath() {
		return textPath;
	}

	public void setTextPath(final String textPath) {
		this.textPath = textPath;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public List<Integer> getPositions() {
		return positions;
	}

	public void setPositions(List<Integer> positions) {
		this.positions = positions;
	}
}
