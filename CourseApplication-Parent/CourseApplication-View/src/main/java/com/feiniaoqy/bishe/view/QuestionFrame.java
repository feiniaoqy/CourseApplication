package com.feiniaoqy.bishe.view;

import com.feiniaoqy.bishe.Server.*;
import com.feiniaoqy.bishe.dao.QuestionDao;
import com.feiniaoqy.bishe.util.JsonUtil;
import com.feiniaoqy.bishe.util.Time;
import entity.Comment;
import entity.Question;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class QuestionFrame extends JFrame {

	private Question                    question;
	private JPanel                      questionPane;
	private JTextField 					titleTextField;
	private JTextField 					answerTextField;
	private JTextField 					commentTextField;
	private JList						questionList;
	private JTable						table;//用于展示交互数据的
	private JTextArea  					jTextArea;
	private GetDataThread               getDataThread;
	private SendThread                  sendThread;
	private ServerThread 				serverThread;

	/**
	 * Create the frame.
	 */
	public QuestionFrame(final Question question) {
		this.question = question;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 1133, 710);
		questionPane = new JPanel();
		questionPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(questionPane);

		JLabel titleLabel = new JLabel("题目：");

		titleTextField = new JTextField();
		titleTextField.setColumns(10);

		JLabel answerLabel = new JLabel("答案：");

		answerTextField = new JTextField();
		answerTextField.setColumns(10);

		JSeparator separator = new JSeparator();

		JScrollPane scrollPane = new JScrollPane();

		commentTextField = new JTextField();
		commentTextField.setColumns(10);


		//给题目和答案文本框赋值
		titleTextField.setText(question.getQuestionContent());
		answerTextField.setText(question.getAnswer());

		JSeparator separator_1 = new JSeparator();

		JButton sendButton = new JButton("发送");
		GroupLayout groupLayout = new GroupLayout(questionPane);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(titleLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, 1041, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(2)
												.addComponent(answerLabel)
												.addGap(4)
												.addComponent(answerTextField, GroupLayout.PREFERRED_SIZE, 1041, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(975)
												.addComponent(sendButton))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(3)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(commentTextField, GroupLayout.PREFERRED_SIZE, 933, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1097, GroupLayout.PREFERRED_SIZE)
																.addGap(1)
																.addComponent(separator, GroupLayout.PREFERRED_SIZE, 1090, GroupLayout.PREFERRED_SIZE))
														.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 1087, GroupLayout.PREFERRED_SIZE))))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(titleLabel)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(1)
												.addComponent(titleTextField, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(22)
												.addComponent(answerLabel))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(16)
												.addComponent(answerTextField, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(23)
												.addComponent(separator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addGap(26)
												.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 432, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
								.addGap(7)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
										.addComponent(commentTextField, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);
		jTextArea = new JTextArea();

		scrollPane.setViewportView(jTextArea);
		questionPane.setLayout(groupLayout);


		sendTheComentData(sendButton);

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (serverThread == null ){
					startService();
				}else {
					//如果已经开启服务直接发送数据
					sendThread.setMsg(JsonUtil.QuestionToJson(question));
				}
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	//======================================开启子线程========================================
	/**
	 * 启动Socket服务
	 */
	private void startService() {
		//启动Socket服务
		serverThread = new ServerThread();
		//start the socket server's thread. And listen the client link to the server!!!!
		serverThread.start();

		serverThread.setSocketListener(new SocketListener() {
			@Override
			public void socketListener(Socket socket) {
				getDataThread = new GetDataThread(socket);
				getDataThread.start();
				sendThread = new SendThread(socket);
				sendThread.start();
				getDataThread.setMessageListener(new MessageListener() {
					public void Message(String msg) {
						System.out.println("The data was got from the client: " + msg);
						Comment comment = JsonUtil.JsonToComment(msg);
						//先把数据存入数据库

						//再从数据库中取最新的数据
						jTextArea.append(comment.getName()+"："+comment.getCommentContent());
					}
				});


				//先查询数库得到最新的question
				QuestionDao questionDao = new QuestionDao();
				question = questionDao.selectLatest(question.getQuestionCreateDate());
				sendThread.setMsg(JsonUtil.QuestionToJson(question));
			}
		});
	}


	public void sendTheComentData(JButton jButton){

		jButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String commentData = commentTextField.getText();
				commentTextField.setText("");

				Comment comment = new Comment();
				comment.setQuestionId(question.getQuestionId());
				comment.setCommentContent(commentData);
				comment.setName("teacher");
				comment.setTime(Time.getTime());

				if (commentData==null||"".equals(commentData)){
					//不能为空
				}else {
					jTextArea.append("\n"+comment.getName()+"："+comment.getCommentContent());
					System.out.println(comment.toString());
					sendThread.setMsg(comment.toString());
			}
			}
		});
	}
}
