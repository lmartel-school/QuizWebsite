package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.MyDB;

import question.AttributeMap;
import question.FillInQuestion;
import question.MultiChoiceQuestion;
import question.PictureQuestion;
import question.Question;
import question.QuestionAttribute;
import quiz.Quiz;
import quiz.QuizConstants;

public class QuestionTests {
	
	private static Quiz quiz;
	
	@BeforeClass
	public static void setup(){
		quiz = new Quiz("My quiz", true, 0, "Me", "This quiz best quiz");
	}
	
	//@Test
	public void retrieveFromDBTest(){
		List<Question> questions = new ArrayList<Question>();
		try {
			Connection conn = MyDB.getConnection();
			Statement stmt = conn.createStatement();      
			String query = "SELECT * from Question;"; 
			ResultSet rs = stmt.executeQuery(query);     
			while (rs.next()) {
				String[] attrs = new String[QuizConstants.QUESTION_ATTRIBUTE_N_COLS];
				for (int i = 0; i < attrs.length; i++) {
					attrs[i] = rs.getString(i + 1);
				}

				questions.add(Question.build(attrs, conn));
			}
			
		} catch (SQLException e) {     
			e.printStackTrace();
		}
		for(int i = 0; i < questions.size(); i++){
			printQuestion(questions.get(i));
		}
	}
	
	@Test
	public void creationTestMC(){
		String p = "MC prompt!";
		List<String> c = new ArrayList<String>();
		c.add("correct answer 1");
		c.add("correct answer 2");
		List<String> w = new ArrayList<String>();
		w.add("wrong answer 1");
		w.add("wrong answer 2");
		w.add("wrong answer 3");
		Question mc = new MultiChoiceQuestion(quiz, quiz.countQuestions() + 1, p, c, w);
		quiz.addQuestion(mc);
		assertEquals(true, mc.checkAnswer(c.get(1)));
		assertEquals(false, mc.checkAnswer(w.get(2)));
	}
	
	@Test
	public void creationTestFillIn(){
		String p = "FillIn prompt!";
		String a = "FillIn answer of extreme correctness";
		Question f = new FillInQuestion(quiz, quiz.countQuestions() + 1, p, a);
		quiz.addQuestion(f);
		assertEquals(true, f.checkAnswer(new String(a)));
		assertEquals(false, f.checkAnswer("I DON'T KNOWWWW AAAAHHH"));
	}
	
	@Test
	public void creationTestPic(){
		String u = "http://picture.url";
		String p = "Picture text prompt homie";
		String a = "Picture answer most excellent";
		Question pq = new PictureQuestion(quiz, quiz.countQuestions() + 1, u, p, a);
		quiz.addQuestion(pq);
		assertEquals(true, pq.checkAnswer(new String(a)));
		assertEquals(false, pq.checkAnswer("I DON'T KNOWWWW AAAAHHH"));
	}
	
	@AfterClass
	public static void printAllQuestions(){
		quiz.saveToDataBase(MyDB.getConnection());
		System.out.println("quiz: id=" + quiz.getID() + ", name=" + quiz.getName() + ", inOrder=" + quiz.isInOrder() + ", pageType=" + quiz.getType() + ", author=" + quiz.getAuthor() + ", description=" + quiz.getDescription());
		for(Question q : quiz.getQuestions()){
			printQuestion(q);
		}
	}
	
	/**
	 * Prints the values of Question q's fields, and the values of all its attributes 
	 * @param q
	 */
	private static void printQuestion(Question q){
		System.out.println("question: id=" + q.getID() + ", quiz_id=" + q.getQuizID() + ", question_number=" + q.getQuestionNumber() + ", question_type=" + q.getType());
		AttributeMap attrs = q.getAttrs();
		for(QuestionAttribute a : attrs.getAll()){
			System.out.println("has attribute with question_id=" + a.getQuestion_id() + ", attr_type=" + a.getAttrType() + ", attr_value=" + a.getAttrValue());
		}
	}
	
	
}
