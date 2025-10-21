package model;

public class ItemPedido {
	public int ID;
    public Pedido Pedido;
    public String Descrição;
    public int Quantidade;
    public double Valor;

    public ItemPedido(Pedido pedido, String descrição, int quantidade, double valor) {
        this.Pedido = pedido;
        this.Descrição = (descrição != null)? descrição: "Sem descrição";
        this.Quantidade = quantidade;
        this.Valor = valor;
    }

    public ItemPedido(int id, Pedido pedido, String descrição, int quantidade, double valor) {
        this.ID = id;
        this.Pedido = pedido;
        this.Descrição = (descrição != null)? descrição: "Sem descrição";
        this.Quantidade = quantidade;
        this.Valor = valor;
    }

    public int getID() {
    	return ID;
    }
    public void setID(int id) {
    	this.ID = id;
    }
    public Pedido getPedido(){
    	return Pedido;
    }
    public void setPedido(Pedido Pedido) {
    	this.Pedido = Pedido;
    }
    public String getDescrição(){
    	return Descrição;
    }
    public void  setDescrição(String descrição){
    	this.Descrição = descrição;
    }
    public int getQuantidade(){
    	return Quantidade;
    }
    public void setQuantidade(int quantidade){
    	this.Quantidade = quantidade;
    }
    public double getValor(){
    	return Valor;
    }
    public void setValor(double valor){
    	this.Valor = valor;
    }

}
