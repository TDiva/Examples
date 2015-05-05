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
		
		tabbedPane.addTab("���������", new FilmPanel());
		tabbedPane.addTab("������ � ���������", new PersonPanel());
		tabbedPane.addTab("�����", new GenrePanel());
		tabbedPane.addTab("��������", new MediaPanel());
		tabbedPane.addTab("����� ������ �� �����", new FindByGenrePanel());
		tabbedPane.addTab("����� ������", new FindNewFilmsPanel());
		tabbedPane.addTab("������-���������", new FindActorsDirectorsPanel());
		pack();
	}

	public static void main(String[] args) {
		App app = new App();
		app.setVisible(true);

	}

}
