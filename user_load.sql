USE c_cs108_jfneff;

DROP TABLE if exists User;

create table User (
	id integer unique,
	username CHAR(64) PRIMARY KEY,
	password_hash text,
	isAdmin boolean
);

DROP TABLE if exists Message;

create table Message (
	id integer PRIMARY KEY,
	sender text references User(username),
	recipient text references User(username),
	beenRead boolean
);

DROP TABLE if exists Note;

create table Note (
	id integer PRIMARY KEY,
	msg text
);

DROP TABLE if exists Challenge;

create table Challenge (
	id integer PRIMARY KEY,
	result_id integer references Quiz_Result(id)
);

DROP TABLE if exists Friend_Request;

create table Friend_Request (
	id integer PRIMARY KEY,
	isAccepted boolean
); 

DROP TABLE if exists Announcement;

create table Announcement (
	id integer PRIMARY KEY,
	msg text
);

DROP TABLE if exists Achievement;

create table Achievement (
	id integer PRIMARY KEY,
	username text references User(username),
	award text
);

DROP TABLE if exists Tag;

create table Tag (
	id integer PRIMARY KEY,
	quiz_id integer references Quiz(id),
	tag text
);

