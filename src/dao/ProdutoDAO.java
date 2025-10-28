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

    public  void inserirProduto  (Produto produto) throws SQLException {
        String sql =  "INSERT INTO Produto (Restaurante, Nome, Descricao, Valor, Disponivel) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ptsm.setInt(1, produto.getRestaurante().getID());
            ptsm.setString(2, produto.getNome());
            ptsm.setString(3, produto.getDescricao());
            ptsm.setDouble(4, produto.getValor());
            ptsm.setBoolean(5, produto.getDisponivel()); // Booleano

            ptsm.execute();

            ResultSet rs = ptsm.getGeneratedKeys();
            if (rs.next()) {
                produto.setID(rs.getInt(1));
            }

        } catch (SQLException e) {
            // NÃO COMENTAR/IGNORAR! RELANÇAR A EXCEÇÃO PARA QUE O SERVICE POSSA TRATAR.
            e.printStackTrace();
            throw e; // <<< RELANÇAR O ERRO
        }
    }

    // ... (listarPorRestaurante - está OK) ...

    public List<Produto> listarPorRestaurante(int idRestaurante) {
        String sql = "SELECT * FROM Produto WHERE Restaurante = ? AND Disponivel = true";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {

            ptsm.setInt(1, idRestaurante);
            ResultSet rs = ptsm.executeQuery();

            while (rs.next()) {
                // 1. Cria um objeto Produto vazio
                Produto produto = new Produto();

                // 2. Preenche com os dados do banco
                produto.setID(rs.getInt("ID_Produto"));
                produto.setNome(rs.getString("Nome"));
                produto.setDescricao(rs.getString("Descricao"));
                produto.setValor(rs.getDouble("Valor"));
                produto.setDisponivel(rs.getBoolean("Disponivel")); // Booleano

                // 3. Cria um objeto Restaurante simples (só com o ID)
                Restaurante restaurante = new Restaurante();
                restaurante.setID(rs.getInt("Restaurante"));

                // 4. Associa o restaurante ao produto
                produto.setRestaurante(restaurante);

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    public List<Produto> listarTodosRestaurante(int idRestaurante) {
        String sql = "SELECT * FROM Produto WHERE Restaurante = ?";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {

            ptsm.setInt(1, idRestaurante);
            ResultSet rs = ptsm.executeQuery();

            while (rs.next()) {
                // 1. Cria um objeto Produto vazio
                Produto produto = new Produto();

                // 2. Preenche com os dados do banco
                produto.setID(rs.getInt("ID_Produto"));
                produto.setNome(rs.getString("Nome"));
                produto.setDescricao(rs.getString("Descricao"));
                produto.setValor(rs.getDouble("Valor"));
                produto.setDisponivel(rs.getBoolean("Disponivel")); // Booleano

                // 3. Cria um objeto Restaurante simples (só com o ID)
                Restaurante restaurante = new Restaurante();
                restaurante.setID(rs.getInt("Restaurante"));

                // 4. Associa o restaurante ao produto
                produto.setRestaurante(restaurante);

                produtos.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produtos;
    }

    // NOVO MÉTODO: Mudar Status de Disponibilidade
    public void mudarStatus(int idProduto, boolean novoStatus) {
        String sql = "UPDATE Produto SET Disponivel = ? WHERE ID_Produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {
            ptsm.setBoolean(1, novoStatus); // Define o booleano
            ptsm.setInt(2, idProduto);
            ptsm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Lançar exceção para ser capturada pelo Service/Controller se necessário
        }
    }
}