package com.feiniaoqy.bishe.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectSql {
	public Connection cnn = null;
	public Statement stmt  = null;
	
	public ConnectSql(){
		try {
			Class.forName(Constants.SQL_DRIVER);
			cnn = DriverManager.getConnection(
					Constants.URL,
					Constants.USER,
					Constants.PASSWORD);
			//准备执行sql
			stmt = cnn.createStatement();
			
		} catch (ClassNotFoundException e) {
			System.out.println("=========加载驱动失败===========");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("=========连接数据库失败===========");
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			this.stmt.close();
			this.cnn.close();
		} catch (SQLException e) {
			System.out.println("=========断开数据库连接失败==========");
			e.printStackTrace();
		}
	}
	
}
