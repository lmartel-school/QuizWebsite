import java.sql.Connection;

import quiz.DataBaseObject;


public class User extends DataBaseObject {
	String name;
	String hashedPassword;
	boolean isAdmin;
	int dbID;

	public User(String[] args, Connection conn) {
		super(args, conn);
		dbID = Integer.parseInt(args[0]);
		name = args[1];
		hashedPassword = args[2];
		isAdmin = Boolean.parseBoolean(args[3]);
	}

	public void saveToDataBase(Connection conn) {
		// TODO Auto-generated method stub
		
	}

}
