<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Members</title>
<style>

	.table-container {
        width: 100%;
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
    .table-container table {
        max-width: 100%;
    }
    .table-container, .header {
	    display: flex;
	    justify-content: center; 
	    align-items: center; 
	    width: 90%; 
	}

    .actions {
        white-space: nowrap; 
    }
    .edit-button {
        font-size: 15px;
	    padding: 5px 10px;
	    background-color: #ffa101;
	    color: white;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background-color 0.3s, color 0.3s;
     }
     .edit-button:hover {
        color: white;
    	background-color: #fae6b1;
     }
     .editErr{
	 	color: red;
	    background-color: #fdecea;
	    border: 1px solid red;
	  }
	  .editSuccess {
	     color: green;
	     background-color: #e7f9e7;
	     border: 1px solid green;
	  }
	  

</style>
</head>
<body>
<%@include file="../assets/header.jsp" %>

<%
if (isAdmin) {
	
  MemberDAO dao = new MemberDAO();
	ArrayList<MemberInfo> members = dao.getAllMemberDetails(isAdmin);

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String idParam = request.getParameter("id");
        int hiddenid = (idParam != null) ? Integer.parseInt(idParam) : 0;
        String action = request.getParameter("action");

        boolean updateSuccess = false;
        String updateMessage = "";

        if (action != null && action.equals("delete")) {

			if (!isAdmin) {
				updateMessage = "Not authorized to delete member";
			}
            updateSuccess = dao.deleteMember(hiddenid, isAdmin);
            updateMessage = updateSuccess ? "Member deleted successfully!" : "Failed to delete member.";
        } if (updateSuccess) {
        %> <div class="editSuccess">
        	<%=updateMessage %>
           </div>
        <%} else {%>
        	<div class="editErr">
        	<%=updateMessage %>
        	</div>
        <%}
        members = dao.getAllMemberDetails(isAdmin);
    }
%>

<div>
	<p><%=members.size() %> total members found</p>
</div>
<div class="table-container">
 <table border="1" style="width:100%; border-collapse:collapse;">
    <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Role</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Addresses</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <% 
        if (members != null && !members.isEmpty()) {
            for (MemberInfo eachMember : members) { 
        %>
        <tr>
            <td><%= eachMember.getId() %></td>
            <td><%= eachMember.getName() %></td>
            <td><%= dao.getRoleName(eachMember.getRole())%></td>
            <td><%= eachMember.getEmail() %></td>
            <td><%= eachMember.getPhone() %></td>
            <td>
                <% 
                ArrayList<Address> addresses = eachMember.getAddress();
                if (addresses != null && !addresses.isEmpty()) {
                    for (Address address : addresses) { 
                %>
                <div>
                    <%= address.getAddress() %> 
                </div>
                <% 
                    }
                } else { 
                %>
                <div><i>No Address Saved</i></div>
                <% } %>
            </td>
            <td>
			    <form method="post" action="profile.jsp">
			        <input type="hidden" name="id" value="<%= eachMember.getId() %>">
			        <button type="submit" name="action" value="manage" class="edit-button">Manage</button>
			    </form>
			    <form method="post">
			        <input type="hidden" name="id" value="<%= eachMember.getId() %>">
			        <button type="submit" name="action" value="delete" class="edit-button">Delete</button>
			    </form>
			</td>
        </tr>
        <% 
            }
        } else { 
        %>
        <tr>
            <td colspan="6">No members found.</td>
        </tr>
        <% } %>
    </tbody>
</table>
</div>
<%} else { %>
<p style="color: red; font-weight: bold; font-size: 16px; text-align: center;">You are not authorized</p>
<%} %>
<%@include file="../assets/footer.html" %>
</body>
</html>