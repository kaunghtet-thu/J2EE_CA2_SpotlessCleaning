<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px; /* space to account for the header */
        }

        .register-card {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            text-align: center;
            margin-bottom: 30px;
        }

        .register-card input[type="text"],
        .register-card input[type="email"],
        .register-card input[type="password"],
        .register-card input[type="tel"],
        .register-card button {
            width: 80%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .register-card button {
            background-color: #c5d1ba;
            color: #000;
            border: none;
            cursor: pointer;
        }

        .register-card button:hover {
            color: white;
            background-color: #4cae4c;
        }

        .links {
            margin-top: 10px;
        }

        .links a {
            color: #007bff;
            text-decoration: none;
        }

        .links a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: red;
            font-size: 14px;
        }
    </style>
</head>
<body>
<%@include file="../assets/header.jsp" %>
<%
    String message = request.getParameter("errCode");
    if(message != null && message.equals("registerError")) {
        out.print("<h3 class='error-message'>An error occurred during registration. Please try again.</h3><br>");
    }
%>
    <div class="container">
        <div class="register-card">
            <h2>Create an account</h2>
            <form action="Register" method="post">
                <input type="text" name="name" placeholder="Full Name" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="tel" name="phone" placeholder="Phone Number" required pattern="[0-9]{8}">
                <button type="submit">Register</button>
            </form>
            <div class="links">
                <p>Already have an account? <a href="login.jsp">Login here</a></p>
                <p><a href="index.jsp">Explore without registering</a></p>
            </div>
        </div>
    </div>
<%@ include file="../assets/footer.html" %>
</body>
</html>

