<%@ page import="models.Reserva" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static persistencia.Persistencia.generaListadoReservas" %>
<%@ page import="comunicaciones.Email" %>
<%@ page import="comunicaciones.PlantillaMail" %>
<%@ page import="models.Propietario" %>
<%@ page import="static persistencia.Persistencia.generaListadoReservasWeb" %>
<%@ page import="controller.FernanbnbAPP" %>
<%
    // Verifica si el usuario ha iniciado sesión
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");
    if (usuarioLogueado == null) response.sendRedirect("index.jsp");

    FernanbnbAPP fernanbnbAPP;
    try {
        String contextPath = request.getServletContext().getRealPath("/");
        fernanbnbAPP = new FernanbnbAPP(contextPath);

    if (usuarioLogueado instanceof Propietario) {

        Propietario user = (Propietario) fernanbnbAPP.actualizaPerfil(usuarioLogueado);
        ArrayList<Reserva> reservas = user.getReservas();
        if (reservas.isEmpty()) {
            session.setAttribute("error", "No tiene ninguna reserva...");
            response.sendRedirect("error.jsp");
            return;
        } else {
            if (generaListadoReservasWeb(reservas, contextPath)) {
                Email.enviarMensajeExcelAdjuntoListadoReservasWeb(user.getEmail(), "Listado Reservas",
                        PlantillaMail.mensajeListadoReservas(user.getNombre()),contextPath);
                session.setAttribute("exito", "Mire su correo ya se han enviado sus reservas!");
                response.sendRedirect("exito.jsp");
                return;
            } else {
                session.setAttribute("error", "No se ha podido enviar el correo");
                response.sendRedirect("error.jsp");
                return;
            }
        }
    } else response.sendRedirect("index.jsp");
    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un error inesperado al mandar su mensaje...");
        response.sendRedirect("error.jsp");
        return; //
    }

%>
