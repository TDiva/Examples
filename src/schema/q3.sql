select actor.* from lab_1.actors actor inner join lab_1.films_directors direct on (actor.id = direct.id);

select p.* from lab_1.actors inner join lab_1.films_actors a on (p.id = a.actors_id) inner join lab_1.films_directors d on (p.id = d.directors_id);