package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Login;

public class LoginDAO {
	
	public void inserirItemPedido (Login login) {
		String sql =  "INSERT INTO ItemPedido (Email, Senha, TipoUsuario, Referencia) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql)){
			ptsm.setString(1, login.getEmail());
			ptsm.setString(2, login.getSenha());
			ptsm.setString(3, login.getTipoUsuario());
			ptsm.setInt(4, login.getReferencia());
			
			ptsm.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
}
