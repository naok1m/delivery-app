package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Cliente;
import model.Restaurante; // IMPORTAR A CLASSE RESTAURANTE
import service.LoginService;

public class LoginController {

    // --- 1. DECLARAÇÃO DOS COMPONENTES FXML ---
    @FXML private TextField emailField;
    @FXML private PasswordField senhaField;
    @FXML private Button loginButton; // Embora não usado, é comum ter
    @FXML private Label statusLabel;

    // --- 2. DECLARAÇÃO DAS DEPENDÊNCIAS DE SERVIÇO E APP ---
    private LoginService loginService;
    private MainApp mainApp; // Referência para a MainApp (para trocar de tela)

    /**
     * Este método é chamado pela MainApp para "injetar"
     * as dependências necessárias. (ESSENCIAL!)
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
            // 1. CHAMA O MÉTODO GENÉRICO QUE RETORNA Cliente OU Restaurante
            Object usuarioAutenticado = loginService.autenticar(email, senha);

            if (usuarioAutenticado != null) {
                // SUCESSO!
                statusLabel.setText("Login bem-sucedido!");

                // 2. VERIFICA O TIPO DO USUÁRIO E REDIRECIONA
                if (usuarioAutenticado instanceof Cliente) {
                    mainApp.mostrarTelaRestaurantes((Cliente) usuarioAutenticado);
                } else if (usuarioAutenticado instanceof Restaurante) {
                    // Redireciona para a tela principal do restaurante
                    mainApp.mostrarTelaRestaurantePrincipal((Restaurante) usuarioAutenticado);
                } else {
                    // Caso o objeto retornado não seja nem Cliente nem Restaurante
                    statusLabel.setText("Erro: Tipo de usuário desconhecido.");
                }

            } else {
                // FALHA: Login Service retornou null (email/senha inválidos)
                statusLabel.setText("Email ou senha inválidos.");
            }

        } catch (Exception e) {
            statusLabel.setText("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Chamado quando o botão "Cadastre-se" é clicado.
     */
    @FXML
    private void handleCadastroButtonAction() {
        mainApp.mostrarTelaCadastro();
    }
}