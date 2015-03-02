package recherche.model;

import java.util.ArrayList;
import java.util.List;

public class DocPosition {
	
	private String filePath;
	private List<Integer> positions;
	private List<String> originWords;

	public DocPosition(final String filePath) {
		this.filePath = filePath;
		positions = new ArrayList<Integer>();
		originWords = new ArrayList<String>();
	}

	public DocPosition(final String filePath, final Integer position) {
		this(filePath);
		positions.add(position);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<Integer> getPositions() {
		return positions;
	}

	public void setPositions(List<Integer> positions) {
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
