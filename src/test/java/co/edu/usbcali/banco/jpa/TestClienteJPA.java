package co.edu.usbcali.banco.jpa;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

class TestClienteJPA {

	long clieId = 123456789L;

	@Test
	@DisplayName("CrearCliente")
	void aTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es Nulo");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 2L);
		assertNotNull(tipoDocumento, "El tipo de documento existe");

		cliente = new Cliente(clieId, "S", "Avenida Siempre viva 123", "hjsimpson@gmail.com", "Homero Jota Simpson",
				"315 550 44 40", tipoDocumento);

		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
	}

	@Test
	@DisplayName("ModificarCliente")
	void bTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es Nulo");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente NO existe");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento, "El Cliente NO existe");

		cliente.setActivo("N");
		cliente.setDireccion("Avenida Siempre viva 123");
		cliente.setEmail("hjsimpson@gmail.com");
		cliente.setNombre("Homero Jota Simpson Gomez");
		cliente.setTelefono("315 550 44 40");
		cliente.setTipoDocumento(tipoDocumento);

		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
	}

	@Test
	@DisplayName("BorrarCliente")
	void cTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es Nulo");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El Cliente NO existe");

		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
	}

	private final static Logger log = LoggerFactory.getLogger(TestClienteJPA.class); // orgslf4j

	@Test
	@DisplayName("ConsultarTodosClientes")
	void dTest() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("banco-logic");
		assertNotNull(entityManagerFactory, "El entityManagerFactory es Nulo");

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		assertNotNull(entityManager, "El entityManager es nulo");

		String JPQL = "SELECT cli FROM Cliente cli";
		List<Cliente> losClientes = entityManager.createQuery(JPQL).getResultList();

		// foreach tradicional
		/*for (Cliente cliente : losClientes) {
			// esto de log se usa en lugar de system.out.print, pero declarando private
			// final static Logger log
			log.info("Id: " + cliente.getClieId() + " Nombre: " + cliente.getNombre());
		}*/

		// foreach programacion funcional, solo desde las últimas versiones
		losClientes.forEach(cliente -> {
			log.info("Id: " + cliente.getClieId() + " Nombre: " + cliente.getNombre());
		});
	}

}