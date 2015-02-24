package recherche.model;

import java.util.ArrayList;
import java.util.List;

public class DocPosition {

	private String filePath;
	private List<Long> positions;

	public DocPosition(final String filePath) {
		this.filePath = filePath;
		positions = new ArrayList<Long>();
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
	
	public String toString(){
		return filePath;
	}
}
