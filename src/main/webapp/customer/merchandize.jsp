<%@include file="../assets/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
	int memberId = 14;
    List<Product> itemList = (List<Product>)session.getAttribute("products");
    String totalAmount = (String)session.getAttribute("total");
%>
<html>
<head>
    <title>Item List</title>
    <!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


    <script>
        function increaseQuantity(id, stock) {
            let quantityInput = document.getElementById('quantity_' + id);
            let currentValue = parseInt(quantityInput.value);
            if (currentValue < stock) {
                quantityInput.value = currentValue + 1;
            }
        }
        
        function decreaseQuantity(id) {
            let quantityInput = document.getElementById('quantity_' + id);
            let currentValue = parseInt(quantityInput.value);
            if (currentValue > 1) {
                quantityInput.value = currentValue - 1;
            }
        }
        let totalPrice = 0;

        function decreaseQuantity(id, price) {
            let quantityInput = document.getElementById('quantity_' + id);
            let quantity = parseInt(quantityInput.value);
            if (quantity > 1) {
                quantity--;
                quantityInput.value = quantity;
                updateTotal(-price);
            }
        }

        function increaseQuantity(id, stock, price) {
            let quantityInput = document.getElementById('quantity_' + id);
            let quantity = parseInt(quantityInput.value);
            if (quantity < stock) {
                quantity++;
                quantityInput.value = quantity;
                updateTotal(price);
            }
        }

        function updateTotal(amount) {
            totalPrice += amount;
            document.getElementById("totalPrice").innerText = totalPrice.toFixed(2);
        }

        // Initialize total price
        window.onload = function () {
            let total = 0;
            <% if (itemList != null) { %>
                <% for (Product item : itemList) { %>
                    total += <%= item.getPrice() %>;
                <% } %>
            <% } %>
            document.getElementById("totalPrice").innerText = total.toFixed(2);
        };
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Available Items</h2>
        <form action="./payments.jsp?total=<%= totalAmount %>" method="post">
        
<div class="d-flex flex-wrap justify-content-center pb-3">
                <% if (itemList != null) { %>
                    <% for (Product item : itemList) { %>
                        <div class="card p-3 shadow-sm mx-2" style="min-width: 300px;">
                            <h5 class="card-title"><%= item.getName() %></h5>
                            <p class="card-text"><%= item.getDescription() %></p>
                            <p class="fw-bold">$<%= item.getPrice() %></p>
                            <% if (item.getStock() > 0) { %>
                                <div class="d-flex align-items-center">
                                    <button type="button" class="btn btn-outline-secondary" onclick="decreaseQuantity(<%= item.getId() %>, <%= item.getPrice() %>)">-</button>
                                    <input type="text" id="quantity_<%= item.getId() %>" name="quantity_<%= item.getId() %>" value="1" class="form-control text-center mx-2" style="width: 50px;" readonly>
                                    <button type="button" class="btn btn-outline-secondary" onclick="increaseQuantity(<%= item.getId() %>, <%= item.getStock() %>, <%= item.getPrice() %>)">+</button>
                                </div>
                            <% } else { %>
                                <p class="text-danger">Out of Stock</p>
                            <% } %>
                        </div>
                    <% } %>
                <% } else { %>
                    <span style="color: red;">No items</span>
                <% } %>
            </div>

            <!-- Total Price Section -->
            <div class="text-center mt-4">
                <h4>Total Price: $<span id="totalPrice">0.00</span></h4>
                <button type="submit" class="btn btn-primary">Order Now</button>
            </div>
        </form>
    </div>
<!-- Bootstrap JS (Bundle includes Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>


</html>
