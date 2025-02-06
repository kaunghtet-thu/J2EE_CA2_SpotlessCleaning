<%@ page import="DAO.*" %>
<%@ page import="bean.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Services</title>
    <link rel="stylesheet" href="../assets/css/services.css">
</head>
<body>

<%@ include file="../assets/header.jsp" %>
<h1 id="availServ">AVAILABLE SERVICES</h1>

<div class="category-container">
    <%
        // Fetch all service categories
        ServiceCategoryDAO categoryDAO = new ServiceCategoryDAO();
        List<ServiceCategory> categories = categoryDAO.getAllServiceCategories();
        DiscountDAO disDao = new DiscountDAO();

        // Fetch services for each category
        ServiceDAO serviceDAO = new ServiceDAO();
        if(isAdmin) {%>
		<div class="category-card-wrapper">
	      <div class="category-card" id="addNewCat">
	        <h3 style="color: white;">+ Add New Category</h3>
	        <form action="../AddCategory" method="post" enctype="multipart/form-data">
	          <label for="serviceCategory">Category Name:</label>
	          <input type="text" id="serviceCategory" name="serviceCategory" required><br>
	
	          <label for="categoryImage">Category Image:</label>
	          <input type="file" id="categoryImage" name="categoryImage" ><br><br>
	
	          <button type="submit" class="manage-btn">Add Category</button>
	        </form>
	      </div>
	    </div>
		<% }
        for (ServiceCategory category : categories) {
            int categoryId = category.getId();
            List<Service> services = serviceDAO.getServicesByCategory(categoryId);
            boolean hasServices = (services != null && !services.isEmpty());


			
            if (isAdmin || hasServices) {
    %>
                <div class="category-card-wrapper">
                    <div class="category-card">
                        <h3><%= category.getName() %> Services</h3>
                        <img src="../assets/images/<%= category.getImage() %>"
                             alt="<%= category.getName() %>"
                             width="100"
                             height="100"
                             onerror="this.onerror=null; this.src='../assets/images/default.png';" /><br>

                        <% if (isAdmin) { %>
                            <form action="../admin/updateCategory.jsp" method="post">
                                <input type="hidden" name="categoryId" value="<%= categoryId %>">
                                <button type="submit" class="manage-btn">Edit Category</button>
                            </form><br><br>
                        <% } %>

                        <% for (Service service : services) { %>
                            <div class="service-item">
                                <a href="serviceDetails.jsp?serviceId=<%= service.getId() %>" class="service-link">
                                    <%= service.getName() %>
                                    <%if (disDao.getDiscountStatusByServiceId(service.getId())){ %>
                                    	<span class="discount-banner">Discount!</span>
                                    <%} %>
                                </a>
                            </div>
                        <% } %>

                        <% if (isAdmin) { %>
                            <br><br>
                            <h3>Add a new service under <%= category.getName() %></h3>
                            <fieldset>
                                <form action="AddNewService" method="post">
                                    <input type="hidden" name="categoryId" value="<%= categoryId %>">

                                    <label for="service_name">Service Name:</label>
                                    <input type="text" id="service_name" name="serviceName" required><br>

                                    <label for="description">Description:</label>
                                    <textarea id="description" name="serviceDescription" rows="4" required></textarea><br>

                                    <label for="price">Price:</label>
                                    <input type="number" id="price" name="servicePrice" step="0.01" required><br><br>

                                    <button type="submit" class="manage-btn">Add Service</button>
                                </form>
                            </fieldset>
                        <% } %>
                    </div>
                </div>
    <%
            }
        }
    %>
</div>

<%@ include file="../assets/footer.html" %>
</body>
</html>
