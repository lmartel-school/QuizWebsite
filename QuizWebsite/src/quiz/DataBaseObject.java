package quiz;

import java.sql.Connection;

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
<<<<<<< HEAD
	
	public DataBaseObject() {
=======
	/**
	 * Alternate constructor: called for new instances. sets ID to -1.
	 */
	public DataBaseObject(){
>>>>>>> c1dcfd1b5b64ef8ac27bef76407962db8b81cd31
		dbID = -1;
	}
	
	
	/**
	 * Takes an object that needs to be saved to, or updated in the 
	 * database.
	 * @param obj
	 */
	public abstract void saveToDataBase(Connection conn);

}
