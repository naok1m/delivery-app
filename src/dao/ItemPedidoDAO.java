package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ItemPedido;

public class ItemPedidoDAO {
	
	public void inserirItemPedido (ItemPedido itempedido) {
		String sql =  "INSERT INTO ItemPedido (Pedido, descrição, quantidade, valor) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql)){
			ptsm.setInt(1, itempedido.getPedido().getID());
			ptsm.setString(2, itempedido.getDescrição());
			ptsm.setInt(3, itempedido.getQuantidade());
			ptsm.setDouble(4, itempedido.getValor());
			
			ptsm.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
}
