<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.administration.dto.User" %>
<%@ page import="org.unibl.etf.ip.administration.dao.UserDao" %>
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
		<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/users.css">
		<script>	
			function selectCurrentUser(userId) {
				document.getElementById("currentSelectedUser").value = userId;
			}
			
			async function validateAndSubmitAddUserForm() {
				let username = document.getElementById("addUserUsernameInput").value;
				let response = await fetch('http://localhost:8081/etfbl-ip-administration/validate?action=validate-username&username=' + username);
				let jsonData = await response.json();
				if (jsonData == "1") {
					alert("Username is already taken!")
					return;
				}
				
				else {
					document.getElementById("addUserForm").submit();
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
						<h2>Manage Users</h2>
					</div>
					<div class="col-sm-6">
						<a href="#addUserModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New User</span></a>
								
					</div>
				</div>
			</div>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>City</th>
						<th>Username</th>
						<th>Email</th>
						<th>Activated</th>
						<th>Deleted</th>
						<th>Avatar URL</th>
					</tr>
				</thead>
				<tbody>
				 <% for(User user : UserDao.selectAll()) { %>
					<tr>
						<td><%=user.getFirstName() %></td>
						<td><%=user.getLastName() %></td>
						<td><%=user.getCity() %></td>
						<td><%=user.getUsername() %></td>
						<td><%=user.getEmail() %></td>
						<td>
							<span class="custom-checkbox">
								<input type="checkbox" disabled id="activated<%=user.getId()%>" name="options[]" value="1" <%=(user.isActivated()) ? "checked" : "" %> >
								<label for="activated<%=user.getId()%>"></label>
							</span>
						</td>
						<td>
							<span class="custom-checkbox">
								<input type="checkbox" disabled id="deleted<%=user.getId()%>" name="options[]" value="1" <%=(user.isDeleted()) ? "checked" : "" %> >
								<label for="deleted<%=user.getId()%>"></label>
							</span>
						</td>
						<td>
						<a target="_blank" <%=user.hasAvatar() ? "href=" + "http://localhost:9000/api/v1" + user.getAvatarURL() : ("/" + "class='disabled'")  %>>
						<%=user.hasAvatar() ? "Link" : "No avatar" %>
						</a>
						</td>
						<td>
							<a href="#editUserModal<%=user.getId()%>" class="edit" data-toggle="modal" onclick="selectCurrentUser(<%= user.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
							<a href="#deleteUserModal" class="delete" data-toggle="modal" onclick="selectCurrentUser(<%= user.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
						</td>
						
						<!-- Edit Modal HTML -->
						<div id="editUserModal<%=user.getId()%>" class="modal fade">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="POST" action="?action=update">
										<input type="hidden" name="id" value="<%=user.getId()%>"> 
										<div class="modal-header">						
											<h4 class="modal-title">Edit User</h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										</div>
										<div class="modal-body">					
											<div class="form-group">
												<label>First Name</label>
												<input type="text" class="form-control" name="first-name" required value="<%= user.getFirstName() %>">
											</div>
											<div class="form-group">
												<label>Last Name</label>
												<input type="text" class="form-control" name="last-name" required value="<%= user.getLastName() %>">
											</div>
											<div class="form-group">
												<label>City</label>
												<input type="text" class="form-control" name="city" required value="<%= user.getCity() %>">
											</div>
											<div class="form-group">
												<label>Username</label>
												<input type="text" class="form-control" name="username" required value="<%= user.getUsername() %>">
											</div>
											<div class="form-group">
												<label>Email</label>
												<input type="email" class="form-control" name="email" required value="<%= user.getEmail() %>">
											</div>
											<div class="form-group">
												<input type="checkbox" id="activated" name="activated" value="Activated" <%= user.isActivated() ? "checked" : "" %>>
												<label for="activated"> Activated </label><br>
											</div>	
											<div class="form-group">
												<input type="checkbox" id="deleted" name="deleted" value="Deleted" <%= user.isDeleted() ? "checked" : "" %>>
												<label for="deleted"> Deleted </label><br>
											</div>	
											<div class="form-group">
												<label>Avatar URL</label>
												<input type="text" name="avatar-url" class="form-control" value="<%= user.hasAvatar() ? user.getAvatarURL() : ""  %>">
											</div>							
										</div>
										<div class="modal-footer">
											<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
											<input type="submit" class="btn btn-info" value="Save"  >
										</div>
									</form>
								</div>
							</div>
						</div>
					</tr>		
					<% } %>
				</tbody>
			</table>
		</div>
	</div>        
</div>
<!-- Add Modal HTML -->
<div id="addUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addUserForm" method="POST" action="?action=add-user">
				<div class="modal-header">						
					<h4 class="modal-title">Add User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<div class="form-group">
						<label>First Name</label>
						<input type="text" class="form-control" name="first-name" required>
					</div>
					<div class="form-group">
						<label>Last Name</label>
						<input type="text" class="form-control" name="last-name" required>
					</div>
					<div class="form-group">
						<label>City</label>
						<input type="text" class="form-control" name="city" required>
					</div>
					<div class="form-group">
						<label>Username</label>
						<input id="addUserUsernameInput" type="text" class="form-control" name="username" required>
					</div>
					<div class="form-group">
						<label>Password</label>
						<input type="password" class="form-control" name="password" required>
					</div>
					<div class="form-group">
						<label>Email</label>
						<input type="email" class="form-control" name="email" required>
					</div>
					<div class="form-group">
						<input type="checkbox" id="activated" name="activated" value="Activated">
						<label for="activated"> Activated </label><br>
					</div>	
					<div class="form-group">
						<input type="checkbox" id="deleted" name="deleted" value="Deleted">
						<label for="deleted"> Deleted </label><br>
					</div>		
					<div class="form-group">
						<label>Avatar URL</label>
						<input type="text" class="form-control" name="avatar-url" >
					</div>			
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
					<input type="button" class="btn btn-success" value="Add" onclick="validateAndSubmitAddUserForm()">
				</div>
			</form>
		</div>
	</div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteUserModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="?action=delete">
				<div class="modal-header">						
					<h4 class="modal-title">Delete User</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<p>Are you sure you want to delete this user?</p>
					<p class="text-warning"><small>This action cannot be undone.</small></p>
				</div>
				<input id="currentSelectedUser" type=hidden name=id> 
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