
USE c_cs108_jfneff;

DROP TABLE if exists Achievement;

create table Achievement (
	id integer PRIMARY KEY,
	name text,
	description text,
	requirement text
);

DROP TABLE if exists Achievement_Record;

create table Achievement_Record (
	username text references User(username),
	time text,
	achievement_id integer references Achievement(id)
);