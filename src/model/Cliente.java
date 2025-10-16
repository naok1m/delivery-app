package model;

public class Cliente {
	public int id;
	public String nome;
	public String endereço;
	public String telefone;
	public String email;
	
	
	public Cliente(String nome, String endereço, String telefone, String email) {
		this.nome = nome;
		this.endereço = endereço;
		this.telefone = telefone;
		this.email = email;
	}
	public Cliente(int id, String nome, String endereço, String telefone, String email) {
		this.id = id;
		this.nome = nome;
		this.endereço = endereço;
		this.telefone = telefone;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereço() {
		return endereço;
	}
	public void setEndereço(String endereço) {
		this.endereço = endereço;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
