<%@page import="bean.*" %><%@page import="DAO.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/style.css">
    
    <title>SPOTLESS Cleaning Services</title>
</head>
<body>
<%@include file="./checkUserRole.jsp" %>

<header>
    <div class="header-title">
    	 <% if (isMember || isAdmin || isStaff) { %>
        <i>WELCOME</i> <%=member.getName().toUpperCase() %>
        <%} else { %>
        <i>SPOTLESS</i> Cleaning Services
        <%} %>
    </div>
    <div class="profile-icon">
        <% if (isMember || isAdmin || isStaff) { %>
            <a href="<%=request.getContextPath() %>/shared/displayMember.jsp" class="profile-button">Profile</a><br>
            <button class ="logout-button" onclick="location.href='<%=request.getContextPath() %>/customer/warningLogout.jsp'">Log out</button>
        <% } else { %>
            <button class="login-button" onclick="location.href='<%=request.getContextPath() %>/public/login.jsp'">Log in</button>
        <% } %>
    </div> 
</header>


<nav>
    <ul>
        <li><a href="<%=request.getContextPath() %>/index.jsp">Home</a></li>
        <li><a href="<%=request.getContextPath() %>/public/services.jsp">Services</a></li>
        <% if (isAdmin) { %>
        	  <li><a href="<%=request.getContextPath() %>/admin/serviceDashboard.jsp">Service Dashboard</a></li>
        <% }if (isMember){
        %>	       
        <li><a href="<%=request.getContextPath() %>/customer/cart.jsp">Cart</a></li>
        <li><a href="<%=request.getContextPath() %>/customer/bookingHistory.jsp">Booking History</a></li>
        <li><a href="<%=request.getContextPath() %>/shared/applyForStaff.jsp">Career Opportunities</a></li>
        <% } %>
        <% if (isAdmin){ %>	
        <li><a href="<%=request.getContextPath() %>/admin/displayAllMembers.jsp">Members</a></li>
        <li><a href="<%=request.getContextPath() %>/admin/feedback.jsp">Feedback History</a></li>
        <% } %>
      
    </ul>
</nav>
<%@include file="./successError.jsp" %>

</body>
</html>
