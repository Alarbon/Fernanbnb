package componentes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Excepciones {
  private static Scanner s = new Scanner(System.in);

  /**
   * Comprueba si una cadena de caracteres representa un número entero válido y devuelve su valor como un entero.
   * Si la cadena no representa un número entero válido, solicita al usuario que introduzca un nuevo valor hasta que se proporcione uno válido.
   *
   * @param numEscrito La cadena de caracteres que se va a comprobar.
   * @return El valor numérico representado por la cadena de caracteres proporcionada.
   * @autor Adrián Lara Bonilla
   */

  public static int compruebaNumeroEntero(String numEscrito) {
    boolean esComprobado = false;
    int num = 0;
    do {
      try {
        num = Integer.parseInt(numEscrito);
        esComprobado = true;
      } catch (NumberFormatException e) {
        System.out.print("Hay que introducir un numero sin decimales: ");
        numEscrito = s.nextLine();
      }
    } while (!esComprobado);
    return num;
  }

  /**
   * Comprueba si una cadena de caracteres representa un número flotante válido y devuelve su valor como un número de tipo double.
   * Si la cadena no representa un número flotante válido, solicita al usuario que introduzca un nuevo valor hasta que se proporcione uno válido.
   *
   * @param numEscrito La cadena de caracteres que se va a comprobar.
   * @return El valor numérico representado por la cadena de caracteres proporcionada.
   * @autor Adrián Lara Bonilla
   */
  public static double compruebaNumeroFlotante(String numEscrito) {
    boolean esComprobado = false;
    double num = 0;
    do {
      try {
        num = Double.parseDouble(numEscrito);
        esComprobado = true;
      } catch (Exception e) {
        System.out.print("Hay que introducir un numero: ");
        numEscrito = s.nextLine();
      }
    } while (!esComprobado);
    return num;
  }

  /**
   * Comprueba si una cadena de caracteres representa una fecha válida en formato "dd-MM-yyyy" y devuelve la fecha como un objeto de tipo LocalDate.
   * Si la cadena no representa una fecha válida, solicita al usuario que introduzca una nueva fecha hasta que se proporcione una fecha válida en el formato especificado.
   *
   * @param fechaEscrita La cadena de caracteres que se va a comprobar.
   * @return El objeto LocalDate que representa la fecha proporcionada en formato "dd-MM-yyyy".
   * @autor Adrián Lara Bonilla
   */

  public static LocalDate compruebaFechaEscrita(String fechaEscrita) {
    boolean esComprobado = false;
    LocalDate fechaCorrecta = null;
    do {
      try {
        fechaCorrecta = LocalDate.parse(fechaEscrita, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        esComprobado = true;
      } catch (DateTimeParseException e) {
        System.out.print("Introduzca una fecha correcta, con el formato (dd-MM-yyyy): ");
        fechaEscrita = s.nextLine();
      }
    } while (!esComprobado);
    return fechaCorrecta;
  }
}
