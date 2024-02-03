

# <img src="https://cdn.discordapp.com/attachments/1052443807112773724/1067485591652020376/portada.png" alt="presentacion" style="zoom:60%;" />



# 😇Tabla de contenido😇

1. [**Prerequisitos e instalación**](#id1)

   1.1. [Cosas persistencia tener en cuenta](#id1.1)

2. [**Menú Principal**](#id2)

   2.1[Menú de navegación de las opciones del menú principal](#id2.1)

3. [**Menú Usuario**](#id3)

   3.1[Menú de navegación de las opciones de los usuarios normales](#id3.1)

4. [**Menú Usuario Propietario**](#id4)

   4.1[Menú de navegación de las opciones de usuarios propietarios](#id4.1)

5. [**Menú  Usuario Administrador**](#id5)

   5.1[Menú de navegación del usuario Administrador](#id5.1)

6. [**Menú Invitado**](#id6)

7. [**Lenguajes utilizados**](#id7)

8. [**Actualizaciones**](#id8)

9. [**Autores**](#i9)

   

   

   # 👽Prerrequisitos e instalación 👽

   <div id='id1' />

   Para instalar y ejecutar este programa necesitaremos tener instalado una version de jdk. Es recomendable para que no haya fallos tener el jdk 19.   [Descarga aqui](https://www.oracle.com/es/java/technologies/downloads/#jdk19-windows"Descarga aqui")

   Por otro lado la versión web va a utilizar apache tomcat 8.5.92 puediendo decargar [aquí](https://tomcat.apache.org/download-80.cgi"aqui")

   

   ## 🙏Cosas persistencia tener en cuenta💢

   <div id='id1.1' />

   En la carpeta config hay un archivo llamado "fernanbnb.properties", tal y como se puede observar en la imagen previa. Dicho archivo almacena rutas que son utilizadas para el almacenamiento de datos o para la configuración de las comunicaciones del programa.

   Dentro del archivo "fernanbnb.properties", se encuentran dos líneas importantes: "primer.inicio" y "invitado.menu". La primera línea, si se encuentra con el valor "true", indica que se trata del primer inicio del programa y, por tanto, se ejecutará desde cero utilizando únicamente los datos del mock, eliminando así cualquier persistencia previa. Posteriormente, el valor de esta línea cambiará automáticamente persistencia "false" y se mantendrá así.

   La segunda línea, "invitado.menu", tiene dos posibles valores: "true" o "false". Si su valor es "true", se habilitará el menú de invitado, mientras que si su valor es "false", dicho menú no se encontrará disponible.

   En web el modo invitado estará habilitado siempre

   

   <img src="https://cdn.discordapp.com/attachments/1099778833852420166/1100522098289545216/properties.png" style="zoom:75%;" />

   Los datos para el logueo por defecto son los siguientes:

   **Usuarios normales:** ❕❕❕

   ​	-Correo: paco@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: eduardo@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: maria@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: cindy@gmail.com

   ​	-Contraseña: 1234.

   **Usuarios propietarios:** ❕❕❕

   ​	-Correo: juan@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: vicente@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: felipe@gmail.com

   ​	-Contraseña: 1234.

   **Usuario administrador:** ❕❕❕

   ​	-Correo: adrian@gmail.com

   ​	-Contraseña: 1234.

   ​	-Correo: josemi@gmail.com

   ​	-Contraseña: 1234.

   

   

   He aquí el diagrama de estados del sitio web:

   <img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155661268682805258/diagrama_estados.png" />

   

   
   
   

# 👉Menú Principal 📖

<div id='id2' />

Este será el menu principal tanto como para el invitado como el usuario normal.

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155629601620504668/image.png?width=1368&height=670" style="zoom:80%;" />

Podremos iniciar sesión (cualquier tipo de usuario) o registrarnos como usuario normal. Si queremos como propietario podremos acceder mediante las opciones de "Pon tu casa en Fernanbnb"

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155629856223154236/image.png?width=551&height=413" style="zoom:80%;" />

Tendremos esta visualizacion siendo los logins y registros iguales :

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155630341478948914/image.png" style="zoom:80%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155630594806513705/image.png?width=1413&height=670" style="zoom:80%;" />



Tambien podremos acceder desde la pantalla de inicio saliendo modales ocultos:

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155631011196063755/image.png" style="zoom:80%;" />



Al registrarnos necesitaremos acceder mediante un botón a nuestro correo:

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155631488642068510/image.png?width=515&height=670" style="zoom:80%;" />







# 👉Menú Usuario Normal 📖

<div id='id3' />

Tras acceder con nuestro correo y contraseña de usuario normal nos aparecerá el menu de usuario. Tendremos las mismas opciones que en la versión de consola aqui enseñaré varias imagenes.

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155632126146924564/image.png" style="zoom:80%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155634454044344460/image.png?width=1378&height=670" style="zoom:80%;" /><img src="https://media.discordapp.net/attachments/1155629589486379059/1155634120299388928/image.png?width=1386&height=670" style="zoom:80%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155634369650774107/image.png?width=1440&height=660" style="zoom:80%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155634846467633252/image.png?width=1361&height=670" style="zoom:80%;" />

El chat se ha añadido en la version Web.

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155634656717312010/image.png?width=1403&height=670" style="zoom:80%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155635882016124968/image.png?width=1401&height=670" style="zoom:80%;" />



Confirmaremos la reserva y si todo ha ido correctamente nos saldra un mensaje como el anterior. Además tanto el usuario que hace la reserva como el usuario propietario reciben un correo de aviso. Ademas el administrador recibe un mensaje por telegram.

El usuario recibirá una notificacion de que la reserva se ha efectuado correctamente y al propietario que su vivienda ha sido reservada, los mensajes son como este:

<img src="https://cdn.discordapp.com/attachments/1069378423388131410/1069395085223018566/image.png" style="zoom:80%;" />

Podremos buscar alojamiento, reservar editar la reserva o eliminarla (7 días antes del inicio de la reserva), modificar nuestra cuenta etc



# 👉Menú Usuario Propietario 🎓

<div id='id4' />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155636349722955857/image.png?width=1393&height=670" style="zoom:80%;" />

El apartado del propietario es muy parecido al usuario con leves cambios. 

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155636587699388528/image.png" style="zoom:80%;" />



La zona de reservas donde podemos ver cada una de ellas

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155636720079994890/image.png" style="zoom:100%;" />

Nuestras viviendas:

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155636659702988860/image.png?width=1391&height=670" style="zoom:80%;" />

La manipulación de cada una de ellas:

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155645663825117264/image.png" style="zoom:100%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155646249748410389/image.png?width=1371&height=670" style="zoom:100%;" />

<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155646329234669688/image.png" style="zoom:100%;" />



# 👉Menú  Usuario Administrador 🔒 

<div id='id5' />

El  index del administrador se ve así:

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155647333246189578/image.png?width=1383&height=670" style="zoom:100%;" />



<img src="https://media.discordapp.net/attachments/1155629589486379059/1155647473822470184/image.png?width=1423&height=670" style="zoom:100%;" />



<img src="https://cdn.discordapp.com/attachments/1155629589486379059/1155647643423354910/image.png" style="zoom:100%;" />



<img src="https://media.discordapp.net/attachments/1155629589486379059/1155647860155613274/image.png?width=1386&height=670" style="zoom:100%;" />



<img src="https://media.discordapp.net/attachments/1155629589486379059/1155648012966699060/image.png?width=1393&height=670" style="zoom:100%;" />

<img src="https://media.discordapp.net/attachments/1155629589486379059/1155648247201812642/image.png?width=1393&height=670" style="zoom:100%;" />

Por último quedaría salirse del programa, para ello selecciona la opcion de salir en el menú principal.

# 👉Menú Invitado ![:trollface:](https://github.githubassets.com/images/icons/emoji/trollface.png) 

<div id='id6' />

En la versión web el apartado invitado esta siempre activado.

# 💿Lenguajes utilizados 🛠️

<div id='id7' />

La herramientas que se utilizo para crear Fernanbnb

* JAVA - El principal lenguaje de codigo usado.

* HTML && CSS - Para las plantillas de los mails. 

* HTML && TailwindCSS - Para el maquetado y estilo de la web

* JSP - Para crear las páginas webs dinamicas

  

# **👀Actualizaciones📋**

<div id='id8' />

- Mejoras de antiguos fallos.

- Optimización del codigo

- Versión Web

- Chat entre usuarios

  

# 💬Autor ✒️

<div id='id9' />

* **Adrián Lara Bonilla** -  [@Alarbon - GitHub](https://github.com/Alarbon "@Alarbon - GitHub")



# 💬Antiguos compañeros de código✒️

<div id='id9' />

* **José Miguel Aranda Fernandez** -  [@JoseMiAranda- GitHub](https://github.com/JoseMiAranda)

  
