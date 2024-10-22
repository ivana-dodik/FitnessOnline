package org.unibl.etf.ip.administration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.unibl.etf.ip.administration.dao.utils.ConnectionPool;
import org.unibl.etf.ip.administration.dao.utils.ServiceUtil;
import org.unibl.etf.ip.administration.dto.Attribute;

public class AttributeDao {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.attribute WHERE deleted = false";
	private static final String SQL_SELECT_ALL_FROM_CATEGORY = "SELECT * FROM fitness_online_db.attribute WHERE category_id = ? AND deleted = false";
	private static final String SQL_SELECT_ONE = "SELECT * FROM fitness_online_db.attribute WHERE id = ?";
	private static final String SQL_SELECT_BY_NAME_AND_CATEGORY_ID = "SELECT * FROM fitness_online_db.attribute WHERE name = ? AND category_id = ?";
	private static final String SQL_UPDATE = "UPDATE fitness_online_db.attribute a SET a.name = ?,  a.type = ? WHERE a.id = ?";
	// private static final String SQL_DELETE = "DELETE FROM fitness_online_db.attribute a WHERE a.id = ?";
	private static final String SQL_DELETE = "UPDATE fitness_online_db.attribute a SET a.deleted = true WHERE a.id = ? ";
	//private static final String SQL_CREATE = "INSERT INTO fitness_online_db.attribute (name, type, category_id, deleted) VALUES (?, ?, ?, 0)";
	private static final String SQL_CREATE = "INSERT INTO fitness_online_db.attribute (name, category_id, deleted) VALUES (?, ?, 0)";

	public static ArrayList<Attribute> selectAll() {
		ArrayList<Attribute> retVal = new ArrayList<Attribute>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Attribute(rs.getInt("id"), rs.getString("name"),
						rs.getInt("category_id"), rs.getBoolean("deleted")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static ArrayList<Attribute> selectAllFromCategory(int categoryId) {
		ArrayList<Attribute> retVal = new ArrayList<Attribute>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { categoryId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_ALL_FROM_CATEGORY, false,
					values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal.add(new Attribute(rs.getInt("id"), rs.getString("name"),
						rs.getInt("category_id"), rs.getBoolean("deleted")));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static Attribute selectById(int id) {
		Attribute retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_ONE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Attribute(rs.getInt("id"), rs.getString("name"),
						rs.getInt("category_id"), rs.getBoolean("deleted"));
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
				// attribute.setDeleted(true);
			}

			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}

	public static boolean update(Attribute attribute) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { attribute.getName(), attribute.getId() };
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

	public static boolean create(Attribute attribute) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { attribute.getName(), attribute.getCategoryId() };
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

	public static Attribute selectByNameAndCategoryId(String name, int categoryId) {
		Attribute retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { name, categoryId };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_SELECT_BY_NAME_AND_CATEGORY_ID,
					false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retVal = new Attribute(rs.getInt("id"), rs.getString("name"),
						rs.getInt("category_id"), rs.getBoolean("deleted"));
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
