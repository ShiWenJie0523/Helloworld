/**
 * @{#} ModifyProperties.java Create on 2016年7月19日 下午4:11:06
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
  *
  * 
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年7月19日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class ModifyProperties {

	public static void main(String[] args) throws Exception{
		//System.out.println(ModifyProperties.class.getResource("/").getPath());
		//加载配置文件
		Properties pro = new Properties();
		InputStream in = null;
		in = new BufferedInputStream (new FileInputStream("D:\\Git\\HelloWorld\\src\\test.properties"));
		pro.load(in);
		//重新写入配置文件
		FileOutputStream file = new FileOutputStream("D:\\Git\\HelloWorld\\src\\test.properties");
		pro.put("LOCAL_USER", "你好");
		pro.put("LOCAL_PWD","http://www.baidu.com");
		System.out.println("得到属性key:"+pro.getProperty("LOCAL_PWD"));
		pro.store(file, "系统配置修改"); //这句话表示重新写入配置文件
	}
}
