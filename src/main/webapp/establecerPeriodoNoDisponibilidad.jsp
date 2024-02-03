<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Vivienda" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Usuario" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="static persistencia.Persistencia.logNuevaReservaWeb" %>

<%
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");


    if (usuarioLogueado == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    if (usuarioLogueado instanceof Usuario) {
        response.sendRedirect("index.jsp");
        return;
    }
    Propietario prop = null;
    if (usuarioLogueado instanceof Propietario) {
        prop = (Propietario) usuarioLogueado;
    }


    String contextPath = request.getServletContext().getRealPath("/");
    String viviendaId = request.getParameter("viviendaId");
    LocalDate fechaEntradaReserva = LocalDate.parse(request.getParameter("fechaEntradaReserva"));
    int numNochesReserva = Integer.parseInt(request.getParameter("numNochesReserva"));
    FernanbnbAPP fernanbnbAPP;
    Vivienda v;

    if (fechaEntradaReserva.isBefore(LocalDate.now()) || fechaEntradaReserva.isEqual(LocalDate.now())) {
        session.setAttribute("error", "No se permiten fechas anteriores al dia de hoy.");
        response.sendRedirect("error.jsp");
        return;
    }


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        v = fernanbnbAPP.buscaViviendaId(Integer.parseInt(viviendaId));

        if (fernanbnbAPP.addPeriodoNoDisponible(v, fechaEntradaReserva, numNochesReserva)) {
            logNuevaReservaWeb(usuarioLogueado, v.getId(),contextPath);
            session.setAttribute("exito", "Periodo de no disponibilidad realizado con exito.");
            response.sendRedirect("exito.jsp");
            return; //
        } else {
            session.setAttribute("error", "Las fechas seleccionadas no estan disponibles para la vivienda...");
            response.sendRedirect("error.jsp");
            return; //
        }

    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un problema con la solicitud de su reserva, vuelva a intentarlo...");
        response.sendRedirect("error.jsp");
        return; //
    }


%>