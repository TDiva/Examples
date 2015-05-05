set @genre = 'Романтика';
select film.* from lab_1.films film left join lab_1.genre_rate genre on (film.id = genre.film_id)  where genre.level >=3 and genre.genre = @genre;

select * from lab_1.films film left join lab_1.genre_rate genre on (film.id = genre.film_id)  where genre.level >=3 and genre.genre = @genre;