<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List, bean.Service" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Warning</title>
</head>
<body>
<%@ include file="../assets/header.jsp" %>
<%
    List<Service> cart = (List<Service>) session.getAttribute("cart");
    if (cart != null && !cart.isEmpty()) {
%>
    <div style="margin: 20px auto; padding: 20px; max-width: 500px; text-align: center;">
        <h2>Are you sure?</h2>
        <p>If you log out now, your cart will be cleared.</p>
        <form method="post" action="../public/logout.jsp" style="display: inline;">
            <button type="submit" style="padding: 10px 20px; background-color: #cc0000; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer;">
                Yes, Logout
            </button>
        </form>
        <a href="../index.jsp" style="padding: 10px 20px; background-color: #31525b; color: white; text-decoration: none; border-radius: 5px; font-size: 16px; cursor: pointer; display: inline-block; margin-left: 10px;">
           Stay
        </a>
    </div>
<%
    } else {
        session.invalidate();
        response.sendRedirect("../public/login.jsp");
    }
%>
<%@ include file="../assets/footer.html" %>
</body>
</html>
