/**
 * @{#} Test.java Create on 2016年7月11日 下午2:04:02
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.swj.test.entity.UserInfo;

/**
  *
  * 对象序列化、反序列化测试类
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年7月11日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File("d:\\person.txt")));
			//UserInfo userInfo = (UserInfo) inputStream.readObject();
			System.out.println(inputStream.readObject());
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*try {
			String[] commandstr = {"cmd", "/C", "e:", "E:\\javapro gradle build"};
			Process p = Runtime.getRuntime().exec(commandstr);
			InputStream in = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(in,"utf8");
			BufferedReader bf = new BufferedReader(isr);
			String line = null;
			while ((line = bf.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		double f = 111231.5575;
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
		System.out.println(f1);
		
		DecimalFormat format = new DecimalFormat("#.00");
		System.out.println(format.format(f));
		System.out.println("---------------------------------------------");
		
		String str = String.format("%.2f", f);
		System.out.println(str);
	}

}
