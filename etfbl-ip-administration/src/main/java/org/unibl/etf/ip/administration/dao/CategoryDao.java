package org.unibl.etf.ip.administration.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.unibl.etf.ip.administration.dao.utils.ConnectionPool;
import org.unibl.etf.ip.administration.dao.utils.ServiceUtil;
import org.unibl.etf.ip.administration.dto.Attribute;
import org.unibl.etf.ip.administration.dto.Category;

public class CategoryDao {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	//private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.category";
    private static final String SQL_SELECT_ALL = "SELECT * FROM fitness_online_db.category WHERE deleted = false";
	private static final String SQL_SELECT_ONE = "SELECT * FROM fitness_online_db.category WHERE id = ?";
	private static final String SQL_SELECT_BY_NAME = "SELECT * FROM fitness_online_db.category WHERE name = ?";
    private static final String SQL_UPDATE = "UPDATE fitness_online_db.category c SET c.name = ?, c.parent_category_id = ? WHERE c.id = ?";
	// private static final String SQL_DELETE = "DELETE FROM fitness_online_db.category c WHERE c.id = ?";
    private static final String SQL_DELETE = "UPDATE fitness_online_db.category c SET c.deleted = true WHERE c.id = ?";
	private static final String SQL_CREATE="INSERT INTO fitness_online_db.category ( parent_category_id, name) VALUES (?, ?) ";
	
	public static ArrayList<Category> selectAll() {
		ArrayList<Category> retVal = new ArrayList<Category>();
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = {};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ALL, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArrayList<Attribute> attributes = new ArrayList<Attribute>();
				attributes = AttributeDao.selectAllFromCategory(rs.getInt("id"));
				retVal.add(new Category(rs.getInt("id"), rs.getInt("parent_category_id"), rs.getString("name"), rs.getBoolean("deleted"), attributes));
			}
			pstmt.close();
		} catch (SQLException exp) {
			exp.printStackTrace();
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	} 
	
	
	public static Category selectById(int id) {
		Category retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { id };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_ONE, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArrayList<Attribute> attributes = new ArrayList<Attribute>();
				attributes = AttributeDao.selectAllFromCategory(rs.getInt("id"));
				retVal = new Category(rs.getInt("id"), rs.getInt("parent_category_id"), rs.getString("name"), rs.getBoolean("deleted"), attributes);
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
				ArrayList<Attribute> attributes = AttributeDao.selectAllFromCategory(id);
				for(Attribute a  : attributes) {
					AttributeDao.delete(a.getId());
				}
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
	
	public static boolean update(Category category) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { category.getName(), category.getParentId() == 0 ? null : category.getParentId(), category.getId() };
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
	
	public static boolean create(Category category) {
		boolean retVal = false;
		Connection connection = null;
		Object values[] = { category.getParentId() == 0 ? null : category.getParentId(), category.getName()};
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection, SQL_CREATE, false, values);
			int affectedRows = pstmt.executeUpdate();
			if (affectedRows == 0) {
				retVal = false;
			}
				
			else {
				retVal = true;
				if(category.getAttributes() != null)
				for(Attribute a : category.getAttributes()) {
					AttributeDao.create(a);
				}
			}
			
			pstmt.close();
		} catch (SQLException e) {
			retVal = false;
		} finally {
			connectionPool.checkIn(connection);
		}
		return retVal;
	}
	
	public static ArrayList<Category> getAvailableCategories(Category cbe) { 
		ArrayList<Category> allCategories = selectAll(); 
		
		ArrayList<Category> availableCategories = new ArrayList<Category>(); 
		for(Category c : allCategories) {
			if(canBeParent(c, cbe)) {
				availableCategories.add(c);
			}
		}
		
		Category parent = selectById(cbe.getParentId());
		if (!(availableCategories.contains(parent)) && parent != null) {
			availableCategories.add(parent);
		}
		return availableCategories;
	}
	
	private static boolean canBeParent(Category category, Category cbe) {		
		
		if (isNull(category) || isNull(cbe)) {
			return false;
		}		
				
		if (areSameCategory(category, cbe)) {
            return false;
        }
		
    	if (isCategoryDirectChild(category, cbe)) {
    		return false;
    	}
    	
    	if (isRootCategory(category)) {
    		return true;
    	}
    	
    	Category parentCategory = CategoryDao.selectById(category.getParentId());
    	return canBeParent(parentCategory, cbe);
    	
	}
	
	private static boolean isNull(Object object) {
		return object == null;
	}
	
	private static boolean areSameCategory(Category c1, Category c2) {
		return c1.getId() == c2.getId();
	}
	
	private static boolean isCategoryDirectChild(Category category, Category cbe) {
		return category.getParentId() == cbe.getId(); // && category.getId() != cbe.getId();
	}
	
	private static boolean isRootCategory(Category category) {
		return category.getParentId() == 0;
	}


	public static Category selectByName(String name) {
		Category retVal = null;
		Connection connection = null;
		ResultSet rs = null;
		Object values[] = { name };
		try {
			connection = connectionPool.checkOut();
			PreparedStatement pstmt = ServiceUtil.prepareStatement(connection,
					SQL_SELECT_BY_NAME, false, values);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ArrayList<Attribute> attributes = new ArrayList<Attribute>();
				attributes = AttributeDao.selectAllFromCategory(rs.getInt("id"));
				retVal = new Category(rs.getInt("id"), rs.getInt("parent_category_id"), rs.getString("name"), rs.getBoolean("deleted"), attributes);
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
