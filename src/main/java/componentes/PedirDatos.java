package componentes;

import controller.FernanbnbAPP;
import models.Propietario;
import models.Usuario;

import java.time.LocalDate;
import java.util.Scanner;

import static componentes.Excepciones.compruebaFechaEscrita;
import static componentes.Excepciones.compruebaNumeroEntero;
import static componentes.Utils.*;


public class PedirDatos {
  public static Scanner S = new Scanner(System.in);

  /**
   * Solicita al usuario que introduzca su nombre y lo retorna como una cadena de caracteres.
   *
   * @return una cadena de caracteres que contiene el nombre introducido por el usuario.
   * @author Adrián Lara Bonilla
   */
  public static String recogeNombreUsuario() {

    System.out.print("Escriba su nombre: ");
    return S.nextLine().trim();
  }

  /**
   * Solicita al usuario que introduzca una contraseña y la confirme escribiéndola dos veces. Retorna la contraseña
   * introducida como una cadena de caracteres.
   * <p>
   * * @return una cadena de caracteres que contiene la contraseña introducida por el usuario.
   *
   * @author Adrián Lara Bonilla
   */
  public static String recogeClaveUsuario() {
    String clave, claveRepetida;
    do {
      System.out.print("Escriba su contraseña: ");
      clave = S.nextLine();
      System.out.print("Vuelva a escribir su contraseña: ");
      claveRepetida = S.nextLine();
    } while (!clave.equals(claveRepetida));
    return clave;
  }

  /**
   * Solicita al usuario que introduzca su dirección de correo electrónico y comprueba si es válida y si está disponible
   * en la instancia de FernanbnbAPP. Retorna la dirección de correo electrónico introducida como una cadena de caracteres.
   *
   * @param fernanbnbAPP es el controller de la clase FernanbnbAPP que está siendo utilizada.
   * @return una cadena de caracteres que contiene la dirección de correo electrónico introducida por el usuario.
   * @author Adrián Lara Bonilla
   */
  public static String recogeEmailUsuario(FernanbnbAPP fernanbnbAPP, String mensaje) {
    String email;
    do {
      System.out.print("Escriba su " + mensaje + ": ");
      email = S.nextLine().toLowerCase().trim();
      if (!fernanbnbAPP.compruebaCorreoDisponible(email)) System.out.println("El email ya esta en uso.");
      if (!emailValido(email)) System.out.println("El email no es válido.");
    } while (!fernanbnbAPP.compruebaCorreoDisponible(email) || !emailValido(email));
    return email;
  }

  /**
   * Esta función recoge el nombre de una localidad o provincia a partir de un mensaje pasado como parámetro.
   * Se realiza un bucle do-while para comprobar que el valor introducido no contenga números.
   * Si se detectan números en la entrada, se mostrará un mensaje de error y se pedirá al usuario que vuelva a introducir el dato.
   * Una vez validado el dato, se devolverá como resultado de la función.
   *
   * @param mensaje Mensaje que se mostrará al usuario para solicitar la entrada de datos.
   * @return Devuelve el valor introducido por el usuario tras comprobar que no contiene números.
   * @author José Miguel Aranda Fernández
   */
  public static String recogeNombreLocalidadOProvincia(String mensaje) {
    String input;
    do {
      System.out.print("Introduce " + mensaje + " de su vivienda: ");
      input = S.nextLine();
      if (input.contains("1234567890")) System.out.println("No se admiten valores numéricos.");
      if (input.length() < 4) System.out.println("");
    } while (input.contains("1234567890"));
    return input;
  }

  /**
   * Este método se encarga de validar la cuenta del usuario a través de un token enviado al correo electrónico registrado.
   * Si el usuario desea validar su cuenta, se enviará un correo con el token de activación y se solicitará que lo ingrese.
   * Si el token es válido, la cuenta del usuario será validada y podrá acceder a todas las funcionalidades de la APP.
   * Si el usuario decide no verificar su cuenta, no podrá acceder a todas las funcionalidades de la APP.
   *
   * @param email        el correo electrónico del usuario
   * @param user         el objeto usuario (puede ser de tipo Usuario, Propietario o Administrador
   * @param fernanbnbAPP la instancia de FernanbnbAPP que se está ejecutando
   * @author Adrian Lara Bonilla
   */
  public static void validarCuentaToken(String email, Object user, FernanbnbAPP fernanbnbAPP, boolean usuarioRegistrado) {
    String tokenTeclado = "";
    System.out.print("Para tener todas las funcionalidades de nuestra APP necesita validar su cuenta. ¿Quiere " +
            "validarla? (S/N): ");
    switch (S.nextLine().toUpperCase()) {
      case "S" -> {
        if (!usuarioRegistrado) {
          esperar();
          fernanbnbAPP.restauraToken(user);
        }
        boolean validado;
        do {
          System.out.print("Por favor introduzca el token de 6 caracteres enviado al correo del registro: ");
          tokenTeclado = S.nextLine();
          validado = fernanbnbAPP.validaTokenUsuario(tokenTeclado, user);
          if (!validado)
            System.out.println("Si desea salir sin verificar su cuenta pulse 0. No podrá acceder a nuestras funcionalidades " +
                    "completas hasta que valide sus datos.");
          System.out.println();
        } while (!validado && !tokenTeclado.equals("0"));

        if (!validado) {
          System.out.println("Su cuenta sigue sin estar verificada, no podrá acceder a nuestras funcionalidades " +
                  "completas hasta que la verifique." +
                  "Discupe las molestias y vuelva a intentarlo mas tarde.");
        } else {
          System.out.println("Su cuenta ha sido verificada! ");
          pulseEnterContinuar();
        }
      }
      default -> {
        System.out.println("Su cuenta no está verificada, debera verificarla para usar todas las funciones.");
        pulseEnterContinuar();
      }
    }
  }

  /**
   * Solicita una fecha de formato (dd-MM-yyyy) que nos la pedirá con un mensaje que pasemos por parámetro
   * Este método devolverá la fecha con el formato estricto, es decir, encaso de no poner el formato
   * correcto nos la volverá a pedir tantas veces hasta que el usuario la introduzca correctamente
   *
   * @param mensaje el mensaje que mostrará al usuario antes de introducir la fecha
   * @return la fecha en formato (dd-MM-yyyy)
   * @author José Miguel Aranda Fernández
   */
  public static LocalDate recogeFecha(String mensaje) {
    String fecha = "";
    System.out.print(mensaje);
    fecha = S.nextLine().replace("/", "-");
    return compruebaFechaEscrita(fecha);
  }

  /**
   * Este método devuelve un número que esté dentro del rango del número total de viviendas que posee el propietario.
   * Este método a su vez comprueba que el valor introducido sea un número
   *
   * @param p el propietario para obtener el número total de viviendas
   * @return un número comprendido entre el 1 y el número total de viviendas
   * @author José Miguel Aranda Fernández
   */
  public static int recogeNumeroVivienda(Propietario p) {
    int numVivienda;
    boolean salir = false;
    do {
      System.out.print("Seleccione el número de la vivienda (-1 para salir): ");
      numVivienda = compruebaNumeroEntero(S.nextLine());
      if (numVivienda > p.getViviendas().size() || numVivienda < 1 && numVivienda != -1)
        System.out.println("Número de vivienda incorrecta");
      if (numVivienda == -1) salir = true;
    } while (numVivienda > p.getViviendas().size() || numVivienda < 1 && !salir);
    if (numVivienda == -1) return -1;
    return numVivienda;
  }

  /**
   * Recoge el número de reserva de entre las pendientes de un usuario, validando que el número elegido sea correcto.
   *
   * @param u Usuario del que se recogerá el número de reserva.
   * @return El número de reserva seleccionado por el usuario, restando 1 para ajustarse a la posición del ArrayList
   * de reservas pendientes.
   * @author José Miguel Aranda Fernández
   */
  public static int recogeNumeroReserva(Usuario u) {
    int numReserva;
    boolean salir = false;
    do {
      System.out.print("Seleccione el número de la reserva (-1 para salir): ");
      numReserva = compruebaNumeroEntero(S.nextLine());
      if (numReserva > u.reservasPendientes().size() || numReserva < 1 && numReserva != -1)
        System.out.println("Número de reserva incorrecta");
      if (numReserva == -1) salir = true;
    } while (numReserva > u.reservasPendientes().size() || numReserva < 1 && !salir);
    return numReserva;
  }
}



