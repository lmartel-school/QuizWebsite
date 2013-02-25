package quiz;

import java.sql.Connection;

public class FriendRequest extends Message {
	private boolean isAccepted;

	public FriendRequest(String[] args, Connection conn) {
		super(args, conn);
		
		//TODO set isAccepted from FriendReq table
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
	public void acceptRequest() {
		isAccepted = true;
	}
	@Override
	public void saveToDataBase(Connection conn) {
		//TODO: Save this request in the messages and FriendReq tables
	}

}
