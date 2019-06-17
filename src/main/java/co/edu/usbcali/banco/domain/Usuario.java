package co.edu.usbcali.banco.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "usu_usuario")
	private String usuUsuario;

	@NotNull
	@Pattern(regexp="^[SN]{1}$", message="Se permite S o N May�scula")
	private String activo;

	@NotNull
	private String clave;

	@NotNull
	private BigDecimal identificacion;

	@NotNull
	@Size(min=5, max=50)
	private String nombre;

	// bi-directional many-to-one association to Transaccion
	@OneToMany(mappedBy = "usuario")
	private List<Transaccion> transaccions;

	// bi-directional many-to-one association to TipoUsuario
	@ManyToOne
	@JoinColumn(name = "tius_id")
	private TipoUsuario tipoUsuario;

	public Usuario() {
	}

	public Usuario(String usuUsuario, String activo, String clave, BigDecimal identificacion, String nombre,
			TipoUsuario tipoUsuario) {
		super();
		this.usuUsuario = usuUsuario;
		this.activo = activo;
		this.clave = clave;
		this.identificacion = identificacion;
		this.nombre = nombre;
		this.tipoUsuario = tipoUsuario;
	}

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

	public List<Transaccion> getTransaccions() {
		return this.transaccions;
	}

	public void setTransaccions(List<Transaccion> transaccions) {
		this.transaccions = transaccions;
	}

	public Transaccion addTransaccion(Transaccion transaccion) {
		getTransaccions().add(transaccion);
		transaccion.setUsuario(this);

		return transaccion;
	}

	public Transaccion removeTransaccion(Transaccion transaccion) {
		getTransaccions().remove(transaccion);
		transaccion.setUsuario(null);

		return transaccion;
	}

	public TipoUsuario getTipoUsuario() {
		return this.tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}