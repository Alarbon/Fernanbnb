package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Vivienda implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private String localidad;
    private String provincia;
    private int maxOcupantes;
    private double precioNoche;
    private ArrayList<Reserva> reservas;

    /**
     * Crea un nuevo objeto Vivienda con los parámetros especificados.
     *
     * @param id           el identificador de la vivienda
     * @param titulo       el título de la vivienda
     * @param descripcion  la descripción de la vivienda
     * @param localidad    la localidad donde se encuentra la vivienda
     * @param provincia    la provincia donde se encuentra la vivienda
     * @param maxOcupantes la capacidad máxima de la vivienda
     * @param precioNoche  el precio por noche de la vivienda
     * @autor Adrián Lara Bonilla
     */
    public Vivienda(int id, String titulo, String descripcion, String localidad, String provincia, int maxOcupantes,
                    double precioNoche) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.maxOcupantes = maxOcupantes;
        this.precioNoche = precioNoche;
        reservas = new ArrayList<>();
    }

    /*Getters & Setters*/

    public int getId() {
        return id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getMaxOcupantes() {
        return maxOcupantes;
    }

    public void setMaxOcupantes(int maxOcupantes) {
        this.maxOcupantes = maxOcupantes;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
    /*Otros Métodos*/

    /**
     * Comprueba si la vivienda tiene disponibilidad para alojar a un determinado número de ocupantes
     * durante un periodo de tiempo concreto a partir de una fecha de inicio.
     *
     * @param fechaIni  la fecha de inicio del periodo que se quiere comprobar.
     * @param numNoches el número de noches que dura el periodo que se quiere comprobar.
     * @param ocupantes el número de ocupantes que se quieren alojar en la vivienda.
     * @return true si la vivienda tiene disponibilidad para alojar a los ocupantes durante el periodo indicado,
     * false en caso contrario.
     * @autor Adrián Lara Bonilla
     */
    public boolean tieneDisponibilidad(LocalDate fechaIni, int numNoches, int ocupantes) {

        if (ocupantes > this.maxOcupantes) return false;
        else {
            for (Reserva r : reservas) {
                if (r.periodoComprendido(fechaIni, numNoches)) return false;
            }
        }
        return true;
    }

    /**
     * Añade una reserva a la lista de reservas de la vivienda.
     *
     * @param id          el identificador de la reserva.
     * @param idVivienda  el identificador de la vivienda para la que se hace la reserva.
     * @param fechaInicio la fecha de inicio de la reserva.
     * @param noches      el número de noches que dura la reserva.
     * @param precio      el precio total de la reserva.
     * @param ocupantes   el número de ocupantes que se alojarán en la vivienda durante la reserva.
     * @return la reserva añadida a la lista de reservas de la vivienda.
     * @autor Adrián Lara Bonilla
     */
    public Reserva addReserva(int id, int idVivienda, int idUsuario, LocalDate fechaInicio, int noches, double precio, int ocupantes) {
        Reserva r = (new Reserva(id, idVivienda, idUsuario, fechaInicio, noches, precio, ocupantes));
        this.reservas.add(r);
        return r;
    }

    /**
     * Determina si la vivienda tiene alguna reserva y si esa reserva no ha finalizado aún respecto la fecha actual.
     *
     * @return true si la fecha final de la reserva es menor que la fecha actual, false en caso contrario
     * @autor Adrián Lara Bonilla
     */
    public boolean tieneReservasPendientes() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaFinReserva;
        if (reservas.size() == 0) return false;
        else {
            for (Reserva r : reservas) {
                fechaFinReserva = r.getFechaInicio().plusDays(r.getNoches());
                if (fechaActual.isBefore(r.getFechaInicio())
                        || (fechaActual.isAfter(r.getFechaInicio()) &&
                        fechaActual.isBefore(fechaFinReserva))) return true;
            }
        }
        return false;
    }

    /**
     * Elimina una reserva de la lista de reservas de la vivienda.
     *
     * @param reserva la reserva que se quiere eliminar.
     * @autor Adrián Lara Bonilla
     */
    public void deleteReserva(Reserva reserva) {
        this.reservas.remove(reserva);
    }

    public String pintaDatos() {
        return String.format("""
                                             
                        \t╔══════════════════════════════╣ ID VIVIENDA %07d ╠═══════════════════════════════╗  
                        \t  ----------------------------------------------------------------------------------   
                        \t  TITULO: %S                                       
                        \t  ----------------------------------------------------------------------------------   
                        \t  DESCRIPCION: %s    
                        \t  ----------------------------------------------------------------------------------   
                        \t  LOCALIDAD: %S                                         
                        \t  ----------------------------------------------------------------------------------    
                        \t  PROVINCIA: %S                                         
                        \t  ----------------------------------------------------------------------------------    
                        \t  OCUPANTES MAXIMOS: %d                                                          
                        \t  ----------------------------------------------------------------------------------   
                        \t  PRECIO NOCHE: %4.2f Eu                                                            
                        \t  ----------------------------------------------------------------------------------   
                        \t╚════════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , id, titulo, descripcion, localidad, provincia, maxOcupantes, precioNoche);

    }

    public String pintaResumen() {
        return String.format("""
                                             
                        ╔══════════════════════════════╣ ID VIVIENDA %07d ╠═══════════════════════════════╗ 
                          ----------------------------------------------------------------------------------   
                          TITULO: %S                                       
                          ----------------------------------------------------------------------------------   
                          LOCALIDAD: %S                                         
                          ----------------------------------------------------------------------------------    
                          PROVINCIA: %S                                         
                          ----------------------------------------------------------------------------------    
                          NUMERO DE RESERVAS EN LA VIVIENDA: %d                                           
                          ----------------------------------------------------------------------------------   
                        ╚════════════════════════════════════════════════════════════════════════════════════╝
                                                                       
                        """
                , id, titulo, localidad, provincia, reservas.size());
    }

    @Override
    public String toString() {
        return "Vivienda{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", maxOcupantes=" + maxOcupantes +
                ", precioNoche=" + precioNoche +
                ", Reservas=" + reservas +
                '}';
    }


}
