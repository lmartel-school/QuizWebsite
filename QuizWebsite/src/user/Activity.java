package user;


public class Activity {
	
	private User user;
	private String msg;
	private String link;
	private int id;
	boolean authored;
	
	public Activity(User user, int id, boolean isAuthoring, String quiz) {
		this.user = user;
		if (isAuthoring) {
			msg = user.getName() + " authored a quiz: <a href=\"QuizServlet?id=" + id + "\">" + quiz + "</a> \n ";
			link = "<a href=\"UserProfileServlet?username=" + user.getName() + "\">" + user.getName() + "'s Profile Page" + "</a>";
		} else {
			msg = user.getName() + " recently took a quiz \n "; 
			link = "<a href=\"QuizResultServlet?id=" + id + "\">See the Result</a>";
		}
		this.id = id;
		authored = isAuthoring;
	}

	public boolean isAuthoring() {
		return authored;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public String getLink() {
		return link;
	}
	
	public int getQuizID() {
		return id;
	}
}
