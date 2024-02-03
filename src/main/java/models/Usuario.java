package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static componentes.Utils.encriptarClave;
import static componentes.Utils.verificarClave;


public class Usuario implements Serializable {
  //Atributos
  private int id;
  private String nombre;
  private String clave;
  private String email;
  private String token;
  private boolean verificado;
  private ArrayList<Reserva> reservas;

  //Constructor

  /**
   * Constructor público de la clase Usuario.
   * Crea una nueva instancia de Usuario con los siguientes atributos:
   *
   * @param id         el identificador del usuario
   * @param nombre     el nombre del usuario
   * @param clave      la contraseña del usuario
   * @param email      el correo electrónico del usuario
   * @param token      es un token de activación para validar la cuenta
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @author José Miguel Aranda Fernández
   */
  public Usuario(int id, String nombre, String clave, String email, String token, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = encriptarClave(clave);
    this.email = email;
    this.token = token;
    this.verificado = verificado;
    reservas = new ArrayList();
  }

  /**
   * Constructor copia público de la clase Usuario.
   * Crea una nueva instancia de Usuario con datos obtenidos desde nuestra bbdd, al no pasarle el token significa
   * entiende que la contraseña que le pasamos es la codificada.
   *
   * @param id         el identificador del usuario
   * @param nombre     el nombre del usuario
   * @param clave      la contraseña del usuario
   * @param email      el correo electrónico del usuario
   * @param verificado bandera que identificará si el usuario está validado o no.
   * @author Adrián Lara Bonilla
   */
  public Usuario(int id, String nombre, String clave, String email, boolean verificado) {
    this.id = id;
    this.nombre = nombre;
    this.clave = clave;
    this.email = email;
    this.token = "";
    this.verificado = verificado;
    reservas = new ArrayList();
  }


  //Getter and Setters


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


  public ArrayList<Reserva> getReservas() {
    return reservas;
  }

  public void setReservas(ArrayList<Reserva> reservas) {
    this.reservas = reservas;
  }

  /**
   * Comprueba si las credenciales de inicio de sesión son válidas para este Usuario.
   *
   * @param emailTemp el correo electrónico que el usuario ha escrito.
   * @param claveTemp la clave que el usuario ha escrito.
   * @return true si las credenciales son válidas, false en caso contrario.
   * @autor Adrián Lara Bonilla
   */
  public boolean login(String emailTemp, String claveTemp) {
    return this.email.equals(emailTemp) &&  verificarClave(claveTemp,this.clave);
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


  /**
   * Retorna una lista de las reservas que aún no han finalizado.
   *
   * @return una lista de objetos de tipo Reserva que aún no han finalizado.
   * @author José Miguel Aranda Fernández
   */
  public ArrayList<Reserva> reservasPendientes() {
    ArrayList<Reserva> reservasPendientesTemp = new ArrayList<Reserva>();
    for (Reserva r : reservas) {
      LocalDate fechaFinReserva = r.getFechaInicio().plusDays(r.getNoches());
      if (fechaFinReserva.isAfter(LocalDate.now()) ||
              fechaFinReserva.isEqual(LocalDate.now())) reservasPendientesTemp.add(r);
    }
    return reservasPendientesTemp;
  }

  /**
   * Retorna una lista de las reservas que ya han finalizado.
   *
   * @return una lista de objetos de tipo Reserva que ya han finalizado.
   * @author José Miguel Aranda Fernández
   */
  public ArrayList<Reserva> reservasHistorico() {
    ArrayList<Reserva> reservasHistoricoTemp = new ArrayList<Reserva>();
    for (Reserva r : reservas) {
      if (r.getFechaInicio().plusDays(r.getNoches()).isBefore(LocalDate.now())) {
        reservasHistoricoTemp.add(r);
      }
    }
    Collections.sort(reservasHistoricoTemp);
    return reservasHistoricoTemp;
  }

  /**
   * Añadimos una reserva al usuario
   *
   * @param reserva la reserva que el usuario ha reservado
   * @author José Miguel Aranda Fernández
   */
  public void addReserva(Reserva reserva) {
    this.reservas.add(reserva);
  }

  /**
   * Borramos una reserva del usuario
   *
   * @param reservaABorrar la reserva a borrar
   * @author José Miguel Aranda Fernández
   */
  public void deleteReserva(Reserva reservaABorrar) {
    this.reservas.remove(reservaABorrar);
  }


  /**
   * Devuelve una cadena con los datos básicos del usuario
   *
   * @return String
   * @author José Miguel Aranda Fernández
   */
  private String pintaResumen() {
    return "ID Usuario: " + id + "\n" +
            "Nombre: " + nombre + "\n" +
            "Correo electrónico: " + email;
  }

  @Override
  public String toString() {
    return String.format("""
                                         
                    \t╔══════════════════════════════╣ ID USUARIO %07d ╠═══════════════════════════════╗  
                    \t  ---------------------------------------------------------------------------------   
                    \t  NOMBRE: %s                               
                    \t  ---------------------------------------------------------------------------------   
                    \t  CLAVE: %s    
                    \t  ---------------------------------------------------------------------------------   
                    \t  EMAIL: %s                                         
                    \t  ---------------------------------------------------------------------------------    
                    \t  NÚMERO DE RESERVAS:%d                                        
                    \t  ---------------------------------------------------------------------------------    
                    \t╚═══════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, nombre, clave, email, reservas.size());
  }

}