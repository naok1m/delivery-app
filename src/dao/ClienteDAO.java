package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Cliente;

public class ClienteDAO {
	// CRIAR CLIENTE
	public void inserirCliente (Cliente cliente) {
		String sql =  "INSERT INTO cliente (nome, telefone, endereço) VALUES (?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
			ptsm.setString(1, cliente.getNome());
			ptsm.setString(2, cliente.getTelefone());
			ptsm.setString(3, cliente.getEndereço());
			
			ptsm.execute();
			
			ResultSet rs = ptsm.getGeneratedKeys();
	        if (rs.next()) {
	            cliente.setID(rs.getInt(1));
	        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	
	// READ CLIENTE
	public Cliente buscarPorId(int id) {
		String sql = "SELECT * FROM Cliente WHERE ID_CLiente = ?";
		Cliente cliente = null;
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql)){
				ptsm.setInt(1, 	id);
				ResultSet rs = ptsm.executeQuery();
				if (rs.next()) {
	                cliente = new Cliente(
	                 rs.getInt("ID_CLiente"),
	                 rs.getString("nome"),
	                 rs.getString("telefone"),
	                 rs.getString("endereço")
	                );
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return cliente;
	    }
	};
