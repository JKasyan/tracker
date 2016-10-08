<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 10/7/2016
  Time: 2:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%--<script href="resources/js/socket.client.js"></script>--%>
    <script src="resources/js/socket/socket.client.js"></script>
    <script>
        //http://obscure-thicket-55734.herokuapp.com
        var socket = io.connect('http://localhost:9000');
        //
        socket.on('connect', function() {
            console.log('connect port 9000');
        });
    </script>
</head>
<body>

</body>
</html>
