package com.feiniaoqy.bishe.view;

import com.feiniaoqy.bishe.Server.ServerThread;
import com.feiniaoqy.bishe.dao.QuestionDao;
import com.feiniaoqy.bishe.dao.StuDao;
import com.feiniaoqy.bishe.model.StuTableModel;
import com.feiniaoqy.bishe.util.Constants;
import com.feiniaoqy.bishe.util.CreateWifi;
import com.feiniaoqy.bishe.util.Time;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import entity.Question;
import entity.Student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.*;


public class ContentFrame2 extends JFrame {
	public List<Socket> clients;//保存连接到服务器的客户端

	private String wifiName;
	private int answerABCD=1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	
	private JRadioButton btnB;
	private JTextArea textB;
	private JRadioButton btnC;
	private JTextArea textC;
	private JRadioButton btnD;
	private JTextArea textD;
	private JRadioButton btnA;
	private JTextArea textA;
	private JTextArea textAreaWdt;
	private JLabel label;

	private ServerThread serverThread = null;

	private JList listQuestion;
	
	/**
	 * Create the frame.
	 */
	public ContentFrame2(String wifiName) {
		if (serverThread==null){
			startSocketServer();
		}
		this.wifiName = wifiName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 978, 718);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		//========================选项卡部分===============================================
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel createNewTask_1 = new JPanel();
		tabbedPane.addTab("创建新的练习题", null, createNewTask_1, null);
		//创建新的练习题
		addNewTask(createNewTask_1);


		JPanel overTaskPanel = new JPanel();
		tabbedPane.addTab("已完成的练习题", null, overTaskPanel, null);
		//查看已完成的练习题
		showOverTask(overTaskPanel);


		//=========================左侧scroll部分===================================

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(300, 2));
		contentPane.add(scrollPane, BorderLayout.WEST);
		setScrollPanel(scrollPane);


	}
	

	//scrollPanel 部分
	public void setScrollPanel(JScrollPane scrollPane){
		JPanel panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(0, 0, 300, 121);
		panel_3.setLayout(null);

		JLabel lblWifiName = new JLabel("WiFi名称："+wifiName);
		lblWifiName.setBounds(25, 15, 231, 27);
		panel_3.add(lblWifiName);

		JButton btnStop = new JButton("关闭");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//restart WiFi
				Timer timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						CreateWifi.stopHotspot();
					}
				});
				//setting repeat
				timer.setRepeats(false);
				timer.start();
			}
		});
		btnStop.setBounds(25, 71, 93, 29);
		panel_3.add(btnStop);

		JButton btnRestart = new JButton("重启");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//restart WiFi
				Timer timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						CreateWifi.restartWifi();
					}
				});
				//setting repeat
				timer.setRepeats(false);
				timer.start();
			}
		});
		panel_2.setLayout(null);
		btnRestart.setBounds(133, 71, 93, 29);
		panel_3.add(btnRestart);
		panel_2.add(panel_3);


		final JTable stuTable = new JTable();


		//final JList studentsList = new JList();

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				synchronized (""){
					final List<String> arrList = CreateWifi.getConers();
					arrList.remove(arrList.size()-1);
					String[] colNames = {"姓名","签到"};

					//签到数据的得到  1、从数据库中得到总的要上课的学生 2、通过连接WIFI的MAC 地址来设置学生签到的状态
					ArrayList<Student> stuList = StuDao.selectAll();
					ArrayList<Object[]> list = new ArrayList<Object[]>();

					for (int i=0;i<stuList.size();i++){
						Object[] s = {stuList.get(i).getName(),new Boolean(false)};
						if ((arrList.size()-1)>=16){
							for (int j=16;j<=(arrList.size()-1);j++){
								//System.out.println(arrList.get(j).substring(0,17));
								if ((stuList.get(i).getMACAddress()).equals(arrList.get(j).substring(0,17))){
									s[1] = new Boolean(true);
								}
							}
						}
						list.add(s);
					}
					System.out.print("");
					stuTable.setModel(new StuTableModel(colNames,list));
				}
			}
		});
		//setting repeat is true
		timer.setRepeats(true);
		timer.start();

		JScrollPane jScrollPane = new JScrollPane(stuTable);
		jScrollPane.setBounds(0, 124, 300, 526);
		panel_2.add(jScrollPane);
	}


	public void addNewTask(JPanel createNewTask){
		JLabel lblTitle = new JLabel("题目：");

		final JTextArea taTitle = new JTextArea();

		JSeparator separator = new JSeparator();

		final JPanel panelAnswer = new JPanel();
		panelAnswer.setVisible(true);
		//=====================选择是问答题，还是选择题===============================
		final JRadioButton rBtnWdt = new JRadioButton("问答题");
		rBtnWdt.setSelected(true);
		buttonGroup_1.add(rBtnWdt);
		rBtnWdt.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(rBtnWdt.isSelected()){
					setAnswer(panelAnswer,false);
				}else{
					setAnswer(panelAnswer,true);
				}
			}
		});

		final JRadioButton rBtnDxt = new JRadioButton("单选题");
		buttonGroup_1.add(rBtnDxt);
		rBtnDxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rBtnDxt.isSelected()){
					setAnswer(panelAnswer,true);
				}else{
					setAnswer(panelAnswer,false);
				}
			}
		});

		btnA = new JRadioButton("A");
		btnA.setBounds(44, 35, 41, 29);
		buttonGroup.add(btnA);
		btnA.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(btnA.isSelected()){
					answerABCD = 1;
				}
			}
		});

		btnB = new JRadioButton("B");
		btnB.setBounds(44, 99, 41, 29);
		buttonGroup.add(btnB);
		btnB.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(btnB.isSelected()){
					answerABCD = 2;
				}
			}
		});

		btnC = new JRadioButton("C");
		btnC.setBounds(44, 163, 41, 29);
		buttonGroup.add(btnC);
		btnC.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(btnC.isSelected()){
					answerABCD = 3;
				}
			}
		});

		btnD = new JRadioButton("D ");
		btnD.setBounds(44, 219, 51, 29);
		buttonGroup.add(btnD);
		btnD.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(btnD.isSelected()){
					answerABCD = 4;
				}
			}
		});

		textB = new JTextArea();
		textB.setBounds(96, 97, 507, 29);

		textA = new JTextArea();
		textA.setBounds(96, 33, 508, 29);

		textC = new JTextArea();
		textC.setBounds(97, 162, 506, 29);

		textD = new JTextArea();
		textD.setBounds(96, 218, 507, 29);

		textAreaWdt = new JTextArea();
		textAreaWdt.setBounds(84, 16, 494, 274);

		label = new JLabel("答案：");
		label.setBounds(15, 15, 54, 21);

		panelAnswer.setLayout(null);
		panelAnswer.add(btnB);
		panelAnswer.add(textB);
		panelAnswer.add(btnC);
		panelAnswer.add(textC);
		panelAnswer.add(btnD);
		panelAnswer.add(textD);
		panelAnswer.add(btnA);
		panelAnswer.add(textA);
		panelAnswer.add(textAreaWdt);
		panelAnswer.add(label);
		//开始时，显示单选题
		setAnswer(panelAnswer,false);


		JSeparator separator_1 = new JSeparator();
		
		//得到输入数据
		JButton btnTitleSure = new JButton("确定");
		btnTitleSure.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					final Question question = new Question();
					question.setQuestionContent(taTitle.getText());
					if(rBtnWdt.isSelected()){
						//问答题
						question.setQuestionType(Constants.QUESTION_TYPE_WENDA);
						question.setAnswer(textAreaWdt.getText());
						question.setAnswerA("");
						question.setAnswerB("");
						question.setAnswerC("");
						question.setAnswerD("");
					}else{
						question.setQuestionType(Constants.QUESTION_TYPE_SELECT);
						if(answerABCD == 1){
							question.setAnswer(textA.getText());
						}else if(answerABCD == 2){
							question.setAnswer(textB.getText());
						}else if(answerABCD == 3){
							question.setAnswer(textC.getText());
						}else {
							question.setAnswer(textD.getText());
						}
						question.setAnswerA(textA.getText());
						question.setAnswerB(textB.getText());
						question.setAnswerC(textC.getText());
						question.setAnswerD(textD.getText());
						
					}
					String dateString = Time.getTime();
					question.setQuestionCreateDate(dateString);



					//开启异步线程向把数据存入数据库
					Timer timer = new Timer(1000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							//向数据库中插入数据
							QuestionDao questionDao = new QuestionDao();
							questionDao.insert(question);

							//跳转到题目页面
							new QuestionFrame(question,serverThread).setVisible(true);
							getDataToListQuestion(listQuestion);

						}
					});
					timer.setRepeats(false);
					timer.start();
				}
			});
		JButton btnTitleCancel = new JButton("取消");
		
		
		
		
		GroupLayout gl_createNewTask_1 = new GroupLayout(createNewTask);
		gl_createNewTask_1.setHorizontalGroup(
				gl_createNewTask_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_createNewTask_1.createSequentialGroup()
						.addGap(233)
						.addComponent(rBtnWdt)
						.addGap(25)
						.addComponent(rBtnDxt))
				.addGroup(gl_createNewTask_1.createSequentialGroup()
						.addGap(16)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_createNewTask_1.createSequentialGroup()
						.addGap(16)
						.addGroup(gl_createNewTask_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_createNewTask_1.createSequentialGroup()
										.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(taTitle, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE))
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE))
						.addContainerGap())
				.addGroup(gl_createNewTask_1.createSequentialGroup()
						.addContainerGap()
						.addComponent(panelAnswer, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_createNewTask_1.createSequentialGroup()
						.addContainerGap(264, Short.MAX_VALUE)
						.addComponent(btnTitleCancel)
						.addGap(46)
						.addComponent(btnTitleSure)
						.addGap(208))
				);
		gl_createNewTask_1.setVerticalGroup(
				gl_createNewTask_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_createNewTask_1.createSequentialGroup()
						.addGroup(gl_createNewTask_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_createNewTask_1.createSequentialGroup()
										.addGap(19)
										.addComponent(lblTitle))
								.addGroup(gl_createNewTask_1.createSequentialGroup()
										.addContainerGap()
										.addComponent(taTitle, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
						.addGap(45)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
						.addGap(9)
						.addGroup(gl_createNewTask_1.createParallelGroup(Alignment.LEADING)
								.addComponent(rBtnWdt)
								.addComponent(rBtnDxt))
						.addGap(9)
						.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelAnswer, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_createNewTask_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnTitleCancel)
								.addComponent(btnTitleSure))
						.addContainerGap(16, Short.MAX_VALUE))
				);

		createNewTask.setLayout(gl_createNewTask_1);

	}


	public void setAnswer(JPanel panelAnswer,Boolean isSelected){
		if(isSelected){
			btnB.setVisible(true);
			textB.setVisible(true);
			btnC.setVisible(true);
			textC.setVisible(true);
			btnD.setVisible(true);
			textD.setVisible(true);
			btnA.setVisible(true);
			textA.setVisible(true);
			textAreaWdt.setVisible(false);
			label.setVisible(false);
		}else{
			btnB.setVisible(false);
			textB.setVisible(false);
			btnC.setVisible(false);
			textC.setVisible(false);
			btnD.setVisible(false);
			textD.setVisible(false);
			btnA.setVisible(false);
			textA.setVisible(false);
			textAreaWdt.setVisible(true);
			label.setVisible(true);
		}

	}
	//========================================overTask部分==================================================
	public void showOverTask(JPanel jpanel){
		listQuestion = new JList();
		JScrollPane jScrollPane = new JScrollPane();

		GroupLayout gl_overTaskPanel = new GroupLayout(jpanel);
		gl_overTaskPanel.setHorizontalGroup(
				gl_overTaskPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_overTaskPanel.createSequentialGroup()
						.addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_overTaskPanel.setVerticalGroup(
				gl_overTaskPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(jScrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
				);
		jpanel.setLayout(gl_overTaskPanel);
		jScrollPane.setViewportView(listQuestion);
		//向list展示数据
		getDataToListQuestion(listQuestion);
		
	}

	/**
	 * 用于展示数据
	 * @param jList 传递进来盛装问题数据的容器
	 */
	public void getDataToListQuestion(final JList jList){
		Timer timer = new Timer(1000, new ActionListener() {
			//得到数据————————————————————————————————————————————————————————————————————————————————————————————————————————————————
			QuestionDao qd = new QuestionDao();
			List<Question> questionList = qd.selectAll();
			@Override
			public void actionPerformed(ActionEvent arg0) {
				jList.addListSelectionListener(new ListSelectionListener() {
					int i=0;
					@Override
					public void valueChanged(ListSelectionEvent e) {
						i++;
						if (i==2){
							Question question = questionList.get(jList.getSelectedIndex());
							//跳转
							new QuestionFrame(question,serverThread).setVisible(true);
							i=0;
						}
					}
				});

				jList.setModel(new ListModel() {

					@Override
					public void addListDataListener(ListDataListener l) {
						// TODO Auto-generated method stub

					}

					@Override
					public String getElementAt(int index) {
						return questionList.get(index).getQuestionContent();
					}

					@Override
					public int getSize() {
						return questionList.size();
					}

					@Override
					public void removeListDataListener(ListDataListener l) {

					}
				}); 
			}
		});
		//setting repeat is true
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * 开启 SocketServer
	 */
	public void startSocketServer(){
		//启动Socket服务
		serverThread = new ServerThread();
		//start the socket server's thread. And listen the client link to the server!!!!
		serverThread.start();
	}
}

