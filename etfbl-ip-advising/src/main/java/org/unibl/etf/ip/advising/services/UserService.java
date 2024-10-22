package org.unibl.etf.ip.advising.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.unibl.etf.ip.advising.utils.*;

public class UserService {

	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private static final String SQL_GET_EMAIL = "SELECT u.email FROM fitness_online_db.user u WHERE u.id = ?;";
	
	
	public UserService() {
		// TODO Auto-generated constructor stub
	}
	
	public static String getEmailById(int id){
		String retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_GET_EMAIL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new String(rs.getString("email"));
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
