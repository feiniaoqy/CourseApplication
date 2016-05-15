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
        System.out.println(json);
        return json;
    }

    //===========================解析json=======================================

    /**
     * 解析json
     * @param string
     * @return
     */
    public static Comment JsonToComment(String string){
        JSONTokener jsonParser;
        JSONObject data_jo;
        Comment comment = new Comment();
        try {
            jsonParser = new JSONTokener(string);
            data_jo = (JSONObject) jsonParser.nextValue();
            JSONObject message = data_jo.getJSONObject("comment");
            comment.setQuestionId(message.getInt("questionId"));
            comment.setCommentContent(message.getString("commentContent"));
            comment.setName(message.getString("name"));
            comment.setTime(message.getString("time"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public static Answer jsonToAnswer(String string){
        JSONTokener jsonParser;
        JSONObject data_jo;
        Answer answer = new Answer();
        try {
            jsonParser = new JSONTokener(string);
            data_jo = (JSONObject) jsonParser.nextValue();
            JSONObject answerObj = data_jo.getJSONObject("comment");

            answer.setQuestionId(answerObj.getInt("questionId"));
            answer.setAnswerContent(answerObj.getString("answerContent"));
            answer.setName(answerObj.getString("name"));
            answer.setTime(answerObj.getString("time"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return answer;
    }

    //{"question":{"questionId":0,"questionContent":"现在vcxzvzxc","questionType":2,"answer":"在vcxzvzcx "}}
    //{"message":{"name":"teacher","message":"afsdfads"}}

}
