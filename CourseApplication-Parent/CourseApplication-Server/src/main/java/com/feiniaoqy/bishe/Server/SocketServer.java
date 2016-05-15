package com.feiniaoqy.bishe.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket 服务端
 * @author feiniaoqy
 *
 */
public class SocketServer {

	//SocketServer 静态 私有 只能通过getSocketServer()得到实例化对象
	private static SocketServer socketServer = null;
	private static ServerSocket server=null;
	private static Socket socket = null;

	/**
	 * 单列重要实现的方法
	 * 线程同步安全
	 * @return
     */
	public static synchronized SocketServer getSocketServer(){
		if (socketServer == null){
			socketServer = new SocketServer();
		}
		return socketServer;
	}


	/**
	 * 构造方法私有
	 */
	private SocketServer(){
		try{
			//创建一个ServerSocket在端口8070监听客户请求
			server=new ServerSocket(8070);
			System.out.println("服务端已经启动！！！！！！！！！！！！！！！！");
		}catch(Exception e) {
			//出错，打印出错信息
			System.out.println("can not listen to:"+e);
		}
	}

	public static synchronized Socket getSocket(){
		//使用accept()阻塞等待客户请求，有客户
		//请求到来则产生一个Socket对象，并继续执行
		try {
			socket=server.accept();
			System.out.println("socket已经创建！！！！！！！！！！！！！！！！");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return socket;
	}

	/**
	 * 结束对象调用时必须调用该函数，关闭相应的资源
	 */
	public void serverAndSocketClose(){
		try {
			socket.close();//关闭Socket
			server.close(); //关闭ServerSocket
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
