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
  String tituloVivienda =request.getParameter("tituloVivienda");
  String descripcionVivienda =request.getParameter("descripcionVivienda");
  int numOcupantes = Integer.parseInt(request.getParameter("nuevoNumeroViajeros"));
  double precioNocheVivienda = Integer.parseInt(request.getParameter("precioNocheVivienda"));

  String contextPath = request.getServletContext().getRealPath("/");
  FernanbnbAPP fernanbnbAPP;


  try {
    fernanbnbAPP = new FernanbnbAPP(contextPath);
    Vivienda v = fernanbnbAPP.buscaViviendaId((idVivienda));

        if (fernanbnbAPP.modificaVivienda(new Vivienda(idVivienda, tituloVivienda, descripcionVivienda,
            v.getLocalidad(), v.getProvincia(),numOcupantes, precioNocheVivienda), true)){
      session.setAttribute("exito", "Su vivienda se ha modificado con exito!!!");
    response.sendRedirect("exito.jsp");
    return;
  }else {
      session.setAttribute("error", "No se ha podido modificar su vivienda...");
      response.sendRedirect("error.jsp");
      return;
    }


  } catch (Exception e) {
    session.setAttribute("error", "Ha ocurrido un error inexperado, vuelva a intentarlo...");
    response.sendRedirect("error.jsp");
    return; //
  }
%>