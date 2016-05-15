package com.feiniaoqy.bishe.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.feiniaoqy.bishe.util.Constants;
import com.sun.istack.internal.logging.Logger;

/**
 * it is the main method to start the application
 */
public class Main {
	//private static final Logger LOG = Logger.getLogger(Main.class);
	
	public static void main (String[] args) {
		int fWidith = 200;
		int fHeight = 500;
		GUI gui = new GUI(Constants.appName, fWidith, fHeight);
		gui.setVisible(true);
	}
}






