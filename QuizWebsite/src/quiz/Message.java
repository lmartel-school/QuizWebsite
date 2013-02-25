package quiz;

import java.sql.Connection;

public abstract class Message extends DataBaseObject {
	protected String sender;
	protected String recipient;
	protected boolean beenRead;
	
	//Constructor from db row
	public Message(String[] args, Connection conn) {
		super(args, conn);
		sender = args[1];
		recipient = args[2];
		beenRead = Boolean.parseBoolean(args[4]);
	}
	
	//Constructor of new Message
	public Message(String sender, String recipient) {
		this.sender = sender;
		this.recipient = recipient;
		beenRead = false;
		
	}
	public String getSender() {
		return sender;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	

}
