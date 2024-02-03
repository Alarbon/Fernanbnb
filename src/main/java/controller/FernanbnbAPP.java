package controller;

import comunicaciones.PlantillaMail;
import comunicaciones.PlantillaTelegram;
import dao.*;
import dataClass.ResumenReservaPDF;
import models.*;
import persistencia.Persistencia;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static componentes.Utils.quitaTildes;
import static comunicaciones.Email.*;
import static comunicaciones.PlantillaMail.*;
import static comunicaciones.PlantillaTelegram.messageTelegramReservaAdd;
import static comunicaciones.Telegram.enviarMensajeTelegram;
import static comunicaciones.Telegram.enviarMensajeTelegramWeb;


public class FernanbnbAPP {
    private DAOManager dao;
    private DAOUsuarioSQL daoUsuarioSQL;
    private DAOPropietarioSQL daoPropietarioSQL;
    private DAOAdministradorSQL daoAdministradorSQL;
    private DAOReservaSQL daoReservaSQL;
    private DAOViviendaSQL daoViviendaSQL;
    private DAOImagenViviendaSQL daoImagenViviendaSQL;
    private DAOMensajesSql daoMensajesSql;


    //constructor
    public FernanbnbAPP() {
        dao = DAOManager.getSinglentonInstance();
        daoUsuarioSQL = new DAOUsuarioSQL();
        daoPropietarioSQL = new DAOPropietarioSQL();
        daoAdministradorSQL = new DAOAdministradorSQL();
        daoReservaSQL = new DAOReservaSQL();
        daoViviendaSQL = new DAOViviendaSQL();
        //conectarBBDD();
        if (Persistencia.primerInicio()) {
            //si es primer inicio de la app borramos los datos en bbdd
            eliminarContenidosFernanbnbBBDD();
            //Posteriormente inicio el mock
            mock();
        }


    }

    //constructor de web
    public FernanbnbAPP(String context) {
        dao = DAOManager.getSinglentonInstance(context);
        daoUsuarioSQL = new DAOUsuarioSQL();
        daoPropietarioSQL = new DAOPropietarioSQL();
        daoAdministradorSQL = new DAOAdministradorSQL();
        daoReservaSQL = new DAOReservaSQL();
        daoViviendaSQL = new DAOViviendaSQL();
        daoImagenViviendaSQL = new DAOImagenViviendaSQL();
        daoMensajesSql = new DAOMensajesSql();
        if (Persistencia.primerInicio(context)) {
            //si es primer inicio de la app borramos los datos en bbdd
            eliminarContenidosFernanbnbBBDD();
            //Posteriormente inicio el mock
            mock();
        }
    }


    /**
     * Eliminamos toda información (reservas, viviendas, propietarios, usuarios y administradores) de nuestra base de datos
     *
     * @return true si se han borrado completamente los datos de la base de datos,
     * false en caso contrario
     * @autor Adrián Lara Bonilla
     * @ator José Miguel Aranda Fernández
     */

    private boolean eliminarContenidosFernanbnbBBDD() {
        return (daoReservaSQL.deleteAllData(dao) &&
                daoViviendaSQL.deleteAllData(dao) &&
                daoPropietarioSQL.deleteAllData(dao) &&
                daoAdministradorSQL.deleteAllData(dao) &&
                daoUsuarioSQL.deleteAllData(dao));
    }

    /**
     * Genera un número aleatorio entre 1 y 1000000.
     *
     * @return Un entero entre 1 y 1000000.
     * @autor Adrián Lara Bonilla
     */
    private int generaAleatorio() {
        return (int) (Math.random() * 100000);
    }

    /**
     * Genera un nuevo ID de usuario aleatorio que no exista en la lista de usuarios.
     *
     * @return Un ID de usuario que no existe en la lista de usuarios.
     * @autor Adrián Lara Bonilla
     */
    private int generaIdUsuario() {
        int id;
        do {
            id = generaAleatorio();
            id += 1000000;
        } while (buscaUsuariobyId(id) != null);
        return id;
    }

    /**
     * Busca un usuario por su id en la lista de usuarios.
     *
     * @param id El id del usuario que se desea buscar.
     * @return El usuario con el id proporcionado, o null si no se encuentra.
     * @autor José Miguel Aranda Fernández
     */
    public Usuario buscaUsuariobyId(int id) {
        return daoUsuarioSQL.readByID(id, dao);
    }


    /**
     * Genera un nuevo ID de propietario aleatorio que no exista en la lista de propietarios.
     *
     * @return Un ID de propietario que no existe en la lista de propietarios.
     * @autor Adrián Lara Bonilla
     */
    private int generaIdPropietario() {
        int id;
        do {
            id = generaAleatorio();
            id += 2000000;
        } while (buscaPropietariobyId(id) != null);
        return id;
    }

    /**
     * Busca un propietario por su id en la lista de propietarios.
     *
     * @param id El id del propietario que se desea buscar.
     * @return El propietario con el id proporcionado, o null si no se encuentra.
     * @autor Adrian Lara Bonilla
     */
    public Propietario buscaPropietariobyId(int id) {
        return daoPropietarioSQL.readByID(id, dao);
    }

    /**
     * Genera un nuevo ID de administrador aleatorio que no exista en la lista de administradores.
     *
     * @return Un ID de administrador que no existe en la lista de administradores.
     * @autor Adrián Lara Bonilla
     */
    private int generaIdAdministrador() {
        int id;
        do {
            id = generaAleatorio();
            id += 3000000;
        } while (buscaAdministradorbyId(id) != null);
        return id;
    }


    /**
     * Busca un administrador por su id en la lista de administradores.
     *
     * @param id El id del administrador que se desea buscar.
     * @return El administrador con el id proporcionado, o null si no se encuentra.
     * @autor Adrián Lara Bonilla
     */
    private Administrador buscaAdministradorbyId(int id) {
        for (Administrador a : daoAdministradorSQL.readListadoAdministradores(dao)) {
            if (id == a.getId()) return a;
        }
        return null;
    }

    /**
     * Genera un ID de vivienda aleatorio que no está en uso actualmente.
     *
     * @return un entero que representa el ID de la vivienda generada.
     * @autor Adrián Lara Bonilla
     */
    private int generaIdVivienda() {
        int id;
        do {
            id = generaAleatorio();
            id += 4000000;
        } while (buscaViviendabyId(id) != null);
        return id;
    }

    /**
     * Busca una vivienda por su ID en la lista de viviendas de todos los propietarios.
     *
     * @param id el ID de la vivienda a buscar
     * @return la vivienda con el ID especificado, o null si no se encontró ninguna vivienda con ese ID.
     * @autor Adrián Lara Bonilla
     */
    private Vivienda buscaViviendabyId(int id) {
        return daoViviendaSQL.readByID(id, dao);
    }

    /**
     * Genera un ID de reserva aleatorio que no está en uso actualmente.
     *
     * @return un entero que representa el ID de la reserva generada.
     * @autor Adrián Lara Bonilla
     */
    private int generaIdReserva() {
        int id;
        do {
            id = generaAleatorio();
            id += 5000000;
        } while (buscaReservabyId(id) != null);
        return id;
    }


    /**
     * Busca una reserva por su ID en la bbdd;
     *
     * @param id el ID de la reserva a buscar
     * @return la reserva con el ID especificado, o null si no se encontró ninguna reserva con ese ID.
     * @autor Adrián Lara Bonilla
     */
    public Reserva buscaReservabyId(int id) {
        return daoReservaSQL.readByID(id, dao);
    }

    /**
     * Devuelve el número de reservas pendientes para el propietario especificado.
     * Una reserva se considera pendiente si su fecha de inicio más el número de noches es posterior
     * a la fecha actual.
     *
     * @param p el propietario para el que se desea obtener el número de reservas pendientes
     * @return el número de reservas pendientes para el propietario especificado
     */
    public int numReservasPendientes(Propietario p) {
        int reservasPendientes = 0;
        for (Reserva r : p.getReservas()) {
            if (r.getFechaInicio().plusDays(r.getNoches()).isAfter(LocalDate.now())) reservasPendientes++;
        }
        return reservasPendientes;
    }

    /**
     * Crea y agrega un nuevo objeto de la clase Usuario a la lista de usuarios existentes, con el nombre, clave y
     * email proporcionados.
     * El método genera un ID de usuario único para el usuario creado.
     *
     * @param nombre      El nombre del nuevo usuario.
     * @param clave       La clave del nuevo usuario.
     * @param email       El correo electrónico del nuevo usuario.
     * @param validaToken Si es true el usuario estará validado si es false no lo estará
     * @return El objeto Usuario recién creado y agregado a la lista de usuarios existentes.
     * @autor Adrián Lara Bonilla
     */
    public Usuario addUsuario(String nombre, String clave, String email, boolean validaToken) {
        String token = generaToken();
        Usuario user = new Usuario(generaIdUsuario(), nombre, clave, email, token, validaToken);
        //Insertamos al usuario a nuestra bbdd
        if (!daoUsuarioSQL.insert(user, dao)) return null;
        if (!validaToken) enviarMensaje(email, "Token de activación", PlantillaMail.mensajeToken(token));
        return user;
    }

    public Usuario addUsuarioWeb(String nombre, String clave, String email, boolean validaToken, String context) {
        String token = generaToken();
        Usuario user = new Usuario(generaIdUsuario(), nombre, clave, email, token, validaToken);
        //Insertamos al usuario a nuestra bbdd
        if (!daoUsuarioSQL.insert(user, dao)) return null;
        if (!validaToken)
            enviarMensajeWeb(email, "Token de activación", PlantillaMail.mensajeTokenWeb(String.valueOf(user.getId())), context);
        return user;
    }

    /**
     * Crea y agrega un nuevo objeto de la clase Propietario a la lista de propietarios existentes, con el nombre, clave
     * y email proporcionados.
     * El método genera un ID de propietario único para el propietario creado.
     *
     * @param nombre      El nombre del nuevo propietario.
     * @param clave       La clave del nuevo propietario.
     * @param email       El correo electrónico del nuevo propietario.
     * @param validaToken Si es true el usuario estará validado si es false no lo estará
     * @return El objeto Propietario recién creado y agregado a la lista de propietarios existentes.
     * @autor Adrián Lara Bonilla
     */
    public Propietario addPropietario(String nombre, String clave, String email, boolean validaToken) {
        String token = generaToken();
        Propietario prop = new Propietario(generaIdPropietario(), nombre, clave, email, token, validaToken);
        if (!daoPropietarioSQL.insert(prop, dao)) return null;
        if (!validaToken) enviarMensaje(email, "Token de activación", PlantillaMail.mensajeToken(token));
        return prop;
    }

    public Propietario addPropietarioWeb(String nombre, String clave, String email, boolean validaToken, String context) {
        String token = generaToken();
        Propietario prop = new Propietario(generaIdPropietario(), nombre, clave, email, token, validaToken);
        if (!daoPropietarioSQL.insert(prop, dao)) return null;
        if (!validaToken)
            enviarMensajeWeb(email, "Token de activación", PlantillaMail.mensajeTokenWeb(String.valueOf(prop.getId())), context);
        return prop;
    }

    /**
     * Crea y agrega un nuevo objeto de la clase Administrador a la lista de administradores existentes, con el nombre, clave y email proporcionados.
     * El método genera un ID de administrador único para el administrador creado.
     *
     * @param nombre      El nombre del nuevo administrador.
     * @param clave       La clave del nuevo administrador.
     * @param email       El correo electrónico del nuevo administrador.
     * @param validaToken Si es true el usuario estará validado si es false no lo estará
     * @return El objeto Administrador recién creado y agregado a la lista de administradores existentes.
     * @autor Adrián Lara Bonilla
     */
    public Administrador addAdministrador(String nombre, String clave, String email, boolean validaToken) {
        String token = generaToken();
        Administrador admin = new Administrador(generaIdAdministrador(), nombre, clave, email, token, validaToken);
        if (!daoAdministradorSQL.insert(admin, dao)) return null;
        if (!validaToken) enviarMensaje(email, "Token de activación", PlantillaMail.mensajeToken(token));
        return admin;
    }

    /**
     * Método público de la clase que permite a un usuario iniciar sesión en el sistema.
     * El método busca en las listas de usuarios, propietarios y administradores, y verifica si alguno de ellos
     * tiene el correo electrónico y la contraseña proporcionados.
     * Si se encuentra una coincidencia, el método devuelve el objeto correspondiente al usuario que inició sesión si no
     * devuelve null.
     *
     * @param email el correo electrónico del usuario que desea iniciar sesión
     * @param clave la contraseña del usuario que desea iniciar sesión
     * @return el objeto correspondiente al usuario que inició sesión, o null si no se encontró ninguna coincidencia
     * @autor Adrián Lara Bonilla
     */
    public Object login(String email, String clave) {
        Usuario u = daoUsuarioSQL.readByEmail(email, dao);
        if (u != null && u.login(email, clave)) {
            return u;
        }
        Propietario p = daoPropietarioSQL.readByEmail(email, dao);
        if (p != null && p.login(email, clave)) {
            return p;
        }
        Administrador adm = daoAdministradorSQL.readByEmail(email, dao);
        if (adm != null && adm.login(email, clave)) {
            return adm;
        }

        return null;
    }

    /**
     * Actualiza el perfil de un usuario con sus reservas y cambios pertinentes.
     * Si el usuario es un Usuario, se actualiza con sus reservas.
     * Si el usuario es un Propietario, se actualiza con sus viviendas y reservas.
     * Si el usuario es un Administrador, se actualiza con los cambios pertinentes.
     *
     * @param user el objeto usuario a actualizar datos
     * @return el usuario actualizado
     * @autor Adrián Lara Bonilla
     */
    public Object actualizaPerfil(Object user) {
        //Actualizo el perfil del usuario con sus reservas y cambios pertinentes
        if (user instanceof Usuario) {
            //Actualizo el perfil buscando por id en la bbdd
            user = daoUsuarioSQL.readByID(((Usuario) user).getId(), dao);
            //Asigno las reservas del usuario
            ((Usuario) user).setReservas(daoReservaSQL.readListadoReservasUsuario(((Usuario) user).getId(), dao));
        }
        //Actualizo el perfil del usuario propietarios con sus viviendas y reservas y cambios pertinentes
        if (user instanceof Propietario) {
            //Actualizo el perfil buscando por id en la bbdd
            user = daoPropietarioSQL.readByID(((Propietario) user).getId(), dao);
            //le asigno sus viviendas
            ((Propietario) user).setViviendas(daoViviendaSQL.readListadoViviendasPropietario(((Propietario) user).getId(), dao));
            for (Vivienda v : ((Propietario) user).getViviendas()) {
                //le asigno a la viviendas del propietario sus reservas
                v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
            }
        }
        //Actualizo el perfil del usuario administrador con sus cambios pertinentes
        if (user instanceof Administrador) user = daoAdministradorSQL.readByID(((Administrador) user).getId(), dao);

        return user;
    }


    /**
     * Asigna un nuevo token de activación al usuario especificado y lo envía por correo electrónico.
     * Si el usuario es un objeto de tipo Usuario, Propietario o Administrador, se asigna el nuevo token y
     * se envía un correo electrónico con el token.
     *
     * @param user el usuario para el que se desea restaurar el token
     * @return true si el token se pudo asignar y enviar por correo electrónico, false en caso contrario.
     * @autor Adrián Lara Bonilla
     */
    public boolean restauraToken(Object user) {
        String token = generaToken();
        if (user instanceof Usuario) {
            ((Usuario) user).setToken(token);
            enviarMensaje(((Usuario) user).getEmail(), "Token de activación", PlantillaMail.mensajeToken(token));
            return true;
        }
        if (user instanceof Propietario) {
            ((Propietario) user).setToken(token);
            enviarMensaje(((Propietario) user).getEmail(), "Token de activación", PlantillaMail.mensajeToken(token));
            return true;
        }
        if (user instanceof Administrador) {
            ((Administrador) user).setToken(token);
            enviarMensaje(((Administrador) user).getEmail(), "Token de activación", PlantillaMail.mensajeToken(token));
            return true;
        }
        return false;
    }

    /**
     * Crea y añade una nueva vivienda al sistema con los parámetros especificados.
     *
     * @param titulo       el título de la vivienda
     * @param descripcion  la descripción de la vivienda
     * @param localidad    la localidad donde se encuentra la vivienda
     * @param maxOcupantes el número máximo de ocupantes de la vivienda
     * @param precioNoche  el precio por noche de la vivienda
     * @return la vivienda creada y añadida al sistema
     * @autor Adrián Lara Bonilla
     * @autor José Miguel Aranda Fernández
     */
    public Vivienda addVivienda(int idPropietario, String titulo, String descripcion, String localidad, String
            provincia, int maxOcupantes, double precioNoche) {
        Propietario p = buscaPropietariobyId(idPropietario);
        if (p == null) return null;
        Vivienda v = new Vivienda(generaIdVivienda(), titulo, descripcion, localidad, provincia, maxOcupantes, precioNoche);
        if (!daoViviendaSQL.insert(v, p, dao)) return null;
        return v;
    }

    /**
     * Genera un token de 6 caracteres alfanumericos aleatorios.
     *
     * @return el token generado
     * @autor Adrián Lara Bonilla
     */
    private String generaToken() {
        String contenido = "ABCDEFGHIJKLMNOPQRSTUVWZ0123456789";
        String token = "";
        for (int i = 0; i < 6; i++) {
            token += contenido.charAt((int) (Math.random() * contenido.length()));
        }
        return token;
    }

    /**
     * Valida un token de activación de un usuario, propietario o administrador a partir del token y email proporcionados.
     *
     * @param token el token a validar.
     * @param user  el usuario, propietario o administrador.
     * @return true si el token es válido para el usuario, propietario o administrador con el email proporcionado, false en caso contrario.
     * @autor Adrián Lara Bonilla
     */
    public boolean validaTokenUsuario(String token, Object user) {

        if ((user instanceof Usuario && ((Usuario) user).getToken().equals(token) && ((Usuario) user).validaToken(token))) {
            //Actualizamos datos del usuario
            return daoUsuarioSQL.update((Usuario) user, dao);
        }
        if (((user instanceof Propietario && ((Propietario) user).getToken().equals(token) &&
                ((Propietario) user).validaToken(token)))) {
            //Actualizamos datos del usuario propietario
            return daoPropietarioSQL.update((Propietario) user, dao);
        }
        if (user instanceof Administrador && ((Administrador) user).getToken().equals(token) && ((Administrador) user).validaToken(token)) {
            //Actualizamos datos del usuario administrador
            return daoAdministradorSQL.update(((Administrador) user), dao);
        }
        return false;


    }

    /**
     * Comprueba si el token de validación del usuario ha sido validado.
     *
     * @param user objeto de tipo Usuario, Propietario o Administrador.
     * @return true si el token ha sido validado, false en caso contrario.
     * @autor Adrián Lara Bonilla
     */
    public boolean compruebaTokenValidado(Object user) {
        if ((user instanceof Usuario && ((Usuario) user).isVerificado())) return true;
        if ((user instanceof Propietario && ((Propietario) user).isVerificado())) return true;
        return (user instanceof Administrador && ((Administrador) user).isVerificado());
    }


    /**
     * Devuelve un objeto Vivienda si coincide su id con el introducido.
     *
     * @param idVivienda es el id de la vivienda a buscar
     * @return vivienda si coincide, null en caso contrario
     * @author José Miguel Aranda Fernández
     */
    public Vivienda buscaViviendaId(int idVivienda) {
        return daoViviendaSQL.readByIDDisponible(idVivienda, dao);
    }

    /**
     * Devuelve un objeto Usuario si coincide su email con el introducido.
     *
     * @param emailUsuario es el email del usuario a buscar
     * @return usuario si coincide, null en caso contrario
     * @author José Miguel Aranda Fernández
     */
    public Usuario buscaUsuarioEmail(String emailUsuario) {
        Usuario u = daoUsuarioSQL.readByEmail(emailUsuario, dao);
        return u;
    }

    /**
     * Devuelve un objeto Propietario si coincide su email con el introducido.
     *
     * @param emailPropietario es el email del propietario a buscar
     * @return propietario si coincide, null en caso contrario
     * @author José Miguel Aranda Fernández
     */
    public Propietario buscaPropietarioEmail(String emailPropietario) {
        Propietario p = daoPropietarioSQL.readByEmail(emailPropietario, dao);
        return p;
    }

    /**
     * Devuelve un objeto Administrador si coincide su email con el introducido.
     *
     * @param emailAdministrador es el email del administrador a buscar
     * @return administrador si coincide, null en caso contrario
     * @author José Miguel Aranda Fernández
     */
    private Administrador buscaAdministradorEmail(String emailAdministrador) {
        Administrador adm = daoAdministradorSQL.readByEmail(emailAdministrador, dao);
        return adm;
    }


    /**
     * Comprueba si dos cadenas de caracteres son iguales, sin tener en cuenta los acentos y el formato de mayúsculas y minúsculas.
     *
     * @param palabra        la primera cadena de caracteres a comparar esta será de la vivienda del propietario
     * @param palabraEscrita la segunda cadena de caracteres a comparar esta será escrita por el usuario
     * @return true si las cadenas son iguales sin tener en cuenta el formato, false en caso contrario
     * @autor Adrián Lara Bonilla
     */
    public boolean compruebaSinFormato(String palabra, String palabraEscrita) {
        return (quitaTildes(palabra).equalsIgnoreCase(quitaTildes(palabraEscrita)));
    }

    /**
     * Busca alojamientos que cumplan con ciertos criterios de búsqueda, incluyendo localidad, provincia, fecha de inicio,
     * duración de la estancia y número de ocupantes.
     *
     * @param localidad      la localidad deseada para el alojamiento
     * @param provincia      la provincia deseada para el alojamiento
     * @param fechaInicio    la fecha de inicio de la estancia
     * @param noches         la duración de la estancia en noches
     * @param ocupantes      el número de ocupantes que se hospedarán en el alojamiento
     * @param buscaLocalidad si es true busca por localidad si es false busca por Provincia
     * @return una lista de alojamientos que cumplen con los criterios de búsqueda o null si no se encuentra ninguno
     * @author José Miguel Aranda Fernández
     * @autor Adrián Lara Bonilla
     */
    public ArrayList<Vivienda> buscaAlojamiento(String localidad, String provincia, LocalDate fechaInicio,
                                                int noches, int ocupantes, boolean buscaLocalidad) {
        ArrayList<Vivienda> alojamientosTemp = new ArrayList<>();

        //si buscaLocalidad es true es localidad sino es provincia
        String tipo = buscaLocalidad ? localidad : provincia;
        for (Vivienda v : daoViviendaSQL.readListadoViviendasbyLocalidadProvincia(buscaLocalidad, tipo, dao)) {
            v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
            if (v.tieneDisponibilidad(fechaInicio, noches, ocupantes)) alojamientosTemp.add(v);
        }
        if (alojamientosTemp.isEmpty()) return null;
        return alojamientosTemp;
    }

    /**
     * Busca alojamientos que cumplan con ciertos criterios de búsqueda, como localidad, provincia, fechas y capacidad.
     * Retorna una lista de alojamientos que coinciden con los criterios de búsqueda, incluso aquellos que no están disponibles
     * en las fechas especificadas.
     *
     * @param localidad   La localidad donde se desea buscar alojamiento.
     * @param provincia   La provincia donde se desea buscar alojamiento.
     * @param fechaInicio La fecha de inicio de la estancia deseada.
     * @param noches      La cantidad de noches de la estancia deseada.
     * @param ocupantes   La cantidad de ocupantes que se hospedarán.
     * @return Una lista de alojamientos que coinciden con los criterios de búsqueda.
     * Si no se encuentra ningún alojamiento, devuelve null.
     * @author José Miguel Aranda Fernández
     * @autor Adrián Lara Bonilla
     */
    public ArrayList<Vivienda> buscaAlojamientosSinDisponibilidad(String localidad, String provincia, LocalDate
            fechaInicio, int noches, int ocupantes, boolean buscaLocalidad) {
        ArrayList<Vivienda> alojamientosTemp = new ArrayList<>();
        //si buscaLocalidad es true es localidad sino es provincia
        String tipo = buscaLocalidad ? localidad : provincia;

        for (Vivienda v : daoViviendaSQL.readListadoViviendasbyLocalidadProvincia(buscaLocalidad, tipo, dao)) {
            v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
            if (!v.tieneDisponibilidad(fechaInicio, noches, ocupantes)) alojamientosTemp.add(v);
        }
        if (alojamientosTemp.isEmpty()) return null;
        return alojamientosTemp;
    }

    /**
     * Devuelve el número total de usuarios del sistema, incluyendo usuarios y propietarios.
     *
     * @return un entero que representa el número total de usuarios del sistema.
     * @author José Miguel Aranda Fernández
     */
    public int numeroUsuarios() {
        int numUsuarios = daoUsuarioSQL.readListadoUsuarios(dao).size() + daoPropietarioSQL.readListadoPropietarios(dao).size();
        return numUsuarios;
    }

    /**
     * Devuelve el número total de viviendas del sistema, sumando el número de viviendas de todos los propietarios.
     *
     * @return un entero que representa el número total de viviendas del sistema.
     * @author José Miguel Aranda Fernández
     */
    public int numeroViviendas() {
        int numeroViviendas = 0;
        for (Propietario p : daoPropietarioSQL.readListadoPropietarios(dao)) {
            numeroViviendas += daoViviendaSQL.readListadoViviendasPropietario(p.getId(), dao).size();
        }
        return numeroViviendas;
    }

    /**
     * Devuelve el número total de reservas del sistema, sumando el número de reservas de todos los usuarios.
     *
     * @return un entero que representa el número total de reservas del sistema.
     * @author José Miguel Aranda Fernández
     */
    public int numeroReservas() {
        int numeroReservas = 0;
        for (Usuario u : daoUsuarioSQL.readListadoUsuarios(dao)) {
            numeroReservas += daoReservaSQL.readListadoReservasUsuario(u.getId(), dao).size();
        }
        return numeroReservas;
    }

    /**
     * Devuelve una lista de todas las viviendas
     *
     * @return una lista de las viviendas del programa, si no se encuentran ninguna devuelve null
     * @author José Miguel Aranda Fernández
     */
    public ArrayList<Vivienda> getAllViviendas() {
        ArrayList<Vivienda> allViviendasTemp = daoViviendaSQL.readListadoViviendas(dao);
        if (allViviendasTemp.isEmpty()) return null;
        return allViviendasTemp;
    }

    /**
     * Devuelve una lista imagenes de viviendas
     *
     * @return una lista de imagenes las viviendas del programa, si no se encuentran ninguna devuelve null
     * @author Adrián Lara Bonilla
     */
    public ArrayList<String> getAllImagenesViviendas() {
        ArrayList<String> imagenesViviendasTemp = daoImagenViviendaSQL.readListadoImagenesViviendas(dao);
        if (imagenesViviendasTemp.isEmpty()) return null;
        return imagenesViviendasTemp;
    }


    /**
     * Devuelve una lista de todas las reservas del programa.
     *
     * @return una lista de las reservas del programa, null si no se encuentran ninguna
     * @author José Miguel Aranda Fernández
     */
    public ArrayList<Reserva> getAllReservas() {
        ArrayList<Reserva> allReservasTemp = daoReservaSQL.readAllListadoReservas(dao);
        if (allReservasTemp.isEmpty()) return null;
        return allReservasTemp;
    }


    /**
     * Añade la reserva creada por el usuario tanto en la vivienda del propietario como en el usuario mismo.
     * El método revisará que al realizar la reserva el usuario la vivienda esté todavía disponible debido a varios factores
     * (la vivienda ha sido borrada o se ha creado un periodo de no disponibilidad u otra reserva)
     *
     * @param idUsuario              el id del usuario.
     * @param v                      el objeto Vivienda para extraer su id y su precio por noche.
     * @param fechaInicioIntroducida la fecha inicio de la reserva
     * @param nochesIntroducidas     el total de noches de la reserva.
     * @param ocupantesIntroducidos  el total de ocupantes que irán a la vivienda.
     * @param enviaComunicaciones    si es true envia las notificaciones si es false no las envia
     * @return true si se ha podido crear la reserva tanto en el usuario como en el propietario, false en caso contrario.
     * @autor Adrián Lara Bonilla
     */
    public boolean addReserva(int idUsuario, Vivienda v, LocalDate fechaInicioIntroducida, int nochesIntroducidas, int ocupantesIntroducidos, boolean enviaComunicaciones) {
        int idReserva = generaIdReserva();
        v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
        if (!v.tieneDisponibilidad(fechaInicioIntroducida, nochesIntroducidas, ocupantesIntroducidos)) return false;
        //creo una reserva temporal
        Reserva reservaTemp = new Reserva(idReserva, v.getId(), idUsuario, fechaInicioIntroducida,
                nochesIntroducidas, v.getPrecioNoche() * nochesIntroducidas, ocupantesIntroducidos);
        //insertamos la reserva a mysql
        daoReservaSQL.insert(reservaTemp, dao);
        //busco al usuario y le asigno la reserva
        Usuario u = buscaUsuariobyId(buscaReservabyId(idReserva).getIdUsuario());
        u.getReservas().add(reservaTemp);

        //comunicaciones a enviar
        if (enviaComunicaciones) {
            //Envia mensaje al admin a telegram
            Reserva r = buscaReservabyId(idReserva);
            //envio mensaje al admin por telegram
            enviarMensajeTelegram(messageTelegramReservaAdd(r, u));
            //Busco al propietario para enviarle los mensajes
            Propietario p = buscaPropietariobyVivienda(v);
            //Genereamos pdf resumen de la reserva para adjuntarlo al correo
            new ResumenReservaPDF(r.getId(), u.getNombre(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), u.getEmail(), r.getFechaInicio(), r.getNoches(), r.getOcupantes(), v.getTitulo()).generarResumenReserva();

            //Enviamos los correos
            enviarMensajePdfAdjunto(u.getEmail(), "Reserva añadida!", mensajeReservaUsuario(u.getNombre(),
                    v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(),
                    v.getMaxOcupantes(), p.getEmail(), p.getNombre(),
                    r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getNoches(), r.getNoches() * r.getPrecio(), r.getOcupantes()));
            enviarMensaje(p.getEmail(), "Reserva añadida!", mensajeReservaPropietario(p.getNombre(),
                    v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(),
                    v.getMaxOcupantes(), u.getEmail(), u.getNombre(),
                    r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getNoches(), r.getNoches() * r.getPrecio(), r.getOcupantes()));
        }
        return true;
    }

    public boolean addReservaWeb(int idUsuario, Vivienda v, LocalDate fechaInicioIntroducida, int nochesIntroducidas,
                                 int ocupantesIntroducidos, boolean enviaComunicaciones, String context) {
        int idReserva = generaIdReserva();
        v.setReservas(daoReservaSQL.readListadoReservasVivienda(v.getId(), dao));
        if (!v.tieneDisponibilidad(fechaInicioIntroducida, nochesIntroducidas, ocupantesIntroducidos)) return false;
        //creo una reserva temporal
        Reserva reservaTemp = new Reserva(idReserva, v.getId(), idUsuario, fechaInicioIntroducida,
                nochesIntroducidas, v.getPrecioNoche() * nochesIntroducidas, ocupantesIntroducidos);
        //insertamos la reserva a mysql
        daoReservaSQL.insert(reservaTemp, dao);
        //busco al usuario y le asigno la reserva
        Usuario u = buscaUsuariobyId(buscaReservabyId(idReserva).getIdUsuario());
        u.getReservas().add(reservaTemp);

        //comunicaciones a enviar
        if (enviaComunicaciones) {
            //Envia mensaje al admin a telegram
            Reserva r = buscaReservabyId(idReserva);
            //envio mensaje al admin por telegram
            enviarMensajeTelegramWeb(messageTelegramReservaAdd(r, u), context);
            //Busco al propietario para enviarle los mensajes
            Propietario p = buscaPropietariobyVivienda(v);
            //Genereamos pdf resumen de la reserva para adjuntarlo al correo
            new ResumenReservaPDF(r.getId(), u.getNombre(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), u.getEmail(), r.getFechaInicio(), r.getNoches(), r.getOcupantes(), v.getTitulo()).generarResumenReservaWeb(context);

            //Enviamos los correos
            enviarMensajePdfAdjuntoWeb(u.getEmail(), "Reserva añadida!", mensajeReservaUsuario(u.getNombre(),
                    v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(),
                    v.getMaxOcupantes(), p.getEmail(), p.getNombre(),
                    r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getNoches(), r.getNoches() * r.getPrecio(), r.getOcupantes()), context);
            enviarMensajeWeb(p.getEmail(), "Reserva añadida!", mensajeReservaPropietario(p.getNombre(),
                    v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(),
                    v.getMaxOcupantes(), u.getEmail(), u.getNombre(),
                    r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    r.getNoches(), r.getNoches() * r.getPrecio(), r.getOcupantes()), context);
        }
        return true;
    }

    /**
     * Busca a un propietario por su vivienda, si la vivienda le pertenece a un propietario en concreto lo devolvemos.
     *
     * @param v La vivienda para buscar al propietario
     * @return el propietario si lo encuentra, null si no.
     * @autor Adrián Lara Bonilla
     */
    public Propietario buscaPropietariobyVivienda(Vivienda v) {
        for (Propietario p : daoPropietarioSQL.readListadoPropietarios(dao)) {
            for (Vivienda vivienda : daoViviendaSQL.readListadoViviendasPropietario(p.getId(), dao)) {
                if (vivienda.getId() == v.getId()) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * Cancela una reserva identificada por su ID.
     *
     * @param idReserva           El ID de la reserva a cancelar.
     * @param enviaComunicaciones Si es true envia comunicaciones a los usuarios si no, no lo envia
     * @return True si se ha cancelado la reserva correctamente, de lo contrario, falso.
     * @autor Adrián Lara Bonilla
     */
    public boolean cancelaReserva(int idReserva, boolean enviaComunicaciones) {
        Reserva r = buscaReservabyId(idReserva);
        if (r == null) return false;
        //Busco al usuario
        Usuario u = buscaUsuariobyId(r.getIdUsuario());
        //Busco a la vivienda
        Vivienda v = buscaViviendaId(r.getIdVivienda());
        //Busco al propietario
        Propietario p = buscaPropietariobyVivienda(v);

        //Eliminamos la reserva de nuestra bbdd
        if (!daoReservaSQL.delete(r, dao)) return false;

        if (enviaComunicaciones) {
            //envio mensaje al admin por telegram
            enviarMensajeTelegram(PlantillaTelegram.messageTelegramReservaCancelada(r, u));

            //Envio los correos correspondientes
            enviarMensaje(u.getEmail(), "Reserva cancelada!", mensajeCancelaReservaUsuario(u.getNombre(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), v.getMaxOcupantes(), p.getEmail(), p.getNombre(), r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getNoches(), r.getPrecio() * r.getPrecio(), r.getOcupantes()));
            enviarMensaje(p.getEmail(), "Reserva cancelada!", mensajeCancelaReservaPropietario(p.getNombre(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), v.getMaxOcupantes(), u.getEmail(), u.getNombre(), r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getNoches(), r.getPrecio() * r.getPrecio(), r.getOcupantes()));
        }



 /*   //Eliminamos la reserva al usuario a nuestro objeto
    u.deleteReserva(r);
    //eliminamos la reserva de la vivienda objeto
    v.deleteReserva(r);*/

        return true;
    }

    public boolean cancelaReservaWeb(int idReserva, boolean enviaComunicaciones, String context) {
        Reserva r = buscaReservabyId(idReserva);
        if (r == null) return false;
        //Busco al usuario
        Usuario u = buscaUsuariobyId(r.getIdUsuario());
        //Busco a la vivienda
        Vivienda v = buscaViviendaId(r.getIdVivienda());
        //Busco al propietario
        Propietario p = buscaPropietariobyVivienda(v);

        //Eliminamos la reserva de nuestra bbdd
        if (!daoReservaSQL.delete(r, dao)) return false;

        if (enviaComunicaciones) {
            //envio mensaje al admin por telegram
            enviarMensajeTelegram(PlantillaTelegram.messageTelegramReservaCancelada(r, u));

            //Envio los correos correspondientes
            enviarMensajeWeb(u.getEmail(), "Reserva cancelada!", mensajeCancelaReservaUsuario(u.getNombre(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), v.getMaxOcupantes(), p.getEmail(), p.getNombre(), r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getNoches(), r.getPrecio() * r.getPrecio(), r.getOcupantes()), context);
            enviarMensajeWeb(p.getEmail(), "Reserva cancelada!", mensajeCancelaReservaPropietario(p.getNombre(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getPrecioNoche(), v.getMaxOcupantes(), u.getEmail(), u.getNombre(), r.getFechaInicio().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getFechaInicio().plusDays(r.getNoches()).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), r.getNoches(), r.getPrecio() * r.getPrecio(), r.getOcupantes()), context);
        }

        return true;
    }

    /**
     * Modifica el perfil de un usuario, propietario o administrador.
     *
     * @param user el objeto que representa al usuario, propietario o administrador cuyo perfil se modificará.
     * @return verdadero si el perfil se modificó correctamente, falso si no se pudo modificar.
     * @autor Adrián Lara Bonilla
     */
    public boolean modificaPerfil(Object user) {
        if (user instanceof Usuario) {
            Usuario u = buscaUsuariobyId(((Usuario) user).getId());
            u.setNombre(((Usuario) user).getNombre());
            u.setClave(((Usuario) user).getClave());
            u.setEmail(((Usuario) user).getEmail());
            //Grabo en bbdd los cambios pertinentes
            return (daoUsuarioSQL.update(u, dao));

        }
        if (user instanceof Propietario) {
            Propietario p = buscaPropietariobyId(((Propietario) user).getId());
            p.setNombre(((Propietario) user).getNombre());
            p.setClave(((Propietario) user).getClave());
            p.setEmail(((Propietario) user).getEmail());
            //Grabo en bbdd los cambios pertinentes
            return (daoPropietarioSQL.update(p, dao));
        }
        if (user instanceof Administrador) {
            Administrador adm = buscaAdministradorbyId(((Administrador) user).getId());
            adm.setNombre(((Administrador) user).getNombre());
            adm.setClave(((Administrador) user).getClave());
            adm.setEmail(((Administrador) user).getEmail());
            //Grabo en bbdd los cambios pertinentes
            return (daoAdministradorSQL.update(adm, dao));
        }
        return false;
    }

    /**
     * Modifica la vivienda deseada
     *
     * @param vivienda la vivienda ya modificada
     * @return verdadero si la vivienda se modificó correctamente.
     * @autor Adrián Lara Bonilla
     */
    public boolean modificaVivienda(Vivienda vivienda, boolean visible) {

        Vivienda v = buscaViviendaId(vivienda.getId());
        v.setTitulo(vivienda.getTitulo());
        v.setDescripcion(vivienda.getDescripcion());
        v.setLocalidad(vivienda.getLocalidad());
        v.setProvincia(vivienda.getProvincia());
        v.setMaxOcupantes(vivienda.getMaxOcupantes());
        v.setPrecioNoche(vivienda.getPrecioNoche());

        //Cambiamos los datos en nuestra bbdd
        return daoViviendaSQL.update(v, visible, dao);
    }


    /**
     * Modifica la reserva deseada para ello guardo en una variable la reserva antigua, la cancelo temporalmente y compruebo
     * que haya disponibilidad.
     *
     * @param reserva la reserva ya modificada
     * @return verdadero si la reserva se modificó correctamente, false si no se ha podido.
     * @autor Adrián Lara Bonilla
     */
    public boolean modificaReserva(Reserva reserva) {
        Reserva reservaAntigua = buscaReservabyId(reserva.getId());
        // buscaUsuariobyId(reservaAntigua.getIdUsuario()).deleteReserva(reservaAntigua);
        //si no se puede eliminar la reserva retornamos false
        if (!cancelaReserva(reservaAntigua.getId(), false)) return false;
        if (addReserva(reserva.getIdUsuario(), buscaViviendaId(reserva.getIdVivienda()), //Intento añadir la nueva reserva
                reserva.getFechaInicio(), reserva.getNoches(), reserva.getOcupantes(), false)) {
            return true;
        }
        //Si no la ha podido añadir devolveremos la reserva antigua a su sitio
        addReserva(reservaAntigua.getIdUsuario(), buscaViviendaId(reservaAntigua.getIdVivienda()), //Intento añadir la nueva reserva
                reservaAntigua.getFechaInicio(), reservaAntigua.getNoches(), reservaAntigua.getOcupantes(), false);
        return false;
    }

    /**
     * Comprueba si un correo electrónico está disponible para su uso, es decir es único.
     *
     * @param email el correo electrónico que se comprobará.
     * @return verdadero si el correo electrónico está disponible, falso si ya está siendo utilizado por algún usuario,
     * propietario o administrador.
     * @autor Adrián Lara Bonilla
     */
    public boolean compruebaCorreoDisponible(String email) {
        if (daoUsuarioSQL.readByEmail(email, dao) != null) {
            return false;
        }
        if (daoPropietarioSQL.readByEmail(email, dao) != null) {
            return false;
        }
        if (daoAdministradorSQL.readByEmail(email, dao) != null) {
            return false;
        }
        return true;
    }

    /**
     * Elimina una vivienda del sistema si no tiene reservas pendientes.
     *
     * @param vivienda La vivienda que se desea borrar del sistema.
     * @return true si la vivienda fue borrada exitosamente, false si no se pudo borrar.
     * @autor Adrián Lara Bonilla
     */
    public boolean deleteVivienda(Vivienda vivienda) {
        //asigno a la vivienda las reservas que tiene
        vivienda.setReservas(daoReservaSQL.readListadoReservasVivienda(vivienda.getId(), dao));
        //veo si tiene alguna reserva pendiente, sino la elimino
        if (!vivienda.tieneReservasPendientes()) {
            return (daoViviendaSQL.update(vivienda, false, dao));
        }
        return false;
    }

    /**
     * Agrega un periodo no disponible a una vivienda especificada.
     *
     * @param vivienda    La vivienda a la cual se desea agregar un periodo no disponible.
     * @param fechaInicio La fecha de inicio del periodo no disponible.
     * @param noches      El número de noches del periodo no disponible.
     * @return true si se agrega el periodo no disponible exitosamente, false si no se puede agregar.
     * @autor Adrián Lara Bonilla
     */
    public boolean addPeriodoNoDisponible(Vivienda vivienda, LocalDate fechaInicio, int noches) {
        vivienda.setReservas(daoReservaSQL.readListadoReservasVivienda(vivienda.getId(), dao));
        if (vivienda.tieneDisponibilidad(fechaInicio, noches, 1)) {
            if (daoReservaSQL.insert(new Reserva(generaIdReserva(), vivienda.getId(),
                    -1, fechaInicio, noches, 0, 0), dao)) {
                return true;
            }

        }
        return false;
    }



    public ArrayList<Usuario> getUsuarios() {
        return daoUsuarioSQL.readListadoUsuarios(dao);
    }

    public ArrayList<Propietario> getPropietarios() {
        return daoPropietarioSQL.readListadoPropietarios(dao);
    }
    public ArrayList<Administrador> getAdministradores() {
        return daoAdministradorSQL.readListadoAdministradores(dao);
    }



    /**
     * Guarda los datos del sistema creando arraylist de los grupos de usuarios una vez hecho se pasan a la persistencia
     * en backup y hace una copia  para recuperarlo a futuro
     *
     * @param ruta ruta donde esta el backup del sistema
     * @return true Si se ha logrado restaurar el backup, false si no se ha podido.
     * @autor Adrián Lara Bonilla
     */
    public boolean guardaBackup(String ruta) {
        ArrayList<Usuario> usuarios = daoUsuarioSQL.readListadoUsuarios(dao);
        for (Usuario u : usuarios) {
            u.setReservas(daoReservaSQL.readListadoReservasUsuario(u.getId(), dao));
        }
        ArrayList<Propietario> propietarios = daoPropietarioSQL.readListadoPropietarios(dao);
        for (Propietario p : propietarios) {
            p.setViviendas(daoViviendaSQL.readListadoViviendasPropietario(p.getId(), dao));
        }
        ArrayList<Administrador> administradores = daoAdministradorSQL.readListadoAdministradores(dao);

        return Persistencia.guardaBackup(ruta, usuarios, propietarios, administradores);
    }
    public boolean guardaBackupWeb(String ruta,String context) {
        ArrayList<Usuario> usuarios = daoUsuarioSQL.readListadoUsuarios(dao);
        for (Usuario u : usuarios) {
            u.setReservas(daoReservaSQL.readListadoReservasUsuario(u.getId(), dao));
        }
        ArrayList<Propietario> propietarios = daoPropietarioSQL.readListadoPropietarios(dao);
        for (Propietario p : propietarios) {
            p.setViviendas(daoViviendaSQL.readListadoViviendasPropietario(p.getId(), dao));
        }
        ArrayList<Administrador> administradores = daoAdministradorSQL.readListadoAdministradores(dao);

        return Persistencia.guardaBackup(ruta, usuarios, propietarios, administradores);
    }



    /**
     * Recupera los datos de un backup hecho por el usuario administrador en un directorio determinado. Ademas si se
     * ha encontrado el backup se guardan los datos del backup al sistema
     *
     * @param ruta ruta donde esta el backup del sistema
     * @return true Si se ha logrado restaurar el backup, false si no se ha podido.
     * @autor Adrián Lara Bonilla
     */
    public boolean cargarBackup(String ruta) {
        eliminarContenidosFernanbnbBBDD();
        File pathData = new File(System.getProperty("user.home") + ruta);
        //Si no existe ese directorio lo crearemos
        if (!pathData.exists()) return false;
        //Creamos un listado de usuarios temporales
        ArrayList<Usuario> usuariosTemp = Persistencia.cargaBackupUsuarios(ruta);
        ArrayList<Propietario> propietariosTemp = Persistencia.cargaBackupPropietarios(ruta);
        ArrayList<Administrador> administradoresTemp = Persistencia.cargaBackupAdministradores(ruta);
        //Comprobamos que no este vacio porque no existan los datos
        if (usuariosTemp.isEmpty() && propietariosTemp.isEmpty() && administradoresTemp.isEmpty()) return false;
        else {
            //Insertamos a los propietarios
            for (Propietario p : propietariosTemp) {
                daoPropietarioSQL.insert(p, dao);
                //insertamos sus viviendas
                for (Vivienda v : p.getViviendas()) {
                    daoViviendaSQL.insert(v, p, dao);
                }
            }
            //Insertamos a los usuarios
            for (Usuario u : usuariosTemp) {
                daoUsuarioSQL.insert(u, dao);
                //insertamos sus reservas
                for (Reserva r : u.getReservas()) {
                    daoReservaSQL.insert(r, dao);
                }

            }
            for (Administrador adm : administradoresTemp) {
                daoAdministradorSQL.insert(adm, dao);
            }

            return true;
        }
    }
//Mensajeria web



    public boolean enviarMensajeChat(int idEmisor, int idReceptor, int idVivienda, String mensaje) {
        return daoMensajesSql.enviaMensaje(new Mensaje(idEmisor, idReceptor, idVivienda, mensaje), dao);
    }


    //Busca el mensaje por su id
    public ArrayList<Mensaje> agruparMensajesPorViviendas(int idUsuario) {

        return daoMensajesSql.agruparChatsPorViviendas(idUsuario, dao);
    }

    public ArrayList<Mensaje> mensajesUnSoloChat(int idEmisor, int idReceptor, int idVivienda) {
        return daoMensajesSql.mensajesUnSoloChat(idEmisor, idReceptor, idVivienda, dao);
    }

    //Busca el mensaje por su id
    public Mensaje buscaMensajeById(int idMensaje) {
        return daoMensajesSql.buscaMensajeByID(idMensaje, dao);


    }


    public ArrayList<Mensaje> mensajesNoLeidosByUser(int idUser) {
        ArrayList<Mensaje> mensajesNoLeidos = daoMensajesSql.mensajesNoLeidosByUsuario(idUser, dao);
        return mensajesNoLeidos;
    }


    public boolean marcarMensajeLeido(int idMensaje, int idUser) {
        return daoMensajesSql.marcaMensajeLeido(idMensaje, idUser, dao);
    }

    public boolean borrarMensajeReceptor(int idMensaje, int idReceptor) {
        return daoMensajesSql.borraMensajeReceptor(idMensaje, idReceptor, dao);
    }

    public boolean borrarMensajeEmisor(int idMensaje, int idEmisor) {
        return daoMensajesSql.borraMensajeEmisor(idMensaje, idEmisor, dao);
    }


    public void mock() {
        addAdministrador("Adrian", "1234", "adrian@gmail.com", true);
        addAdministrador("Josemi", "1234", "josemi@gmail.com", true);
        addUsuario("Paco", "1234", "paco@gmail.com", true);
        addUsuario("eduardo", "1234", "eduardo@gmail.com", true);
        addUsuario("Maria", "1234", "maria@gmail.com", true);
        addUsuario("Cindy Nero", "1234", "cindy@gmail.com", true);
        addPropietario("Juanito ", "1234", "juan@gmail.com", true);
        addPropietario("Vicente ", "1234", "vicente@gmail.com", true);
        addPropietario("Felipe ", "1234", "felipe@gmail.com", true);

        Vivienda v = addVivienda(buscaPropietarioEmail("vicente@gmail.com").getId(), "Paraíso del interior", "Casa adosada y reformada", "Martos", "Jaen", 6, 63.5);
        Vivienda v2 = addVivienda(buscaPropietarioEmail("vicente@gmail.com").getId(), "Caseria de las Delicias", "Casa Rural", "Álcala la Real", "Jaén", 20, 40);
        Vivienda v3 = addVivienda(buscaPropietarioEmail("felipe@gmail.com").getId(), "Caserio Colesterol", "Duplex reformado cerca de playa", "Fuengirola", "Málaga", 20, 40);
        addVivienda(buscaPropietarioEmail("felipe@gmail.com").getId(), "Villa Churro", "Pisito en la playa", "Fuengirola", "Málaga", 20, 40);
        addVivienda(buscaPropietarioEmail("felipe@gmail.com").getId(), "Casa de la Peña", "Caseron rocoso", "Martos", "Jaen", 20, 40);
        addVivienda(buscaPropietarioEmail("juan@gmail.com").getId(), "Casa de los Pitufos", "Azul, preciosa y para gente bajita", "Martos", "Jaen", 20, 40);
        addReserva(buscaUsuarioEmail("eduardo@gmail.com").getId(), v, LocalDate.parse("2023-12-03"), 3, 4, false);
        addReserva(buscaUsuarioEmail("cindy@gmail.com").getId(), v2, LocalDate.parse("2023-10-03"), 7, 3, false);
        addReserva(buscaUsuarioEmail("cindy@gmail.com").getId(), v3, LocalDate.parse("2023-01-23"), 10, 8, false);
        addReserva(buscaUsuarioEmail("paco@gmail.com").getId(), v, LocalDate.parse("2023-06-26"), 3, 4, false);
        addReserva(buscaUsuarioEmail("paco@gmail.com").getId(), v, LocalDate.parse("2023-06-29"), 5, 4, false);

    }


}



