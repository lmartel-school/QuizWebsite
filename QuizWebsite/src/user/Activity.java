package user;


public class Activity {
	
	private User user;
	private String message;
	private int quizId;
	
	public Activity(User user, String text, int quiz) {
		this.user = user;
		message = text;
		quizId = quiz;
	}

	
	public User getUser() {
		return user;
	}
	
	public String getActivity() {
		return message;
	}
	
	public int getQuizID() {
		return quizId;
	}
}
