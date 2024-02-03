<%@ page import="static persistencia.Persistencia.logSesion" %>
<%@ page import="static persistencia.Persistencia.logSesionWeb" %>
<%
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    String contextPath = request.getServletContext().getRealPath("/");
    logSesionWeb(usuarioLogueado, "Cierre de sesión", contextPath);
    session.removeAttribute("usuarioLogueado");
    response.sendRedirect("index.jsp");
%>