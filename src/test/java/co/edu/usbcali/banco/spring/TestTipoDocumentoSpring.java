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

import co.edu.usbcali.banco.domain.TipoDocumento;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoDocumentoSpring {

	Long tdocId = 4L;

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@DisplayName("CrearTipoDocumento")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void aTest() {

		assertNotNull(entityManager);

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNull(tipoDocumento, "El tipo de Documento ya existe");

		tipoDocumento = new TipoDocumento(tdocId, "S", "Pasaporte");

		entityManager.persist(tipoDocumento);
	}

	@Test
	@DisplayName("ModificarTipoDocumento")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void bTest() {

		assertNotNull(entityManager);

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento, "El Tipo de Documento No existe");

		tipoDocumento.setNombre("Cédula Extranjeria");
		tipoDocumento.setActivo("N");

		entityManager.merge(tipoDocumento);
	}

	@Test
	@DisplayName("BorrarTipoDocumento")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void cTest() {

		assertNotNull(entityManager);

		TipoDocumento tipoDocumento = entityManager.find(TipoDocumento.class, tdocId);
		assertNotNull(tipoDocumento, "El Tipo de Documento No existe");

		entityManager.remove(tipoDocumento);
	}

	private final static Logger log = LoggerFactory.getLogger(TestTipoDocumentoSpring.class);

	@Test
	@DisplayName("BorrarTipoDocumento")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void dTest() {
		assertNotNull(entityManager);

		String JPQL = "SELECT cli FROM TipoDocumento cli";
		List<TipoDocumento> losTiposDocumentos = entityManager.createQuery(JPQL).getResultList();

		losTiposDocumentos.forEach(tipoDocumento -> {
			log.info("Id: " + tipoDocumento.getTdocId() + " Nombre: " + tipoDocumento.getNombre());
		});
	}
}
