package model;

public class Restaurante {
public int ID;
public String Nome;
public String Telefone;
public String TipoCozinha;

public Restaurante(int Id, String Nome, String Telefone, String TipoCozinha) {
	this.ID = Id;
	this.Nome = Nome;
	this.Telefone = Telefone;
	this.TipoCozinha = TipoCozinha;
}

public Restaurante(String Nome, String Telefone, String TipoCozinha) {
	this.Nome = Nome;
	this.Telefone = Telefone;
	this.TipoCozinha = TipoCozinha;
}


public int getID() {
	return ID;
}
public void setID(int Id) {
	this.ID = Id;
}
public String getNome() {
	return Nome;
}
public void setNome(String Nome) {
	this.Nome = Nome;
}
public String getTelefone() {
	return Telefone;
}
public void setTelefone(String Telefone) {
	this.Telefone = Telefone;
}
public String getTipoCozinha() {
	return TipoCozinha;
}
public void setTipoCozinha(String TipoCozinha) {
	this.TipoCozinha = TipoCozinha;
}

}
