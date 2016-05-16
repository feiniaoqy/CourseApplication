package entity;

/**
 * Created by asus on 2016/5/14.
 */
public class Answer {
    private int         questionId;//唯一标识是哪道题的答案
    private String      name;//回答者的姓名
    private String      answerContent;//回答的答案
    private String      time;//

    public Answer() {}

    public Answer(int questionId, String name, String answerContent, String time) {

        this.questionId = questionId;
        this.name = name;
        this.answerContent = answerContent;
        this.time = time;
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{\"answer\":{" +
                "\"questionId\":" + questionId +
                ", \"name\":" +"\"" +name + "\"" +
                ", \"answerContent\":" +"\"" +answerContent + "\"" +
                ", \"time\":" +"\"" +time + "\"" +
                "}}";
    }
}
