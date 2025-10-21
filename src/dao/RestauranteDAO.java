package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Restaurante;

public class RestauranteDAO {

	public  void inserirRestaurante  (Restaurante restaurante) {
		String sql =  "INSERT INTO Restaurante (Nome, Telefone, TipoCozinha) VALUES (?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
			ptsm.setString(1, restaurante.getNome());
			ptsm.setString(2, restaurante.getTelefone());
			ptsm.setString(3, restaurante.getTipoCozinha());
			
			ptsm.execute();
			
			ResultSet rs = ptsm.getGeneratedKeys();
	        if (rs.next()) {
	            restaurante.setID(rs.getInt(1));
	        }
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	
	
	
}
