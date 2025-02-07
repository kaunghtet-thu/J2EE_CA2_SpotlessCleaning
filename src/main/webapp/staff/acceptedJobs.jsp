<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
<%@include file="../assets/header.jsp" %>
<%if (isStaff) { %>

<%}else { %>
  	<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>
<%@include file="../assets/footer.html" %>
</html>