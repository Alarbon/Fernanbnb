package dao;

import models.Administrador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOAdministradorSQL implements DAOAdministrador {
  public boolean conectarBBDD(DAOManager dao) {
    try {
      dao.open();
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }
  }

  public boolean desconectarBBDD(DAOManager dao) {
    try {
      dao.close();
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }
  }

  /**
   * Inserta un objeto Administrador en la base de datos.
   *
   * @param a   El objeto Administrador a insertar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se realizó la inserción correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean insert(Administrador a, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

     INSERT INTO Administradores VALUES (
     id(int) primary key,
     'nombre',
     'clave',
     'correo(unique)',
     verificado (boolean)
     );
     */

    sentencia = String.format("""
            INSERT INTO Administradores VALUES (
                %s,
               '%s',
               '%s',
               '%s',
                %s
            );
            """, a.getId(), a.getNombre(), a.getClave(), a.getEmail(), a.isVerificado());


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
   * Actualiza los datos de un objeto Administrador en la base de datos.
   *
   * @param a   El objeto Administrador con los nuevos datos.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se realizó la actualización correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean update(Administrador a, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

      UPDATE Administradores SET
      nombre = 'nombre',
      clave = 'clave',
      email = 'email',
      verificado = verificado(boolean)
      where id = id(int(;
     */
    sentencia = String.format("""
            UPDATE Administradores SET
            nombre = '%s',
            clave = '%s',
            email = '%s',
            verificado = %s
            where id = %s;
            """, a.getNombre(), a.getClave(), a.getEmail(), a.isVerificado(), a.getId());

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
   * Elimina un objeto Administrador de la base de datos.
   *
   * @param a   El objeto Administrador a eliminar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se realizó la eliminación correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean delete(Administrador a, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Administradores where id = '" + a.getId() + "';";

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
   * Elimina todos los datos de la tabla Administradores en la base de datos.
   *
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si se eliminaron todos los datos correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean deleteAllData(DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Administradores;";
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
   * Lee un objeto Administrador de la base de datos utilizando su ID.
   *
   * @param idAdministrador El ID del Administrador a leer.
   * @param dao             El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return El objeto Administrador leído de la base de datos, o null si no se encontró ningún Administrador con el ID especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Administrador readByID(int idAdministrador, DAOManager dao) {
    conectarBBDD(dao);
    Administrador adm = null;
    String sentencia = "SELECT * FROM Administradores where id = " + idAdministrador + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        adm = new Administrador(rs.getInt("id"), rs.getString("nombre"), rs.getString("clave"), rs.getString("email"), rs.getBoolean("verificado"));
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return adm;
  }

  /**
   * Lee un objeto Administrador de la base de datos utilizando su dirección de correo electrónico.
   *
   * @param email El correo electrónico del Administrador a leer.
   * @param dao   El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return El objeto Administrador leído de la base de datos, o null si no se encontró ningún Administrador con el correo electrónico especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Administrador readByEmail(String email, DAOManager dao) {
    conectarBBDD(dao);
    Administrador adm = null;
    String sentencia = "SELECT * FROM Administradores where email = '" + email + "';";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        adm = new Administrador(rs.getInt("id"), rs.getString("nombre"), rs.getString("clave"), rs.getString("email"), rs.getBoolean("verificado"));
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return adm;
  }

  /**
   * Lee un listado de objetos Administrador de la base de datos.
   *
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return Un ArrayList que contiene todos los objetos Administrador leídos de la base de datos.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Administrador> readListadoAdministradores(DAOManager dao) {
    conectarBBDD(dao);
    Administrador adm;
    ArrayList<Administrador> administradores = new ArrayList<>();
    String sentencia = "SELECT * FROM Administradores;";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        adm = new Administrador(rs.getInt("id"), rs.getString("nombre"), rs.getString("clave"), rs.getString("email"), rs.getBoolean("verificado"));
        administradores.add(adm);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return administradores;
  }
}
