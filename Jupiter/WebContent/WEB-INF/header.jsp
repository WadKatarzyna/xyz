<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Account credentials = (Account)request.getSession().getAttribute("credentials");



%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Jupiter</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


  <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/cart/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/cart/resetCart.css"> <!-- CSS reset -->
<link rel="stylesheet" href="css/cart/styleCart.css"> <!-- Gem style -->
<script src="js/cart/modernizr.js"></script> <!-- Modernizr -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Shopin Responsive web template, Bootstrap Web Templates, Flat Web Templates, AndroId Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link href="css/cart/style4.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/cart/jstarbox.js"></script>
<link rel="stylesheet" href="css/cart/jstarbox.css" type="text/css" media="screen" charset="utf-8" />




  <style>
  .badge-notify{
    background:red;
    position:relative;
    top: -20px;
    right: 10px;
  }
  .my-cart-icon-affix {
    position: fixed;
    z-index: 999;
  }
  </style>


<script>

</script>
</head>
<body>

      
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="home">Jupiter</a>
    </div>
    <ul class="nav navbar-nav">
      <li class="active"><a href="home">Home</a></li>

     
    </ul>
    <form class="navbar-form navbar-left" method="Get" action=" <%=request.getContextPath()%>/home" >
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search" name="searchText">
        <div class="input-group-btn">
          <button class="btn btn-default" type="submit">
            <i class="glyphicon glyphicon-search"></i>
          </button>
        </div>
      </div>
    </form>
    
    <ul class="nav navbar-nav navbar-right">
      
      
     

    <% if(credentials == null){ %>
			
	      <li><a href="registration"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
        <li><a href="login" ><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
    
      <%}else{ %>
     	  <li id="cd-cart-trigger" ><a href="#0"><span></span></a></li>
	      <li><a><span class="glyphicon glyphicon-user"></span> Hallo <%out.print(credentials.getUsername()); %></a></li>
	      <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
	   <%} %>
    </ul>
  </div>


</nav>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/cart/main.js"></script> <!-- Gem jQuery -->


</body>
</html>