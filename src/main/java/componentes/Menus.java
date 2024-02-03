package componentes;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Menus {
    /**
     * Genera un menú de usuario con las opciones disponibles para el usuario.
     *
     * @param nombre      el nombre del usuario que inició sesión
     * @param numReservas el número de reservas pendientes que tiene el usuario
     * @return una cadena con el menú de usuario formateado
     * @autor Adrián Lara Bonilla
     */
    public static String menuUsuario(String nombre, int numReservas, String lastSession) {
        return String.format("""
                                                             ╔═════════════════╗
                            ╔════════════════════════════════╣ MENÚ DE USUARIO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║  %30s, busque su alojamiento adecuado.                  ║ 
                            ║   %53s                           ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s%45s     ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Búsqueda de alojamientos                                                     ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Ver mis reservas                                                             ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Modificar mis reservas                                                       ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Ver mi perfil                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Modificar mi perfil                                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [6] Cerrar sesión                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numReservas > 0 ? numReservas == 1 ? "Actualmente tienes 1 reserva pendiente." :
                        "Actualmente tienes " + numReservas + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), lastSession.length() > 1 ? ". Su ultima conexión fue: " + lastSession : "");
    }

    /**
     * Genera un menú de invitado con las opciones disponibles para el usuario.
     *
     * @return una cadena con el menú de usuario formateado
     * @autor Adrián Lara Bonilla
     */
    public static String menuInvitado() {
        return String.format("""
                                                            ╔══════════════════╗
                            ╔═══════════════════════════════╣ MENÚ DE INVITADO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚══════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║  Bienvenido, esta usted en modo invitado, en este modo no podrá hacer operaciones ║  
                            ║  normales, registrese para poder operar correctamente.                            ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Búsqueda de alojamientos                                                     ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Ver mis reservas                                                             ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Modificar mis reservas                                                       ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Ver mi perfil                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Modificar mi perfil                                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [6] Cerrar sesión                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * Genera un menú de usuario con las opciones de tipo de reservas.
     *
     * @param nombre      el nombre del usuario que inició sesión
     * @param numReservas el número de reservas pendientes que tiene el usuario
     * @return una cadena con el menú de usuario formateado
     * @autor Adrián Lara Bonilla
     */
    public static String menuReservasUsuario(String nombre, int numReservas) {
        return String.format("""
                                                             ╔═════════════════╗
                            ╔════════════════════════════════╣ MENÚ DE USUARIO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║  %30s, busque su alojamiento adecuado.                  ║  
                            ║   %53s                           ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Reservas Pendientes                                                          ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Reservas Finalizadas                                                         ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numReservas > 0 ? numReservas == 1 ? "Actualmente " + "tienes 1 reserva pendiente." :
                        "Actualmente tienes " + numReservas + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public static String menuReservasPropietario(String nombre, int numViviendas, int numReservasPendientes) {
        return String.format("""
                                                             ╔═════════════════════╗
                            ╔════════════════════════════════╣ MENÚ DE PROPIETARIO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, gestione sus viviendas en alquiler.                 ║ 
                            ║   %33s%42s         ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════════╣
                            ║ -----------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                      ║
                            ║ -----------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════════╣
                            ║  ----------------------------------------------------------------------------------   ║                                                      
                            ║  [1] Reservas Pendientes                                                              ║
                            ║  ----------------------------------------------------------------------------------   ║  
                            ║  [2] Reservas Finalizadas                                                             ║
                            ║  -----------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                            ║
                            ║  -----------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numViviendas > 0 ? numViviendas == 1 ? "Actualmente tienes 1 vivienda" : "Actualmente tienes " +
                        numViviendas + " viviendas" : "", numReservasPendientes > 0 ? numReservasPendientes == 1 ? ". Actualmente " +
                        "tienes 1 reserva pendiente." : " Actualmente tienes " + numReservasPendientes + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * Genera un menú de usuario con las opciones de busqueda de alojamientos.
     *
     * @param nombre      el nombre del usuario que inició sesión
     * @param numReservas el número de reservas pendientes que tiene el usuario
     * @return una cadena con el menú de usuario formateado
     * @autor Adrián Lara Bonilla
     */
    public static String menuBusquedaAlojamiento(String nombre, int numReservas) {
        return String.format("""
                                                             ╔═════════════════╗
                            ╔════════════════════════════════╣ MENÚ DE USUARIO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║  %30s, busque su alojamiento adecuado.                  ║ 
                            ║   %53s                           ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Búsqueda por localidad                                                       ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Búsqueda por provincia                                                       ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numReservas > 0 ? numReservas == 1 ? "Actualmente " + "tienes 1 reserva pendiente." :
                        "Actualmente tienes " + numReservas + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * Genera un menú de usuario con las opciones de busqueda de alojamientos.
     *
     * @param nombre      el nombre del usuario que inició sesión
     * @param numReservas el número de reservas pendientes que tiene el usuario
     * @return una cadena con el menú de usuario formateado
     * @autor Adrián Lara Bonilla
     */
    public static String modificaReserva(String nombre, int numReservas) {
        return String.format("""
                                                             ╔═════════════════╗
                            ╔════════════════════════════════╣ MENÚ DE USUARIO ╠════════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║  %30s, modifique su reserva.                            ║ 
                            ║   %53s                           ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Cambiar fecha inicio                                                         ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Cambiar duración                                                             ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Cambiar número de huéspedes                                                  ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Borrar reserva                                                               ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numReservas > 0 ? numReservas == 1 ? "Actualmente " + "tienes 1 reserva pendiente." :
                        "Actualmente tienes " + numReservas + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * Menú para que el propietario gestione sus viviendas en alquiler.
     *
     * @param nombre                nombre del propietario
     * @param numViviendas          número de viviendas que tiene el propietario
     * @param numReservasPendientes número de reservas pendientes que tiene el propietario
     * @return el menú de opciones en formato String
     * @author Adrián Lara Bonilla
     */
    public static String menuPropietario(String nombre, int numViviendas, int numReservasPendientes, String lastSession) {
        return String.format("""
                                                           ╔═════════════════════╗
                            ╔══════════════════════════════╣ MENÚ DE PROPIETARIO ╠══════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, gestione sus viviendas en alquiler.             ║ 
                            ║   %33s%42s     ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s%45s     ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Ver mis viviendas en alquiler                                                ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Añadir o editar mis viviendas                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Ver las reservas de mis viviendas                                            ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Establecer un periodo de no disponible para una vivienda                     ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Ver mi perfil                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [6] Modificar mi perfil                                                          ║
                            ║  -------------------------------------------------------------------------------  ║
                            ║  [7] Enviar listado de reservas por correo                                        ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [8] Cerrar sesión                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", numViviendas > 0 ? numViviendas == 1 ? "Actualmente tienes 1 vivienda" : "Actualmente tienes " +
                        numViviendas + " viviendas" : "", numReservasPendientes > 0 ? numReservasPendientes == 1 ? ". Actualmente " +
                        "tienes 1 reserva pendiente." : " Actualmente tienes " + numReservasPendientes + " reservas pendientes." : "",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), lastSession.length() > 1 ? ". Su ultima conexión fue: " + lastSession : "");
    }

    /**
     * Genera y retorna un menú de opciones para el perfil de administración.
     *
     * @param nombre El nombre del usuario con perfil de administración.
     * @return El menú de opciones en formato de cadena de caracteres.
     * @autor Adrián Lara Bonilla
     */
    public static String menuAdministrador(String nombre, int usuariosTotales, int viviendastotales, int reservasTotales, String lastSession) {
        return String.format("""
                                                          ╔═══════════════════════╗
                            ╔═════════════════════════════╣ MENÚ DE ADMINISTRADOR ╠═════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═══════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, perfil de administración.                       ║ 
                            ║   Actualmente hay %6d usuarios registrados en Fernanbnb.                       ║ 
                            ║   Actualmente hay %6d viviendas y %6d reservas en total en Fernanbnb.       ║       
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s%45s     ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Ver todas las viviendas en alquiler                                          ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Ver todos los usuarios del sistema                                           ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Ver todas las reservas de viviendas                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Ver mi perfil                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Modificar mi perfil                                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [6] Mostrar configuracion del programa                                           ║
                            ║  -------------------------------------------------------------------------------  ║
                            ║  [7] Enviar listado de reservas por correo                                        ║
                            ║  -------------------------------------------------------------------------------  ║
                            ║  [8] Copia de seguridad                                                           ║
                            ║  -------------------------------------------------------------------------------  ║
                            ║  [9] Cerrar sesión                                                                ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", usuariosTotales, viviendastotales,
                reservasTotales, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                lastSession.length() > 1 ? ". Su ultima conexión fue: " + lastSession : "");
    }

    public static String menuConfiguracionPrograma(String nombre, int usuariosTotales, int viviendastotales, int reservasTotales) {
        return String.format("""
                                                          ╔═══════════════════════╗
                            ╔═════════════════════════════╣ MENÚ DE ADMINISTRADOR ╠═════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═══════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, perfil de administración.                       ║ 
                            ║   Actualmente hay %6d usuarios registrados en Fernanbnb.                       ║ 
                            ║   Actualmente hay %6d viviendas y %6d reservas en total en Fernanbnb.       ║       
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Mostrar configuración del properties                                         ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Ver última conexión de los usuarios                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝             
                        """, nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", usuariosTotales, viviendastotales,
                reservasTotales, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }
    public static String menuConfiguracionBackup(String nombre, int usuariosTotales, int viviendastotales, int reservasTotales) {
        return String.format("""
                                                          ╔═══════════════════════╗
                            ╔═════════════════════════════╣ MENÚ DE ADMINISTRADOR ╠═════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═══════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, perfil de administración.                       ║ 
                            ║   Actualmente hay %6d usuarios registrados en Fernanbnb.                       ║ 
                            ║   Actualmente hay %6d viviendas y %6d reservas en total en Fernanbnb.       ║       
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Recuperar copia de seguridad                                                 ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Guardar copia de seguridad                                                   ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝             
                        """, nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", usuariosTotales, viviendastotales,
                reservasTotales, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public static String menuModificaVivienda(String nombre) {
        return String.format("""
                                                           ╔═════════════════════╗
                            ╔══════════════════════════════╣ MENÚ DE PROPIETARIO ╠══════════════════════════════╗
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║   %30S, aquí podrá modificar los datos                  ║
                            ║   de su vivienda.                                                                 ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Añadir vivienda                                                              ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Cambiar título                                                               ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Cambiar descripción                                                          ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Cambiar precio por noche                                                     ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [5] Cambiar numero máximo de ocupantes                                           ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [6] Borrar vivienda                                                              ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [7] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , nombre.length() > 0 ? "Bienvenido " + nombre.toUpperCase() : "", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }


    /**
     * Genera el menú de inicio de Fernanbnb con tres opciones: Iniciar sesión, Registrarse y Salir.
     *
     * @return una cadena de caracteres con el menú de inicio de Fernanbnb.
     * @autor Adrián Lara Bonilla
     */
    public static String menuInicio(boolean invitado) {
        return String.format("""
                                                               ╔═════════════╗
                            ╔══════════════════════════════════╣  FERNANbnb  ╠══════════════════════════════════╗ 
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║    Bienvenido a Fernanbnb!! Encuentra el alojamiento perfecto para tus próximas   ║ 
                            ║    vacaciones alquilando tus pisos favoritos. Si eres propietario alquila tus     ║ 
                            ║    propias viviendas. Registrate ya si no tienes cuenta es muy fácil!             ║
                            ║ -------------------------------------------------------------------------------   ║
                            ║       Si ya tienes una cuenta, inicia sesión y reserva tu próximo destino.        ║ 
                            ║              ¡Disfruta de tus vacaciones al máximo con nosotros!                  ║ 
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Inicio de sesión                                                             ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Registrarse                                                                  ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            %s
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), invitado ? """
                        ║  [3] Iniciar como invitado                                                        ║
                            ║  -------------------------------------------------------------------------------  ║
                            ║  [4] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║""" : //si es true invitado sino solo salir
                        """
                                ║  [3] Salir                                                                        ║
                                    ║  -------------------------------------------------------------------------------  ║"""
        );
    }

    /**
     * Genera un menú con las opciones de registro de usuario, registro de propietario y salir del programa.
     * La fecha actual se muestra en el encabezado del menú.
     *
     * @return el menú generado como una cadena de texto
     * @autor Adrián Lara Bonilla
     */
    public static String menuTipoRegistro() {
        return String.format("""
                                                               ╔═════════════╗
                            ╔══════════════════════════════════╣  FERNANbnb  ╠══════════════════════════════════╗ 
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚═════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Registro de Usuario                                                          ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Registro de Propietario                                                      ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * Retorna el menú de modificación de perfil del usuario.
     *
     * @return el menú de modificación de perfil del usuario.
     * @autor Adrián Lara Bonilla
     */
    public static String menuModificaPerfilUsuario() {
        return String.format("""
                                                         ╔════════════════════════╗
                            ╔════════════════════════════╣ Modificación de perfil ╠═════════════════════════════╗ 
                            ║░░░░░░░░░░░░░░░░░░░░░░░░░░░░╚════════════════════════╝░░░░░░░░░░░░░░░░░░░░░░░░░░░░░║ 
                            ║ -------------------------------------------------------------------------------   ║
                            ║   La fecha actual es: %s                                                  ║        
                            ║ -------------------------------------------------------------------------------   ║
                            ╠═══════════════════════════════════════════════════════════════════════════════════╣
                            ║  -------------------------------------------------------------------------------  ║                                                      
                            ║  [1] Cambiar Nombre                                                               ║
                            ║  -------------------------------------------------------------------------------  ║  
                            ║  [2] Cambiar correo                                                               ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [3] Cambiar contraseña                                                           ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ║  [4] Salir                                                                        ║
                            ║  -------------------------------------------------------------------------------  ║ 
                            ╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public static String cartelNumeracionReservas(int numero) {
        return String.format("""
                \t\t\t\t\t\t\t  ╔═══════════════════════╗
                \t\t\t\t\t\t\t  ║   Número: %4d        ║       
                \t\t\t\t\t\t\t  ╚═══════════════════════╝
                """, numero);
    }

    public static String cartelNumeracionViviendasNormal(int numero) {
        return String.format("""
                \t\t\t\t\t\t\t\t   ╔═════════════════════╗
                \t\t\t\t\t\t\t\t   ║    Número: %4d     ║       
                \t\t\t\t\t\t\t\t   ╚═════════════════════╝
                """, numero);
    }

    public static String cartelNumeracionViviendasResumen(int numero) {
        return String.format("""
                \t\t\t\t\t\t\t\t\t\t\t\t╔═════════════════════╗
                \t\t\t\t\t\t\t\t\t\t\t\t║    Número: %4d     ║       
                \t\t\t\t\t\t\t\t\t\t\t\t╚═════════════════════╝
                """, numero);
    }


}

