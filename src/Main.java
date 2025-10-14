import dao.ClienteDAO;
import model.Cliente;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDAO dao = new ClienteDAO();
		Cliente novo = new Cliente( "Eduardo", "rua ali","05585923457384");
		dao.inserirCliente(novo);
	}

}
