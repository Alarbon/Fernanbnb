<%@ page import="controller.FernanbnbAPP" %>

<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    boolean correoEnUso = false;
    request.getSession().setAttribute("correoEnUso", correoEnUso);

    if (usuarioLogueado != null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String nombre = "";
    String email = "";
    String clave = "";
    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = new FernanbnbAPP(contextPath);
    Object user = null;
    // Verifica de dónde provienen los parámetros


    if (request.getParameter("nombreUsuario") != null && request.getParameter("correoUsuario") != null &&
            request.getParameter("contrasenaRegistro") != null) {
        if (!fernanbnbAPP.compruebaCorreoDisponible(request.getParameter("correoUsuario"))) {
            correoEnUso = true;
            request.getSession().setAttribute("correoEnUso", correoEnUso);
            response.sendRedirect("registroUsuario.jsp");
            return;
        } else {
            nombre = request.getParameter("nombreUsuario");
            email = request.getParameter("correoUsuario").toLowerCase();
            clave = request.getParameter("contrasenaRegistro");
            user = fernanbnbAPP.addUsuarioWeb(nombre, clave, email, false, contextPath);
        }
    }
    if (request.getParameter("nombrePropietario") != null && request.getParameter("correoPropietario") != null &&
            request.getParameter("contrasenaRegistroPropietario") != null) {
        if (!fernanbnbAPP.compruebaCorreoDisponible(request.getParameter("correoPropietario"))) {
            correoEnUso = true;
            request.getSession().setAttribute("correoEnUso", correoEnUso);
            response.sendRedirect("registroUsuarioPropietario.jsp");
            return;
        } else {
            nombre = request.getParameter("nombrePropietario");
            email = request.getParameter("correoPropietario");
            clave = request.getParameter("contrasenaRegistroPropietario");
            user = fernanbnbAPP.addPropietarioWeb(nombre, clave, email, false, contextPath);
        }
    }

    session.setAttribute("usuarioLogueado", user);
    session.setAttribute("exito", "Usuario registrado con exito. No olvide mirar su correo y validar la cuenta!");
    response.sendRedirect("exito.jsp");


%>
