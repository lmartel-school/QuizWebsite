USE c_cs108_jfneff;

DROP TABLE if exists User;

create table User (
	id integer unique,
	username text PRIMARY KEY,
	password_hash text,
	isAdmin boolean
);

DROP TABLE if exists Friendship;

create table Friendship (
	id integer unique,
	req_user text,
	acc_user text
);

DROP TABLE if exists Message;

create table Message (
	id integer PRIMARY KEY,
	sender text references User(username),
	recipient text references User(username),
	type integer,
	beenRead boolean
);

DROP TABLE if exists Note;

create table Note (
	id integer PRIMARY KEY,
	message_id integer references Message(id),
	msg text
);

DROP TABLE if exists Challenge;

create table Challenge (
	id integer PRIMARY KEY,
	message_id integer references Message(id),
	quiz_id integer,
	result_id integer
);

/* DROP TABLE if exists friend_requests;

create table friend_requests (
	id integer
); */

