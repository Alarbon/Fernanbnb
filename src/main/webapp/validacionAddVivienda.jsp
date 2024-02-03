<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Vivienda" %>
<%@ page import="static comunicaciones.Telegram.enviarMensajeTelegram" %>
<%@ page import="static comunicaciones.PlantillaTelegram.messageTelegramViviendaAdd" %>
<%@ page import="models.Propietario" %>
<%@ page import="static persistencia.Persistencia.logViviendaWeb" %>
<%@ page import="static comunicaciones.Telegram.enviarMensajeTelegramWeb" %>

<%

    Object usuarioLogueado = session.getAttribute("usuarioLogueado");

    if (usuarioLogueado instanceof Propietario) {
        int idPropietario = ((Propietario) usuarioLogueado).getId();
        String tituloVivienda = request.getParameter("tituloVivienda");
        String descripcionVivienda = request.getParameter("descripcionVivienda");
        String localidad = request.getParameter("localidad");
        String provincia = request.getParameter("provincia");
        int numHuespedes = Integer.parseInt(request.getParameter("numHuespedes"));
        double precioNoche = Double.parseDouble(request.getParameter("precioNoche"));

        String contextPath = request.getServletContext().getRealPath("/");
        FernanbnbAPP fernanbnbAPP;
        try {
            fernanbnbAPP = new FernanbnbAPP(contextPath);
            Vivienda v = fernanbnbAPP.addVivienda(idPropietario, tituloVivienda, descripcionVivienda, localidad,
                    provincia, numHuespedes, precioNoche);

            if (v == null) {
                session.setAttribute("error", "Ha ocurrido un problema al crear la vivienda...");
                response.sendRedirect("error.jsp");
                return; //
            } else {
                enviarMensajeTelegramWeb(messageTelegramViviendaAdd(v, (Propietario) usuarioLogueado),contextPath);
                logViviendaWeb((Propietario) usuarioLogueado, v.getId(), "Inserción de Vivienda", contextPath);
                session.setAttribute("exito", "Su vivienda se ha creado correctamente!!!");
                response.sendRedirect("exito.jsp");
                return; //
            }

        } catch (Exception e) {
            session.setAttribute("error", "No se ha podido crear la vivienda...");
            response.sendRedirect("error.jsp");
            return; //
        }
    }
    response.sendRedirect("index.jsp");



%>
