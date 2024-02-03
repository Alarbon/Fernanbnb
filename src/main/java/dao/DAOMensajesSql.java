package dao;

import models.Mensaje;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DAOMensajesSql implements DAOMensajes {

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
    @Override
    public boolean enviaMensaje(Mensaje mensaje, DAOManager dao) {
        try {
            // Establece el formato de fecha y hora
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String dateEnvio = mensaje.getFechaEnvio().format(formatter);

            // Crea la sentencia SQL
            String querySql = String.format(
                    "INSERT INTO Mensajes (id, idEmisor, idReceptor, idVivienda, texto, fechaEnvio, leido, borradoEmisor, borradoReceptor) " +
                            "VALUES (%d, %d, %d, %d, '%s', '%s', %s, %s, %s)",
                    mensaje.getId(),
                    mensaje.getIdEmisor(),
                    mensaje.getIdReceptor(),
                    mensaje.getIdVivienda(),
                    mensaje.getTexto(),
                    dateEnvio,
                    false, // leido
                    false, // borradoEmisor
                    false  // borradoReceptor
            );

            conectarBBDD(dao);

            // Crea un Statement
            Statement stmt = dao.getConn().createStatement();

            // Ejecuta la inserción
            stmt.executeUpdate(querySql);

                      desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }


    @Override
    public Mensaje buscaMensajeByID(int idMensaje, DAOManager dao) {
        Mensaje mensaje = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sentencia = "SELECT * FROM Mensajes WHERE id = ?;";
        conectarBBDD(dao);
        try {
            PreparedStatement ps = dao.getConn().prepareStatement(sentencia);
            ps.setInt(1, idMensaje);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mensaje = new Mensaje(
                            idMensaje,
                            rs.getInt("idEmisor"),
                            rs.getInt("idReceptor"),
                            rs.getInt("idVivienda"),
                            rs.getString("texto"),
                            LocalDateTime.parse(rs.getString("fechaEnvio"),formatter),
                            rs.getBoolean("leido"),
                            rs.getBoolean("borradoEmisor"),
                            rs.getBoolean("borradoReceptor")
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            desconectarBBDD(dao);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mensaje;
    }

    @Override
    public ArrayList<Mensaje> agruparChatsPorViviendas(int idUsuario, DAOManager dao) {
        ArrayList<Mensaje> mensajesAgrupados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT M.id, M.idEmisor,  M.idReceptor, M.idVivienda, M.texto,M.fechaEnvio, M.leido, M.borradoEmisor, M.borradoReceptor
                    FROM Mensajes M
                    JOIN (
                        SELECT idVivienda, MAX(fechaEnvio) AS fechaMax
                        FROM Mensajes
                        WHERE (idEmisor = ? OR idReceptor = ?)
                        GROUP BY idVivienda
                    ) Subquery
                    ON M.idVivienda = Subquery.idVivienda AND M.fechaEnvio = Subquery.fechaMax
                    ORDER BY M.fechaEnvio DESC;
                    """);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mensajesAgrupados.add(
                        new Mensaje(
                                rs.getInt("id"),
                                rs.getInt("idEmisor"),
                                rs.getInt("idReceptor"),
                                rs.getInt("idVivienda"),
                                rs.getString("texto"),
                                LocalDateTime.parse(rs.getString("fechaEnvio"),formatter),
                                rs.getBoolean("leido"),
                                rs.getBoolean("borradoEmisor"),
                                rs.getBoolean("borradoReceptor")
                        )
                        );

            }
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return mensajesAgrupados;
    }

    @Override
    public ArrayList<Mensaje> mensajesUnSoloChat(int idEmisor, int idReceptor, int idVivienda, DAOManager dao) {
        ArrayList<Mensaje> mensajesDelChat = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT *
                    FROM `Mensajes`
                    WHERE (`idEmisor` = ? OR `idReceptor` = ?) AND (`idEmisor` = ? OR `idReceptor` = ?) AND `idVivienda` = ?
                    ORDER BY `fechaEnvio` ASC ;
                                          
                    """);
            stmt.setInt(1, idEmisor);
            stmt.setInt(2, idEmisor);
            stmt.setInt(3, idReceptor);
            stmt.setInt(4, idReceptor);
            stmt.setInt(5, idVivienda);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mensajesDelChat.add(
                        new Mensaje(
                                rs.getInt("id"),
                                rs.getInt("idEmisor"),
                                rs.getInt("idReceptor"),
                                rs.getInt("idVivienda"),
                                rs.getString("texto"),
                                LocalDateTime.parse(rs.getString("fechaEnvio"),formatter),
                                rs.getBoolean("leido"),
                                rs.getBoolean("borradoEmisor"),
                                rs.getBoolean("borradoReceptor")
                        )
                );

            }
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return mensajesDelChat;
    }


    @Override
    public ArrayList<Mensaje> mensajesNoLeidosByUsuario(int idUsuario, DAOManager dao) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ArrayList<Mensaje> mensajesRecibidosUsuario = new ArrayList<>();
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, idEmisor, idReceptor, idVivienda, texto, fechaEnvio, leido, borradoEmisor, borradoReceptor
                    FROM Mensajes
                    WHERE idReceptor = ? AND borradoReceptor = FALSE AND leido = FALSE;
                    """);
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mensajesRecibidosUsuario.add(
                        new Mensaje(
                                rs.getInt("id"),
                                rs.getInt("idEmisor"),
                                rs.getInt("idReceptor"),
                                rs.getInt("idVivienda"),
                                rs.getString("texto"),
                                LocalDateTime.parse(rs.getString("fechaEnvio"),formatter),
                                rs.getBoolean("leido"),
                                rs.getBoolean("borradoEmisor"),
                                rs.getBoolean("borradoReceptor")
                        )
                );
            }
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return mensajesRecibidosUsuario;
    }

    @Override
    public boolean marcaMensajeLeido(int idMensaje, int idUsuario, DAOManager dao) {
        int checkRowUpdate = 0;
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE Mensajes
                    SET leido = true
                    WHERE id = ? AND idReceptor = ?;
                    """);
            stmt.setInt(1, idMensaje);
            stmt.setInt(2, idUsuario);
            checkRowUpdate = stmt.executeUpdate();
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.printf("Error en la modificación del mensaje: %s", e);
        }
        return checkRowUpdate == 1;
    }

    @Override
    public boolean borraMensajeEmisor(int idMensaje, int idEmisor, DAOManager dao) {
        int checkRowUpdate = 0;
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE Mensajes
                    SET borradoEmisor = TRUE
                    WHERE id = ? AND idEmisor = ?;
                    """);
            stmt.setInt(1, idMensaje);
            stmt.setInt(2, idEmisor);
            checkRowUpdate = stmt.executeUpdate();
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.printf("Error en la modificación del mensaje: %s", e);
        }
        return checkRowUpdate == 1;
    }

    @Override
    public boolean borraMensajeReceptor(int idMensaje, int idReceptor, DAOManager dao) {
        int checkRowUpdate = 0;
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    UPDATE Mensajes
                    SET borradoReceptor = TRUE
                    WHERE id = ? AND idReceptor = ?;
                    """);
            stmt.setInt(1, idMensaje);
            stmt.setInt(2, idReceptor);
            checkRowUpdate = stmt.executeUpdate();
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.printf("Error en la modificación del mensaje: %s", e);
        }
        return checkRowUpdate == 1;
    }


    public ArrayList<Mensaje> obtenerMensajesDeUsuario(int idUsuario, DAOManager dao) {
        ArrayList<Mensaje> mensajes = new ArrayList<>();
        try {
            conectarBBDD(dao);
            PreparedStatement stmt = dao.getConn().prepareStatement("""
                    SELECT id, idEmisor, idReceptor, idVivienda, texto, fechaEnvio, leido, borradoEmisor, borradoReceptor
                    FROM Mensajes
                    WHERE idVivienda IN (
                    SELECT DISTINCT idVivienda
                    FROM Mensajes
                    WHERE idEmisor = ? OR idReceptor = ?);
                    """);
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                mensajes.add(
                        new Mensaje(
                                rs.getInt("id"),
                                rs.getInt("idEmisor"),
                                rs.getInt("idReceptor"),
                                rs.getInt("idVivienda"),
                                rs.getString("texto"),
                                LocalDateTime.parse(rs.getString("fechaEnvio")),
                                rs.getBoolean("leido"),
                                rs.getBoolean("borradoEmisor"),
                                rs.getBoolean("borradoReceptor")
                        )
                );
            }
            desconectarBBDD(dao);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return mensajes;
    }
    }




