package recherche.model;

public class Text {

	private String textPath;

	private String text;

	public Text(final String textPath, final String text) {
		this.textPath = textPath;
		this.text = text;
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
	
	public String toString(){
		return text;
	}
}
