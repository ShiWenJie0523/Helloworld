/**
 * @{#} TestConnection.java Create on 2016年5月18日 下午3:36:50
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.sql.Connection;
import java.sql.DriverManager;

/**
  *
  * 
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年5月18日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class TestConnection {

		/**
		 * @param args
		 */
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			   String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   //加载JDBC驱动
			   String dbURL = "jdbc:sqlserver://192.168.16.5:1433;integratedSecurity=true;DatabaseName=sms_cm";   //连接服务器和数据库msdb
			   Connection dbConn=null;

			   try {
			   Class.forName(driverName);
			   dbConn = DriverManager.getConnection(dbURL);
			   System.out.println("Connection Successful!");   //如果连接成功 控制台输出Connection Successful!
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		}

}
