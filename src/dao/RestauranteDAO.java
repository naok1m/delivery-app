package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Restaurante;

public class RestauranteDAO {

	public void inserirRestaurante(Restaurante restaurante) throws SQLException {
		String sql = "INSERT INTO Restaurante (Nome, Telefone, TipoCozinha) VALUES (?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ptsm.setString(1, restaurante.getNome());
			ptsm.setString(2, restaurante.getTelefone());
			ptsm.setString(3, restaurante.getTipoCozinha());

			ptsm.execute();

			ResultSet rs = ptsm.getGeneratedKeys();
			if (rs.next()) {
				restaurante.setID(rs.getInt(1));
			}

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // <<< RELANÇAR O ERRO
        }
	}

	// Buscar restaurante por ID
	public static Restaurante buscarPorId(int id) throws SQLException {
		String sql = "SELECT * FROM Restaurante WHERE ID_Restaurante = ?";
		Restaurante restaurante = null;
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql)) {
			ptsm.setInt(1, id);
			ResultSet rs = ptsm.executeQuery();
			if (rs.next()) {
				restaurante = new Restaurante(
						rs.getInt("ID_Restaurante"),
						rs.getString("Nome"),
						rs.getString("Telefone"),
						rs.getString("TipoCozinha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return restaurante;
	}

	// Listar todos os restaurantes
	public List<Restaurante> listarTodos() throws SQLException {
		String sql = "SELECT * FROM Restaurante ORDER BY Nome";
		List<Restaurante> restaurantes = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql)) {
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				Restaurante restaurante = new Restaurante(
						rs.getInt("ID_Restaurante"),
						rs.getString("Nome"),
						rs.getString("Telefone"),
						rs.getString("TipoCozinha"));
				restaurantes.add(restaurante);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return restaurantes;
	}

	// Buscar restaurantes por tipo de cozinha
	public List<Restaurante> buscarPorTipoCozinha(String tipoCozinha) throws SQLException {
		String sql = "SELECT * FROM Restaurante WHERE TipoCozinha LIKE ? ORDER BY Nome";
		List<Restaurante> restaurantes = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ptsm = conn.prepareStatement(sql)) {
			ptsm.setString(1, "%" + tipoCozinha + "%");
			ResultSet rs = ptsm.executeQuery();
			while (rs.next()) {
				Restaurante restaurante = new Restaurante(
						rs.getInt("ID_Restaurante"),
						rs.getString("Nome"),
						rs.getString("Telefone"),
						rs.getString("Tipo_cozinha"));
				restaurantes.add(restaurante);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return restaurantes;
	}
}
