package componentes;


import models.Propietario;
import models.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  public static Scanner s = new Scanner(System.in);

  public static void esperar() {//
    System.out.print("Espere un momento");
    for (int i = 0; i < 3; i++) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.print(".");
    }
    System.out.println();
    System.out.println();
  }

  public static void cerrarSesion() {
    System.out.print("Cerrando sesión");
    for (int i = 0; i < 3; i++) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.print(".");
    }
    System.out.println();
  }

  public static void inicioSesion() {
    System.out.print("Iniciando sesión");
    for (int i = 0; i < 3; i++) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      System.out.print(".");
    }
    System.out.println();
    System.out.println();
  }

  public static void pulseEnterContinuar() {
    System.out.println("Pulse enter para continuar...");
    s.nextLine();
  }

  /**
   * Quita las tildes de un texto.
   *
   * @param texto el texto del cual se quitarán las tildes.
   * @return una cadena de texto sin tildes.
   * @autor Adrián Lara Bonilla
   */
  public static String quitaTildes(String texto) {
    texto = texto.replace('á', 'a');
    texto = texto.replace('é', 'e');
    texto = texto.replace('í', 'i');
    texto = texto.replace('ó', 'o');
    texto = texto.replace('ú', 'u');
    texto = texto.replace('Á', 'A');
    texto = texto.replace('É', 'E');
    texto = texto.replace('Í', 'I');
    texto = texto.replace('Ó', 'O');
    texto = texto.replace('Ú', 'U');
    return texto;
  }

  /**
   * Verifica si una dirección de correo electrónico es válida utilizando expresiones regulares.
   *
   * @param email la dirección de correo electrónico que se va a validar.
   * @return true si la dirección de correo electrónico es válida, false si no lo es.
   * @autor Adrián Lara Bonilla
   */
  public static boolean emailValido(String email) {
    if (email == null) return false;
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    Pattern pattern = Pattern.compile(emailRegex);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  /**
   * Codifica una clave en una funcion hash.
   *
   * @param clave la clave que se desea codificar.
   * @return la clave codificada
   * @autor Adrián Lara Bonilla
   */
  public static String encriptarClave(String clave) {
    try {
      // Crear una instancia de MessageDigest con el algoritmo SHA-256
      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      // Convertir la clave en un arreglo de bytes
      byte[] bytes = clave.getBytes(StandardCharsets.UTF_8);

      // Calcular el valor de hash de la clave y convertirlo a una cadena hexadecimal
      byte[] hash = digest.digest(bytes);
      StringBuilder sb = new StringBuilder();
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();

    } catch (NoSuchAlgorithmException ex) {
      // Manejar una posible excepción si el algoritmo no está disponible
      return null;
    }
  }

  /**
   * Verifica si la clave introducida por el usuario coincide con la clave almacenada en el sistema.
   *
   * @param claveIntroducida la clave introducida por el usuario.
   * @param claveAlmacenada  la clave almacenada en el sistema, codificada con el algoritmo SHA-256.
   * @return true si la clave introducida coincide con la clave almacenada; false en caso contrario.
   * @autor Adrián Lara Bonilla
   */
  public static boolean verificarClave(String claveIntroducida, String claveAlmacenada) {
    String hashIntroducido = encriptarClave(claveIntroducida);
    return hashIntroducido.equals(claveAlmacenada);
  }

  public static String formateaFecha(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return date.format(formatter).replace("-", "/");
  }

  public static String formateaFechaHora(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return date.format(formatter).replace("-", "/");
  }
  public static String formateaHora(LocalDateTime date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return date.format(formatter).replace("-", "/");
  }

  public static String pintaUsuarioParaAdmin(Usuario u) {
    return String.format("""
                                 
            \t╔══════════════════════════════╣ ID USUARIO %07d ╠═══════════════════════════════╗  
            \t  ---------------------------------------------------------------------------------   
            \t  NOMBRE: %s (%s)                              
            \t  ---------------------------------------------------------------------------------   
            \t╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                           
            """, u.getId(), u.getNombre(), u.getEmail());
  }

  public static String pintaPropietarioParaAdmin(Propietario p) {
    return String.format("""
                                 
            \t╔════════════════════════════╣ ID PROPIETARIO %07d ╠═════════════════════════════╗  
            \t  ---------------------------------------------------------------------------------   
            \t  NOMBRE: %s (%s)                              
            \t  ---------------------------------------------------------------------------------   
            \t╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                           
            """, p.getId(), p.getNombre(), p.getEmail());
  }

}
