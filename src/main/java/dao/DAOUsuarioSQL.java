package dao;

import models.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOUsuarioSQL implements DAOUsuario {
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
   * Inserta un nuevo usuario en la base de datos.
   *
   * @param u   El usuario que se desea insertar en la base de datos.
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si el usuario se insertó correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean insert(Usuario u, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia
     INSERT INTO Usuarios VALUES (
     id(int) primary key,
     'nombre',
     'clave',
     'correo(unique)',
     verificado (boolean)
     );
     */

    sentencia = String.format("""
            INSERT INTO Usuarios VALUES (
                %s,
               '%s',
               '%s',
               '%s',
                %s
            );
            """, u.getId(), u.getNombre(), u.getClave(), u.getEmail(), u.isVerificado());


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
   * Actualiza los datos de un usuario en la base de datos.
   *
   * @param u   El usuario con los nuevos datos que se desean actualizar en la base de datos.
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si la actualización se realizó correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean update(Usuario u, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

      UPDATE Usuarios SET
      nombre = 'nombre',
      clave = 'clave',
      email = 'email',
      verificado = verificado(boolean)
      where id = id(int(;
     */
    sentencia = String.format("""
            UPDATE Usuarios SET
            nombre = '%s',
            clave = '%s',
            email = '%s',
            verificado = %s
            where id = %s;
            """, u.getNombre(), u.getClave(), u.getEmail(), u.isVerificado(), u.getId());

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
   * Elimina un usuario de la base de datos.
   *
   * @param u   El usuario que se desea eliminar de la base de datos.
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si el usuario se eliminó correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean delete(Usuario u, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Usuarios where id = '" + u.getId() + "';";

     /*Asi quedaría la sentencia

     DELETE FROM Usuarios where id = 'id';
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
   * Elimina todos los datos de la tabla Usuarios en la base de datos.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si se eliminaron los datos correctamente, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean deleteAllData(DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Usuarios;";
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
   * Lee un usuario de la base de datos mediante su ID.
   *
   * @param idUsuario El ID del usuario que se desea leer.
   * @param dao       El DAOManager utilizado para acceder a la base de datos.
   * @return El usuario correspondiente al ID especificado, o null si no se encuentra en la base de datos.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Usuario readByID(int idUsuario, DAOManager dao) {
    conectarBBDD(dao);
    Usuario user = null;
    String sentencia = "SELECT * FROM Usuarios where id = " + idUsuario + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        user = new Usuario(
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
    return user;
  }

  /**
   * Lee un usuario de la base de datos mediante su dirección de correo electrónico.
   *
   * @param email El correo electrónico del usuario que se desea leer.
   * @param dao   El DAOManager utilizado para acceder a la base de datos.
   * @return El usuario correspondiente al correo electrónico especificado, o null si no se encuentra en la base de datos.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Usuario readByEmail(String email, DAOManager dao) {
    conectarBBDD(dao);
    Usuario user = null;
    String sentencia = "SELECT * FROM Usuarios where email = '" + email + "';";
//    System.out.println(sentencia);
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        user = new Usuario(
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
    return user;
  }

  /**
   * Lee un listado de usuarios de la base de datos.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return Un ArrayList de usuarios que contiene todos los usuarios de la base de datos.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Usuario> readListadoUsuarios(DAOManager dao) {
    conectarBBDD(dao);
    Usuario user;
    ArrayList<Usuario> usuarios = new ArrayList<>();
    String sentencia = "SELECT * FROM Usuarios;";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        user = new Usuario(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("clave"),
                rs.getString("email"),
                rs.getBoolean("verificado")
        );
        usuarios.add(user);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return usuarios;
  }
}
