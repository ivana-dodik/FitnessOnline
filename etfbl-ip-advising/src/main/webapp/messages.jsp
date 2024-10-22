<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.advising.services.UserService" %>
<%@ page import="org.unibl.etf.ip.advising.services.MessageService" %>
<%@ page import="org.unibl.etf.ip.advising.entities.Message" %>
<%@ page import="org.unibl.etf.ip.advising.beans.AdvisorBean" %>
<jsp:useBean id="advisor" scope="session" class="org.unibl.etf.ip.advising.beans.AdvisorBean"/>

<%
	if(advisor == null || !advisor.isLoggedIn()) {
		response.sendRedirect("login.jsp");
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
    	<link rel="stylesheet" type="text/css" href="/etfbl-ip-advising/styles/messages.css">
    	<script>
		   document.addEventListener("DOMContentLoaded", function() {
		      var searchInput = document.getElementById("search-input");
		      searchInput.addEventListener("keyup", function() {
		         var searchContent = searchInput.value.trim(); 
		         var messageCards = document.getElementsByClassName("single-message-card");
		         
		         for (var i = 0; i < messageCards.length; i++) {
		            var card = messageCards[i];
		            var messageText = card.querySelector(".card-text").textContent;
		            
		            if (searchContent === "" || messageText.includes(searchContent)) {
		               card.style.display = "block"; 
		            } else {
		               card.style.display = "none"; 
		            }
		         }
		      });
		   });
		</script>
    	
    </head>
	<body>
		<%@ include file="./WEB-INF/header.html" %>
		<div class="content">
			<div class="search-bar">
  				<div class="search-input-and-icon">
	   				<input type="search" id="search-input" placeholder="Search messages" class="form-control" name="searchContent" />
	    			<button type="submit" class="search-icon-btn search-button" ></button>
 			 	</div>
			</div>
			<div class="card messages-section">
     	    	<label>Received messages</label>
     	    	<% for (Message message : MessageService.selectAll()) {
     	    		String searchContent = request.getParameter("searchContent");
     	    		String messageClass = "read-message";
     	    		 if (searchContent == null || message.getContent().contains(searchContent)) {
     	    		    messageClass = message.isRead() ? "read-message" : "unread-message";
     	    		 }
     	    		%>
     	    	
     	    	<a class="card single-message-card <%= messageClass %>" href="message.jsp?id=<%= message.getId() %>">
        			<h6 class="card-title"><%= UserService.getEmailById(message.getUserId()) %></h6>
        			<p><%= message.getDateTimeCustomFormat() %></p>
        			<p class="card-text"><%= message.getContent() %></p>
      			</a>
      			<% } %>
           </div>
        </div>
	    <%@ include file="./WEB-INF/footer.html" %>
	</body>
</html>