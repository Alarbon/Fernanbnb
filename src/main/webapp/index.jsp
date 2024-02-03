<%@ page import="models.Usuario" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Administrador" %>
<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    request.getSession().setAttribute("credencialesIncorrectas", false);
    if (usuarioLogueado != null) {
        if (usuarioLogueado instanceof Usuario) {
            // El usuario logueado es de la clase Usuario
            response.sendRedirect("indexUsuario.jsp");
            return;

        } else if (usuarioLogueado instanceof Propietario) {
            // El usuario logueado es de la clase Propietario
            response.sendRedirect("indexPropietario.jsp");
            return;

        } else if (usuarioLogueado instanceof Administrador) {
            // El usuario logueado es de la clase Administrador
            response.sendRedirect("indexAdministrador.jsp");
            return;

        } else {
            // El tipo de usuario no es reconocido
            response.sendRedirect("indexUsuario.jsp");
            return;
        }
    } else {
        response.sendRedirect("indexUsuario.jsp");
    }
%>
