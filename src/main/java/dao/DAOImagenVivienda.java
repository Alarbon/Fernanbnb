package dao;

import models.Administrador;

import java.util.ArrayList;

public interface DAOImagenVivienda {
    ArrayList<String> readListadoImagenesViviendas(DAOManager dao);
}
