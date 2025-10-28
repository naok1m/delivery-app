package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Pedido;
import model.Restaurante;

public class PedidoDAO {

    public void inserirPedido(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (Cliente, Restaurante, DataHora, Stats) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ptsm.setInt(1, pedido.getCliente().getID());
            ptsm.setInt(2, pedido.getRestaurante().getID());
            ptsm.setString(3, pedido.getDataHora());
            ptsm.setString(4, pedido.getStats());

            ptsm.execute();

            ResultSet rs = ptsm.getGeneratedKeys();
            if (rs.next()) {
                pedido.setID(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Buscar pedido por ID
    public Pedido buscarPorId(int id) throws SQLException {
        // 🚨 Query: CORRIGIDO para r.TipoCozinha
        String sql = "SELECT p.*, c.Nome as cliente_nome, c.Telefone as cliente_telefone, c.Endereço as cliente_endereco, "
                + "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
                + "FROM Pedido p "
                + "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
                + "JOIN Restaurante r ON p.Restaurante = r.ID_Restaurante "
                + "WHERE p.ID_Pedido = ?";

        Pedido pedido = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setInt(1, id);
            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                // Criar cliente
                Cliente cliente = new Cliente(
                        rs.getInt("Cliente"),
                        rs.getString("cliente_nome"), // O nome é este.
                        rs.getString("cliente_endereco"),
                        rs.getString("cliente_telefone"));

                // Criar restaurante
                Restaurante restaurante = new Restaurante(
                        rs.getInt("Restaurante"),
                        rs.getString("restaurante_nome"),
                        rs.getString("restaurante_telefone"),
                        // 🚨 Mapeamento: CORRIGIDO para TipoCozinha
                        rs.getString("TipoCozinha"));

                // Criar pedido
                pedido = new Pedido(
                        rs.getInt("ID_Pedido"),
                        cliente,
                        restaurante,
                        rs.getString("DataHora"),
                        rs.getString("Stats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return pedido;
    }

    // Listar pedidos por cliente
    public List<Pedido> listarPorCliente(int idCliente) throws SQLException {
        // 🚨 Query: CORRIGIDO para r.TipoCozinha
        String sql = "SELECT p.*, c.Nome as cliente_nome, c.Telefone as cliente_telefone, c.Endereço as cliente_endereco, "
                + "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
                + "FROM Pedido p "
                + "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
                + "JOIN Restaurante r ON p.Restaurante = r.ID_Restaurante "
                + "WHERE p.Cliente = ? ORDER BY p.DataHora DESC";

        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setInt(1, idCliente);
            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                // Criar cliente
                Cliente cliente = new Cliente(
                        rs.getInt("Cliente"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_endereco"),
                        rs.getString("cliente_telefone"));

                // Criar restaurante
                Restaurante restaurante = new Restaurante(
                        rs.getInt("Restaurante"),
                        rs.getString("restaurante_nome"),
                        rs.getString("restaurante_telefone"),
                        // 🚨 Mapeamento: CORRIGIDO para TipoCozinha
                        rs.getString("TipoCozinha"));

                // Criar pedido
                Pedido pedido = new Pedido(
                        rs.getInt("ID_Pedido"),
                        cliente,
                        restaurante,
                        rs.getString("DataHora"),
                        rs.getString("Stats"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return pedidos;
    }

    // Atualizar status do pedido
    public void atualizarStatus(int idPedido, String novoStatus) throws SQLException {
        String sql = "UPDATE Pedido SET Stats = ? WHERE ID_Pedido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setString(1, novoStatus);
            ptsm.setInt(2, idPedido);
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Listar todos os pedidos de um restaurante
    public List<Pedido> listarPorRestaurante(int idRestaurante) throws SQLException {
        // 🚨 Query: CORRIGIDO para r.TipoCozinha
        String sql = "SELECT p.*, c.Nome as cliente_nome, c.Telefone as cliente_telefone, c.Endereço as cliente_endereco, "
                + "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
                + "FROM Pedido p "
                + "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
                + "JOIN Restaurante r ON p.Restaurante = r.ID_Restaurante "
                + "WHERE p.Restaurante = ? ORDER BY p.DataHora DESC";

        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setInt(1, idRestaurante);
            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                // Reutilizando a lógica de criação de objetos
                Cliente cliente = new Cliente(
                        rs.getInt("Cliente"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_endereco"),
                        rs.getString("cliente_telefone"));

                Restaurante restaurante = new Restaurante(
                        rs.getInt("Restaurante"),
                        rs.getString("restaurante_nome"),
                        rs.getString("restaurante_telefone"),
                        // 🚨 Mapeamento: CORRIGIDO para TipoCozinha
                        rs.getString("TipoCozinha"));

                Pedido pedido = new Pedido(
                        rs.getInt("ID_Pedido"),
                        cliente,
                        restaurante,
                        rs.getString("DataHora"),
                        rs.getString("Stats"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return pedidos;
    }

    // Listar pedidos em andamento de um restaurante
    public List<Pedido> listarEmAndamentoPorRestaurante(int idRestaurante) throws SQLException {
        // 🚨 CORREÇÃO DE STATUS E NOME DE COLUNA
        // Assumindo ENUM SQL: 'Em Preparo', 'A Caminho', 'Entregue'
        String sql = "SELECT p.*, c.Nome as cliente_nome, c.Telefone as cliente_telefone, c.Endereço as cliente_endereco, "
                + "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
                + "FROM Pedido p "
                + "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
                + "JOIN Restaurante r ON p.Restaurante = r.ID_Restaurante "
                // 🚨 Status que estão em andamento:
                + "WHERE p.Restaurante = ? AND p.Stats IN ('Em Preparo', 'A Caminho') "
                + "ORDER BY p.DataHora DESC";

        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setInt(1, idRestaurante);
            ResultSet rs = ptsm.executeQuery();
            while (rs.next()) {
                // Reutilizando a lógica de criação de objetos
                Cliente cliente = new Cliente(
                        rs.getInt("Cliente"),
                        rs.getString("cliente_nome"),
                        rs.getString("cliente_endereco"),
                        rs.getString("cliente_telefone"));

                Restaurante restaurante = new Restaurante(
                        rs.getInt("Restaurante"),
                        rs.getString("restaurante_nome"),
                        rs.getString("restaurante_telefone"),
                        // 🚨 Mapeamento: CORRIGIDO para TipoCozinha
                        rs.getString("TipoCozinha"));

                Pedido pedido = new Pedido(
                        rs.getInt("ID_Pedido"),
                        cliente,
                        restaurante,
                        rs.getString("DataHora"),
                        rs.getString("Stats"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return pedidos;
    }
}