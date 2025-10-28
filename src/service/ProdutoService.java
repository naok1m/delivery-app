package service;

import java.sql.SQLException;
import java.util.List;
import dao.ProdutoDAO;
import model.Produto;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    // Método que a MainApp injeta (o antigo cadastrarProduto é redundante)
    // O Controller deve usar este método.
    public void inserirNovoProduto(Produto produto) throws SQLException {
        // Garantimos que ao cadastrar, o produto esteja SEMPRE DISPONÍVEL (true)
        produto.setDisponivel(true);
        produtoDAO.inserirProduto(produto);
    }

    // Método para listar produtos do restaurante
    public List<Produto> listarProdutosPorRestaurante(int idRestaurante) {
        return produtoDAO.listarPorRestaurante(idRestaurante);
    }

    public List<Produto> listarTodosProdutosParaGestao(int idRestaurante) {
        return produtoDAO.listarTodosRestaurante(idRestaurante);
    }
    // NOVO MÉTODO: Mudar o status (usa um booleano no DAO)
    // O novoStatus deve ser tratado como "DISPONÍVEL" ou "INDISPONÍVEL" no Controller,
    // mas convertido para boolean aqui para o DAO.
    public void mudarStatus(int idProduto, String novoStatusString) {
        if (idProduto <= 0 || novoStatusString == null || novoStatusString.trim().isEmpty()) {
            throw new IllegalArgumentException("Dados inválidos para mudar status.");
        }

        boolean novoStatusBoolean;

        // Conversão de String para Boolean para o DAO
        if (novoStatusString.equalsIgnoreCase("DISPONÍVEL")) {
            novoStatusBoolean = true;
        } else if (novoStatusString.equalsIgnoreCase("INDISPONÍVEL")) {
            novoStatusBoolean = false;
        } else {
            throw new IllegalArgumentException("Status inválido. Use 'DISPONÍVEL' ou 'INDISPONÍVEL'.");
        }

        // Chama o DAO com o booleano
        produtoDAO.mudarStatus(idProduto, novoStatusBoolean);
    }

}