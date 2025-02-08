<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Spotifact</title>
<style>
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
    .table-container table {
        max-width: 100%;
    }
    .table-container, .header {
	    display: flex;
	    justify-content: center; 
	    align-items: center; 
	    width: 90%; 
	}

</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<%if (isAdmin) { 
	

%>




<%
	MerchandizeSalesDAO dao = new MerchandizeSalesDAO();
	List<ProductSales> prodsales = dao.getMerchandizeSales();
	if (prodsales.size()>0) {

%>
<h1>SPOTIFACT Sales Reporting</h1>
	 <!-- Start of Table -->
	 <table border="1">
	     <thead>
	         <tr>
	             <th>Payment reference ID</th>
	             <th>Name</th>
	           
	             <th>Stock</th>
	             <th>Price</th>
	       
	             <th>Account Payable</th>
	             <th>Transferred</th>
	             <th>Spotifact has received</th>
	         </tr>
	     </thead>
	     <tbody>
	         <!-- Loop through product sales and create rows -->
	         <%
	             for (ProductSales product : prodsales) {
	         %>
	             <tr>
	                 <td><%= product.getId() %></td>
	                 <td><%= product.getName() %></td>
	                 <td><%= product.getStock() %></td>
	                 <td><%= product.getPrice() %></td>
	                 <td><%= product.getAccountPayable() %></td>
	                 <td>
	                 <%if (product.isTransferred()) {%>
	                 Transferred. Pending approval
	                 <%} else { %>
	                 	 <form action="../SetPaymentStatusToPending" method="post">
                            <input type="hidden" name="paymentReferenceId" value="<%= product.getId() %>" />
                            <button type="submit">Transfer Now</button>
                        </form>
	                 <%} %>
	                 </td>
	                 <td><%= product.isReceived() ? "Spotifact has received" : "Pending" %></td>
	             </tr>
	         <%
	             }
	         %>
	     </tbody>
	 </table>
<%} else { %>
<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">No sales records</p>

<%} %>
<%} else { %>
<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>
<%@include file="../assets/footer.html" %>
</body>
</html>