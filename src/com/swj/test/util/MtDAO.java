/**
 * @{#} MtDAO.java Create on 2016年5月19日 下午3:38:46
 *
 * Copyright (c) 2016 by JRJ. 
 */

package com.swj.test.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
  *
  * 保存下行记录到数据库
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

public class MtDAO {

	public static void saveMt(String[] val){
		try {
			DbConn dbConn = new DbConn();
			StringBuffer sb = new StringBuffer("select * from T_SVC_SERVICE where fservicecode = '");
			sb.append(val[4]);
			sb.append("'");
			ResultSet rs = dbConn.executeQuery(sb.toString());
			String[] str = new String[5];
			while (rs.next()) {
				String gatewayid = rs.getString("fgatewayid");
				String serviceid = rs.getString("fserviceid");
				String ffeetypeid = rs.getString("ffeetypeid");
				String ffeecode = rs.getString("ffeecode");
				System.out.println("fgatewayid 为：" + gatewayid);
				System.out.println("fserviceid 为：" + serviceid);
				System.out.println("ffeetypeid 为：" + ffeetypeid);
				System.out.println("ffeecode 为：" + ffeecode);
				str[0] = gatewayid;
				str[1] = serviceid;
				str[2] = ffeetypeid;
				str[3] = ffeecode;
			}
			
			StringBuffer sql = new StringBuffer("insert into t_sta_bill(FMsgType,FOrgAddr,FDestAddr,FFeeTerminal,FContent,FGatewayID,FServiceID,FServiceCode,FFeetypeID,FFeeCode,FFeeUserType,FReportFlag,FMTFlag,FLinkID,FSubmitStatus,FSubmitTime,FState) values('");
			sql.append("2','");
			sql.append(val[1]).append("','");
			sql.append(val[0]).append("','");
			sql.append(val[0]).append("','");
			sql.append(val[2]).append("','");
			sql.append(str[0]).append("','");
			sql.append(str[1]).append("','");
			sql.append(val[4]).append("','");
			sql.append(str[2]).append("','");
			sql.append(str[3]).append("','");
			sql.append("0','0','0','").append(val[3]).append("','");
			sql.append("0','").append(val[5]).append("','0')");
			dbConn.execute(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Object> queryMt(String[] val, List<Object> hread) {
		List<Object> list = new ArrayList<Object>();
		try {
			DbConn dbConn = new DbConn();
			StringBuffer sb = new StringBuffer("select * from T_SVC_SERVICE where fservicecode = '");
			sb.append(val[4]);
			sb.append("'");
			ResultSet rs = dbConn.executeQuery(sb.toString());
			String[] str = new String[5];
			while (rs.next()) {
				String gatewayid = rs.getString("fgatewayid");
				String serviceid = rs.getString("fserviceid");
				String ffeetypeid = rs.getString("ffeetypeid");
				String ffeecode = rs.getString("ffeecode");
				System.out.println("fgatewayid 为：" + gatewayid);
				System.out.println("fserviceid 为：" + serviceid);
				System.out.println("ffeetypeid 为：" + ffeetypeid);
				System.out.println("ffeecode 为：" + ffeecode);
				str[0] = gatewayid;
				str[1] = serviceid;
				str[2] = ffeetypeid;
				str[3] = ffeecode;
			}

			for (Object obj : hread) {
				if (obj.equals("FMSGID") || obj.equals("FWAITSENDID") || obj.equals("FUNIID")
						|| obj.equals("FCPSERVICEID") || obj.equals("FReportFlag") || obj.equals("FSUBMITDESC") || obj.equals("FREPORTSTATUS")
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
				}else if(obj.equals("FFEETYPEID")){
					list.add(str[2]);
				}else if(obj.equals("FFEECODE")){
					list.add(str[3]);
				}else{
					list.add(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
