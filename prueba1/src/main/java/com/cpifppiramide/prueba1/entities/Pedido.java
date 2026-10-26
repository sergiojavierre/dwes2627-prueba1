package com.cpifppiramide.prueba1.entities;

public class Pedido {
    private final Mesa mesa;
    private final Plato plato;
    private final int cantidad;

    public Pedido(Mesa mesa, Plato plato, int cantidad) {
        this.mesa = mesa;
        this.plato = plato;
        this.cantidad = cantidad;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public Plato getPlato() {
        return plato;
    }

    public int getCantidad() {
        return cantidad;
    }
}
