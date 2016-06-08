/**
 * @{#} MoDAO.java Create on 2016年5月19日 下午3:27:30
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 存储上行记录到数据库
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

public class MoDAO {

	private static HashMap<String, String> map = new HashMap<String, String>();
	
	static{
		DbConn dbConn = new DbConn();
		StringBuffer sb = new StringBuffer("select * from T_SVC_SERVICE");
		
		ResultSet rs;
		try {
			rs = dbConn.executeQuery(sb.toString());
			while (rs.next()) {
				String servicecode = rs.getString("fservicecode");
				String gatewayid = rs.getString("fgatewayid");
				String serviceid = rs.getString("fserviceid");
				System.out.println("fgatewayid 为：" + gatewayid);
				System.out.println("fserviceid 为：" + serviceid);
				map.put(servicecode + "0",gatewayid);
				map.put(servicecode + "1",serviceid);
			}
			System.out.println("====获取到的业务数据记录为：" + map.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbConn.closeConnection();
		}
	}
	
	public static void saveMo(String[] val) {
		try {
			DbConn dbConn = new DbConn();
			StringBuffer sb = new StringBuffer("select * from T_SVC_SERVICE where fservicecode = '");
			sb.append(val[4]);
			sb.append("'");
			ResultSet rs = dbConn.executeQuery(sb.toString());
			String[] str = new String[2];
			while (rs.next()) {
				String gatewayid = rs.getString("fgatewayid");
				String serviceid = rs.getString("fserviceid");
				System.out.println("fgatewayid 为：" + gatewayid);
				System.out.println("fserviceid 为：" + serviceid);
				str[0] = gatewayid;
				str[1] = serviceid;
			}

			// 合并数组
			/* ArrayUtils.addAll(val, str); */
			StringBuffer sql = new StringBuffer(
					"insert into t_sta_bill(FMsgType,FOrgAddr,FDestAddr,FFeeTerminal,FContent,FGatewayID,FServiceID,FServiceCode,FFeeUserType,FReportFlag,FMTFlag,FLinkID,FSubmitStatus,FSubmitTime,FState) values('");
			sql.append("1','");
			sql.append(val[0]).append("','");
			sql.append(val[1]).append("','");
			sql.append(val[0]).append("','");
			sql.append(val[2]).append("','");
			sql.append(str[0]).append("','");
			sql.append(str[1]).append("','");
			sql.append(val[4]).append("','");
			sql.append("0','0','0','").append(val[3]).append("','");
			sql.append("0','").append(val[5]).append("','0')");
			dbConn.execute(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<Object> queryMo(String[] val, List<Object> hread) {
		List<Object> list = new ArrayList<Object>();
		try {
			/*DbConn dbConn = new DbConn();
			StringBuffer sb = new StringBuffer("select * from T_SVC_SERVICE where fservicecode = '");
			sb.append(val[4]);
			sb.append("'");
			ResultSet rs = dbConn.executeQuery(sb.toString());
			String[] str = new String[2];
			while (rs.next()) {
				String gatewayid = rs.getString("fgatewayid");
				String serviceid = rs.getString("fserviceid");
				System.out.println("fgatewayid 为：" + gatewayid);
				System.out.println("fserviceid 为：" + serviceid);
				str[0] = gatewayid;
				str[1] = serviceid;
			}*/
			String[] str = new String[2];
			String gatewayid = map.get(val[4]+"0");
			String serviceid = map.get(val[4]+"1");
			System.out.println("fgatewayid 为：" + gatewayid);
			System.out.println("fserviceid 为：" + serviceid);
			str[0] = gatewayid;
			str[1] = serviceid;
			for (Object obj : hread) {
				if (obj.equals("FMSGID") || obj.equals("FWAITSENDID") || obj.equals("FUNIID")
						|| obj.equals("FCPSERVICEID") || obj.equals("FFEETYPEID") || obj.equals("FFEECODE")
						|| obj.equals("FReportFlag") || obj.equals("FSUBMITDESC") || obj.equals("FREPORTSTATUS")
						|| obj.equals("FREPORTDESC") || obj.equals("FREPORTTIME") || obj.equals("FGIVENCODE")) {
					list.add(null);
				}else if(obj.equals("FMsgType")){
					list.add(1);
				}else if(obj.equals("FOrgAddr") || obj.equals("FFeeTerminal")){
					list.add(val[0]);
				}else if(obj.equals("FDestAddr")){
					list.add(val[1]);
				}else if(obj.equals("FContent")){
					list.add(val[2]);
				}else if(obj.equals("FServiceCode")){
					list.add(val[4]);
				}else if(obj.equals("FServiceID")){
					list.add(str[1]);
				}else if(obj.equals("FGatewayID")){
					list.add(str[0]);
				}else if(obj.equals("FLinkID")){
					list.add(val[3]);
				}else if(obj.equals("FSubmitTime")){
					list.add(val[5]);
				}else{
					list.add(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public static List<List<Object>> queryList(){
		List<Object> list = null;
		List<List<Object>> dataList = null;
		DbConn conn = null;
		try {
			conn = new DbConn();
			dataList = new ArrayList<List<Object>>();
			String sql = "select * from t_sta_bill where fsubmittime >= '2016-01-01' and fmsgtype = '1' order by fsubmittime desc";
			ResultSet rs = conn.executeQuery(sql);
			while (rs.next()) {
				list = new ArrayList<Object>();
				String mobile = rs.getString("forgaddr");
				String desraddr = rs.getString("fdestaddr");
				String content = rs.getString("fcontent");
				String linkid = rs.getString("flinkid");
				String serviceid = rs.getString("fservicecode");
				Date time = rs.getTimestamp("fsubmittime");
				list.add(mobile);
				list.add(desraddr);
				list.add(content);
				list.add(linkid);
				list.add(serviceid);
				list.add(time);
				dataList.add(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
}
