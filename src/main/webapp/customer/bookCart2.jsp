<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@include file="../assets/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Cleaning Services</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://js.stripe.com/v3/"></script>
<link rel="stylesheet" type="text/css" href="../assets/css/cart.css">
</head>
<body>

<%
    int memberId = (int)session.getAttribute("memberId");
    MemberDAO memberDAO = new MemberDAO();
    List<Address> addresses = (List<Address>)memberDAO.getAddressByMemberId(memberId);
    List<Service> cart = (List<Service>) session.getAttribute("cart");

    double subtotal = 0.0;
    double gstRate = 0.09;
    double gstAmount = 0.0;
    double totalAmount = 0.0;
%>

<div class="form-container">
    <h2 class="text-center mb-4">Checkout</h2>
<form id="booking-form" action="<%= request.getContextPath()%>/SaveCheckoutData" method="POST">
	<input type="hidden" name="memberId" value="<%= memberId %>">
    <!-- Checkboxes for Different Selections -->
    <div class="form-check">
        <input class="form-check-input" type="checkbox" id="differentAddresses" name="differentAddresses" onchange="toggleAddressInputs()">
        <label class="form-check-label" for="differentAddresses">Use different addresses for each service</label>
    </div>
    <div class="form-check">
        <input class="form-check-input" type="checkbox" id="differentDates" name="differentDates" onchange="toggleDateTimeInputs()">
        <label class="form-check-label" for="differentDates">Use different dates for each service</label>
    </div>
    <div class="form-check">
        <input class="form-check-input" type="checkbox" id="differentTimes" name="differentTimes" onchange="toggleDateTimeInputs()">
        <label class="form-check-label" for="differentTimes">Use different times for each service</label>
    </div>

    <% if (cart == null || cart.isEmpty()) { %>
        <p>Your cart is empty.</p>
    <% } else { %>
        <!-- Common Address Selection -->
        <div id="commonAddressSection">
            <label for="commonAddress">Select Address:</label>
                <select name="commonAddress" id="commonAddress" class="form-control" onchange="toggleNewAddressInput('commonAddress', 'newCommonAddress')">
                <% for (Address address : addresses) { %>
                    <option value="<%= address.getId() %>"><%= address.getAddress() %></option>
                <% System.out.println("id:" + address.getId());} %>
                <option value="new">New Address</option>
            </select>
            <input type="text" name="newCommonAddress" id="newCommonAddress" class="form-control mt-2" placeholder="Enter new address" style="display:none;">
        </div>

        <!-- Common Date & Time Selection -->
        <div id="commonDateSection">
            <label for="commonDate">Preferred Date:</label>
            <input type="date" name="commonDate" id="commonDate" class="form-control">
        </div>
        <div id="commonTimeSection">
            <label for="commonTime">Preferred Time:</label>
            <select class="form-control" id="commonTime" name="commonTime">
                <% 
                    for (int hour = 7; hour <= 19; hour++) {
                        String time = String.format("%02d:00", hour);
                        String displayTime = (hour > 12 ? hour - 12 : hour) + ":00" + (hour >= 12 ? " PM" : " AM");
                %>
                    <option value="<%= time %>"><%= displayTime %></option>
                <% 
                    } 
                %>
            </select>
        </div>

        <div class="mt-4">
            <% for (Service service : cart) { 
                subtotal += service.getPrice();
            %>
                <div class="card mb-3 p-3">
                    <h5><%= service.getName() %></h5>
                    <p>Price: SGD <%= String.format("%.2f", service.getPrice()) %></p>

                    <!-- Address Selection per Service -->
                    <div class="service-address" style="display:none;">
                        <label for="address_<%= service.getId() %>">Select Address:</label>
                        <select name="address_<%= service.getId() %>" id="address_<%= service.getId() %>" class="form-control">
                            <% for (Address address : addresses) { %>
                                <option value="<%= address.getId() %>"><%= address.getAddress() %></option>
                            <% } %>
                            <option value="new">Enter New Address</option>
                        </select>
                        <input type="text" name="newAddress_<%= service.getId() %>" id="newAddress_<%= service.getId() %>" class="form-control mt-2" placeholder="Enter new address" style="display:none;">
                    </div>

                    <!-- Date & Time Selection per Service -->
                    <div class="service-date" style="display:none;">
                        <label for="serviceDate_<%= service.getId() %>">Preferred Date:</label>
                        <input type="date" name="serviceDate_<%= service.getId() %>" id="serviceDate_<%= service.getId() %>" class="form-control">
                    </div>
                    <div class="service-time" style="display:none;">
                        <label for="serviceTime_<%= service.getId() %>">Preferred Time:</label>
                        <select class="form-control" id="serviceTime_<%= service.getId() %>" name="serviceTime_<%= service.getId() %>">
                            <% 
                                for (int hour = 7; hour <= 19; hour++) {
                                    String time = String.format("%02d:00", hour);
                                    String displayTime = (hour > 12 ? hour - 12 : hour) + ":00" + (hour >= 12 ? " PM" : " AM");
                            %>
                                <option value="<%= time %>"><%= displayTime %></option>
                            <% 
                                } 
                            %>
                        </select>
                    </div>
                </div>
            <% } %>
        </div>

        <%
            gstAmount = subtotal * gstRate;
            totalAmount = subtotal + gstAmount;
        %>

        <div class="mt-4">
            <h5>Subtotal: SGD <%= String.format("%.2f", subtotal) %></h5>
            <h5>GST (9%): SGD <%= String.format("%.2f", gstAmount) %></h5>
            <h4><strong>Total: SGD <%= String.format("%.2f", totalAmount) %></strong></h4>
        </div>
        <input type="hidden" name="totalAmount" value="<%= String.format("%.2f", totalAmount) %>">
        <div>
        	<button type="submit" class="btn btn-primary mt-3" id="book-btn">Confirm Booking</button>
        </div>

    <% } %>
</form>
</div>
<script>
function toggleNewAddressInput(selectId, inputId) {
    var select = document.getElementById(selectId);
    var input = document.getElementById(inputId);

    if (select.value === "new") {
        input.style.display = "block";
    } else {
        input.style.display = "none";
    }
}
function toggleAddressInputs() {
    let commonAddressSection = document.getElementById("commonAddressSection");
    let serviceAddressSections = document.querySelectorAll(".service-address");
    let differentAddressesCheckbox = document.getElementById("differentAddresses");

    commonAddressSection.style.display = differentAddressesCheckbox.checked ? "none" : "block";
    serviceAddressSections.forEach(section => {
        section.style.display = differentAddressesCheckbox.checked ? "block" : "none";
    });
}

function toggleDateTimeInputs() {
    let commonDateSection = document.getElementById("commonDateSection");
    let commonTimeSection = document.getElementById("commonTimeSection");
    let serviceDateSections = document.querySelectorAll(".service-date");
    let serviceTimeSections = document.querySelectorAll(".service-time");
    let differentDatesCheckbox = document.getElementById("differentDates");
    let differentTimesCheckbox = document.getElementById("differentTimes");

    commonDateSection.style.display = differentDatesCheckbox.checked ? "none" : "block";
    commonTimeSection.style.display = differentTimesCheckbox.checked ? "none" : "block";
    serviceDateSections.forEach(section => {
        section.style.display = differentDatesCheckbox.checked ? "block" : "none";
    });
    serviceTimeSections.forEach(section => {
        section.style.display = differentTimesCheckbox.checked ? "block" : "none";
    });
}
</script>

<%@include file="../assets/footer.html" %>
</body>
</html>