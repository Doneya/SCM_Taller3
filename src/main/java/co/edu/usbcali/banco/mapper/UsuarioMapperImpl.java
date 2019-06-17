package co.edu.usbcali.banco.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import co.edu.usbcali.banco.domain.Usuario;
import co.edu.usbcali.banco.dto.UsuarioDTO;

@Component
@Scope("singleton")
public class UsuarioMapperImpl implements UsuarioMapper {
	 private static final Logger log = LoggerFactory.getLogger(TipoUsuarioMapperImpl.class);
	 
	@Transactional(readOnly = true)
	public UsuarioDTO usuarioToUsuarioDTO(Usuario usuario) throws Exception {
		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();

            usuarioDTO.setIdentificacion(usuario.getIdentificacion());
            usuarioDTO.setActivo((usuario.getActivo() != null)
                ? usuario.getActivo() : null);
            usuarioDTO.setNombre((usuario.getNombre() != null)
                ? usuario.getNombre() : null);
            usuarioDTO.setUsuUsuario(usuario.getUsuUsuario());

            return usuarioDTO;
        } catch (Exception e) {
            throw e;
        }
	}

	@Transactional(readOnly = true)
	public Usuario usuarioDTOToUsuario(UsuarioDTO usuarioDTO) throws Exception {
		  try {
	            Usuario usuario = new Usuario();

	            usuario.setIdentificacion(usuarioDTO.getIdentificacion());
	            usuario.setActivo((usuarioDTO.getActivo() != null)
	                ? usuarioDTO.getActivo() : null);
	            usuario.setNombre((usuarioDTO.getNombre() != null)
	                ? usuarioDTO.getNombre() : null);
	            usuario.setClave(usuarioDTO.getClave());
	            usuario.setUsuUsuario(usuarioDTO.getUsuUsuario());
	            return usuario;
	        } catch (Exception e) {
	            throw e;
	        }
	}

	@Transactional(readOnly = true)
	public List<UsuarioDTO> listUsuarioToListTipoUsuarioDTO(List<Usuario> listUsuario) throws Exception {
		try {
            List<UsuarioDTO> usuarioDTOs = new ArrayList<UsuarioDTO>();

            for (Usuario usuario : listUsuario) {
                UsuarioDTO usuarioDTO = usuarioToUsuarioDTO(usuario);

                usuarioDTOs.add(usuarioDTO);
            }

            return usuarioDTOs;
        } catch (Exception e) {
            throw e;
        }
	}

	@Transactional(readOnly = true)
	public List<Usuario> listUsuarioDTOToListTipoUsuario(List<UsuarioDTO> listUsuarioDTO) throws Exception {
		try {
            List<Usuario> listUsuario = new ArrayList<Usuario>();

            for (UsuarioDTO usuarioDTO : listUsuarioDTO) {
                Usuario usuario = usuarioDTOToUsuario(usuarioDTO);

                listUsuario.add(usuario);
            }

            return listUsuario;
        } catch (Exception e) {
            throw e;
        }
	}

}
