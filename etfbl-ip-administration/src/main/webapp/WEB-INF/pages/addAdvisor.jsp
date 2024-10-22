<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.administration.dto.Advisor" %>
<%@ page import="org.unibl.etf.ip.administration.dao.AdvisorDao" %>
<!DOCTYPE html>
<html>
<head>
    <title>IP Fitness Online Administration - Add Advisor</title>
    <meta charset="ISO-8859-1">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/global.css">
	<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/users.css">
	<script>	
		function selectCurrentAdvisor(advisorId) {
			document.getElementById("currentSelectedAdvisor").value = advisorId;
		}
		
		async function validateAndSubmitAddAdvisorForm() {
			let username = document.getElementById("addAdvisorUsernameInput").value;
			let response = await fetch('http://localhost:8081/etfbl-ip-administration/validate?action=validate-advisor-username&username-advisor=' + username);
			let jsonData = await response.json();
			if (jsonData == "1") {
				alert("Username is already taken!")
				return;
			}
			
			else {
				document.getElementById("addAdvisorForm").submit();
			}
		}
	</script>
</head>
<body>
    <%@ include file="./header-with-navigation.html" %>
	<div class="container-xl content container">
		<div class="table-responsive">
			<div class="table-wrapper">
				<div class="table-title">
					<div class="row">
						<div class="col-sm-6">
							<h2>Manage Advisors</h2>
						</div>
						<div class="col-sm-6">
							<a href="#addAdvisorModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Advisor</span></a>
									
						</div>
					</div>
				</div>
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
					 <% for(Advisor advisor : AdvisorDao.selectAll()) { %>
						<tr>
							<td><%=advisor.getFirstName() %></td>
							<td><%=advisor.getLastName() %></td>
							<td><%=advisor.getUsername() %></td>
							<td>
								<a href="#deleteAdvisorModal" class="delete" data-toggle="modal" onclick="selectCurrentAdvisor(<%= advisor.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
							</td>
						</tr>		
						<% } %>
					</tbody>
				</table>
			</div>
		</div>        
	</div>
	<!-- Add Modal HTML -->
	<div id="addAdvisorModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="addAdvisorForm" method="POST" action="?action=add-advisor">
					<div class="modal-header">						
						<h4 class="modal-title">Add Advisor</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">					
						<div class="form-group">
							<label>First Name</label>
							<input type="text" class="form-control" name="first-name-advisor" required>
						</div>
						<div class="form-group">
							<label>Last Name</label>
							<input type="text" class="form-control" name="last-name-advisor" required>
						</div>
						<div class="form-group">
							<label>Username</label>
							<input id="addAdvisorUsernameInput" type="text" class="form-control" name="username-advisor" required>
						</div>
						<div class="form-group">
							<label>Password</label>
							<input type="password" class="form-control" name="password-advisor" required>
						</div>	
					</div>
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
						<input type="button" class="btn btn-success" value="Add" onclick="validateAndSubmitAddAdvisorForm()">
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Delete Modal HTML -->
	<div id="deleteAdvisorModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<form method="POST" action="?action=delete-advisor">
					<div class="modal-header">						
						<h4 class="modal-title">Delete Advisor</h4>
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">					
						<p>Are you sure you want to delete this advisor?</p>
						<p class="text-warning"><small>This action cannot be undone.</small></p>
					</div>
					<input id="currentSelectedAdvisor" type=hidden name=id-advisor> 
					<div class="modal-footer">
						<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
						<input type="submit" class="btn btn-danger" value="Delete" >
					</div>
				</form>
			</div>
		</div>
	</div>
 <%@ include file="./footer.html" %>
</body>
</html>
