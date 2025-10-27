package service;

import dao.LoginDAO;
import dao.ClienteDAO; // Importante: Precisamos do ClienteDAO
import model.Cliente;
import model.Login;
// (Não precisamos de SQLException aqui, pois seu DAO trata internamente)

public class LoginService {

    private LoginDAO loginDAO;
    private ClienteDAO clienteDAO;

    /**
     * Construtor: Inicializa os DAOs que este serviço usará.
     */
    public LoginService() {
        this.loginDAO = new LoginDAO();
        this.clienteDAO = new ClienteDAO(); // Instancia o ClienteDAO
    }

    /**
     * Este é o método que sua tela (LoginController) deve chamar.
     * Ele autentica e retorna o Cliente completo.
     *
     * @param email O email digitado pelo usuário.
     * @param senha A senha digitada pelo usuário.
     * @return Um objeto Cliente se o login for válido e for um cliente,
     * caso contrário, retorna null.
     */
    public Cliente autenticarCliente(String email, String senha) {

        // 1. Validação básica (boa prática)
        if (email == null || email.trim().isEmpty() || senha == null || senha.isEmpty()) {
            System.err.println("LoginService: Email ou senha vazios.");
            return null;
        }

        // 2. Chama o seu LoginDAO.autenticar()
        // Este método já trata a exceção SQL internamente.
        Login login = loginDAO.autenticar(email, senha);

        // 3. Verifica se o login foi bem-sucedido
        if (login == null) {
            System.err.println("LoginService: Autenticação falhou (email/senha errados).");
            return null; // Email ou senha errados
        }

        // 4. Verifica se o tipo de usuário é "Cliente"
        // (Seu modelo usa getTipoUsuario())
        if ("Cliente".equalsIgnoreCase(login.getTipoUsuario())) {

            // 5. Busca o Cliente usando o ID de Referência
            // (Seu modelo usa getReferencia())
            int clienteId = login.getReferencia();

            // Assumindo que seu ClienteDAO tem o método buscarPorId()
            Cliente cliente = clienteDAO.buscarPorId(clienteId);

            if (cliente == null) {
                System.err.println("LoginService: Login OK, mas Cliente (ID: " + clienteId + ") não encontrado.");
            }

            return cliente; // Retorna o objeto Cliente (ou null se não achou o ID)

        } else {
            // Login válido, mas não é um cliente (pode ser um Restaurante)
            System.err.println("LoginService: Login válido, mas não é do tipo 'Cliente'.");
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