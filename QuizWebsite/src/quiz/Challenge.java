package quiz;

import java.sql.Connection;

public class Challenge extends Message {
	private QuizResult result;

	public Challenge(String[] args, Connection conn) {
		super(args, conn);
		// TODO set result from ChallengeTable
	}
	
	public Challenge(String sender, String recipient, QuizResult result) {
		super(sender, recipient);
		this.result = result;
	}

	@Override
	public void saveToDataBase(Connection conn) {
		// TODO Save this challenge in the messages table, and the challenges table

	}

}
