package recherche.launcher;

import recherche.model.SearchEngine;
import recherche.view.MainFrame;

public class Launcher {
	public static void main(String[] args){
		@SuppressWarnings("unused")
		final SearchEngine searchEngine = SearchEngine.getInstance(); 
		@SuppressWarnings("unused")
		final MainFrame mainFrame = MainFrame.getInstance();
	}
}
