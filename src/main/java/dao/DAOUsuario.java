package dao;


import models.Usuario;

import java.util.ArrayList;

public interface DAOUsuario {

  boolean insert(Usuario u, DAOManager dao);

  boolean update(Usuario u, DAOManager dao);

  boolean delete(Usuario u, DAOManager dao);

  boolean deleteAllData(DAOManager dao);

  Usuario readByID(int idUsuario, DAOManager dao);

  Usuario readByEmail(String email, DAOManager dao);

  ArrayList<Usuario> readListadoUsuarios(DAOManager dao);


}
