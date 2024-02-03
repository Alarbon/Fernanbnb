package dao;

import models.Mensaje;

import java.util.ArrayList;

public interface DAOMensajes {
    boolean enviaMensaje(Mensaje mensaje, DAOManager dao);

    Mensaje buscaMensajeByID(int idMensaje, DAOManager dao);


    ArrayList<Mensaje> agruparChatsPorViviendas(int idUsuario, DAOManager dao);

    ArrayList<Mensaje> mensajesUnSoloChat(int idEmisor, int idReceptor, int idVivienda, DAOManager dao);

    ArrayList<Mensaje> mensajesNoLeidosByUsuario(int idUsuario, DAOManager dao);

    boolean marcaMensajeLeido(int idMensaje, int idUsuario, DAOManager dao);

    boolean borraMensajeEmisor(int idMensaje, int idEmisor, DAOManager dao);

    boolean borraMensajeReceptor(int idMensaje, int idReceptor, DAOManager dao);
}
