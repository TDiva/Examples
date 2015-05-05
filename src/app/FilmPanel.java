package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import repo.FilmRepository;
import entity.Film;

public class FilmPanel extends JPanel {

	private static final long serialVersionUID = -454054067376128953L;
	
	public final String[] colNames = { "№", "Имя на русском",
			"Оригинальное имя", "Год выхода", "Страна" };
	public List<Film> allFilms;

	private static FilmRepository repo = FilmRepository.getInstance();

	private int clickedRow = -1;

	public FilmPanel() {
		super();
		setLayout(new BorderLayout());

		allFilms = repo.getAll();

		final TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = -1902886942248393137L;

			@Override
			public Object getValueAt(int row, int col) {
				Film person = allFilms.get(row);
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
				return allFilms.size();
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

		JTable table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		add(pane, BorderLayout.CENTER);
		pane.setPreferredSize(new Dimension(800, 300));

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table = (JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (row == clickedRow) {
					// Open edit film dialog with id = allFilms.get(row);
					clickedRow = -1;
					allFilms = repo.getAll();

					JDialog dlg = new JDialog();
					dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dlg.getContentPane().add(
							new FilmEditPanel(dlg, allFilms, model, allFilms
									.get(row)));
					dlg.pack();
					dlg.setVisible(true);
				}
				clickedRow = row;
			}
		});

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);

		JButton add = new JButton("Добавить");
		add.setSize(100, 30);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO: open adding dialog

				JDialog dlg = new JDialog();
				dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				dlg.getContentPane().add(
						new FilmEditPanel(dlg, allFilms, model));
				dlg.pack();
				dlg.setVisible(true);
			}
		});
		bottomPanel.add(add);

	}
}
