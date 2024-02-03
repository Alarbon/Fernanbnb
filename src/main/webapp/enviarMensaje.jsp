<%@ page import="controller.FernanbnbAPP" %>

<%
    Object usuario = session.getAttribute("usuarioLogueado");


    int idEmisor = Integer.parseInt(request.getParameter("idEmisor"));
    int idReceptor = Integer.parseInt(request.getParameter("idReceptor"));
    int idVivienda = Integer.parseInt(request.getParameter("idVivienda"));
    String mensaje = request.getParameter("mensaje");

    if (usuario == null || idEmisor == -1) {
        response.sendRedirect("loginUsuario.jsp");
        return;
    }
    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP;


    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);

        if (mensaje.length()>250){
            session.setAttribute("error", "El mensaje no debe exceder los 250 caracteres...");
            response.sendRedirect("error.jsp");
            return; //
        }
        if (fernanbnbAPP.enviarMensajeChat(idEmisor, idReceptor, idVivienda, mensaje)) {
            response.sendRedirect("chat.jsp?idEmisor=" + idEmisor + "&idReceptor=" + idReceptor + "&idVivienda=" + idVivienda );
            return;
        } else {
            session.setAttribute("error", "No se ha enviado su mensaje...");
            response.sendRedirect("error.jsp");
            return; //
        }


    } catch (Exception e) {
        session.setAttribute("error", "No se ha enviado su mensaje...");
        response.sendRedirect("error.jsp");
        return; //
    }

%>