package org.unibl.etf.ip.administration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ip.administration.beans.AdvisorBean;
import org.unibl.etf.ip.administration.dao.utils.ConnectionPool;
import org.unibl.etf.ip.administration.dao.utils.ServiceUtil;
import org.unibl.etf.ip.administration.dto.Advisor;

public class AdvisorDao {

	public AdvisorDao() {
		// TODO Auto-generated constructor stub
	}
	
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static String SQL_SELECT_BY_CREDENTIALS = "SELECT * FROM  fitness_online_db.advisor_account where username = ? AND password = ? AND is_admin = true";
	
	private static final String SQL_CREATE="INSERT INTO fitness_online_db.advisor_account (first_name, last_name, username, password, is_admin) VALUES (?, ?, ?, ?, 0)";
	private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.advisor_account WHERE is_admin = false";
	private static final String SQL_SELECT_ONE_BY_USERNAME = "SELECT * FROM fitness_online_db.advisor_account WHERE username = ? AND is_admin = false";
	private static final String SQL_DELETE = "DELETE FROM fitness_online_db.advisor_account WHERE id = ?";
	
	public static AdvisorBean login(String username, String password) {
		String passwordHash = hashPassword(password);
		return loginAdvisor(username, passwordHash);
	}
	
	private static String hashPassword(String password) {
		return password;
	}
	
	private static AdvisorBean loginAdvisor(String username, String password) {
		
		Advisor advisor = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { username, password };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_BY_CREDENTIALS, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				advisor = new Advisor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("password"), rs.getBoolean("is_admin"));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return mapAdvisorToAdvisorBean(advisor);
	}
	
	private static AdvisorBean mapAdvisorToAdvisorBean(Advisor advisor) {
		if(advisor == null) {
			return null;
		}
		
		AdvisorBean advisorBean = new AdvisorBean();
		advisorBean.setFirstName(advisor.getFirstName());
		advisorBean.setLastName(advisor.getLastName());
		advisorBean.setUsername(advisor.getUsername());
		advisorBean.setPassword(advisor.getPassword());
		advisorBean.setLoggedIn(true);
		return advisorBean;
	}
	
	public static boolean create(Advisor advisor) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { advisor.getFirstName(), advisor.getLastName(), advisor.getUsername(), advisor.getPassword()};
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
	
	public static ArrayList<Advisor> selectAll() {
		ArrayList<Advisor> retVal = new ArrayList<Advisor>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Advisor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static Advisor selectByUsername(String username) {
		Advisor retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { username };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_ONE_BY_USERNAME, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Advisor(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"));
				//System.out.print(retVal);
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	
	public static boolean deleteAdvisor(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean success = false;

        try {
            connection = connectionPool.checkOut();
            preparedStatement = ServiceUtil.prepareStatement(connection, SQL_DELETE, false, id);
            int rowsAffected = preparedStatement.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                ConnectionPool.getConnectionPool().checkIn(connection);
            }
        }

        return success;
    }
}
