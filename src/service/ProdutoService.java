package service;

import java.util.List;
import dao.ProdutoDAO;
import model.Produto;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public void cadastrarProduto(Produto produto) {
        produtoDAO.inserirProduto(produto);
    }

    public List<Produto> listarProdutosPorRestaurante(int idRestaurante) {
        return produtoDAO.listarPorRestaurante(idRestaurante);
    }
}
