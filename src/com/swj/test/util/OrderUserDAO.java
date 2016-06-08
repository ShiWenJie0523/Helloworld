/**
 * @{#} OrderUserDAO.java Create on 2016年5月19日 下午4:12:39
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.sql.ResultSet;
import java.util.List;

/**
  *
  *存储用户订购关系到数据库
  * @history 
  * <PRE> 
  * --------------------------------------------------------- 
  * VERSION       DATE            BY       CHANGE/COMMENT 
  * --------------------------------------------------------- 
  * 1.0           2016年5月19日       wenjie.shi               create  
  * ---------------------------------------------------------
  * </PRE>
  *
  */

public class OrderUserDAO {

	public static void saveOrderUser(String[] val){
		try {
			DbConn dbConn = new DbConn();
			if("0".equals(val[3])){
				val[3] = "1";
			}else{
				val[3] = "2";
			}
			
			int cnt = 0;
	       String sql = "select count(*) from t_sta_orderuser where FFeeTerminal='"
	                + val[0] + "' and FServiceId='" + val[1] + "'";
	        ResultSet rs = dbConn.executeQuery(sql);
	        if (rs != null && rs.next()) {
	            cnt = rs.getInt(1);
	        }
	        rs.close();
	        if (cnt == 0) {
	            sql = "insert into t_sta_orderuser(FMobile,FFeeTerminal,FServiceID,FState,FRegTime,FCancelTime,FLinkID)values('";
	            sql += val[0] + "','" + val[0] + "','" + val[1]
	                    + "','" + val[3] + "'," + val[4] +",'" + val[5] + "','')";
	        } else {
	            sql = "update t_sta_orderuser set FState='" + val[3]
	                    + "',FRegTime='" + val[4] +"',FCancelTime='"+val[5]
	                    + "' where FFeeTerminal='" + val[0]
	                    + "' and FServiceId='" + val[1] + "'";
	        }
	        dbConn.executeUpdate(sql);//保存至数据库
	        System.out.println("执行成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<Object> queryOrderUser(String[] val, List<Object> hread){
		List<Object> list = null;
		try {
			if("0".equals(val[3])){
				val[3] = "1";
			}else{
				val[3] = "2";
			}
			//"FMobile","FFeeTerminal","FServiceID","FState","FRegTime","FCancelTime","FLinkID","FMark"
			for (Object obj : list) {
				if(obj.equals("FMark") || obj.equals("FLinkID")){
					list.add(null);
				}else if(obj.equals("FMobile") || obj.equals("FFeeTerminal")){
					list.add(val[0]);
				}else if(obj.equals("FServiceID")){
					list.add(val[1]);
				}else if(obj.equals("FState")){
					list.add(val[3]);
				}else if(obj.equals("FRegTime")){
					list.add(val[4]);
				}else if(obj.equals("FCancelTime")){
					list.add(val[5]);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
