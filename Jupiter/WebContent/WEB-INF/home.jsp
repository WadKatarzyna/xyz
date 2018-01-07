<%@page import="model.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Account credentials = (Account)request.getSession().getAttribute("credentials");
@SuppressWarnings("unchecked")
List<Artikel> artikelList = (List<Artikel>)request.getAttribute("artikelList");
@SuppressWarnings("unchecked")
List<Hersteller> herstellerList = (List<Hersteller>)request.getAttribute("herstellerList");
@SuppressWarnings("unchecked")
List<BestellteArtikel> bestelleArtikelList = (List<BestellteArtikel>)request.getAttribute("bestelleArtikelList");
@SuppressWarnings("unchecked")
List<WarenkorbArtikel> warenkorbArtikel = (List<WarenkorbArtikel>) request.getAttribute("warenkorbArtikel");
@SuppressWarnings("unchecked")
List<Artikel> warenkorbartikellist = (List<Artikel>) request.getAttribute("warenkorbartikellist");

Warenkorb warenkorb = (Warenkorb) request.getAttribute("warennkorb");
%>



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
	    <%for(Artikel artikel : artikelList){ %>
	        <div class="item  col-xs-4 col-lg-4">
	        
	            <div class="thumbnail">
	                <img class="group list-group-image" src="http://placehold.it/400x250/000/fff" alt="" />
	                <div class="caption">
	              
	                    <h4 class="group inner list-group-item-heading">
	                        <%for(Hersteller hersteller : herstellerList){
	                        		if(artikel.getId() == hersteller.getId()){
	                        	%><b> <%out.println(hersteller.getName());  %></b> <%
	                        		}
	                        	 }%></h4>
	                       
	                    <p class="group inner list-group-item-text">
	                        <%out.println(artikel.getBeschreibung());%></p>
	                    <div class="row">
	                        <div class="col-xs-12 col-md-6">
	                            <p class="lead">
	                     <%for(BestellteArtikel b : bestelleArtikelList){
	                    	 	if(artikel.getId() == b.getArtikelId()){
	                    	 		out.println(b.getPreis());
	                    	 	}
	                    	 
	                    	 
	                     }
	                           
	                           
	                           %></p>
							</div>
							<%
							if(credentials != null ){ %>
								<form class="navbar-form navbar-left" method="post" action="<%=request.getContextPath()%>/home">
									<input type="hidden" name="artikelId" value="<%=artikel.getId()%>" />
									<input type="hidden" name="accountId" value="<%=credentials.getId()%>" />
									<input type="hidden" name="typ" value="cart" />
									<button type="submit" value="warenkorb" class="btn btn-success my-cart-icon"> Add to cart</button>
								</form>
							<%}
				
							%>
						
	                    </div>
	                   
	                </div>
	            </div>
	            
			</div>
			<%}%>

	    </div>
	     
    </div>
    <div id="cd-shadow-layer"></div>

      <!-- example mocked cart -->
			<div id="cd-cart">
				<h2 id="warenkorb">Warenkorb</h2>
				<ul class="cd-cart-items">
				<% double total = 0;
				if(warenkorbArtikel != null){
						 for(WarenkorbArtikel w : warenkorbArtikel){ %>
							<li>
								<form class="navbar-form navbar-left" method="post" action="<%=request.getContextPath()%>/home">
									<input type="hidden" name="typ" value="decrease" />
									<input type="hidden" name="artikelId" value="<%=w.getArtikelid()%>" />
									<input type="hidden" name="accountId" value="<%=credentials.getId()%>" />
									<input type="submit" value='-' class='qtyminus' field='quantity' />
									<% out.print(w.getMenge()); %>
								</form>
								<form class="navbar-form navbar-left" method="post" action="<%=request.getContextPath()%>/home">
									<input type="hidden" name="typ" value="increase" />
									<input type="hidden" name="artikelId" value="<%=w.getArtikelid()%>" />
									<input type="hidden" name="accountId" value="<%=credentials.getId()%>" />
									<input type="submit" value='+' class='qtyplus' field='quantity' />
								</form>
	
								<% 
								for(Artikel artikel : artikelList){
									if(w.getArtikelid()==artikel.getId()){
										out.print(artikel.getBeschreibung());
										
										for(BestellteArtikel b : bestelleArtikelList){
											if(artikel.getId() == b.getArtikelId()){
												%>
												<div class="cd-price">€<% out.print(b.getPreis()); %></div>
												<%
												total += w.getMenge() * b.getPreis();
											}
										}
									}
								}
								
								 %>
	
								
								
								<form class="navbar-form navbar-left" method="post" action="<%=request.getContextPath()%>/home">
									<input type="hidden" name="typ" value="remove" />
									<input type="hidden" name="artikelId" value="<%=w.getArtikelid()%>" />
									<a href="#" class="cd-item-remove cd-img-replace" onclick="$(this).closest('form').submit()">Remove</a>
								</form>
								
							</li>
						<%} 
						}%>
						
				</ul> <!-- cd-cart-items -->

				<div class="cd-cart-total">
					<% if (warenkorb != null) { %>
						<p>Total <span>$<% out.print(total); %></span></p>
					<% } else { %>
						<p>Total <span>$0</span></p>
					<% } %>
				</div> <!-- cd-cart-total -->

				<a href="#0" class="checkout-btn">Jetzt Bestellen(noch nicht impl.)</a>

				<p class="cd-go-to-cart"><a href="#0">Zeige Bestellungen(noch nicht impl.)</a></p>
			</div> <!-- cd-cart -->


 

	
</body>
</html>

