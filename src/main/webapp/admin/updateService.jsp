<%@page import="DAO.*" %>
<%@page import="bean.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	  fieldset {
            margin-bottom: 10px;
            background-color: #E3EED4;
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
<%

if (isAdmin) {
String serviceIdStr = request.getParameter("serviceId");
int serviceId=0;
if (serviceIdStr != null) {
	serviceId = Integer.parseInt(serviceIdStr);
}

ServiceDAO dao = new ServiceDAO();
Service service = dao.getServiceById(serviceId);

%>
<div class="container">
	<fieldset>
		<legend>Update Service:</legend>
		 <form action="UpdateService" method="POST" style="display:inline;">
        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
        
        <!-- Label and input for service name -->
        <label for="serviceName">Service Name:</label><br>
        <input type="text" id="serviceName" name="serviceName" value="<%= service.getName() %>" /><br>
        
        <!-- Label and textarea for service description -->
        <label for="serviceDescription">Service Description:</label><br>
        <textarea id="serviceDescription" rows="5" name="serviceDescription"><%= service.getDescription() %></textarea><br>
        
        <!-- Label and input for service price -->
        <label for="servicePrice">Service Price:</label><br>
        <input type="number" id="servicePrice" step="0.01" name="servicePrice" value="<%= service.getPrice() %>" /><br>
        
        <input type="submit" value="Save Changes" />
    </form>

		<form action="DeleteService" method="POST" style="display:inline;">
		     <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
		     <input type="submit" value="Delete Service" />
		</form>
	</fieldset>		
	</div>	
	<%} else{ %>    
	<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>   
<%@include file="../assets/footer.html" %>
</body>
</html>