package models;

import java.io.Serializable;

import static componentes.Utils.encriptarClave;
import static componentes.Utils.verificarClave;

public class Administrador implements Serializable {

  private int id;
  private String nombre;
  private String clave;
  private String email;
  private String token;
  private boolean verificado;

  /**
   * Crea un nuevo objeto Administrador con los parámetros especificados.
   *
   * @param id         el identificador del administrador.
   * @param nombre     el nombre del administrador.
   * @param clave      la clave del administrador.
   * @param email      el correo electrónico del administrador.
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @autor Adrián Lara Bonilla
   */

  public Administrador(int id, String nombre, String clave, String email, String token, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = encriptarClave(clave);
    this.email = email;
    this.token = token;
    this.verificado = verificado;
  }
  /**
   * Constructor copia público de la clase Administrador.
   * Crea una nueva instancia de Administrador con datos obtenidos desde nuestra bbdd, al no pasarle el token significa
   * entiende que la contraseña que le pasamos es la codificada.
   *
   * @param id         el identificador del usuario
   * @param nombre     el nombre del usuario
   * @param clave      la contraseña del usuario
   * @param email      el correo electrónico del usuario
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @author Adrián Lara Bonilla
   */
  public Administrador(int id, String nombre, String clave, String email, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = clave;
    this.email = email;
    this.token = "";
    this.verificado = verificado;
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


  /*Otros Métodos*/

  /**
   * Comprueba si las credenciales de inicio de sesión son válidas para este Administrador.
   *
   * @param emailTemp el correo electrónico que el usuario administrador ha escrito.
   * @param claveTemp la clave que el usuario administrador ha escrito.
   * @return true si las credenciales son válidas, false en caso contrario.
   * @autor Adrián Lara Bonilla
   */
  public boolean login(String emailTemp, String claveTemp) {
    return this.email.equals(emailTemp) && verificarClave(claveTemp,this.clave);
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
                                         
                    ╔══════════════════════════════╣ ID ADMINISTRADOR %07d ╠═══════════════════════════════╗  
                      ---------------------------------------------------------------------------------------   
                      NOMBRE: %s                               
                      ---------------------------------------------------------------------------------------   
                      CLAVE: %s    
                      ---------------------------------------------------------------------------------------   
                      EMAIL: %s                                         
                      ---------------------------------------------------------------------------------------    
                    ╚═════════════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, nombre, encriptarClave(clave), email);
  }
}


