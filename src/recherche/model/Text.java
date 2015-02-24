package recherche.model;

public class Text {

	private String textPath;

	private String stoppedText;
	private String originalText;

	public Text(final String textPath, final String text) {
		this.textPath = textPath;
		this.originalText = text;
	}

	public String getTextPath() {
		return textPath;
	}

	public void setTextPath(String textPath) {
		this.textPath = textPath;
	}

	public String getStoppedText() {
		return stoppedText;
	}

	public void setStoppedText(String stoppedText) {
		this.stoppedText = stoppedText;
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}
}
