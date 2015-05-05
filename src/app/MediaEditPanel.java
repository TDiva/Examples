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

import repo.MediaRepository;
import entity.Media;

public class MediaEditPanel extends JPanel {

	private static final long serialVersionUID = -3283506426487191455L;
	
	public static final Dimension LABEL_DIM = new Dimension(150, 20);
	public static final Dimension TEXT_DIM = new Dimension(300, 20);

	private JDialog dlg;
	private List<Media> all;
	private TableModel model;

	private Media media;

	private JTextArea type;

	private MediaRepository repo = MediaRepository.getInstance();

	public MediaEditPanel(JDialog dlg, List<Media> all, TableModel model) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;

		media = new Media();

		type = new JTextArea();
		createPanel();
	}

	public MediaEditPanel(JDialog dlg, List<Media> all, TableModel model,
			Media person) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;
		this.media = person;

		type = new JTextArea(person.getType());

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
		type.setPreferredSize(TEXT_DIM);
		p.add(type);
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

				if (media.getType() != null) {
					repo.delete(media);
					all.remove(media);
				}

				media.setType(type.getText());
				media.setFilms(null);

				if (!all.contains(media)) {
					all.add(media);
					repo.saveOrUpdate(media);
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

		if (media.getType() != null) {
			JButton delete = new JButton("Delete");
			btnPanel.add(delete);
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					repo.delete(media);
					all.remove(media);

					((AbstractTableModel) model).fireTableDataChanged();
					dlg.setVisible(false);
				}
			});

		}

	}

}
