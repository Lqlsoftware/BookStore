<%--
  Created by IntelliJ IDEA.
  User: robinlu
  Date: 2017/7/6
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Mall</title>
    <meta name="description" content="Static description">
    <meta name="keywords" content="key words">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no,minimal-ui">
    <link href="css/style.css" rel='stylesheet' type='text/css'>
    <script src="js/jquery.js"></script>
    <script>
        window.onload = function () {
            var errMsg = '${errMsg}';
            if (errMsg != '')
                document.getElementById('login_name').placeholder = errMsg;
        }
    </script>
    <!--webfonts-->
    <%--<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,300,600,700,800' rel='stylesheet' type='text/css'>--%>
    <!--//webfonts-->
</head>

<body>
<div class="app-location">
    <h2>Welcome to Mall</h2>
    <div class="line"><span></span></div>
    <div class="location"><img src="img/fuckchat.png" class="img-responsive" alt="" /></div>
    <form action="login" method="post">
    <input id="login_name" name="login_name" type="text" class="text" placeholder="ID">
    <input name="password" type="password" placeholder="Password">
    <button onclick="submit();" value="Sign in">Sign in</button>
    </form>
    <div class="clear"></div>
</div>
</body>

</html>