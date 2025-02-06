<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="DAO.MemberDAO" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 35vw;
            margin: auto;
            padding: 15px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .message {
            margin-bottom: 15px;
            padding: 10px;
            border-radius: 4px;
        }
        .message.success {
            color: green;
            background-color: #e7f9e7;
            border: 1px solid green;
        }
        .message.error {
            color: red;
            background-color: #fdecea;
            border: 1px solid red;
        }
        fieldset {
            margin-bottom: 10px;
            background-color: #b3dee5;
        }
        button {
            background-color: #31525b;
            color: white;
            border-radius: 15px;
        }
        input::placeholder {
            font-style: italic;
        }
    </style>
</head>
<body>
<%@ include file="../assets/header.jsp" %>

<%
    // DAO and member information setup
    MemberDAO dao = new MemberDAO();

    ArrayList<Role> roleNames = dao.getRoleList();

    // Determine the current user ID
    int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : member.getId();

    int actorId = member.getId();

    // Fetch member details
    MemberInfo memberInfo = dao.getMemberDetail(id);

    // Initialize response variables
    boolean updateSuccess = false;
    String updateMessage = null;

    // Handle POST requests for updates
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String idParam = request.getParameter("id");
        int hiddenId = idParam != null ? Integer.parseInt(idParam) : 0;
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        if (field != null) {
            switch (field) {
	            case "role": {
	                int newRoleId = Integer.parseInt(request.getParameter("newRole"));
	                updateSuccess = dao.updateMemberRole(hiddenId, newRoleId, actorId, session);
	                updateMessage = updateSuccess ? "Role updated successfully!" : "Failed to update role.";
	                break;
	            }
                case "name":
                    updateSuccess = dao.updateMemberName(hiddenId, value, actorId, session);
                    updateMessage = updateSuccess ? "Name updated successfully!" : "Failed to update name.";
                    break;
                case "email":
                    updateSuccess = dao.updateMemberEmail(hiddenId, value, actorId);
                    updateMessage = updateSuccess ? "Email updated successfully!" : "Failed to update email.";
                    break;
                case "phone":
                    updateSuccess = dao.updateMemberPhone(hiddenId, value);
                    updateMessage = updateSuccess ? "Phone updated successfully!" : "Failed to update phone.";
                    break;
                case "newAddress":
                    updateSuccess = dao.addMemberAddress(hiddenId, value);
                    updateMessage = updateSuccess ? "Address added successfully!" : "Failed to add address.";
                    break;
                case "address":
                    int index = 0;
                    while (true) {
                        String addressIdParam = "addressId_" + index;
                        String valueParam = "value_" + index;
                        String addressValue = request.getParameter(valueParam);
                        if (addressValue == null) break;

                        int addressId = Integer.parseInt(request.getParameter(addressIdParam));
                        String deleteAddress = request.getParameter("deleteAddress");

                        if (deleteAddress != null && Integer.parseInt(deleteAddress) == addressId) {
                            updateSuccess = dao.deleteMemberAddress(addressId);
                            updateMessage = updateSuccess ? "Address deleted successfully!" : "Failed to delete address.";
                        } else {
                            updateSuccess = dao.updateMemberAddress(addressId, addressValue);
                            updateMessage = updateSuccess ? "Address updated successfully!" : "Failed to update address.";
                        }
                        index++;
                    }
                    break;
                case "password":
                    String oldPassword = request.getParameter("oldPassword");
                    String newPassword = request.getParameter("newPassword");
                    String confirmNewPassword = request.getParameter("confirmNewPassword");
                    if (newPassword != null && confirmNewPassword != null && newPassword.equals(confirmNewPassword)) {
                        updateSuccess = dao.updateMemberPassword(hiddenId, newPassword, oldPassword);
                        updateMessage = updateSuccess ? "Password updated successfully!" : "Failed to update password.";
                    } else {
                        updateMessage = "Passwords do not match.";
                    }
                    break;
                default:
                    updateMessage = "Invalid field specified.";
            }
        }

        memberInfo = dao.getMemberDetail(hiddenId); // Refresh member details

    }
%>

<div class="container">
    <h1>User Profile of <%=memberInfo.getName() %></h1>

    <% if (updateMessage != null) { %>
        <div class="message <%= updateSuccess ? "success" : "error" %>">
            <strong><%= updateMessage %></strong>
        </div>
    <% } %>
    <%
    if (memberInfo == null) {
%>
    <div class="message error">
        <strong>Error: Member details not found. Please check the Member ID.</strong>
    </div>
<%
    } 
    
    %>

	
    <fieldset>
        <legend>Role:</legend>
        <form method="POST">
            <p><%= memberInfo.getName() %> is currently a <%= dao.getRoleName(memberInfo.getRole()) %>.</p>
            <input type="hidden" name="id" value="<%= id %>">
            <input type="hidden" name="field" value="role">
            <%  if(isAdmin){
                String currentRole = dao.getRoleName(memberInfo.getRole());
                for (Role role : roleNames) {
                    if (!role.getName().equalsIgnoreCase(currentRole)) { 
            %>
                <button type="submit" name="newRole" value="<%= role.getId() %>">
                    Make <%= role.getName() %>
                </button>
            <% } } } %>
        </form>
    </fieldset>
 
    
    <fieldset>

        <legend>Name:</legend>

        <form method="post" class="edit-form">
        	<input type="hidden" name="id" value="<%= id %>">
            <input type="hidden" name="field" value="name">
            <input type="text" id="name" name="value" value="<%= memberInfo.getName() %>" required>
            <button type="submit">Update</button>
        </form>

    </fieldset>

	<% if (id == actorId) { %>
    <fieldset>
        <legend>Email:</legend>
        <form method="post">
            <input type="hidden" name="id" value="<%= id %>">
            <input type="hidden" name="field" value="email">
            <input type="text" name="value" value="<%= memberInfo.getEmail() %>" required>
            <button type="submit">Update</button>
        </form>
    </fieldset>
	<% } %>
	
    <fieldset>
        <legend>Phone:</legend>
        <form method="post">
            <input type="hidden" name="id" value="<%= id %>">
            <input type="hidden" name="field" value="phone">
            <input type="text" name="value" value="<%= memberInfo.getPhone() %>" required>
            <button type="submit">Update</button>
        </form>
    </fieldset>

    <fieldset>
	    <legend>Address:</legend>
	    <form method="post">
	        <input type="hidden" name="id" value="<%= id %>">
	        <% 
	            List<Address> addresses = memberInfo.getAddress();
	            int index = 0;
	            if(addresses.size()<=0) {
	            	%><label>You don't have registered addresses yet</label> <%
	            }
	            for (Address address : addresses) { 
	        %>
	            <!-- Displaying the address number -->
	            <label> <%= index + 1 %>:</label>
	            <input type="hidden" name="field" value="address">
	            <input type="hidden" name="addressId_<%= index %>" value="<%= address.getId() %>">
	            <input type="text" name="value_<%= index %>" value="<%= address.getAddress() %>" required>
	            <button type="submit" name="updateAddress" value="<%= index %>">Update</button>
	            <button type="submit" name="deleteAddress" value="<%= address.getId() %>">Delete</button>
	            <br>
	        <% index++; } %>
	    </form>
	
	    <form method="post">
	    	 <label> <%= index + 1 %>:</label>
	        <input type="hidden" name="field" value="newAddress">
	        <input type="hidden" name="id" value="<%= id %>">
	        <input type="text" name="value" placeholder="Add a new address" required>
	        <button type="submit">Add</button>
	    </form>
	</fieldset>


   
    <% if (id == actorId) { %>
    <fieldset>
        <legend>Reset Password</legend>
        <form method="post">
            <input type="hidden" name="field" value="password">
            <input type="hidden" name="id" value="<%= id %>">
            <input type="password" name="oldPassword" placeholder="Enter old password" required><br>
            <input type="password" name="newPassword" placeholder="Enter new password" required><br>
            <input type="password" name="confirmNewPassword" placeholder="Confirm new password" required><br>
            <button type="submit">Update</button>
        </form>
    </fieldset>
    <% } %>
</div>
<%@ include file = "../assets/footer.html"%>>
</body>
</html>