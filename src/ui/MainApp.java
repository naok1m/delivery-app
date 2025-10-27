package ui;

import dao.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Cliente;
import model.Restaurante; // 1. IMPORTAR MODELO RESTAURANTE
import service.ClienteService;
import service.LoginService;
import service.PedidoService;
import service.ProdutoService;
import service.RestauranteService; // 2. IMPORTAR SERVICE RESTAURANTE

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApp extends Application {

    private Stage primaryStage;

    // Nossos serviços de backend
    private LoginService loginService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;
    private ClienteService clienteService;
    private RestauranteService restauranteService; // 3. ADICIONAR SERVICE

    @Override
    public void init() throws Exception {
        try {
            // Teste de conexão
            try (Connection testConn = ConnectionFactory.getConnection()) {
                if (testConn == null) {
                    throw new SQLException("ConnectionFactory retornou conexão nula.");
                }
                System.out.println("Conexão com banco de dados estabelecida.");
            }

            // Inicializa TODOS os serviços
            loginService = new LoginService();
            produtoService = new ProdutoService();
            pedidoService = new PedidoService();
            clienteService = new ClienteService();
            restauranteService = new RestauranteService(); // 4. INICIALIZAR SERVICE

            System.out.println("Serviços inicializados.");

        } catch (Exception e) {
            System.err.println("Erro crítico ao inicializar serviços ou DB!");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mostrarTelaLogin(); // O app começa aqui
    }

    /**
     * 1. Mostra a Tela de Login (Método existente)
     */
    public void mostrarTelaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/LoginView.fxml"));
            Parent root = loader.load();
            LoginController controller = loader.getController();
            controller.initData(this, loginService);
            primaryStage.setTitle("Login");
            primaryStage.setScene(new Scene(root, 300, 300));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. Mostra a Tela de Cadastro (Método existente)
     */
    public void mostrarTelaCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CadastroView.fxml"));
            Parent root = loader.load();
            CadastroController controller = loader.getController();
            controller.initData(this, clienteService);
            primaryStage.setTitle("Cadastro");
            primaryStage.setScene(new Scene(root, 350, 450));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. Mostra a Tela de Restaurantes (NOVO MÉTODO)
     * (Chamado pelo LoginController)
     */
    public void mostrarTelaRestaurantes(Cliente clienteLogado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/RestauranteView.fxml"));
            Parent root = loader.load();
            RestauranteController controller = loader.getController();

            // Injeta o cliente e o serviço de restaurante
            controller.initData(this, restauranteService, clienteLogado);

            primaryStage.setTitle("Escolha um Restaurante");
            primaryStage.setScene(new Scene(root, 350, 400));

        } catch (IOException e) {
            System.err.println("Erro ao carregar RestauranteView.fxml!");
            e.printStackTrace();
        }
    }


    /**
     * 4. Mostra a Tela Principal (MÉTODO MODIFICADO)
     * (Chamado pelo RestauranteController)
     * ESTE É O MÉTODO ONDE O SEU ERRO ESTAVA ACONTECENDO
     */
    public void mostrarTelaPrincipal(Cliente clienteLogado, Restaurante restauranteSelecionado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/DeliveryView.fxml"));
            Parent root = loader.load();

            DeliveryUIController controller = loader.getController();

            // AGORA SIM: Passa os 4 argumentos necessários
            controller.initData(clienteLogado, restauranteSelecionado, produtoService, pedidoService);

            primaryStage.setTitle("Cardápio - " + restauranteSelecionado.getNome());
            primaryStage.setScene(new Scene(root, 700, 450));

        } catch (IOException e) {
            System.err.println("Erro ao carregar DeliveryView.fxml!");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Aplicação encerrada.");
    }

    public static void main(String[] args) {
        try {
            Class.forName("javafx.application.Application");
            launch(args);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: JavaFX Runtime Components não encontrados.");
            System.err.println("Verifique os VM arguments: --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml");
            e.printStackTrace();
        }
    }
}