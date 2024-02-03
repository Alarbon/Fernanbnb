package models;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Mensaje implements Comparable<Mensaje> {
    //Atributos
    private int id;
    private int idEmisor;
    private int idReceptor;
    private int idVivienda;
    private String texto;
    private LocalDateTime fechaEnvio;
    private boolean leido;
    private boolean borradoEmisor;
    private boolean borradoReceptor;

    //Constructor

    public Mensaje(int idEmisor, int idReceptor, int idVivienda, String texto) {
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idVivienda = idVivienda;
        this.texto = texto;
        this.fechaEnvio = LocalDateTime.now();
        this.leido = false;
        this.borradoEmisor = false;
        this.borradoReceptor = false;

    }

    //Constructor desde SQL

    public Mensaje(int id, int idEmisor, int idReceptor, int idVivienda, String texto, LocalDateTime fechaEnvio, boolean leido, boolean borradoEmisor, boolean borradoReceptor) {
        this.id = id;
        this.idEmisor = idEmisor;
        this.idReceptor = idReceptor;
        this.idVivienda = idVivienda;
        this.texto = texto;
        this.fechaEnvio = fechaEnvio;
        this.leido = leido;
        this.borradoEmisor = borradoEmisor;
        this.borradoReceptor = borradoReceptor;
    }


    //Getters

    public int getId() {
        return id;
    }

    public int getIdEmisor() {
        return idEmisor;
    }

    public int getIdReceptor() {
        return idReceptor;
    }

    public int getIdVivienda() {
        return idVivienda;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isLeido() {
        return leido;
    }

    public boolean isBorradoEmisor() {
        return borradoEmisor;
    }

    public boolean isBorradoReceptor() {
        return borradoReceptor;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

     public void setId(int id) {
        this.id = id;
    }

    public void setIdEmisor(int idEmisor) {
        this.idEmisor = idEmisor;
    }

    public void setIdReceptor(int idReceptor) {
        this.idReceptor = idReceptor;
    }

    public void setIdVivienda(int idVivienda) {
        this.idVivienda = idVivienda;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }


    public void setBorradoEmisor(boolean borradoEmisor) {
        this.borradoEmisor = borradoEmisor;
    }

    public void setBorradoReceptor(boolean borradoReceptor) {
        this.borradoReceptor = borradoReceptor;
    }


    public String dateFormatter(LocalDate date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(dateFormat);
    }

    @Override
    public int compareTo(Mensaje otroMensaje) {
        // Compara los mensajes en función de la fecha de envío
        return otroMensaje.getFechaEnvio().compareTo(this.fechaEnvio);
    }


}
