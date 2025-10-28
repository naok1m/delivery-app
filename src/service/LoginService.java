package service;

import dao.LoginDAO;
import dao.ClienteDAO; // Importante: Precisamos do ClienteDAO
import dao.RestauranteDAO;
import model.Cliente;
import model.Login;

import java.sql.SQLException;
// (Não precisamos de SQLException aqui, pois seu DAO trata internamente)

public class LoginService {

    private final RestauranteDAO restauranteDAO;
    private LoginDAO loginDAO;
    private ClienteDAO clienteDAO;


    public LoginService() {
        this.loginDAO = new LoginDAO();
        this.clienteDAO = new ClienteDAO();
        this.restauranteDAO = new RestauranteDAO();
    }


    public Object autenticar(String email, String senha) {

        if (email == null || email.trim().isEmpty() || senha == null || senha.isEmpty()) {
            System.err.println("LoginService: Email ou senha vazios.");
            return null;
        }

        Login login = loginDAO.autenticar(email, senha);

        if (login == null) {
            return null; // Email ou senha errados
        }

        int referenciaId = login.getReferencia();

        try {
            if ("Cliente".equalsIgnoreCase(login.getTipoUsuario())) {
                return clienteDAO.buscarPorId(referenciaId);

            } else if ("Restaurante".equalsIgnoreCase(login.getTipoUsuario())) {
                // 🚨 CORREÇÃO: Chamar a instância que foi injetada no construtor
                return restauranteDAO.buscarPorId(referenciaId);

            } else {
                System.err.println("LoginService: Tipo de usuário ('" + login.getTipoUsuario() + "') desconhecido.");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("LoginService: Erro ao buscar dados do usuário (ID: " + referenciaId + "): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Este método é um "wrapper" para o inserirLogin do seu DAO.
     * Pode ser usado para uma futura tela de cadastro.
     * @param login O objeto Login a ser inserido.
     */
    public void cadastrarLogin(Login login) {
        // Validação
        if (login == null || login.getEmail() == null || login.getSenha() == null ||
                login.getTipoUsuario() == null) {
            System.err.println("LoginService: Dados de login incompletos para cadastro.");
            return;
        }

        loginDAO.inserirLogin(login);
    }


}