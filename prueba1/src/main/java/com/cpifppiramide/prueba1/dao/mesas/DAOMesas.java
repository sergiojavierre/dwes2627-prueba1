package com.cpifppiramide.prueba1.dao.mesas;

import com.cpifppiramide.prueba1.entities.Mesa;
import com.cpifppiramide.prueba1.entities.Pedido;
import java.util.List;

public interface DAOMesas {
    List<Mesa> getAll();

    Mesa getById(int id);

    List<Pedido> getPedidos(int id);

    void addPedido(int mesa, String plato);

    void limpiarMesa(int id);
}
