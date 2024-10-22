package org.unibl.etf.ip.administration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ip.administration.dao.utils.ConnectionPool;
import org.unibl.etf.ip.administration.dao.utils.ServiceUtil;
import org.unibl.etf.ip.administration.dto.Log;

public class LogDao {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.log ORDER BY date_time DESC";
	private static final String SQL_SELECT_ONE = "SELECT * FROM fitness_online_db.log WHERE id = ?";
	
public LogDao() {}
	
	public static Log selectById(int id) {
		Log retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ONE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Log(rs.getInt("id"), rs.getTimestamp("date_time").toString(), rs.getString("content"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<Log> selectAll() {
		ArrayList<Log> retVal = new ArrayList<Log>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Log(rs.getInt("id"),  rs.getTimestamp("date_time").toString(), rs.getString("content")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}  
}
