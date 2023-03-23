package com.seidor.inventario.model;
// Generated 3 mar. 2023 11:27:00 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idUsuario;
	private Empleado empleado;
	private String usuario;
	private String password;
	private Date fecha;
	private int activo;
	private Set perfilUsuarios = new HashSet(0);

	public Usuario() {
	}

	public Usuario(Empleado empleado, Date fecha, int activo) {
		this.empleado = empleado;
		this.fecha = fecha;
		this.activo = activo;
	}

	public Usuario(Empleado empleado, String usuario, String password, Date fecha, int activo, Set perfilUsuarios) {
		this.empleado = empleado;
		this.usuario = usuario;
		this.password = password;
		this.fecha = fecha;
		this.activo = activo;
		this.perfilUsuarios = perfilUsuarios;
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public Set getPerfilUsuarios() {
		return this.perfilUsuarios;
	}

	public void setPerfilUsuarios(Set perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}

}
