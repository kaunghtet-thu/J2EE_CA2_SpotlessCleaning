<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cleaning Service Booking</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Flatpickr CSS -->
<link href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css" rel="stylesheet">
</head>
<body>
<%@page import="java.util.List" %>
<%@page import="DAO.MemberDAO" %>
<%@include file="../assets/header.jsp" %>

<%
	int memberId = (int)session.getAttribute("memberId");
	MemberDAO memberDAO = new MemberDAO();
	List<Address> addresses = (List<Address>)memberDAO.getAddressByMemberId(memberId);
	
String serviceId = request.getParameter("serviceId");
String name = request.getParameter("serviceName");
String price = request.getParameter("servicePrice");
String addressId = request.getParameter("addressId");
int cleaningHour = 1; // Default cleaning hour
double totalAmount = 0.0;

if (serviceId != null) {
    session.setAttribute("serviceId", serviceId);
    session.setAttribute("serviceName", name);
    session.setAttribute("servicePrice", price);
    session.setAttribute("addressId", addressId);
}  
	serviceId = (String) session.getAttribute("serviceId");
    name = (String) session.getAttribute("serviceName");
    price = (String) session.getAttribute("servicePrice");
    System.out.println(price + " at book a servicejsp");
    addressId = (String) session.getAttribute("addressId");

if (request.getParameter("cleaningHour") != null) {
    cleaningHour = Integer.parseInt(request.getParameter("cleaningHour"));
    totalAmount = Double.parseDouble(price) * cleaningHour;
} else
	totalAmount = Double.parseDouble(price);
%>

<h2>You have chosen <%= name %>.</h2>

<form action="BookService" method="POST">
    <!-- Calendar Date Picker -->
    <label for="serviceDate" class="form-label">Preferred Date</label>
    <input 
        type="text" 
        id="serviceDate" 
        class="form-control" 
        name="serviceDate" 
        placeholder="Select a date" 
        required>

    <label for="serviceTime" class="form-label">Preferred Time</label>
    <select class="form-control" id="serviceTime" name="serviceTime" required>
        <% 
        for (int hour = 7; hour <= 19; hour++) {
            String time = String.format("%02d:00", hour);
            String displayTime = (hour > 12 ? hour - 12 : hour) + ":00" + (hour >= 12 ? " PM" : " AM");
        %>
                        <option value="<%= time %>" <%= (time.equals(request.getParameter("serviceTime")) ? "selected" : "") %>><%= displayTime %></option>
            
        <% 
        } 
        %>
    </select>
    <!-- Cleaning Hour Dropdown -->
    <label for="cleaningHour" class="form-label">Cleaning Hour</label>
    
      <select class="form-control" id="cleaningHour" name="cleaningHour" onchange="setActionAndSubmit();">
        
        <% 
        for (int i = 1; i <= 5; i++) {
        %>
            <option value="<%= i %>" <%= (i == cleaningHour) ? "selected" : "" %>>
                <%= i %> hour<%= i > 1 ? "s" : "" %>
            </option>
        <% 
        } 
        %>
    </select>
    <label for="address">Select Address:</label>
    <select name="addressId" id="address" class="form-control" required>
	    <%
	        // Ensure the 'addresses' attribute is a List of Address objects passed from the servlet
	        if (addresses != null) {
	            for (Address address : addresses) {
	    %>
	                <option value="<%= address.getId() %>"><%= address.getAddress() %></option>
	    <%
	            }
	        } else {
	    %>
	            <option value="" disabled>No addresses available</option>
	    <%
	        }
	    %>
	</select>


    

    <!-- Display Total Amount -->
    <div class="mt-3">
        <label for="totalAmount" class="form-label"><strong>Total Amount:</strong></label>
        <input 
            type="text" 
            id="totalAmount" 
            class="form-control" 
            value="SGD <%= String.format("%.2f", totalAmount) %>" 
            readonly>
    </div>
    
    <!-- Hidden field to differentiate submit source 
    <input type="hidden" id="submitSource" name="submitSource" value="button"> -->
    
    <!-- Hidden field to identify the action -->
    <input type="hidden" name="action" id="action" value="lee">
    
    <!-- Submit Button --><br>
    <button type="submit" >Submit Booking</button> <br>

</form>
    <form action="bookings.jsp" method="POST" style="display:inline;">
		 <input type="submit" value="Go To Bookings Page" />
	</form>

<!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"></script>
  <!-- Flatpicker JS -->
  <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
  <script>
    // Initialize Flatpickr
    flatpickr("#serviceDate", {
      dateFormat: "Y-m-d", // Customize format
      minDate: "today",    // Disable past dates
      defaultDate: "today" // Pre-select today's date
    });
  function setActionAndSubmit() {
	    document.getElementById("action").value = "update";  // This will indicate we are just updating the total amount
        document.forms[0].submit();  // Automatically submit the form to refresh the total amount
	}
  </script>
  
<%@include file="../assets/footer.html" %>
</body>
</html>
