package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ItemPedido;

public class ItemPedidoDAO {
	
	public void inserirItemPedido (ItemPedido itempedido) throws SQLException {
		String sql =  "INSERT INTO ItemPedido (Pedido, Descrição, Quantidade, Valor) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql)){
			ptsm.setInt(1, itempedido.getPedido().getID());
			ptsm.setString(2, itempedido.getDescrição());
			ptsm.setInt(3, itempedido.getQuantidade());
			ptsm.setDouble(4, itempedido.getValor());

			
			ptsm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // 🚨 2. Relançar a exceção para que o Service saiba!
        }
		}
}
