package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Login;

public class LoginDAO {

    // Inserir um novo login
    public void inserirLogin(Login login) {
        String sql = "INSERT INTO Login (Email, Senha, TipoUsuario, Referencia) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ptsm.setString(1, login.getEmail());
            ptsm.setString(2, login.getSenha());
            ptsm.setString(3, login.getTipoUsuario());
            ptsm.setInt(4, login.getReferencia());

            ptsm.execute();

            // Pegar ID gerado
            ResultSet rs = ptsm.getGeneratedKeys();
            if (rs.next()) {
                login.setID(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Autenticar usuário pelo email e senha
    public Login autenticar(String email, String senha) {
        String sql = "SELECT * FROM Login WHERE Email = ? AND Senha = ?";
        Login login = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {

            ptsm.setString(1, email);
            ptsm.setString(2, senha);

            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                login = new Login(
                        rs.getInt("ID_Login"),
                        rs.getString("Email"),
                        rs.getString("Senha"),
                        rs.getString("TipoUsuario"),
                        rs.getInt("Referencia")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }

    // Buscar login por ID
    public Login buscarPorId(int id) {
        String sql = "SELECT * FROM Login WHERE ID_Login = ?";
        Login login = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ptsm = conn.prepareStatement(sql)) {

            ptsm.setInt(1, id);
            ResultSet rs = ptsm.executeQuery();
            if (rs.next()) {
                login = new Login(
                        rs.getInt("ID_Login"),
                        rs.getString("Email"),
                        rs.getString("Senha"),
                        rs.getString("TipoUsuario"),
                        rs.getInt("Referencia")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }
}
