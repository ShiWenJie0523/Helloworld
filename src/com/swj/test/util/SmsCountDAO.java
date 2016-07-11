/**
 * @{#} SmsCountDAO.java Create on 2016年7月5日 上午9:09:30
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.sql.SQLException;

public class SmsCountDAO {

	public static void saveSmsCount(String[] val){
		DbConn dbConn = new DbConn();
		
		StringBuffer sql = new StringBuffer("insert into T_STA_SMS_COUNT(gatewayName,FeeSum,FdbUserCount,FmonUserCount,add_date) values('");
		sql.append(val[0]).append("','");
		sql.append(val[1]).append("','");
		sql.append(val[2]).append("','");
		sql.append(val[3]).append("','"); 
		sql.append(val[4]).append("')");
		try {
			dbConn.execute(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
