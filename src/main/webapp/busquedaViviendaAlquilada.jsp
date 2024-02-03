<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Vivienda" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="models.Usuario" %>
<%@ page import="java.time.LocalDate" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Alojamiento vacacionales y apartamentos Fernanbnb</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" type="image/png"
          href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="javascript/scriptindexUsuario.js"></script>
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

    boolean notificacion = false;
    Usuario usuario = null;
    if (usuarioLogueado != null) {
        if (usuarioLogueado instanceof Usuario) {
            usuario = (Usuario) usuarioLogueado;
        } else response.sendRedirect("index.jsp");
    }

    boolean tipoVivienda = request.getParameter("tipoBusqueda").equals("localidad");
    String nombreLugar = request.getParameter("nombre").trim();
    LocalDate fechaVivienda = LocalDate.parse(request.getParameter("fechaEntrada"));
    int numNoches = Integer.parseInt(request.getParameter("numNoches"));
    int numViajeros = Integer.parseInt(request.getParameter("numViajeros"));

    if (fechaVivienda.isBefore(LocalDate.now()) || fechaVivienda.isEqual(LocalDate.now())) {
        session.setAttribute("error", "No se permiten fechas anteriores al dia de hoy.");
        response.sendRedirect("error.jsp");
        return;
    }
    if (nombreLugar == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = null;
    ArrayList<Vivienda> viviendasDisponibles = new ArrayList<>();
    ArrayList<Vivienda> viviendasNoDisponibles = new ArrayList<>();
    ArrayList<String> imagenesViviendas = null;

    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        imagenesViviendas = fernanbnbAPP.getAllImagenesViviendas();
        if (tipoVivienda) {
            viviendasDisponibles = fernanbnbAPP.buscaAlojamiento(nombreLugar, "", fechaVivienda,
                    numNoches, numViajeros, tipoVivienda);
            viviendasNoDisponibles = fernanbnbAPP.buscaAlojamientosSinDisponibilidad(nombreLugar, "", fechaVivienda,
                    numNoches, numViajeros, tipoVivienda);
        } else {
            viviendasDisponibles = fernanbnbAPP.buscaAlojamiento("", nombreLugar, fechaVivienda,
                    numNoches, numViajeros, tipoVivienda);
            viviendasNoDisponibles = fernanbnbAPP.buscaAlojamientosSinDisponibilidad("", nombreLugar, fechaVivienda,
                    numNoches, numViajeros, tipoVivienda);
        }
        Collections.shuffle(imagenesViviendas);

        if (usuarioLogueado != null) {
            notificacion = !fernanbnbAPP.mensajesNoLeidosByUser(usuario.getId()).isEmpty();
        }

        // Si fui capaz de cargar los datos exitosamente, continúa con el procesamiento

        if (fernanbnbAPP == null || imagenesViviendas == null) {
            throw new Exception("Datos no cargados"); // Lanza una excepción si los datos no se cargaron correctamente
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

    <!-- search bar -->
    <div class="flex-shrink flex-grow-0 justify-start px-27">
        <div class="inline-block">
            <div class="inline-flex items-center max-w-full ">
                <button id="abrirModal"
                        class="flex items-center flex-grow-0 flex-shrink pl-2 relative md:w-60 sm:mr-4 border border-gray-300 rounded-full px-1 py-1 mi-elemento">
                    <style>
                        @media (min-width: 1024px) {
                            .mi-elemento {
                                width: 35rem;
                            }
                        }
                    </style>

                    <div class="block flex-grow flex-shrink overflow-hidden ">Cualquier Lugar</div>
                    <div class="flex items-center justify-center relative h-10 w-10 rounded-full"
                         style="background-color: #FF5A5F; border-radius: 100%;">

                        <svg viewBox="0 0 32 32" xmlns="http://www.w3.org/2000/svg" aria-hidden="true"
                             role="presentation" focusable="false" style="
                                    display: block;
                                    fill: #FF5A5F;
                                    height: 16px;
                                    width: 16px;
                                    stroke: #ffffff;
                                    stroke-width: 5.33333;
                                    overflow: visible;

                                ">
                            <g fill="none">
                                <path
                                        d="m13 24c6.0751322 0 11-4.9248678 11-11 0-6.07513225-4.9248678-11-11-11-6.07513225 0-11 4.92486775-11 11 0 6.0751322 4.92486775 11 11 11zm8-3 9 9">
                                </path>
                            </g>
                        </svg>
                    </div>
                </button>
                <!-- Modal (oculto por defecto) -->
                <div id="miModal" class="modal-background fixed inset-0 bg-black opacity-50 z-20 hidden"></div>
                <div id="miModal2" class="fixed inset-12 flex items-center justify-center z-20 hidden">

                    <div class="modal-content bg-white rounded-lg p-4 sm:p-6  md:p-8 lg:p-10 xl:p-20 z-11 relative">
                        <button id="cerrarModal"
                                class="absolute top-0 right-0 transform translate-x-0 mt-2 sm:mt-4 mr-2 sm:mr-4 text-black font-bold hover:bg-gray-100 px-3 sm:px-4 py-2 rounded-full">
                            X
                        </button>

                        <h2 class="text-xl sm:text-2xl font-semibold mb-2 sm:mb-4">Búsqueda de Alojamiento</h2>
                        <form action="busquedaViviendaAlquilada.jsp" onsubmit="return validacionFormularioBusqueda()">
                            <div class="mb-2 sm:mb-4">
                                <label for="tipoBusqueda"
                                       class="block text-gray-700 font-semibold mb-1 sm:mb-2">Tipo de
                                    Búsqueda</label>
                                <select id="tipoBusqueda" name="tipoBusqueda"
                                        class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                                        required aria-required="true">
                                    <option value="" disabled selected>Seleccione una opción</option>
                                    <option value="localidad">Por Localidad</option>
                                    <option value="provincia">Por Provincia</option>
                                </select>
                            </div>
                            <div class="mb-2 sm:mb-4">
                                <label for="nombre" class="block text-gray-700 font-semibold mb-2">Nombre de
                                    Localidad o Provincia</label>
                                <input type="text" id="nombre" name="nombre"
                                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                                       required aria-required="true">
                            </div>
                            <div class="mb-2 sm:mb-4">
                                <label for="fechaEntrada" class="block text-gray-700 font-semibold mb-2">Fecha
                                    de Entrada</label>
                                <input type="date" id="fechaEntrada" name="fechaEntrada"
                                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                                       required aria-required="true">
                            </div>
                            <div class="mb-2 sm:mb-4">
                                <label for="numNoches" class="block text-gray-700 font-semibold mb-2">Número de
                                    Noches</label>
                                <input type="number" id="numNoches" name="numNoches"
                                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                                       required aria-required="true" min="1">
                            </div>
                            <div class="mb-2 sm:mb-4">
                                <label for="numViajeros" class="block text-gray-700 font-semibold mb-2">Número
                                    de Viajeros</label>
                                <input type="number" id="numViajeros" name="numViajeros"
                                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                                       required aria-required="true" min="1">
                            </div>
                            <button type="submit"
                                    class=" bg-red-500 hover:bg-red-600 text-white px-3 sm:px-4 py-2 rounded-md  transition duration-300">
                                Buscar
                                Alojamiento
                            </button>
                        </form>

                    </div>

                </div>


            </div>
        </div>
    </div>
    <!-- end search bar -->

    <!-- login -->
    <div class="flex-initial">

        <div class="flex justify-end items-center relative">
            <div class="flex mr-4 items-center">
                <a class="hidden sm:block inline-block py-2 px-3 hover:bg-gray-200 rounded-full"
                   href="loginPropietario.jsp">
                    <div
                            class=" hidden sm:block flex items-center relative cursor-pointer whitespace-nowrap text-sm">
                        <b>Pon tu
                            casa en
                            Fernanbnb </b>
                    </div>
                </a>
            </div>

            <div class="block xl:mr-16  ">
                <div class="inline relative">

                    <button type="button"
                            class="inline-flex items-center relative px-2 border rounded-full hover:shadow-lg"
                            id="profileButton"><%
                        if (usuarioLogueado != null) {
                            if (notificacion) {
                    %>

                        <!-- Notificación de Material Icons -->
                        <span
                                class="material-icons iconDot z-10 text-red-500 text-base align-middle absolute top-0 right-0  mr-1">fiber_manual_record</span>
                        <!-- Fin Notificación de Material Icons -->

                        <%
                                }
                            }
                        %>
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
                            <%if (usuario == null) {%>
                            <!-- Si el usuario no está logueado -->
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold">
                                <button
                                        id="abrirInicioSesion">Iniciar sesión
                                </button>
                            </li>
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 ">
                                <button
                                        id="abrirRegistro">Registrarse
                                </button>
                            </li>
                            <%} else {%>
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                    href="misMensajes.jsp?idUsuario=<%=usuario.getId()%>">Mensajes
                                <%
                                    if (usuarioLogueado != null) {
                                        if (notificacion) {
                                %>

                                <!-- Notificación de Material Icons -->
                                <span
                                        class="material-icons iconDot z-10 text-red-500 text-base align-middle absolute top-0 right-0  mr-1">fiber_manual_record</span>
                                <!-- Fin Notificación de Material Icons -->

                                <%
                                        }
                                    }
                                %>
                            </a>


                            </li>
                            <!-- Usuario normal logueado -->
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                    href="misReservas.jsp?idUsuario=<%=usuario.getId()%>">Mis
                                reservas

                            </a></li>
                            <% } %>
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200"><a
                                    href="loginPropietario.jsp">Pon tu casa en Fernanbnb</a></li>
                            <%
                                if (usuario != null) {
                            %>
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a
                                    href="perfil.jsp?idUsuario=<%=usuario.getId()%>">Cuenta</a>
                            </li>
                            <%}%>

                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200"><a
                                    href="centroAyuda.html">Centro de ayuda</a></li>
                            <%if (usuario != null) {%>
                            <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a href="logout.jsp">Cerrar sesión</a></li>
                            <%}%>
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
                            <!-- Fin Formulario de Registro -->
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
    if (viviendasDisponibles == null) viviendasDisponibles = new ArrayList<>();
    if (viviendasNoDisponibles == null) viviendasNoDisponibles = new ArrayList<>();
    if (viviendasDisponibles.isEmpty() && viviendasNoDisponibles.isEmpty()) {
%>
<div class="fixed inset-0 flex items-center justify-center">
    <div class="p-4 opacity-20">
        <h1 class="text-2xl sm:text-3xl md:text-4xl lg:text-5xl xl:text-6xl font-bold text-gray-600">Parece que no hay
            nada por aquí...</h1>
    </div>
</div>
<%
} else { // Si hay viviendasDisponibles
%>


<div class="container mx-auto mt-32 px-4">
    <% if (!viviendasDisponibles.isEmpty()) {%>
    <div class="text-center mb-6">
        <h1 class="text-3xl font-semibold">Viviendas Disponibles</h1>
    </div>
    <%} %>
    <div class="px- grid xl:grid-cols-4  md:grid-cols-3 sm:grid-cols-2 gap-6 mb-16">
        <%
            if (!viviendasDisponibles.isEmpty()) {
                int posicionVivienda = 0;
                String imagen = "https://cdn.discordapp.com/attachments/1151279463661850765/1151571140909727824/h3e3w.png";
                for (Vivienda v : viviendasDisponibles) {
                    if (!imagenesViviendas.isEmpty()) {
                        imagen = imagenesViviendas.get(posicionVivienda);
                        posicionVivienda++;

        %>
        <!-- Tarjeta Vivienda a repetir -->


        <a href="vistaViviendaUsuario.jsp?idVivienda=<%= v.getId() %>"
           class="bg-white rounded-lg shadow-lg overflow-hidden transition transform hover:scale-105">
            <img src="<%=imagen%>"
                 alt="Vivienda" class="w-auto lg:h-72 md:h-52 rounded-lg object-cover">
            <div class="p-4">
                <h2 class="text-2xl font-semibold text-gray-800"><%=v.getTitulo() %>
                </h2>
                <p class="text-black font-semibold"><%=v.getLocalidad() %>, <%=v.getProvincia() %>
                </p>
                <p class="text-gray-700">Huéspedes <%=v.getMaxOcupantes() %>
                </p>
                <p class="text-black text-lg mt-4"><b><%=v.getPrecioNoche() %> €</b> noche</p>
            </div>
        </a>
        <%
                    }
                }
            }
        %>

    </div>
    <% if (!viviendasNoDisponibles.isEmpty()) {%>
    <div class="text-center mb-6">
        <h1 class="text-3xl font-semibold">Viviendas No Disponibles</h1>
    </div>
    <%} %>
    <div class="px- grid xl:grid-cols-4  md:grid-cols-3 sm:grid-cols-2 gap-6 mb-16">
        <%
            if (!viviendasNoDisponibles.isEmpty()) {
                int posicionVivienda = 0;
                String imagen = "https://cdn.discordapp.com/attachments/1151279463661850765/1151571140909727824/h3e3w.png";
                for (Vivienda v : viviendasNoDisponibles) {
                    if (!imagenesViviendas.isEmpty()) {
                        imagen = imagenesViviendas.get(posicionVivienda);
                        posicionVivienda++;

        %>
        <!-- Tarjeta Vivienda a repetir -->


        <a href="vistaViviendaUsuario.jsp?idVivienda=<%= v.getId() %>"
           class="bg-white rounded-lg shadow-lg overflow-hidden transition transform hover:scale-105">
            <img src="<%=imagen%>"
                 alt="Vivienda" class="w-auto lg:h-72 md:h-52 rounded-lg object-cover">
            <div class="p-4">
                <h2 class="text-2xl font-semibold text-gray-800"><%=v.getTitulo() %>
                </h2>
                <p class="text-black font-semibold"><%=v.getLocalidad() %>, <%=v.getProvincia() %>
                </p>
                <p class="text-gray-700">Huéspedes <%=v.getMaxOcupantes() %>
                </p>
                <p class="text-black text-lg mt-4"><b><%=v.getPrecioNoche() %> €</b> noche</p>
            </div>
        </a>
        <%
                    }
                }
            }
        %>

    </div>

</div>
<%
    }
%>

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
