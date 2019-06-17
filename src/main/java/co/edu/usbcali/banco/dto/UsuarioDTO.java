package co.edu.usbcali.banco.dto;

import java.math.BigDecimal;

public class UsuarioDTO {
	
	private String usuUsuario;

	private String activo;

	private String clave;

	private BigDecimal identificacion;

	private String nombre;
	
	public String getUsuUsuario() {
		return this.usuUsuario;
	}

	public void setUsuUsuario(String usuUsuario) {
		this.usuUsuario = usuUsuario;
	}

	public String getActivo() {
		return this.activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public BigDecimal getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(BigDecimal identificacion) {
		this.identificacion = identificacion;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
