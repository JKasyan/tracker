<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="resources/css/reset.css">
    <link rel="stylesheet" href="resources/css/main.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
    <!-- Google Tag Manager -->
    <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
    new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
    j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
    'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-TDTD9MN');</script>
    <!-- End Google Tag Manager -->
</head>
<body>

<!-- Google Tag Manager (noscript) -->
<noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-TDTD9MN"
height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
<!-- End Google Tag Manager (noscript) -->

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
