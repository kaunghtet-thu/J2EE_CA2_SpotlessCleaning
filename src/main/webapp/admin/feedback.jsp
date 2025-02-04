<%@page import="bean.*, DAO.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Feedbacks</title>
<style>
   
    table {
        width: 100%;
        table-layout: fixed; /* Ensures table columns don't stretch beyond the container */
        border-collapse: collapse;
        margin: 0px;
    }

    th, td {
        padding: 10px;
        border: 1px solid #ddd;
        word-wrap: break-word; /* Break long words into multiple lines */
    }

    th {
        background-color: #f4f4f4;
    }

    /* Optional: Add a max-width to make sure it doesn’t stretch too wide */
   .table-container, .header {
    display: flex;
    justify-content: center; /* This will horizontally center the table */
    align-items: center; /* This will vertically center the table (if needed) */
    width: 100%; /* Ensures the container takes full width */
	}
    .actions {
        white-space: nowrap; 
    }
     .edit-button {
            font-size: 14px;
            padding: 5px 10px;
            border: 1px solid grey;
            background-color: #c5d1ba;
            cursor: pointer;
            transition: background-color 0.3s, color 0.3s;
        }
        .edit-button:hover {
            background-color: #4cae4c;
            color: white;
        }

</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>

<% if (isAdmin) {
    // Get all feedbacks assuming isAdmin is guaranteed (no need to check)
    FeedbackDAO dao = new FeedbackDAO();
    ArrayList<Feedback> allFeedbacks = dao.getAllFeedback(isAdmin); 

%>

<div class="header">
<h2>Feedback List</h2>
</div>
<div class="table-container">


<table border="1">
    <thead>
        <tr>
            <th>Feedback ID</th>
            <th>Booking ID</th>
            <th>Rating</th>
            <th>Comments</th>
            <th>Display</th>
            <th>Action</th> 
        </tr>
    </thead>
    <tbody>
        <% 
            if (allFeedbacks != null && !allFeedbacks.isEmpty()) {
                for (Feedback feedback : allFeedbacks) { 
        %>
            <tr>
                <td><%= feedback.getId() %></td>
                <td><%= feedback.getBookingId() %></td>
                <td>
                    <% 
                        // Display the rating stars
                        int rating = feedback.getRating();
                        for (int i = 0; i < rating; i++) {
                            out.print("★");  // Filled star
                        }
                        for (int i = rating; i < 5; i++) {
                            out.print("☆");  // Empty star
                        }
                    %>
                </td>
                <td><%= feedback.getComments() %></td>
                <td><%= feedback.getDisplay() %></td>

                <td>
                    <%if (feedback.getDisplay()) { %>
                    <form action="DisplayFalse" method="POST">
                        <input type="hidden" name="feedbackId" value="<%= feedback.getId() %>">
                        <button type="submit" class="edit-button">Remove this from display</button>
                    </form>
                    <%} else { %>
                    <form action="DisplayTrue" method="POST">
                        <input type="hidden" name="feedbackId" value="<%= feedback.getId() %>">
                        <button type="submit" class="edit-button">Add this to display</button>
                    </form>
                    <%} %>
                </td>
            </tr>
        <% 
                }
            } else {
        %>
            <tr>
                <td colspan="6">No feedbacks available.</td> <!-- Adjust colspan to match the number of columns -->
            </tr>
        <% 
            }
        %>
    </tbody>
</table>
</div>
<%} else { %>
<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>

<%@include file="../assets/footer.html" %>
</body>
</html>
