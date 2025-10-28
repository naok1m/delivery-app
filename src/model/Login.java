package model;

public class Login {

	public int ID;
	public String Email;
	public String Senha;
	public String TipoUsuario;
	public int Referencia;
	
	public Login(int id, String email, String senha, String tipousuario, int referencia) {
		this.ID = id;
		this.Email = email;
		this.Senha = senha; 
		this.TipoUsuario = tipousuario;
		this.Referencia = referencia;
	}
	
	public Login(String email, String senha, String tipousuario, int referencia) {
		this.Email = email;
		this.Senha = senha; 
		this.TipoUsuario = tipousuario;
		this.Referencia = referencia;
	}

    public Login() {

    }


    public int getID() {
		return ID;
	}
	public void setID(int id) {
		this.ID = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		this.Email = email;
	}
	public String getSenha() {
		return Senha;
	}
	public void setSenha(String senha) {
		this.Senha = senha;
	}
	public String getTipoUsuario() {
		return TipoUsuario;
	}
	public void setTipoUsuario(String tipousuario) {
		this.TipoUsuario = tipousuario;
	}
	public int getReferencia() {
		return Referencia;
	}
	public void setReferencia(int referencia) {
		this.Referencia = referencia;
	}
	
}
