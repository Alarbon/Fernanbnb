<%@ page import="java.security.AuthProvider" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Administrador" %>

<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");

    int idUsuario = Integer.parseInt(request.getParameter("id"));
    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = new FernanbnbAPP(contextPath);
    Object user = null;
    user = fernanbnbAPP.buscaUsuariobyId(idUsuario);
    if (user == null) user =fernanbnbAPP.buscaPropietariobyId(idUsuario);
    if (user != null) {
        if (user instanceof Usuario) fernanbnbAPP.validaTokenUsuario(((Usuario) user).getToken(), user);
        if (user instanceof Propietario) fernanbnbAPP.validaTokenUsuario(((Propietario) user).getToken(), user);
        if (user instanceof Administrador) fernanbnbAPP.validaTokenUsuario(((Administrador) user).getToken(), user);
        session.setAttribute("exito", "Su cuenta ha sido validada correctamente.");
        response.sendRedirect("exito.jsp");
    } else response.sendRedirect("index.jsp");

%>
