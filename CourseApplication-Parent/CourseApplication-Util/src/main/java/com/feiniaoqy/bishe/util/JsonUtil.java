package com.feiniaoqy.bishe.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonToken;
import entity.Answer;
import entity.Comment;
import entity.Question;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;

/**
 * Created by asus on 2016/5/12.
 */
public class JsonUtil {
    //========================转为json================================

    /**
     * 返回的是json对象
     * @param question
     * @return
     */
    public static String QuestionToJson(Question question){
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(question);
        json = "{\"question\":"+json+"}";
        return json;
    }

    //===========================解析json=======================================

    /**
     * 解析json
     * @return
     */
    public static Comment JsonToComment(JSONObject message){
        Comment comment = new Comment();
        comment.setQuestionId(message.getInt("questionId"));
        comment.setCommentContent(message.getString("commentContent"));
        comment.setName(message.getString("name"));
        comment.setTime(message.getString("time"));
        return comment;
    }

    public static Answer jsonToAnswer(JSONObject answerObj){
        Answer answer = new Answer();
        answer.setQuestionId(answerObj.getInt("questionId"));
        answer.setAnswerContent(answerObj.getString("answerContent"));
        answer.setName(answerObj.getString("name"));
        answer.setTime(answerObj.getString("time"));

        return answer;
    }


}
