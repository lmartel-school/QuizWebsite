package quiz;

import java.sql.Connection;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;


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
    	
    	Connection con = MyDB.getConnection();
        
        context.setAttribute("database", con);
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	HttpSession session = arg0.getSession();
        
        //ShoppingCart cart = new ShoppingCart();
        //session.setAttribute("cart", cart);
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
        // TODO Auto-generated method stub
    }
	
}
