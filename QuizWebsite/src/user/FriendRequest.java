package user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FriendRequest extends Message {
	private boolean isAccepted;

	public FriendRequest(String[] args, Connection conn) {
		super(args, conn);
		isAccepted = Boolean.parseBoolean(args[4]); //accepted field was joined onto this db row (1-to-1 relationship)
	}
	
	public FriendRequest(String sender, String recipient) {
		super(sender, recipient);
		//Friend requests cannot be approved at the creation of the request,
		//only once the recipient has accepted. 
		isAccepted = false;
	}
	
	public boolean getStatus() {
		return isAccepted;
	}
	
	/* Friends requests can only be accepted, if one user removes someone from
	 * thier friend's list then the message will be deleted entirely, and m
	 */
	public void acceptRequest(Connection conn) {
		isAccepted = true;
		saveToDataBase(conn);
	}
	
	public void rejectRequest(Connection conn) {
		isAccepted = false;
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE from Friend_Request WHERE id='" + dbID + "';");
			stmt.executeUpdate("DELETE from Message WHERE id='" + dbID + "';");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	@Override
	public void saveToDataBase(Connection conn) {
		saveToFriends(conn);
		saveToMsg(conn);
		
	}
	
	
	/** 
	 * This db query assumes that there already exists an 
	 * entry in the friend request table for this id. PLEASE VERIFY
	 */
	private void getAcceptance(String id, Connection conn) {
		try {
			Statement stmt = conn.createStatement();      
			String query = "SELECT isAccepted from Friend_Request WHERE id=" + id + ";";     
			
			ResultSet rs = stmt.executeQuery(query);     
			isAccepted = rs.getBoolean("isAccepted");	      
			   
		} catch (SQLException e) {     
			e.printStackTrace();
		}
	}
	
	
	private void saveToFriends(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			String query;
			if (dbID == -1) {
				generateID(conn, "Message");
				query = "Insert into Friend_Request VALUES (" + dbID + ", " + isAccepted + ");";
				stmt.executeUpdate(query);
			} else {
				query = "UPDATE Friend_Request set isAccepted=" + isAccepted + " WHERE id=" + dbID + ";";
				stmt.executeUpdate(query);
			}
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getHTMLSummary() {
		return sender + " wants to be your friend.";
	}

}
