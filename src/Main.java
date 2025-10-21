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
		Cliente novoClient = new Cliente("joao", "sfasf","fsafafaw");
		daoClient.inserirCliente(novoClient);
		
		RestauranteDAO daoRest = new RestauranteDAO();
		Restaurante novoRest = new Restaurante("chicobem", "256364", "massas");
		daoRest.inserirRestaurante(novoRest);
		
		PedidoDAO daoPedido = new PedidoDAO();
		Pedido novoPedido = new Pedido(novoClient, novoRest, "jsiafja", "Em Preparo" );
		daoPedido.inserirPedido(novoPedido);
		
		
		ItemPedidoDAO dao = new ItemPedidoDAO();
		
		ItemPedido novo = new ItemPedido(novoPedido,null,3,23.45);

		
		dao.inserirItemPedido(novo);

	}

}
