package model;

public class Cliente {
	public int id;
	public String nome;
	public String email;
	public String senha;
	
	public Cliente(int id, String nome, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	public Cliente(String nome, String email, String senha) {
		this(0, nome, email, senha);
	}
}
