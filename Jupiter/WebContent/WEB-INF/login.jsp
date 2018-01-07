<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>

<%
	String errormessage = (String)request.getAttribute("errmsg");
%>
<!DOCTYPE html>
<html lang="en" >

<head>
  <meta charset="UTF-8">
  <title>Registration</title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script>

<!--  jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

  
  <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>
<link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css'>
<link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css'>

      <link rel="stylesheet" href="css/style.css">
       <link rel="stylesheet" href="css/login.css">
<link rel="stylesheet" href="https://formden.com/static/cdn/bootstrap-iso.css" /> 

<link rel="stylesheet" href="https://formden.com/static/cdn/font-awesome/4.4.0/css/font-awesome.min.css" />

<style>.bootstrap-iso .formden_header h2, .bootstrap-iso .formden_header p, .bootstrap-iso form{font-family: Arial, Helvetica, sans-serif; color: black}.bootstrap-iso form button, .bootstrap-iso form button:hover{color: white !important;} .asteriskField{color: red;}</style>

<jsp:include page="header.jsp" />
</head>

<body>

     <div class="container">

    <form class="well form-horizontal" action=" <%=request.getContextPath()%>/login" method="post"  id="contact_form">
	<fieldset>
	
	<!-- Form Name -->
	<legend><center><h2><b>Login</b></h2></center></legend><br>
	
	<div class = "errormessage">
		<span>
			<% if(null!=request.getAttribute("errmsg"))
   			   {
      			  out.println(request.getAttribute("errmsg"));
       
   			   }%>
		</span>
	</div>

	

	
	<!-- Text input-->
	
	<div class="form-group">
	  <label class="col-md-4 control-label">Username</label>  
	  <div class="col-md-4 inputGroupContainer">
	  <div class="input-group">
	  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	  <input  name="username" placeholder="Username" class="form-control"  type="text">
	    </div>
	  </div>
	</div>

	<!-- Text input-->
	
	<div class="form-group">
	  <label class="col-md-4 control-label" >Passwort</label> 
	    <div class="col-md-4 inputGroupContainer">
	    <div class="input-group">
	  <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	  <input name="user_passwort" placeholder="Passwort" class="form-control"  type="password">
	    </div>
	  </div>
	</div>

	<!-- Select Basic -->
	
	<!-- Success message -->
	<div class="alert alert-success" role="alert" id="success_message">Success <i class="glyphicon glyphicon-thumbs-up"></i> Success!.</div>
	
	<!-- Button -->
	<div class="form-group">
	  <label class="col-md-4 control-label"></label>
	  <div class="col-md-4"><br>
	    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<button type="submit" class="btn btn-warning" >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspSUBMIT <span class="glyphicon glyphicon-send"></span>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</button>
	  </div>
	</div>
	
	</fieldset>
</form>
</div>
    </div><!-- /.container -->
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/bootstrap-validator/0.4.5/js/bootstrapvalidator.min.js'></script>

  

  <!--  <script  src="js/index.js"></script> -->




</body>

</html>
