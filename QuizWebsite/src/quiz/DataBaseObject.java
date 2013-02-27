package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DataBaseObject {
	protected int dbID;
	
	/**
	 * Constructor. Receives an array of strings from 
	 * a database row, and returns an object with those values. Uses the 
	 * sql connection to parse and create any child objects, as required
	 * by different sub-classes
	 * @param args
	 * @return
	 */
	public DataBaseObject(String[] args, Connection conn){
		dbID = Integer.parseInt(args[0]);
	}
	/**
	 * Alternate constructor: called for new instances. sets ID to -1.
	 */
	public DataBaseObject(){

		dbID = -1;
	}
	
	
	/**
	 * Takes an object that needs to be saved to, or updated in the 
	 * database.
	 * @param obj
	 */
	public abstract void saveToDataBase(Connection conn);
	
	
	
	public void generateID(Connection conn, String table) {
		try {
			Statement stmt = conn.createStatement();      
			String query = "SELECT max(id) from " + table + ";";     
			ResultSet rs = stmt.executeQuery(query);     
			int id = rs.getInt("Max(id)"); //needs to be checked      
			dbID = id + 1;    
		} catch (SQLException e) {     
			e.printStackTrace();
		}
	}

}
