package org.unibl.etf.ip.advising.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import org.unibl.etf.ip.advising.entities.Message;
import org.unibl.etf.ip.advising.utils.*;

public class MessageService {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.message WHERE is_read = false ORDER BY date_time DESC";
	private static final String SQL_SELECT_ONE = "SELECT * FROM fitness_online_db.message WHERE id = ?";
	private static final String SQL_UPDATE = "UPDATE fitness_online_db.message m SET m.is_read = 1 WHERE m.id = ?";
	
	public static ArrayList<Message> selectAll() {
		ArrayList<Message> retVal = new ArrayList<Message>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Message(rs.getInt("id"), rs.getInt("user_id"), rs.getString("content"), rs.getBoolean("is_read"), rs.getTimestamp("date_time").toString()));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static Message selectById(int id){
		Message retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ONE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Message(rs.getInt("id"), rs.getInt("user_id"), rs.getString("content"), rs.getBoolean("is_read"), rs.getTimestamp("date_time").toString());
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	

	public static boolean changeStatus(Message message) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { message.getId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_UPDATE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				retVal = false;
			}
				
			else {
				retVal = true;
				message.setRead(true);
			}
			
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

}
