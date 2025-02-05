<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Welcome Page</h2>
    
    <%
        // Get the userID and userRole parameters passed from verifyUser.jsp
        String userID = request.getParameter("userID");
        String userRole = request.getParameter("userRole");

        if (userID != null && userRole != null) {
    %>
        <p>Welcome!.. <b><%= userID %></b></p>
        <p>Your user role is: <b><%= userRole %></b></p>
    <% 
        } else {
            out.println("<p style='color:red;'>Error: Missing user information.</p>");
        }
    %>

    <form action="login.jsp" method="post">
        <input type="submit" value="Home">
    </form>
</body>
</html>