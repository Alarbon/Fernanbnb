<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Vivienda" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Usuario" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="static persistencia.Persistencia.logNuevaReservaWeb" %>
<%@ page import="static persistencia.Persistencia.logNuevaReserva" %>

<%
    Object usuarioLogueado = session.getAttribute("usuarioLogueado");

    Usuario usuario = (Usuario) usuarioLogueado;
    if (usuarioLogueado == null) {
        response.sendRedirect("loginUsuario.jsp");
        return;
    }
    if (usuarioLogueado instanceof Propietario) {
        response.sendRedirect("index.jsp");
        return;
    }


    String contextPath = request.getServletContext().getRealPath("/");
    String viviendaId = request.getParameter("idVivienda");
    LocalDate fechaEntradaReserva = LocalDate.parse(request.getParameter("fechaEntradaReserva"));
    int numNochesReserva = Integer.parseInt(request.getParameter("numNochesReserva"));
    int numViajerosReserva = Integer.parseInt(request.getParameter("numViajerosReserva"));
    FernanbnbAPP fernanbnbAPP;
    Vivienda v;

    if (fechaEntradaReserva.isBefore(LocalDate.now()) || fechaEntradaReserva.isEqual(LocalDate.now())) {
        session.setAttribute("error", "No se permiten fechas anteriores al dia de hoy vuelva a intentar reservar la " +
                "vivienda <a class=\"text-blue-600\" href=\"vistaViviendaUsuario.jsp?idVivienda=" + viviendaId + "\">aqui</a>");
        response.sendRedirect("error.jsp");
        return;
    }


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        v = fernanbnbAPP.buscaViviendaId(Integer.parseInt(viviendaId));
        if (numViajerosReserva > v.getMaxOcupantes()) {
            session.setAttribute("error", "El numero de huespedes maximo es de " + v.getMaxOcupantes());
            response.sendRedirect("error.jsp");
            return;
        }
    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un problema con la solicitud de su reserva, vuelva a intentarlo...");
        response.sendRedirect("error.jsp");
        return; //
    }

    if (fernanbnbAPP.addReservaWeb(usuario.getId(), v, fechaEntradaReserva, numNochesReserva, numViajerosReserva,
            true, contextPath)) {
        logNuevaReservaWeb(usuarioLogueado, v.getId(),contextPath);
        session.setAttribute("exito", "Reserva realizada con exito");
        response.sendRedirect("exito.jsp");
        return; //
    } else {
        session.setAttribute("error", "Las fechas seleccionadas no estan disponibles para la vivienda... Vuelva a la" +
                " vivienda <a class=\"text-blue-600\" href=\"vistaViviendaUsuario.jsp?idVivienda=" + viviendaId + "\">aqui</a>");
        response.sendRedirect("error.jsp");
        return; //
    }

%>