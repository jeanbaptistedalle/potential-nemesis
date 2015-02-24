package launcher;

import recherche.model.SearchEngine;
import recherche.view.MainFrame;

public class Launcher {
	public static void main(String[] args){
		final SearchEngine searchEngine = SearchEngine.getInstance(); 
		final MainFrame mainFrame = MainFrame.getInstance();
	}
}
