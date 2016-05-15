package entity;

/**
 * Created by asus on 2016/5/14.
 */
public class Comment {
    private int     questionId;
    private String  name;
    private String  commentContent;

    public Comment() {}

    public Comment(int questionId, String name, String commentContent, String time) {

        this.questionId = questionId;
        this.name = name;
        this.commentContent = commentContent;
        this.time = time;
    }

    private String  time;

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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{\"comment\":{" +
                "\"questionId\":" + questionId +
                ", \"name\":" +"\"" +name + "\"" +
                ", \"commentContent\":" +"\"" +commentContent + "\"" +
                ", \"time\":" +"\"" +time + "\"" +
                "}}";
    }
}
