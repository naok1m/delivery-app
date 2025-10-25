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

	public void inserirPedido(Pedido pedido) {
		String sql = "INSERT INTO Pedido (Cliente, Restaurante, datahora, Status) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ptsm.setInt(1, pedido.getCliente().getID());
			ptsm.setInt(2, pedido.getRestaurante().getID());
			ptsm.setString(3, pedido.getDataHora());
			ptsm.setString(4, pedido.getStatus());

			ptsm.execute();

			ResultSet rs = ptsm.getGeneratedKeys();
			if (rs.next()) {
				pedido.setID(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Buscar pedido por ID
	public Pedido buscarPorId(int id) throws SQLException {
		String sql = "SELECT p.*, c.nome as cliente_nome, c.telefone as cliente_telefone, c.endereço as cliente_endereco, "
				+ "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
				+ "FROM Pedido p "
				+ "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
				+ "JOIN Restaurante r ON p.Restaurante = r.ID "
				+ "WHERE p.ID = ?";

		Pedido pedido = null;
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql)) {
			ptsm.setInt(1, id);
			ResultSet rs = ptsm.executeQuery();
			if (rs.next()) {
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
						rs.getString("TipoCozinha"));

				// Criar pedido
				pedido = new Pedido(
						rs.getInt("ID"),
						cliente,
						restaurante,
						rs.getString("datahora"),
						rs.getString("Status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return pedido;
	}

	// Listar pedidos por cliente
	public List<Pedido> listarPorCliente(int idCliente) throws SQLException {
		String sql = "SELECT p.*, c.nome as cliente_nome, c.telefone as cliente_telefone, c.endereço as cliente_endereco, "
				+ "r.Nome as restaurante_nome, r.Telefone as restaurante_telefone, r.TipoCozinha "
				+ "FROM Pedido p "
				+ "JOIN Cliente c ON p.Cliente = c.ID_Cliente "
				+ "JOIN Restaurante r ON p.Restaurante = r.ID "
				+ "WHERE p.Cliente = ? ORDER BY p.datahora DESC";

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
						rs.getString("TipoCozinha"));

				// Criar pedido
				Pedido pedido = new Pedido(
						rs.getInt("ID"),
						cliente,
						restaurante,
						rs.getString("datahora"),
						rs.getString("Status"));
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
		String sql = "UPDATE Pedido SET Status = ? WHERE ID = ?";
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
}