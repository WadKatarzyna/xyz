<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jupiter HOME</title>
<jsp:include page="header.jsp"></jsp:include>

 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

  <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css'>

      <style>
      /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
      .glyphicon { margin-right:5px; }
.thumbnail
{
    margin-bottom: 20px;
    padding: 0px;
    -webkit-border-radius: 0px;
    -moz-border-radius: 0px;
    border-radius: 0px;
}

.item.list-group-item
{
    float: none;
    width: 100%;
    background-color: #fff;
    margin-bottom: 10px;
}
.item.list-group-item:nth-of-type(odd):hover,.item.list-group-item:hover
{
    background: #428bca;
}

.item.list-group-item .list-group-image
{
    margin-right: 10px;
}
.item.list-group-item .thumbnail
{
    margin-bottom: 0px;
}
.item.list-group-item .caption
{
    padding: 9px 9px 0px 9px;
}
.item.list-group-item:nth-of-type(odd)
{
    background: #eeeeee;
}

.item.list-group-item:before, .item.list-group-item:after
{
    display: table;
    content: " ";
}

.item.list-group-item img
{
    float: left;
}
.item.list-group-item:after
{
    clear: both;
}
.list-group-item-text
{
    margin: 0 0 11px;
}

    </style>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/prefixfree/1.0.7/prefixfree.min.js"></script>
</head>
<body>
	<div class="container">
	    <div class="well well-sm">
	        <strong>Display</strong>
	        <div class="btn-group">
	            <a href="" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list">
	            </span>List</a> <a href="" id="grid" class="btn btn-default btn-sm"><span
	                class="glyphicon glyphicon-th"></span>Grid</a>
	        </div>
	    </div>
	    <div id="products" class="row list-group">
	        <div class="item  col-xs-4 col-lg-4">
	            <div class="thumbnail">
	                <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt="" />
	                <div class="caption">
	                    <h4 class="group inner list-group-item-heading">
	                        Product title</h4>
	                    <p class="group inner list-group-item-text">
	                        Product description... Lorem ipsum dolor sit amet, consectetuer adipiscing elit,
	                        sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.</p>
	                    <div class="row">
	                        <div class="col-xs-12 col-md-6">
	                            <p class="lead">
	                                $21.000</p>
	                        </div>
	                        <div class="col-xs-12 col-md-6">
	                            <a class="btn btn-success" href="http://www.jquery2dotnet.com">Add to cart</a>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
	  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script src='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
	
	  
	
	    <script  src="js/home.js"></script>

	
</body>
</html>

