package com.feiniaoqy.bishe.dataserver;

import com.feiniaoqy.bishe.dao.AnswerDao;
import com.feiniaoqy.bishe.dao.CommentDao;
import entity.Answer;
import entity.Comment;

import java.util.ArrayList;

/**
 * Created by asus on 2016/5/16.
 */
public class GetDataService {
    public static synchronized ArrayList<Answer> getAnswerByQuestionId(int questionId){
        final AnswerDao answerDao = new AnswerDao();
        return answerDao.selectAllByQuestionId(questionId);
    }

    public static synchronized ArrayList<Comment> getCommentByQuestionId(int questionId){
        final CommentDao commentDao = new CommentDao();
        return commentDao.selectAllByQuestionId(questionId);
    }
}
