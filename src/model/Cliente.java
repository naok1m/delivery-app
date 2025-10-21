package model;

public class Cliente {
	public int ID;
	public String Nome;
	public String Endereço;
	public String Telefone;
	
	
	public Cliente(String nome, String endereço, String telefone) {
		this.Nome = nome;
		this.Endereço = endereço;
		this.Telefone = telefone;

	}
	public Cliente(int id, String nome, String endereço, String telefone) {
		this.ID = id;
		this.Nome = nome;
		this.Endereço = endereço;
		this.Telefone = telefone;
	}
	public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		this.Nome = nome;
	}
	public String getEndereço() {
		return Endereço;
	}
	public void setEndereço(String endereço) {
		this.Endereço = endereço;
	}
	public String getTelefone() {
		return Telefone;
	}
	public void setTelefone(String telefone) {
		this.Telefone = telefone;
	}
	
}
