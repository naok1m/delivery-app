package service;

import dao.LoginDAO;
import dao.RestauranteDAO;
import model.Login;
import model.Restaurante;

import java.sql.SQLException;
import java.util.List;

public class RestauranteService {

    private final RestauranteDAO restauranteDAO;
    private final LoginDAO loginDAO; // O DAO que você forneceu

    public RestauranteService() {
        this.restauranteDAO = new RestauranteDAO();
        this.loginDAO = new LoginDAO();
    }

    // Este é o método que o Controller irá chamar.
    public boolean cadastrarNovoRestaurante(String nome, String telefone, String tipoCozinha, String email, String senha) throws IllegalArgumentException, SQLException {

        // 2. Cria o objeto Restaurante
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(nome);
        restaurante.setTelefone(telefone);
        restaurante.setTipoCozinha(tipoCozinha);


        restauranteDAO.inserirRestaurante(restaurante);

        // 4. Verifica se a inserção deu certo (se o ID foi gerado)
        if (restaurante.getID() > 0) {

            // 5. Cria o objeto Login
            Login login = new Login();
            login.setEmail(email);
            login.setSenha(senha); // Idealmente, a senha deveria ser hasheada aqui!
            login.setTipoUsuario("Restaurante");
            login.setReferencia(restaurante.getID());

            // 6. Salva o Login no DB
            loginDAO.inserirLogin(login);

            return true;
        } else {
            // Falha na inserção do restaurante (erro de DB)
            return false;
        }
    }

    // Você deve adicionar métodos como listarTodosRestaurantes, buscarPorId, etc.
    public Restaurante buscarPorId(int id) throws SQLException {
        return restauranteDAO.buscarPorId(id); // Assumindo que seu DAO tem buscarPorId
    }

    public List<Restaurante> listarTodosRestaurantes() throws SQLException {
        return restauranteDAO.listarTodos();
    }
}