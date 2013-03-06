package quiz;

public class Activity {
	
	private User user;
	private String message;
	
	public Activity(User user, String text) {
		this.user = user;
		message = text;
	}

	
	public User getUser() {
		return user;
	}
	
	public String getActivity() {
		return message;
	}
}
