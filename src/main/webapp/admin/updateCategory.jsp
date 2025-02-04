<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DAO.*, bean.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Category</title>
</head>
<style>
  .category-container {
    display: flex;
    flex-wrap: nowrap;
    overflow-x: auto;
    gap: 20px;
    padding: 20px;
  }
  .category-card-wrapper {
    min-width: 300px;
    flex-shrink: 0;
  }
  .category-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px;
    text-align: center;
    background-color: #fff;
  }
  .category-card h3 {
    margin: 0 0 15px 0;
    font-size: 20px;
    color: #333;
  }
  .current-image {
    max-width: 100%;
    height: auto;
    margin-bottom: 15px;
    border-radius: 8px;
  }
</style>
<body>
<%@include file="../assets/header.jsp" %>

<% if (isAdmin) { 
    int categoryId = Integer.parseInt(request.getParameter("categoryId"));
    ServiceCategoryDAO dao = new ServiceCategoryDAO();
    ServiceCategory category = dao.getServiceCategoryById(categoryId);
    if (category != null) {
%>
    <div class="category-card-wrapper">
      <div class="category-card" id="addNewCat">
        <form action="UpdateCategory" method="post" enctype="multipart/form-data">
          <!-- Hidden field for categoryId -->
          <input type="hidden" name="categoryId" value="<%= categoryId %>">

          <!-- Display current category name in an editable text field -->
          <label for="serviceCategory">Category Name:</label>
          <input type="text" id="serviceCategory" name="serviceCategory" value="<%= category.getName() %>" required><br>

          <!-- Display current image as a static image -->
          <label>Current Image:</label><br>
          <img src="images/<%= category.getImage() %>" alt="Current Category Image" class="current-image"><br>

          <!-- Optional file upload for new image -->
          <label for="categoryImage">Upload New Image (optional):</label>
          <input type="file" id="categoryImage" name="categoryImage"><br><br>

          <!-- Submit button -->
          <button type="submit" class="manage-btn">Update Category</button>
        </form>
      </div>
    </div>
<%
    } else {
%>
    <p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">Category not found!</p>
<%
    }
} else {
%>
    <p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<% } %>

<%@include file="../assets/footer.html" %>
</body>
</html>