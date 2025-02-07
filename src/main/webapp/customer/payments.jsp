<%@include file="../assets/header.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stripe Checkout</title>
    
    
    </script>
    <!-- Stripe.js -->
    <script src="https://js.stripe.com/v3/"></script>

    <!-- Button Styling -->
    <style>
        #checkout-button {
            background-color: #6772e5;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        #checkout-button:hover {
            background-color: #5469d4;
        }
        #center {
		    height: 100vh;            /* Full viewport height */
		    margin: 0;                /* Remove default margin */
		}
 
        
    </style>
</head>

<body id="center" class="d-flex align-items-center">
<%
    String totalStr = request.getParameter("total");
%>
	<div>
    	<p>Total Amount: <%= totalStr != null ? totalStr : "N/A" %></p>
	</div>
    
	<div id="container">
		<button id="checkout-button">Pay with STRIPE</button>
	</div>
<script>
const stripe = Stripe('pk_test_51QlQe5PO5PNshsgdtUMyjNn6KbZHlgMD4vlkvIHDvXNPwF5rFBASJtnoEWmwRNSCzwL8NsjCllTmNrrU1n5yzidf00PT3TeXJf'); // Replace with your Stripe publishable key

const baseUrl = window.location.origin; // Get the base URL
const endpoint = `${baseUrl}<%= request.getContextPath() %>/StripeAPI`;

console.log(endpoint);

document.getElementById('checkout-button').addEventListener('click', () => {
    const totalAmount = '<%= totalStr %>'; // Example total amount
	console.log(totalAmount)
	
    const formData = new URLSearchParams();
    formData.append("totalAmount", totalAmount);

    fetch(endpoint, {
        method: 'POST', // Keep POST method
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData.toString() // Convert to URL-encoded string
    })
    .then(response => response.json())
    .then(data => {
        return stripe.redirectToCheckout({ sessionId: data.id });
    })
    .catch(error => {
        console.error('Error:', error);
        alert('An error occurred during checkout. Please try again.');
    });
});

</script>

</body>
</html>
<%@include file="../assets/footer.html" %>
