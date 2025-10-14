import dao.ClienteDAO;
import model.Cliente;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDAO dao = new ClienteDAO();
		Cliente novo = new Cliente(1, "Thiago", "thiago@email.com", "9999999");
		dao.inserirCliente(novo);
	}

}
