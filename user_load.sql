USE c_cs108_jfneff;

DROP TABLE if exists User;

create table User (
	id integer unique,
	username CHAR(64) PRIMARY KEY,
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
	message_id integer references Message(id),
	quiz_id integer,
	result_id integer
);

DROP TABLE if exists Friend_Request;

create table Friend_Request (
	id integer PRIMARY KEY,
	isAccepted boolean
); 

