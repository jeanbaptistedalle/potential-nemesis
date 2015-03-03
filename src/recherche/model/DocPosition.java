package recherche.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4881241492296984088L;
	private String filePath;
	private List<Integer> positions;
	private List<String> originWords;

	private Double tfIdf;

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

	public Integer getSize() {
		return positions.size();
	}

	public List<String> getOriginWords() {
		return originWords;
	}

	public void setOriginWords(List<String> originWords) {
		this.originWords = originWords;
	}

	public Double getTfIdf() {
		return tfIdf;
	}

	public void setTfIdf(Double tfIdf) {
		this.tfIdf = tfIdf;
	}

	@Override
	public String toString() {
		return "DocPosition [filePath=" + filePath + ", positions=" + positions + ", originWords="
				+ originWords + ", tfIdf=" + tfIdf + "]";
	}
}
