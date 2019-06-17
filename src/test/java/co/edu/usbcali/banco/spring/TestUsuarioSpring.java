package co.edu.usbcali.banco.spring;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
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
import co.edu.usbcali.banco.domain.TipoUsuario;
import co.edu.usbcali.banco.domain.Usuario;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)
class TestUsuarioSpring {

	String usuUsuario = "hgoogle";
	BigDecimal identificacion = new BigDecimal(123456789);

	@PersistenceContext
	EntityManager entityManager;

	@Test
	@DisplayName("CrearUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void aTest() {

		assertNotNull(entityManager);

		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNull(usuario, "El Usuario ya Existe");

		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 1L);
		assertNotNull(tipoUsuario, "El tipo Usuario NO existe");

		usuario = new Usuario(usuUsuario, "S", "abcd1234", identificacion, "Homero Jota Simpson", tipoUsuario);

		entityManager.persist(usuario);
	}

	@Test
	@DisplayName("ModificarUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void bTest() {

		assertNotNull(entityManager);

		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El Usuario NO existe");

		TipoUsuario tipoUsuario = entityManager.find(TipoUsuario.class, 2L);
		assertNotNull(tipoUsuario, "El Usuario NO existe");

		usuario.setActivo("N");
		usuario.setClave("1234abcd");
		usuario.setIdentificacion(identificacion);
		usuario.setNombre("Homero Jota Restrepo Velarde");
		usuario.setTipoUsuario(tipoUsuario);

		entityManager.merge(usuario);
	}

	@Test
	@DisplayName("BorrarUsuario")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void cTest() {

		assertNotNull(entityManager);

		Usuario usuario = entityManager.find(Usuario.class, usuUsuario);
		assertNotNull(usuario, "El Usuario NO existe");

		entityManager.remove(usuario);
	}

	private final static Logger log = LoggerFactory.getLogger(TestUsuarioSpring.class);

	@Test
	@DisplayName("ConsultarTodos")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void dTest() {
		assertNotNull(entityManager);

		String JPQL = "SELECT cli FROM Usuario cli";
		List<Usuario> losUsuarios = entityManager.createQuery(JPQL).getResultList();

		losUsuarios.forEach(usuario -> {
			log.info("Id: " + usuario.getUsuUsuario() + " Noombre: " + usuario.getNombre());
		});
	}

}
