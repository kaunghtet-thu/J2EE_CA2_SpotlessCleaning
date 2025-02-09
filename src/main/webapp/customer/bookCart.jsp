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
<style>
		body {
		    height: 80vh;            /* Full viewport height */
		    margin: 0;                /* Remove default margin */
		}
    
    	#card {
		    width: 50%;
		    padding: 20px;
		    border-radius: 10px;
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
		    background-color: #fff;
		    margin: 0 auto; /* This will center the card */
		    margin-bottom: 100px; /* Space at the bottom */
		    display: flex; /* Ensure block-level for centering */
		    flex-direction: column;
		}

	     .leebtn {
	    display: flex;
	    width: 100%;
	    padding: 10px;
	    margin: 10px 0;
	    background-color: #28a745;
	    color: white;
	    border: none;
	    cursor: pointer;
	  }
	  .center {
	  	align-self: center;
	  }
	  
    </style>
</head>
<body>
<%
    int memberId = (int) session.getAttribute("memberId");
	String singleId = request.getParameter("serviceId");
	boolean emptyCart = true;

	ServiceDAO serviceDAO = new ServiceDAO();
    MemberDAO memberDAO = new MemberDAO();
    
    List<Service> cart = (List<Service>) session.getAttribute("cart");
    List<Address> addresses = memberDAO.getAddressByMemberId(memberId);
    
	
	if(singleId != null && !singleId.isEmpty()) { //book directly without putting into cart
		int sId = Integer.parseInt(singleId);
		Service single = serviceDAO.getServiceById(sId);
		cart.clear();
		cart.add(single);
		emptyCart = false;
		session.setAttribute("singleId", single.getId());
	}
	session.setAttribute("emptyCart", emptyCart);
	
	// total
    double subtotal = cart != null ? cart.stream().mapToDouble(Service::getPrice).sum() : 0.0;
    double gstRate = 0.09;
    double gstAmount = subtotal * gstRate;
    double totalAmount = subtotal + gstAmount;

    String email = memberDAO.getMemberEmail(memberId);
    String maskedEmail = email.replaceFirst("\\w{3}", "***");
    
    
%>

<h2>Checkout</h2>
<div id="card" class="d-flex flex-column">
    <div style="align-self: center; text-align: left;">
        <%@include file="../customer/newAddress.jsp" %>
        
        
        <form id="booking-form" action="<%= request.getContextPath() %>/SaveCheckoutData" method="POST" class="d-flex flex-column">
            <input type="hidden" name="memberId" value="<%= memberId %>">
            
            <% if (cart != null && !cart.isEmpty()) { %>
                <!-- Checkboxes for Different Selections -->
                <% if (cart.size() > 1) { %>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="differentAddresses" name="differentAddresses" onchange="toggleInputs()">
                        <label class="form-check-label" for="differentAddresses">Use different addresses for each service</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="differentDates" name="differentDates" onchange="toggleInputs()">
                        <label class="form-check-label" for="differentDates">Use different dates for each service</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="differentTimes" name="differentTimes" onchange="toggleInputs()">
                        <label class="form-check-label" for="differentTimes">Use different times for each service</label>
                    </div>
                <% } %>

                <!-- Common Address Selection -->
                <div id="commonAddressSection">
                    <label for="commonAddress">Select Address:</label>
                    <select name="commonAddress" id="commonAddress" class="form-control">
                        <% for (Address address : addresses) { %>
                            <option value="<%= address.getId() %>"><%= address.getAddress() %></option>
                        <% } %>
                    </select>
                </div>

                <!-- Common Date & Time Selection -->
                <div id="commonDateSection">
                    <label for="commonDate">Preferred Date:</label>
                    <input type="date" name="commonDate" id="commonDate" class="form-control">
                </div>
                <div id="commonTimeSection">
                    <label for="commonTime">Preferred Time:</label>
                    <select class="form-control" id="commonTime" name="commonTime">
                        <% for (int hour = 7; hour <= 19; hour++) { 
                            String time = String.format("%02d:00", hour);
                            String displayTime = (hour > 12 ? hour - 12 : hour) + ":00" + (hour >= 12 ? " PM" : " AM");
                        %>
                            <option value="<%= time %>"><%= displayTime %></option>
                        <% } %>
                    </select>
                </div>
				
				<!-- Common Date & Time Selection -->
           
                <!-- Service-Specific Selection -->
                <div class="mt-4">
                    <% for (Service service : cart) { %>
                        <div class="card center mb-3 p-3">
                            <h5><%= service.getName() %></h5>
                            <p>Price: SGD <%= String.format("%.2f", service.getPrice()) %></p>

                            <!-- Address per Service -->
                            <div class="service-address" style="display:none;">
                                <label for="address_<%= service.getId() %>">Select Address:</label>
                                <select name="address_<%= service.getId() %>" class="form-control">
                                    <% for (Address address : addresses) { %>
                                        <option value="<%= address.getId() %>"><%= address.getAddress() %></option>
                                    <% } %>
                                </select>
                            </div>

                            <!-- Date & Time per Service -->
                            <div class="service-date" style="display:none;">
                                <label for="serviceDate_<%= service.getId() %>">Preferred Date:</label>
                                <input type="date" name="serviceDate_<%= service.getId() %>" class="form-control">
                            </div>
                            <div class="service-time" style="display:none;">
                                <label for="serviceTime_<%= service.getId() %>">Preferred Time:</label>
                                <select class="form-control" name="serviceTime_<%= service.getId() %>">
                                    <% for (int hour = 7; hour <= 19; hour++) { 
                                        String time = String.format("%02d:00", hour);
                                        String displayTime = (hour > 12 ? hour - 12 : hour) + ":00" + (hour >= 12 ? " PM" : " AM");
                                    %>
                                        <option value="<%= time %>"><%= displayTime %></option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <!-- Gender Service -->                            
                            <div class="service-gender" style="display:block;">
							    <label>Preferred Gender:</label><br>
							    <input type="radio" id="male_<%= service.getId() %>" name="serviceGender_<%= service.getId() %>" value="M">
							    <label for="male_<%= service.getId() %>">Male</label>
							
							    <input type="radio" id="female_<%= service.getId() %>" name="serviceGender_<%= service.getId() %>" value="F">
							    <label for="female_<%= service.getId() %>">Female</label>
							</div>

                            
                        </div>
                    <% } %>
                </div>

                <!-- Pricing Summary -->
                <div class="mt-4">
                    <h5>Subtotal: SGD <%= String.format("%.2f", subtotal) %></h5>
                    <h5>GST (9%): SGD <%= String.format("%.2f", gstAmount) %></h5>
                    <h4><strong>Total: SGD <%= String.format("%.2f", totalAmount) %></strong></h4>
                </div>
                <input type="hidden" name="totalAmount" value="<%= String.format("%.2f", totalAmount) %>">

                <!-- Email Selection -->
                <div class="mt-4">
                    <p id="currentEmail">Receipt will be sent to this email: <%= maskedEmail %></p>
                    <div id="newEmailSection" class="form-group" style="display:none;">
                        <label for="recipientEmail">Recipient Email:</label>
                        <input type="email" class="form-control" id="recipientEmail" name="recipientEmail" placeholder="Enter recipient email">
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="useNewEmail" name="useNewEmail" onchange="toggleNewEmailInput()">
                        <label class="form-check-label" for="useNewEmail">Use a different email</label>
                    </div>
                </div>
				<input type="hidden"  id="email" name="email" value="<%= email %>">
                <button class="btn btn-primary mt-3" id="book-btn">Confirm Booking</button>
            <% } else { %>
                <p>Your cart is empty.</p>
            <% } %>
        </form>
    </div>
</div>

<script>
document.addEventListener("DOMContentLoaded", function () {
    setDefaultDates();
    toggleInputs();
});

function setDefaultDates() {
    document.querySelectorAll('input[type="date"]').forEach(input => {
        input.value = new Date().toISOString().slice(0, 10);
    });
}

function toggleInputs() {
    let showAddress = document.getElementById("differentAddresses").checked;
    let showDate = document.getElementById("differentDates").checked;
    let showTime = document.getElementById("differentTimes").checked;

    document.getElementById("commonAddressSection").style.display = showAddress ? "none" : "block";
    document.getElementById("commonDateSection").style.display = showDate ? "none" : "block";
    document.getElementById("commonTimeSection").style.display = showTime ? "none" : "block";

    document.querySelectorAll(".service-address").forEach(el => el.style.display = showAddress ? "block" : "none");
    document.querySelectorAll(".service-date").forEach(el => el.style.display = showDate ? "block" : "none");
    document.querySelectorAll(".service-time").forEach(el => el.style.display = showTime ? "block" : "none");
}

function toggleNewEmailInput() {
    var currentEmailSection = document.getElementById("currentEmail");
    var newEmailSection = document.getElementById("newEmailSection");
    var useNewEmailCheckbox = document.getElementById("useNewEmail");

    if (useNewEmailCheckbox.checked) {
        currentEmailSection.style.display = "none";
        newEmailSection.style.display = "block";
    } else {
        currentEmailSection.style.display = "block";
        newEmailSection.style.display = "none";
    }
}

</script>

<%@include file="../assets/footer.html" %>
</body>
</html>