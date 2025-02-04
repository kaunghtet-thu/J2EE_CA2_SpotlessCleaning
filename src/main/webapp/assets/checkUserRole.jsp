<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check User</title>
</head>
<body>
<%
    Member member = (Member) session.getAttribute("member");

    boolean isMember = false;
    boolean isAdmin = false;
    boolean isPublic = false;
    boolean isStaff = false;

    if (member == null) {
        isPublic = true;
    } else {
        int role = member.getRole();
        switch (role) {
            case 1:
                isAdmin = true;
                break;
            case 2:
                isMember = true;
                break;
            case 3:
                isStaff = true;
                break;
            default:
                isPublic = true;
                break;
        }
    }
%>


</body>
</html>