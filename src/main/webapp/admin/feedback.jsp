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

   .table-container, .header {
	    display: flex;
	    justify-content: center; 
	    align-items: center; 
	    width: 100%; 
	}
    .actions {
        white-space: nowrap; 
    }
    .edit-button {
        font-size: 14px;
	    padding: 5px 10px;
	    background-color: #31525b;
	    color: white;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background-color 0.3s, color 0.3s;
	}
    .edit-button:hover {
 		color: black;
    	background-color: #497C85;
     }
     .remove-button {
     	font-size: 15px;
	    padding: 5px 10px;
	    background-color: red;
	    color: white;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background-color 0.3s, color 0.3s;
     }
     .remove-button:hover {
 		 background-color: darkred;
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
                    <form action="../DisplayFalse" method="POST">
                        <input type="hidden" name="feedbackId" value="<%= feedback.getId() %>">
                        <button type="submit" class="remove-button">Remove this from display</button>
                    </form>
                    <%} else { %>
                    <form action="../DisplayTrue" method="POST">
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
