<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    String errorMessage = (String) request.getParameter("errorMsg");
	String successMessage = (String) request.getParameter("successMsg");
    if (errorMessage != null) {
%>
    <p class="errMsg"><%= errorMessage %></p>
<% } ;
   if (successMessage != null) { %>
   
   <p class= "succMsg"><%=successMessage%></p>
<%} %>
</body>
</html>