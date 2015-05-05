package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import repo.FilmRepository;
import repo.GenreRepository;
import repo.MediaRepository;
import repo.PersonRepository;
import entity.Film;
import entity.FilmGenre;
import entity.FilmGenreId;
import entity.FilmMedia;
import entity.FilmMediaId;
import entity.Genre;
import entity.Media;
import entity.Person;

public class FilmEditPanel extends JPanel {

	private static final long serialVersionUID = -5130394063778269836L;
	
	public static final Dimension LABEL_DIM = new Dimension(150, 20);
	public static final Dimension TEXT_DIM = new Dimension(300, 20);
	public static final Dimension TEXT_DIM_2 = new Dimension(140, 20);

	public static final Person EMPTY_PERSON = new Person(-1l);
	public static final Genre EMPTY_GENRE = new Genre("не указан");
	public static final Media EMPTY_MEDIA = new Media("не указан");

	private JDialog dlg;
	private List<Film> all;
	private TableModel model;

	private Film film;

	private JTextArea originalName;
	private JTextArea russianName;
	private JTextArea year;
	private JTextArea country;

	private JPanel actors;
	private JPanel directors;
	private JPanel genres;
	private JPanel media;

	private FilmRepository repo = FilmRepository.getInstance();

	private PersonRepository personRepository = PersonRepository.getInstance();
	private GenreRepository genreRepository = GenreRepository.getInstance();
	private MediaRepository mediaRepository = MediaRepository.getInstance();

	private List<Person> allPersons = personRepository.getAll();
	private List<JComboBox<Person>> selectedActors = new ArrayList<>();
	private List<JComboBox<Person>> selectedDirectors = new ArrayList<>();

	private List<Genre> allGenres = genreRepository.getAll();
	private List<JComboBox<Genre>> selectedGenres = new ArrayList<>();
	private List<JComboBox<Long>> selectedGenresLevel = new ArrayList<>();

	private List<Media> allMedia = mediaRepository.getAll();
	private List<JComboBox<Media>> selectedMedia = new ArrayList<>();
	private List<JTextArea> selectedMediaLevel = new ArrayList<>();

	public FilmEditPanel(JDialog dlg, List<Film> all, TableModel model) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;

		film = new Film();

		originalName = new JTextArea();
		russianName = new JTextArea();
		year = new JTextArea();
		country = new JTextArea();

		allPersons.add(EMPTY_PERSON);
		actors = createActorsPanel(new HashSet<Person>());
		directors = createDirectorsPanel(new HashSet<Person>());
		genres = createGenrePanel(new HashSet<FilmGenre>());
		media = createMediaPanel(new HashSet<FilmMedia>());

		createPanel();
	}

	public FilmEditPanel(JDialog dlg, List<Film> all, TableModel model,
			Film film) {
		this.dlg = dlg;
		this.all = all;
		this.model = model;
		this.film = film;

		originalName = new JTextArea(film.getOriginalName());
		russianName = new JTextArea(film.getRussianName());
		year = new JTextArea(film.getYear().toString());
		country = new JTextArea(film.getCountry());

		allPersons.add(EMPTY_PERSON);
		actors = createActorsPanel(film.getActors());
		directors = createDirectorsPanel(film.getDirectors());
		genres = createGenrePanel(film.getGenres());
		media = createMediaPanel(film.getMedia());

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

		p = new JPanel();
		lbl = new JLabel("Год выхода: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		year.setPreferredSize(TEXT_DIM);
		p.add(year);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Страна: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		country.setPreferredSize(TEXT_DIM);
		p.add(country);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Актеры: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		p.add(actors);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Режиссеры: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		p.add(directors);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Жанр: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		p.add(genres);
		selectPanel.add(p);

		p = new JPanel();
		lbl = new JLabel("Доступны: ");
		lbl.setPreferredSize(LABEL_DIM);
		p.setLayout(new FlowLayout());
		p.add(lbl);
		p.add(media);
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

				film.setOriginalName(originalName.getText());
				film.setRussianName(russianName.getText());
				film.setYear(Integer.parseInt(year.getText()));
				film.setCountry(country.getText());

				Set<Person> actors = new HashSet<>();
				for (JComboBox<Person> item : selectedActors) {
					Person p = (Person) item.getSelectedItem();
					if (!p.equals(EMPTY_PERSON)) {
						actors.add(p);
					}
				}
				film.setActors(actors);

				Set<Person> directors = new HashSet<>();
				for (JComboBox<Person> item : selectedDirectors) {
					Person p = (Person) item.getSelectedItem();
					if (!p.equals(EMPTY_PERSON)) {
						directors.add(p);
					}
				}
				film.setDirectors(directors);

				Set<FilmGenre> genres = new HashSet<>();
				for (int i = 0; i < selectedGenres.size(); i++) {
					Genre g = (Genre) selectedGenres.get(i).getSelectedItem();
					Long l = (Long) selectedGenresLevel.get(i)
							.getSelectedItem();
					if (!g.equals(EMPTY_GENRE)) {
						FilmGenreId id = new FilmGenreId(film, g);
						genres.add(new FilmGenre(id, l));
					}
				}
				film.setGenres(genres);

				Set<FilmMedia> medias = new HashSet<>();
				for (int i = 0; i < selectedMedia.size(); i++) {
					Media m = (Media) selectedMedia.get(i).getSelectedItem();
					String l = selectedMediaLevel.get(i).getText();
					try {
						Long level = Long.parseLong(l);
						if (!m.equals(EMPTY_GENRE)) {
							FilmMediaId id = new FilmMediaId(film, m);
							medias.add(new FilmMedia(id, level));
						}
					} catch (NumberFormatException e) {
					}
				}
				film.setMedia(medias);

				if (film.getId() != null) {
					repo.deleteGenreRate(film.getId());
					repo.deleteMedia(film.getId());
				}

				repo.saveOrUpdate(film);

				if (!all.contains(film)) {
					all.add(film);
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

		if (film.getId() != null) {
			JButton delete = new JButton("Delete");
			btnPanel.add(delete);
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent ae) {
					repo.delete(film);
					all.remove(film);

					((AbstractTableModel) model).fireTableDataChanged();
					dlg.setVisible(false);
				}
			});

		}

	}

	private JPanel createActorsPanel(Set<Person> items) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		final JPanel mainPanel = new JPanel();
		panel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		for (Person p : items) {
			JComboBox<Person> c = new JComboBox<>(
					new Vector<Person>(allPersons));
			c.setPreferredSize(TEXT_DIM);
			c.setSelectedIndex(allPersons.indexOf(p));
			mainPanel.add(c);
			selectedActors.add(c);
		}

		JComboBox<Person> c = new JComboBox<>(new Vector<Person>(allPersons));
		c.setPreferredSize(TEXT_DIM);
		c.setSelectedIndex(allPersons.size() - 1);
		mainPanel.add(c);
		selectedActors.add(c);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.EAST);
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(new JLabel(""));

		JButton add = new JButton("Add");
		btnPanel.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox<Person> c = new JComboBox<>(new Vector<Person>(
						allPersons));
				c.setSelectedIndex(allPersons.size() - 1);
				c.setPreferredSize(TEXT_DIM);
				mainPanel.add(c);
				selectedActors.add(c);
				panel.revalidate();
				dlg.pack();
			}
		});

		return panel;
	}

	private JPanel createDirectorsPanel(Set<Person> items) {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		final JPanel mainPanel = new JPanel();
		panel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		for (Person p : items) {
			JComboBox<Person> c = new JComboBox<>(
					new Vector<Person>(allPersons));
			c.setPreferredSize(TEXT_DIM);
			c.setSelectedIndex(allPersons.indexOf(p));
			mainPanel.add(c);
			selectedDirectors.add(c);
		}

		JComboBox<Person> c = new JComboBox<>(new Vector<Person>(allPersons));
		c.setPreferredSize(TEXT_DIM);
		c.setSelectedIndex(allPersons.size() - 1);
		mainPanel.add(c);
		selectedDirectors.add(c);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.EAST);
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(new JLabel(""));

		JButton add = new JButton("Add");
		btnPanel.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JComboBox<Person> c = new JComboBox<>(new Vector<Person>(
						allPersons));
				c.setSelectedIndex(allPersons.size() - 1);
				c.setPreferredSize(TEXT_DIM);
				mainPanel.add(c);
				selectedDirectors.add(c);
				panel.revalidate();
				dlg.pack();
			}
		});

		return panel;
	}

	private JPanel createGenrePanel(Set<FilmGenre> items) {
		allGenres.add(EMPTY_GENRE);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		final JPanel mainPanel = new JPanel();
		panel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		final Long[] levels = new Long[] { 1l, 2l, 3l, 4l, 5l };

		for (FilmGenre g : items) {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			mainPanel.add(p);

			JComboBox<Genre> c = new JComboBox<>(new Vector<Genre>(allGenres));
			c.setPreferredSize(TEXT_DIM_2);
			c.setSelectedIndex(allGenres.indexOf(g.getGenre()));
			p.add(c);
			selectedGenres.add(c);

			JComboBox<Long> l = new JComboBox<Long>(levels);
			l.setSelectedIndex((int) (g.getLevel() - 1));
			l.setPreferredSize(TEXT_DIM_2);
			p.add(l);
			selectedGenresLevel.add(l);
		}

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		mainPanel.add(p);

		JComboBox<Genre> c = new JComboBox<>(new Vector<Genre>(allGenres));
		c.setSelectedIndex(allGenres.size() - 1);
		c.setPreferredSize(TEXT_DIM_2);
		p.add(c);
		selectedGenres.add(c);

		JComboBox<Long> l = new JComboBox<Long>(levels);
		l.setSelectedIndex(0);
		l.setPreferredSize(TEXT_DIM_2);
		p.add(l);
		selectedGenresLevel.add(l);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.EAST);
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(new JLabel(""));

		JButton add = new JButton("Add");
		btnPanel.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JPanel p = new JPanel();
				p.setLayout(new FlowLayout());
				mainPanel.add(p);

				JComboBox<Genre> c = new JComboBox<>(new Vector<Genre>(
						allGenres));
				c.setSelectedIndex(allGenres.size() - 1);
				c.setPreferredSize(TEXT_DIM_2);
				p.add(c);
				selectedGenres.add(c);

				JComboBox<Long> l = new JComboBox<Long>(levels);
				l.setSelectedIndex(0);
				l.setPreferredSize(TEXT_DIM_2);
				p.add(l);
				selectedGenresLevel.add(l);

				panel.revalidate();
				dlg.pack();
			}
		});

		return panel;
	}

	private JPanel createMediaPanel(Set<FilmMedia> items) {
		allMedia.add(EMPTY_MEDIA);
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		final JPanel mainPanel = new JPanel();
		panel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		for (FilmMedia m : items) {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			mainPanel.add(p);

			JComboBox<Media> c = new JComboBox<>(new Vector<Media>(allMedia));
			c.setSelectedIndex(allMedia.indexOf(m.getMedia()));
			c.setPreferredSize(TEXT_DIM_2);
			p.add(c);
			selectedMedia.add(c);

			JTextArea q = new JTextArea();
			q.setText(m.getQuantity().toString());
			q.setPreferredSize(TEXT_DIM_2);
			p.add(q);
			selectedMediaLevel.add(q);
		}

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		mainPanel.add(p);

		JComboBox<Media> c = new JComboBox<>(new Vector<Media>(allMedia));
		c.setSelectedIndex(allMedia.size() - 1);
		c.setPreferredSize(TEXT_DIM_2);
		p.add(c);
		selectedMedia.add(c);

		JTextArea q = new JTextArea();
		q.setPreferredSize(TEXT_DIM_2);
		p.add(q);
		selectedMediaLevel.add(q);

		JPanel btnPanel = new JPanel();
		panel.add(btnPanel, BorderLayout.EAST);
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(new JLabel(""));

		JButton add = new JButton("Add");
		btnPanel.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JPanel p = new JPanel();
				p.setLayout(new FlowLayout());
				mainPanel.add(p);

				JComboBox<Media> c = new JComboBox<>(
						new Vector<Media>(allMedia));
				c.setSelectedIndex(allMedia.size() - 1);
				c.setPreferredSize(TEXT_DIM_2);
				p.add(c);
				selectedMedia.add(c);

				JTextArea q = new JTextArea();
				q.setPreferredSize(TEXT_DIM_2);
				p.add(q);
				selectedMediaLevel.add(q);

				panel.revalidate();
				dlg.pack();
			}
		});

		return panel;
	}
}
