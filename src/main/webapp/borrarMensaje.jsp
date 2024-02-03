<%@ page import="models.Reserva" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="java.time.LocalDate" %>
<%

    Object usuario = session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    int idMensaje = Integer.parseInt(request.getParameter("idMensaje"));
    int idUsuarioHaBorrado = Integer.parseInt(request.getParameter("idUsuarioHaBorrado"));


    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP;


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        if (fernanbnbAPP.buscaMensajeById(idMensaje).getIdEmisor() == idUsuarioHaBorrado) {
            fernanbnbAPP.borrarMensajeEmisor(idMensaje, idUsuarioHaBorrado);
        } else fernanbnbAPP.borrarMensajeReceptor(idMensaje, idUsuarioHaBorrado);
        response.sendRedirect(request.getHeader("referer"));
        return; //

    } catch (Exception e) {
        session.setAttribute("error", "No se ha podido borrar su mensaje vuelva a intentarlo...");
        response.sendRedirect("error.jsp");
        return; //
    }
%>