<%@include file="../assets/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%
    int memberId = 14;
    List<Product> itemList = (List<Product>) session.getAttribute("products");
    String totalAmount = (String) session.getAttribute("total");
%>

<html>
<head>
    <title>Item List</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Available Items</h2>

        <form action="<%= request.getContextPath() %>/SaveMerchandizeData" method="post">

        
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
                                    <input type="text" id="quantity_<%= item.getId() %>" name="quantity_<%= item.getId() %>" value="0" class="form-control text-center mx-2" style="width: 50px;" readonly>
                                    <button type="button" class="btn btn-outline-secondary" onclick="increaseQuantity(<%= item.getId() %>, <%= item.getStock() %>, <%= item.getPrice() %>)">+</button>
                                </div>
                                <!-- Hidden input to send selected quantity -->
                                <input type="hidden" id="hidden_quantity_<%= item.getId() %>" name="quantity_<%= item.getId() %>" value="0">
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
                <h4>Total Price: $<span id="serverTotal"><%= totalAmount %></span> + $<span id="mTotalPrice">0.00</span> = $<span id="grandTotal"><%= totalAmount %></span></h4>
                <!-- Hidden Input for Total Price -->
                <input type="hidden" id="mTotalInput" name="mTotalInput" value="0">
                <button type="submit" class="btn btn-primary">Order Now</button>
            </div>
        </form>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        let merTotal = 0.0;
        let serverTotal = parseFloat("<%= totalAmount %>") || 0.0;

        window.onload = function () {
            document.getElementById("mTotalPrice").innerText = merTotal.toFixed(2);
            document.getElementById("mTotalInput").value = merTotal.toFixed(2);
            document.getElementById("grandTotal").innerText = (serverTotal + merTotal).toFixed(2);
        };

        function increaseQuantity(itemId, maxStock, price) {
            let quantityInput = document.getElementById("quantity_" + itemId);
            let hiddenInput = document.getElementById("hidden_quantity_" + itemId);

            let currentQty = parseInt(quantityInput.value);
            if (currentQty < maxStock) {
                quantityInput.value = currentQty + 1;
                hiddenInput.value = quantityInput.value; 
                updateTotal(price); 
            }
        }

        function decreaseQuantity(itemId, price) {
            let quantityInput = document.getElementById("quantity_" + itemId);
            let hiddenInput = document.getElementById("hidden_quantity_" + itemId);

            let currentQty = parseInt(quantityInput.value);
            if (currentQty > 0) {
                quantityInput.value = currentQty - 1;
                hiddenInput.value = quantityInput.value;
                updateTotal(-price); 
            }
        }

        function updateTotal(amount) {
            merTotal += amount;
            let newGrandTotal = serverTotal + merTotal;

            document.getElementById("mTotalPrice").innerText = merTotal.toFixed(2);
            document.getElementById("mTotalInput").value = merTotal.toFixed(2);
            document.getElementById("grandTotal").innerText = newGrandTotal.toFixed(2);
        }
    </script>
</body>
</html>
