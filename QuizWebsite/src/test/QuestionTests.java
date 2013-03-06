package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import question.Question;
import quiz.MyDB;

public class QuestionTests {
	
	private static final int NUM_COLUMNS = 4;
	
	@Test
	public void retrieveFromDBTest(){
		List<Question> questions = new ArrayList<Question>();
		try {
			Connection conn = MyDB.getConnection();
			Statement stmt = conn.createStatement();      
			String query = "SELECT * from Questions;"; 
			ResultSet rs = stmt.executeQuery(query);     
			while (rs.next()) {
				String[] attrs = new String[NUM_COLUMNS];
				for (int i = 0; i < attrs.length; i++) {
					attrs[i] = rs.getString(i + 1);
				}

				questions.add(Question.build(attrs, conn));
			}
			
		} catch (SQLException e) {     
			e.printStackTrace();
		}
		for(int i = 0; i < questions.size(); i++){
			System.out.println("question " + i + ": quiz_id=" + questions.get(i).getQuizID() + ", question_number=" + questions.get(i).getQuestionNumber() + ", question_type=" + questions.get(i).getType());
		}
	}
}
