<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404 Page</title>
</head>
<body>
	<h1>Üdvözöljük!</h1>
    <p>A kért oldal nem található</p>
    <a href="<%= request.getContextPath() %>">Vissza a főoldalra</a>
</body>
</html>