<%@ page import="models.Usuario" %>
<%@ page import="models.Propietario" %>
<%@ page import="controller.FernanbnbAPP" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Mensaje" %>
<%@ page import="models.Vivienda" %>
<%@ page import="componentes.Utils" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Mensajes de Fernanbnb</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" type="image/png"
          href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="javascript/scriptChat2.js"></script>
    <link rel='stylesheet' type='text/css' media='screen' href='css/chat.css'>

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
    boolean notificacion = false;
    if (usuarioLogueado != null) {
        if (usuarioLogueado instanceof Usuario) {
            Usuario usuarioTipo = (Usuario) usuarioLogueado;
            // Ahora puedes llamar a getId() en usuarioTipo
            idUsuario = usuarioTipo.getId();
        } else if (usuarioLogueado instanceof Propietario) {
            Propietario propietarioTipo = (Propietario) usuarioLogueado;
            idUsuario = propietarioTipo.getId();
        }
    } else {
        response.sendRedirect("index.jsp");
        return;
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
                    <%if (notificacion) {%>
                    <!-- Notificación de Material Icons -->
                    <span class="material-icons iconDot z-10 text-red-500 text-base align-middle absolute top-4 right-44">fiber_manual_record</span>
                    <!-- Fin Notificación de Material Icons -->
                    <%}%>


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
                                href="misMensajes.jsp?idUsuario=<%=idUsuario%>">Mensajes
                            <%if (notificacion) {%>
                            <!-- Notificación de Material Icons -->
                            <span class="material-icons iconDot z-10 text-red-500 text-base align-middle absolute top-4 right-44">fiber_manual_record</span>
                            <!-- Fin Notificación de Material Icons -->
                            <%}%>
                        </a>


                        </li>
                        <%
                            if (usuarioLogueado instanceof Propietario || (usuarioLogueado instanceof Usuario)) {%>
                        <!-- Usuario normal logueado -->
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                href="misReservas.jsp?idUsuario=<%=idUsuario%>">Mis
                            reservas

                        </a></li>
                        <% }
                            if (usuarioLogueado instanceof Propietario) {%>
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 font-semibold "><a
                                href="misViviendas.jsp?idUsuario=<%=idUsuario%>">Mis
                            viviendas
                        </a>
                        </li>
                        <% }
                            if (usuarioLogueado instanceof Usuario) {%>
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200 "><a
                                href="loginPropietario.jsp">Pon tu casa en Fernanbnb</a></li>
                        <%
                            }
                            if (usuarioLogueado != null) {
                        %>
                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 "><a
                                href="perfil.jsp?idUsuario=<%=idUsuario%>">Cuenta</a>
                        </li>
                        <%}%>

                        <li class="py-2 px-3 mx-auto hover:bg-gray-100 border-t-2 border-gray-200"><a
                                href="centroAyuda.html">Centro de ayuda</a></li>

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
                        <!-- Fin Formulario de Registro -->
                    </div>
                </div>


            </div>
        </div>
    </div>


    </div>
    <!-- end login -->
</nav>


<!-- Mensajeria de usuarios -->
<div class="container mx-auto rounded-lg mt-4 px-4 py-16">

    <div class="flex flex-col lg:flex-row justify-center items-center">
        <div class="w-full lg:w-4/5">
            <div class="border-b-2 py-4 px-2">
                <h2 class="text-2xl font-semibold text-center">Mensajes</h2>
            </div>
            <%
                String contextPath = request.getServletContext().getRealPath("/");
                FernanbnbAPP fernanbnbAPP;
                ArrayList<Mensaje> chats = new ArrayList<>();
                try {
                    fernanbnbAPP = new FernanbnbAPP(contextPath);
                    chats = fernanbnbAPP.agruparMensajesPorViviendas(idUsuario);
                    if (usuarioLogueado != null) {
                        notificacion = !fernanbnbAPP.mensajesNoLeidosByUser(idUsuario).isEmpty();
                    }

                } catch (Exception e) {
                    session.setAttribute("error", "No se ha enviado su mensaje...");
                    response.sendRedirect("error.jsp");
                    return; //
                }
            %>
            <%if (chats.isEmpty()) {%>
                     <div class="fixed inset-0 flex items-center justify-center">
                <div class="p-4 opacity-20">
                    <h1 class="text-2xl sm:text-3xl md:text-4xl lg:text-5xl xl:text-6xl font-bold text-gray-600">Parece
                        que no tienes ningun chat...</h1>
                </div>
            </div>
            <%
            } else {
            %>


            <div class="flex flex-col items-center">
                <%
                    for (Mensaje chat : chats) {
                        Vivienda v = fernanbnbAPP.buscaViviendaId(chat.getIdVivienda());
                        Propietario p = fernanbnbAPP.buscaPropietariobyVivienda(v);
                        boolean leido = chat.isLeido();
                        boolean mensajeTuyo = chat.getIdEmisor() == idUsuario;
                        String textoCompleto = chat.getTexto();
                        String textoRecortado = textoCompleto.length() > 55 ? textoCompleto.substring(0, 55) + "..." : textoCompleto;
                        String nombre = null;
                        String tituloCasa = v.getTitulo();


                        if (usuarioLogueado instanceof Usuario) {
                            nombre = p.getNombre();
                        } else {
                            if (idUsuario == chat.getIdEmisor())
                                nombre = fernanbnbAPP.buscaUsuariobyId(chat.getIdReceptor()).getNombre();
                            else {
                                nombre = fernanbnbAPP.buscaUsuariobyId(chat.getIdEmisor()).getNombre();
                            }
                        }

                %>

                <!-- El boton del chat del usuarioLogueado -->
                <a href="chat.jsp?idEmisor=<%=chat.getIdEmisor()%>&idReceptor=<%=chat.getIdReceptor()%>&idVivienda=<%=chat.getIdVivienda()%>"
                   class="button-chat flex justify-center items-center p-2 hover:bg-gray-100 w-4/5 mb-2 relative">
                    <img src="https://media.discordapp.net/attachments/1147607064525414410/1149748752081494016/perfil.png?width=342&height=3400"
                         class="object-cover h-12 w-12 rounded-full" alt=""/>
                    <div class="w-full ml-2 text-center">
                        <div class="text-lg font-semibold"><%=nombre%> - <%=tituloCasa%>
                        </div>

                        <span class="text-gray-500 text-sm"><%=textoRecortado%>... <span
                                class="ml-4"><%=Utils.formateaFechaHora(chat.getFechaEnvio())%></span></span>

                    </div>
                    <%
                        if (usuarioLogueado != null) {
                            if (!leido && !mensajeTuyo) {
                    %>

                    <!-- Notificación de Material Icons -->
                    <span
                            class="material-icons iconDot z-10 text-red-500 text-base align-middle absolute right-10">fiber_manual_record</span>
                    <!-- Fin Notificación de Material Icons -->

                    <%
                            }
                        }
                    %>
                </a>
                <!-- Fin boton del chat del usuarioLogueado -->


                <% }
                %>


            </div>
        </div>
    </div>
    <% }
    %>
</div>

<!-- Fin Mensajeria de usuarios -->


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