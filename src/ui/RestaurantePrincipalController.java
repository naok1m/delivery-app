package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Pedido;
import model.Produto;
import model.Restaurante;
import service.PedidoService;
import service.ProdutoService;
import java.sql.SQLException;
import java.util.List;

public class RestaurantePrincipalController {

    // --- Componentes FXML ---
    @FXML private Label tituloRestauranteLabel;
    @FXML private Label statusProdutoLabel;

    // Pedidos Em Andamento
    @FXML private TableView<Pedido> pedidosEmAndamentoTable;
    @FXML private TableColumn<Pedido, Integer> andamentoIdColumn;
    @FXML private TableColumn<Pedido, String> andamentoClienteColumn;
    @FXML private TableColumn<Pedido, String> andamentoStatusColumn;

    // Gestão de Produtos
    @FXML private TextField nomeProdutoField;
    @FXML private TextField descricaoProdutoField;
    @FXML private TextField precoProdutoField;
    @FXML private TableView<Produto> produtosTable;
    @FXML private TableColumn<Produto, String> produtosNomeColumn;
    @FXML private TableColumn<Produto, Double> produtosPrecoColumn;
    @FXML private TableColumn<Produto, Boolean> produtosStatusColumn;


    // --- Dependências Injetadas ---
    private MainApp mainApp;
    private Restaurante restauranteLogado;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    private ObservableList<Pedido> pedidosEmAndamento;
    private ObservableList<Produto> produtosRestaurante;

    /**
     * Inicializa o Controller injetando o Restaurante logado e os Services.
     */
    public void initData(MainApp mainApp, Restaurante restaurante, ProdutoService produtoService, PedidoService pedidoService) {
        this.mainApp = mainApp;
        this.restauranteLogado = restaurante;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;

        tituloRestauranteLabel.setText(restaurante.getNome() + " - Painel de Gestão");

        configurarTabelas();
        carregarDados();
    }

    // --- Métodos de Inicialização ---

    private void configurarTabelas() {
        // Pedidos Em Andamento
        andamentoIdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        // Mapeia para o nome do Cliente dentro do objeto Cliente aninhado no Pedido (Requer Pedido.getCliente().getNome())
        andamentoClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cliente.nome"));

        andamentoStatusColumn.setCellValueFactory(new PropertyValueFactory<>("stats"));

        // Produtos
        produtosNomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        produtosPrecoColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        produtosStatusColumn.setCellValueFactory(new PropertyValueFactory<>("disponivel"));

        // Personaliza a célula do Status do Produto
        produtosStatusColumn.setCellFactory(column -> new TableCell<Produto, Boolean>() {
            @Override
            protected void updateItem(Boolean disponivel, boolean empty) {
                super.updateItem(disponivel, empty);
                if (empty || disponivel == null) {
                    setText(null);
                } else {
                    setText(disponivel ? "DISPONÍVEL" : "INDISPONÍVEL");
                }
            }
        });

        // Adiciona placeholders caso as tabelas estejam vazias
        pedidosEmAndamentoTable.setPlaceholder(new Label("Não há pedidos em andamento."));
        produtosTable.setPlaceholder(new Label("Nenhum produto cadastrado neste restaurante."));
    }

    private void carregarDados() {
        try {
            // 1. Carregar Pedidos EM ANDAMENTO
            List<Pedido> listaPedidos = pedidoService.listarPedidosEmAndamentoPorRestaurante(restauranteLogado.getID());
            pedidosEmAndamento = FXCollections.observableArrayList(listaPedidos);
            pedidosEmAndamentoTable.setItems(pedidosEmAndamento);

            // 2. Carregar Produtos
            List<Produto> listaProdutos = produtoService.listarTodosProdutosParaGestao(restauranteLogado.getID());
            produtosRestaurante = FXCollections.observableArrayList(listaProdutos);
            produtosTable.setItems(produtosRestaurante);

        } catch (SQLException e) {
            // *** CRÍTICO: Imprime o log de exceção SQL no console ***
            exibirAlerta("Erro de Banco de Dados", "Falha ao carregar dados! Detalhe: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace(); // <--- O ERRO DEVE ESTAR AQUI OU NA CAMADA DAO/SERVICE
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro inesperado ao carregar dados: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // --- Métodos de Ação: Pedidos ---

    @FXML
    private void handleMudarStatusPedido() {
        Pedido pedidoSelecionado = pedidosEmAndamentoTable.getSelectionModel().getSelectedItem();

        if (pedidoSelecionado != null) {
            String statusAtual = pedidoSelecionado.getStats();
            String proximoStatus;

            // 🚨 CORREÇÃO: Usar os status reais do seu ENUM MySQL
            switch (statusAtual) {
                case "Em Preparo": proximoStatus = "A Caminho"; break; // Ex: Muda de 'Em Preparo' para 'A Caminho'
                case "A Caminho": proximoStatus = "Entregue"; break;    // Ex: Muda de 'A Caminho' para 'Entregue'
                case "Entregue":
                    exibirAlerta("Aviso", "O pedido já foi entregue e não pode ser alterado.", Alert.AlertType.WARNING);
                    return;
                default:
                    exibirAlerta("Aviso", "Este status não permite avanço.", Alert.AlertType.WARNING);
                    return;
            }

            try {
                pedidoService.atualizarStatusPedido(pedidoSelecionado.getID(), proximoStatus);
                exibirAlerta("Sucesso", "Status do Pedido #" + pedidoSelecionado.getID() + " mudado para " + proximoStatus, Alert.AlertType.INFORMATION);
                // Recarrega os dados para atualizar a tabela
                carregarDados();
            } catch (Exception e) {
                exibirAlerta("Erro", "Falha ao avançar status: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            exibirAlerta("Aviso", "Por favor, selecione um pedido para alterar o status.", Alert.AlertType.WARNING);
        }
    }

    // --- Métodos de Ação: Produtos ---

    @FXML
    private void handleAddProduto() {
        String nome = nomeProdutoField.getText();
        String descricao = descricaoProdutoField.getText();
        String precoStr = precoProdutoField.getText();

        try {
            double preco = Double.parseDouble(precoStr);
            if (nome.isEmpty() || descricao.isEmpty() || preco <= 0) {
                statusProdutoLabel.setText("Preencha todos os campos e use um preço válido.");
                return;
            }

            Produto novoProduto = new Produto();
            novoProduto.setNome(nome);
            novoProduto.setDescricao(descricao);
            novoProduto.setValor(preco);
            // O setID e setDisponivel são tratados dentro do Service
            novoProduto.setRestaurante(restauranteLogado);

            produtoService.inserirNovoProduto(novoProduto);

            exibirAlerta("Sucesso", "Produto " + nome + " adicionado ao cardápio!", Alert.AlertType.INFORMATION);

            nomeProdutoField.clear();
            descricaoProdutoField.clear();
            precoProdutoField.clear();
            statusProdutoLabel.setText("");
            carregarDados();

        } catch (NumberFormatException e) {
            statusProdutoLabel.setText("O preço deve ser um número válido.");
        } catch (Exception e) {
            statusProdutoLabel.setText("Erro ao adicionar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMudarStatusProduto() {
        Produto produtoSelecionado = produtosTable.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            try {
                boolean statusAtual = produtoSelecionado.getDisponivel();
                boolean novoStatus = !statusAtual;

                String novoStatusString = novoStatus ? "DISPONÍVEL" : "INDISPONÍVEL";

                produtoService.mudarStatus(produtoSelecionado.getID(), novoStatusString);

                produtoSelecionado.setDisponivel(novoStatus);
                produtosTable.refresh();

                exibirAlerta("Sucesso", "Disponibilidade de " + produtoSelecionado.getNome() + " alterada para " + novoStatusString, Alert.AlertType.INFORMATION);

            } catch (Exception e) {
                exibirAlerta("Erro", "Falha ao mudar disponibilidade: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            exibirAlerta("Aviso", "Por favor, selecione um produto para alterar a disponibilidade.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleLogout() {
        if (mainApp != null) {
            mainApp.mostrarTelaLogin();
        }
    }

    // --- Auxiliar ---
    private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}