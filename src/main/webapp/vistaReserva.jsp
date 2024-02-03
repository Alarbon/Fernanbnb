<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="models.Usuario" %>
<%@ page import="models.Reserva" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="models.Propietario" %>
<%@ page import="models.Vivienda" %>
<%@ page import="static componentes.Utils.formateaFecha" %>
<%@ page import="jdk.jshell.execution.Util" %>
<%@ page import="componentes.Utils" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Resumen reserva</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" type="image/png"
          href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="javascript/scriptReservas.js"></script>
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
    Object usuario = session.getAttribute("usuarioLogueado");

    String idUsuario = null;
    boolean notificacion = false;
    if (usuario != null) {
        if (usuario instanceof Usuario) {
            idUsuario = String.valueOf(((Usuario) usuario).getId());

        } else if (usuario instanceof Propietario) {
            idUsuario = String.valueOf(((Propietario) usuario).getId());
        } else response.sendRedirect("index.jsp");
    } else response.sendRedirect("index.jsp");


    String contextPath = request.getServletContext().getRealPath("/");
    FernanbnbAPP fernanbnbAPP = null;

    boolean reservaFinalizada = false;

    if (Boolean.parseBoolean(request.getParameter("reservaFinalizada"))) reservaFinalizada = true;

    try {
        fernanbnbAPP = new FernanbnbAPP(contextPath);
        usuario = fernanbnbAPP.actualizaPerfil(usuario);
        if (usuario != null) {
            notificacion = !fernanbnbAPP.mensajesNoLeidosByUser(Integer.parseInt(idUsuario)).isEmpty();
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
                        id="profileButton"><%
                    if (usuario != null) {
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
                                href="misMensajes.jsp?idUsuario=<%=idUsuario%>">Mensajes
                        </a>

                        </li>


                        <!-- Usuario normal logueado -->
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                href="misReservas.jsp?idUsuario=<%=idUsuario%>">Mis
                            reservas
                        </a></li>
                        <% }
                            if (usuario instanceof Propietario) {%>
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                href="misViviendas.jsp?idUsuario=<%=idUsuario%>">Mis
                            viviendas
                        </a>
                        </li>
                        <% }
                            if (usuario instanceof Usuario) {%>

                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200"><a
                                href="loginPropietario.jsp">Pon tu casa en Fernanbnb</a></li>
                        <%
                            }
                            if (usuario != null) {
                        %>
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a
                                href="perfil.jsp?idUsuario=<%=idUsuario%>">Cuenta</a>
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

<%
    Reserva r = fernanbnbAPP.buscaReservabyId(Integer.parseInt(request.getParameter("idReserva")));
    Vivienda v = fernanbnbAPP.buscaViviendaId(r.getIdVivienda());
    Usuario u = fernanbnbAPP.buscaUsuariobyId(r.getIdUsuario());
    ArrayList<String> imagenesViviendas = fernanbnbAPP.getAllImagenesViviendas();
    Collections.shuffle(imagenesViviendas);


%>
<!-- Contenido de la vivienda -->

<section class="container mx-auto my-8 p-4 mt-14 mb-14">
    <div class="flex flex-wrap -mx-4">
        <!-- Título de la Reserva -->
        <div class="w-full px-4">
            <h1 class="text-2xl font-semibold">Reserva en <%=v.getTitulo() %>
            </h1>
        </div>

        <!-- Datos de la Reserva -->


        <!-- Imagen principal de la Vivienda -->
        <div class="w-full md:w-1/2 px-4 mt-4">
            <img src="<%=imagenesViviendas.get(0) %>"
                 alt="Imagen Principal de la Vivienda" class="w-full">
        </div>

        <!-- Miniaturas de las Imágenes -->
        <div class="w-full md:w-1/2 px-4 mt-4 mb-6">

            <div class="grid grid-cols-2 gap-2">
                <!-- Miniatura 1 -->
                <div class="relative group">
                    <img src="<%=imagenesViviendas.get(1) %>"
                         alt="Miniatura 1" class="w-full">
                    <div class="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity flex justify-center items-center">
                        <!-- Puedes agregar contenido adicional aquí si lo deseas -->
                    </div>
                </div>
                <!-- Miniatura 2 -->
                <div class="relative group">
                    <img src="<%=imagenesViviendas.get(2) %>"
                         alt="Miniatura 2" class="w-full">
                    <div class="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity flex justify-center items-center">
                        <!-- Puedes agregar contenido adicional aquí si lo deseas -->
                    </div>
                </div>
                <!-- Miniatura 3 -->
                <div class="relative group">
                    <img src="<%=imagenesViviendas.get(3) %>"
                         alt="Miniatura 3" class="w-full">
                    <div class="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity flex justify-center items-center">
                        <!-- Puedes agregar contenido adicional aquí si lo deseas -->
                    </div>
                </div>
                <!-- Miniatura 4 -->
                <div class="relative group">
                    <img src="<%=imagenesViviendas.get(4) %>"
                         alt="Miniatura 4" class="w-full">
                    <div class="absolute inset-0 bg-black bg-opacity-50 opacity-0 group-hover:opacity-100 transition-opacity flex justify-center items-center">
                        <!-- Puedes agregar contenido adicional aquí si lo deseas -->
                    </div>
                </div>
            </div>

        </div>
        <div class="w-full md:w-1/2 px-4 mt-4">
            <div class="bg-gray-100 p-4 rounded-lg">
                <h2 class="text-xl font-semibold mb-2">Información de la Reserva</h2>
                <p class="text-gray-500">Fecha de Reserva: <%=Utils.formateaFecha(r.getFechaInicio()) %>
                </p>
                <p class="text-gray-500">Número de Noches: <%=r.getNoches() %>
                </p>
                <p class="text-gray-500">Número de Viajeros: <%=r.getOcupantes() %>
                </p>
                <p class="text-gray-500">Precio Total: <%=r.getPrecio() %>€</p>
            </div>
        </div>
        <% if (usuario instanceof Propietario) {
            if (r.getIdUsuario() != -1) {%>
        <!-- Datos del Usuario -->
        <div class="w-full md:w-1/2 px-4 mt-4">
            <div class="bg-gray-100 p-4 rounded-lg">
                <h2 class="text-xl font-semibold mb-2">Información del Usuario de la reserva</h2>
                <p class="text-gray-500">Nombre del Usuario: <%=u.getNombre() %>
                </p>
                <p class="text-gray-500">Correo Electrónico: <%=u.getEmail() %>
                </p>
            </div>
        </div>
        <%} else {%>
        <div class="w-full md:w-1/2 px-4 mt-4">
            <div class="bg-gray-100 p-4 rounded-lg">
                <h2 class="text-xl font-semibold mb-2">Esta reserva es un Periodo de no disponibilidad</h2>
                <p class="text-gray-500">Tu nombre del Usuario: <%=((Propietario)usuario).getNombre() %>
                </p>
                <p class="text-gray-500">Tu correo Electrónico: <%=((Propietario)usuario).getEmail() %>
                </p>
            </div>
        </div>
        <% }
        } else {
            if (!reservaFinalizada) {%>
        <div class="w-full md:w-1/2 px-4 mt-4">
            <div class="w-full md:w-2/3 px-4 mt-4 flex justify-center items-center space-x-4 ml-20">
                <button id="editarReservaButton"
                        class="bg-red-500 hover:bg-red-600 text-white px-6 py-3 rounded-md text-xl font-semibold transition duration-300">
                    Editar
                    Reserva
                </button>
                <button id="eliminarReservaButton"
                        class="bg-gray-900 hover:bg-gray-800 text-white px-6 py-3 rounded-md text-xl font-semibold transition duration-300">
                    Eliminar
                    Reserva
                </button>
            </div>
        </div>
        <% }
        }%>
        <!-- Datos de la Vivienda -->
        <div class="w-full px-4 mt-4 ">
            <div class="bg-gray-100 p-4 rounded-lg">
                <h2 class="text-xl font-semibold mb-2">Información de la Vivienda</h2>
                <p class="text-gray-500">Título de la Vivienda: <%=v.getTitulo() %>
                </p>
                <p class="text-gray-500">Descripción: <%=v.getDescripcion() %>
                </p>
                <p class="text-gray-500">Precio noche: <%=v.getPrecioNoche() %>€</p>
                <p class="text-gray-500">Ubicación: (<%=v.getLocalidad() %>)<%=v.getProvincia() %>
                </p>
            </div>
        </div>

    </div>
</section>

<div id="modalEditarReserva" class="fixed inset-0 flex items-center justify-center z-50 hidden">
    <!-- Fondo oscuro que cubre la pantalla -->
    <div id="cerrarEditarReserva" class="fixed inset-0 bg-black opacity-60"></div>

    <div class="modal-content bg-white rounded-lg p-4 sm:p-6 md:p-8 lg:p-10 xl:p-20 z-11 relative">
        <button id="cerrarEditarReserva2"
                class="absolute top-0 right-0 transform translate-x-0 mt-2 sm:mt-4 mr-2 sm:mr-4 text-black font-bold hover:bg-gray-100 px-3 sm:px-4 py-2 rounded-full">
            X
        </button>
        <h2 class="text-xl sm:text-2xl font-semibold mb-2 sm:mb-4">Editar Reserva</h2>
        <p class="text-gray-400 text-sm sm:text-md mb-2 sm:mb-4">Cambie los datos de la Reserva que desea modificar,
            sino deje los valores por defecto.</p>


        <!-- Formulario de Edición de Reserva -->
        <form id="formularioEditarReserva" action="modificarReserva.jsp" method="get">
            <!-- Campos de edición, por ejemplo: -->
            <input type="hidden" id="idReserva" name="idReserva" value="<%=r.getId()%>">

            <div class="mb-2 sm:mb-4">
                <label for="nuevaFechaReserva" class="block text-gray-700 font-semibold mb-2">Nueva Fecha de
                    Reserva</label>
                <input type="date" id="nuevaFechaReserva" name="nuevaFechaReserva" value="<%=r.getFechaInicio()%>"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required aria-required="true">
            </div>
            <div class="mb-2 sm:mb-4">
                <label for="nuevoNumeroNoches" class="block text-gray-700 font-semibold mb-2">Nuevo Número de
                    Noches</label>
                <input type="number" id="nuevoNumeroNoches" name="nuevoNumeroNoches" value="<%=r.getNoches()%>"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required aria-required="true" min="1">
            </div>
            <div class="mb-2 sm:mb-4">
                <label for="nuevoNumeroViajeros" class="block text-gray-700 font-semibold mb-2">Nuevo Número de
                    Viajeros</label>
                <input type="number" id="nuevoNumeroViajeros" name="nuevoNumeroViajeros" value="<%=r.getOcupantes()%>"
                       class="w-full px-3 sm:px-4 py-2 border rounded-md focus:outline-none focus:ring focus:border-blue-300"
                       required aria-required="true" min="1">
            </div>

            <button type="submit"
                    class="bg-red-500 hover:bg-red-600 text-white px-3 sm:px-4 py-2 rounded-md transition duration-300">
                Guardar
                Cambios
            </button>
        </form>
    </div>
</div>

<script>
    // Obtén una referencia al botón de editar reserva
    const editarReservaButton = document.getElementById('editarReservaButton');

    // Obtén una referencia al modal y al botón de cierre
    const modalEditarReserva = document.getElementById('modalEditarReserva');
    const cerrarEditarReserva = document.getElementById('cerrarEditarReserva');
    const cerrarEditarReserva2 = document.getElementById('cerrarEditarReserva2');

    // Agrega un manejador de evento clic al botón
    editarReservaButton.addEventListener('click', () => {
        // Abre el modal
        modalEditarReserva.classList.remove('hidden');
    });

    // Agrega un manejador de evento clic al botón de cierre ("X")
    cerrarEditarReserva.addEventListener('click', () => {
        // Cierra el modal
        modalEditarReserva.classList.add('hidden');
    });
    // Agrega un manejador de evento clic fuera en el fondo oscuro
    cerrarEditarReserva2.addEventListener('click', () => {
        // Cierra el modal
        modalEditarReserva.classList.add('hidden');
    });

    // Agrega un manejador de evento clic al fondo oscuro
    modalEditarReserva.addEventListener('click', (e) => {
        if (e.target === modalEditarReserva) {
            // Cierra el modal solo si se hizo clic en el fondo oscuro
            modalEditarReserva.classList.add('hidden');
        }
    });

    // Evita que los clics dentro del modal se propaguen al fondo oscuro
    modalEditarReserva.querySelector('.modal-content').addEventListener('click', (e) => {
        e.stopPropagation();
    });

    const eliminarReservaButton = document.getElementById('eliminarReservaButton');

    eliminarReservaButton.addEventListener('click', () => {
        // Mostrar una alerta de confirmación
        const confirmarEliminacion = confirm('¿Estás seguro de que deseas eliminar la reserva? ');

        // Si el usuario confirma la eliminación
        if (confirmarEliminacion) {

            const idReserva = <%=r.getId()%>;

            // Redirigir a la página JSP con el ID de la reserva como parámetro
            window.location.href = "eliminarReserva.jsp?idReserva=" + idReserva;
        }
    });


</script>


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