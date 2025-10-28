package ui;

import dao.RestauranteDAO; // Ainda usamos para a referência
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField; // Adicionado
import model.Restaurante;
import service.RestauranteService; // Adicionado (você precisará criar/atualizar este Service)

public class CadastroRestauranteController {

    @FXML private TextField nomeField;
    @FXML private TextField telefoneField;
    @FXML private TextField tipoCozinhaField;
    @FXML private TextField emailField;     // Novo
    @FXML private PasswordField senhaField; // Novo
    @FXML private Label statusLabel;
    @FXML private Button cadastrarButton;

    private MainApp mainApp;
    // O service deve lidar com a lógica do DAO e a inserção do Login
    private RestauranteService restauranteService;

    /**
     * Chamado pela MainApp para injetar dependências
     */
    public void initData(MainApp mainApp, RestauranteService restauranteService) {
        this.mainApp = mainApp;
        this.restauranteService = restauranteService;
    }

    @FXML
    private void handleCadastroButtonAction() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String tipoCozinha = tipoCozinhaField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText(); // A senha deve ser tratada como String pelo PasswordField

        try {
            // 1. Validação simples
            if (nome.isEmpty() || telefone.isEmpty() || tipoCozinha.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                statusLabel.setText("Por favor, preencha todos os campos.");
                return;
            }

            // 2. Chama o método unificado do RestauranteService
            boolean sucesso = restauranteService.cadastrarNovoRestaurante(nome, telefone, tipoCozinha, email, senha);

            if (sucesso) {
                // 3. Sucesso!
                exibirAlerta("Cadastro Realizado!", "Seu restaurante foi cadastrado! Faça o login.");

                // Volta para a tela de login
                mainApp.mostrarTelaLogin();
            } else {
                // 4. Falha (provavelmente email duplicado)
                statusLabel.setText("Erro ao cadastrar. O email já pode estar em uso.");
            }
        } catch (IllegalArgumentException ie) {
            // Pega validações do Service (ex: "Telefone inválido")
            statusLabel.setText(ie.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Erro inesperado ao cadastrar restaurante.");
        }
    }

    // ... (handleIrParaCliente e exibirAlerta mantidos) ...
    @FXML
    private void handleIrParaCliente() {
        if (mainApp != null) {
            mainApp.mostrarCadastroView();
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}