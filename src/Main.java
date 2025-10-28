
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Restaurante;
import service.ClienteService;
import service.PedidoService;
import service.RestauranteService;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClienteService clienteService = new ClienteService();
    private static RestauranteService restauranteService = new RestauranteService();
    private static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE DELIVERY ===");
        System.out.println("Bem-vindo ao sistema de delivery!");
        
        try {
            // Demonstração do sistema
            demonstrarSistema();
        } catch (Exception e) {
            System.err.println("Erro no sistema: " + e.getMessage());
            e.printStackTrace();
        }
        
        scanner.close();
    }

    private static void demonstrarSistema() throws SQLException {
        System.out.println("\n--- DEMONSTRAÇÃO DO SISTEMA ---");
        
        // 1. Cadastrar cliente
        System.out.println("\n1. Cadastrando cliente...");
        Cliente cliente = new Cliente("João Silva", "Rua das Flores, 123", "(11) 99999-9999");
        clienteService.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado: " + cliente.getNome());

        // 2. Cadastrar restaurante
        System.out.println("\n2. Cadastrando restaurante...");
        Restaurante restaurante = new Restaurante("Pizzaria do João", "(11) 3333-3333", "Pizza");
        System.out.println("Restaurante cadastrado: " + restaurante.getNome());

        // 3. Criar produtos
        System.out.println("\n3. Criando produtos...");
        Produto produto1 = new Produto(restaurante, "Pizza Margherita", "Pizza com molho de tomate e mussarela", 35.90, true);
        Produto produto2 = new Produto(restaurante, "Pizza Calabresa", "Pizza com calabresa e cebola", 42.50, true);
        
        
        // 4. Criar itens do pedido
        System.out.println("\n4. Criando itens do pedido...");
        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido(null, "Pizza Margherita", 1, 35.90, produto1));
        itens.add(new ItemPedido(null, "Pizza Calabresa", 1, 42.50, produto2));
        
        System.out.println("Itens criados:");
        for (ItemPedido item : itens) {
            System.out.println("- " + item.getDescrição() + " x" + item.getQuantidade() + " = R$ " + item.getSubtotal());
        }

        // 5. Criar pedido
        System.out.println("\n5. Criando pedido...");
        Pedido pedido = pedidoService.criarPedido(cliente.getID(), restaurante.getID(), itens);
        System.out.println("Pedido criado com sucesso!");
        System.out.println("ID do pedido: " + pedido.getID());
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Restaurante: " + pedido.getRestaurante().getNome());
        System.out.println("Data/Hora: " + pedido.getDataHora());
        System.out.println("Status: " + pedido.getStats());
        System.out.println("Valor Total: R$ " + String.format("%.2f", pedido.getValorTotal()));

        // 6. Atualizar status do pedido
        System.out.println("\n6. Atualizando status do pedido...");
        pedidoService.atualizarStatusPedido(pedido.getID(), "PREPARANDO");
        System.out.println("Status atualizado para: PREPARANDO");

        // 7. Listar pedidos do cliente
        System.out.println("\n7. Listando pedidos do cliente...");
        List<Pedido> pedidosCliente = pedidoService.listarPedidosPorCliente(cliente.getID());
        System.out.println("Pedidos do cliente " + cliente.getNome() + ":");
        for (Pedido p : pedidosCliente) {
            System.out.println("- Pedido #" + p.getID() + " - " + p.getStats() + " - R$ " + String.format("%.2f", p.getValorTotal()));
        }

        System.out.println("\n--- DEMONSTRAÇÃO CONCLUÍDA ---");
        System.out.println("Sistema funcionando corretamente!");
    }
}
