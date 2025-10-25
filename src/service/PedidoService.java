package service;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.PedidoDAO;
import dao.ItemPedidoDAO;
import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Restaurante;

public class PedidoService {

    private PedidoDAO pedidoDAO;
    private ItemPedidoDAO itemPedidoDAO;
    private ClienteService clienteService;
    private RestauranteService restauranteService;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.itemPedidoDAO = new ItemPedidoDAO();
        this.clienteService = new ClienteService();
        this.restauranteService = new RestauranteService();
    }

    // Criar um novo pedido
    public Pedido criarPedido(int idCliente, int idRestaurante, List<ItemPedido> itens) throws SQLException {
        // Validações básicas
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Pedido deve conter pelo menos um item");
        }

        // Buscar cliente e restaurante
        Cliente cliente = clienteService.buscarPorId(idCliente);
        Restaurante restaurante = restauranteService.buscarPorId(idRestaurante);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        if (restaurante == null) {
            throw new IllegalArgumentException("Restaurante não encontrado");
        }

        // Validar itens
        validarItens(itens);

        // Criar objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setDataHora(getDataHoraAtual());
        pedido.setStatus("PENDENTE");
        pedido.setItens(itens);

        // Calcular valor total
        double total = calcularValorTotal(itens);
        pedido.setValorTotal(total);

        // Salvar pedido no banco
        pedidoDAO.inserirPedido(pedido);

        // Salvar itens do pedido
        for (ItemPedido item : itens) {
            item.setPedido(pedido);
            itemPedidoDAO.inserirItemPedido(item);
        }

        return pedido;
    }

    // Buscar pedido por ID
    public Pedido buscarPedidoPorId(int idPedido) throws SQLException {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("ID do pedido deve ser maior que zero");
        }
        return pedidoDAO.buscarPorId(idPedido);
    }

    // Listar pedidos de um cliente
    public List<Pedido> listarPedidosPorCliente(int idCliente) throws SQLException {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("ID do cliente deve ser maior que zero");
        }
        return pedidoDAO.listarPorCliente(idCliente);
    }

    // Atualizar status do pedido
    public void atualizarStatusPedido(int idPedido, String novoStatus) throws SQLException {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("ID do pedido deve ser maior que zero");
        }
        if (novoStatus == null || novoStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser vazio");
        }

        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }

        pedido.setStatus(novoStatus);
        pedidoDAO.atualizarStatus(idPedido, novoStatus);
    }

    // Cancelar pedido
    public void cancelarPedido(int idPedido) throws SQLException {
        atualizarStatusPedido(idPedido, "CANCELADO");
    }

    // Métodos auxiliares privados
    private void validarItens(List<ItemPedido> itens) {
        for (ItemPedido item : itens) {
            if (item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero");
            }
            if (item.getValor() <= 0) {
                throw new IllegalArgumentException("Valor deve ser maior que zero");
            }
        }
    }

    private double calcularValorTotal(List<ItemPedido> itens) {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    private String getDataHoraAtual() {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return agora.format(formatter);
    }
}
