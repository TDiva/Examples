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

import repo.PersonRepository;
import entity.Person;

public class PersonEditPanel extends JPanel {

	private static final long serialVersionUID = 984577010126984528L;
	
	public static final Dimension LABEL_DIM = new Dimension(150, 20);
	public static final Dimension TEXT_DIM = new Dimension(300, 20);

	private JDialog dlg;
	private List<Person> all;
	private TableModel model;

	private Person person;

	private JTextArea originalName;
	private JTextArea russianName;

	private PersonRepository repo = PersonRepository.getInstance();

	public PersonEditPanel(JDialog dlg, List<Person> all, TableModel model) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;

		person = new Person();

		originalName = new JTextArea();
		russianName = new JTextArea();

		createPanel();
	}

	public PersonEditPanel(JDialog dlg, List<Person> all, TableModel model,
			Person person) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;
		this.person = person;

		originalName = new JTextArea(person.getOriginalName());
		russianName = new JTextArea(person.getRussianName());

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
		originalName.setPreferredSize(TEXT_DIM);
		p.add(originalName);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Русское имя: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		russianName.setPreferredSize(TEXT_DIM);
		p.add(russianName);
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

				person.setOriginalName(originalName.getText());
				person.setRussianName(russianName.getText());

				repo.saveOrUpdate(person);
				if (!all.contains(person)) {
					all.add(person);
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

		if (person.getId() != null) {
			JButton delete = new JButton("Delete");
			btnPanel.add(delete);
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					repo.delete(person);
					all.remove(person);
					
					((AbstractTableModel) model).fireTableDataChanged();
					dlg.setVisible(false);
				}
			});

		}

	}

}
