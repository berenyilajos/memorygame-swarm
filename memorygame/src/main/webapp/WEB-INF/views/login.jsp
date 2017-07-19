<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
</head>
<body>
	<div id="body">
		<div id="nav">
			<ul>
				<li><a class="active" href="#">Login</a></li>
			<li class="right" ><a href="<%= request.getContextPath() %>/game/register">Register</a></li>
			</ul>
		</div>
		<h3>Login</h3>
		<% String msg = (String)request.getAttribute("msg"); if (msg == null) msg = ""; %>
	<form action="<%= request.getContextPath() %>/game/login" METHOD="POST">
		Username <input type="text" name="username" required /><br>
		Password <input type="password" name="password" required /><br>
		<input type="submit" value="Login" /><br>
		<span class="error"><%= msg %></span>
	</form>
	</div>
</body>
</html>