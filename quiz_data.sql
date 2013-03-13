
USE c_cs108_jfneff;


 INSERT INTO Quiz VALUES
	(1, "States and Cities", false, 1, "test_1", "Testing you on states and their cities", "Geography");

INSERT INTO Question VALUES
	(1, 1, 1, 0),
	(2, 1, 2, 0),
	(3, 1, 3, 1),
	(4, 1, 4, 2);

INSERT INTO Question_Attribute VALUES 

	(1, 1, "prompt", "What is the capital of California?"),
	(2, 2, "prompt", "What is the largest land mass state?"),
	(3, 3, "prompt", "** is the capital of the United States."),
	(4, 4, "prompt", "http://events.stanford.edu/events/252/25201/Memchu_small.jpg"),
	(5, 1, "wrong", "Los Angeles"),
	(6, 1, "wrong", "San Francisco"),
	(7, 1, "correct", "Sacramento"),
	(8, 2, "wrong", "California"),
	(9, 2, "correct", "Alaska"),
	(10, 2, "wrong", "Idaho"),
	(11, 2, "wrong", "Texas"),
	(12, 3, "correct", "Washington DC"),
	(13, 4, "correct", "Memorial Church");

	
