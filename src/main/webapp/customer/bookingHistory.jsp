<%@include file="../assets/header.jsp" %>

<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="DAO.BookedServiceDAO" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Booking History</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Booking History</h2>
        <div class="accordion" id="bookingAccordion">
            <%
                BookedServiceDAO dao = new BookedServiceDAO();
                int memberId = 14; // Change this dynamically as needed
                int needKeyIn = 2;
                int completeId = 4;
                List<Map<String, Object>> bookings = dao.getBookingsWithServicesByMember(memberId);
                Map<Integer, List<Map<String, Object>>> groupedBookings = new HashMap<>();
                Map<Integer, Integer> completedCount = new HashMap<>();
                
                for (Map<String, Object> booking : bookings) {
                    int bookingId = (int) booking.get("booking_id");
                    int status = (int) booking.get("status_id");
                    groupedBookings.putIfAbsent(bookingId, new ArrayList<>());
                    groupedBookings.get(bookingId).add(booking);
                    
                    completedCount.put(bookingId, completedCount.getOrDefault(bookingId, 0) + (status == completeId ? 1 : 0));
                }
                
                for (Map.Entry<Integer, List<Map<String, Object>>> entry : groupedBookings.entrySet()) {
                    int bookingId = entry.getKey();
                    List<Map<String, Object>> services = entry.getValue();
                    int totalServices = services.size();
                    int completedServices = completedCount.getOrDefault(bookingId, 0);
            %>
            <div class="accordion-item">
                <h2 class="accordion-header" id="heading<%= bookingId %>">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse<%= bookingId %>" aria-expanded="true" aria-controls="collapse<%= bookingId %>">
                        Booking #<%= bookingId %> - Status: <%= completedServices %>/<%= totalServices %> Completed
                    </button>
                </h2>
                <div id="collapse<%= bookingId %>" class="accordion-collapse collapse show" aria-labelledby="heading<%= bookingId %>" data-bs-parent="#bookingAccordion">
                    <div class="accordion-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
									<th style="width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">Service Name</th>
                                    <th>Booking Date</th>
                                    <th>Booking Time</th>
                                    <th>Status</th>
                                    <th>Clock-in Code</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Map<String, Object> service : services) { %>
                                <tr>
									<td style="width: 200px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;"><%= service.get("service_name") %></td>

                                    <td><%= service.get("booking_date") %></td>
                                    <td><%= new java.text.SimpleDateFormat("h:mm a").format(service.get("booking_time")) %></td>
                                    <%
                                    	int status_id = (int)service.get("status_id"); 
                                    	String status = dao.getStatus(status_id);
                                    %>
                                    <td><%= status %></td>
                                    <td><%= (int)service.get("code") %></td>
                                    <td>
                                    	<% if (status_id == 1) { %>
                                        <p>Spotless Soon!</p>
                                    	<%} else if (status_id == 4) { %>
                                        <p>Cleaning Completed!</p>
                                        
                                        <%} else if (status_id == needKeyIn) { %>
                                        <p>Please give the code to staff when they arrive.</p>
                                        
                                        <% } else { %>
	                                    	<form action="../ConfirmComplete" method="post">
											    <input type="hidden" name="serviceId" value="<%=(int)service.get("service_id") %>">
											    <input type="hidden" name="bookingId" value="<%=(int)service.get("booking_id") %>">
											    <button type="submit">Confirm Complete</button>
											</form>
                                        <%} %>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@include file="../assets/footer.html" %>

