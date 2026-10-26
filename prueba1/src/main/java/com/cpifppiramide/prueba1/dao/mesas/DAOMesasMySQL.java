package com.cpifppiramide.prueba1.dao.mesas;

import com.cpifppiramide.prueba1.dao.DBConnector;
import com.cpifppiramide.prueba1.entities.Mesa;
import com.cpifppiramide.prueba1.entities.Pedido;
import com.cpifppiramide.prueba1.entities.Plato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMesasMySQL implements DAOMesas {
    @Override
    public List<Mesa> getAll() {
        List<Mesa> mesas = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT id FROM mesas");
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mesas.add(new Mesa(resultSet.getInt("id")));
            }
            return mesas;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron obtener las mesas", e);
        }
    }

    @Override
    public Mesa getById(int id) {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT id FROM mesas WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? new Mesa(resultSet.getInt("id")) : null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo obtener la mesa", e);
        }
    }

    @Override
    public List<Pedido> getPedidos(int id) {
        String sql = """
                SELECT p.mesa, p.plato, p.cantidad, pl.precio
                FROM pedidos p
                JOIN platos pl ON p.plato = pl.nombre
                WHERE p.mesa = ?
                """;
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    pedidos.add(new Pedido(
                            new Mesa(resultSet.getInt("mesa")),
                            new Plato(resultSet.getString("plato"), resultSet.getBigDecimal("precio")),
                            resultSet.getInt("cantidad")));
                }
            }
            return pedidos;
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron obtener los pedidos de la mesa", e);
        }
    }

    @Override
    public void addPedido(int mesa, String plato) {
        String sql = """
                INSERT INTO pedidos (mesa, plato, cantidad) VALUES (?, ?, 1)
                ON DUPLICATE KEY UPDATE cantidad = cantidad + 1
                """;
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, mesa);
            statement.setString(2, plato);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo añadir el plato al pedido", e);
        }
    }

    @Override
    public void limpiarMesa(int id) {
        try (Connection connection = DBConnector.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM pedidos WHERE mesa = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudo limpiar la mesa", e);
        }
    }
}
