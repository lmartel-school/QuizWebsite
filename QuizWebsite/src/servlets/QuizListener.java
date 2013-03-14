package servlets;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

import database.MyDB;



/**
 * Application Lifecycle Listener implementation class QuizListener
 *
 */
@WebListener
public class QuizListener implements ServletContextListener, HttpSessionListener {

    /**
     * Default constructor. 
     */
    public QuizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	ServletContext context = arg0.getServletContext();
    	
    	con = MyDB.getConnection();
        
        context.setAttribute("database", con);
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     * 
     * I figure that the necessary parts of a session are stuff that "bars" a user
     * from certain sections of the site. As well, we will probably have to store
     * information later about a particular quiz they are taking...
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	HttpSession session = arg0.getSession();    	
    	
    	session.setAttribute("user", null);
        session.setAttribute("currentQuiz", null);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
        // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private Connection con;
	
}
