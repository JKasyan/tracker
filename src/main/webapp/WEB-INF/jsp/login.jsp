<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="resources/css/reset.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<div class="header">
    <div class="links_header right_links_header">
        <ul>
            <li><a class="buttons_header" href="#">Log in</a></li>
        </ul>
    </div>
</div>

<div class="login_form">
    <div class="inner_form">
        <form action="/login" method="post" id="form_login">
            <label for="email">Email</label>
            <input class="tracker_input" type="text" name="email" id="email">
            <label for="password">Password</label>
            <input class="tracker_input" type="password" name="password" id="password">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <a href="#" class="tracker_button" id="submit_sign_in">Sign in</a>
        </form>
    </div>
</div>

<script>
    $('#submit_sign_in').click(function(){
        console.log('Submit...');
        $('#form_login').submit();
    });
</script>
</body>
</html>
