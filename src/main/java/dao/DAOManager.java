package dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static persistencia.Persistencia.datosConexionBBDD;

public class DAOManager {
  private Connection conn;
  private final String USER;
  private final String PASS;
  private final String URL;
  private static DAOManager singlenton; //Atributo estatico que guarda una referencia al dao

  private DAOManager() { //Contructor privado para que no se pueda llamar cuando quiera
    this.conn = null;
    this.USER = datosConexionBBDD("user.bbdd"); //usuario de la BBDD
    this.PASS = datosConexionBBDD("pass.bbdd"); //pass de la BBDD
    this.URL = "jdbc:mysql://"+datosConexionBBDD("servidor.bbdd"); //Servidor de la bbdd a usar
  }
  private DAOManager(String context) { //Contructor privado para que no se pueda llamar cuando quiera
    this.conn = null;
    this.USER = datosConexionBBDD("user.bbdd",context); //usuario de la BBDD
    this.PASS = datosConexionBBDD("pass.bbdd",context); //pass de la BBDD
    this.URL = "jdbc:mysql://"+datosConexionBBDD("servidor.bbdd", context); //Servidor de la bbdd a usar
  }

  // Este metodo devuelve el DAO, si el atributo estatico ya ha sido inicializado devuelve el DAO anterior
  public static DAOManager getSinglentonInstance() {
    if (singlenton == null) {
      singlenton = new DAOManager();
    }
    return singlenton;
  }
  public static DAOManager getSinglentonInstance(String context) {
    if (singlenton == null) {
      singlenton = new DAOManager(context);
    }
    return singlenton;
  }

  public Connection getConn() {
    return conn;
  }

  public void open() throws Exception {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver"); //cargo el driver de conexion jdbc
      conn = DriverManager.getConnection(URL, USER, PASS); //Uso la clave driveManager para crear la conexion
    } catch (Exception e) {
      throw e;
    }
  }

  public void close() throws SQLException {
    try {
      if (this.conn != null) this.conn.close();
    } catch (Exception e) {
      throw e;
    }
  }
}
