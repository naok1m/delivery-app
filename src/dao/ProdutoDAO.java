package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Produto;

public class ProdutoDAO {

	public  void inserirProduto  (Produto produto) {
		String sql =  "INSERT INTO Produto (Restaurante, Nome, Descrição, Valor, Disponivel) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
			ptsm.setInt(1, produto.getRestaurante().getID());
			ptsm.setString(2, produto.getNome());
			ptsm.setString(3, produto.getDescricao());
			ptsm.setDouble(4, produto.getValor());
			ptsm.setBoolean(3, produto.getDisponivel());
			
			ptsm.execute();
			
			ResultSet rs = ptsm.getGeneratedKeys();
	        if (rs.next()) {
	            produto.setID(rs.getInt(1));
	        }
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
	
	
	
}
