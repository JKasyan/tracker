<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 10/18/2016
  Time: 5:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="/login" method="post">
    <input name="email" type="text">
    <input name="password" type="password">
    <input name="submit" type="submit" value="submit" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>
