package servlets;

import java.sql.*;


public class MessageQueries {
	
	public static void generalMessage(String[] attrs, ResultSet rs) {
		try {
			attrs[0] = rs.getString("id");
			attrs[1] = rs.getString("sender");
			attrs[2] = rs.getString("recipient");
			attrs[3] = rs.getString("beenRead");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


}
