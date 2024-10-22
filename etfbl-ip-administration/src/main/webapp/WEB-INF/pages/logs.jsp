<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.administration.dto.Log" %>
<%@ page import="org.unibl.etf.ip.administration.dao.LogDao" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>IP Fitness Online Administration</title>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
		<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/global.css">
		<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/logs.css">
		<script>
			function getDateTimeCustomFormat(dateTime, elementId) {
			    var timestamp = new Date(dateTime).getTime();
			    var dt = new Date(timestamp);
			    var formatter = new Intl.DateTimeFormat('en', { 
			        year: 'numeric', 
			        month: '2-digit', 
			        day: '2-digit', 
			        hour: '2-digit', 
			        minute: '2-digit', 
			        hour12:true
			    });
			    document.getElementById(elementId).innerHTML = formatter.format(dt);
			}
			
			window.onload = function() {
			    <% for(Log log : LogDao.selectAll()) { %>
			       var dateTimeT = "<%= log.getDateTime() %>";
			       var elementId = "dateTimeHeader<%= log.getId() %>";
			       getDateTimeCustomFormat(dateTimeT, elementId);
			   <% } %>
			};
		</script>
	</head>
	<body>
		<%@ include file="./header-with-navigation.html" %>
		<div class="content">
		    <div class="card logs-section">
		        <label>Logs</label>
		        <% for(Log log : LogDao.selectAll()) { %>
		        <a class="card single-log-card">
		            <h6 id="dateTimeHeader<%= log.getId() %>" class="card-title"></h6>
		            <p class="card-text"><%= log.getContent() %></p>
		        </a>
		        <% } %>
		    </div>
		</div>
		<%@ include file="./footer.html" %>
	</body>
</html>
