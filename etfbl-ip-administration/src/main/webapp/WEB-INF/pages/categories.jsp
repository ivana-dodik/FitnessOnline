<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.unibl.etf.ip.administration.dto.Category" %>
<%@ page import="org.unibl.etf.ip.administration.dao.CategoryDao" %>
<%@ page import="org.unibl.etf.ip.administration.dto.Attribute" %>
<%@ page import="org.unibl.etf.ip.administration.dao.AttributeDao" %>
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
			function selectCurrentCategory(categoryId) {
				document.getElementById("currentSelectedCategory").value = categoryId;
			}
			
			async function validateAndSubmitAddCategoryForm() {
				let name = document.getElementById("add-category-name").value;
				let parentId = document.getElementById("add-parent-id").value;
				let response = await fetch('http://localhost:8081/etfbl-ip-administration/validate?action=validate-category&category-name=' + name +'&parent-id=' + parentId );
				let jsonData = await response.json();
				if(jsonData == "1") {
					alert("Category with the same name already exists!");
					return
				}
				else {
					document.getElementById("addCategoryForm").submit();
				}
			}
		
			function getAttributes(catId) {
				console.log(catId);
				if(catId != null)
				{
					document.getElementById("category-id").value = catId;
					document.getElementById("attributesForm").submit();
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
						<h2>Manage Categories</h2>
					</div>
					<div class="col-sm-6">
						<a href="#addCategoryModal" class="btn btn-success" data-toggle="modal"><i class="material-icons">&#xE147;</i> <span>Add New Category</span></a>
								
					</div>
				</div>
			</div>
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Name</th>
						<th>Parent Category</th>
						<th>Attributes</th>
					</tr>
				</thead>
				<tbody>
				 <% for(Category category : CategoryDao.selectAll()) { %>
					<tr>
						<td><%=category.getName() %></td>
						<td><%= (category.getParentId() != 0) ? CategoryDao.selectById(category.getParentId()).getName() : "No parent" %></td>
						<td>
							<a onclick="getAttributes(<%=category.getId() %>)">
							<%=category.getAttributes().size() != 0  ? "Attributes" : "No attributes" %> 
							</a>
							<form id="attributesForm" method="POST" action="?action=attributes">
								<input id="category-id" type="hidden" name="category-id" value=""> 
							</form>
						</td>
						<td>
							<a href="#editCategoryModal<%=category.getId()%>" class="edit" data-toggle="modal" onclick="selectCurrentCategory(<%= category.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a>
							<a href="#deleteCategoryModal" class="delete" data-toggle="modal" onclick="selectCurrentCategory(<%= category.getId() %>)"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a>
						</td>
						
						<!-- Edit Modal HTML -->
						<div id="editCategoryModal<%=category.getId()%>" class="modal fade">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="POST" action="?action=update-category">
										<input type="hidden" name="id" value="<%= category.getId() %>"> 
										<div class="modal-header">						
											<h4 class="modal-title">Edit Category</h4>
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										</div>
										<div class="modal-body">
											<div class="form-group">
												<label>Category Name</label>
												<input type="text" class="form-control" name="category-name" required value="<%= category.getName() %>">
											</div>	
											<div class="form-group">
												<label for="categories">Parent category:</label>
												<select name="parent-id" id="categories">
													<option value="0">No parent</option>
 													<% 
 													// CategoryDao.getAvailableCategories(category).size() == 0;
 													for (Category c : CategoryDao.getAvailableCategories(category)) { %>
 														<option <%= category.getParentId() == c.getId() ? "selected" : "" %> value="<%= c.getId() %>"><%= c.getName() %></option>
  													<% } %>
												</select>
											</div>	
										</div>
										<div class="modal-footer">
											<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
											<input type="submit" class="btn btn-info" value="Save" >
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
<div id="addCategoryModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="addCategoryForm" method="POST" action="?action=add-category">
				<div class="modal-header">						
					<h4 class="modal-title">Add Category</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<div class="form-group">
						<label>Category Name</label>
						<input type="text" class="form-control" id="add-category-name" name="category-name" required value="">
					</div>	
					<div class="form-group">
						<label for="categories">Parent category:</label>
						<select name="parent-id" id="add-parent-id">
						<option value="0">No parent</option>
 						<% 
 							for (Category c : CategoryDao.selectAll()) { %>
 								<option value="<%= c.getId() %>"><%= c.getName() %></option>
  								<% } %>
						</select>
					</div>	
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-default" data-dismiss="modal" value="Cancel">
					<input type="button" class="btn btn-success" value="Add" onclick="validateAndSubmitAddCategoryForm()">
				</div>
			</form>
		</div>
	</div>
</div>
<!-- Delete Modal HTML -->
<div id="deleteCategoryModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<form method="POST" action="?action=delete-category">
				<div class="modal-header">						
					<h4 class="modal-title">Delete Category</h4>
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">					
					<p>Are you sure you want to delete this category and it's attributes?</p>
					<p class="text-warning"><small>This action cannot be undone.</small></p>
				</div>
				<input id="currentSelectedCategory" type=hidden name=id> 
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