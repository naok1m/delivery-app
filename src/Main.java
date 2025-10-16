import dao.ClienteDAO;
import model.Cliente;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClienteDAO dao = new ClienteDAO();
		Cliente novo = new Cliente( "Eduardo", "rua ali","05585923457384", "edu@email.com");

		Cliente novo2 = new Cliente( "Maria", "rua ali","49154985", "mariadu@email.com");
		
		dao.inserirCliente(novo);
		dao.inserirCliente(novo2);
		Cliente buscado = dao.buscarPorId(3);
		Cliente buscado2 = dao.buscarPorId(7);
		System.out.println("Cliente buscado: + " + buscado.getNome());

		System.out.println("Cliente buscado: + " + buscado2.getNome());
	}

}
