package service;

import java.sql.SQLException;
import java.util.List;

import dao.RestauranteDAO;
import model.Restaurante;

public class RestauranteService {
    private RestauranteDAO restauranteDAO;

    public RestauranteService() {
        this.restauranteDAO = new RestauranteDAO();
    }

    public Restaurante buscarPorId(int idRestaurante) {
        try {
            if (idRestaurante <= 0) {
                throw new IllegalArgumentException("ID do restaurante deve ser maior que zero");
            }
            return restauranteDAO.buscarPorId(idRestaurante);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void cadastrarRestaurante(Restaurante restaurante) {
        if (restaurante.getNome() == null || restaurante.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do restaurante é obrigatório");
        }
        if (restaurante.getTelefone() == null || restaurante.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do restaurante é obrigatório");
        }
        restauranteDAO.inserirRestaurante(restaurante);
    }

    public List<Restaurante> listarTodosRestaurantes() {
        try {
            return restauranteDAO.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Restaurante> buscarPorTipoCozinha(String tipoCozinha) {
        try {
            if (tipoCozinha == null || tipoCozinha.trim().isEmpty()) {
                throw new IllegalArgumentException("Tipo de cozinha não pode ser vazio");
            }
            return restauranteDAO.buscarPorTipoCozinha(tipoCozinha);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
