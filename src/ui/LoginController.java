package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Cliente;
import service.LoginService; // Seu serviço de Login

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button loginButton;
    @FXML private Label statusLabel;

    private LoginService loginService;
    private MainApp mainApp; // Referência para a MainApp (para trocar de tela)

    /**
     * Este método é chamado pela MainApp para "injetar"
     * as dependências necessárias.
     */
    public void initData(MainApp mainApp, LoginService loginService) {
        this.mainApp = mainApp;
        this.loginService = loginService;
    }

    /**
     * Chamado quando o botão "Entrar" é clicado (definido no FXML).
     */
    @FXML
    private void handleLoginButtonAction() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            statusLabel.setText("Por favor, preencha todos os campos.");
            return;
        }
        if (loginService == null) {
            statusLabel.setText("Erro: Serviço de login não iniciado.");
            return;
        }

        try {
            // ** LINHA CORRIGIDA AQUI **
            // Nós não criamos um objeto 'new Login()'.
            // Nós passamos as Strings diretamente para o service.
            Cliente clienteAutenticado = loginService.autenticarCliente(email, senha);

            if (clienteAutenticado != null) {
                // SUCESSO!
                statusLabel.setText("Login bem-sucedido!");
                mainApp.mostrarTelaRestaurantes(clienteAutenticado);

            } else {
                // FALHA
                statusLabel.setText("Email ou senha inválidos.");
            }

        } catch (Exception e) {
            // Este catch agora pegará qualquer erro inesperado
            statusLabel.setText("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCadastroButtonAction() {
        mainApp.mostrarTelaCadastro(); // Chama a MainApp para trocar de tela
    }
}