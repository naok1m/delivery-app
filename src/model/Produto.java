package model;

public class Produto {

	public int ID;
	public Restaurante Restaurante;
	public String Nome;
	public String Descricao;
	public Double Valor;
	public boolean Disponivel;
	
	public Produto(int id, Restaurante restaurante, String nome, String descricao, Double valor, boolean disponivel) {
		this.ID = id;
		this.Restaurante = restaurante;
		this.Nome = nome; 
		this.Descricao = descricao;
		this.Valor = valor;
		this.Disponivel = disponivel;
	}
	
	public Produto(Restaurante restaurante, String nome, String descricao, Double valor, boolean disponivel) {
		this.Restaurante = restaurante;
		this.Nome = nome; 
		this.Descricao = descricao;
		this.Valor = valor;
		this.Disponivel = disponivel;
	}

	public String toString() {
		// Formata para: "Nome do Produto - R$ XX.XX"
		return String.format("%s - R$ %.2f", getNome(), getValor() != null ? getValor() : 0.0);
	}
	public Produto() { }
	
	
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public Restaurante getRestaurante() {
		return Restaurante;
	}
	public void setRestaurante(Restaurante restaurante) {
		this.Restaurante = restaurante;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		this.Nome = nome;
	}
	public String getDescricao() {
		return Descricao;
	}
	public void setDescricao(String descricao) {
		this.Descricao = descricao;
	}
	public Double getValor() {
		return Valor;
	}
	public void setValor(Double valor) {
		this.Valor = valor;
	}
	public boolean getDisponivel() {
		return Disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.Disponivel = disponivel;
	}
}
