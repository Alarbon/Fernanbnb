<%@ page import="models.Reserva" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="models.Vivienda" %>
<%

    Object usuario = session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    int idVivienda = Integer.parseInt(request.getParameter("idVivienda"));

    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP;


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        Vivienda v = fernanbnbAPP.buscaViviendaId((idVivienda));
        if (v.tieneReservasPendientes()) {
            session.setAttribute("error", "No puede eliminar la vivienda, aún tiene reservas pendientes...");
            response.sendRedirect("error.jsp");
            return; //
        } else {
            if (fernanbnbAPP.deleteVivienda(v)){
                session.setAttribute("exito", "Su vivienda ha sido eliminada con exito!!!");
                response.sendRedirect("exito.jsp");
                return; //
            }else {
                session.setAttribute("error", "No se ha podido eliminar su vivienda...");
                response.sendRedirect("error.jsp");
                return; //
            }
        }


    } catch (Exception e) {
        session.setAttribute("error", "Ha ocurrido un error inexperado, vuelva a intentarlo...");
        response.sendRedirect("error.jsp");
        return; //
    }
%>