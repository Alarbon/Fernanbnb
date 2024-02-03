<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error 404</title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="icon" type="image/png"
          href="https://cdn.discordapp.com/attachments/1147607064525414410/1147607094774743112/logo.png"/>
    <script src="https://cdn.tailwindcss.com"></script>

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


</nav>

<div class="flex flex-col md:flex-row justify-center items-center min-h-screen p-4 md:p-0">
    <div class="text-center md:text-left max-w-screen-md mt-16 md:mt-0 md:ml-8">
        <p class="text-4xl font-bold">Ha ocurrido un error al realizar la petición</p>
        <p class="text-lg text-red-500 mt-2">
            <% if (session.getAttribute("error") != null) { %>
            <%=session.getAttribute("error")%>
            <% } %>
            <% session.removeAttribute("error");%>
        </p>
        <button class="mt-4 bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded">
            <a href="index.jsp">Inicio</a>
        </button>
        <button class="mt-4 bg-gray-900 hover:bg-gray-800 text-white font-bold py-2 px-4 rounded" onclick="goBack()">
            Volver
        </button>

        <script>
            function goBack() {
                window.history.back();
            }
        </script>


    </div>
    <img class="mt-4 w-full md:w-auto max-w-md h-auto mx-auto md:mx-0"
         src="https://cdn.discordapp.com/attachments/1147607064525414410/1148711789454971000/404-Airbnb_final-d652ff855b1335dd3eedc3baa8dc8b69.gif"
         alt="Error GIF">
</div>


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