

<%
	Cookie cookie = new Cookie("DB","SQL");
   	response.addCookie(cookie);
	response.sendRedirect("WorkWithController");
%>

