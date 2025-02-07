<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Career Opportunities</title>
   <style>
   		body {
   			text-align: center;
   		}
        .container {
        	text-align: center;
            max-width: 50vw;
            margin: auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        .apply-btn {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 15px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .apply-btn:hover {
            background-color: #0056b3;
        }
        
        h2, .why {
        	text-align: center;
        }
          .table-container {
        width: 70vw;
        margin: 0 auto;
        overflow-x: auto;
        padding: 10px;
    }
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
    .accept {
        display: inline-block;
        background-color: #31525b;
        color: white;
        padding: 8px 15px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
        text-align: center;
    }
    .accept:hover {
        background-color: #233d46;
    }
    .reject {
     	display: inline-block;
        background-color: red;
        color: white;
        padding: 8px 15px;
        border-radius: 10px;
        text-decoration: none;
        font-weight: bold;
        text-align: center;
    }
     .reject:hover {
        background-color: darkred;
    }
    
 
        
    </style>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<%if (isMember) { %>
	
	<div class="container">
		<h1>Career opportunities at <b>SPOTLESS</b></h1>
	<%
	ApplicantDAO dao = new ApplicantDAO();
	int applicationStatus = dao.getApplicationStatus(((int)session.getAttribute("memberId")));
	if (applicationStatus == 3) {
%>


    <p><b>Job Title:</b> Cleaning Crew</p>
    <p><b>Job Type:</b> Part-Time/Freelance</p>
    <p><b>Job Summary:</b> We are looking for a dedicated and detail-oriented Part-Time Cleaner to maintain cleanliness and hygiene in our premises. The ideal candidate should be able to work independently and efficiently to ensure a safe and clean environment.</p>

    <h2>Why Join Us?</h2>
    <p class="why"><b>SPOTLESS</b> is a growing local cleaning service provider, serving hundreds of households
     and businesses everyday. Ideal for individuals looking for flexible work hours.</p>
 

    <form action="../ApplyApplicant" method="post">
    <input type="checkbox" name="isChecked" value="true"> I agree to the SPOTLESS's terms and conditions
    <button type="submit">Apply now</button>
</form>



<%} else if (applicationStatus == 2) {%>
		<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">
		Thank you for your interest<br>
		Unfortunately, Your application has been rejected</p>
 
<%} else if (applicationStatus == 1) { %>
		<p style="color: green; font-weight: bold; font-size: 16px; text-align: center;">
		Thank you for your interest<br>
		Your application is under review</p>

<%} %>


</div>
<%} else if (isAdmin) { %>
	<h1>List of applicants</h1>
	
<%
    ApplicantDAO dao = new ApplicantDAO();
    List<Applicant> applicants = dao.getAllPendingApplicants();
%>

<div class="table-container">
<table >
    <thead>
        <tr>
            <th>Application ID</th>
            <th>Applicant Name</th>
            <th>Agreed to T&C</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <%
            for (Applicant applicant : applicants) {
        %>
            <tr>
                <!-- Display applicant details -->
                <td><%= applicant.getApplicationId() %></td>
                <td><%= applicant.getName() %></td>
                <td><%= applicant.isAgree() ? "Yes" : "No" %></td>
                <td>
                    <form action="../UpdateApplicationStatus" method="post">
                        <input type="hidden" name="applicationId" value="<%= applicant.getApplicationId() %>">
                        <input type="hidden" name="approve" value="Yes">
                        <button class="accept" type="submit">Approve</button>
                    </form>
                    <form action="../UpdateApplicationStatus" method="post">
                        <input type="hidden" name="applicationId" value="<%= applicant.getApplicationId() %>">
                        <input type="hidden" name="approve" value="No">
                        <button class="reject" type="submit">Reject</button>
                    </form>
                </td>
            </tr>
        <%
            }
        %>
    </tbody>
</table>

<p>Staff list</p>
</div>
	
<%}else { %>
  	<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">Application is for members only</p>
<%} %>
<%@include file="../assets/footer.html" %>
</body>
</html>