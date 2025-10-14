package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Cliente;

public class ClienteDAO {
	// CRIAR CLIENTE
	public void inserirCliente (Cliente cliente) {
		String sql =  "INSERT INTO cliente (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql)){
			ptsm.setString(1, cliente.getNome());
			ptsm.setString(2, cliente.getEmail());
			ptsm.setString(3, cliente.getTelefone());
			ptsm.setString(4, cliente.getEndereco());
			
			ptsm.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	
	// READ CLIENTE
	};
