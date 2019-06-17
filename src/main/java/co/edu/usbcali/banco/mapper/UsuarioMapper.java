package co.edu.usbcali.banco.mapper;

import java.util.List;

import co.edu.usbcali.banco.domain.Usuario;
import co.edu.usbcali.banco.dto.UsuarioDTO;

public interface UsuarioMapper {
	public UsuarioDTO usuarioToUsuarioDTO(
	        Usuario usuario) throws Exception;

	    public Usuario usuarioDTOToUsuario(
	    		UsuarioDTO usuarioDTO) throws Exception;

	    public List<UsuarioDTO> listUsuarioToListTipoUsuarioDTO(
	        List<Usuario> listUsuario) throws Exception;

	    public List<Usuario> listUsuarioDTOToListTipoUsuario(
	        List<UsuarioDTO> listUsuarioDTO) throws Exception;
}
