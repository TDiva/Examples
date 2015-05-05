
CREATE TABLE Actors
(
	id                   INTEGER NOT NULL,
	russian_name         VARCHAR(100) NOT NULL,
	original_name        VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);



CREATE TABLE Directors
(
	id                   INTEGER NOT NULL,
	original_name        VARCHAR(100) NOT NULL,
	russian_name         VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);



CREATE TABLE Films
(
	id                   INTEGER NOT NULL,
	russian_name         VARCHAR(100) NOT NULL,
	original_name        VARCHAR(100) NULL,
	year                 INTEGER NOT NULL CHECK ( year BETWEEN 1895 AND YEAR(NOW()) ),
	country              VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);



CREATE TABLE Films_Actors
(
	film_id              INTEGER NOT NULL,
	actor_id             INTEGER NOT NULL,
	PRIMARY KEY (film_id,actor_id),
	FOREIGN KEY (film_id) REFERENCES Films (id) ON DELETE CASCADE,
	FOREIGN KEY (actor_id) REFERENCES Actors (id) ON DELETE CASCADE
);



CREATE TABLE Films_Directors
(
	film_id              INTEGER NOT NULL,
	director_id          INTEGER NOT NULL,
	PRIMARY KEY (film_id,director_id),
	FOREIGN KEY (film_id) REFERENCES Films (id) ON DELETE CASCADE,
	FOREIGN KEY (director_id) REFERENCES Actors (id) ON DELETE CASCADE
);



CREATE TABLE Genre
(
	name                 VARCHAR(100) NOT NULL,
	PRIMARY KEY (name)
);



CREATE TABLE Genre_rate
(
	film_id              INTEGER NOT NULL,
	genre                VARCHAR(100) NOT NULL,
	level                INTEGER NOT NULL CHECK ( level BETWEEN 1 AND 5 ),
	PRIMARY KEY (film_id,genre), 
	FOREIGN KEY (film_id) REFERENCES Films (id) ON DELETE CASCADE,
	FOREIGN KEY (genre) REFERENCES Genre (name) ON DELETE CASCADE
);



CREATE TABLE Media
(
	type                 VARCHAR(100) NOT NULL,
	PRIMARY KEY (type)
);



CREATE TABLE Video
(
	film_id              INTEGER NOT NULL,
	media_type           VARCHAR(100) NOT NULL,
	quantity             INTEGER NOT NULL CHECK ( quantity >= 0 ),
	PRIMARY KEY (film_id,media_type),
	FOREIGN KEY (film_id) REFERENCES Films (id) ON DELETE CASCADE,
	FOREIGN KEY (media_type) REFERENCES Media (type) ON DELETE CASCADE
);


