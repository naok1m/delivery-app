package service;

import java.sql.SQLException;
import dao.ClienteDAO;
import dao.LoginDAO; // 1. Precisamos do LoginDAO
import model.Cliente;
import model.Login;     // 2. Precisamos do Model Login

public class ClienteService {
	private ClienteDAO clienteDAO;
	private LoginDAO loginDAO; // 3. Adicionar o LoginDAO


	public ClienteService() {
		this.clienteDAO = new ClienteDAO();
		this.loginDAO = new LoginDAO(); // 4. Instanciar o LoginDAO
	}

	public boolean cadastrarNovoCliente(String nome, String endereco, String telefone, String email, String senha) {


		if (nome == null || nome.isBlank() || email == null || email.isBlank() || senha == null || senha.isBlank()) {

			throw new IllegalArgumentException("Nome, email e senha são obrigatórios.");
		}

		try {


			Cliente novoCliente = new Cliente(nome, endereco, telefone);


			clienteDAO.inserirCliente(novoCliente);

			if (novoCliente.getID() <= 0) {
				// Isso acontece se o DAO falhar em retornar o ID
				System.err.println("ClienteService: Falha ao obter ID do novo cliente.");
				return false;
			}

			int novoClienteId = novoCliente.getID();

			Login novoLogin = new Login(email, senha, "Cliente", novoClienteId);


			loginDAO.inserirLogin(novoLogin);


			return true;

		} catch (Exception e) {
			// Pega erros (ex: email duplicado, pois definimos a coluna como UNIQUE)
			System.err.println("Erro no ClienteService ao cadastrar: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Este é o seu método antigo.
	 * Ele pode ser usado internamente ou para outras partes do app.
	 */
	public void cadastrarCliente(Cliente cliente) {
		if (cliente.getNome() == null || cliente.getNome().isBlank()) {
			throw new IllegalArgumentException("Nome obrigatorio");
		}
		clienteDAO.inserirCliente(cliente);
	}


	public Cliente buscarPorId(int id) throws SQLException{
		Cliente cliente = clienteDAO.buscarPorId(id);
		if(cliente == null) {
			throw new IllegalArgumentException("Cliente não encontrado.");

		}
		return cliente;
	}
}