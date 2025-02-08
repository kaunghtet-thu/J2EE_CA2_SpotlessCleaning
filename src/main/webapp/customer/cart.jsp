<%@include file="../assets/header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Service" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-rbsA2VBKQfz6Kp06j5zSuOEPBJHRJf7j0iP4Ccm6Ksb3kzA9BvLrr6hWJj4twt3A" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IMpD2M36nB2n6zQhT7M05wD3zNzzTBTdS6k" crossorigin="anonymous"></script>
    
</head>
<body>
<link rel="stylesheet" type="text/css" href="../assets/css/cart.css">
<%if (isMember){ %>
    <h1>Your Cart</h1>
    
    <%
        // Retrieve the cart from the session
        ServiceDAO serviceDao = new ServiceDAO();
        DiscountDAO disDao = new DiscountDAO();
        List<Service> cart = (List<Service>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
    %>
        <p class="succMsg">Your cart is empty.</p>
    <%
        } else {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th width="300">Name</th>
                    <th width="600">Description</th>
                    <th width="80">Price</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Service service : cart) {
                    
                 		
                     	 double price = service.getPrice();
                     	 if (disDao.getDiscountStatusByServiceId(service.getId())) {
                         double discountPercent = disDao.getDiscountPercentByServiceId(service.getId());
               			 price = serviceDao.getServiceById(service.getId()).getPrice() * (1 - discountPercent / 100);
               			 service.setName(serviceDao.getServiceNameById(service.getId()) + "(" + (int)(discountPercent)+ "% OFF)");
               			 service.setPrice(price);                     	 }
                %>
                <tr>
                    <td><%= service.getName() %></td>
                    <td><%= service.getDescription() %></td>
                    <td><%= price %></td>
                    <td>
                        <!-- Delete Form -->
                        <form action="../RemoveFromCart" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                            <button type="submit">Delete</button>
                        </form>
                        <%
                        if(isPublic) {
                        %>
                        
                        <form action="../public/login.jsp" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
                            <input type="hidden" name="serviceName" value="<%= service.getName() %>" />
                            <input type="submit" value="Book" />
                        </form>
                        <%	
                        } else {
                        %>

                        <form action="./bookCart.jsp" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
                            <input type="hidden" name="serviceName" value="<%= service.getName() %>" />
                            <input type="hidden" name="servicePrice" value="<%= price %>" />
                            <input type="submit" value="Book" />
                        </form>
                        <% }
                        %>
                    </td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <p>Total Items: <%= cart.size() %></p>
    <%
        }
    %>
    <div class="container">
		<form action="../public/services.jsp" method="GET">
		    <button type="submit">Continue Shopping</button> <br>
		</form>
		<% if(cart != null && cart.size()> 1) {%>
		<form action="./bookCart.jsp" method="GET">
		    <button type="submit">Book All</button>
		</form>
		<%} %>
    </div>
    <%@include file="../assets/footer.html" %>
 <%} else { %>
<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>   
</body>
</html>
