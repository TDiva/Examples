package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import repo.FilmRepository;
import entity.Film;

public class FindNewFilmsPanel extends JPanel {

	private static final long serialVersionUID = 351352769498650024L;

	public final String[] colNames = { "№", "Имя на русском",
			"Оригинальное имя", "Год выхода", "Страна" };
	public List<Film> foundFilms;

	private static FilmRepository repo = FilmRepository.getInstance();

	public FindNewFilmsPanel() {
		super();
		setLayout(new BorderLayout());

		foundFilms = repo.findNew();

		final TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = 7573095794457921960L;

			@Override
			public Object getValueAt(int row, int col) {
				Film person = foundFilms.get(row);
				switch (col) {
				case 0:
					return person.getId();
				case 1:
					return person.getRussianName();
				case 2:
					return person.getOriginalName();
				case 3:
					return person.getYear().toString();
				case 4:
					return person.getCountry();
				}
				return "";
			}

			@Override
			public int getRowCount() {
				return foundFilms.size();
			}

			@Override
			public int getColumnCount() {
				return colNames.length;
			}

			@Override
			public String getColumnName(int col) {
				return colNames[col];
			}
		};

		final JTable table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(800, 300));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		add(topPanel, BorderLayout.NORTH);

		JButton btn = new JButton("Обновить");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				foundFilms = repo.findNew();
				((AbstractTableModel) model).fireTableDataChanged();
			}
		});
		topPanel.add(btn);

	}
}
