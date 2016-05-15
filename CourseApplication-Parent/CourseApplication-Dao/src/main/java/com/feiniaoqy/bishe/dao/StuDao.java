package com.feiniaoqy.bishe.dao;

import com.feiniaoqy.bishe.util.ConnectSql;
import entity.Question;
import entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2016/5/12.
 * 主要用于查询 学生
 */
public class StuDao {
    public static ArrayList<Student> selectAll(){
        ArrayList<Student> list =  new ArrayList();
        Question question;
        String sql =  "select * from student";
        ConnectSql cs = new ConnectSql();
        try {
            ResultSet rs = cs.stmt.executeQuery(sql);
            //System.out.println(rs.toString()+"++++++++++++++++++");
            while(rs.next()){
                Student student = new Student(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6));
                list.add(student);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cs.close();
        return list;
    }
}
