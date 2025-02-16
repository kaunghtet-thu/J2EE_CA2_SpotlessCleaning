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
<%@include file="../assets/header.jsp" %>

    <h1>Your Cart</h1>
    
    <%
        // Retrieve the cart from the session
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
                %>
                <tr>
                    <td><%= service.getName() %></td>
                    <td><%= service.getDescription() %></td>
                    <td><%= service.getPrice() %></td>
                    <td>
                        <!-- Delete Form -->
                        <form action="RemoveFromCart" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                            <button type="submit">Delete</button>
                        </form>
                        <%
                        if(isPublic) {
                        %>
                        
                        <form action="login.jsp" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
                            <input type="hidden" name="serviceName" value="<%= service.getName() %>" />
                            <input type="submit" value="Book" />
                        </form>
                        <%	
                        } else {
                        %>
                        <form action="bookAService.jsp" method="POST" style="display:inline;">
                            <input type="hidden" name="serviceId" value="<%= service.getId() %>" />
                            <input type="hidden" name="serviceName" value="<%= service.getName() %>" />
					                <input type="hidden" name="servicePrice" value="<%= service.getPrice() %>" />
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
	<form action="../shared/services.jsp" method="get">
	    <button type="submit">Continue Shopping</button> <br>
	</form>
	<form action="./bookCart.jsp" method="get">
	    <button type="submit">Book All</button>
	</form>
    </div>
    <%@include file="../assets/footer.html" %>
    
</body>
</html>
