/**
 * @{#} UserInfo.java Create on 2016年7月11日 下午1:57:37
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

/**
  *用户信息
  * 
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

public class UserInfo implements Externalizable{
	
	private String userName;
	
	private String password;
	
	private int age;
	
	public UserInfo() {
		super();
	}

	public UserInfo(String userName, String password, int age) {
		super();
		this.userName = userName;
		this.password = password;
		this.age = age;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		System.out.println("对象反序列化开始");
		Date date = (Date)in.readObject();
		System.out.println(date);
		this.userName = (String) in.readObject();
		this.password = (String) in.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		System.out.println("对象开始序列化");
		Date date = new Date();
		out.writeObject(date);
		out.writeObject(this.userName);
		out.writeObject(this.password);
	}
	
	@Override
	public String toString() {
		return "userName : " + this.userName + " password : " + this.password
					+" age : " + this.age;
	}

}
