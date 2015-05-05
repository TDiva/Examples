package entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Video")
@AssociationOverrides({
		@AssociationOverride(name = "id.film_id", joinColumns = @JoinColumn(name = "film_id")),
		@AssociationOverride(name = "id.media_type", joinColumns = @JoinColumn(name = "media_type")) })
public class FilmMedia {

	@EmbeddedId
	private FilmMediaId id;

	@Column(name = "quantity")
	private Long quantity;

	public FilmMedia() {
	}

	public FilmMedia(FilmMediaId id, Long quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}

	public FilmMediaId getId() {
		return id;
	}

	public void setId(FilmMediaId id) {
		this.id = id;
	}

	@Transient
	public Film getFilm() {
		return getId().getFilm();
	}

	public void setFilm(Film film) {
		getId().setFilm(film);
	}

	@Transient
	public Media getMedia() {
		return getId().getMedia();
	}

	public void setMedia(Media media) {
		getId().setMedia(media);
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FilmMedia) {
			FilmMedia f = (FilmMedia) o;
			return id.equals(f.getId());
		}
		return false;
	}

}
