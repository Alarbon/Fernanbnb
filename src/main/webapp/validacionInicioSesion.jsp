<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="static persistencia.Persistencia.guardaLastSession" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Administrador" %>
<%@ page import="static persistencia.Persistencia.guardaLastSessionWeb" %>

<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    boolean credencialesIncorrectas = false;
    request.getSession().setAttribute("credencialesIncorrectas", credencialesIncorrectas);

    if (usuarioLogueado != null) {
        session.removeAttribute("usuarioLogueado");
    }

    String email = "";
    String clave = "";
    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = new FernanbnbAPP(contextPath);
    Object user = null;
    // Verifica de dónde provienen los parámetros
    if (request.getParameter("usuario") != null && request.getParameter("contrasena") != null) {
        email = request.getParameter("usuario").toLowerCase();
        clave = request.getParameter("contrasena");
        user = fernanbnbAPP.login(email, clave);
        if (user == null) {
            credencialesIncorrectas = true;
            request.getSession().setAttribute("credencialesIncorrectas", credencialesIncorrectas);
            response.sendRedirect("loginUsuario.jsp");
            return;
        }
    } else if (request.getParameter("usuarioPropietario") != null && request.getParameter("contrasenaPropietario") != null) {
        email = request.getParameter("usuarioPropietario");
        clave = request.getParameter("contrasenaPropietario");
        user = fernanbnbAPP.login(email, clave);
        if (user == null) {
            credencialesIncorrectas = true;
            request.getSession().setAttribute("credencialesIncorrectas", credencialesIncorrectas);
            response.sendRedirect("loginPropietario.jsp");
            return;
        }

    }
    if (user != null) {
        session.setAttribute("usuarioLogueado", user);
    } else session.setAttribute("usuarioLogueado", usuarioLogueado);

    if (user instanceof Usuario) {
        guardaLastSessionWeb(((Usuario) user).getId(), contextPath);
    } else if (user instanceof Propietario) {
        guardaLastSessionWeb(((Propietario) user).getId(), contextPath);
    } else if (user instanceof Administrador) {
        guardaLastSessionWeb(((Administrador) user).getId(), contextPath);
    }
    response.sendRedirect("index.jsp");

%>
