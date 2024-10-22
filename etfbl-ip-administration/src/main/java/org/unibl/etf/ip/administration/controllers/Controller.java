package org.unibl.etf.ip.administration.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.unibl.etf.ip.administration.beans.AdvisorBean;
import org.unibl.etf.ip.administration.dao.AttributeDao;
import org.unibl.etf.ip.administration.dao.CategoryDao;
import org.unibl.etf.ip.administration.dao.AdvisorDao;
import org.unibl.etf.ip.administration.dao.UserDao;
import org.unibl.etf.ip.administration.dto.User;
import org.unibl.etf.ip.administration.dto.Category;
import org.unibl.etf.ip.administration.dto.Advisor;
import org.unibl.etf.ip.administration.dto.Attribute;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;


@WebServlet("/")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String address = "/WEB-INF/pages/login.jsp";
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if ("login".equals(action)) {
			String providedUsername = request.getParameter("username");
			String providedPassword = request.getParameter("password");
			if (providedUsername != null && providedPassword != null) {
				AdvisorBean loggedInAdvisor = AdvisorDao.login(providedUsername, providedPassword);
				if (loggedInAdvisor != null) {
					session.setAttribute("loggedInAdvisor", loggedInAdvisor);
					address = "/WEB-INF/pages/menu.jsp";
				}
				
				else {
					address = "/WEB-INF/pages/login.jsp?invalid=true";
				}
			}
		}
		
		else if ("redirect".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				address = "/WEB-INF/pages/" + request.getParameter("path") + ".jsp";
			}
		}
		
		else if("attributes".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			String categoryId = request.getParameter("category-id");
			if (loggedInAdvisor.isLoggedIn() && categoryId != null) {
				address = "/WEB-INF/pages/attributes.jsp?category-id=" + categoryId;
			}
		}
		
		else if ("delete".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String userId = request.getParameter("id");
				UserDao.delete(Integer.valueOf(userId));
				address = "/WEB-INF/pages/users.jsp";
			}
		}
		
		else if ("delete-advisor".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String advisorId = request.getParameter("id-advisor");
				AdvisorDao.deleteAdvisor(Integer.valueOf(advisorId));
				address = "/WEB-INF/pages/addAdvisor.jsp";
			}
		}
		
		else if ("update".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				int userId = Integer.valueOf(request.getParameter("id"));
				String firstName = request.getParameter("first-name");
				String lastName = request.getParameter("last-name");
				String city = request.getParameter("city");
				String username = request.getParameter("username");
				String email = request.getParameter("email");
				Boolean activated = "Activated".equals(request.getParameter("activated"));
				Boolean deleted = "Deleted".equals(request.getParameter("deleted"));
				String avatarURL = "No avatar".equals(request.getParameter("avatar-url")) ? null : request.getParameter("avatar-url");

				User updatedUser = new User(userId, firstName, lastName, city, username, email, activated, deleted, avatarURL);
				UserDao.update(updatedUser);
					
				address = "/WEB-INF/pages/users.jsp";
			}
		}
		
		else if ("add-user".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String firstName = request.getParameter("first-name");
				String lastName = request.getParameter("last-name");
				String city = request.getParameter("city");
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				Boolean activated = "Activated".equals(request.getParameter("activated"));
				Boolean deleted = "Deleted".equals(request.getParameter("deleted"));
				String avatarURL = "".equals(request.getParameter("avatar-url")) ? null : request.getParameter("avatar-url");
				
				User newUser = new User(firstName, lastName, city, username, password, email, activated, deleted, avatarURL);
				if(UserDao.create(newUser)) {
					address = "/WEB-INF/pages/users.jsp";
				}
			}
		}
		
		else if ("add-advisor".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String firstName = request.getParameter("first-name-advisor");
				String lastName = request.getParameter("last-name-advisor");
				String username = request.getParameter("username-advisor");
				String password = request.getParameter("password-advisor");
				
				Advisor newAdvisor = new Advisor(firstName, lastName, username, password);
				if(AdvisorDao.create(newAdvisor)) {
					address = "/WEB-INF/pages/addAdvisor.jsp";
				}
			}
		}
		
		else if ("logout".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				session.invalidate();
				loggedInAdvisor.setLoggedIn(false);
				address = "/WEB-INF/pages/login.jsp";
			}
		}
		
		else if ("logs".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				address = "/WEB-INF/pages/logs.jsp";
			}
		}
		
		else if ("delete-category".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String categoryId = request.getParameter("id");
				CategoryDao.delete(Integer.valueOf(categoryId));
				address = "/WEB-INF/pages/categories.jsp";
			}
		}
		
		else if ("update-category".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				int categoryId = Integer.valueOf(request.getParameter("id"));
				String categoryName = request.getParameter("category-name");
				String parentId = request.getParameter("parent-id");
				Category updatedCategory = new Category(categoryId, categoryName, Integer.parseInt(parentId));
				CategoryDao.update(updatedCategory);
				address = "/WEB-INF/pages/categories.jsp";
			}
		}
		
		else if ("add-category".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String categoryName = request.getParameter("category-name");
				String parentId = request.getParameter("parent-id");
				Category category = new Category(categoryName, Integer.parseInt(parentId));
				if(CategoryDao.create(category)) {
					address = "/WEB-INF/pages/categories.jsp";
				}
			}
		}
		
		else if ("add-attribute".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String attributeName = request.getParameter("add-attribute-name");
				//String attributeType = request.getParameter("add-attribute-type");
				String attributeCategoryId = request.getParameter("add-attribute-category");
				Attribute newAttribute = new Attribute(attributeName, Integer.valueOf(attributeCategoryId));
				AttributeDao.create(newAttribute);
				address = "/WEB-INF/pages/attributes.jsp?category-id=" + attributeCategoryId;
			}
		}
		
		else if ("update-attribute".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				int attributeId = Integer.valueOf(request.getParameter("id"));
				String attributeName = request.getParameter("attribute-name");
				//String attributeType = request.getParameter("attribute-type");
				String attributeCategoryId = request.getParameter("attribute-category");
				Attribute updatedAttribute = new Attribute(attributeId, attributeName);
				AttributeDao.update(updatedAttribute);
				address = "/WEB-INF/pages/attributes.jsp?category-id=" + attributeCategoryId;
			}
		}
		
		else if ("delete-attribute".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String attributeId = request.getParameter("id");
				String attributeCategoryId = request.getParameter("attribute-category");
				AttributeDao.delete(Integer.valueOf(attributeId));
				address = "/WEB-INF/pages/attributes.jsp?category-id=" + attributeCategoryId;
			}
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
