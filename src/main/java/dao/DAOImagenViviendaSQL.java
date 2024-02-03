package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOImagenViviendaSQL implements DAOImagenVivienda {

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
     * Lee un listado de enlaces de imágenes de viviendas de la base de datos.
     *
     * @param dao El objeto DAOManager que gestiona la conexión a la base de datos.
     * @return Un ArrayList que contiene todos los enlaces de imágenes de viviendas leídos de la base de datos.
     * @author Adrian Lara Bonilla
     */
    @Override
    public ArrayList<String> readListadoImagenesViviendas(DAOManager dao) {
        conectarBBDD(dao);
        ArrayList<String> imagenesViviendas = new ArrayList<>();
        String sentencia = "SELECT imagen FROM imagenesVivienda;";
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String imagen = rs.getString("imagen");
                imagenesViviendas.add(imagen);
            }
        } catch (Exception e) {
            desconectarBBDD(dao);
            e.printStackTrace();
        }
        desconectarBBDD(dao);
        return imagenesViviendas;
    }

}
