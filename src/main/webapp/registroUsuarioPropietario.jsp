<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registro Usuario Propietario</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" type="image/png"
          href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="javascript/scriptRegistroPropietario2.js"></script>
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
<nav
        class="bg-white w-full flex fixed top-0 justify-between items-center mx-auto px-8 h-20 border-b-2 border-gray-200 z-50">
    <!-- logo -->
    <div class="inline-flex">
        <a class="_o6689fn" href="index.jsp">
            <div class=" md:block lg:ml-12">
                <img src="https://cdn.discordapp.com/attachments/1147607064525414410/1147613138947153990/fernanbnb.png"
                     width="152" height="42" style="display: block">
            </div>

        </a>
    </div>

    <div class="text-center md:text-left ml-5">
        <p
                class="text-lg md:text-xl lg:text-2xl xl:text-3xl sm:text-xs font-bold text-indigo-700 hover:text-indigo-900 transition duration-300">
            PROPIETARIOS</p>
    </div>


</nav>

<!-- contenido principal -->
<section class="bg-gray-50 dark:bg-gray-100">
    <div class="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <div class="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:border-gray-400">
            <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
                <h1 class="text-xl font-bold leading-tight tracking-tight  md:text-2xl ">
                    Registro de cuenta de propietario
                </h1>
                <!-- Formulario de registro -->
                <form id="registroFormPropietario" action="validacionRegistro.jsp" method="get">
                    <div class="mb-2 sm:mb-4">
                        <label for="nombrePropietario" class="block mb-2 text-sm font-medium text-gray-900"
                        >Nombre
                        </label>
                        <input type="text" id="nombrePropietario" name="nombrePropietario"
                               placeholder="Escriba su nombre completo"
                               class="bg-gray-100 border border-gray-300 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               required>
                    </div>
                    <div class="mb-2 sm:mb-4">
                        <label for="correoPropietario" class="block mb-2 text-sm font-medium text-gray-900"
                        >Correo
                            electrónico</label>
                        <input type="email" id="correoPropietario" name="correoPropietario"
                               placeholder="Escriba su correo"
                               class="bg-gray-100 border border-gray-300 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               required>
                    </div>
                    <div class="mb-2 sm:mb-4">
                        <label for="contrasenaRegistroPropietario"
                               class="block mb-2 text-sm font-medium text-gray-900">Contraseña</label>
                        <input type="password" id="contrasenaRegistroPropietario" name="contrasenaRegistroPropietario"
                               placeholder="••••••••••••••••••••"
                               class="bg-gray-100 border border-gray-300 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               required>
                    </div>
                    <div class="mb-2 sm:mb-4">
                        <label for="confirmarContrasenaPropietario"
                               class="block mb-2 text-sm font-medium text-gray-900">Confirmar Contraseña</label>
                        <input type="password" id="confirmarContrasenaPropietario" name="confirmarContrasenaPropietario"
                               placeholder="••••••••••••••••••••"
                               class="bg-gray-100 border border-gray-300 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:border-gray-600 dark:placeholder-gray-400  dark:focus:ring-blue-500 dark:focus:border-blue-500"
                               required>
                    </div>
                    <div id="mensajeErrorProp" class="text-red-600 mb-2 sm:mb-4"></div>
                    <%
                        Boolean correoEnUso = (Boolean) request.getSession().getAttribute("correoEnUso");
                        if (correoEnUso != null && correoEnUso) {
                    %>
                    <div class="text-red-600 mb-2 sm:mb-4">El correo electrónico ya esta en uso.</div>
                    <%} %>
                    <button type="submit"
                            class="w-full text-white bg-gray-900 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:focus:ring-primary-800">
                        Registrarse
                    </button>
                </form>
                <!-- Fin del formulario de registro -->
            </div>
        </div>
    </div>
</section>
<!-- Fin del contenido principal -->

<!-- ... (tu código actual) ... -->

</body>

</html>


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