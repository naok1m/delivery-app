package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	public int ID;
	public Cliente Cliente;
	public Restaurante Restaurante;
	public String DataHora;
	public String Stats;
	private double valorTotal;
	private List<ItemPedido> itens;
	
	
	public Pedido(int id, Cliente cliente, Restaurante restaurante, String dataHora, String stats) {
		this.ID = id;
		this.Cliente = cliente;
		this.Restaurante = restaurante;
		this.DataHora = dataHora;
		this.Stats = stats;
	}
	public Pedido(Cliente cliente, Restaurante restaurante, String dataHora, String stats, List<ItemPedido> itens) {
		this.Cliente = cliente;
		this.Restaurante = restaurante;
		this.DataHora = dataHora;
		this.Stats = stats;
		this.itens = itens;
		calcularValorTotal();
	}
	
	public Pedido() {
        this.itens = new ArrayList<>();
    }
	public void calcularValorTotal() {
        double total = 0;
        if (itens != null) {
            for (ItemPedido item : itens) {
                total += item.getSubtotal();
            }
        }
        this.valorTotal = total;
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
	public double getValorTotal() {
        return valorTotal;
    }
	public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
	public void adicionarItem(ItemPedido item) {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        itens.add(item);
        calcularValorTotal();
    }
	public List<ItemPedido> getItens() {
        return itens;
    }
	public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
        calcularValorTotal(); // recalcula sempre que a lista é atualizada
    }
	
	
}
