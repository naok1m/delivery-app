package service;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import dao.PedidoDAO;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Restaurante;

public class PedidoService {

    private PedidoDAO pedidoDAO;
    private ClienteService clienteService;
    private RestauranteService restauranteService;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.clienteService = new ClienteService();
        this.restauranteService = new RestauranteService();
    }

    // Criar um novo pedido
    public Pedido criarPedido(int idCliente, int idRestaurante, List<ItemPedido> itens) throws SQLException {
        Cliente cliente = clienteService.buscarPorId(idCliente);
        Restaurante restaurante = restauranteService.buscarPorId(idRestaurante);

        if (cliente == null) throw new IllegalArgumentException("Cliente não encontrado");
        if (restaurante == null) throw new IllegalArgumentException("Restaurante não encontrado");

        // 2️⃣ Criar objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setDataHora(null);
        pedido.setItens(itens);

        // 3️⃣ Calcular valor total
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        pedido.setValorTotal(total);

        // 4️⃣ Inserir no banco
        pedidoDAO.inserirPedido(pedido); // o DAO deve salvar o pedido e os itens

        return pedido;
    }


    
}
