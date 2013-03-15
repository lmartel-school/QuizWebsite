package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MyDB;

/**
 * Servlet implementation class AddCategory
 */
@WebServlet("/AddCategory")
public class AddCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyDB.getConnection();
		String text = request.getParameter("text");
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT MAX(id) from Category;";     
			ResultSet rs = stmt.executeQuery(query);   
			rs.next();
			int id = rs.getInt(1) + 1; 
			query = "INSERT into Category VALUES (" + id + ", '" + text + "');";
			stmt.executeUpdate(query);

		} catch (SQLException e) {     
			e.printStackTrace();
		}
		RequestDispatcher dispatch = request.getRequestDispatcher("AdminServlet");
		dispatch.forward(request, response);
		
	}

}
