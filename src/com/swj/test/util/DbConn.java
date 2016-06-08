package com.swj.test.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class DbConn implements Serializable {
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;

	private ResultSet rs = null;
	public int dbType = 0;

	public DbConn() {
		conn = this.getConnection();
	}

	public Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				String fileName = null;

				fileName = this.getClass().getClassLoader().getResource("")
						.getPath()
						+ "config.properties";
				fileName = java.net.URLDecoder.decode(fileName, "gb2312");
				Properties p = new Properties();
				InputStream is = new FileInputStream(fileName);
				p.load(is);
				int dbType = Maths.atoi(p.getProperty("DBType"));
				this.dbType = dbType;
				String url = p.getProperty("DBUrl");
				String user = p.getProperty("DBUser");
				String password = p.getProperty("DBPass");
				String driver = p.getProperty("DBDriver");
				is.close();

				Class.forName(driver).newInstance();

				conn = java.sql.DriverManager
						.getConnection(url, user, password);

			}
		} catch (Exception ex) {
			System.out.println("DbConn getConnection error:" + ex.getMessage());

		}
		return conn;
	}

	public Connection getNewConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
		this.getConnection();
		return conn;
	}

	public void closeStmtRs() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (java.sql.SQLException e) {
			System.err.println(e);
		}
	}

	public void closeConnection() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (java.sql.SQLException e) {
			System.err.println(e);
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		if (conn == null) {
			throw new SQLException("connection is null");
		}
		if (sql == null || sql.trim().equals("")) {
			throw new SQLException("sql is empty");
		}
		// LogW.info(sql);
		stmt = conn.createStatement(1004, 1007);
		rs = stmt.executeQuery(sql);
		return rs;
	}

	public ResultSet executeQuery(String sql, boolean pre) {
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.trim().equals("")) {
				throw new SQLException("sql is empty");
			}
			// LogW.info(sql);
			if (pre == true) {
				stmt = conn.createStatement(1004, 1007);
				rs = stmt.executeQuery(sql);
			} else if (pre == false) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
			}
		} catch (Exception e) {
			Log.WriteToLogFile(0,"DbConn executeQuery error:" + e.getMessage());
			return null;
		}
		return rs;
	}

	public ArrayList getResultList(String sql) throws Exception {
		ArrayList list = new ArrayList();
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.trim().equals("")) {
				throw new SQLException("sql is empty");
			}
			Log.WriteToLogFile(0,sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				HashMap map = new HashMap();
				for (int i = 1; i <= columnCount; i++) {
					String cname = rsmd.getColumnName(i).toUpperCase();
					String value = rs.getString(i);
					map.put(cname, value);
				}
				list.add(map);
			}

		} catch (Exception e) {
			return null;
		}
		return list;
	}

	public void TransProcess(String[] sql) throws SQLException, Exception {
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.length == 0) {
				throw new SQLException("sql is empty");
			}
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < sql.length; i++) {
				String _sql = sql[i];
				Log.WriteToLogFile(0, _sql);
				stmt.addBatch(_sql);
			}
			stmt.executeBatch();
			stmt.close();
			conn.commit();
		} catch (SQLException ex) {
			conn.rollback();
			Log.WriteToLogFile(1,ex.getMessage());
			throw ex;
		}
	}

	public void execute(String sql) throws SQLException {
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.trim().equals("")) {
				throw new SQLException("sql is empty");
			}
			Log.WriteToLogFile(0,sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			stmt = null;
		} catch (SQLException e) {
			Log.WriteToLogFile(0,"DbConn executeUpdate error:" + e.getMessage());
			throw e;
		}
	}

	public int mt(String orgAddr, String destAddr, int feeUserType,
			String feeTerminal, String serviceID, int mtFlag, String msg,
			String linkID, String uniID, java.util.Date sendTime)
			throws SQLException {
		java.sql.Timestamp sendTimeT = null;
		if (sendTime != null) {
			sendTimeT = new java.sql.Timestamp(sendTime.getTime());
		}
		
		int result = 999;
		CallableStatement cs = null;
		if (conn == null) {
			throw new SQLException("connection is null");
		}
		cs = conn.prepareCall("{call xp_sys_MT(?,?,?,?,?,?,?,?,?,?,?)}");
		try {
			cs.setString(1, orgAddr);
			cs.setString(2, destAddr);
			cs.setInt(3, feeUserType);
			cs.setString(4, feeTerminal);
			cs.setString(5, serviceID);
			cs.setInt(6, mtFlag);
			cs.setString(7, msg);
			cs.setString(8, linkID);
			cs.setString(9, uniID);
			cs.setTimestamp(10, sendTimeT);
			cs.registerOutParameter(11, java.sql.Types.INTEGER);
			cs.execute();
			result = cs.getInt(11);
		} finally {
			if (cs != null) {
				cs.close();
			}
		}
		return result;
	}

	public boolean executeUpdate(String sql) {
		boolean result = false;
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.trim().equals("")) {
				throw new SQLException("sql is empty");
			}
			Log.WriteToLogFile(0,sql);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			stmt = null;
			result = true;
		} catch (SQLException e) {
			Log.WriteToLogFile(0,"DbConn executeUpdate error:" + e.getMessage());
			return false;
		}
		return result;
	}

	public boolean executeUpdateByParams(String sql, List params) {
		boolean result = false;
		try {
			if (conn == null) {
				throw new SQLException("connection is null");
			}
			if (sql == null || sql.trim().equals("")) {
				throw new SQLException("sql is empty");
			}
			Log.WriteToLogFile(0,sql);
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int k = 0; k < params.size(); k++) {
					Object param = params.get(k);
					if (param instanceof Integer) {
						pstmt.setInt(k + 1, ((Integer) param).intValue());
					}
					if (param instanceof Long) {
						pstmt.setLong(k + 1, ((Long) param).longValue());
					}
					if (param instanceof String) {
						pstmt.setString(k + 1, param.toString());
					}
					if (param instanceof java.util.Date) {
						pstmt.setTimestamp(k + 1, new java.sql.Timestamp(
								(((java.util.Date) param)).getTime()));
					}
				}
			}
			pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			result = true;
		} catch (SQLException e) {
			Log.WriteToLogFile(0,"DbConn executeUpdateByParams error:" + e.getMessage());
			return false;
		}
		return result;
	}

	public int getRowCount(String sql) throws SQLException {
		int result = 0;
		if (conn == null) {
			return result;
		}
		if (sql == null || sql.trim().equals("")) {
			return result;
		}
		Log.WriteToLogFile(0,sql);
		stmt = conn.createStatement(1004, 1007);
		rs = stmt.executeQuery(sql);
		if (rs != null) {
			rs.last();
			result = rs.getRow();
		}
		rs.close();
		stmt.close();
		return result;
	}

	public ResultSet executeQueryByParams(String sql, List params)
			throws SQLException {
		try {
			conn = this.getConnection();
		} catch (Exception ex) {
		}

		if (conn == null) {
			System.out.println("conn is null!");
		} else {
			System.out.println("sql:" + sql);
			String sqlQ = sql;
			pstmt = conn.prepareStatement(sqlQ);
			if (params != null) {
				for (int k = 0; k < params.size(); k++) {
					Object param = params.get(k);
					if (param instanceof Integer) {
						pstmt.setInt(k + 1, ((Integer) param).intValue());
					}
					if (param instanceof String) {
						pstmt.setString(k + 1, param.toString());
					}
					if (param instanceof Long) {
						pstmt.setLong(k + 1, ((Long) param).longValue());
					}
					if (param instanceof java.util.Date) {
						pstmt.setTimestamp(k + 1, new java.sql.Timestamp(
								(((java.util.Date) param)).getTime()));
					}
				}
			}
			rs = pstmt.executeQuery();
		}
		return rs;
	}

}
