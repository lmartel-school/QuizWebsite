package quiz;

import java.sql.Connection;

public abstract class DataBaseObject {
	
	
	
	/**
	 * Templatized factory constructor. Receives an array of strings from 
	 * a database row, and returns an object with those values. Uses the 
	 * sql connection to parse and create any child objects.
	 * @param args
	 * @return
	 */
	public DataBaseObject(String[] args, Connection conn){}
	
	
	/**
	 * Takes an object that needs to be saved to, or updated in the 
	 * database.
	 * @param obj
	 */
	public abstract void saveToDataBase(Connection conn);

}
