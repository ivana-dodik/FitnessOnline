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
		<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/menu.css">
		<script>
		function redirect(path) { 
			document.getElementById('formAction').value = path;
			document.getElementById('menuForm').submit();
		}
		</script>
	</head>
	<body>
		<%@ include file="./header.html" %>
		<div class="content special-content">
		   <div class="menu">
			<form id="menuForm" method="POST" action="?action=redirect">
					<div class="card"><a onclick="redirect('categories')">Categories</a></div>
					<div class="card"><a onclick="redirect('users')">Users</a></div>
					<div class="card"><a onclick="redirect('logs')">Logs</a></div>
					<div class="card"><a onclick="redirect('addAdvisor')">Add advisor</a></div>
				<input type="hidden" id="formAction" name="path" value="">
			</form>
			</div>
		</div>
		<%@ include file="./footer.html" %>
	</body>
</html>
