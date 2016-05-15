package com.feiniaoqy.bishe.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


public class CreateWifi {
	private static Process p;
	private static InputStreamReader isr;
	private static BufferedReader reader;

	//public static ResourceBundle bundle;

	//private static final String STRINGS_PATH = "lt.tasler.ShareWifi.Resources.Strings";
	//private static final Logger LOG = Logger.getLogger(CreateWifi.class);
	/**
	 * stop Wi-Fi
	 */

	public static void stopHotspot() {
		//bundle = ResourceBundle.getBundle(STRINGS_PATH); //TODO: locale
		String message = "";
		try {
			ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "stop", "hostednetwork");
			pb.redirectErrorStream(true);
			p = pb.start();
			isr = new InputStreamReader(p.getInputStream());
			reader = new BufferedReader(isr);

			String line = null;
			while ((line = reader.readLine()) != null) {
				if(line != null)
					message = message.concat(line + "\n");
			}
			JOptionPane.showMessageDialog(null, Constants.stoppedMsg);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, message);
			e.printStackTrace();
		}
		//LOG.info(message);
	}
	/**
	 * create Wi-Fi 
	 * @param name Wi-Fi's name
	 * @param pass Wi-Fi's password
	 */
	public static void createHotspot(String name, String pass) {
		//bundle = ResourceBundle.getBundle(STRINGS_PATH); //TODO: locale
		if(name.length() < 5) {
			JOptionPane.showMessageDialog(null, Constants.shortNameMsg, Constants.errorMsgTitle, JOptionPane.ERROR_MESSAGE);
			return;
		}
		else if(pass.length() < 8) {
			JOptionPane.showMessageDialog(null, Constants.shortPassMsg, Constants.errorMsgTitle, JOptionPane.ERROR_MESSAGE);
			return ;
		}

		String message = "";
		try {
			ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "set", "hostednetwork", "mode=allow", "ssid=" + name, "key=" + pass);
			pb.redirectErrorStream(true);
			p = pb.start();

			isr = new InputStreamReader(p.getInputStream());
			reader = new BufferedReader(isr);

			String line = null;
			while((line = reader.readLine()) != null) {
				if(line != null)
					message = message.concat(line + "\n");
			}
		} catch (IOException e) {
			//return bundle.getString("unknowErrorMsg");
			JOptionPane.showMessageDialog(null, Constants.unknowErrorMsg , Constants.errorMsgTitle, JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}

		//start a hostpot
		try {
			ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "start", "hostednetwork");
			pb.redirectErrorStream(true);
			p = pb.start();

			isr = new InputStreamReader(p.getInputStream());
			reader = new BufferedReader(isr);

			String line = null;
			while((line = reader.readLine()) != null) {
				if(line != null)
					message = message.concat(line + "\n");
			}

		} catch(IOException e) {
			//return bundle.getString("unknowErrorMsg");
			JOptionPane.showMessageDialog(null, Constants.unknowErrorMsg, Constants.errorMsgTitle , JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, Constants.startedMsg);

		//LOG.info(message);
	}

	/**
	 * get the connectors info(Mac address)
	 * @return
	 */
	public static List<String> getConers(){
		try {
			//netsh wlan show hostednetwork
			ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "show", "hostednetwork");
			pb.redirectErrorStream(true);
			p = pb.start();

			isr = new InputStreamReader(p.getInputStream(),"GBK");
			reader = new BufferedReader(isr);

			String line = null;
			//int i = 0;
			List<String> listConnectors = new ArrayList<String>();
			while((line = reader.readLine()) != null) {
				if(line != null){
					//cut blank space
					String s = line.replaceAll(" ", "");
					listConnectors.add(s);
				}
			}
			return listConnectors;
		} catch (IOException e) {
			//return bundle.getString("unknowErrorMsg");
			JOptionPane.showMessageDialog(null, Constants.unknowErrorMsg , Constants.errorMsgTitle, JOptionPane.WARNING_MESSAGE);
			return null;
			//e.printStackTrace();
		}
	}

	public static void restartWifi(){
		//start a hostpot
		String message = "";
		try {
			ProcessBuilder pb = new ProcessBuilder("netsh", "wlan", "start", "hostednetwork");
			pb.redirectErrorStream(true);
			p = pb.start();

			isr = new InputStreamReader(p.getInputStream());
			reader = new BufferedReader(isr);

			String line = null;
			while((line = reader.readLine()) != null) {
				if(line != null)
					message = message.concat(line + "\n");
			}

		} catch(IOException e) {
			//return bundle.getString("unknowErrorMsg");
			JOptionPane.showMessageDialog(null, Constants.unknowErrorMsg, Constants.errorMsgTitle , JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, Constants.startedMsg);
	}
}
