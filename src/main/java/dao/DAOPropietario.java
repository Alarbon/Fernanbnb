package dao;

import models.Propietario;

import java.util.ArrayList;

public interface DAOPropietario {

  boolean insert(Propietario p, DAOManager dao);

  boolean update(Propietario p, DAOManager dao);

  boolean delete(Propietario p, DAOManager dao);

  boolean deleteAllData(DAOManager dao);

  Propietario readByID(int idPropietario, DAOManager dao);
  Propietario readByEmail(String email, DAOManager dao);

  ArrayList<Propietario> readListadoPropietarios(DAOManager dao);
}
