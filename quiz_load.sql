
USE c_cs108_jfneff;

DROP TABLE if exists Quiz;

create table Quiz (
	id integer PRIMARY KEY,
	name text,
	inOrder boolean,
	pageType integer, /* single page, multi immediate, multi at end */
	author text references User(username),
	description text
);

DROP TABLE if exists Question;

create table Question (
	id integer PRIMARY KEY,
	quiz_id integer references Quiz(id),
	question_number integer,
	question_type integer /* multi-choice, fill-in/response, pic */
);

DROP TABLE if exists Question_Attribute;

create table Question_Attribute (
	id integer PRIMARY KEY,
	question_id integer references Question(id),
	attr_type text, 
	attr_value text /* "correct", "wrong", "prompt" */
);

DROP TABLE if exists Quiz_Result;

create table Quiz_Result (
	id integer PRIMARY KEY,
	username text references User(username),
	score integer,
	time_taken text,
	elapsed_time text,
	quiz_id integer references Quiz(id)
	
);


