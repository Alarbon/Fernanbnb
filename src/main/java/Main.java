import componentes.Excepciones;
import componentes.Menus;
import componentes.Utils;
import comunicaciones.Email;
import comunicaciones.PlantillaMail;
import controller.FernanbnbAPP;
import dataClass.ResumenReservaPDF;
import models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static persistencia.Persistencia.*;
import static componentes.Excepciones.compruebaNumeroEntero;
import static componentes.Excepciones.compruebaNumeroFlotante;
import static componentes.Menus.*;
import static componentes.PedirDatos.*;
import static componentes.Utils.*;
import static comunicaciones.PlantillaTelegram.messageTelegramViviendaAdd;
import static comunicaciones.Telegram.enviarMensajeTelegram;

public class Main {
  public static Scanner S = new Scanner(System.in);

  public static void main(String[] args) throws InterruptedException {
    FernanbnbAPP fernanbnbAPP = new FernanbnbAPP();
    Object user = null;
    boolean invitado;
    boolean appRunning = true;
    int op; //Opción a elegir del menu inicio
    System.out.println("""
            ___   _                                   _      _                    ___                                    _             _   \s
            | _ ) (_)  ___   _ _   __ __  ___   _ _   (_)  __| |  ___     __ _    | __|  ___   _ _   _ _    __ _   _ _   | |__   _ _   | |__\s
            | _ \\ | | / -_) | ' \\  \\ V / / -_) | ' \\  | | / _` | / _ \\   / _` |   | _|  / -_) | '_| | ' \\  / _` | | ' \\  | '_ \\ | ' \\  | '_ \\
            |___/ |_| \\___| |_||_|  \\_/  \\___| |_||_| |_| \\__,_| \\___/   \\__,_|   |_|   \\___| |_|   |_||_| \\__,_| |_||_| |_.__/ |_||_| |_.__/
                                                                                                                                            \s""");

    do {
      invitado = invitadoMenu();
      op = menuInicio(invitado);
      switch (op) {
        case 1 -> {//Login
          user = login(fernanbnbAPP);
          logSesion(user, "Inicio de sesión");
        }
        case 2 -> //Registro
                user = registro(fernanbnbAPP);
        case 3 -> { //Inicio como invitado
          if (!invitado) { //Si invitado es false es que no esta activado la opcion de invitado, por lo que en
            //el menu tendremos la opcion de salir del menu en el 3 asi que saldremos
            appRunning = salirAPP(fernanbnbAPP);
          }
        }
        case 4 -> //Salir de la app
                appRunning = salirAPP(fernanbnbAPP);

        default -> {
          System.out.println("Elige una opción correcta.");
          pulseEnterContinuar();
        }
      }
      if (user != null || (invitado && appRunning)) {
        if (user instanceof Usuario) menuUsuario(fernanbnbAPP, (Usuario) user);
        if (user instanceof Propietario) menuPropietario(fernanbnbAPP, (Propietario) user);
        if (user instanceof Administrador) menuAdministrador(fernanbnbAPP, (Administrador) user);
        if (user == null && invitado && op == 3) menuInvitado(fernanbnbAPP);
      }
      user = null;
    } while (appRunning);
  }

  private static boolean salirAPP(FernanbnbAPP fernanbnbAPP) {
    System.out.println("Saliendo del sistema...");
    esperar();
    System.out.println("Vuelva pronto. :)");
    return false;
  }


  private static int menuInicio(boolean invitado) {
    System.out.println(Menus.menuInicio(invitado));
    System.out.print("Elige la opción que desee: ");
    return compruebaNumeroEntero(S.nextLine());
  }

  /**
   * Este método se encarga de realizar el inicio de sesión de un usuario en la aplicación Fernanbnb.
   *
   * @param fernanbnbAPP La instancia de la clase FernanbnbAPP que maneja la lógica de la aplicación.
   * @return Un objeto que representa al usuario que ha iniciado sesión o null si las credenciales son incorrectas.
   * @author Adrián Lara Bonilla
   */
  private static Object login(FernanbnbAPP fernanbnbAPP) {
    Object user;
    String email;
    System.out.print("Escriba su correo: ");
    email = S.nextLine();
    System.out.print("Escriba su clave: ");
    String clave = S.nextLine();
    user = fernanbnbAPP.login(email, clave);
    if (user == null) {
      System.out.println("Las credenciales son incorrectas. Vuelva a intentarlo.");
      pulseEnterContinuar();
    }
    return user;
  }

  /**
   * Este método se encarga de mostrar un menú para que el usuario elija el tipo de registro que desea realizar y
   * llevar a cabo el proceso de registro correspondiente.
   *
   * @param fernanbnbAPP La instancia de la clase FernanbnbAPP que maneja la lógica de la aplicación.
   * @return Un objeto que representa al usuario que se ha registrado o null si el usuario ha decidido salir del menú.
   * @author Adrián Lara Bonilla
   */
  private static Object registro(FernanbnbAPP fernanbnbAPP) {
    boolean opcionCorrecta;
    Object user = null;
    do {
      opcionCorrecta = true;
      System.out.println(menuTipoRegistro());
      System.out.print("Elige la opción que desee: ");
      switch (S.nextLine()) {
        case "1" -> user = registroUsuario(fernanbnbAPP);
        case "2" -> user = registroPropietario(fernanbnbAPP);
        case "#3AdminFurioso2023" -> user = registroAdministrador(fernanbnbAPP);
        case "3" -> {
          System.out.println("Saliendo...");
          pulseEnterContinuar();
          return null;
        }
        default -> {
          System.out.println("No ha elegido la opción correcta.");
          opcionCorrecta = false;
        }
      }
    } while (!opcionCorrecta);

    return user;
  }

  /**
   * Este método se encarga de realizar el registro de un usuario en la aplicación Fernanbnb.
   *
   * @param fernanbnbAPP La instancia de la clase FernanbnbAPP que maneja la lógica de la aplicación.
   * @return Un objeto que representa al usuario que se ha registrado.
   * @author Adrián Lara Bonilla
   */
  private static Usuario registroUsuario(FernanbnbAPP fernanbnbAPP) {
    Usuario u;
    System.out.println("Bienvenido, escriba los datos para su registro.");
    String nombre = recogeNombreUsuario(), clave = recogeClaveUsuario(), email = recogeEmailUsuario(fernanbnbAPP, "email");
    System.out.println("Estamos creando su usuario, por favor acceda a su correo estamos generando un token de confirmación...");
    u = fernanbnbAPP.addUsuario(nombre, clave, email, false);
    esperar();
    System.out.println("Usted se ha registrado correctamente.");
    validarCuentaToken(email, u, fernanbnbAPP, true);
    return u;
  }

  /**
   * Este método se encarga de realizar el registro de un usuario propietario en la aplicación Fernanbnb.
   *
   * @param fernanbnbAPP La instancia de la clase FernanbnbAPP que maneja la lógica de la aplicación.
   * @return Un objeto que representa al usuario propietario que se ha registrado.
   * @author Adrián Lara Bonilla
   */
  private static Propietario registroPropietario(FernanbnbAPP fernanbnbAPP) {
    Propietario p;
    System.out.println("Bienvenido, escriba los datos para su registro.");
    String nombre = recogeNombreUsuario(), clave = recogeClaveUsuario(), email = recogeEmailUsuario(fernanbnbAPP, "email");
    System.out.println("Estamos creando su usuario, por favor acceda a su correo estamos generando un token de confirmación...");
    p = fernanbnbAPP.addPropietario(nombre, clave, email, false);
    esperar();
    System.out.println("Usted se ha registrado correctamente.");
    validarCuentaToken(email, p, fernanbnbAPP, true);
    return p;
  }

  /**
   * Este método se encarga de realizar el registro de un usuario en la aplicación Fernanbnb.
   *
   * @param fernanbnbAPP La instancia de la clase FernanbnbAPP que maneja la lógica de la aplicación.
   * @return Un objeto que representa al usuario que se ha registrado.
   * @author Adrián Lara Bonilla
   */
  private static Administrador registroAdministrador(FernanbnbAPP fernanbnbAPP) {
    Administrador adm;
    System.out.println("Bienvenido, escriba los datos para su registro.");
    String nombre = recogeNombreUsuario(), clave = recogeClaveUsuario(), email = recogeEmailUsuario(fernanbnbAPP, "email");
    System.out.println("Estamos creando su usuario, por favor acceda a su correo estamos generando un token de confirmación...");
    adm = fernanbnbAPP.addAdministrador(nombre, clave, email, false);
    esperar();
    System.out.println("Usted se ha registrado correctamente.");
    validarCuentaToken(email, adm, fernanbnbAPP, true);
    return adm;
  }


  private static void menuAdministrador(FernanbnbAPP fernanbnbAPP, Administrador user) throws InterruptedException {
    if (!fernanbnbAPP.compruebaTokenValidado(user)) {
      validarCuentaToken(user.getEmail(), user, fernanbnbAPP, false);
    } else {
      int op;
      boolean salidaForzosa = false;
      do {
        user = (Administrador) fernanbnbAPP.actualizaPerfil(user);
        op = menuInicioAdministrador(fernanbnbAPP, user);
        switch (op) {
          case 1 -> verViviendasEnAlquiler(fernanbnbAPP);
          case 2 -> verTodosUsuariosSistema(fernanbnbAPP);
          case 3 -> verTodasReservasSistema(fernanbnbAPP);
          case 4 -> verPerfil(user);
          case 5 -> modificarPerfil(user, fernanbnbAPP);
          case 6 -> mostrarConfiguracionFernanbnb(user, fernanbnbAPP);
          case 7 -> enviarlistadoReservasCorreo(user, fernanbnbAPP);
          case 8 -> salidaForzosa = copiadeSeguridadDelSistema(user, fernanbnbAPP);
          case 9 -> op = cerrandoSesion(op, user);

          default -> System.out.println("Seleccione una opción correcta.");
        }
      } while (op != 9 && !salidaForzosa);

      if (salidaForzosa) cerrarSesion();
    }

  }

  private static boolean copiadeSeguridadDelSistema(Administrador user, FernanbnbAPP fernanbnbAPP) {
    int op;
    boolean salirSesion = false;
    do {
      System.out.println(Menus.menuConfiguracionBackup(user.getNombre(), fernanbnbAPP.numeroUsuarios(), fernanbnbAPP.numeroViviendas(), fernanbnbAPP.numeroReservas()));
      System.out.print("Elige la opción que desee: ");
      op = Excepciones.compruebaNumeroEntero(s.nextLine());
      switch (op) {
        case 1 -> {
          salirSesion = cargarBackupEnRutaDeseada(fernanbnbAPP);
          if (salirSesion) op = 3;
        }
        case 2 -> guardarBackupEnRutaDeseada(fernanbnbAPP);
        case 3 -> op = salirVentanaMenu(op);
        default -> System.out.println("Seleccione una opción correcta.");
      }
    } while (op != 3);

    return salirSesion;

  }

  private static boolean cargarBackupEnRutaDeseada(FernanbnbAPP fernanbnbAPP) {
    System.out.println("Si hiciste la copìa de seguridad en la siguiente ruta:'C:\\Users\\usuario\\Desktop\\backup\\enero\\'. \n" +
            "Deberias de escribir lo siguiente:'\\Desktop\\backup\\enero\\'. La Parte del 'C:\\Users\\usuario la debes ignorar.");
    System.out.print("Escriba la ruta como se le ha indicado: ");
    String ruta;
    do {
      ruta = s.nextLine();
      if (!ruta.endsWith("\\")) System.out.print("La ruta debe acabar en '\\'. Vuelva a escribir la ruta: ");
    } while (!ruta.endsWith("\\"));
    System.out.println("Esto puede tardar un poco, espere...");
    if (fernanbnbAPP.cargarBackup(ruta)) {
      System.out.println("La copia de seguridad se ha restaurado correctamente.");
      pulseEnterContinuar();
      return true;
    } else {
      System.out.println("No se ha podido recuperar la copia de seguridad. ¿Ruta correcta? " +
              "¿Existen los archivos de backup?");
      pulseEnterContinuar();
      return false;
    }

  }

  private static void guardarBackupEnRutaDeseada(FernanbnbAPP fernanbnbAPP) {
    System.out.println("Si desease hacer el backup en esta ruta :'C:\\Users\\usuario\\Desktop\\backup\\enero\\'. \n" +
            "Deberias de escribir lo siguiente:'\\Desktop\\backup\\enero\\'. La Parte del 'C:\\Users\\usuario la debes ignorar.");
    System.out.print("Escriba la ruta como se le ha indicado: ");
    String ruta;
    do {
      ruta = s.nextLine();
      if (!ruta.endsWith("\\")) System.out.print("La ruta debe acabar en '\\'. Vuelva a escribir la ruta: ");
    } while (!ruta.endsWith("\\"));
    System.out.println("Espere un momento, se está guardando los datos...");
    if (fernanbnbAPP.guardaBackup(ruta)) System.out.println("Backup creado correctamente!");
    else System.out.println("Ha ocurrido un error. ¿Ruta incorrecta?.");
    pulseEnterContinuar();
  }

  private static void enviarlistadoReservasCorreo(Administrador user, FernanbnbAPP fernanbnbAPP) {
    ArrayList<Reserva> reservas = fernanbnbAPP.getAllReservas();
    Collections.sort(reservas);
    if (reservas.isEmpty()) {
      System.out.println("No hay reservas registradas.");
    } else {
      if (generaListadoReservas(reservas)) {
        System.out.println("Estamos generando el archivo, espere un momento...");
        Email.enviarMensajeExcelAdjuntoListadoReservas(user.getEmail(), "Listado Reservas",
                PlantillaMail.mensajeListadoReservas(user.getNombre()));
        System.out.println("Listo! el listado ha sido enviado a su correo.");
      } else System.out.println("Ha ocurrido un error inesperado, vuelva a intentarlo mas tarde.");
    }
    pulseEnterContinuar();
  }

  private static void enviarlistadoReservasCorreo(Propietario user) {
    ArrayList<Reserva> reservas = user.getReservas();
    if (reservas.isEmpty()) {
      System.out.println("No hay reservas registradas.");
    } else {
      if (generaListadoReservas(reservas)) {
        System.out.println("Estamos generando el archivo, espere un momento...");
        Email.enviarMensajeExcelAdjuntoListadoReservas(user.getEmail(), "Listado Reservas",
                PlantillaMail.mensajeListadoReservas(user.getNombre()));
        System.out.println("Listo! el listado ha sido enviado a su correo.");
      } else System.out.println("Ha ocurrido un error inesperado, vuelva a intentarlo mas tarde.");
    }
    pulseEnterContinuar();
  }

  private static void mostrarConfiguracionFernanbnb(Administrador user, FernanbnbAPP fernanbnbAPP) {
    int op;
    do {
      System.out.println(Menus.menuConfiguracionPrograma(user.getNombre(), fernanbnbAPP.numeroUsuarios(), fernanbnbAPP.numeroViviendas(), fernanbnbAPP.numeroReservas()));
      System.out.print("Elige la opción que desee: ");
      op = Excepciones.compruebaNumeroEntero(s.nextLine());
      switch (op) {
        case 1 -> mostrarConfiguracionProperties();
        case 2 -> mostrarLastSesionUsers();
        case 3 -> op = salirVentanaMenu(op);
        default -> System.out.println("Seleccione una opción correcta.");
      }
    } while (op != 3);


  }

  private static void mostrarLastSesionUsers() {
    System.out.println("Aqui tiene la el id del usuario con la ultima conexión:");
    System.out.println();
    System.out.println(leerLastSesionUsers());
    pulseEnterContinuar();
  }

  private static void mostrarConfiguracionProperties() {
    System.out.println("Aqui tiene la configuración actual del programa:");
    System.out.println();
    System.out.println(leerConfiguracionProperties());
    pulseEnterContinuar();
  }

  private static void verTodasReservasSistema(FernanbnbAPP fernanbnbAPP) throws InterruptedException {
    ArrayList<Reserva> reservas = fernanbnbAPP.getAllReservas();
    Usuario u;
    Vivienda v;
    Propietario p;
    Collections.sort(reservas);
    if (reservas.isEmpty()) {
      System.out.println("No hay reservas registradas.");
    } else {
      int index = 0;
      int ultimaReservaMostrada = -1; // Inicializar la variable
      boolean continuar = true;
      do {
        int limiteSuperior = Math.min(index + 5, reservas.size());
        for (int i = ultimaReservaMostrada + 1; i < limiteSuperior; i++) {//Muestra viviendas de 5 en 5
          u = fernanbnbAPP.buscaUsuariobyId(reservas.get(i).getIdUsuario()); //Busco al usuario
          v = fernanbnbAPP.buscaViviendaId(reservas.get(i).getIdVivienda());//Busco la vivienda de la reserva
          p = fernanbnbAPP.buscaPropietariobyVivienda(v); //Busco al propietario de la vivienda
          System.out.println(reservas.get(i).pintaDatosParaAdmin(u, p, v)); //pinto los datos de las reservas
          ultimaReservaMostrada = i; // Actualizar la variable

        }
        if (index < reservas.size()) {
          System.out.print("Presione enter para mostrar las siguientes reservas o escriba 'salir' para salir: ");
          String input = S.nextLine();
          if (input.equalsIgnoreCase("salir")) {
            continuar = false;
          }
        }
        index += 5;
      } while (index < reservas.size() && continuar);
    }
    pulseEnterContinuar();
  }


  private static void menuPropietario(FernanbnbAPP fernanbnbAPP, Propietario user) {
    int op;
    do {
      user = (Propietario) fernanbnbAPP.actualizaPerfil(user);
      op = menuInicioPropietario(user, fernanbnbAPP);
      switch (op) {
        case 1 -> verViviendas(user);
        case 2 -> editarViviendas(fernanbnbAPP, user);
        case 3 -> verReservasViviendas(fernanbnbAPP, user);
        case 4 -> establecerPeriodoNoDisponibilidad(fernanbnbAPP, user);
        case 5 -> verPerfil(user);
        case 6 -> modificarPerfil(user, fernanbnbAPP);
        case 7 -> enviarlistadoReservasCorreo(user);
        case 8 -> op = cerrandoSesion(op, user);
        default -> System.out.println("Seleccione una opción correcta.");
      }
    } while (op != 8);

  }

  private static int cerrandoSesion(int op, Object user) {
    boolean decision = false;
    do {
      System.out.print("¿Desea cerrar sesión? (S/N): ");
      switch (S.nextLine().toUpperCase()) {
        case "S" -> {
          cerrarSesion();
          pulseEnterContinuar();
          logSesion(user, "Cierre de sesión");
          return op;
        }
        case "N" -> {
          pulseEnterContinuar();
          return -1;
        }
        default -> System.out.println("Seleccione una opción correcta.");
      }
    } while (!decision);
    return op;
  }

  private static int salirVentanaMenu(int op) {
    boolean decision = false;
    do {
      System.out.print("¿Desea salir? (S/N): ");
      switch (S.nextLine().toUpperCase()) {
        case "S" -> {
          System.out.println("Saliendo...");
          return op;
        }
        case "N" -> {
          return -1;
        }
        default -> System.out.println("Seleccione una opción correcta.");
      }
    } while (!decision);
    pulseEnterContinuar();
    return op;
  }

  private static void menuUsuario(FernanbnbAPP fernanbnbAPP, Usuario user) {
    int op;
    do {
      user = (Usuario) fernanbnbAPP.actualizaPerfil(user);
      op = menuInicioUsuario(user);
      switch (op) {
        case 1 -> buscarAlojamiento(fernanbnbAPP, user);
        case 2 -> verReservasUsuario(fernanbnbAPP, user);
        case 3 -> modificarReservas(fernanbnbAPP, user);
        case 4 -> verPerfil(user);
        case 5 -> modificarPerfil(user, fernanbnbAPP);
        case 6 -> op = cerrandoSesion(op, user);
        default -> System.out.println("Error al introducir la opción");
      }
    } while (op != 6);
  }

  private static void menuInvitado(FernanbnbAPP fernanbnbAPP) {
    int op;
    do {
      op = menuInicioInvitado();
      switch (op) {
        case 1 -> buscarAlojamientoInvitado(fernanbnbAPP);
        case 2 -> {
          System.out.println("No tiene permitido ver las reservas como Invitado. Registrese como usuario.");
          pulseEnterContinuar();
        }
        case 3 -> {
          System.out.println("No tiene permitido modificar reservas como Invitado. Registrese como usuario.");
          pulseEnterContinuar();
        }
        case 4 -> {
          System.out.println("No tiene permitido ver el perfil de Invitado. Registrese como usuario.");
          pulseEnterContinuar();
        }
        case 5 -> {
          System.out.println("No tiene permitido modificar el perfil como Invitado. Registrese como usuario.");
          pulseEnterContinuar();
        }
        case 6 -> op = cerrandoSesion(op, null);
        default -> System.out.println("Error al introducir la opción");
      }
    } while (op != 6);
  }


  /**
   * Permite al usuario modificar su perfil, incluyendo su nombre, email y contraseña.
   *
   * @param user         el usuario que desea modificar su perfil.
   * @param fernanbnbAPP el controller de Fernanbnb.
   * @author Adrian Lara Bonilla
   */
  private static void modificarPerfil(Object user, FernanbnbAPP fernanbnbAPP) {

    //comprobamos que sea un usuario validado si no lo es tendra que validar la cuenta
    if (!fernanbnbAPP.compruebaTokenValidado(user)) {
      if (user instanceof Usuario) validarCuentaToken(((Usuario) user).getEmail(), user, fernanbnbAPP, false);
      if (user instanceof Propietario) validarCuentaToken(((Propietario) user).getEmail(), user, fernanbnbAPP, false);
      if (user instanceof Administrador)
        validarCuentaToken(((Administrador) user).getEmail(), user, fernanbnbAPP, false);
    } else {
      int op;
      do {
        System.out.println(Menus.menuModificaPerfilUsuario());
        System.out.print("Elige la opción que desee: ");
        op = compruebaNumeroEntero(S.nextLine());
        switch (op) {
          case 1 -> {
            String nombre = recogeNombreUsuario();
            if (user instanceof Usuario) {
              fernanbnbAPP.modificaPerfil(new Usuario(((Usuario) user).getId(), nombre, ((Usuario) user).getClave(), ((Usuario) user).getEmail(), ((Usuario) user).isVerificado()));
            }
            if (user instanceof Propietario) {
              fernanbnbAPP.modificaPerfil(new Propietario(((Propietario) user).getId(), nombre, ((Propietario) user).getClave(), ((Propietario) user).getEmail(), ((Propietario) user).isVerificado()));
            }
            if (user instanceof Administrador) {
              fernanbnbAPP.modificaPerfil(new Administrador(((Administrador) user).getId(), nombre, ((Administrador) user).getClave(), ((Administrador) user).getEmail(), ((Administrador) user).isVerificado()));
            }
            System.out.println("Cambio de nombre realizado con éxito");
          }
          case 2 -> {
            if (!fernanbnbAPP.compruebaTokenValidado(user)) {
              if (user instanceof Usuario)
                validarCuentaToken(((Usuario) user).getEmail(), user, fernanbnbAPP, false);
              if (user instanceof Propietario)
                validarCuentaToken(((Propietario) user).getEmail(), user, fernanbnbAPP, false);
              if (user instanceof Administrador)
                validarCuentaToken(((Administrador) user).getEmail(), user, fernanbnbAPP, false);
            } else {
              String email = recogeEmailUsuario(fernanbnbAPP, "nuevo email");
              if (user instanceof Usuario) {
                fernanbnbAPP.modificaPerfil(new Usuario(((Usuario) user).getId(), ((Usuario) user).getNombre(), ((Usuario) user).getClave(), email, ((Usuario) user).isVerificado()));
              }
              if (user instanceof Propietario) {
                fernanbnbAPP.modificaPerfil(new Propietario(((Propietario) user).getId(), ((Propietario) user).getNombre(), ((Propietario) user).getClave(), email, ((Propietario) user).isVerificado()));
              }
              if (user instanceof Administrador) {
                fernanbnbAPP.modificaPerfil(new Administrador(((Administrador) user).getId(), ((Administrador) user).getNombre(), ((Administrador) user).getClave(), email, ((Administrador) user).isVerificado()));
              }
              System.out.println("Cambio de email realizado con éxito");
            }
          }
          case 3 -> {
            String clave = recogeClaveUsuario();
            if (user instanceof Usuario) {
              fernanbnbAPP.modificaPerfil(new Usuario(((Usuario) user).getId(), ((Usuario) user).getNombre(), clave, ((Usuario) user).getEmail(), ((Usuario) user).getToken(), ((Usuario) user).isVerificado()));
            }
            if (user instanceof Propietario) {
              fernanbnbAPP.modificaPerfil(new Propietario(((Propietario) user).getId(), ((Propietario) user).getNombre(), clave, ((Propietario) user).getEmail(), ((Propietario) user).getToken(), ((Propietario) user).isVerificado()));
            }
            if (user instanceof Administrador) {
              fernanbnbAPP.modificaPerfil(new Administrador(((Administrador) user).getId(), ((Administrador) user).getNombre(), clave, ((Administrador) user).getEmail(), ((Administrador) user).getToken(), ((Administrador) user).isVerificado()));
            }
            System.out.println("Cambio de clave realizada con éxito");

          }
          case 4 -> op = salirVentanaMenu(op);
          default -> System.out.println("Error al introducir la opción.");

        }

      } while (op != 4);
    }
  }


  private static void editarViviendas(FernanbnbAPP fernanbnbAPP, Propietario p) {
    p = (Propietario) fernanbnbAPP.actualizaPerfil(p);
    if (!fernanbnbAPP.compruebaTokenValidado(p)) {
      validarCuentaToken(p.getEmail(), p, fernanbnbAPP, false);
    } else {
      int op;
      do {
        p = (Propietario) fernanbnbAPP.actualizaPerfil(p);
        System.out.print(menuModificaVivienda(p.getNombre()));
        System.out.print("Elige la opción que desee: ");
        op = compruebaNumeroEntero(S.nextLine());
        switch (op) {
          case 1 -> // Añadir vivienda
                  agregarVivienda(fernanbnbAPP, p);
          case 2 -> // Cambiar título
                  cambiarTitulo(fernanbnbAPP, p);
          case 3 -> // Cambiar descripción
                  cambiarDescripcion(fernanbnbAPP, p);
          case 4 -> // Cambiar precio
                  cambiarPrecioNoche(fernanbnbAPP, p);
          case 5 -> // Cambiar numero de ocupantes
                  cambiarNumOcupantes(fernanbnbAPP, p);
          case 6 -> // Borrar vivienda
                  borrarVivienda(fernanbnbAPP, p);
          case 7 -> // Salir
                  op = salirVentanaMenu(op);
          default -> System.out.println("Error al introducir la opción");
        }
      } while (op != 7);
    }
  }

  /**
   * Este método pedirá cada uno de los datos de la nueva vivienda que se le agregará al propietario.
   * Una vez pedidos se agregará al propietario una nueva vivienda con dichos datos.
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP el controlador que será el encargado de añadir la vivienda al propietario
   * @param p            el propietario al cual agregaremos la nueva vivienda
   * @author José Miguel Aranda Fernández
   */

  private static void agregarVivienda(FernanbnbAPP fernanbnbAPP, Propietario p) {
    String titulo = recogeNombreLocalidadOProvincia("el titulo");
    String descripcion = recogeNombreLocalidadOProvincia("la descripción");
    String localidad = recogeNombreLocalidadOProvincia("la localidad");
    String provincia = recogeNombreLocalidadOProvincia("la provincia");
    System.out.print("Introduzca el número máximo de ocupantes: ");
    int numMaxOcupantes = Excepciones.compruebaNumeroEntero(S.nextLine());
    System.out.print("Introduzca el precio por noche: ");
    double precioNoche = Excepciones.compruebaNumeroFlotante(S.nextLine());
    esperar();
    Vivienda v = fernanbnbAPP.addVivienda(p.getId(), titulo, descripcion, localidad, provincia, numMaxOcupantes, precioNoche);
    if (v == null) System.out.println("No hemos podido crear la vivienda");
    else {
      enviarMensajeTelegram(messageTelegramViviendaAdd(v, p));
      System.out.println("Vivienda agregada con éxito!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println(v.pintaDatos());
      logVivienda(p, v.getId(), "Inserción de Vivienda");
    }


    pulseEnterContinuar();
  }

  /**
   * Este método se encarga de modificar el título de la vivienda que el propietario haya seleccionado
   * Para que se modifique el título debe de haber registrada una vivienda
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP controller del sistema
   * @param p            el propietario, en el que modificaremos el título de una de sus viviendas
   * @author José Miguel Aranda Fernández
   */
  private static void cambiarTitulo(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas.");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        Vivienda v = fernanbnbAPP.buscaViviendaId(p.getViviendas().get(numVivienda - 1).getId());
        System.out.print("Introduce el nuevo título: ");
        String titulo = S.nextLine();
        esperar();
        if (fernanbnbAPP.modificaVivienda(new Vivienda(v.getId(), titulo, v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getMaxOcupantes(), v.getPrecioNoche()), true))
          System.out.println("El titulo de su vivienda ha sido cambiado con éxito!!!!!!!!!!!!!!!");
        else System.out.println("No hemos podido cambiar el título");

        //fernanbnbAPP.guardaDatosEnDisco(p);
      }
      pulseEnterContinuar();
    }
  }

  /**
   * Este método se encarga de modificar la descripción de la vivienda que el propietario haya seleccionado
   * Para que se modifique la descripción debe de haber registrada una vivienda
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP controller del sistema
   * @param p            el propietario, en el que modificaremos la descripción de una de sus viviendas
   * @author José Miguel Aranda Fernández
   */
  private static void cambiarDescripcion(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        Vivienda v = fernanbnbAPP.buscaViviendaId(p.getViviendas().get(numVivienda - 1).getId());
        System.out.print("Introduce la nueva descripción: ");
        String descripcion = S.nextLine();
        p.getViviendas().get(numVivienda - 1).setDescripcion(descripcion);
        esperar();
        if (fernanbnbAPP.modificaVivienda(new Vivienda(v.getId(), v.getTitulo(), descripcion, v.getLocalidad(), v.getProvincia(), v.getMaxOcupantes(), v.getPrecioNoche()), true))
          System.out.println("La descripción de su vivienda ha sido cambiado con éxito!!!!!!!!!!!!!!!");
        else System.out.println("No hemos podido cambiar la descripción");

      }
      pulseEnterContinuar();
    }
  }

  /**
   * Este método se encarga de modificar el precio por noche de la vivienda que el propietario haya seleccionado
   * Para que se modifique el precio por noche debe de haber registrada una vivienda
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP controller del sistema
   * @param p            el propietario, en el que modificaremos el precio por noche de una de sus viviendas
   * @author José Miguel Aranda Fernández
   */
  private static void cambiarPrecioNoche(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        Vivienda v = fernanbnbAPP.buscaViviendaId(p.getViviendas().get(numVivienda - 1).getId());
        System.out.print("Introduce el nuevo precio: ");
        double precioNoche = compruebaNumeroFlotante(S.nextLine());
        p.getViviendas().get(numVivienda - 1).setPrecioNoche(precioNoche);
        esperar();
        if (fernanbnbAPP.modificaVivienda(new Vivienda(v.getId(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getMaxOcupantes(), precioNoche), true))
          System.out.println("El precio de su vivienda ha sido cambiado con éxito!!!!!!!!!!!!!!!");
        else System.out.println("No hemos podido cambiar el precio por noche");
      }
      pulseEnterContinuar();
    }
  }

  /**
   * Este método se encarga de modificar el número máximo de ocupantes de la vivienda que el propietario haya seleccionado
   * Para que se modifique el número máximo de ocupantes debe de haber registrada una vivienda
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP controller del sistema
   * @param p            el propietario, en el que modificaremos el precio por noche de una de sus viviendas
   * @author José Miguel Aranda Fernández
   */
  private static void cambiarNumOcupantes(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        Vivienda v = fernanbnbAPP.buscaViviendaId(p.getViviendas().get(numVivienda - 1).getId());
        System.out.print("Introduce cual es el límite de ocupantes: ");
        int numOcupantes = compruebaNumeroEntero(S.nextLine());
        p.getViviendas().get(numVivienda - 1).setMaxOcupantes(numOcupantes);
        esperar();
        if (fernanbnbAPP.modificaVivienda(new Vivienda(v.getId(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), numOcupantes, v.getPrecioNoche()), true))
          System.out.println("El máximo de ocupantes de su vivienda ha sido cambiado con éxito!!!!!!!!!!!!!!!");
        else System.out.println("No hemos podido cambiar el número de ocupantes");

      }
      pulseEnterContinuar();
    }
  }

  /**
   * Este método se encarga de borrar una vivienda registrada por el usuario
   * Para que se borre una vivienda debe de estar registrada al menos una
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP el controlador el cual se hará cargo de eliminar la vivienda del propietario
   * @param p            el propietario, para obtener la vivienda que se desea eliminar
   * @author José Miguel Aranda Fernández
   * @author Adrián Lara Bonilla
   */
  private static void borrarVivienda(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        Vivienda viviendaTemp = p.getViviendas().get(numVivienda - 1);
        //le indicamos la vivienda que desea eleminiar
        if (fernanbnbAPP.deleteVivienda(p.getViviendas().get(numVivienda - 1))) {
          logVivienda(p, viviendaTemp.getId(), "Eliminación de vivienda");
          esperar();
          System.out.println("Vivienda eliminada con éxito!!!!");

        } else System.out.println("No se ha podido borrar la vivienda");
      }
      pulseEnterContinuar();
    }
  }

  /**
   * Muestra información sobre el propietario
   *
   * @param user es el usuario, propietario o administrador para obtener sus datos y poder representarlos
   * @author Adrián Lara Bonilla
   */
  private static void verPerfil(Object user) {
    if (user instanceof Usuario) System.out.println(user);
    if (user instanceof Propietario) System.out.println(user);
    if (user instanceof Administrador) System.out.println(user);
    pulseEnterContinuar();
  }

  /**
   * Este método añade un periodo de no disponibilidad (como si de una reserva se tratase) a la vivienda que el
   * propietario haya seleccionado
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param fernanbnbAPP el controlador que se hará cargo de añadir el periodo de no disponibilidad a la vivienda
   *                     que el propietario haya seleccionado
   * @param p            el propietario para obtener sus viviendas
   * @author José Miguel Aranda Fernández
   * @author Adrián Lara Bonilla
   */
  private static void establecerPeriodoNoDisponibilidad(FernanbnbAPP fernanbnbAPP, Propietario p) {
    if (p.getViviendas().size() == 0) System.out.println("Actualmente no tienes viviendas registradas");
    else {
      System.out.println(muestraDatosViviendas(p));
      int numVivienda = recogeNumeroVivienda(p);
      if (numVivienda != -1) {
        LocalDate fechaInicio = recogeFecha("Introduzca una fecha correcta, con el formato (dd-MM-yyyy): ");
        System.out.print("Introduce los días de duración: ");
        int noches = compruebaNumeroEntero(S.nextLine());
        esperar();
        if (fernanbnbAPP.addPeriodoNoDisponible(p.getViviendas().get(numVivienda - 1), fechaInicio, noches)) {
          System.out.println("Periodo de no disponibilidad creada entre: " + formateaFecha(fechaInicio) + " y " + formateaFecha(fechaInicio.plusDays(noches)));
          logNuevaReserva(p, p.getViviendas().get(numVivienda - 1).getId());
        } else System.out.println("Ya hay una reserva en esas fechas. Contacte con el usuario.");

      }
    }
    pulseEnterContinuar();
  }


  /**
   * Devuelve una cadena con los datos de las viviendas del propietario
   *
   * @param p el propietario para obtener las viviendas
   * @return la cadena con información sobre las viviendas
   * @author José Miguel Aranda Fernández
   */
  private static String muestraDatosViviendas(Propietario p) {
    String resultado = "";
    for (int i = 0; i < p.getViviendas().size(); i++) {
      resultado = resultado.concat(cartelNumeracionViviendasNormal(i + 1) + p.getViviendas().get(i).pintaDatos() + "\n");
    }
    return resultado;
  }

  /**
   * Muestra las reservas pendientes y finalizadas de las viviendas del propietario.
   * Si el propietario no tiene viviendas, muestra un mensaje indicándolo.
   * Muestra un menú con las opciones de ver las reservas pendientes, ver las reservas finalizadas o salir del menú.
   * En caso de que no haya reservas pendientes o finalizadas, muestra un mensaje indicándolo.
   *
   * @param fernanbnbAPP objeto de la clase FernanbnbAPP que contiene la información de la aplicación.
   * @param p            objeto de la clase Propietario que representa al propietario del que se quieren ver las reservas.
   * @author Adrián Lara Bonilla
   */
  private static void verReservasViviendas(FernanbnbAPP fernanbnbAPP, Propietario p) {
    ArrayList<Vivienda> viviendas = p.getViviendas();
    ArrayList<Reserva> reservasFinalizadas;
    ArrayList<Reserva> reservasPendientes;
    if (viviendas.isEmpty()) System.out.println("No tienes viviendas actualmente..");
    else {
      int op;
      do {
        p = (Propietario) fernanbnbAPP.actualizaPerfil(p);
        reservasFinalizadas = reservasFinalizadas(viviendas);
        reservasPendientes = reservasPendientes(viviendas);

        System.out.println(menuReservasPropietario(p.getNombre(), p.getViviendas().size(), fernanbnbAPP.numReservasPendientes(p)));
        System.out.print("Introduzca opción: ");
        op = compruebaNumeroEntero(S.nextLine());
        switch (op) {
          case 1 -> {
            if (reservasPendientes.isEmpty())
              System.out.println("No tiene reservas pendientes actualmente.");
            else {
              Collections.sort(reservasPendientes);
              for (Reserva r : reservasPendientes)
                System.out.println(r.pintaDatosParaPropietario(fernanbnbAPP.buscaUsuariobyId(r.getIdUsuario()), fernanbnbAPP.buscaViviendaId(r.getIdVivienda())));

            }
            pulseEnterContinuar();
          }
          case 2 -> {
            if (reservasFinalizadas.isEmpty())
              System.out.println("No tiene reservas finalizadas actualmente.");
            else {
              Collections.sort(reservasFinalizadas);
              for (Reserva r : reservasFinalizadas)
                System.out.println(r.pintaDatosParaPropietario(fernanbnbAPP.buscaUsuariobyId(r.getIdUsuario()), fernanbnbAPP.buscaViviendaId(r.getIdVivienda())));
            }
            pulseEnterContinuar();
          }
          case 3 -> op = salirVentanaMenu(op);
          default -> System.out.println("Error al introducir la opción");
        }
      } while (op != 3);
    }
    pulseEnterContinuar();
  }


  /**
   * Devuelve un ArrayList con todas las reservas finalizadas de un conjunto de viviendas dadas.
   * Si no hay reservas finalizadas en las viviendas dadas, devuelve un ArrayList vacío.
   *
   * @param viviendas objeto de la clase ArrayList que contiene las viviendas de las que se quiere obtener las reservas finalizadas.
   * @return objeto de la clase ArrayList que contiene todas las reservas finalizadas de las viviendas dadas, o un ArrayList vacío si no hay ninguna reserva finalizada.
   * @autor Adrian Lara Bonilla
   */
  public static ArrayList<Reserva> reservasFinalizadas(ArrayList<Vivienda> viviendas) {
    ArrayList<Reserva> reservasFinalizadasTemp = new ArrayList<>();
    for (Vivienda v : viviendas) { //Generamos el arraylist de las reservas finalizadas y pendientes
      if (!v.getReservas().isEmpty()) {
        for (Reserva r : v.getReservas()) {//Buscamos las reservas que hayan finalizado y la añadimos
          if (r.getFechaInicio().plusDays(r.getNoches()).compareTo(LocalDate.now()) <= 0)
            reservasFinalizadasTemp.add(r);
        }
      }
    }
    return reservasFinalizadasTemp;
  }

  /**
   * Devuelve un ArrayList con todas las reservas pendientes de un conjunto de viviendas dadas.
   * Si no hay reservas pendientes en las viviendas dadas, devuelve un ArrayList vacío.
   *
   * @param viviendas objeto de la clase ArrayList que contiene las viviendas de las que se quiere obtener las reservas pendientes.
   * @return objeto de la clase ArrayList que contiene todas las reservas pendientes de las viviendas dadas, o un ArrayList vacío si no hay ninguna reserva pendiente.
   * @autor Adrian Lara Bonilla
   */
  private static ArrayList<Reserva> reservasPendientes(ArrayList<Vivienda> viviendas) {
    ArrayList<Reserva> reservasPendientesTemp = new ArrayList<>();
    for (Vivienda v : viviendas) { //Generamos el arraylist de las reservas finalizadas y pendientes
      if (!v.getReservas().isEmpty()) {
        for (Reserva r : v.getReservas()) {//Buscamos las reservas pendientes y la añadimos
          if (r.getFechaInicio().plusDays(r.getNoches()).compareTo(LocalDate.now()) > 0)
            reservasPendientesTemp.add(r);
        }
      }
    }
    return reservasPendientesTemp;
  }

  /**
   * Muestra información sobre cada una de las viviendas del propietario
   * Si no tiene viviendas registradas, indicará al propietario que aún no tiene ninguna vivienda registrada
   *
   * @param p el propietario para obtener las viviendas
   * @author Adrián Lara Bonilla
   */
  private static void verViviendas(Propietario p) {
    ArrayList<Vivienda> viviendas = p.getViviendas();
    if (viviendas.isEmpty()) System.out.println("Actualmente no tienes viviendas. ");
    else {
      for (Vivienda v : viviendas) {
        System.out.println(v.pintaDatos());
      }
      enviarListadoViviendasCorreo(p, viviendas);

    }
    pulseEnterContinuar();
  }

  private static void enviarListadoViviendasCorreo(Propietario p, ArrayList<Vivienda> viviendas) {
    System.out.print("Quiere que le enviemos un listado de sus viviendas al correo? (S/n): ");
    if (s.nextLine().equalsIgnoreCase("S")) {//Si desea le enviamos un correo con el listado
      if (generaListadoViviendas(viviendas)) {
        System.out.println("Estamos generando el archivo, espere un momento...");
        Email.enviarMensajeExcelAdjuntoListadoViviendas(p.getEmail(), "Listado Viviendas",
                PlantillaMail.mensajeListadoViviendasPropietario(p.getNombre()));
        System.out.println("Listo! el listado ha sido enviado a su correo.");
      } else System.out.println("Ha ocurrido un error inesperado, vuelva a intentarlo mas tarde.");
    }
  }


  /**
   * Muestra información sobre todos los usuarios del sistema
   * Si no hay propietarios ni usuarios, indicará al administrador que no hay usuarios registrados
   * Los usuarios mostrados se dividirán en Propietarios y usuarios
   * Si no existen usuarios o propietarios no los muestran
   *
   * @param fernanbnbAPP el controlador encargado de obtener todos los usuarios y propietarios
   * @author Adrián Lara Bonilla
   * @author José Miguel Aranda Fernández
   */
  private static void verTodosUsuariosSistema(FernanbnbAPP fernanbnbAPP) {
    ArrayList<Propietario> propietarios = fernanbnbAPP.getPropietarios();
    ArrayList<Usuario> usuarios = fernanbnbAPP.getUsuarios();

    if (propietarios.isEmpty() && usuarios.isEmpty())
      System.out.println("No existen usuarios ni propietarios registrados en el sistema");
    else {
      System.out.println("╔═══════════════════════════════════╣ USUARIOS DEL SISTEMA ╠═════════════════════════════════════╗");
      for (Usuario u : usuarios) {
        System.out.println(pintaUsuarioParaAdmin(u));
      }
      System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
      System.out.println();
      System.out.println("╔═════════════════════════════════╣ PROPIETARIOS DEL SISTEMA ╠═══════════════════════════════════╗");
      for (Propietario p : propietarios) {
        System.out.println(pintaPropietarioParaAdmin(p));
      }
      System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
    pulseEnterContinuar();
  }

  /**
   * Método que muestra las viviendas en alquiler de la aplicación Fernanbnb.
   * Se muestran las viviendas de 5 en 5 y se permite al usuario salir del bucle cuando desee.
   *
   * @param fernanbnbAPP el controller de Fernanbnb.
   * @author Adrián Lara Bonilla
   */
  private static void verViviendasEnAlquiler(FernanbnbAPP fernanbnbAPP) throws InterruptedException {
    ArrayList<Vivienda> viviendas = fernanbnbAPP.getAllViviendas();
    if (viviendas.isEmpty()) {
      System.out.println("No hay viviendas registradas.");
    } else {
      int index = 0;
      int ultimaViviendaMostrada = -1; // Inicializar la variable
      boolean continuar = true;
      do {
        int limiteSuperior = Math.min(index + 5, viviendas.size());
        for (int i = ultimaViviendaMostrada + 1; i < limiteSuperior; i++) {//Muestra viviendas de 5 en 5
          System.out.println(viviendas.get(i).pintaResumen());
          ultimaViviendaMostrada = i; // Actualizar la variable
          Thread.sleep(500);
        }
        if (index < viviendas.size()) {
          System.out.print("Presione enter para mostrar las siguientes viviendas o escriba 'salir' para salir: ");
          String input = S.nextLine();
          if (input.equalsIgnoreCase("salir")) {
            continuar = false;
          }
        }
        index += 5;
      } while (index < viviendas.size() && continuar);
    }
    pulseEnterContinuar();
  }

  private static int menuInicioPropietario(Propietario p, FernanbnbAPP fernanbnbAPP) {
    System.out.println(Menus.menuPropietario(p.getNombre(), p.getViviendas().size(), fernanbnbAPP.numReservasPendientes(p), cargaLastSession(p.getId())));
    guardaLastSession(p.getId());
    System.out.print("Elige la opción que desee: ");
    return (compruebaNumeroEntero(S.nextLine()));
  }

  private static int menuInicioAdministrador(FernanbnbAPP fernanbnbAPP, Administrador adm) {
    System.out.println(Menus.menuAdministrador(adm.getNombre(), fernanbnbAPP.numeroUsuarios(), fernanbnbAPP.numeroViviendas(), fernanbnbAPP.numeroReservas(), cargaLastSession(adm.getId())));
    guardaLastSession(adm.getId());
    System.out.print("Elige la opción que desee: ");
    return compruebaNumeroEntero(S.nextLine());

  }

  private static int menuInicioUsuario(Usuario u) {
    System.out.println(Menus.menuUsuario(u.getNombre(), u.reservasPendientes().size(), cargaLastSession(u.getId())));
    guardaLastSession(u.getId());
    System.out.print("Elige la opción que desee: ");
    return (compruebaNumeroEntero(S.nextLine()));
  }

  private static int menuInicioInvitado() {
    System.out.println(Menus.menuInvitado());
    System.out.print("Elige la opción que desee: ");
    return (compruebaNumeroEntero(S.nextLine()));
  }

  private static void modificarReservas(FernanbnbAPP fernanbnbAPP, Usuario user) {
    int op;
    do {
      user = (Usuario) fernanbnbAPP.actualizaPerfil(user);
      System.out.println(Menus.modificaReserva(user.getNombre(), user.reservasPendientes().size()));
      System.out.print("Elige la opción que desee: ");
      op = Excepciones.compruebaNumeroEntero(S.nextLine());
      switch (op) {
        case 1 -> cambiarFechaReserva(fernanbnbAPP, user);
        case 2 -> cambiarDuracionReserva(fernanbnbAPP, user);
        case 3 -> cambiarNumeroOcupantes(fernanbnbAPP, user);
        case 4 -> borrarReserva(fernanbnbAPP, user);
        case 5 -> op = salirVentanaMenu(op);
        default -> System.out.println("Error al introducir la opción: ");
      }
    } while (op != 5);
  }

  /**
   * Permite cambiar el número de ocupantes de una reserva pendiente de un usuario.
   *
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @param user         el usuario al que pertenece la reserva pendiente.
   * @author Adrian Lara Bonilla
   */
  private static void cambiarNumeroOcupantes(FernanbnbAPP fernanbnbAPP, Usuario user) {
    if (user.reservasPendientes().size() == 0) System.out.println("Actualmente no tienes reservas registradas.");
    else {
      System.out.println(muestraDatosReservasPendientes(user, fernanbnbAPP));
      int numReserva = recogeNumeroReserva(user);
      if (numReserva != -1) {
        if (!user.reservasPendientes().get(numReserva - 1).getFechaInicio().minusDays(6).isAfter(LocalDate.now())) {
          System.out.println("Modificar su reserva es necesario hacerlo 7 previos a su fecha de inicio. Perdone las molestias.");
        } else {
          Reserva r = fernanbnbAPP.buscaReservabyId(user.reservasPendientes().get(numReserva - 1).getId());
          Vivienda v = fernanbnbAPP.buscaViviendaId(r.getIdVivienda());
          int numOcupantes;
          do {
            System.out.print("Introduce el nuevo numero de ocupantes: ");
            numOcupantes = compruebaNumeroEntero(S.nextLine());
            if (numOcupantes > v.getMaxOcupantes())
              System.out.println("El máximo número de ocupantes de esta vivienda son " + v.getMaxOcupantes() + ".");
            if (numOcupantes < 1) System.out.println("Debes introducir como minimo a un ocupante...");
          } while (numOcupantes > v.getMaxOcupantes() || numOcupantes < 1);

          System.out.println(fernanbnbAPP.modificaReserva(new Reserva(r.getId(), r.getIdVivienda(), r.getIdUsuario(),
                  r.getFechaInicio(), r.getNoches(), r.getPrecio(), numOcupantes)) ?
                  "El numero de ocupantes de tu reserva han sido cambiada con éxito!!!!!!!!!!!!" :
                  "Ha ocurrido un error y no se puede procesar su solicitud.");
        }
      }
    }
    pulseEnterContinuar();
  }

  /**
   * Permite cambiar la fecha de inicio de una reserva pendiente de un usuario.
   *
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @param user         el usuario al que pertenece la reserva pendiente.
   * @author Adrian Lara Bonilla
   */
  private static void cambiarFechaReserva(FernanbnbAPP fernanbnbAPP, Usuario user) {
    if (user.reservasPendientes().size() == 0) System.out.println("Actualmente no tienes reservas registradas.");
    else {
      System.out.println(muestraDatosReservasPendientes(user, fernanbnbAPP));
      int numReserva = recogeNumeroReserva(user);
      if (numReserva != -1) {
        if (!user.reservasPendientes().get(numReserva - 1).getFechaInicio().minusDays(6).isAfter(LocalDate.now())) {
          System.out.println("Modificar su reserva es necesario hacerlo 7 previos a su fecha de inicio. Perdone las molestias.");
        } else {
          Reserva r = fernanbnbAPP.buscaReservabyId(user.reservasPendientes().get(numReserva - 1).getId());
          LocalDate fechaInicio = recogeFecha("Introduzca una fecha con el formato (dd-MM-yyyy): ");
          System.out.println(fernanbnbAPP.modificaReserva(new Reserva(r.getId(), r.getIdVivienda(),
                  r.getIdUsuario(), fechaInicio, r.getNoches(), r.getPrecio(), r.getOcupantes())) ?
                  "La fecha de tu reserva ha sido cambiada con éxito!!!!!!!!!!!!!!!" :
                  "Lo sentimos, no se ha podido cambiar la fecha de su reserva ya que en esas fechas ya hay una reserva.");
        }
      }
    }
    pulseEnterContinuar();
  }

  /**
   * Permite cambiar la duración de una reserva pendiente de un usuario.
   *
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @param user         el usuario al que pertenece la reserva pendiente.
   * @author Adrian Lara Bonilla
   */
  private static void cambiarDuracionReserva(FernanbnbAPP fernanbnbAPP, Usuario user) {
    if (user.reservasPendientes().size() == 0) System.out.println("Actualmente no tienes reservas registradas.");
    else {
      System.out.println(muestraDatosReservasPendientes(user, fernanbnbAPP));
      int numReserva = recogeNumeroReserva(user);
      if (numReserva != -1) {
        if (!user.reservasPendientes().get(numReserva - 1).getFechaInicio().minusDays(6).isAfter(LocalDate.now())) {
          System.out.println("Modificar su reserva es necesario hacerlo 7 previos a su fecha de inicio. Perdone las molestias.");
        } else {
          Reserva r = fernanbnbAPP.buscaReservabyId(user.reservasPendientes().get(numReserva - 1).getId());
          System.out.print("Introduce el número de noches: ");
          int noches = compruebaNumeroEntero(S.nextLine());
          System.out.println(fernanbnbAPP.modificaReserva(new Reserva(r.getId(), r.getIdVivienda(), r.getIdUsuario(), r.getFechaInicio(), noches, r.getPrecio(), r.getOcupantes())) ? "La duración de tu reserva ha sido cambiada con éxito!!!!!!!!!!!!!!!" : "Lo sentimos, no se ha podido cambiar la fecha de su reserva ya que en esas fechas ya hay una reserva.");
        }
      }
    }
    pulseEnterContinuar();
  }

  /**
   * Devuelve una cadena de texto con los datos de las reservas pendientes de un usuario.
   *
   * @param u            el usuario del que se quieren obtener las reservas pendientes.
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @return una cadena de texto con los datos de las reservas pendientes del usuario.
   * @author Adrian Lara Bonilla
   */
  private static String muestraDatosReservasPendientes(Usuario u, FernanbnbAPP fernanbnbAPP) {
    String resultado = "";
    for (int i = 0; i < u.reservasPendientes().size(); i++) {
      resultado = resultado.concat(Menus.cartelNumeracionReservas(i + 1) + u.reservasPendientes().get(i).pintaDatos(fernanbnbAPP.buscaViviendaId(u.reservasPendientes().get(i).getIdVivienda())) + "\n");
    }
    return resultado;
  }

  /**
   * Borra la reserva que el usuario haya seleccionado
   * La reserva se borrará en el usuario como en la vivienda en la que
   * se realizó dicha reserva
   *
   * @param fernanbnbAPP el controlador encargado de borrar la reserva
   * @param user         el usuario para obtener de él sus reservas pendientes
   */
  private static void borrarReserva(FernanbnbAPP fernanbnbAPP, Usuario user) {
    ArrayList<Reserva> reservasPendientes = user.reservasPendientes();
    if (user.reservasPendientes().size() == 0) System.out.println("Actualmente no tienes reservas registradas.");
    else {
      for (int i = 0; i < reservasPendientes.size(); i++) {
        System.out.println(Menus.cartelNumeracionReservas(i + 1) + reservasPendientes.get(i).pintaDatos(fernanbnbAPP.buscaViviendaId(reservasPendientes.get(i).getIdVivienda())));
      }
      int numReserva;
      boolean salir = false;
      do {
        System.out.print("Seleccione el número de su reserva a borrar (Introduzca '-1' para salir): ");
        numReserva = compruebaNumeroEntero(S.nextLine());
        if (numReserva == -1) salir = true;
      } while (!salir && (numReserva > reservasPendientes.size() || numReserva < 1));
      if (salir) Utils.esperar();
      else {
        String decision;
        do {
          System.out.print("Estas seguro de borrar su reserva ?? (S/N): ");
          decision = S.nextLine().toUpperCase();
          switch (decision) {
            case "S" -> {
              esperar();
              if (!reservasPendientes.get(numReserva - 1).getFechaInicio().minusDays(6).isAfter(LocalDate.now())) {
                System.out.println("Para eliminar la reserva es necesario hacerlo 7 previos a su fecha de inicio. Perdone las molestias.");
              } else {
                if (fernanbnbAPP.cancelaReserva(reservasPendientes.get(numReserva - 1).getId(), true))
                  System.out.println("Reserva eliminada con éxito.");
                else System.out.println("Error al eliminar la reserva.");
              }
              salir = true;
            }
            case "N" -> esperar();
            default -> System.out.println("Error al introducir la opción.");
          }
        } while (!decision.equals("N") && !salir);
      }
    }
    pulseEnterContinuar();
  }

  /**
   * Muestra un menú para buscar alojamientos por localidad o por provincia.
   *
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @param user         el usuario que realiza la búsqueda.
   * @author Adrian Lara Bonilla
   */
  private static void buscarAlojamiento(FernanbnbAPP fernanbnbAPP, Usuario user) {
    user = (Usuario) fernanbnbAPP.actualizaPerfil(user);
    if (!fernanbnbAPP.compruebaTokenValidado(user)) {
      validarCuentaToken(user.getEmail(), user, fernanbnbAPP, false);
    } else {
      int op;
      do {
        System.out.println(Menus.menuBusquedaAlojamiento(user.getNombre(), user.reservasPendientes().size()));
        System.out.print("Elige la opción que desee: ");
        op = compruebaNumeroEntero(S.nextLine());
        switch (op) {
          case 1 -> busquedaPorLocalidad(fernanbnbAPP, user);
          case 2 -> busquedaPorProvincia(fernanbnbAPP, user);
          case 3 -> op = salirVentanaMenu(op);
          default -> System.out.println("Error al introducir la opción.");

        }
      } while (op != 3);
    }

  }

  /**
   * Muestra un menú para buscar alojamientos por localidad o por provincia en modo invitado, se pone null donde va el usuario
   * para indicar que estamos en modo invitado
   *
   * @param fernanbnbAPP la instancia de la aplicación Fernanbnb.
   * @author Adrian Lara Bonilla
   */
  private static void buscarAlojamientoInvitado(FernanbnbAPP fernanbnbAPP) {
    int op;
    do {
      System.out.println(Menus.menuBusquedaAlojamiento("Invitado", 0));
      System.out.print("Elige la opción que desee: ");
      op = compruebaNumeroEntero(S.nextLine());
      switch (op) {
        case 1 -> busquedaPorLocalidad(fernanbnbAPP, null);
        case 2 -> busquedaPorProvincia(fernanbnbAPP, null);
        case 3 -> op = salirVentanaMenu(op);
        default -> System.out.println("Error al introducir la opción.");

      }
    } while (op != 3);
  }


  /**
   * Método que pedirá al usuario el número de la vivienda que desee reservar, la reserva se guardará en el usuario y en la vivienda del propietario.
   * Si ha habido un error al realizarla, se indicará al usuario. Si el usuario esta a null significa que el usuario ha iniciado como invitado
   *
   * @param fernanbnbAPP               el controlador que se encargará de guardar la reserva en el usuario y en la vivienda del propietario
   * @param user                       el usuario en el que se guardará la reserva, si es null ha iniciado como invitado
   * @param viviendasConDisponibilidad la lista de viviendas disponibles en la cual el usuario escogerá una a su elección
   * @param fechaInicio                el inicio en el cuál se iniciará la reserva
   * @param noches                     la duración de la reserva
   * @param numOcupantes               el total de ocupantes que irán a la vivienda
   * @author Adrian Lara Bonilla
   * @author José Miguel Aranda Fernández
   */
  private static void registrarAlojamiento(FernanbnbAPP fernanbnbAPP, Usuario
          user, ArrayList<Vivienda> viviendasConDisponibilidad, ArrayList<Vivienda> viviendasSinDisponibilidad, LocalDate
                                                   fechaInicio, int noches, int numOcupantes) {
    int numVivienda;
    if (viviendasConDisponibilidad == null && viviendasSinDisponibilidad == null)
      System.out.println("No se han encontrado alojamientos con esos requisitos.");
    else {
      if (viviendasConDisponibilidad != null && viviendasConDisponibilidad.isEmpty())
        System.out.println("No hay alojamientos disponibles actualmente");
      else {
        if (viviendasConDisponibilidad != null) {
          System.out.println("╔═════════════════════════════════╣ RESERVAS DISPONIBLES ╠═══════════════════════════════════╗");

          int cont = 1;
          for (Vivienda v : viviendasConDisponibilidad) {
            System.out.println(cartelNumeracionViviendasNormal(cont) + v.pintaDatos());
            cont++;
          }
          System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");
        }
      }
      if (viviendasSinDisponibilidad != null && !viviendasSinDisponibilidad.isEmpty()) {
        System.out.println("╔═════════════════════════════════╣ RESERVAS NO DISPONIBLES ╠═══════════════════════════════════╗");
        for (Vivienda v : viviendasSinDisponibilidad) {
          System.out.println(v.pintaDatos());
        }
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════╝");
      }
      boolean salir = false;
      if (user == null) System.out.println("Para escoger la vivienda es necesario que se registre como usuario");
      else {
        // Si hay viviendas disponibles nos pedirá que escojamos una
        if (viviendasConDisponibilidad != null && !viviendasConDisponibilidad.isEmpty()) {
          do {
            System.out.print("Seleccione el número de la vivienda a reservar (-1 para salir): ");
            numVivienda = compruebaNumeroEntero(S.nextLine());
            if (numVivienda > viviendasConDisponibilidad.size() || numVivienda < 1 && numVivienda != -1)
              System.out.println("Número de vivienda incorrecta");
            if (numVivienda == -1) salir = true;
          } while (numVivienda > viviendasConDisponibilidad.size() || numVivienda < 1 && !salir);
          if (!salir) {
            numVivienda--;
            Utils.esperar();
            if (fernanbnbAPP.addReserva(user.getId(), viviendasConDisponibilidad.get(numVivienda), fechaInicio, noches, numOcupantes, true)) {
              System.out.println("Reserva realizada con éxito");
              logNuevaReserva(user, viviendasConDisponibilidad.get(numVivienda).getId());

              user = (Usuario) fernanbnbAPP.actualizaPerfil(user);
              // System.out.println(user.getReservas().get(user.getReservas().size() - 1).pintaDatos(viviendasConDisponibilidad.get(numVivienda)));
              System.out.println(user.getReservas().get(user.getReservas().size() - 1).pintaDatos(viviendasConDisponibilidad.get(numVivienda)));
            } else System.out.println("Ha habido un error al realizar la reserva");
          }
        }
      }
    }
    pulseEnterContinuar();
  }

  /**
   * Método que buscará las viviendas que coincidan con la ciudad.
   * Una vez obtenga las viviendas que coincidan llamará al método registrarAlojamiento para crear la reserva
   *
   * @param fernanbnbAPP el controlador que se encargará de obtener las viviendas cuya localidad coincida con la
   *                     introducida por el usuario
   * @param user         el usuario en el cual se guardará la reserva en el método registrarAlojamiento
   * @author Adrian Lara Bonilla
   * @author José Miguel Aranda Fernández
   */
  public static void busquedaPorLocalidad(FernanbnbAPP fernanbnbAPP, Usuario user) {
    String localidad;
    do {
      System.out.print("Introduzca la localidad para buscar: ");
      localidad = S.nextLine();
      if (localidad.length() < 2) System.out.println("La localidad buscada debe tener mínimo 2 caracteres.");
    } while (localidad.length() < 2);
    LocalDate fechaInicio;
    do {
      fechaInicio = recogeFecha("Introduzca la fecha de inicio de la reserva (dd-MM-yyyy): ");
      if (fechaInicio.isBefore(LocalDate.now()) || fechaInicio.isEqual(LocalDate.now()))
        System.out.println("Las reservas deben de realizarse minimo un dia después al actual. Vuelva a poner otra fecha.");
    } while (fechaInicio.isBefore(LocalDate.now()) || fechaInicio.isEqual(LocalDate.now()));
    System.out.print("Introduce el numero de noches: ");
    int noches = compruebaNumeroEntero(S.nextLine());
    System.out.print("Introduce los viajeros: ");
    int numOcupantes = compruebaNumeroEntero(S.nextLine());
    ArrayList<Vivienda> viviendasConDisponibilidad = fernanbnbAPP.buscaAlojamiento(localidad, "", fechaInicio, noches, numOcupantes, true);
    ArrayList<Vivienda> viviendasSinDisponibilidad = fernanbnbAPP.buscaAlojamientosSinDisponibilidad(localidad, "", fechaInicio, noches, numOcupantes, true);
    registrarAlojamiento(fernanbnbAPP, user, viviendasConDisponibilidad, viviendasSinDisponibilidad, fechaInicio, noches, numOcupantes);
  }

  /**
   * Método que buscará las viviendas que coincidan con la provincia.
   * Una vez obtenga las viviendas que coincidan llamará al método registrarAlojamiento para crear la reserva
   *
   * @param fernanbnbAPP el controlador que se encargará de obtener las viviendas cuya provincia coincida con la
   *                     intoducida por el usuario
   * @param user         el usuario en el cual se guardará la reserva en el método registrarAlojamiento
   * @author Adrian Lara Bonilla
   * @author José Miguel Aranda Fernández
   */
  public static void busquedaPorProvincia(FernanbnbAPP fernanbnbAPP, Usuario user) {
    String provincia;
    do {
      System.out.print("Introduzca la provincia para buscar: ");
      provincia = S.nextLine();
      if (provincia.length() < 2) System.out.println("La provincia buscada debe tener mínimo 2 caracteres.");
    } while (provincia.length() < 2);
    LocalDate fechaInicio;
    do {
      fechaInicio = recogeFecha("Introduzca la fecha de inicio de la reserva (dd-MM-yyyy): ");
      if (fechaInicio.isBefore(LocalDate.now()) || fechaInicio.isEqual(LocalDate.now()))
        System.out.println("Las reservas deben de realizarse minimo un dia después al actual. Vuelva a poner otra fecha.");
    } while (fechaInicio.isBefore(LocalDate.now()) || fechaInicio.isEqual(LocalDate.now()));
    System.out.print("Introduce el numero de noches: ");
    int noches = compruebaNumeroEntero(S.nextLine());
    System.out.print("Introduce los viajeros: ");
    int numOcupantes = compruebaNumeroEntero(S.nextLine());
    ArrayList<Vivienda> viviendasDisponibles = fernanbnbAPP.buscaAlojamiento("", provincia, fechaInicio, noches, numOcupantes, false);
    ArrayList<Vivienda> viviendasNoDisponibles = fernanbnbAPP.buscaAlojamientosSinDisponibilidad("", provincia, fechaInicio, noches, numOcupantes, false);
    registrarAlojamiento(fernanbnbAPP, user, viviendasDisponibles, viviendasNoDisponibles, fechaInicio, noches, numOcupantes);
  }

  /**
   * Muestra las reservas del usuario y permite ver las pendientes y las finalizadas.
   *
   * @param fernanbnbAPP objeto FernanbnbAPP que contiene la información de la aplicación
   * @param user         objeto Usuario cuyas reservas se van a mostrar
   * @author Adrian Lara Bonilla
   */
  private static void verReservasUsuario(FernanbnbAPP fernanbnbAPP, Usuario user) {
    user = (Usuario) fernanbnbAPP.actualizaPerfil(user);
    int op;
    do {
      System.out.println(menuReservasUsuario(user.getNombre(), user.getReservas().size()));
      System.out.print("Introduzca opción: ");
      op = compruebaNumeroEntero(S.nextLine());
      switch (op) {
        case 1 -> {
          ArrayList<Reserva> reservasPendientes = user.reservasPendientes();
          if (reservasPendientes.isEmpty()) System.out.println("No hay reservas pendientes actualmente");
          else {
            Collections.sort(reservasPendientes);
            int cont = 1;

            for (Reserva r : reservasPendientes) {
              System.out.println(cartelNumeracionReservas(cont) + r.pintaDatos(fernanbnbAPP.buscaViviendaId(r.getIdVivienda())));
              cont++;
            }
            enviarResumenReservaPdfUsuario(reservasPendientes, fernanbnbAPP, user);
          }
          pulseEnterContinuar();
        }
        case 2 -> {
          ArrayList<Reserva> reservasHistorico = user.reservasHistorico();
          if (reservasHistorico.isEmpty()) System.out.println("No hay reservas finalizadas actualmente");
          else {
            Collections.sort(reservasHistorico);
            int cont = 1;
            for (Reserva r : reservasHistorico) {
              System.out.println(cartelNumeracionReservas(cont) + r.pintaDatos(fernanbnbAPP.buscaViviendaId(r.getIdVivienda())));
              cont++;
            }
            enviarResumenReservaPdfUsuario(reservasHistorico, fernanbnbAPP, user);
          }
          pulseEnterContinuar();
        }
        case 3 -> op = salirVentanaMenu(op);
        default -> {
          System.out.println("Error al introducir la opción");
          pulseEnterContinuar();
        }
      }
    } while (op != 3);
  }

  private static void enviarResumenReservaPdfUsuario(ArrayList<Reserva> listaReservas, FernanbnbAPP
          fernanbnbAPP, Usuario user) {
    System.out.print("Desea que le enviemos un resumen de su reserva al correo? (S/n): ");
    if (s.nextLine().equalsIgnoreCase("S")) {
      System.out.print("Escribe el numero de la reserva que quiere que le enviemos el resumen: ");
      int numReserva;
      do {
        numReserva = Excepciones.compruebaNumeroEntero(s.nextLine()) - 1;
        if (numReserva < 0 || numReserva >= listaReservas.size()) {
          System.out.println("Usted solo tiene " + listaReservas.size() + " reservas. ");
          System.out.print("Escribe el numero de la reserva: ");
        }
      } while (numReserva < 0 || numReserva >= listaReservas.size());
      Reserva r = listaReservas.get(numReserva);//Reserva del resumen
      Vivienda v = fernanbnbAPP.buscaViviendaId(r.getIdVivienda()); //Vivienda de la reserva
      Propietario p = fernanbnbAPP.buscaPropietariobyVivienda(v); //Propietario de la vivienda
      System.out.println("Estamos generando su resumen para enviarlo al correo...");
      esperar();
      new ResumenReservaPDF(r.getId(), user.getNombre(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), p.getEmail(),
              r.getFechaInicio(), r.getNoches(), r.getOcupantes(), v.getTitulo()).generarResumenReserva();
      Email.enviarMensajePdfAdjunto(user.getEmail(), "Resumen de su reserva", PlantillaMail.mensajeResumenReservaUsuario(user.getNombre()));
      System.out.println("Correo enviado con exito!!!!!!");
    }
  }
}