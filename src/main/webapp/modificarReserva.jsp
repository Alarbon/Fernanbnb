<%@ page import="models.Reserva" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="java.time.LocalDate" %>
<%

  Object usuario = session.getAttribute("usuarioLogueado");
  if (usuario == null) {
    response.sendRedirect("index.jsp");
    return;
  }
  String idReserva = request.getParameter("idReserva");
  LocalDate fechaInicio =LocalDate.parse(request.getParameter("nuevaFechaReserva"));
  int numNoches = Integer.parseInt(request.getParameter("nuevoNumeroNoches"));
  int numOcupantes = Integer.parseInt(request.getParameter("nuevoNumeroViajeros"));

  String contextPath = request.getServletContext().getRealPath("/");
  FernanbnbAPP fernanbnbAPP;


  try {
    fernanbnbAPP = new FernanbnbAPP(contextPath);
    Reserva r = fernanbnbAPP.buscaReservabyId(Integer.parseInt(idReserva));

    if (!r.getFechaInicio().minusDays(6).isAfter(LocalDate.now())) {
      session.setAttribute("error", "Para modificar su reserva es necesario hacerlo 7 previos a su fecha de inicio. Perdone las molestias.");
      response.sendRedirect("error.jsp");
      return;
    }

    if (fernanbnbAPP.modificaReserva(new Reserva(r.getId(), r.getIdVivienda(),
            r.getIdUsuario(), fechaInicio, numNoches, r.getPrecio(), numOcupantes))) {
      session.setAttribute("exito", "Su reserva se ha modificado correctamente!");
      response.sendRedirect("exito.jsp");
      return;
    } else {
      session.setAttribute("error", "No se ha podido modificar su reserva...");
      response.sendRedirect("error.jsp");
      return;
    }

  } catch (Exception e) {
    session.setAttribute("error", "No se ha podido modificar su reserva...");
    response.sendRedirect("error.jsp");
    return; //
  }
%>