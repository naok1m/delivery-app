package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Cliente;
import model.Restaurante;
import service.RestauranteService; // O service que preenchemos

import java.util.List;

public class RestauranteController {

    @FXML private Label welcomeLabel;
    @FXML private ListView<Restaurante> restauranteListView;
    @FXML private Label statusLabel;

    private MainApp mainApp;
    private RestauranteService restauranteService;
    private Cliente clienteLogado;

    // Lista "observável" que a ListView usa para se atualizar
    private ObservableList<Restaurante> listaRestaurantes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liga a ListView à nossa lista de dados
        restauranteListView.setItems(listaRestaurantes);
    }

    /**
     * Chamado pela MainApp para injetar os dados necessários.
     */
    public void initData(MainApp mainApp, RestauranteService restauranteService, Cliente cliente) {
        this.mainApp = mainApp;
        this.restauranteService = restauranteService;
        this.clienteLogado = cliente;

        welcomeLabel.setText("Olá, " + cliente.getNome() + "!");
        carregarRestaurantes();
    }

    /**
     * Usa o RestauranteService para popular a lista.
     */
    private void carregarRestaurantes() {
        try {
            // CHAMA O SEU SERVICE
            List<Restaurante> restaurantes = restauranteService.listarTodosRestaurantes();
            listaRestaurantes.clear();
            listaRestaurantes.addAll(restaurantes);
        } catch (Exception e) {
            statusLabel.setText("Erro ao carregar restaurantes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chamado quando o botão "Ver Cardápio" é clicado.
     */
    @FXML
    private void handleSelecionarRestaurante() {
        Restaurante selecionado = restauranteListView.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            statusLabel.setText("Por favor, selecione um restaurante.");
            return;
        }

        // Chama a MainApp para ir para a próxima tela (a de delivery)
        mainApp.mostrarTelaPrincipal(clienteLogado, selecionado);
    }
    @FXML
    private void handleLogoutButtonAction() {
        // Simplesmente chama a MainApp para mostrar o login
        mainApp.mostrarTelaLogin();
    }
}