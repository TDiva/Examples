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

import repo.MediaRepository;
import entity.Media;

public class MediaPanel extends JPanel {

	private static final long serialVersionUID = -2943114916615479614L;

	public final String[] colNames = { "Тип носителя" };

	public List<Media> allMedias;

	private static MediaRepository repo = MediaRepository.getInstance();

	private int clickedRow = -1;

	public MediaPanel() {
		super();
		setLayout(new BorderLayout());

		allMedias = repo.getAll();

		final TableModel model = new AbstractTableModel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5077753792884263506L;

			@Override
			public Object getValueAt(int row, int col) {
				Media media = allMedias.get(row);
				switch (col) {
				case 0:
					return media.getType();
				}
				return "";
			}

			@Override
			public int getRowCount() {
				return allMedias.size();
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
							new MediaEditPanel(dlg, allMedias, model, allMedias
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
						new MediaEditPanel(dlg, allMedias, model));
				dlg.pack();
				dlg.setVisible(true);
			}
		});
		bottomPanel.add(add);

	}

}
