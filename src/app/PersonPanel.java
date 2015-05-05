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

import repo.PersonRepository;
import entity.Person;

public class PersonPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4944602422772379335L;

	public final String[] colNames = { "№", "Имя на русском",
			"Оригинальное имя"};
	
	public List<Person> allPersons;

	private static PersonRepository repo = PersonRepository.getInstance();

	private int clickedRow = -1;

	public PersonPanel() {
		super();
		setLayout(new BorderLayout());

		allPersons = repo.getAll();

		final TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = 3792228808529538771L;

			@Override
			public Object getValueAt(int row, int col) {
				Person person = allPersons.get(row);
				switch (col) {
				case 0:
					return person.getId();
				case 1:
					return person.getRussianName();
				case 2:
					return person.getOriginalName();
				}
				return "";
			}

			@Override
			public int getRowCount() {
				return allPersons.size();
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

					JDialog dlg = new JDialog();
					dlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dlg.getContentPane().add(
							new PersonEditPanel(dlg, allPersons, model, allPersons.get(row)));
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
				dlg.getContentPane().add(new PersonEditPanel(dlg, allPersons, model));
				dlg.pack();
				dlg.setVisible(true);
			}
		});
		bottomPanel.add(add);

	}

}
