package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reserva implements Comparable<Reserva>, Serializable {
  //Atributos
  private int id;
  private int idVivienda;
  private int idUsuario;
  private LocalDate fechaInicio;
  private int noches;
  private double precio;
  private int ocupantes;

  //Constructor

  /**
   * Constructor público de la clase Reserva.
   * Crea una nueva instancia de Reserva con los siguientes atributos:
   *
   * @param id          el identificador de la reserva
   * @param idVivienda  el identificador de la vivienda asociada a la reserva
   * @param idUsuario   el identificador del usuario que realiza la reserva
   * @param fechaInicio la fecha de inicio de la reserva en formato LocalDate
   * @param noches      el número de noches de la reserva
   * @param precio      el precio total de la reserva
   * @param ocupantes   el número de ocupantes de la vivienda durante la reserva
   * @author José Miguel Aranda Fernández
   */
  public Reserva(int id, int idVivienda, int idUsuario, LocalDate fechaInicio, int noches, double precio, int ocupantes) {
    this.id = id;
    this.idVivienda = idVivienda;
    this.idUsuario = idUsuario;
    this.fechaInicio = fechaInicio;
    this.noches = noches;
    this.precio = precio;
    this.ocupantes = ocupantes;
  }

  public Reserva(Reserva r) {
    this.id = r.getId();
    this.idVivienda = r.getIdVivienda();
    this.idUsuario = r.getIdUsuario();
    this.fechaInicio = r.getFechaInicio();
    this.noches = r.getNoches();
    this.precio = r.getPrecio();
    this.ocupantes = r.getOcupantes();
  }

  //Getters

  public int getId() {
    return id;
  }


  public int getIdVivienda() {
    return idVivienda;
  }


  public int getIdUsuario() {
    return idUsuario;
  }


  public LocalDate getFechaInicio() {
    return fechaInicio;
  }


  public int getNoches() {
    return noches;
  }


  public double getPrecio() {
    return precio;
  }


  public int getOcupantes() {
    return ocupantes;
  }


  //Metodos

  /**
   * Verifica si una fecha especificada está comprendida en un rango de fechas que se extiende desde una fecha de inicio
   * dada durante un número especificado de noches.
   *
   * @param fechaInicioIntroducida la fecha de inicio del rango de fechas
   * @param nochesIntroducidas     el número de noches que cubre el rango de fechas
   * @return verdadero si la fecha especificada está dentro del rango de fechas, falso en caso contrario
   * @autor Adrián Lara Bonilla
   */
  public boolean periodoComprendido(LocalDate fechaInicioIntroducida, int nochesIntroducidas) {
    for (int i = 0; i < nochesIntroducidas; i++) {//Recorro todas las noches de la fecha
      for (int j = 0; j < noches; j++) {//Recorro todas las noches de la reserva
        LocalDate fechaActual = fechaInicioIntroducida.plusDays(i);
        LocalDate fechaFin = fechaInicio.plusDays(j);
        if (fechaActual.isEqual(fechaFin)) return true; //si coinciden
      }
    }
    return false;
  }

  /**
   * Devuelve una cadena que contiene los datos de una reserva de una vivienda.
   *
   * @param vivienda la vivienda reservada
   * @return una cadena que contiene los datos de la reserva
   * @autor Adrián Lara Bonilla
   */
  public String pintaDatos(Vivienda vivienda) {
    return String.format("""
                                         
                    ╔═════════════════════════════╣ ID DE RESERVA %07d ╠══════════════════════════════╗ 
                       -------------------------------------------------------------------------------   
                       VIVIENDA: %S                                                                    
                       -------------------------------------------------------------------------------   
                       FECHA: %10s                                                                       
                       -------------------------------------------------------------------------------     
                       NOCHES: %d                                                                       
                       -------------------------------------------------------------------------------   
                       VIAJEROS: %d                                                                     
                       -------------------------------------------------------------------------------    
                       PRECIO TOTAL: %.2f                                                               
                       -------------------------------------------------------------------------------   
                    ╚════════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, vivienda == null ? "Esta vivienda ha sido eliminada. " : vivienda.getTitulo(),
            fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), noches, ocupantes, precio);
  }

  /**
   * Devuelve una cadena que contiene los datos de una reserva de una vivienda en formato de tabla, específicamente
   * para el propietario de la vivienda.
   *
   * @param u el usuario que ha reservado
   * @return una cadena que contiene los datos de la reserva
   * @autor Adrián Lara Bonilla
   */
  public String pintaDatosParaPropietario(Usuario u, Vivienda v) {
    return String.format("""
                                         
                    ╔═════════════════════════════╣ ID DE RESERVA %07d ╠══════════════════════════════╗ 
                       ---------------------------------------------------------------------------------   
                       %s                              
                       ---------------------------------------------------------------------------------   
                       FECHA: %10s                                                                  
                       ---------------------------------------------------------------------------------     
                       NOCHES: %d                                                                     
                       ---------------------------------------------------------------------------------   
                       VIAJEROS: %d                                                                    
                       ---------------------------------------------------------------------------------   
                       PRECIO TOTAL: %.2f                                                         
                       ---------------------------------------------------------------------------------
                       ---------------------------------------------------------------------------------   
                       VIVIENDA: %s                                                         
                       ---------------------------------------------------------------------------------   
                    ╚════════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, u == null ? "Esta reserva es un periodo de no disponibilidad." :
                    "USUARIO :                                                                         \n" +
                            "     -NOMBRE : " + u.getNombre() + "\n" +
                            "     -CORREO : " + u.getEmail(),
            fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            noches, ocupantes, precio * noches, v == null ?
                    "Esta vivienda no existe actualmente" : "ID: (" + v.getId() + "). TITULO: " + v.getTitulo());
  }

  /**
   * Devuelve una cadena que contiene los datos del usuario que ha hecho la reserva del propietario de la vivienda reservada
   * y la propia vivienda.
   *
   * @param u el usuario que ha reservado
   * @param p el propietario de la vivienda reservada
   * @param v la vivienda reservada
   * @return una cadena que contiene los datos de la reserva
   * @autor Adrián Lara Bonilla
   */

  public String pintaDatosParaAdmin(Usuario u, Propietario p, Vivienda v) {
    return String.format("""
                                         
                    ╔═════════════════════════════╣ ID DE RESERVA %07d ╠══════════════════════════════╗ 
                       ---------------------------------------------------------------------------------   
                       USUARIO : (%s)                                                                         
                            -NOMBRE : %S                                
                            -CORREO : %S                                
                       ---------------------------------------------------------------------------------  
                       PROPIETARIO : (%s)                                                                        
                            -NOMBRE : %S                                
                            -CORREO : %S                                
                       ---------------------------------------------------------------------------------   
                       VIVIENDA: %s                                                                  
                       ---------------------------------------------------------------------------------     
                    ╚════════════════════════════════════════════════════════════════════════════════════╝
                                                                   
                    """
            , id, u != null ? u.getId() : "Periodo no disponibilidad", u != null ? u.getNombre() : "-",
            u != null ? u.getEmail() : "-", p.getId(), p.getNombre(), p.getEmail(), v == null ?
                    "Esta vivienda no existe actualmente" : "ID: (" + v.getId() + "). TITULO: " + v.getTitulo());
  }

  @Override
  public String toString() {
    return "Reserva{" +
            "id=" + id +
            ", idVivienda=" + idVivienda +
            ", idUsuario=" + idUsuario +
            ", fechaInicio=" + fechaInicio +
            ", noches=" + noches +
            ", precio=" + precio +
            ", ocupantes=" + ocupantes +
            '}';
  }

  @Override
  public int compareTo(Reserva r) {
    return (this.fechaInicio).compareTo(r.getFechaInicio());
  }


}