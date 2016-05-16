package com.feiniaoqy.bishe.dao;

import com.feiniaoqy.bishe.util.ConnectSql;
import entity.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiniaoqy on 2016/5/15.
 * 主要用于对评论的存储操作
 */
public class CommentDao {
    //insert
    public synchronized void insert(Comment comment){
        if (!(selectAllByTime(comment.getTime()).size()>0)){
            String sql = "";
            sql = "insert into comment ("
                    + "questionId,"
                    + "name,"
                    + "commentContent,"
                    + "time)"
                    + " values ("
                    +"\'"+comment.getQuestionId()+"\',"
                    +"\'"+comment.getName()+"\',"
                    +"\'"+comment.getCommentContent()+"\',"
                    +"\'"+comment.getTime()
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
    public ArrayList<Comment> selectAllByQuestionId(int questionId){
        ArrayList<Comment> list =  new ArrayList();
        Comment comment;
        String sql =  "select * from comment where questionId="+questionId;
        ConnectSql cs = new ConnectSql();

        try {
            ResultSet rs = cs.stmt.executeQuery(sql);
            //System.out.println(rs.toString()+"++++++++++++++++++");
            while(rs.next()){
                comment = new Comment(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                list.add(comment);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cs.close();
        return list;
    }
    //select all
    public ArrayList<Comment> selectAllByTime(String time){
        ArrayList<Comment> list =  new ArrayList();
        Comment comment;
        String sql =  "select * from comment where time=\'"+time+"\'";
        ConnectSql cs = new ConnectSql();
        try {
            ResultSet rs = cs.stmt.executeQuery(sql);
            while(rs.next()){
                comment = new Comment(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                list.add(comment);
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
