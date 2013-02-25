package quiz;
import java.sql.*;

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
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn);
				query = "Insert into User VALUES (" + dbID + "'" + name + "', '" + hashedPassword + "', " + isAdmin + ");";
				
			} else {
				query = "UPDATE User set isAdmin=" + isAdmin + "WHERE id=" + dbID;
			}
			
			stmt.executeUpdate(query);
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Write a query to save this User object back into the database.
		//Use the dbID to check if it it is a new entry or an update.
		
	}
	
	private void generateID(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT max(id) from User";
			ResultSet rs = stmt.executeQuery(query);
			int id = rs.getInt("Max(id)"); //needs to be checked
			dbID = id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
}
