package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.ClienteService; // O service que acabamos de preencher

public class CadastroController {

    @FXML private TextField nomeField;
    @FXML private TextField enderecoField;
    @FXML private TextField telefoneField;
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Label statusLabel;

    private MainApp mainApp;
    private ClienteService clienteService;

    /**
     * Chamado pela MainApp para injetar dependências
     */
    public void initData(MainApp mainApp, ClienteService clienteService) {
        this.mainApp = mainApp;
        this.clienteService = clienteService;
    }

    @FXML
    private void handleCadastroButtonAction() {
        String nome = nomeField.getText();
        String endereco = enderecoField.getText();
        String telefone = telefoneField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();

        try {
            // 1. Validação simples (o service fará a validação principal)
            if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                statusLabel.setText("Por favor, preencha todos os campos.");
                return;
            }

            // 2. Chama o NOVO método do ClienteService
            boolean sucesso = clienteService.cadastrarNovoCliente(nome, endereco, telefone, email, senha);

            if (sucesso) {
                // 3. Sucesso!
                exibirAlerta("Cadastro Realizado!", "Sua conta foi criada com sucesso. Faça o login.");
                mainApp.mostrarTelaLogin(); // Volta para a tela de login
            } else {
                // 4. Falha (provavelmente email duplicado)
                statusLabel.setText("Erro ao cadastrar. O email já pode estar em uso.");
            }
        } catch (IllegalArgumentException ie) {
            // Pega a validação que você criou (ex: "Nome obrigatorio")
            statusLabel.setText(ie.getMessage());
        } catch (Exception e) {
            statusLabel.setText("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVoltarButtonAction() {
        mainApp.mostrarTelaLogin(); // Apenas volta para o login
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}