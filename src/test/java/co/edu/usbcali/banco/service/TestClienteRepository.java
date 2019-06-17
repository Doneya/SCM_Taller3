package co.edu.usbcali.banco.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
class TestClienteRepository {
	
	@Autowired
	private TipoDocumentoService tipoDocumentoService;
	
	@Autowired
	private ClienteService clienteService;

	long clieId = 15623789254L;

	@Test
	@DisplayName("CrearCliente")	
	void aTest()throws Exception {
		assertNotNull(clienteService);
		assertNotNull(tipoDocumentoService);

		Cliente cliente = clienteService.findByid(clieId);
		assertNull(cliente, "El cliente ya existe");

		TipoDocumento tipoDoc=tipoDocumentoService.findById(1L);

		cliente = new Cliente(clieId, "S", "Avenida Siempre viva 123", "hjsimpson@gmail.com", "Doneya",
				"315 550 44 40", tipoDoc);

		clienteService.save(cliente);
	}

	@Test
	@DisplayName("ModificarCliente")	
	void bTest()throws Exception {
		assertNotNull(clienteService);
		assertNotNull(tipoDocumentoService);

		Cliente cliente = clienteService.findByid(clieId);
		assertNotNull(cliente, "El cliente No existe");

		TipoDocumento tipoDoc = tipoDocumentoService.findById(1L);
		assertNotNull(tipoDoc, "El Tipo de Documento No existe");

		cliente.setActivo("N");
		cliente.setDireccion("Cra 2c # 44 - 56");
		cliente.setEmail("doneya23@gmail.com");
		cliente.setNombre("Doneya");
		cliente.setTelefono("315 550 44 40");
		cliente.setTipoDocumento(tipoDoc);

		clienteService.update(cliente);
	}

	@Test
	@DisplayName("BorrarCliente")	
	void cTest()throws Exception {
		assertNotNull(clienteService);

		Cliente cliente = clienteService.findByid(clieId);
		assertNotNull(cliente, "El cliente No existe");

		clienteService.delete(cliente);
	}

	private final static Logger log = LoggerFactory.getLogger(TestClienteRepository.class);// orgslf4j

	@Test
	@DisplayName("consultarTodosClientes")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class) // spring
	void dTest() {
		assertNotNull(clienteService);
		
		List<Cliente> losClientes = clienteService.findAll();

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
