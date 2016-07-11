/**
 * @{#} MoDAO.java Create on 2016年5月19日 下午3:27:30
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 统计下行
 * 
 * @history
 * 
 *          <PRE>
 *  
 * --------------------------------------------------------- 
 * VERSION       DATE            BY       CHANGE/COMMENT 
 * --------------------------------------------------------- 
 * 1.0           2016年5月19日       wenjie.shi               create  
 * ---------------------------------------------------------
 *          </PRE>
 *
 */

public class SmsSumDAO {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static void saveSmsSum(String[] val){
		DbConn dbConn = new DbConn();
		StringBuffer sql = new StringBuffer("insert into T_STA_SMS_SUM values('");
		sql.append(val[0]).append("','");
		sql.append(val[1]).append("','");
		sql.append(val[2]).append("','");
		sql.append(val[3]).append("','");
		sql.append(val[4]).append("','");
		sql.append(val[5]).append("','");
		sql.append(val[6]).append("','");
		sql.append(val[7]).append("','");
		sql.append(val[8]).append("','");
		sql.append(val[9]).append("')");
		try {
			dbConn.execute(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<List<Object>> queryList(){
		List<Object> list = null;
		List<List<Object>> dataList = null;
		DBUtil dbconn = null;
		Connection conn = null;
		PreparedStatement pr = null;
		try {
			dbconn = new DBUtil();
			dataList = new ArrayList<List<Object>>();
			String sql = "select * from T_STA_SMS_SUM order by add_date desc";
			conn = dbconn.getConn("");
			pr = conn.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				list = new ArrayList<Object>();
				String provinceName = rs.getString("FProvinceName");
				String getwayName = rs.getString("getwayName");
				String servicename = rs.getString("fservicename");
				String ffeecode = rs.getString("ffeecode");
				String feeSum = rs.getString("FeeSum");
				String fcount = rs.getString("Fcount");
				String userCount = rs.getString("UserCount");
				Date add_date = rs.getTimestamp("add_date");
				list.add(provinceName);
				list.add(getwayName);
				list.add(servicename);
				list.add(ffeecode);
				list.add(Double.parseDouble(feeSum));
				list.add(fcount);
				list.add(userCount);
				list.add(sdf.format(add_date));
				dataList.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	public static List<List<Object>> queryCountList(){
		
		List<Object> list = null;
		List<List<Object>> dataList = null;
		DBUtil dbconn = null;
		Connection conn = null;
		PreparedStatement pr = null;
		try {
			dbconn = new DBUtil();
			dataList = new ArrayList<List<Object>>();
			String sql = "select * from T_STA_SMS_COUNT order by add_date desc";
			conn = dbconn.getConn("");
			pr = conn.prepareStatement(sql);
			ResultSet rs = pr.executeQuery();
			while (rs.next()) {
				list = new ArrayList<Object>();
				String getwayName = rs.getString("gatewayName");
				String feeSum = rs.getString("FeeSum");
				String userCount = rs.getString("FdbUserCount");
				String monUserCount = rs.getString("FmonUserCount");
				Date add_date = rs.getTimestamp("add_date");
				list.add(getwayName);
				list.add(feeSum);
				list.add(monUserCount);
				list.add(userCount);
				list.add(sdf.format(add_date));
				dataList.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
				pr.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
}
