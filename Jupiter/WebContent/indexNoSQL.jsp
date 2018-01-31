<%
	Cookie cookie = new Cookie("DB","NoSQL");
   	response.addCookie(cookie);
	response.sendRedirect("WorkWithController");
%>
