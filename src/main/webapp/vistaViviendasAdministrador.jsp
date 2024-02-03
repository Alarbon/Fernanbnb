<%@ page import="controller.FernanbnbAPP" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="models.*" %>
<%@ page import="dao.*" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Todas las viviendas</title>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <link rel="icon" type="image/png"
        href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
  <script src="https://cdn.tailwindcss.com"></script>
  <script src="javascript/scriptindexPropietario.js"></script>
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
  <style>
    @font-face {
      font-family: 'AirbnbCereal';
      src: url('AirbnbCereal_W_Lt.otf') format('opentype');
    }

    body {
      font-family: 'AirbnbCereal', Arial, Helvetica, sans-serif;
    }
  </style>

</head>

<body>
<%
  Object usuarioLogueado = session.getAttribute("usuarioLogueado");
  int idUsuario = 0;
  Administrador usuario = null;
  boolean notificacion = false;
  if (usuarioLogueado != null) {
    if (usuarioLogueado instanceof Propietario || usuarioLogueado instanceof Usuario) {
      response.sendRedirect("index.jsp");
      return;
    } else {
      usuario = (Administrador) usuarioLogueado;
      idUsuario = ((Administrador) usuarioLogueado).getId();
    }
  }


  String contextPath = request.getServletContext().getRealPath("/");
  FernanbnbAPP fernanbnbAPP = null;


  try {
    fernanbnbAPP = new FernanbnbAPP(contextPath);
    if (usuarioLogueado != null) {
      notificacion = !fernanbnbAPP.mensajesNoLeidosByUser(idUsuario).isEmpty();
    }


  } catch (Exception e) {
    session.setAttribute("error", "Estamos en mantenimiento con nuestros servidores, vuelva en unas horas...");
    response.sendRedirect("error.jsp");
    return; //
  }
%>
<nav
        class="bg-white w-full flex fixed top-0 justify-between items-center mx-auto px-8 h-20 border-b-2 border-gray-200 z-50">
  <!-- logo -->
  <div class="inline-flex">
    <a class="_o6689fn" href="index.jsp">
      <div class="hidden md:block lg:ml-12">
        <img src="https://cdn.discordapp.com/attachments/1147607064525414410/1147613138947153990/fernanbnb.png"
             width="152" height="42" style="display: block">
      </div>
      <div class="block md:hidden">
        <img src="https://media.discordapp.net/attachments/1147607064525414410/1147607094774743112/logo.png?width=508&height=506"
             width="30" height="32" fill="currentcolor" style="display: block">

      </div>
    </a>
  </div>

  <!-- end logo -->

  <!-- login -->
  <div class="flex-initial">

    <div class="block xl:mr-16  ">
      <div class="inline relative">

        <button type="button"
                class="inline-flex items-center relative px-2 border rounded-full hover:shadow-lg"
                id="profileButton">


          <div class="pl-1">
            <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true"
                 role="presentation" focusable="false"
                 style="display: block; fill: none; height: 16px; width: 16px; stroke: currentcolor; stroke-width: 3; overflow: visible;">

              <path d="m2 16h28"></path>
              <path d="m2 24h28"></path>
              <path d="m2 8h28"></path>
              </g>
            </svg>
          </div>

          <div class="block flex-grow-0 flex-shrink-0 h-10 w-12 pl-5">
            <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true"
                 role="presentation" focusable="false"
                 style="display: block; height: 100%; width: 100%; fill: currentcolor;">
              <path
                      d="m16 .7c-8.437 0-15.3 6.863-15.3 15.3s6.863 15.3 15.3 15.3 15.3-6.863 15.3-15.3-6.863-15.3-15.3-15.3zm0 28c-4.021 0-7.605-1.884-9.933-4.81a12.425 12.425 0 0 1 6.451-4.4 6.507 6.507 0 0 1 -3.018-5.49c0-3.584 2.916-6.5 6.5-6.5s6.5 2.916 6.5 6.5a6.513 6.513 0 0 1 -3.019 5.491 12.42 12.42 0 0 1 6.452 4.4c-2.328 2.925-5.912 4.809-9.933 4.809z">
              </path>
            </svg>

          </div>
        </button>
        <div id="profileMenu"
             class="mt-2 absolute right-0 bg-white border border-gray-300 rounded-lg shadow-lg w-72 hidden">
          <ul class="mt-2">


            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                    href="vistaViviendasAdministrador.jsp">Ver todas las Viviendas
            </a>


            </li>

            <!-- Usuario normal logueado -->
            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                    href="vistaReservaAdministrador.jsp">Ver todas las Reservas

            </a></li>

            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                    href="vistaUsuariosbyAdmin.jsp">Ver todos los Usuarios
            </a>
            </li>


            <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a
                    href="perfil.jsp?idUsuario=<%=idUsuario%>">Cuenta</a>
            </li>


            <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200"><a
                    href="verConfiguracionPrograma.jsp">Configuración del programa</a></li>

            <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a href="logout.jsp">Cerrar sesión</a></li>

          </ul>
        </div>

        <!-- Modal de Inicio de Sesión (oculto por defecto) -->
        <div id="modalInicioSesion"
             class="modal-background fixed inset-0 bg-black opacity-50 z-10 hidden  "></div>
        <div id="modalInicioSesion2"
             class="fixed inset-12 flex items-center justify-center z-10 hidden">
          <div
                  class="modal-content bg-white rounded-lg p-4 sm:p-6 md:p-8 lg:p-10 xl:p-20 z-11 relative">
            <button id="cerrarInicioSesion"
                    class="absolute top-0 right-0 transform translate-x-0 mt-2 sm:mt-4 mr-2 sm:mr-4 text-black font-bold hover:bg-gray-100 px-3 sm:px-4 py-2 rounded-full">
              X
            </button>
            <h2 class="text-xl sm:text-2xl font-semibold mb-2 sm:mb-4">Inicio de Sesión</h2>
            <!-- Formulario de Inicio de Sesión -->
            <form action="validacionInicioSesion.jsp">
              <div class="mb-2 sm:mb-4">
                <label for="usuario"
                       class="block text-gray-700 font-semibold mb-2">Usuario</label>
                <input type="text" id="usuario" name="usuario" placeholder="Escriba su correo"
                       class=" w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none
                                    focus:ring focus:border-blue-300" required aria-required="true">
              </div>
              <div class="mb-2 sm:mb-4">
                <label for="contrasena"
                       class="block text-gray-700 font-semibold mb-2">Contraseña</label>
                <input type="password" id="contrasena" name="contrasena"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required aria-required="true">
              </div>
              <button type="submit"
                      class="bg-gray-900 hover:bg-gray-800 text-white px-3 sm:px-4 py-2 rounded-md transition duration-300">
                Iniciar
                Sesión
              </button>
            </form>

          </div>
        </div>

        <!-- Modal de Registro (oculto por defecto) -->
        <div id="modalRegistro" class="modal-background fixed inset-0 bg-black opacity-50 z-10 hidden">
        </div>

        <div id="modalRegistro2" class="fixed inset-12 flex items-center justify-center z-10 hidden">
          <div
                  class="modal-content bg-white rounded-lg p-4 sm:p-6 md:p-8 lg:p-10 xl:p-20 z-11 relative">
            <button id="cerrarRegistro"
                    class="absolute top-0 right-0 transform translate-x-0 mt-2 sm:mt-4 mr-2 sm:mr-4 text-black font-bold hover:bg-gray-100 px-3 sm:px-4 py-2 rounded-full">
              X
            </button>
            <h2 class="text-xl sm:text-2xl font-semibold mb-2 sm:mb-4">Registrarse</h2>

            <!-- Formulario de Registro -->
            <form id="registroForm" action="validacionRegistro.jsp" method="post">
              <div class="mb-2 sm:mb-4">
                <label for="nombreUsuario" class="block text-gray-700 font-semibold mb-2">Nombre
                  completo</label>
                <input type="text" id="nombreUsuario" name="nombreUsuario"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required>
              </div>
              <div class="mb-2 sm:mb-4">
                <label for="correoUsuario" class="block text-gray-700 font-semibold mb-2">Correo
                  electrónico</label>
                <input type="email" id="correoUsuario" name="correoUsuario"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required>
              </div>
              <div class="mb-2 sm:mb-4">
                <label for="contrasenaRegistro"
                       class="block text-gray-700 font-semibold mb-2">Contraseña</label>
                <input type="password" id="contrasenaRegistro" name="contrasenaRegistro"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required>
              </div>
              <div class="mb-2 sm:mb-4">
                <label for="confirmarContrasena"
                       class="block text-gray-700 font-semibold mb-2">Confirmar Contraseña</label>
                <input type="password" id="confirmarContrasena" name="confirmarContrasena"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required>
              </div>
              <div id="mensajeError" class="text-red-600 mb-2 sm:mb-4"></div>
              <button type="submit"
                      class="bg-gray-900 hover:bg-gray-800 text-white px-3 sm:px-4 py-2 rounded-md transition duration-300">
                Registrarse
              </button>
            </form>

          </div>
        </div>


      </div>
    </div>
  </div>


  </div>
  <!-- end login -->
</nav>


<!-- contenido principal -->

<%

  ArrayList<Vivienda> viviendasALL;
  int fila = 0;

  try {
    viviendasALL = fernanbnbAPP.getAllViviendas();

  } catch (Exception e) {
    viviendasALL = new ArrayList<>();

  }

%>
<section class="mt-24 ">
  <div class="text-center mb-6">
    <h1 class="text-3xl font-semibold">Datos de Todas las Viviendas</h1>
  </div>
  <div class="w-full overflow-y-auto">
    <table class="w-full border-collapse table-auto">
      <thead class="bg-gradient-to-tr from-red-500 to-red-800  text-white">
      <tr>
        <th class="py-2 px-4">ID Vivienda</th>
        <th class="py-2 px-4">Título</th>
        <th class="py-2 px-4">Descripción</th>
        <th class="py-2 px-4">Localidad</th>
        <th class="py-2 px-4">Provincia</th>
        <th class="py-2 px-4">Max Ocupantes</th>
        <th class="py-2 px-4">Precio por Noche</th>
        <th class="py-2 px-4">Reservas Activas</th>
      </tr>
      </thead>
      <tbody class="text-gray-700">
      <%
        if (!viviendasALL.isEmpty()) {
          DAOManager dao = DAOManager.getSinglentonInstance();

          DAOReservaSQL daoReservaSQL = new DAOReservaSQL();
          for (Vivienda v : viviendasALL) {
            int reservasActivas = 0;
           v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
            for (Reserva r :v.getReservas() ) {//Buscamos las reservas pendientes y la añadimos
              if (r.getFechaInicio().plusDays(r.getNoches()).compareTo(LocalDate.now()) > 0)
                reservasActivas++;
            }
            if (fila % 2 == 0 || fila == 0) {%>
      <tr class="bg-gray-100">
        <td class="py-2 px-4 text-center"><%=v.getId()%></td>
        <td class="py-2 px-4 text-center"><%=v.getTitulo()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getDescripcion()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getLocalidad()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getProvincia()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getMaxOcupantes()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getPrecioNoche()%>€</td>
        <td class="py-2 px-4 text-center"><%=reservasActivas%>
        </td>
      </tr>
      <%} else { %>
      <tr class="bg-gray-200">
        <td class="py-2 px-4 text-center"><%=v.getId()%></td>
        <td class="py-2 px-4 text-center"><%=v.getTitulo()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getDescripcion()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getLocalidad()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getProvincia()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getMaxOcupantes()%>
        </td>
        <td class="py-2 px-4 text-center"><%=v.getPrecioNoche()%>€</td>
        <td class="py-2 px-4 text-center"><%=reservasActivas%>
      </tr>
      <%
            }
            fila++;
          }
             }
      %>
      </tbody>
    </table>
  </div>
</section>

<!-- Fin del contenido principal -->



<footer
        class="bg-white w-full flex fixed bottom-0 justify-center items-center mx-auto px-8 h-auto sm:h-12 border-t-2 border-gray-200">
  <div class="text-center sm:flex sm:justify-center sm:items-center">
    <p class="mr-2">© 2023 Fernanbnb</p>
    <ul class="hidden sm:flex space-x-4 ml-4">
      <li class=" flex items-center">·</li>
      <li class="flex items-center hover:rounded-md hover:bg-gray-100 px-2 py-1">
        <img src="https://cdn.discordapp.com/attachments/1147607064525414410/1149359532666011759/image.png"
             width="20px" class="mr-2" alt="">
        <a href="https://github.com/Alarbon">Alarbon</a>
      </li>
      <li class=" flex items-center">·</li>
      <li class=" flex items-center hover:rounded-md hover:bg-gray-100 px-2 py-1 "><a
              href="privacidad.html">Privacidad</a>
      </li>
      <li class=" flex items-center">·</li>
      <li class="flex items-center hover:rounded-md hover:bg-gray-100 px-2 py-1"><a
              href="condiciones.html">Condiciones</a>
      </li>
      <li class="flex items-center">·</li>
      <li class="flex items-center hover:rounded-md hover:bg-gray-100 px-2 py-1"><a
              href="datosFernabnb.html">Datos de la
        empresa</a></li>
    </ul>
  </div>
</footer>


</body>

</html>
