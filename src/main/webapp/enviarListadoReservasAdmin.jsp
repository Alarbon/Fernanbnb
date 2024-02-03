<%@ page import="models.Reserva" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static persistencia.Persistencia.generaListadoReservas" %>
<%@ page import="comunicaciones.Email" %>
<%@ page import="comunicaciones.PlantillaMail" %>
<%@ page import="models.Propietario" %>
<%@ page import="static persistencia.Persistencia.generaListadoReservasWeb" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Administrador" %>
<%@ page import="java.util.Collections" %>
<%@ page import="static componentes.Utils.pulseEnterContinuar" %>
<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) response.sendRedirect("index.jsp");

    FernanbnbAPP fernanbnbAPP;
    try {
        String contextPath = request.getServletContext().getRealPath("/");
        fernanbnbAPP = new FernanbnbAPP(contextPath);

        if (usuarioLogueado instanceof Administrador) {

            ArrayList<Reserva> reservas = fernanbnbAPP.getAllReservas();
            Collections.sort(reservas);
            if (reservas.isEmpty()) {
                session.setAttribute("error", "No hay ninguna reserva...");
                response.sendRedirect("error.jsp");
            } else {
                if (generaListadoReservasWeb(reservas, contextPath)) {
                    Email.enviarMensajeExcelAdjuntoListadoReservasWeb(((Administrador) usuarioLogueado).getEmail(),
                            "Listado Reservas",
                            PlantillaMail.mensajeListadoReservas(((Administrador) usuarioLogueado).getNombre()), contextPath);
                    session.setAttribute("exito", "Listo! el listado ha sido enviado a su correo!!!");
                    response.sendRedirect("exito.jsp");
                    return; //
                } else {
                    session.setAttribute("error", "Ha ocurrido un error inesperado, vuelva a intentarlo mas tarde...");
                    response.sendRedirect("error.jsp");
                    return; //
                }
            }
        } else response.sendRedirect("index.jsp");
    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un error inesperado al mandar su mensaje...");
        response.sendRedirect("error.jsp");
        return; //
    }

%>
