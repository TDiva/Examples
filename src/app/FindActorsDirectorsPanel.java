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

import repo.PersonRepository;
import entity.Person;

public class FindActorsDirectorsPanel extends JPanel {

	private static final long serialVersionUID = 1649580886329958367L;

	public final String[] colNames = { "№", "Имя на русском",
			"Оригинальное имя" };

	public List<Person> foundActors;

	private static PersonRepository repo = PersonRepository.getInstance();

	public FindActorsDirectorsPanel() {
		super();
		setLayout(new BorderLayout());

		foundActors = repo.findActorsDirected();

		final TableModel model = new AbstractTableModel() {

			private static final long serialVersionUID = 4049686064830720613L;

			@Override
			public Object getValueAt(int row, int col) {
				Person person = foundActors.get(row);
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
				return foundActors.size();
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

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		add(topPanel, BorderLayout.NORTH);

		JButton btn = new JButton("Обновить");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				foundActors = repo.findActorsDirected();
				((AbstractTableModel) model).fireTableDataChanged();
			}
		});
		topPanel.add(btn);

	}

}
