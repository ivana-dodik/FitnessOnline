<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.advising.services.AdvisorService" %>
<%@ page import="org.unibl.etf.ip.advising.entities.Advisor" %>
<%@ page import="org.unibl.etf.ip.advising.beans.AdvisorBean" %>

<jsp:useBean id="advisor" scope="session" class="org.unibl.etf.ip.advising.beans.AdvisorBean">
	<jsp:setProperty name="advisor" property="username" value=""/>
	<jsp:setProperty name="advisor" property="password" value=""/>
</jsp:useBean>

<%  
	if(request.getParameter("logout") != null) {
		session.invalidate();
	}

	else if(advisor != null && advisor.isLoggedIn()) {
		response.sendRedirect("messages.jsp");
	}

	else if(request.getParameter("submit") != null) {
        AdvisorService advisorService = new AdvisorService();
        AdvisorBean beingLoggedIn = null;
        
        if (request.getParameter("username") != null && request.getParameter("password") != null) {
        	beingLoggedIn = advisorService.login(request.getParameter("username"), request.getParameter("password"));
        }
        
        if(beingLoggedIn == null) {
        	%>
        	<script type="text/javascript">
	        	setTimeout(() => {
	       		 	var x = document.getElementById("snackbar");
	       		 	x.className = "show";
	       		  	setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
	        	}, 1);
			</script>
    		<% 
        } 
       
        else {
        	advisor.copyAdvisorBean(beingLoggedIn);
        	response.sendRedirect("messages.jsp");
        }
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
    <link rel="stylesheet" type="text/css" href="/etfbl-ip-advising/styles/login.css">
    <link rel="stylesheet" type="text/css" href="/etfbl-ip-advising/styles/global.css">
    
</head>
<body>
    <%@ include file="./WEB-INF/header.html" %>
    <div class="content special_content">
        <div class="container">
            <div class="card">
                <img class="card-img" src="/etfbl-ip-advising/images/user.png" alt="Login image" />
                <div class="card-body">
                    <h5 class="card-title">User login</h5>
                    <form class="login-form" method="POST" action="login.jsp">
                        <input type="text" required name="username" placeholder="Username" autocomplete="off" />
                        <input type="password" required name="password" placeholder="Password" autocomplete="off" />
                        <button class="btn btn-primary" mat-button type="submit" name="submit">Log in</button>
                    </form>
                </div>
                
            </div>
            <div id="snackbar">Invalid credentials. Failed to log in.</div>
        </div>
    </div>
    <%@ include file="./WEB-INF/footer.html" %>
    
</body>
</html>