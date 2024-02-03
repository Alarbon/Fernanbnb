package models;

import java.io.Serializable;
import java.util.ArrayList;

import static componentes.Utils.encriptarClave;
import static componentes.Utils.verificarClave;

public class Propietario implements Serializable {
  private int id;
  private String nombre;
  private String clave;
  private String email;
  private String token;
  private boolean verificado;
  private ArrayList<Vivienda> viviendas;

  /**
   * Crea un nuevo objeto Propietario con los parámetros especificados.
   *
   * @param id         el identificador del propietario.
   * @param nombre     el nombre del propietario.
   * @param clave      la clave del propietario.
   * @param email      el correo electrónico del propietario.
   * @param token      es un token de activación para validar la cuenta.
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @autor Adrián Lara Bonilla
   */
  public Propietario(int id, String nombre, String clave, String email, String token, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = encriptarClave(clave);
    this.email = email;
    this.token = token;
    this.verificado = verificado;
    viviendas = new ArrayList<>();
  }

  /**
   * Constructor copia público de la clase Propietario.
   * Crea una nueva instancia de Propietario con datos obtenidos desde nuestra bbdd, al no pasarle el token significa
   * entiende que la contraseña que le pasamos es la codificada.
   *
   * @param id         el identificador del usuario
   * @param nombre     el nombre del usuario
   * @param clave      la contraseña del usuario
   * @param email      el correo electrónico del usuario
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @author Adrián Lara Bonilla
   */
  public Propietario(int id, String nombre, String clave, String email, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = clave;
    this.email = email;
    this.token = "";
    this.verificado = verificado;
    viviendas = new ArrayList<>();
  }



  /*Getters & Setters*/

  public int getId() {
    return id;
  }


  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isVerificado() {
    return verificado;
  }


  public ArrayList<Vivienda> getViviendas() {
    return viviendas;
  }

  public void setViviendas(ArrayList<Vivienda> viviendas) {
    this.viviendas = viviendas;
  }
  /*Otros Métodos*/

  /**
   * Comprueba si las credenciales de inicio de sesión son válidas para este propietario.
   *
   * @param emailTemp el correo electrónico que el usuario propietario ha escrito.
   * @param claveTemp la clave que el usuario propietario ha escrito.
   * @return true si las credenciales son válidas, false en caso contrario.
   * @autor Adrián Lara Bonilla
   */
  public boolean login(String emailTemp, String claveTemp) {
    return this.email.equals(emailTemp) && verificarClave(claveTemp, this.clave);
  }

  /**
   * Agrega una vivienda a la lista de viviendas asociadas a este propietario.
   *
   * @param vivienda es un objeto de la clase Vivienda que se va a agregar.
   * @autor Adrián Lara Bonilla
   */
  public void addVivienda(Vivienda vivienda) {
    this.viviendas.add(vivienda);
  }

  /**
   * Devuelve una lista con todas las reservas realizadas para todas las viviendas disponibles del propietario.
   *
   * @return una lista con todas las reservas realizadas para todas las viviendas disponibles
   * @autor Adrián Lara Bonilla
   */
  public ArrayList<Reserva> getReservas() {
    ArrayList<Reserva> reservas = new ArrayList<>();
    for (Vivienda v : viviendas) {
      for (Reserva r : v.getReservas()) {
        reservas.add(r);
      }
    }
    return reservas;
  }

  /**
   * Valida un token dado comparándolo con el token almacenado en la instancia de la clase.
   * Si el token dado es igual al token almacenado, establece el estado de verificación en verdadero y devuelve verdadero.
   * Si el token dado es diferente al token almacenado, devuelve falso sin cambiar el estado de verificación.
   *
   * @param token El token a validar.
   * @return True si el token es válido y ha sido verificado correctamente, de lo contrario, falso.
   * @autor Adrián Lara Bonilla
   */
  public boolean validaToken(String token) {
    if (this.token.equals(token)) {
      this.verificado = true;
      return true;
    }
    return false;
  }


  @Override
  public String toString() {
    return String.format("""
                                         
                    \t╔══════════════════════════════╣ ID PROPIETARIO %07d ╠═══════════════════════════════╗  
                    \t  -------------------------------------------------------------------------------------  
                    \t  NOMBRE: %s                               
                    \t  -------------------------------------------------------------------------------------   
                    \t  CLAVE: %s    
                    \t  -------------------------------------------------------------------------------------   
                    \t  EMAIL: %s                                         
                    \t  -------------------------------------------------------------------------------------    
                    \t  NÚMERO DE VIVIENDAS:%d                                        
                    \t  -------------------------------------------------------------------------------------    
                    \t╚═══════════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, nombre, encriptarClave(clave), email, viviendas.size());
  }
}



