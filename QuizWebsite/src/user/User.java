package user;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DataBaseObject;



public class User extends DataBaseObject {
	
	private String name;
	private String hashedPassword;
	private boolean isAdmin;

	public User(String[] args, Connection conn) {
		super(args, conn);
		//dbID = Integer.parseInt(args[0]);
		name = args[1];
		hashedPassword = args[2];
		isAdmin = Boolean.parseBoolean(args[3]);
	}
	
	public User(String name, String hashedPwd) {
		super();
		this.name = name;
		hashedPassword = hashedPwd;
		isAdmin = false;
	}

	//Writes a query to save this User object back into the database.
	//Use the dbID to check if it it is a new entry or an update.
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "User");
				query = "Insert into User VALUES (" + dbID + ",'" + name + "', '" + hashedPassword + "', " + isAdmin + ");";
			} else {
				query = "UPDATE User set isAdmin=" + isAdmin + "WHERE id=" + dbID + ";";
			}
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

}