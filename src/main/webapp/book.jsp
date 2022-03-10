<%-- 
    Document   : book
    Created on : Jan 21, 2020, 7:47:23 AM
    Author     : Chris.Cusack


    This view supports a retrieved book or creation of a new book

--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@include file="WEB-INF/jspf/header.jspf"%>
<title>Book</title>
</head>
<body class="p-4">
	<%@include file="WEB-INF/jspf/navigation.jspf"%>
	<h2>Book</h2>
	

	<%
		if (request.getAttribute("vm") != null) {
			BookModel vm = (BookModel)request.getAttribute("vm");		
	%>

	<form method="POST" action="save">
		<table class="table">
			<!-- Display details in view mode -->
			<%
				if (vm.getBook != null && vm.getBook().getId() != 0) {								
			%>
			<tr>
				<td><label>Book Id:</label></td>
				<td>${vm.book.id}<input type="hidden"
					value='${vm.book.id} }' name="hdnBookId" /></td>
			</tr>
			

			<tr>
				<td>Book Name:</td>
				<td><input type="text" name="bookName"
					value='${vm.book.name}' /></td>
			</tr>
			<tr>
				<td>Book Price:</td>
				<td><input type="text" name="bookPrice"
					value='${vm.book.price}' /></td>
			</tr>
			<tr>
				<td>Term</td>
				<td>
					<%
						for (int i: vm.getTerms()) {
							if (vm.getBooks() != null && vm.getBook().getTerm() == i) { %>
								<input type="radio" name="bookTerm" value="<%= i %>" checked /> Term <%=i %>		
							<%} else { %>
								<input type="radio" name="bookTerm" value="" checked />
								Term<%=i %>
								<%}}%>
					<!-- Updating a book or creating a book must have the supported list of terms -->
					 


				</td>
			</tr>
		</table>
		<%
			if (vm.getBook() != null && vm.getBook().getId() > 0) {
		%>
		<!-- Decide on what buttons to render. When updating, show Save and Delete, create show Create -->

		<input class="btn btn-primary" type="submit" value="Delete"
			name="action" /> 
		 <input class="btn btn-primary" type="submit"
			value="Save" name="action" />
		<%} else { %>

		<input class="btn btn-primary" type="submit" value="Create"
			name="action" />
		<%} %>
		
	</form>
	<%} %>
	<!--Set up errors here -->
	<%
		if (request.getAttributes("error") != null) {
			ErrorModel em = (ErrorModel)request.getAttributes("error");
			if (em.getErrors() != null && em.getErrors().size() > 0)  {
	%>
	<ul class="alert alert-danger">
		<%
			for (String err: em.getErrors()) {
		%>


		<li><%=err %></li>
		<%} %>
		

	</ul>
	
<%}} %>
</body>
</html>
