package com.cpifppiramide.prueba1.dao.platos;

import com.cpifppiramide.prueba1.dao.DBConnector;
import com.cpifppiramide.prueba1.entities.Plato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPlatosMySQL implements DAOPlatos {
    @Override
    public List<Plato> getAll() {
        List<Plato> platos = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT nombre, precio FROM platos");
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                platos.add(new Plato(resultSet.getString("nombre"), resultSet.getBigDecimal("precio")));
            }
            return platos;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron obtener los platos", e);
        }
    }

    @Override
    public Plato getByNombre(String nombre) {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT nombre, precio FROM platos WHERE nombre = ?")) {
            statement.setString(1, nombre);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next()
                        ? new Plato(resultSet.getString("nombre"), resultSet.getBigDecimal("precio"))
                        : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo obtener el plato", e);
        }
    }

    @Override
    public void update(Plato plato) {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE platos SET precio = ? WHERE nombre = ?")) {
            statement.setBigDecimal(1, plato.getPrecio());
            statement.setString(2, plato.getNombre());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo actualizar el plato", e);
        }
    }
}
