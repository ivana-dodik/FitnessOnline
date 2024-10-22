<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.advising.services.MessageService"%>
<%@ page import="org.unibl.etf.ip.advising.services.UserService"%>
<%@ page import="org.unibl.etf.ip.advising.services.AdvisorService"%>
<%@ page import="org.unibl.etf.ip.advising.entities.Message"%>
<jsp:useBean id="advisor" scope="session" class="org.unibl.etf.ip.advising.beans.AdvisorBean"/> 

<%@ page import="java.io.*, jakarta.servlet.http.Part" %> 
<%@ page import="java.util.*" %>

<%
	if(advisor == null || !advisor.isLoggedIn()) {
		response.sendRedirect("login.jsp");
		return;
	}

	String messageIdValue = request.getParameter("id");
	if (messageIdValue == null) {
		System.out.println("NEMA BROJA");
		response.sendRedirect("login.jsp");
		return;
	}

	int messageId = Integer.parseInt(messageIdValue);
    Message message = MessageService.selectById(messageId);
    if (message != null) {
    	MessageService.changeStatus(message);
    	session.setAttribute("message", message);
    }
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Advising</title>
		<link rel="icon" type="image/x-icon" href="favicon.ico">
	   	<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css" rel="stylesheet">
	   	
	   	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
	    	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
			integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	    
	   	
	   	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	   	<link rel="preconnect" href="https://fonts.googleapis.com">
	   	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	   	<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;500&family=Playfair+Display&display=swap"
	       rel="stylesheet">
	   	<link rel="stylesheet" type="text/css" href="/etfbl-ip-advising/styles/global.css">
	   	<link rel="stylesheet" type="text/css" href="/etfbl-ip-advising/styles/message.css">
	</head>
	<body>
		<%@ include file="./WEB-INF/header.html" %>
		<div class="content special_content">
		    <div class="back-btn">
		     <a class="btn btn-secondary" href="messages.jsp">
		    &#8592;&nbsp;Back to all messages
		    </a>
		    </div>
		   
			<a class="card message-card">
			<div class="reply-form">
			 	<h6 class="card-title"><%= UserService.getEmailById(message.getUserId()) %></h6>
			    <p><%= message.getDateTimeCustomFormat() %></p>
			    <p class="card-text"><%= message.getContent() %></p>
			</div>
		    </a>
		    <div class="card message-card">
		        <form class="reply-form" action="SubmitEmailServlet" method="POST" enctype="multipart/form-data">
                <textarea class="form-control" required placeholder="Reply to this message here.." name="message-content"></textarea>
                <br>
                <div >
                    <label for="file">Attach File:</label>
                    <input type="file" class="form-control-file" id="file" name="file">
                </div>
                <br>
                <textarea class="form-control" placeholder="Additional description..." name="file-description"></textarea>
                
                <input type="hidden" name="id" value="<%=message.getId()%>">
                
                <button class="btn btn-primary" type="submit" name="submit">
                  Submit
                </button>
            </form>
      		</div>
		</div>
	    <%@ include file="./WEB-INF/footer.html" %>
	</body>
</html>