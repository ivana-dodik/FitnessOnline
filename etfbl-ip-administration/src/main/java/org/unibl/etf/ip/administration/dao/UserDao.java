package org.unibl.etf.ip.administration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ip.administration.dao.utils.ConnectionPool;
import org.unibl.etf.ip.administration.dao.utils.ServiceUtil;
import org.unibl.etf.ip.administration.dto.User;

public class UserDao {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.user WHERE deleted = false";
	private static final String SQL_SELECT_ONE = "SELECT * FROM fitness_online_db.user WHERE id = ?";
	private static final String SQL_SELECT_ONE_BY_USERNAME = "SELECT * FROM fitness_online_db.user WHERE username = ?";
	private static final String SQL_UPDATE = "UPDATE fitness_online_db.user u SET u.first_name = ?, u.last_name = ?, u.city = ?, u.username = ?, u.email = ?, u.activated = ?, u.deleted=?, u.avatar_url = ? WHERE u.id = ?";
	private static final String SQL_DELETE = "UPDATE fitness_online_db.user u SET deleted = true WHERE id = ?";
	private static final String SQL_CREATE="INSERT INTO fitness_online_db.user (first_name, last_name, city, username, password, email, activated, deleted, avatar_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}
	
	public static User selectById(int id) {
		User retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ONE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("city"), 
					rs.getString("username"), rs.getString("email"), rs.getBoolean("activated"), rs.getBoolean("deleted"), rs.getString("avatar_url"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static User selectByUsername(String username) {
		User retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { username };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ONE_BY_USERNAME, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("city"), 
					rs.getString("username"), rs.getString("email"), rs.getBoolean("activated"), rs.getBoolean("deleted"), rs.getString("avatar_url"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<User> selectAll() {
		ArrayList<User> retVal = new ArrayList<User>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("city"), 
					rs.getString("username"), rs.getString("email"), rs.getBoolean("activated"), rs.getBoolean("deleted"), rs.getString("avatar_url")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	} 
	
	public static boolean delete(int id) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				retVal = false;
			}
				
			else {
				retVal = true;
				// user.setDeleted(true);
			}
			
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean update(User user) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { user.getFirstName(), user.getLastName(), user.getCity(), user.getUsername(), user.getEmail(), user.isActivated(), user.isDeleted(), user.getAvatarURL(), user.getId() };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_UPDATE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				retVal = false;
			}
				
			else {
				retVal = true;
			}
			
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static boolean create(User user) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { user.getFirstName(), user.getLastName(), user.getCity(), user.getUsername(), user.getPassword(), user.getEmail(), user.isActivated(), user.isDeleted(), user.getAvatarURL()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_CREATE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				retVal = false;
			}
				
			else {
				retVal = true;
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
