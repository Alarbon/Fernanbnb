package dao;

import models.Propietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOPropietarioSQL implements DAOPropietario {
  public boolean conectarBBDD(DAOManager dao) {
    try {
      dao.open();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean desconectarBBDD(DAOManager dao) {
    try {
      dao.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Inserta un objeto Propietario en la base de datos.
   *
   * @param p   El objeto Propietario a insertar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se realizó la inserción correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean insert(Propietario p, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

     INSERT INTO Propietarios VALUES (
     id(int) primary key,
     'nombre',
     'clave',
     'correo(unique)',
     verificado (boolean)
     );
     */

    sentencia = String.format("""
            INSERT INTO Propietarios VALUES (
                %s,
               '%s',
               '%s',
               '%s',
                %s
            );
            """, p.getId(), p.getNombre(), p.getClave(), p.getEmail(), p.isVerificado());


    try (Statement stmt = dao.getConn().createStatement()) {
      //Enviamos el comando insert
      stmt.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      desconectarBBDD(dao);
      return false;
    }
  }

  @Override
  public boolean update(Propietario p, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

      UPDATE Propietarios SET
      nombre = 'nombre',
      clave = 'clave',
      email = 'email',
      verificado = verificado(boolean)
      where id = id(int(;
     */
    sentencia = String.format("""
            UPDATE Propietarios SET
            nombre = '%s',
            clave = '%s',
            email = '%s',
            verificado = %s
            where id = %s;
            """, p.getNombre(), p.getClave(), p.getEmail(), p.isVerificado(), p.getId());

    try (Statement stmt = dao.getConn().createStatement()) {
      //Enviamos el comando insert
      stmt.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }
  }


  /**
   * Inserta un objeto Propietario en la base de datos.
   *
   * @param p   El objeto Propietario a insertar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se realizó la inserción correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean delete(Propietario p, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Propietarios where id = '" + p.getId() + "';";

     /*Asi quedaría la sentencia

     DELETE FROM Propietarios where id = 'id';
     */

    try (Statement stmt = dao.getConn().createStatement()) {
      //enviar el comando delete
      stmt.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }

  }

  /**
   * Elimina todos los datos de la tabla Propietarios en la base de datos.
   *
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se eliminaron todos los datos correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean deleteAllData(DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Propietarios;";
    try (Statement stmt = dao.getConn().createStatement()) {
      //enviar el comando delete
      stmt.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }

  }

  /**
   * Lee un objeto Propietario de la base de datos utilizando su ID.
   *
   * @param idPropietario El ID del Propietario a leer.
   * @param dao           El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return El objeto Propietario leído de la base de datos, o null si no se encontró ningún Propietario con el ID especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Propietario readByID(int idPropietario, DAOManager dao) {
    conectarBBDD(dao);
    Propietario prop = null;
    String sentencia = "SELECT * FROM Propietarios where id = " + idPropietario + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        prop = new Propietario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("clave"),
                rs.getString("email"),
                rs.getBoolean("verificado")
        );
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return prop;
  }

  /**
   * Lee un objeto Propietario de la base de datos utilizando su correo electrónico.
   *
   * @param email El correo electrónico del Propietario a leer.
   * @param dao   El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return El objeto Propietario leído de la base de datos, o null si no se encontró ningún Propietario con el correo electrónico especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Propietario readByEmail(String email, DAOManager dao) {
    conectarBBDD(dao);
    Propietario prop = null;
    String sentencia = "SELECT * FROM Propietarios where email = '" + email + "';";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        prop = new Propietario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("clave"),
                rs.getString("email"),
                rs.getBoolean("verificado")
        );
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return prop;
  }

  /**
   * Lee una lista de objetos Propietario de la base de datos.
   *
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return Una lista de objetos Propietario leídos de la base de datos. Si no se encontraron Propietarios, la lista estará vacía.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Propietario> readListadoPropietarios(DAOManager dao) {
    conectarBBDD(dao);
    Propietario prop;
    ArrayList<Propietario> propietarios = new ArrayList<>();
    String sentencia = "SELECT * FROM Propietarios;";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        prop = new Propietario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("clave"),
                rs.getString("email"),
                rs.getBoolean("verificado")
        );
        propietarios.add(prop);
      }

    } catch (
            Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return propietarios;
  }
}
