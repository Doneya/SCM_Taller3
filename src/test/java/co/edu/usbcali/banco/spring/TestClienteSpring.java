package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.banco.domain.Cliente;
import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestClienteSpring {

	long clieId = 123456789L;

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@DisplayName("CrearCliente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // spring
	void aTest() {
		assertNotNull(entityManager);

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNull(cliente, "El cliente ya existe");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 2L);
		assertNotNull(tipoDocumento, "El tipo de documento No existe");

		cliente = new Cliente(clieId, "S", "Avenida Siempre viva 123", "hjsimpson@gmail.com", "Homero Jota Simpson",
				"315 550 44 40", tipoDocumento);

		entityManager.persist(cliente);
	}

	@Test
	@DisplayName("ModificarCliente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // spring
	void bTest() {
		assertNotNull(entityManager);

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente No existe");

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, 1L);
		assertNotNull(tipoDocumento, "El Tipo de Documento No existe");

		cliente.setActivo("N");
		cliente.setDireccion("Cra 2c # 44 - 56");
		cliente.setEmail("doneya23@gmail.com");
		cliente.setNombre("Doneya Restrepo");
		cliente.setTelefono("315 550 44 40");
		cliente.setTipoDocumento(tipoDocumento);

		entityManager.merge(cliente);
	}

	@Test
	@DisplayName("BorrarCliente")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // spring
	void cTest() {
		assertNotNull(entityManager);

		Cliente cliente = entityManager.find(Cliente.class, clieId);
		assertNotNull(cliente, "El cliente No existe");

		entityManager.remove(cliente);
	}

	private final static Logger log = LoggerFactory.getLogger(TestClienteSpring.class);// orgslf4j

	@Test
	@DisplayName("consultarTodosClientes")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // spring
	void dTest() {
		assertNotNull(entityManager);

		String JPQL = "SELECT cli FROM Cliente cli";
		List<Cliente> losClientes = entityManager.createQuery(JPQL).getResultList();

		// foreach tradicional
		/*
		 * for (Cliente cliente : losClientes) { // esto de log se usa en lugar de
		 * system.out.print, pero declarando private // final static Logger log
		 * log.info("Id: " + cliente.getClieId() + " Nombre: " + cliente.getNombre()); }
		 */

		// foreach programacion funcional, solo desde las últimas versiones
		losClientes.forEach(cliente -> {
			log.info("Id: " + cliente.getClieId() + " Nombre: " + cliente.getNombre());
		});
	}

}
