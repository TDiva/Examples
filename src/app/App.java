package app;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class App extends JFrame {

	private static final long serialVersionUID = 7607468976958666544L;
	
	public App() {
		JTabbedPane tabbedPane = new JTabbedPane();
		this.setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("Видеотека", new FilmPanel());
		tabbedPane.addTab("Актеры и режиссеры", new PersonPanel());
		tabbedPane.addTab("Жанры", new GenrePanel());
		tabbedPane.addTab("Носители", new MediaPanel());
		tabbedPane.addTab("Найти фильмы по жанру", new FindByGenrePanel());
		tabbedPane.addTab("Новые фильмы", new FindNewFilmsPanel());
		tabbedPane.addTab("Актеры-режиссеры", new FindActorsDirectorsPanel());
		pack();
	}

	public static void main(String[] args) {
		App app = new App();
		app.setVisible(true);

	}

}
