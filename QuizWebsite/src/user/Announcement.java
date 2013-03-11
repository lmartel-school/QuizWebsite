package user;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.*;

public class Announcement extends DataBaseObject {
	
	private String text;
	
	public Announcement(String[] attrs, Connection conn) {
		dbID = Integer.parseInt(attrs[0]);
		text = attrs[1];
	}
	
	public Announcement(String text) {
		this.text = text;
	}

	@Override
	public void saveToDataBase(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Announcement");
				query = "Insert into Announcement VALUES ('" + dbID + "', '" + text + "');";
				stmt.executeUpdate(query);
			} else {
				query = "UPDATE Announcement set text='"+ text +"' WHERE id='" + dbID + "';";
				stmt.executeUpdate(query);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getMsg() {
		return text;
	}

}
