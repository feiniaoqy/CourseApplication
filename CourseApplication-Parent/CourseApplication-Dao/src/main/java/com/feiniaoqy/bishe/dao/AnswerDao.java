package com.feiniaoqy.bishe.dao;

import com.feiniaoqy.bishe.util.ConnectSql;
import com.feiniaoqy.bishe.util.Constants;
import entity.Answer;
import entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiniaoqy on 2016/5/15.
 * 主要用于对答案存储操作
 */
public class AnswerDao {
    //insert
    public synchronized void insert(Answer answer){
        if (!(selectAllByQuestionTime(answer.getTime()).size()>0)){
            String sql = "";
            sql = "insert into answer ("
                    + "questionId,"
                    + "name,"
                    + "answerContent,"
                    + "time)"
                    + " values ("
                    +"\'"+answer.getQuestionId()+"\',"
                    +"\'"+answer.getName()+"\',"
                    +"\'"+answer.getAnswerContent()+"\',"
                    +"\'"+answer.getTime()
                    + "\')";
            ConnectSql cs = new ConnectSql();
            try {
                System.out.println(sql);
                cs.stmt.executeUpdate(sql);
            } catch (SQLException e) {

                e.printStackTrace();
            }
            cs.close();
        }
    }
    //select all
    public ArrayList<Answer> selectAllByQuestionId(int questionId){
        ArrayList<Answer> list =  new ArrayList();
        Answer answer;
        String sql =  "select * from answer where questionId="+questionId;
        ConnectSql cs = new ConnectSql();

        try {
            ResultSet rs = cs.stmt.executeQuery(sql);
            while(rs.next()){
                answer = new Answer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                list.add(answer);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cs.close();
        return list;
    }


    public ArrayList<Answer> selectAllByQuestionTime(String time){
        ArrayList<Answer> list =  new ArrayList();
        Answer answer;
        String sql =  "select * from answer where time=\'"+time+"\'";
        ConnectSql cs = new ConnectSql();

        try {
            ResultSet rs = cs.stmt.executeQuery(sql);
            //System.out.println(rs.toString()+"++++++++++++++++++");
            while(rs.next()){
                answer = new Answer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                list.add(answer);
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
