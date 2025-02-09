<%@ include file="../assets/header.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
String serviceId = request.getParameter("serviceId");
String bookingId = request.getParameter("bookingId");
%>
    <div class="feedback-card">
        <form action="../AddFeedback" method="post">
            <input type="hidden" name="serviceId" value="<%=serviceId %>">
            <input type="hidden" name="bookingId" value="<%=bookingId %>">
            
            <h2>Rate Our Service</h2>
            <label for="rating">Rate our service:</label>
            <div class="star-rating">
                <input type="radio" name="rating" value="5" id="star5"><label for="star5">&#9733;</label>
                <input type="radio" name="rating" value="4" id="star4"><label for="star4">&#9733;</label>
                <input type="radio" name="rating" value="3" id="star3"><label for="star3">&#9733;</label>
                <input type="radio" name="rating" value="2" id="star2"><label for="star2">&#9733;</label>
                <input type="radio" name="rating" value="1" id="star1"><label for="star1">&#9733;</label>
            </div>
            
            <label for="feedback">Your Feedback:</label>
            <textarea name="feedback" rows="4" cols="50">What did you think about our service?</textarea>
            
            <button type="submit">Submit Feedback</button>
        </form>
    </div>
    
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f4f4f4;
        }
        .feedback-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }
        .star-rating {
            display: flex;
            flex-direction: row-reverse;
            justify-content: center;
            margin: 10px 0;
        }
        .star-rating input {
            display: none;
        }
        .star-rating label {
            font-size: 30px;
            color: gray;
            cursor: pointer;
        }
        .star-rating input:checked ~ label {
            color: gold;
        }
        textarea {
            width: 100%;
            margin-top: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</body>
</html>
<%@ include file="../assets/footer.html" %>
