package dao;

import models.Reserva;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOReservaSQL implements DAOReserva {
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
   * Inserta una nueva reserva en la base de datos.
   *
   * @param r   El objeto Reserva a insertar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si la inserción fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean insert(Reserva r, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

     INSERT INTO Reservas VALUES (
     id(int) primary key,
     idVivienda(int) Foreing key,
     idUsuario(int) Foreing key,
     'fecha',
     numNoches(int),
     precio(double),
     ocupantes (int)
     );
     */

    sentencia = String.format("""
                    INSERT INTO Reservas VALUES (
                        %s,
                        %s,
                        %s,
                       '%s',
                        %s,
                        %s,
                        %s
                    );
                    """, r.getId(), r.getIdVivienda(), r.getIdUsuario(), r.getFechaInicio(), r.getNoches(),
            r.getPrecio(), r.getOcupantes());

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
   * Actualiza una reserva existente en la base de datos.
   *
   * @param r   El objeto Reserva con los nuevos datos a actualizar.
   * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
   * @return true si la actualización fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean update(Reserva r, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    /*Asi quedaría la sentencia

      UPDATE Reservas SET
      fechaInicio = 'fechaInicio',
      numNoches = numNoches,
      precio = precio,
      ocupantes = ocupantes
      where id = id(int);
     */
    sentencia = String.format("""
            UPDATE Reservas SET
            fechaInicio = '%s',
            numNoches = %s,
            precio = %s,
            ocupantes = %s
            where id = %s;
            """, r.getFechaInicio(), r.getNoches(), r.getPrecio(), r.getOcupantes(), r.getId());

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
   * Elimina una reserva de la base de datos.
   *
   * @param r   La reserva a eliminar.
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si la eliminación fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean delete(Reserva r, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Reservas where id = '" + r.getId() + "';";

     /*Asi quedaría la sentencia

     DELETE FROM Reservas where id = 'id';
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
   * Elimina todos los datos de la tabla Reservas en la base de datos.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si la eliminación fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean deleteAllData(DAOManager dao) {
    conectarBBDD(dao);
    String sentencia;
    sentencia = "DELETE FROM Reservas;";
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
   * Lee una reserva de la base de datos utilizando su ID.
   *
   * @param idReserva El ID de la reserva a leer.
   * @param dao       El DAOManager utilizado para acceder a la base de datos.
   * @return La reserva encontrada, o null si no se encontró ninguna reserva con el ID especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Reserva readByID(int idReserva, DAOManager dao) {
    conectarBBDD(dao);
    Reserva reserva = null;
    String sentencia = "SELECT * FROM Reservas where id = " + idReserva + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        reserva = new Reserva(
                rs.getInt("id"),
                rs.getInt("idVivienda"),
                rs.getInt("idUsuario"),
                LocalDate.parse(rs.getString("fechaInicio")),
                rs.getInt("numNoches"),
                rs.getDouble("precio"),
                rs.getInt("ocupantes")
        );
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return reserva;
  }

  /**
   * Lee un listado de reservas de un usuario en la base de datos utilizando su ID de usuario.
   *
   * @param idUsuario El ID del usuario para el cual se desea obtener el listado de reservas.
   * @param dao       El DAOManager utilizado para acceder a la base de datos.
   * @return Un ArrayList de Reserva que contiene las reservas del usuario, o una lista vacía si no se encontraron reservas.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Reserva> readListadoReservasUsuario(int idUsuario, DAOManager dao) {
    conectarBBDD(dao);
    ArrayList<Reserva> reservas = new ArrayList<>();
    Reserva reserva;
    String sentencia = "SELECT * FROM Reservas where idUsuario = " + idUsuario + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        reserva = new Reserva(
                rs.getInt("id"),
                rs.getInt("idVivienda"),
                rs.getInt("idUsuario"),
                LocalDate.parse(rs.getString("fechaInicio")),
                rs.getInt("numNoches"),
                rs.getDouble("precio"),
                rs.getInt("ocupantes")
        );
        reservas.add(reserva);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return reservas;
  }

  /**
   * Lee un listado de reservas de una vivienda en la base de datos utilizando su ID de vivienda.
   *
   * @param idVivienda El ID de la vivienda para la cual se desea obtener el listado de reservas.
   * @param dao        El DAOManager utilizado para acceder a la base de datos.
   * @return Un ArrayList de Reserva que contiene las reservas de la vivienda, o una lista vacía si no se encontraron reservas.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Reserva> readListadoReservasVivienda(int idVivienda, DAOManager dao) {
    conectarBBDD(dao);
    ArrayList<Reserva> reservas = new ArrayList<>();
    Reserva reserva;
    String sentencia = "SELECT * FROM Reservas where idVivienda = " + idVivienda + ";";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        reserva = new Reserva(
                rs.getInt("id"),
                rs.getInt("idVivienda"),
                rs.getInt("idUsuario"),
                LocalDate.parse(rs.getString("fechaInicio")),
                rs.getInt("numNoches"),
                rs.getDouble("precio"),
                rs.getInt("ocupantes")
        );
        reservas.add(reserva);
      }

    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return reservas;
  }

  /**
   * Lee un listado de todas las reservas en la base de datos.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return Un ArrayList de Reserva que contiene todas las reservas en la base de datos, o una lista vacía si no se encontraron reservas.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Reserva> readAllListadoReservas(DAOManager dao) {
    conectarBBDD(dao);
    ArrayList<Reserva> reservas = new ArrayList<>();
    Reserva reserva;
    String sentencia = "SELECT * FROM Reservas;";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        reserva = new Reserva(
                rs.getInt("id"),
                rs.getInt("idVivienda"),
                rs.getInt("idUsuario"),
                LocalDate.parse(rs.getString("fechaInicio")),
                rs.getInt("numNoches"),
                rs.getDouble("precio"),
                rs.getInt("ocupantes")
        );
        reservas.add(reserva);
      }

    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return reservas;
  }
}
