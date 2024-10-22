<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>IP Fitness Online Administration</title>
    <link rel="icon" type="image/x-icon" href="/etfbl-ip-administration/images/favicon.ico">
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
    <link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/login.css">
    <link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/global.css">
</head>
<body>
    <%@ include file="./header.html" %>
    <div class="content">
        <div class="container">
            <div class="card">
                <img class="card-img" src="/etfbl-ip-administration/images/user.png" alt="Login image" />
                <div class="card-body">
                    <h5 class="card-title">User login</h5>
                    <form class="login-form" method="POST" action="?action=login">
                        <input type="text" required name="username" placeholder="Username" autocomplete="off" />
                        <input type="password" required name="password" placeholder="Password" autocomplete="off" />
                        <button class="btn btn-primary" mat-button type="submit">Log in</button>
                    </form>
                </div>
                
            </div>
            <div id="snackbar">Invalid credentials. Failed to log in.</div>
        </div>
    </div>
    <%@ include file="./footer.html" %>
    <% 
     if("true".equals(request.getParameter("invalid"))) {
        	%>
        	<script type="text/javascript">
	        	setTimeout(() => {
	       		 	var x = document.getElementById("snackbar");
	       		 	x.className = "show";
	       		  	setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
	        	}, 1);
			</script>
    		<% 
        } %>
</body>
</html>
