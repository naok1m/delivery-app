package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Produto;
import model.Restaurante;

public class ProdutoDAO {

	public  void inserirProduto  (Produto produto) {
		String sql =  "INSERT INTO Produto (Restaurante, Nome, Descrição, Valor, Disponivel) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ptsm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
			ptsm.setInt(1, produto.getRestaurante().getID());
			ptsm.setString(2, produto.getNome());
			ptsm.setString(3, produto.getDescricao());
			ptsm.setDouble(4, produto.getValor());
			ptsm.setBoolean(5, produto.getDisponivel());
			
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
	public List<Produto> listarPorRestaurante(int idRestaurante) {
        String sql = "SELECT * FROM Produto WHERE Restaurante = ?";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {

            ptsm.setInt(1, idRestaurante);
            ResultSet rs = ptsm.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto(idRestaurante, null, sql, sql, null, false);
                produto.setID(rs.getInt("ID_Produto"));
                produto.setNome(rs.getString("Nome"));
                produto.setDescricao(rs.getString("Descrição"));
                produto.setValor(rs.getDouble("Valor"));
                produto.setDisponivel(rs.getBoolean("Disponivel"));

                // Só setando o restaurante com ID, se quiser o objeto completo você pode buscar pelo RestauranteDAO
                Restaurante restaurante = new Restaurante(idRestaurante, sql, sql, sql);
                restaurante.setID(rs.getInt("Restaurante"));
                produto.setRestaurante(restaurante);

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }
	
	
	
}
