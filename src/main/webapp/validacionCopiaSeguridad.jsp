<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Vivienda" %>
<%@ page import="static comunicaciones.Telegram.enviarMensajeTelegram" %>
<%@ page import="static comunicaciones.PlantillaTelegram.messageTelegramViviendaAdd" %>
<%@ page import="models.Propietario" %>
<%@ page import="static persistencia.Persistencia.logViviendaWeb" %>
<%@ page import="static comunicaciones.Telegram.enviarMensajeTelegramWeb" %>

<%

    Object usuarioLogueado = session.getAttribute("usuarioLogueado");

    String rutaCarga = request.getParameter("rutaCarga");
    String rutaGuardado = request.getParameter("rutaGuardado");

    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = null;


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);

        if (rutaGuardado != null) {
            if (!rutaGuardado.endsWith("\\")) rutaGuardado = rutaGuardado.concat("\\");
            if (fernanbnbAPP.guardaBackup(rutaGuardado)) {
                session.setAttribute("exito", "Backup creado correctamente!!!");
                response.sendRedirect("exito.jsp");
                return;
            } else {
                session.setAttribute("error", "Ha ocurrido un error. ¿Ruta incorrecta?");
                response.sendRedirect("error.jsp");
                return; //
            }
        }

        if (rutaCarga != null) {
            if (!rutaCarga.endsWith("\\")) rutaCarga = rutaGuardado.concat("\\");
            if (fernanbnbAPP.cargarBackup(rutaCarga)) {
                session.setAttribute("exito", "Backup cargado correctamente!!!");
                response.sendRedirect("exito.jsp");
                return;
            } else {
                session.setAttribute("error", "Ha ocurrido un error. ¿Ruta incorrecta?");
                response.sendRedirect("error.jsp");
                return; //
            }
        }



    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un problema con la copia de seguridad...");
        response.sendRedirect("error.jsp");
        return; //
    }


%>
