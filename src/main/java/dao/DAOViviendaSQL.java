package dao;

import models.Propietario;
import models.Vivienda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOViviendaSQL implements DAOVivienda {
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
   * Inserta una nueva vivienda en la base de datos.
   *
   * @param v   La vivienda a insertar.
   * @param p   El propietario de la vivienda.
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si la inserción fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean insert(Vivienda v, Propietario p, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia = String.format("""
            INSERT INTO Viviendas VALUES (
                    %s,
                    %s,
                   '%s',
                   '%s',
                   '%s',
                   '%s',
                    %s,
                    %s,
                    true
                );
            """, v.getId(), p.getId(), v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getMaxOcupantes(), v.getPrecioNoche());
    //  System.out.println(sentencia);
    try {
      Statement smtt = dao.getConn().createStatement();
      smtt.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }


  }

  /**
   * Actualiza los datos de una vivienda en la base de datos.
   *
   * @param v       La vivienda con los datos actualizados.
   * @param visible Indica si la vivienda está visible o no.
   * @param dao     El DAOManager utilizado para acceder a la base de datos.
   * @return true si la actualización fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean update(Vivienda v, boolean visible, DAOManager dao) {
    conectarBBDD(dao);
    String sentencia = String.format("""
            UPDATE Viviendas SET
            titulo = '%s',
            descripcion = '%s',
            localidad = '%s',
            provincia = '%s',
            maxOcupantes= %s,
            precioNoche=%s,
            visible = %s
            where id=%s;
            """, v.getTitulo(), v.getDescripcion(), v.getLocalidad(), v.getProvincia(), v.getMaxOcupantes(), v.getPrecioNoche(), visible, v.getId());
    try {
      Statement smtm = dao.getConn().createStatement();
      smtm.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }
  }

  /**
   * Elimina todos los datos de la tabla Viviendas en la base de datos.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return true si la eliminación fue exitosa, false en caso contrario.
   * @author Adrian Lara Bonilla
   */
  @Override
  public boolean deleteAllData(DAOManager dao) {
    conectarBBDD(dao);
    String sentencia = "Delete from Viviendas;";
    try {
      Statement smtm = dao.getConn().createStatement();
      smtm.execute(sentencia);
      desconectarBBDD(dao);
      return true;
    } catch (Exception e) {
      desconectarBBDD(dao);
      return false;
    }
  }

  /**
   * Lee una vivienda de la base de datos utilizando su ID.
   *
   * @param idVivienda El ID de la vivienda a leer.
   * @param dao        El DAOManager utilizado para acceder a la base de datos.
   * @return La vivienda correspondiente al ID especificado, o null si no se encontró ninguna vivienda.
   * @author Adrian Lara Bonilla
   */
  @Override
  public Vivienda readByID(int idVivienda, DAOManager dao) {
    conectarBBDD(dao);
    Vivienda vivienda = null;
    String sentencia = String.format("""
            select * from Viviendas where id = %s;""", idVivienda);
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        vivienda = new Vivienda(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("localidad"),
                rs.getString("provincia"),
                rs.getInt("maxOcupantes"),
                rs.getDouble("precioNoche")
        );
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return vivienda;
  }

  /**
   * Lee una vivienda disponible de la base de datos utilizando su ID.
   *
   * @param idVivienda El ID de la vivienda a leer.
   * @param dao        El DAOManager utilizado para acceder a la base de datos.
   * @return La vivienda disponible correspondiente al ID especificado, o null si no se encontró ninguna vivienda disponible.
   * @author Adrian Lara Bonilla
   */
  public Vivienda readByIDDisponible(int idVivienda, DAOManager dao) {
    conectarBBDD(dao);
    Vivienda vivienda = null;
    String sentencia = String.format("""
            select * from Viviendas where id = %s and visible = true;""", idVivienda);
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        vivienda = new Vivienda(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("localidad"),
                rs.getString("provincia"),
                rs.getInt("maxOcupantes"),
                rs.getDouble("precioNoche")
        );
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return vivienda;
  }

  /**
   * Lee el listado de viviendas de un propietario de la base de datos.
   *
   * @param idPropietario El ID del propietario cuyas viviendas se van a leer.
   * @param dao           El DAOManager utilizado para acceder a la base de datos.
   * @return El listado de viviendas del propietario especificado.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Vivienda> readListadoViviendasPropietario(int idPropietario, DAOManager dao) {
    conectarBBDD(dao);
    Vivienda vivienda;
    ArrayList<Vivienda> viviendas = new ArrayList<>();
    String sentencia = String.format("""
            select * from Viviendas where idPropietario=%s and visible=true;""", idPropietario);
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        vivienda = new Vivienda(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("localidad"),
                rs.getString("provincia"),
                rs.getInt("maxOcupantes"),
                rs.getDouble("precioNoche")
        );
        viviendas.add(vivienda);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return viviendas;
  }

  /**
   * Lee el listado de todas las viviendas disponibles.
   *
   * @param dao El DAOManager utilizado para acceder a la base de datos.
   * @return El listado de todas las viviendas disponibles.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Vivienda> readListadoViviendas(DAOManager dao) {
    conectarBBDD(dao);
    Vivienda vivienda;
    ArrayList<Vivienda> viviendas = new ArrayList<>();
    String sentencia = "select * from Viviendas where visible=true;";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        vivienda = new Vivienda(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("localidad"),
                rs.getString("provincia"),
                rs.getInt("maxOcupantes"),
                rs.getDouble("precioNoche")
        );
        viviendas.add(vivienda);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return viviendas;
  }

  /**
   * Lee el listado de viviendas disponibles filtrado por localidad o provincia.
   *
   * @param buscaLocalidad Indica si se va a buscar por localidad (true) o por provincia (false).
   * @param busqueda       El término de búsqueda para filtrar las viviendas.
   * @param dao            El DAOManager utilizado para acceder a la base de datos.
   * @return El listado de viviendas disponibles que coinciden con los criterios de búsqueda.
   * @author Adrian Lara Bonilla
   */
  @Override
  public ArrayList<Vivienda> readListadoViviendasbyLocalidadProvincia(boolean buscaLocalidad, String busqueda, DAOManager dao) {
    conectarBBDD(dao);
    Vivienda vivienda;
    String tipo = buscaLocalidad ? "localidad" : "provincia";
    ArrayList<Vivienda> viviendas = new ArrayList<>();
    String sentencia = "select * from Viviendas where visible = true and " + tipo + " like '%" + busqueda + "%';";
    try {
      PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        vivienda = new Vivienda(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("localidad"),
                rs.getString("provincia"),
                rs.getInt("maxOcupantes"),
                rs.getDouble("precioNoche")
        );
        viviendas.add(vivienda);
      }
    } catch (Exception e) {
      desconectarBBDD(dao);
      e.printStackTrace();
    }
    desconectarBBDD(dao);
    return viviendas;
  }
}
