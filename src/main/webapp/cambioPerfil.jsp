<%@ page import="models.Usuario" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Administrador" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="componentes.Utils" %>
<%

    Object usuario = session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String nuevoNombre = request.getParameter("nuevoNombre");
    String nuevoCorreo = request.getParameter("nuevoCorreo");
    String nuevaContrasena = request.getParameter("nuevaContrasena");


    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP;


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        if (usuario != null) {
            if (usuario instanceof Usuario) {
                if (!nuevaContrasena.equals(((Usuario) usuario).getClave()))
                    nuevaContrasena = Utils.encriptarClave(nuevaContrasena);
                fernanbnbAPP.modificaPerfil(new Usuario(((Usuario) usuario).getId(), nuevoNombre, nuevaContrasena,
                        nuevoCorreo, ((Usuario) usuario).isVerificado())
                );
            } else if (usuario instanceof Propietario) {
                if (!nuevaContrasena.equals(((Propietario) usuario).getClave()))
                    nuevaContrasena = Utils.encriptarClave(nuevaContrasena);
                fernanbnbAPP.modificaPerfil(new Propietario(((Propietario) usuario).getId(), nuevoNombre, nuevaContrasena, nuevoCorreo,
                        ((Propietario) usuario).isVerificado()));
            } else if (usuario instanceof Administrador) {
                if (!nuevaContrasena.equals(((Propietario) usuario).getClave()))
                    nuevaContrasena = Utils.encriptarClave(nuevaContrasena);
                fernanbnbAPP.modificaPerfil(new Administrador(((Administrador) usuario).getId(), nuevoNombre, nuevaContrasena, nuevoCorreo,
                        ((Administrador) usuario).isVerificado()));
            }
        }
        session.setAttribute("exito", "Su perfil se ha editado correctamente!");
        response.sendRedirect("exito.jsp");
    } catch (Exception e) {
        session.setAttribute("error", "No se ha podido editar su perfil...");
        response.sendRedirect("error.jsp");
        return; //
    }


%>