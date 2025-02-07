<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Dashboard</title>
<style>
    button {
        background-color: #31525b;
        color: white;
        border-radius: 20px;
    }
    .filter-message {
        font-weight: bold;
        color: #31525b;
        margin-bottom: 10px;
    }
    .manage-btn {
        display: inline-block;
        background-color: #31525b;
        color: white;
        padding: 8px 15px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
        text-align: center;
    }
    .manage-btn:hover {
        background-color: #233d46;
    }
    
    .terminate-btn {
        background-color: red;
        color: white;
        padding: 10px 20px;
        border-radius: 10px;
        border: none;
        cursor: pointer;
    }
     .terminate-btn:hover {
        background-color: darkred;
    }
    .filterOptions {
        display: flex;
        justify-content: flex-end;
        margin-top: 20px;
        margin-bottom: 10px;
    }
    .filterOptions button {
        margin-left: 10px;
    }
    .table-container, .filterOptions, .filter-message {
        width: 70vw;
        margin: 0 auto;
        overflow-x: auto;
        padding: 10px;
    }
    table {
        width: 100%;
        table-layout: fixed; 
        border-collapse: collapse;
    }
    th, td {
        padding: 10px;
        border: 1px solid #ddd;
        word-wrap: break-word; 
    }
    th {
        background-color: #b3dee5;
    }
       .content-wrapper {
        display: flex;
        width: 100vw;
        margin: 0 auto;
    }
    .col1 { width: 10%; }
  	.col2 { width: 20%; }
  	.col3 { width: 30%; }

    .left-col {
    width: 25%;
    padding: 20px;
    border-right: 1px solid #ddd;
  }
  .right-col {
    width: 75%;
    padding: 20px;
  }
  .categoryBtn:hover {
  	background-color: #31525b;
  	color: white;
  }
</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<% 
if (isAdmin) { 
    String filter = request.getParameter("filter");
    String categoryId = request.getParameter("categoryId");
    if (filter == null) {
        filter = ""; 
    }
    if (categoryId == null) {
    	categoryId = ""; 
    }

    String filterMessage = "Showing all services";
    if ("booking_count".equals(filter)) {
        filterMessage = "Sorted by Booking Count";
    } else if ("average_rating".equals(filter)) {
        filterMessage = "Sorted by Average Rating";
    }
    List<DashboardService> services;
    ServiceDAO dao = new ServiceDAO();
    if (categoryId == "" || categoryId == null) {
        services = dao.getAllServicesForDashboard(filter); 
    } else {
        services = dao.getServicesForDashboardByCategory(filter, Integer.parseInt(categoryId)); 
    }
%>
<div class="content-wrapper">
<div class="left-col">
  <h2>Service Categories</h2>
  <div style="display: flex; align-items: center; margin-bottom: 30px;">
    <!-- Form for selecting the category -->
    <form action="serviceDashboard.jsp" method="GET" style="margin: 0; flex-grow: 1;">
        <input type="hidden" name="categoryId" value="" />
        <button type="submit" class="categoryBtn" style="width: 100%; text-align: left; padding: 10px; border: none; color: black ; background-color: #b3dee5; border-radius: 5px;">
            All Services
        </button>
    </form>
  </div>
  
  <%-- Dynamically populate the categories --%>
  <%
    ServiceCategoryDAO serviceDao = new ServiceCategoryDAO();
    List<ServiceCategory> categories = serviceDao.getAllServiceCategories();

    for (ServiceCategory category : categories) {
  %>
    <div style="display: flex; align-items: center; margin-bottom: 30px;">
        <!-- Form for selecting the category -->
        <form action="serviceDashboard.jsp" method="GET" style="margin: 0; flex-grow: 1;">
            <input type="hidden" name="categoryId" value="<%= category.getId() %>" />
            <button type="submit" class="categoryBtn" style="width: 100%; text-align: left; padding: 10px; border: none; color: black ; background-color: #b3dee5; border-radius: 5px;">
                <%= category.getName() %>
            </button>
        </form>
    </div>
  <%
    }
  %>
</div>

<div class="right-col">
	<h2 class="filter-message"><%= filterMessage %> | <%=services.size() %> results </h2>
    <div class="filterOptions">
        <form method="get">
            <!-- Include categoryId as a hidden input -->
            <input type="hidden" name="categoryId" value="<%= categoryId %>" />
            <button type="submit" name="filter" value="">Clear filters</button>
            <button type="submit" name="filter" value="booking_count">Sort by Booking Count</button>
            <button type="submit" name="filter" value="average_rating">Sort by Average Rating</button>
        </form>
    </div>

    <div class="table-container">
        <table>
            <colgroup>
                <col style="width: 10%;"> <!-- Service ID column (1) -->
                <col style="width: 40%;"> <!-- Service Name column (4) -->
                <col style="width: 10%;"> <!-- Booking Count column (1) -->
                <col style="width: 10%;"> <!-- Average Rating column (1) -->
                <col style="width: 20%;"> <!-- Manage column (2) -->
            </colgroup>
            <thead>
                <tr>
                    <th class="col1">Service id</th>
                    <th class="col3">Service Name</th>
                    <th class="col1">Booking Count</th>
                    <th class="col1">Average Rating</th>
                    <th class="col2">Discount</th>
                    <th class="col2">Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                DiscountDAO disDao = new DiscountDAO();
                //List<Discount> discounts = disDao.getAllDiscounts(isAdmin);
                for (DashboardService service : services) { 
                	boolean isActive = disDao.getDiscountStatusByServiceId(service.getId());
                	if (service.getId() == 1) {
                		System.out.println("asdfas" + isActive);
                	}
                %>
                
            
                    <tr>
                        <td><%= service.getId() %></td>
                        <td><%= service.getName() %></td>
                        <td><%= service.getNoOfBooks() %></td>
                        <td><%= service.getRating() %></td>
                        <td>
                        <%if (disDao.getDiscountStatusByServiceId(service.getId())) {%>
                        	<%=disDao.getDiscountPercentByServiceId(service.getId()) %> % Off
                        <%} else { %>
                        	<i>No discounts</i>
                        <%} %>
                        </td>
                        <td>
                        	<%if (isActive) {%>
                        		<form action="../TerminatePromotion" method="POST" style="display:inline;">
                        			 <input type="hidden" name="isAdmin" value="<%= isAdmin %>" />
							        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
							        <input type="submit" value="Terminate Promotion" class="terminate-btn" />
							    </form>
                        	<%} else { %>
                            <a href="promoteService.jsp?serviceId=<%= service.getId() %>" class="manage-btn">Promote</a>
                            <%} %>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
</div>
	
<% } else { %>
    <p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<% } %>

<%@include file="../assets/footer.html" %>
</body>
</html>