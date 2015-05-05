package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import repo.GenreRepository;
import entity.Genre;

public class GenreEditPanel extends JPanel {

	private static final long serialVersionUID = -711064449592471910L;
	
	public static final Dimension LABEL_DIM = new Dimension(150, 20);
	public static final Dimension TEXT_DIM = new Dimension(300, 20);

	private JDialog dlg;
	private List<Genre> all;
	private TableModel model;

	private Genre genre;

	private JTextArea name;

	private GenreRepository repo = GenreRepository.getInstance();

	public GenreEditPanel(JDialog dlg, List<Genre> all, TableModel model) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;

		genre = new Genre();

		name = new JTextArea();
		createPanel();
	}

	public GenreEditPanel(JDialog dlg, List<Genre> all, TableModel model,
			Genre person) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;
		this.genre = person;

		name = new JTextArea(person.getName());

		createPanel();
	}

	private void createPanel() {
		setLayout(new BorderLayout());

		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
		add(selectPanel, BorderLayout.CENTER);

		JPanel p = new JPanel();
		JLabel lbl = new JLabel("Оригинальное имя: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		name.setPreferredSize(TEXT_DIM);
		p.add(name);
		selectPanel.add(p);

		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		add(btnPanel, BorderLayout.SOUTH);

		lbl = new JLabel();
		lbl.setPreferredSize(LABEL_DIM);
		btnPanel.add(lbl);

		JButton save = new JButton("Save");
		btnPanel.add(save);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				
				if (genre.getName() != null) {
					repo.delete(genre);
					all.remove(genre);
				}
				
				genre.setName(name.getText());
				genre.setFilms(null);

				if (!all.contains(genre)) {
					all.add(genre);
					repo.saveOrUpdate(genre);
				}

				((AbstractTableModel) model).fireTableDataChanged();
				dlg.setVisible(false);
			}
		});

		JButton cancel = new JButton("Cancel");
		btnPanel.add(cancel);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				dlg.setVisible(false);
			}
		});

		if (genre.getName() != null) {
			JButton delete = new JButton("Delete");
			btnPanel.add(delete);
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					repo.delete(genre);
					all.remove(genre);

					((AbstractTableModel) model).fireTableDataChanged();
					dlg.setVisible(false);
				}
			});

		}

	}

}
