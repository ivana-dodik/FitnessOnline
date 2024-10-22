package org.unibl.etf.ip.administration.controllers;

import org.unibl.etf.ip.administration.beans.AdvisorBean;
import org.unibl.etf.ip.administration.dao.AdvisorDao;
import org.unibl.etf.ip.administration.dao.AttributeDao;
import org.unibl.etf.ip.administration.dao.CategoryDao;
import org.unibl.etf.ip.administration.dao.UserDao;
import org.unibl.etf.ip.administration.dto.Attribute;
import org.unibl.etf.ip.administration.dto.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/validate")
public class ValidationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ValidationController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		
		if ("validate-username".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String username = request.getParameter("username");
				if (UserDao.selectByUsername(username) == null) {
					// User does not exist.
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("0");
					response.getWriter().flush();
				}
				
				else {
					// User exists.
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("1");
					response.getWriter().flush();
				}
			}
			return;
		}
		
		else if("validate-advisor-username".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String username = request.getParameter("username-advisor");
				if (AdvisorDao.selectByUsername(username) == null) {
					// Advisor does not exist.
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("0");
					response.getWriter().flush();
				}
				
				else {
					// Advisor exists.
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("1");
					response.getWriter().flush();
				}
			}
			return;
		}
		
		else if("validate-category".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String name = request.getParameter("category-name");
				String parentId = request.getParameter("parent-id");
				int id = 0;
				if(parentId != null) {
					id = Integer.parseInt(parentId);
				}
				
				Category category = CategoryDao.selectByName(name);
				if (category == null) {
					// Category does not exist.
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("0");
					response.getWriter().flush();
				}
				
				else {
					// Category exists.
					if(category.getParentId() == id) {
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write("1");
						response.getWriter().flush();
					}
					
					else {
						response.setStatus(HttpServletResponse.SC_OK);
						response.getWriter().write("0");
						response.getWriter().flush();
					}
				}
			}
			return;
		}
		
		else if("validate-add-attribute".equals(action)) {
			AdvisorBean loggedInAdvisor = (AdvisorBean) session.getAttribute("loggedInAdvisor");
			if (loggedInAdvisor.isLoggedIn()) {
				String name = request.getParameter("add-attribute-name");
				//String type = request.getParameter("add-attribute-type");
				String category = request.getParameter("add-attribute-category");
				
			    Attribute attribute = AttributeDao.selectByNameAndCategoryId(name, Integer.valueOf(category));
			    if(attribute != null) {
			    	System.out.println("POSTOJI, NE MOZE");
			    	response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("1");
					response.getWriter().flush();	
			    }
			    else {
					response.setStatus(HttpServletResponse.SC_OK);
					response.getWriter().write("0");
					response.getWriter().flush();
			    }
				
			}
			return;
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
