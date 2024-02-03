package dao;

import models.Administrador;

import java.util.ArrayList;

public interface DAOAdministrador {

  boolean insert(Administrador a, DAOManager dao);

  boolean update(Administrador a, DAOManager dao);

  boolean delete(Administrador a, DAOManager dao);

  boolean deleteAllData(DAOManager dao);

  Administrador readByID(int idAdministrador, DAOManager dao);

  Administrador readByEmail(String email, DAOManager dao);

  ArrayList<Administrador> readListadoAdministradores(DAOManager dao);
}
