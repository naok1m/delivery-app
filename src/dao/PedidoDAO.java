package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.Result;

import model.Pedido;

public class PedidoDAO {

	public  void inserirPedido (Pedido pedido) {
		String sql =  "INSERT INTO Pedido (Cliente, Restaurante, datahora, stats) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
}
