<%--
  Created by IntelliJ IDEA.
  User: robinlu
  Date: 2017/7/6
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Mall</title>
    <meta name="description" content="Static description">
    <meta name="keywords" content="key words">
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no,minimal-ui">
    <script src="js/jquery.js"></script>
    <script src="js/main_0.js"></script>
    <link rel="stylesheet" href="css/main.css" type="text/css">
</head>
<body>
    <div class="topNav">
        <div class="navSearch" align="center"></div>
        <h1 class="logo" align="center">Book Mall</h1>
        <div class="cart" align="center">
            <div class="mycart"></div>
        </div>
    </div>
   <div class="container">
        <div class="search" align="center">
            价格
            ¥ <input id="from" type="tel"> - <input id="to" type="tel">
        </div>
        <div class="books" align="center"></div>
        <div class="page" align="center">
            <div class="select"></div>
            <div class="page_total"></div>
        </div>
        <div class="cover" align="center"></div>
   </div>
</body>
</html>
