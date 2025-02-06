<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Promote service</title>
<style>
	  fieldset {
            margin-bottom: 10px;
            background-color: #31525b;
            color: white;
        }
        .container {
            max-width: 35vw;
            margin: auto;
            padding: 15px;
        }
</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>


<%if (isAdmin) { 
	String serviceIdStr = request.getParameter("serviceId");
	int serviceId=0;
	ServiceDAO dao = new ServiceDAO();
	String serviceName ="";
	if (serviceIdStr != null) {
		serviceId = Integer.parseInt(serviceIdStr);
		serviceName = dao.getServiceNameById(serviceId);
	}

%>

	<div class="container">
	<fieldset>
		<legend>Add discount:</legend>
		<form action="../AddDiscount" method="POST" style="display:inline;">

		   
		    <% if (serviceName != "") { %> 
		     <label for="serviceName">Service Name:</label><br>
		        <input type="hidden" name="serviceId" value="<%= serviceId %>" />
		        <input type="text" id="serviceName" name="serviceName" value="<%= serviceName %>" readonly /><br>
		    <% } else { 
		        List<Service> services = dao.getAllServices();
		    %>
		        <label for="serviceName">Select Service:</label><br>
		        <select id="serviceName" name="serviceName">
		            <option value="">--Select a Service--</option>
		            <% for (Service service : services) { %>
		                <option value="<%= service.getId() %>"><%= service.getName() %></option>
		            <% } %>
		        </select><br>
		    <% } %>
		    
		    <!-- User Input: Discount Name -->
		    <label for="discountName">Discount Name:</label><br>
		    <input type="text" id="discountName" name="discountName" required /><br>
		    
		    <!-- User Input: Discount Percentage -->
		    <label for="discount">Discount Percent:</label><br>
		    <input type="number" id="discount" name="discount" min="0" max="100" step="0.01" required> %<br>
		    
		    <input type="submit" value="Add Discount" />
		</form>

		</fieldset>		
		</div>



<% } else { %>
    <p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<% } %>

<%@include file="../assets/footer.html" %>
</body>
</html>