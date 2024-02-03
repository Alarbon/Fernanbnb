package dao;


import models.Propietario;
import models.Vivienda;

import java.util.ArrayList;

public interface DAOVivienda {

  boolean insert(Vivienda v, Propietario p, DAOManager dao);

  boolean update(Vivienda v, boolean visible, DAOManager dao);

  boolean deleteAllData(DAOManager dao);

  Vivienda readByID(int idVivienda, DAOManager dao);

  ArrayList<Vivienda> readListadoViviendasPropietario(int idPropietario, DAOManager dao);

  ArrayList<Vivienda> readListadoViviendas(DAOManager dao);

  ArrayList<Vivienda> readListadoViviendasbyLocalidadProvincia(boolean buscaLocalidad, String busqueda, DAOManager dao);
}
