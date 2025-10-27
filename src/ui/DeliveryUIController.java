package ui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import model.Cliente;
import model.Restaurante; // <-- 9. IMPORTAR RESTAURANTE
import service.PedidoService;
import service.ProdutoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryUIController {

    @FXML private ListView<Produto> menuListView;
    @FXML private ListView<ItemPedido> carrinhoListView;
    @FXML private Label totalLabel;

    @FXML private Label restauranteNomeLabel;

    // --- DADOS E SERVIÇOS ---
    private MainApp mainApp;
    private ProdutoService produtoService;
    private PedidoService pedidoService;
    private Cliente clienteLogado;
    private Restaurante restauranteAtual; // <-- 11. Armazena o restaurante

    // (A variável idRestauranteAtual não é mais necessária)

    private ObservableList<Produto> menuItens = FXCollections.observableArrayList();
    private ObservableList<ItemPedido> carrinhoItens = FXCollections.observableArrayList();

    /**
     * O initialize() continua o mesmo
     */
    @FXML
    public void initialize() {
        menuListView.setItems(menuItens);
        carrinhoListView.setItems(carrinhoItens);
        carrinhoItens.addListener((ListChangeListener<ItemPedido>) c -> atualizarTotal());
        carrinhoListView.setCellFactory(lv -> new javafx.scene.control.ListCell<ItemPedido>() {
            @Override
            protected void updateItem(ItemPedido item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%s x %d - R$ %.2f",
                            item.getProduto().getNome(),
                            item.getQuantidade(),
                            item.getSubtotal()));
                }
            }
        });
    }

    /**
     * 12. MÉTODO initData() MODIFICADO
     * Agora recebe o objeto Restaurante
     */
    public void initData(MainApp mainApp, Cliente cliente, Restaurante restaurante, ProdutoService produtoService, PedidoService pedidoService) {
        this.mainApp = mainApp;
        this.clienteLogado = cliente;
        this.restauranteAtual = restaurante; // Recebe o restaurante
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;

        System.out.println("Tela Principal iniciada para: " + clienteLogado.getNome());
        System.out.println("Restaurante selecionado: " + restauranteAtual.getNome());

        // Define o nome do restaurante no Label
        restauranteNomeLabel.setText(restauranteAtual.getNome());

        // Carrega o menu imediatamente
        carregarMenuDoRestaurante();
    }

    /**
     * 13. MÉTODO carregarMenuDoRestaurante() MODIFICADO
     * Não é mais @FXML e usa o objeto 'restauranteAtual'
     */
    private void carregarMenuDoRestaurante() {
        if (produtoService == null) {
            exibirAlerta("Erro", "ProdutoService não inicializado.");
            return;
        }
        try {
            // Pega o ID do objeto
            int idRestaurante = restauranteAtual.getID();

            // CHAMA O SEU SERVICE (ProdutoService)
            List<Produto> produtosDoBanco = produtoService.listarProdutosPorRestaurante(idRestaurante);

            menuItens.clear();
            if (produtosDoBanco != null) {
                menuItens.addAll(produtosDoBanco);
                if (produtosDoBanco.isEmpty()){
                    exibirAlerta("Cardápio Vazio", "Nenhum produto encontrado para este restaurante.");
                }
            } else {
                exibirAlerta("Erro", "Não foi possível carregar o cardápio.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro de Banco de Dados", "Não foi possível carregar o cardápio: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void finalizarPedido() {
        if (carrinhoItens.isEmpty()) {
            exibirAlerta("Carrinho Vazio", "Adicione itens ao carrinho.");
            return;
        }

        // 14. USA OS OBJETOS ARMAZENADOS
        int idCliente = clienteLogado.getID();
        int idRestaurante = restauranteAtual.getID(); // Pega o ID do objeto

        List<ItemPedido> itensParaSalvar = new ArrayList<>(carrinhoItens);

        try {
            // CHAMA O SEU SERVICE (PedidoService)
            Pedido pedidoCriado = pedidoService.criarPedido(idCliente, idRestaurante, itensParaSalvar);

            if (pedidoCriado != null && pedidoCriado.getID() > 0) {
                exibirAlerta("Pedido Realizado!",
                        String.format("Pedido #%d realizado com sucesso!\nCliente: %s\nTotal: R$ %.2f",
                                pedidoCriado.getID(),
                                clienteLogado.getNome(),
                                pedidoCriado.getValorTotal()));
                carrinhoItens.clear();
            } else {
                exibirAlerta("Erro ao Salvar", "Não foi possível criar o pedido.");
            }
        } catch (Exception e) {
            exibirAlerta("Erro Inesperado", "Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //
    // --- O restante dos métodos (adicionarAoCarrinho, removerDoCarrinho,
    // --- atualizarTotal, exibirAlerta) continuam EXATAMENTE IGUAIS.
    //

    @FXML
    private void adicionarAoCarrinho() {
        Produto produtoSelecionado = menuListView.getSelectionModel().getSelectedItem();
        if (produtoSelecionado == null) {
            exibirAlerta("Seleção Necessária", "Selecione um produto do cardápio.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Quantidade");
        dialog.setHeaderText("Quantos '" + produtoSelecionado.getNome() + "' você deseja?");
        dialog.setContentText("Quantidade:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int quantidade = Integer.parseInt(result.get());
                if (quantidade <= 0) {
                    exibirAlerta("Quantidade Inválida", "A quantidade deve ser maior que zero.");
                    return;
                }
                Optional<ItemPedido> itemExistente = carrinhoItens.stream()
                        .filter(item -> item.getProduto().getID() == produtoSelecionado.getID())
                        .findFirst();
                if (itemExistente.isPresent()) {
                    ItemPedido item = itemExistente.get();
                    item.setQuantidade(item.getQuantidade() + quantidade);
                    carrinhoListView.refresh();
                } else {
                    ItemPedido novoItem = new ItemPedido(null,
                            produtoSelecionado.getNome(),
                            quantidade,
                            produtoSelecionado.getValor(),
                            produtoSelecionado);
                    carrinhoItens.add(novoItem);
                }
                atualizarTotal();
            } catch (NumberFormatException e) {
                exibirAlerta("Erro de Entrada", "Quantidade inválida. Por favor, insira um número.");
            }
        }
    }

    @FXML
    private void removerDoCarrinho() {
        ItemPedido itemSelecionado = carrinhoListView.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            carrinhoItens.remove(itemSelecionado);
        } else {
            exibirAlerta("Seleção Necessária", "Selecione um item do carrinho para remover.");
        }
    }

    private void atualizarTotal() {
        double total = 0.0;
        for (ItemPedido item : carrinhoItens) {
            total += item.getSubtotal();
        }
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    @FXML
    private void handleVoltarButtonAction() {
        // Usa o mainApp para trocar de tela, passando o cliente
        // para que a tela de restaurantes saiba quem é o usuário.
        mainApp.mostrarTelaRestaurantes(clienteLogado);
    }
}