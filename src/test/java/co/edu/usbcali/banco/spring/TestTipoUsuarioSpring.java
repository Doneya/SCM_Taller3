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

import co.edu.usbcali.banco.domain.TipoUsuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestTipoUsuarioSpring {

	Long tiusId = 10L;

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@DisplayName("CrearTipoUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void aTest() {
		assertNotNull(entityManager);

		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNull(tipoUsuario, "El Tipo de Usuario ya Existe");

		tipoUsuario = new TipoUsuario(tiusId, "S", "Gerente Adminsitrativo");

		entityManager.persist(tipoUsuario);
	}

	@Test
	@DisplayName("ModificarTipoUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void bTest() {
		assertNotNull(entityManager);

		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsuario, "El Tipo de Usuario NO Existe");

		tipoUsuario.setNombre("Super Gerente Admitivo");
		tipoUsuario.setActivo("N");

		entityManager.merge(tipoUsuario);
	}

	@Test
	@DisplayName("BorrarTipoUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void cTest() {
		assertNotNull(entityManager);

		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, tiusId);
		assertNotNull(tipoUsuario, "El Tipo de Usuario No Existe");

		entityManager.remove(tipoUsuario);
	}

	private final static Logger log = LoggerFactory.getLogger(TestTipoUsuarioSpring.class);

	@Test
	@DisplayName("ConsultarTodosTiposUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void dTest() {
		assertNotNull(entityManager);

		String JPQL = "SELECT cli FROM TipoUsuario cli";
		List<TipoUsuario> losTiposUsuarios = entityManager.createQuery(JPQL).getResultList();

		losTiposUsuarios.forEach(tipoUsuario -> {
			log.info("Id: " + tipoUsuario.getTiusId() + " Nombre: " + tipoUsuario.getNombre());
		});

	}

}
