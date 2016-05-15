package com.feiniaoqy.bishe.dao;

import com.feiniaoqy.bishe.util.ConnectSql;
import com.feiniaoqy.bishe.util.Constants;
import entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用于有关question的数据库操作
 * @author feiniaoqy
 *
 */
public class QuestionDao {
	//insert
	public void insert(Question question){
		String sql = "";
		//先判断type
		if(Constants.QUESTION_TYPE_WENDA==question.getQuestionType())
		{
			sql = "insert into question ("
					+ "questionContent,"
					+ "questionType,"
					+ "questionAnswer,"
					+ "questionCreateDate)"
					+ " values ("
					+"\'"+question.getQuestionContent()+"\',"
					+"\'"+question.getQuestionType()+"\',"
					+"\'"+question.getAnswer()+"\',"
					+"\'"+question.getQuestionCreateDate()
					+ "\')";
		}else{
			sql = "insert into question ("
					+ "questionContent,"
					+ "questionType,"
					+ "questionAnswer,"
					+ "questionAnswerA,"
					+ "questionAnswerB,"
					+ "questionAnswerC,"
					+ "questionAnswerD,"
					+ "questionCreateDate)"
					+ " values ("
					+"\'"+question.getQuestionContent()+"\',"
					+"\'"+question.getQuestionType()+"\',"
					+"\'"+question.getAnswer()+"\',"
					+"\'"+question.getAnswerA()+"\',"
					+"\'"+question.getAnswerB()+"\',"
					+"\'"+question.getAnswerC()+"\',"
					+"\'"+question.getAnswerD()+"\',"
					+"\'"+question.getQuestionCreateDate()
					+ "\')";
		}
		ConnectSql cs = new ConnectSql();
		try {
			System.out.println(sql);
			cs.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		cs.close();
	}

	//select
	public Question selectLatest(String createTime){
		Question question = null;
		String sql =  "select * from question where questionCreateDate=\'"+createTime+"\'";
		ConnectSql cs = new ConnectSql();

		try {
			ResultSet rs = cs.stmt.executeQuery(sql);
			//System.out.println(rs.toString()+"++++++++++++++++++");
			while(rs.next()){
				question = new Question(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cs.close();
		return question;
	}
	//select all
	public List<Question> selectAll(){
		List<Question> list =  new ArrayList();
		Question question;
		String sql =  "select * from question";
		ConnectSql cs = new ConnectSql();
		
		try {
			ResultSet rs = cs.stmt.executeQuery(sql);
			//System.out.println(rs.toString()+"++++++++++++++++++");
			while(rs.next()){
				question = new Question(
						rs.getInt(1),
						rs.getString(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9));
				list.add(question);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cs.close();
		return list;
	}
	//update
	
	//delete

}
