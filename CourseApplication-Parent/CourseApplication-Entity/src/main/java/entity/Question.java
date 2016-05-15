package entity;

/**
 * question 的数据模型
 * @author feiniaoqy
 *
 */
public class Question {
	public String toString() {
		return "Question [questionId=" + questionId + ", questionContent=" + questionContent + ", questionType="
				+ questionType + ", answer=" + answer + ", answerA=" + answerA + ", answerB=" + answerB + ", answerC="
				+ answerC + ", answerD=" + answerD + ", questionCreateDate=" + questionCreateDate + "]";
	}
	//questionId 是自增的
	private int questionId;
	private String questionContent;
	private int questionType;
	private String answer;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private String questionCreateDate;
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswerA() {
		return answerA;
	}
	
	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}
	public String getAnswerB() {
		return answerB;
	}
	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}
	public String getAnswerC() {
		return answerC;
	}
	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}
	public String getAnswerD() {
		return answerD;
	}
	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}
	public String getQuestionCreateDate() {
		return questionCreateDate;
	}
	public void setQuestionCreateDate(String questionCreateDate) {
		this.questionCreateDate = questionCreateDate;
	}
	public Question(int questionId, String questionContent, int questionType, String answer, String answerA,
			String answerB, String answerC, String answerD, String questionCreateDate) {
		super();
		this.questionId = questionId;
		this.questionContent = questionContent;
		this.questionType = questionType;
		this.answer = answer;
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.questionCreateDate = questionCreateDate;
	}
	public Question(){}

}
