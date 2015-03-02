package recherche.model;

import java.util.ArrayList;
import java.util.List;

public class DocPosition {

	private String filePath;
	private List<Long> positions;
	private List<String> originWords;

	public DocPosition(final String filePath) {
		this.filePath = filePath;
		positions = new ArrayList<Long>();
		originWords = new ArrayList<String>();
	}

	public DocPosition(final String filePath, final Long position) {
		this(filePath);
		positions.add(position);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<Long> getPositions() {
		return positions;
	}

	public void setPositions(List<Long> positions) {
		this.positions = positions;
	}

	public String toString() {
		return filePath + positions;
	}

	public Integer getSize() {
		return positions.size();
	}

	public List<String> getOriginWords() {
		return originWords;
	}

	public void setOriginWords(List<String> originWords) {
		this.originWords = originWords;
	}
}
