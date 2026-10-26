package com.cpifppiramide.prueba1.entities;

import java.math.BigDecimal;

public class Plato {
    private final String nombre;
    private final BigDecimal precio;

    public Plato(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
}
