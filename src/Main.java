import dao.ClienteDAO;
import dao.ItemPedidoDAO;
import dao.PedidoDAO;
import dao.RestauranteDAO;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Restaurante;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDAO daoClient = new ClienteDAO();
		Cliente novoClient = new Cliente("joao", "5415154","rua j");
		daoClient.inserirCliente(novoClient);
		
		RestauranteDAO daoRest = new RestauranteDAO();
		Restaurante novoRest = new Restaurante("chicobem", "256364", "massas");
		daoRest.inserirRestaurante(novoRest);

	}

}
