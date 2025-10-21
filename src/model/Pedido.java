package model;

public class Pedido {
	public int ID;
	public Cliente Cliente;
	public Restaurante Restaurante;
	public String DataHora;
	public String Stats;
	
	
	public Pedido(int id, Cliente cliente, Restaurante restaurante, String dataHora, String stats) {
		this.ID = id;
		this.Cliente = cliente;
		this.Restaurante = restaurante;
		this.DataHora = dataHora;
		this.Stats = stats;
	}
	public Pedido(Cliente cliente, Restaurante restaurante, String dataHora, String stats) {
		this.Cliente = cliente;
		this.Restaurante = restaurante;
		this.DataHora = dataHora;
		this.Stats = stats;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public Cliente getCliente() {
		return Cliente;
	}
	public void setCliente(Cliente cliente) {
		this.Cliente = cliente;
	}
	public Restaurante getRestaurante() {
		return Restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.Restaurante = restaurante;
	}
	public String getDataHora() {
		return DataHora;
	}
	public void setDataHora(String dataHora) {
		this.DataHora = dataHora;
	}
	public String getStats() {
		return Stats;
	}
	public void setStats(String stats) {
		this.Stats = stats;
	}
	
	
}
