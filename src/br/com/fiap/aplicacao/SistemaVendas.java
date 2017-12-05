package br.com.fiap.aplicacao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.fiap.dao.GenericDao;
import br.com.fiap.entity.Clientes;
import br.com.fiap.entity.Pedidos;

public class SistemaVendas {

	public static void main(String[] args) {
		incluirCliente();
		listarClientes();
		buscarCliente(1);
		listarPedidos();
		System.exit(1);
	}

	private static void incluirCliente() {
		System.out.println("Incluindo cliente...");
		Clientes cliente = new Clientes();
		cliente.setEmail("taiane.d.santos@accenture.com");
		cliente.setNome("Tatiane dos Santos");

		Pedidos pedido = new Pedidos();
		pedido.setCliente(cliente);
		pedido.setData(new Date());
		pedido.setDescricao("Notebook");
		pedido.setValor(30000);

		cliente.getPedidos().add(pedido);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -10);

		Pedidos pedido2 = new Pedidos();
		pedido2.setCliente(cliente);
		pedido2.setData(cal.getTime());
		pedido2.setDescricao("Impressora");
		pedido2.setValor(1000);

		cliente.getPedidos().add(pedido2);

		try {
			GenericDao<Clientes> dao = new GenericDao<>(Clientes.class);
			dao.adicionar(cliente);
			System.out.println("Cliente incluído com sucesso.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void listarClientes() {
		System.out.println();
		System.out.println("*** Lista de cliente ***");
		try {
			GenericDao<Clientes> dao = new GenericDao<>(Clientes.class);
			List<Clientes> clientes = dao.listar();
			for (Clientes cliente : clientes) {
				System.out.println(cliente.getNome() + ": " + cliente.getEmail());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("**********************");
	}

	private static void buscarCliente(Integer idCliente) {
		System.out.println();
		System.out.println("Buscando cliente de código: " + idCliente);
		GenericDao<Clientes> dao = new GenericDao<>(Clientes.class);
		Clientes f = dao.buscar(idCliente);
		System.out.println(f.getNome() + ": " + f.getEmail());
	}

	private static void listarPedidos() {
		System.out.println();
		System.out.println("*** Lista de pedidos ***");
		GenericDao<Pedidos> dao = new GenericDao<>(Pedidos.class);
		List<Pedidos> pedidos = dao.listar();
		if (pedidos.size() > 0) {
			System.out.println("Foram encontrados " + pedidos.size() + " pedido(s) do cliente "
					+ pedidos.get(0).getCliente().getNome());
			for (Pedidos pedido : pedidos) {
				System.out.println("**********************");
				System.out.println("Descrição: " + pedido.getDescricao());
				System.out.println("Valor: " + pedido.getValor());
			}
		} else {
			System.out.println("Não foram encontrados pedidos.");
		}
	}

}