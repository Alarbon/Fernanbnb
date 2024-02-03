package dao;


import models.Reserva;

import java.util.ArrayList;

public interface DAOReserva {

  boolean insert(Reserva r, DAOManager dao);

  boolean update(Reserva r, DAOManager dao);

  boolean delete(Reserva r, DAOManager dao);

  boolean deleteAllData(DAOManager dao);

  Reserva readByID(int idReserva, DAOManager dao);


  ArrayList<Reserva> readListadoReservasUsuario(int idUsuario, DAOManager dao);

  ArrayList<Reserva> readListadoReservasVivienda(int idVivienda, DAOManager dao);

  ArrayList<Reserva> readAllListadoReservas(DAOManager dao);
}