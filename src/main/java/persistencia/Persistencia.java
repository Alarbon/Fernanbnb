package persistencia;

import componentes.Utils;
import models.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

public class Persistencia {

    // private static final String DATAPATH_USUARIOS = "datapath.usuarios";
    //private static final String DATAPATH_PROPIETARIOS = "datapath.propietarios";
    //private static final String DATAPATH_ADMINISTRADORES = "datapath.administradores";
    private static final String DATAPATH_LOGS = "datapath.logs";
    private static final String RESUMEN_RESERVA = "ruta.resumen.pdf";
    private static final String LISTADO_RESERVAS = "ruta.listadoReservas.csv";
    private static final String LISTADO_VIVIENDAS = "ruta.listadoViviendas.csv";
    private static final String FERNANBNB_PROPERTIES = "config/fernanbnb.properties";
    private static final String LASTSESSION_PROPERTIES = "data/lastSession/lastSession.properties";

    /**
     * Carga y devuelve un objeto Properties con la información contenida en el archivo "fernanbnb.properties"
     * ubicado en la carpeta "config". Si el archivo no se encuentra o no se puede leer, se devuelve un objeto Properties vacío.
     *
     * @return objeto Properties con la información del archivo "fernanbnb.properties" o un objeto Properties vacío si ocurre un error
     * @autor Adrián Lara Bonilla
     */

    public static Properties cargaProperties() {
        try {
            Properties props = new Properties();
            props.load(new FileReader(FERNANBNB_PROPERTIES));
            return props;
        } catch (Exception e) {
            return new Properties();
        }
    }

    public static Properties cargaProperties(String context) {
        context = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "");
        try {
            Properties props = new Properties();
            props.load(new FileReader(context + File.separator + FERNANBNB_PROPERTIES));
            return props;
        } catch (Exception e) {
            return new Properties();
        }
    }

    /**
     * Cargar el documento de ultima sesion de los usuarios
     *
     * @return objeto Properties con la información del archivo "lastSession.properties" o un objeto Properties vacío si ocurre un error
     * @autor Adrián Lara Bonilla
     */
    public static Properties cargaLastSesion() {
        try {
            Properties props = new Properties();
            props.load(new FileReader(LASTSESSION_PROPERTIES));
            return props;
        } catch (Exception e) {
            return new Properties();
        }
    }

    public static Properties cargaLastSesionWeb(String context) {
        context = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "");
        try {
            Properties props = new Properties();
            props.load(new FileReader(context + File.separator + LASTSESSION_PROPERTIES));
            return props;
        } catch (Exception e) {
            return new Properties();
        }
    }

    /**
     * Crea los directorios necesarios para el funcionamiento básico del programa. Si los directorios ya existen,
     * no hace nada. Los nombres de los directorios se obtienen de las propiedades del programa.
     * Hago un replace para eliminar el archivo ya que el properties esta la ruta del archivo exacto por lo que elimino eso para
     * poder crear la carpeta
     *
     * @autor Adrián Lara Bonilla
     */
    private static void crearDirectoriosBasicosPrograma() {

        try {
            File[] carpetas = {
                    new File("data"),
                    //new File(cargaProperties().getProperty(DATAPATH_USUARIOS).replace("/usuarios.dat", "")),
                    //new File(cargaProperties().getProperty(DATAPATH_PROPIETARIOS).replace("/propietarios.dat", "")),
                    // new File(cargaProperties().getProperty(DATAPATH_ADMINISTRADORES).replace("/administradores.dat", "")),
                    new File(LASTSESSION_PROPERTIES.replace("/lastSession.properties", "")),
                    new File(cargaProperties().getProperty(DATAPATH_LOGS).replace("/logsFernanbnb.log", "")),
                    new File("recursos"),
                    new File(cargaProperties().getProperty(LISTADO_RESERVAS).replace("/listadoReservas.csv", "")),
                    new File(cargaProperties().getProperty(LISTADO_VIVIENDAS).replace("/listadoViviendas.csv", "")),
                    new File(cargaProperties().getProperty(RESUMEN_RESERVA).replace("/resumenReserva.pdf", ""))
            };
            for (File carpeta : carpetas) {
                if (!carpeta.exists()) carpeta.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearDirectoriosBasicosProgramaWeb(String context) {
        String context2 = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "");
        try {
            File[] carpetas = {
                    new File("data"),
                    //new File(cargaProperties().getProperty(DATAPATH_USUARIOS).replace("/usuarios.dat", "")),
                    //new File(cargaProperties().getProperty(DATAPATH_PROPIETARIOS).replace("/propietarios.dat", "")),
                    // new File(cargaProperties().getProperty(DATAPATH_ADMINISTRADORES).replace("/administradores.dat", "")),
                    new File(context2 + File.separator + LASTSESSION_PROPERTIES.replace("/lastSession.properties", "")),
                    new File(cargaProperties(context).getProperty(DATAPATH_LOGS).replace("/logsFernanbnb.log", "")),
                    new File("recursos"),
                    new File(cargaProperties(context).getProperty(LISTADO_RESERVAS).replace("/listadoReservas.csv", "")),
                    new File(cargaProperties(context).getProperty(LISTADO_VIVIENDAS).replace("/listadoViviendas.csv", "")),
                    new File(cargaProperties(context).getProperty(RESUMEN_RESERVA).replace("/resumenReserva.pdf", ""))
            };
            for (File carpeta : carpetas) {
                if (!carpeta.exists()) carpeta.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Carga el properties leyendo el primer inicio y lo guarda en una boolean para indicar si ha sido el primer inicio de
     * la aplicacion o no, en el caso de que sea la primera vez se cambia a false
     *
     * @return true si es el primer inicio de sesion false si no
     * @autor Adrián Lara Bonilla
     */
    public static boolean primerInicio() {
        Properties props = cargaProperties();
        boolean primerInicio = Boolean.parseBoolean(props.getProperty("primer.inicio", "true"));
        props.setProperty("primer.inicio", "false");
        try {
            props.store(new FileWriter(FERNANBNB_PROPERTIES), "Poner primer.inicio a true para resetear los datos");
        } catch (Exception e) {
            return primerInicio;
        }
        return primerInicio;
    }

    public static boolean primerInicio(String context) {
        Properties props = cargaProperties(context);
        boolean primerInicio = Boolean.parseBoolean(props.getProperty("primer.inicio", "true"));
        props.setProperty("primer.inicio", "false");
        try {
            props.store(new FileWriter(FERNANBNB_PROPERTIES), "Poner primer.inicio a true para resetear los datos");
        } catch (Exception e) {
            return primerInicio;
        }
        return primerInicio;
    }

    /**
     * Carga el properties leyendo el invitado.menu y devuelve true si el archivo de configuracion esta en true
     * o false en caso contrario
     *
     * @return true si es el esta activado el modo invitado false si no
     * @autor Adrián Lara Bonilla
     */
    public static boolean invitadoMenu() {
        Properties props = cargaProperties();
        return Boolean.parseBoolean(props.getProperty("invitado.menu"));
    }

    /**
     * Lee la ruta donde se almacenan el pdf del resumen de la reserva
     *
     * @return un String con la ruta.
     * @autor Adrián Lara Bonilla
     */
    public static String resumenReserva() {
        String ruta = cargaProperties().getProperty(RESUMEN_RESERVA);
        return ruta;
    }

    public static String resumenReservaWeb(String context) {
        String ruta = cargaProperties(context).getProperty(RESUMEN_RESERVA);
        return ruta;
    }

    /**
     * Lee la ruta donde se almacenan las key de comunicaciones
     *
     * @param key es el mensaje que se le envia para saber que poner ej: gmail.user
     * @return un String con los datos pertinentes
     * @autor Adrián Lara Bonilla
     */
    public static String devuelveKeysComunicaciones(String key) {
        String ruta = cargaProperties().getProperty(key);
        return ruta;
    }

    public static String devuelveKeysComunicacionesWeb(String key, String context) {
        String ruta = cargaProperties(context).getProperty(key);
        return ruta;
    }

    /**
     * Lee la ruta donde se almacenan las key de comunicaciones
     *
     * @param key es el mensaje que se le envia para saber que poner ej: gmail.user
     * @return un String con los datos pertinentes
     * @autor Adrián Lara Bonilla
     */
    public static String datosConexionBBDD(String key) {
        String ruta = cargaProperties().getProperty(key);
        return ruta;
    }

    //Para web
    public static String datosConexionBBDD(String key, String context) {
        String ruta = cargaProperties(context).getProperty(key);
        return ruta;

    }

    /**
     * Lee la ruta donde se almacenan el excel del listado de reservas
     *
     * @return un String con la ruta.
     * @autor Adrián Lara Bonilla
     */
    public static String listadoReservas() {
        String ruta = cargaProperties().getProperty(LISTADO_RESERVAS);
        return ruta;
    }

    public static String listadoReservasWeb(String context) {
        String ruta = cargaProperties(context).getProperty(LISTADO_RESERVAS);
        return ruta;
    }


    /**
     * En la ruta indicada crea un listado de reservas en formato excel
     *
     * @param reservas listado de todas las reservas
     * @return true si se ha creado el archivo, false si ha habido algun problema
     * @autor Adrián Lara Bonilla
     */
    public static boolean generaListadoReservas(ArrayList<Reserva> reservas) {
        crearDirectoriosBasicosPrograma();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties().getProperty(LISTADO_RESERVAS)));
            bw.write("ID Reserva;ID Vivienda;Fecha Inicio;Fecha Fin;Precio;Ocupantes;Reserva pendiente;\n");
            for (Reserva r : reservas) {
                LocalDate fechaFinReservaTemp = r.getFechaInicio().plusDays(r.getNoches());
                boolean reservaPendiente = fechaFinReservaTemp.isAfter(LocalDate.now());
                bw.write(r.getId() + ";" + r.getIdVivienda() + ";" + Utils.formateaFecha(r.getFechaInicio()) + ";" +
                        Utils.formateaFecha(fechaFinReservaTemp) + ";" + r.getPrecio() + ";" + r.getOcupantes() + ";" +
                        reservaPendiente + "\n");
            }
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean generaListadoReservasWeb(ArrayList<Reserva> reservas, String context) {
        crearDirectoriosBasicosProgramaWeb(context);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties(context).getProperty(LISTADO_RESERVAS)));
            bw.write("ID Reserva;ID Vivienda;Fecha Inicio;Fecha Fin;Precio;Ocupantes;Reserva pendiente;\n");
            for (Reserva r : reservas) {
                LocalDate fechaFinReservaTemp = r.getFechaInicio().plusDays(r.getNoches());
                boolean reservaPendiente = fechaFinReservaTemp.isAfter(LocalDate.now());
                bw.write(r.getId() + ";" + r.getIdVivienda() + ";" + Utils.formateaFecha(r.getFechaInicio()) + ";" +
                        Utils.formateaFecha(fechaFinReservaTemp) + ";" + r.getPrecio() + ";" + r.getOcupantes() + ";" +
                        reservaPendiente + "\n");
            }
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Registra el inicio sesion o cierre de sesion de los usuarios en el archivo de logs del sistema
     *
     * @param user       el objeto que representa al usuario que inició o cerró sesión
     * @param tipoSesion el tipo de sesión, por ejemplo "inicio sesion" o "cierre sesion"
     * @throws IOException si ocurre un error al escribir en el archivo de registro
     * @autor Adrián Lara Bonilla
     */
    public static void logSesion(Object user, String tipoSesion) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties().getProperty(DATAPATH_LOGS), true));
            if (user instanceof Usuario)
                bw.write(tipoSesion + ";" + ((Usuario) user).getNombre() + "(" + ((Usuario) user).getId() + ")" +
                        ";Usuario normal;" + fechaActual.format(formatter) + "\n");
            if (user instanceof Propietario)
                bw.write(tipoSesion + ";" + ((Propietario) user).getNombre() + "(" + ((Propietario) user).getId() + ")" +
                        ";Usuario propietario;" + fechaActual.format(formatter) + "\n");
            if (user instanceof Administrador)
                bw.write(tipoSesion + ";" + ((Administrador) user).getNombre() + "(" + ((Administrador) user).getId() + ")" +
                        ";Usuario administrador;" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public static void logSesionWeb(Object user, String tipoSesion, String context) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties(context).getProperty(DATAPATH_LOGS), true));
            if (user instanceof Usuario)
                bw.write(tipoSesion + ";" + ((Usuario) user).getNombre() + "(" + ((Usuario) user).getId() + ")" +
                        ";Usuario normal;" + fechaActual.format(formatter) + "\n");
            if (user instanceof Propietario)
                bw.write(tipoSesion + ";" + ((Propietario) user).getNombre() + "(" + ((Propietario) user).getId() + ")" +
                        ";Usuario propietario;" + fechaActual.format(formatter) + "\n");
            if (user instanceof Administrador)
                bw.write(tipoSesion + ";" + ((Administrador) user).getNombre() + "(" + ((Administrador) user).getId() + ")" +
                        ";Usuario administrador;" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }


    /**
     * Registra el una nueva reserva o periodo de no disponibilidad en el archivo de logs del sistema
     *
     * @param user       el objeto que representa al usuario que hizo la nueva reserva o periodo de no disponibilidad
     * @param idVivienda id de la vivienda de la reserva
     * @throws IOException si ocurre un error al escribir en el archivo de registro
     * @autor Adrián Lara Bonilla
     */
    public static void logNuevaReserva(Object user, int idVivienda) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties().getProperty(DATAPATH_LOGS), true));
            if (user instanceof Usuario)
                bw.write("Nueva reserva;" + ((Usuario) user).getNombre() + "(" + ((Usuario) user).getId() + ")" +
                        ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            if (user instanceof Propietario)
                bw.write("Nuevo periodo no disponibilidad;" + ((Propietario) user).getNombre() + "(" + ((Propietario) user).getId() + ")" +
                        ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public static void logNuevaReservaWeb(Object user, int idVivienda, String context) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties(context).getProperty(DATAPATH_LOGS), true));
            if (user instanceof Usuario)
                bw.write("Nueva reserva;" + ((Usuario) user).getNombre() + "(" + ((Usuario) user).getId() + ")" +
                        ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            if (user instanceof Propietario)
                bw.write("Nuevo periodo no disponibilidad;" + ((Propietario) user).getNombre() + "(" + ((Propietario) user).getId() + ")" +
                        ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }

    /**
     * Registra la creacion o la eliminacion de una vivienda hecha por un usuario
     *
     * @param user       el objeto que representa al usuario que creo o elimino vivienda
     * @param idVivienda id de la vivienda de la reserva
     * @throws IOException si ocurre un error al escribir en el archivo de registro
     * @autor Adrián Lara Bonilla
     */
    public static void logVivienda(Propietario user, int idVivienda, String tipoLogVivienda) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties().getProperty(DATAPATH_LOGS), true));
            bw.write(tipoLogVivienda + ";" + user.getNombre() + "(" + user.getId() + ")" +
                    ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }

    public static void logViviendaWeb(Propietario user, int idVivienda, String tipoLogVivienda, String context) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties(context).getProperty(DATAPATH_LOGS), true));
            bw.write(tipoLogVivienda + ";" + user.getNombre() + "(" + user.getId() + ")" +
                    ";ID de la vivienda: " + idVivienda + ";" + fechaActual.format(formatter) + "\n");
            bw.close();
        } catch (Exception e) {
            System.out.print("");
        }
    }


    /**
     * Guarda el id con la fecha de la ultima conexion del usuario
     *
     * @param idUser el id que representa al usuario que inició sesión
     * @autor Adrián Lara Bonilla
     */
    public static void guardaLastSession(int idUser) {
        crearDirectoriosBasicosPrograma();
        Properties props = cargaLastSesion();
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        props.setProperty(String.valueOf(idUser), fechaActual.format(formatter).replace("-", "/"));
        try {
            props.store(new FileWriter(LASTSESSION_PROPERTIES), "LASTS SESSIONS");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public static void guardaLastSessionWeb(int idUser, String context) {
        crearDirectoriosBasicosProgramaWeb(context);
        Properties props = cargaLastSesionWeb(context);
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String context2 = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "");
        props.setProperty(String.valueOf(idUser), fechaActual.format(formatter).replace("-", "/"));
        try {
            props.store(new FileWriter(LASTSESSION_PROPERTIES), "LASTS SESSIONS");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Carga con el id la fecha de la ultima conexion del usuario
     *
     * @param idUser el id que representa al usuario que inició sesión
     * @autor Adrián Lara Bonilla
     */
    public static String cargaLastSession(int idUser) {
        Properties props = cargaLastSesion();
        String fechaLastSession = String.valueOf(props.getProperty(String.valueOf(idUser), ""));
        return fechaLastSession;
    }

    public static String cargaLastSessionWeb(int idUser, String context) {
        Properties props = cargaLastSesionWeb(context);
        String fechaLastSession = String.valueOf(props.getProperty(String.valueOf(idUser), ""));
        return fechaLastSession;
    }

    /**
     * Lee el documento de configuracion y lo guarda en un string
     *
     * @return la configuracion en un string o vacio si hay algun problema
     * @autor Adrián Lara Bonilla
     */
    public static String leerConfiguracionProperties() {
        String contenido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(FERNANBNB_PROPERTIES));
            String linea = br.readLine();
            while (linea != null) {
                contenido = contenido.concat(linea) + "\n";
                linea = br.readLine();
            }
            br.close();
            return contenido;
        } catch (Exception e) {
            return "";
        }
    }

    public static String leerConfiguracionPropertiesWeb(String context) {
        String contenido = "";
        context = context.replace("\\target\\fernanbnb-1.0-SNAPSHOT\\", "");
        try {
            BufferedReader br = new BufferedReader(new FileReader(context + File.separator + FERNANBNB_PROPERTIES));
            String linea = br.readLine();
            while (linea != null) {
                contenido = contenido.concat(linea) + " <br>\n";
                linea = br.readLine();
            }
            br.close();
            return contenido;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Lee el documento de ultima sesiones donde esta el id del usuario y su ultima conexion y lo guarda en un string
     *
     * @return los datos en un string o vacio si hay algun problema
     * @autor Adrián Lara Bonilla
     */
    public static String leerLastSesionUsers() {
        String contenido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(LASTSESSION_PROPERTIES));
            String linea = br.readLine();
            while (linea != null) {
                contenido = contenido.concat(linea).replace("\\", "") + "\n";
                linea = br.readLine();
            }
            br.close();
            return contenido;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Realiza una copia de seguridad de los archivos de datos del sistema en la ruta especificada.
     *
     * @param ruta            La ruta donde se guardará la copia de seguridad.
     * @param usuarios
     * @param propietarios
     * @param administradores
     * @return true si se realizó la copia de seguridad correctamente, false si hubo algún error.
     * @autor Adrián Lara Bonilla
     */
    public static boolean guardaBackup(String ruta, ArrayList<Usuario> usuarios, ArrayList<Propietario> propietarios, ArrayList<Administrador> administradores) {
        try {
            File pathData = new File(System.getProperty("user.home") + ruta);
            //Si no existe ese directorio lo crearemos
            if (!pathData.exists()) pathData.mkdirs();

            FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + ruta + "backupUsuarios.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(usuarios);

            fos = new FileOutputStream(System.getProperty("user.home") + ruta + "backupPropietarios.dat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(propietarios);

            fos = new FileOutputStream(System.getProperty("user.home") + ruta + "backupAdministradores.dat");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(administradores);

            oos.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }



    /**
     * Carga el archivo backup del listado de usuarios
     *
     * @param ruta La ruta donde se guardará la copia de seguridad.
     * @return el listado de usuarios del backup si se ha logrado correctamente o un listado vacio si ha ocurrido un error
     * @autor Adrián Lara Bonilla
     */
    public static ArrayList<Usuario> cargaBackupUsuarios(String ruta) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        File pathData = new File(System.getProperty("user.home") + ruta);
        //Si no existe ese directorio lo crearemos
        if (!pathData.exists()) return usuarios;
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + ruta + "backupUsuarios.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            usuarios = (ArrayList<Usuario>) ois.readObject();
            ois.close();
            return usuarios;
        } catch (Exception e) {
            return usuarios;
        }
    }

    /**
     * Carga el archivo backup del listado de Propietarios
     *
     * @param ruta La ruta donde se guardará la copia de seguridad.
     * @return el listado de Propietarios del backup si se ha logrado correctamente o un listado vacio si ha ocurrido un error
     * @autor Adrián Lara Bonilla
     */
    public static ArrayList<Propietario> cargaBackupPropietarios(String ruta) {
        ArrayList<Propietario> propietarios = new ArrayList<>();
        File pathData = new File(System.getProperty("user.home") + ruta);
        //Si no existe ese directorio lo crearemos
        if (!pathData.exists()) return propietarios;
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + ruta + "backupPropietarios.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            propietarios = (ArrayList<Propietario>) ois.readObject();
            ois.close();
            return propietarios;
        } catch (Exception e) {
            return propietarios;
        }
    }

    /**
     * Carga el archivo backup del listado de Administrador
     *
     * @param ruta La ruta donde se guardará la copia de seguridad.
     * @return el listado de Administradores del backup si se ha logrado correctamente o un listado vacio si ha ocurrido un error
     * @autor Adrián Lara Bonilla
     */
    public static ArrayList<Administrador> cargaBackupAdministradores(String ruta) {
        ArrayList<Administrador> administradores = new ArrayList<>();
        File pathData = new File(System.getProperty("user.home") + ruta);
        //Si no existe ese directorio lo crearemos
        if (!pathData.exists()) return administradores;
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.home") + ruta + "backupAdministradores.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            administradores = (ArrayList<Administrador>) ois.readObject();
            ois.close();
            return administradores;
        } catch (Exception e) {
            return administradores;
        }
    }


    public static boolean generaListadoViviendas(ArrayList<Vivienda> viviendas) {
        crearDirectoriosBasicosPrograma();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(cargaProperties().getProperty(LISTADO_VIVIENDAS)));
            bw.write("Titulo Vivienda;Localidad;Provincia;Maximo ocupantes;Precio Noche;Tiene Reserva;\n");
            for (Vivienda v : viviendas) {

                boolean tieneReserva = true;
                if (v.getReservas().isEmpty()) tieneReserva = false;
                bw.write(v.getTitulo() + ";" + v.getLocalidad() + ";" + v.getProvincia() + ";" + v.getMaxOcupantes() + ";" +
                        v.getPrecioNoche() + ";" + tieneReserva + ";\n");
            }
            bw.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lee la ruta donde se almacenan el excel del listado de viviendas
     *
     * @return un String con la ruta.
     * @autor Adrián Lara Bonilla
     */
    public static String listadoViviendas() {
        String ruta = cargaProperties().getProperty(LISTADO_VIVIENDAS);
        return ruta;
    }




    /* *//**
     * Guarda la lista de usuarios especificada en un archivo ubicado en la ruta definida en la propiedad "datapath.usuarios"
     * del archivo de configuración "fernanbnb.properties". Devuelve verdadero si la operación se realizó correctamente
     * y falso si se produjo un error al guardar la lista de usuarios.
     *
     * @param usuarios lista de objetos Usuario a guardar en el archivo
     * @return verdadero si la operación se realizó correctamente y falso si se produjo un error al guardar la lista de usuarios
     * @autor Adrián Lara Bonilla
     *//*
  public static boolean guardarListadoUsuarios(ArrayList<Usuario> usuarios) {
    crearDirectoriosBasicosPrograma();
    try {
      FileOutputStream fos = new FileOutputStream(cargaProperties().getProperty(DATAPATH_USUARIOS));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(usuarios);
      oos.close();

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  *//**
     * Carga y devuelve una lista de objetos Usuario a partir del archivo ubicado en la ruta definida en la propiedad
     * "datapath.usuarios" del archivo de configuración "fernanbnb.properties". Si se produce un error al cargar la lista
     * de usuarios, se devuelve una lista vacía.
     *
     * @return lista de objetos Usuario cargada desde el archivo o una lista vacía si se produjo un error al cargar la lista
     * de usuarios
     * @autor Adrián Lara Bonilla
     *//*
  public static ArrayList<Usuario> cargarListadoUsuarios() {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    try {
      FileInputStream fis = new FileInputStream(cargaProperties().getProperty(DATAPATH_USUARIOS));
      ObjectInputStream ois = new ObjectInputStream(fis);
      usuarios = (ArrayList<Usuario>) ois.readObject();
      ois.close();
      return usuarios;
    } catch (Exception e) {
      return usuarios;
    }
  }

  *//**
     * Guarda la lista de propietarios especificada en un archivo ubicado en la ruta definida en la propiedad "datapath.propietarios"
     * del archivo de configuración "fernanbnb.properties". Devuelve verdadero si la operación se realizó correctamente
     * y falso si se produjo un error al guardar la lista de usuarios.
     *
     * @param propietarios lista de objetos Propietario a guardar en el archivo
     * @return verdadero si la operación se realizó correctamente y falso si se produjo un error al guardar la lista de propietarios
     * @autor Adrián Lara Bonilla
     *//*
  public static boolean guardarListadoPropietarios(ArrayList<Propietario> propietarios) {
    crearDirectoriosBasicosPrograma();
    try {
      FileOutputStream fos = new FileOutputStream(cargaProperties().getProperty(DATAPATH_PROPIETARIOS));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(propietarios);
      oos.close();

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  *//**
     * Carga y devuelve una lista de objetos Propietario a partir del archivo ubicado en la ruta definida en la propiedad
     * "datapath.propietarios" del archivo de configuración "fernanbnb.properties". Si se produce un error al cargar la lista
     * de propietarios, se devuelve una lista vacía.
     *
     * @return lista de objetos Propietario cargada desde el archivo o una lista vacía si se produjo un error al cargar la lista
     * de propietarios
     * @autor Adrián Lara Bonilla
     *//*
  public static ArrayList<Propietario> cargarListadoPropietarios() {
    ArrayList<Propietario> propietarios = new ArrayList<>();
    try {
      FileInputStream fis = new FileInputStream(cargaProperties().getProperty(DATAPATH_PROPIETARIOS));
      ObjectInputStream ois = new ObjectInputStream(fis);
      propietarios = (ArrayList<Propietario>) ois.readObject();
      ois.close();
      return propietarios;
    } catch (Exception e) {
      return propietarios;
    }
  }

  *//**
     * Guarda la lista de administradores especificada en un archivo ubicado en la ruta definida en la propiedad "datapath.administradores"
     * del archivo de configuración "fernanbnb.properties". Devuelve verdadero si la operación se realizó correctamente
     * y falso si se produjo un error al guardar la lista de usuarios.
     *
     * @param administradores lista de objetos Administrador a guardar en el archivo
     * @return verdadero si la operación se realizó correctamente y falso si se produjo un error al guardar la lista de administradores
     * @autor Adrián Lara Bonilla
     *//*
  public static boolean guardarListadoAdministradores(ArrayList<Administrador> administradores) {
    crearDirectoriosBasicosPrograma();
    try {
      FileOutputStream fos = new FileOutputStream(cargaProperties().getProperty(DATAPATH_ADMINISTRADORES));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(administradores);
      oos.close();

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  *//**
     * Carga y devuelve una lista de objetos Administrador a partir del archivo ubicado en la ruta definida en la propiedad
     * "datapath.administradores" del archivo de configuración "fernanbnb.properties". Si se produce un error al cargar la lista
     * de administradores, se devuelve una lista vacía.
     *
     * @return lista de objetos Administrador cargada desde el archivo o una lista vacía si se produjo un error al cargar la lista
     * de administradores
     * @autor Adrián Lara Bonilla
     *//*
  public static ArrayList<Administrador> cargarListadoAdministradores() {
    ArrayList<Administrador> administradores = new ArrayList<>();
    try {
      FileInputStream fis = new FileInputStream(cargaProperties().getProperty(DATAPATH_ADMINISTRADORES));
      ObjectInputStream ois = new ObjectInputStream(fis);
      administradores = (ArrayList<Administrador>) ois.readObject();
      ois.close();
      return administradores;
    } catch (Exception e) {
      return administradores;
    }
  }
*/
}

