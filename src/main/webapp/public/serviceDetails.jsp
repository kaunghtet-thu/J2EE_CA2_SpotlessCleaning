<%@page import="DAO.*" %>
<%@page import="bean.*" %>
<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Service Details</title>
<style>
  body {
    font-family: Arial, sans-serif;
  }
  .service-details {
    width: 80%;
    margin: 20px auto;
  }
  .service-details h2 {
    text-align: center;
  }
  .service-details .service-info {
    display: flex;
    justify-content: space-between;
    padding: 20px;
    border: 1px solid #ddd;
  }
  .service-details .service-info .details {
    width: 60%;
  }
  .service-details .service-info .booking {
    width: 35%;
    padding: 10px;
    background-color: #f7f7f7;
    border: 1px solid #ddd;
  }
  .service-details .button {
    display: block;
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    background-color: #28a745;
    color: white;
    border: none;
    cursor: pointer;
  }
  .service-details .button:hover {
    background-color: #218838;
  }
</style>
</head>
<body>

<%@include file="../assets/header.jsp" %>

<%
  // Get the service ID from the URL parameter
  int serviceId = Integer.parseInt(request.getParameter("serviceId"));
  
  // Fetch service details using the service ID
  ServiceDAO serviceDao = new ServiceDAO();
  Service service = serviceDao.getServiceById(serviceId);
  DiscountDAO disDao = new DiscountDAO();
  
  boolean isInCart = false;
  List<Service> cart = (List<Service>)session.getAttribute("cart");
  if (cart != null) {
  
  for (Service s : cart) {
      if (s.getId() == service.getId()) {
          isInCart = true;
          break;
      }
  } }


%>

<div class="service-details">
  <h2>Service Details</h2>
  
  <div class="service-info">
    <div class="details">
      <h3><%= service.getName() %></h3>
      <p><strong>Description:</strong> <%= service.getDescription() %></p>
     <% if (disDao.getDiscountStatusByServiceId(serviceId)) { 
    double originalPrice = service.getPrice();
    double discountPercent = disDao.getDiscountPercentByServiceId(serviceId);
    double discountedPrice = originalPrice * (1 - discountPercent / 100);
	%>
	    <p>
	        <strong>Price:</strong> 
	        <span style="text-decoration: line-through;">$<%= originalPrice %></span> 
	        <span style="color: red;">$<%= discountedPrice %></span>
	    </p>
	<% } else { %>
	    <p><strong>Price:</strong> $<%= service.getPrice() %></p>
	<% } %>
    </div>
    <%if (isMember) { %>
    <div class="booking">
      <form action="./bookCart.jsp" method="POST">
        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
        <input type="submit" value="Book Service" class="button" />
      </form>
      <% if (isInCart) {%>
      	<p>You have this service in your cart</p>
      <%} else {%>
      <form action="<%= request.getContextPath()%>/AddToCart" method="POST">
        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
        <input type="submit" value="Add to Cart" class="button" />
      </form>
      <%} %>
    </div>
    <%} else if (isAdmin){ %>
    <div class="manage">
      <form action="../admin/updateService.jsp?serviceId=<%= service.getId() %>" method="POST">
        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
        <input type="submit" value="Manage Service" class="button" />
      </form>
    </div>
    <%} else if (isPublic) { %>
    <div class="manage">
      <form action="../public/login.jsp" method="POST">
        <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
        <input type="submit" value="Login to Book" class="button" />
      </form>
    </div>
    <%} %>
  </div>
</div>

<%@include file="../assets/footer.html" %>
</body>
</html>
