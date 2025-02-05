<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log in</title>
<style>
	.container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 50px; /* space to account for the header */
        }

        .login-card {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            text-align: center;
            margin-bottom:30px;
        }

        .login-card input[type="email"],
        .login-card input[type="password"],
        .login-card button {
            width: 80%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

          .login-card button {
            background-color:#31525b;
            color: white;
            border: none;
            cursor: pointer;
        }

        .login-card button:hover {
            color: black;
            background-color: #497C85;
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
</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>

<% if (successMessage != null && successMessage.equals("Registration successful, please login.")) { %>
    <div class="editSuccess">
        <%= successMessage %>
    </div>
<% } %>
    <div class="container">
        <div class="login-card">
            <h2>Login to your account</h2>
            <form action="../VerifyUser" method="post">
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>
                <button type="submit">Login</button>
            </form>
            <div class="links">
                <p>Don't have an account? <a href="register.jsp">Register here</a></p>OR
                <p><a href="../index.jsp">Explore without login</a></p>

            </div>
        </div>
    </div>
<%@ include file="../assets/footer.html" %>
</body>
</html>
