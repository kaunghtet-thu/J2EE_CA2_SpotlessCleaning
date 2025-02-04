<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service categories</title>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<div class="left-column">
    <h2>Service Categories</h2>

    <%-- Dynamically populate the categories --%>

    <%
    ServiceCategoryDAO dao = new ServiceCategoryDAO();
    List<ServiceCategory> categories = dao.getAllServiceCategories();

    for (ServiceCategory category : categories) {
%>
    <div style="display: flex; align-items: center; margin-bottom: 30px;">
    
     

        <% if (isAdmin) { %>
            <form action="UpdateServiceCategory" method="POST" style="margin: 0; display: flex;">
                <input type="hidden" name="categoryId" value="<%= category.getId() %>" />
                <input type="text" name="categoryName" value="<%= category.getName() %>" required>
                <button type="submit" style="background-color: blue; color: white; border: none; padding: 10px; border-radius: 5px;">
                    Save
                </button>
            </form>
        <% } else { %>
        	   <form action="services.jsp" method="POST" style="margin: 0; flex-grow: 1;">
            <input type="hidden" name="categoryId" value="<%= category.getId() %>" />
            <button type="submit" class="categoryBtn" style="width: 100%; text-align: left; padding: 10px; border: none; background-color: #f2f2f2; border-radius: 5px;">
                <%= category.getName() %>
            </button>
        </form>
        <%} %>
    </div>
<%
    }
%>

   
  </div>
  <%@include file="../assets/footer.html" %>
  
</body>
</html>