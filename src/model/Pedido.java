package model;

public class Pedido {
	public int id;
	public Cliente cliente;
	public String dataHora;
	public Restaurante restaurante;
	public String status;
	public Pedido(int id, Cliente cliente, String dataHora, Restaurante restaurante, String status) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.dataHora = dataHora;
		this.restaurante = restaurante;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDataHora() {
		return dataHora;
	}
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	public Restaurante getRestaurante() {
		return restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
