package com.cpifppiramide.prueba1.dao.platos;

import com.cpifppiramide.prueba1.entities.Plato;
import java.util.List;

public interface DAOPlatos {
    List<Plato> getAll();

    Plato getByNombre(String nombre);

    void update(Plato plato);
}
