package com.feiniaoqy.bishe.view;
import com.feiniaoqy.bishe.util.Constants;
import com.feiniaoqy.bishe.util.CreateWifi;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class GUI extends JFrame{
	
	private JPanel panel;
	
	private JButton startButton;
	private JButton createButton;
	
	private JLabel hotspotLabel;
	private JTextField hotspotText;
	
	private JLabel passwordLabel;
	private JTextField passwordText;
	
	private JMenuBar menuBar;
	private JMenu about;
	
	public GUI(String title, int fWidith, int fHight) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(fWidith,fHight);
		
		about = new JMenu(Constants.about);
		about.setMnemonic(KeyEvent.VK_A);
		about.add(new JMenuItem(Constants.createdBy));
		
		menuBar = new JMenuBar();
		menuBar.add(about);
		
		setJMenuBar(menuBar);
		
		panel = new JPanel(new GridBagLayout());
		add(panel);
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.NORTHEAST;
		
		hotspotLabel = new JLabel(Constants.lName);
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		panel.add(hotspotLabel, c);
		
		hotspotText = new JTextField(10);
		hotspotText.setText("feiniao");
		c.gridy = 1;
		panel.add(hotspotText,c);
		
		passwordLabel = new JLabel(Constants.lPass);
		c.gridy = 2;
		panel.add(passwordLabel, c);
		
		passwordText = new JTextField(10);
		passwordText.setText("1234567890");
		c.gridy = 3;
		panel.add(passwordText, c);
		
		startButton = new JButton(Constants.bStart);
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final String name = hotspotText.getText();
				final String pass = passwordText.getText();
				//Main.createHotspot(name, pass);
				Timer timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						//CreateWifi.getConers();
						CreateWifi.createHotspot(name, pass);
						//start next frame
						ContentFrame2 contentFrame = new ContentFrame2(name);
						contentFrame.setVisible(true);
						setVisible(false);
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
		});
		
		c.gridy = 4;
		c.gridwidth = 1;
		panel.add(startButton, c);
		
		createButton = new JButton(Constants.bStop);
		createButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				CreateWifi.stopHotspot();
				
			}
		});
		c.gridx = 1;
		panel.add(createButton, c);
	}
}
