package com.cpifppiramide.prueba1.dao;

import com.cpifppiramide.prueba1.dao.mesas.DAOMesas;
import com.cpifppiramide.prueba1.dao.mesas.DAOMesasMySQL;
import com.cpifppiramide.prueba1.dao.platos.DAOPlatos;
import com.cpifppiramide.prueba1.dao.platos.DAOPlatosMySQL;

public class DaoFactory {
    private static final DaoFactory INSTANCE = new DaoFactory();

    private final DAOMesas mesasDao = new DAOMesasMySQL();
    private final DAOPlatos platosDao = new DAOPlatosMySQL();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return INSTANCE;
    }

    public DAOMesas getMesasDao() {
        return mesasDao;
    }

    public DAOPlatos getPlatosDao() {
        return platosDao;
    }
}
