SELECT hasagenre.genreid, count(movieid)
INTO temp1
FROM hasagenre
GROUP BY hasagenre.genreid;

CREATE TABLE query1(
name,
moviecount)
AS SELECT genres.name, temp1.count
FROM genres
INNER JOIN temp1
ON temp1.genreid = genres.genreid;

---------------------------------------------------

SELECT ratings.rating, ratings.movieid, hasagenre.genreid
INTO temp2
FROM hasagenre
INNER JOIN ratings
ON ratings.movieid = hasagenre.movieid;

SELECT avg(temp2.rating), temp2.genreid
INTO temp22
FROM temp2
GROUP BY temp2.genreid;

CREATE TABLE query2(
name,
rating)
AS SELECT genres.name, temp22.avg
FROM genres
INNER JOIN temp22
ON genres.genreid = temp22.genreid;

---------------------------------------------------

SELECT ratings.movieid, count(userid)
INTO temp3
FROM ratings
GROUP BY ratings.movieid;

CREATE TABLE query3(title, countofratings)
AS SELECT movies.title, temp3.count 
FROM movies
INNER JOIN temp3
ON temp3.movieid = movies.movieid AND temp3.count >=10;

---------------------------------------------------

SELECT hasagenre.movieid, hasagenre.genreid
INTO temp4
FROM hasagenre
WHERE hasagenre.genreid IN
(
    SELECT genres.genreid
    FROM genres
    WHERE name = 'Comedy'
);


CREATE TABLE query4 (movieid, title) 
AS SELECT movies.movieid, movies.title
FROM movies
INNER JOIN temp4
ON temp4.movieid = movies.movieid;

---------------------------------------------------

SELECT ratings.movieid, avg(rating) 
INTO temp5
FROM ratings
GROUP BY ratings.movieid;

CREATE TABLE query5 (title, average) 
AS SELECT movies.title, temp5.avg
FROM movies
INNER JOIN temp5
ON temp5.movieid = movies.movieid;


CREATE TABLE query6 (average) 
AS SELECT query2.rating
FROM query2
WHERE query2.name = 'Comedy';

---------------------------------------------------


SELECT hasagenre.movieid, hasagenre.genreid
INTO temp71
FROM hasagenre
where genreid IN (
	SELECT genres.genreid
	FROM genres
	WHERE name = 'Comedy'
);

SELECT hasagenre.movieid, hasagenre.genreid
INTO temp72
FROM hasagenre
WHERE genreid IN(
	SELECT genres.genreid
	FROM genres
	WHERE genres.name = 'Romance'
	);

SELECT temp71.movieid
INTO temp77
FROM temp71
INTERSECT
select temp72.movieid
FROM temp72;

CREATE TABLE query7(
average)
AS SELECT avg(ratings.rating)
FROM ratings 
INNER JOIN temp77
ON ratings.movieid = temp77.movieid;

---------------------------------------------------

SELECT temp72.movieid
INTO temp87
FROM temp72
EXCEPT
select temp71.movieid
FROM temp71;


CREATE TABLE query8 (average) 
AS SELECT avg(rating)
FROM ratings
INNER JOIN temp87
ON ratings.movieid = temp87.movieid;


---------------------------------------------------

CREATE TABLE query9 (movieid, rating) 
AS SELECT ratings.movieid, ratings.rating
FROM ratings
WHERE ratings.userid = :v1;

---------------------------------------------------------------------------

SELECT movieid, avg(rating) 
INTO temp101
FROM ratings
GROUP BY movieid;


CREATE TABLE temp102 AS TABLE temp101;



CREATE TABLE SIM1 (movieid1, movieid2, sim, rating, title) 
AS SELECT temp101.movieid, temp102.movieid, (1 - (abs(temp101.avg - temp102.avg) / 5)), query9.rating, movies.title
FROM temp101, temp102, query9, movies
WHERE temp101.movieid NOT IN (SELECT movieid FROM query9)
	AND temp101.movieid = movies.movieid 
	AND temp102.movieid IN (SELECT movieid FROM query9)
	AND temp102.movieid = query9.movieid;

CREATE TABLE recommendation (title) 
AS SELECT SIM1.title
FROM SIM1
GROUP BY title, movieid1
HAVING (sum (SIM1.sim * SIM1.rating) / sum (SIM1.sim)) > 3.9;