<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.administration.dto.Category" %>
<%@ page import="org.unibl.etf.ip.administration.dao.CategoryDao" %>
<%@ page import="org.unibl.etf.ip.administration.dto.Attribute" %>
<%@ page import="org.unibl.etf.ip.administration.dao.AttributeDao" %>

<%
	String categoryIdParam = request.getParameter("category-id");
	if(categoryIdParam == null) {
		response.sendRedirect("/etfbl-ip-administration?action=redirect&path=categories");
		return;
	}
	
	int categoryId = Integer.parseInt(categoryIdParam);
%>
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
		<link rel="stylesheet" type="text/css" href="/etfbl-ip-administration/styles/categories.css">
		<script>	
			function selectCurrentAttribute(attributeId) {
				document.getElementById("currentSelectedAttribute").value = attributeId;
			}
			
			async function validateAndSubmitAddAttributeForm() {
				console.log(document.getElementById("add-attribute-name"));
				let attributeName = document.getElementById("add-attribute-name").value;
				//let attributeType = document.getElementById("add-attribute-type").value;
				let attributeCategory = document.getElementById("add-attribute-category").value;
				let response = await fetch('http://localhost:8081/etfbl-ip-administration/validate?action=validate-add-attribute&add-attribute-name=' + attributeName + "&add-attribute-category=" + attributeCategory);
				let jsonData = await response.json();
				if(jsonData == "1") {
					alert("Attribute already exists!");
					return;
				}
				else {
					document.getElementById("addAttributeForm").submit();
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
						<h2>Manage Attributes for <%= CategoryDao.selectById(categoryId).getName() %></h2>
					</div>
					<div class="col-sm-6">
						<a href="#addAttributeModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Attribute</span></a>
					</div>
				</div>
			</div>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Name</th>
					</tr>
				</thead>
				<tbody>
				 <% for(Attribute attribute : AttributeDao.selectAllFromCategory(categoryId)) { %>
					<tr>
						<td><%=attribute.getName() %></td>
						
						<td>
							<a href="#editAttributeModal<%=attribute.getId()%>" class="edit" data-toggle="modal" onclick="selectCurrentAttribute(<%= attribute.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
							<a href="#deleteAttributeModal" class="delete" data-toggle="modal" onclick="selectCurrentAttribute(<%= attribute.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
						</td>
						
						<!-- Edit Modal HTML -->
						<div id="editAttributeModal<%=attribute.getId()%>" class="modal fade">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="POST" action="?action=update-attribute">
										<input type="hidden" name="id" value="<%=attribute.getId()%>"> 
										
										<div class="modal-header">						
											<h4 class="modal-title">Edit Attribute</h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										</div>
										<div class="modal-body">	
										<div class="form-group">
												<label>Attribute Name</label>
												<input type="text" class="form-control" name="attribute-name" required value="<%= attribute.getName() %>">
										</div>	
												
											<div class="form group">
												<input type="hidden" name="attribute-category" value="<%=categoryId%>"> 
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
<div id="addAttributeModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addAttributeForm" method="POST" action="?action=add-attribute">
				<div class="modal-header">						
					<h4 class="modal-title">Add Attribute</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<div class="form-group">
						<label>Attribute Name</label>
						<input type="text" class="form-control" id="add-attribute-name" name="add-attribute-name" required value="">
					</div>	
					
					<div class="form group">
						<input type="hidden"  id="add-attribute-category" name="add-attribute-category" value="<%=categoryId%>"> 
					</div>	
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
					<input type="button" class="btn btn-success" value="Add" onclick="validateAndSubmitAddAttributeForm()">
				</div>
			</form>
		</div>
	</div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteAttributeModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="?action=delete-attribute">
				<div class="modal-header">						
					<h4 class="modal-title">Delete Attribute</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<p>Are you sure you want to delete this attribute?</p>
					<p class="text-warning"><small>This action cannot be undone.</small></p>
				</div>
				<input id="currentSelectedAttribute" type="hidden" name="id"> 
				<input type="hidden" name="attribute-category" value="<%=categoryId%>"> 
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