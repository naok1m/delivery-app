package service;

import java.sql.SQLException;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteService {
	private ClienteDAO clienteDAO;
	
	
	public ClienteService() {
		this.clienteDAO = new ClienteDAO();
	}
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
