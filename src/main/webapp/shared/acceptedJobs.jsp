<%@include file="../assets/header.jsp" %>

<%@page import="java.util.List" %>
<%@page import="DAO.BookedServiceDAO" %>
<%@page import="DAO.ServiceDAO" %>
<%@page import="java.time.format.DateTimeFormatter" %>
<%@page import="java.time.LocalTime" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Accepted Jobs</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Accepted Jobs</h2>
        <div class="accordion" id="bookingAccordion">
            <%	
            	int memberId = (int)session.getAttribute("memberId");
                BookedServiceDAO dao = new BookedServiceDAO();
                ServiceDAO serviceDAO = new ServiceDAO();
                int completeId = 4;
                //List<BookedService> services = dao.getAllBookedServices();
                List<BookedService> services = dao.getAllBookedServicesByStaffId(memberId);
                System.out.println("ziee: " + services.size());
            %>
            
            <div class="accordion-item">
                <h2 class="accordion-header">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true">
                        Job Listings
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse show">
                    <div class="accordion-body">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Service Name</th>
                                    <th>Booking Date</th>
                                    <th>Booking Time</th>
                                    <th>Status</th>
                                    <th>Key In Clock-in Code</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <% for (BookedService service : services) { 
                                	int id = service.getServiceId();
                                    String name = serviceDAO.getServiceNameById(id);
                                    String status = dao.getStatus(service.getStatusId());
                                  
                                    // Parse the 24-hour format time to a Date object
                                    LocalTime time = service.getBookingTime();
                                	String formattedTime = time.format(DateTimeFormatter.ofPattern("h:mm a"));
                                %>
                                <tr>
									<td><a href="../public/serviceDetails.jsp?serviceId=<%= id %>"><%= name %></a></td>                                    
									<td><%= service.getBookingDate() %></td>
                                    <td><%= formattedTime %></td>
                                    <td><%= status %></td>
                                    
                                    <td>
                                    	<form action="../VerifyCode" method="post">
										    <input name="code" placeholder="Code">
										    <input type="hidden" name="serviceId" value="<%=id %>">
										    <input type="hidden" name="bookingId" value="<%=service.getBookingId() %>">
										    <button type="submit">Submit</button>
										</form>
                                        
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
