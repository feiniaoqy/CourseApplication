package com.feiniaoqy.bishe.dataserver;

import com.feiniaoqy.bishe.dao.AnswerDao;
import com.feiniaoqy.bishe.dao.CommentDao;
import entity.Answer;
import entity.Comment;

/**
 * Created by asus on 2016/5/14.
 */
public class HandleDataService {

    //处理答案数据
    public static synchronized void insertAnswer(Answer answer){
        final AnswerDao answerDao = new AnswerDao();
        answerDao.insert(answer);
    }

    //处理comment 数据
    public static synchronized void insertComment(Comment comment){
        final CommentDao commentDao = new CommentDao();
        commentDao.insert(comment);
    }

}
