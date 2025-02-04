<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        	text-align: left;
            max-width: 800px;
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
    </style>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<%if (isMember) { %>
	<div class="container">
    <p><b>Job Title:</b> Part-Time Cleaner</p>
    <p><b>Job Type:</b> Part-Time/Freelance</p>
    <p><b>Job Summary:</b> We are looking for a dedicated and detail-oriented Part-Time Cleaner to maintain cleanliness and hygiene in our premises. The ideal candidate should be able to work independently and efficiently to ensure a safe and clean environment.</p>

    <h2>Why Join Us?</h2>
    <p class="why"><b>SPOTLESS</b> is a growing local cleaning service provider, serving hundreds of households
     and businesses everyday. Ideal for individuals looking for flexible work hours.</p>
 

    <a href="apply.html" class="apply-btn">Apply Now</a>
</div>

<%} else if (isAdmin) { %>
	<p>List of applicants</p>
	
<%}else { %>
  	<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">Application is for members only</p>
<%} %>
<%@include file="../assets/footer.html" %>
</body>
</html>