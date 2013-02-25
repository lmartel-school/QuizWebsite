package quiz;
import java.sql.Connection;

import quiz.DataBaseObject;


public class User extends DataBaseObject {
	String name;
	String hashedPassword;
	boolean isAdmin;

	public User(String[] args, Connection conn) {
		super(args, conn);
		dbID = Integer.parseInt(args[0]);
		name = args[1];
		hashedPassword = args[2];
		isAdmin = Boolean.parseBoolean(args[3]);
	}

	public void saveToDataBase(Connection conn) {
		
		//Write a query to save this User object back into the database.
		//Use the dbID to check if it it is a new entry or an update.
		
	}

}
