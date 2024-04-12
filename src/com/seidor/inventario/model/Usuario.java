package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario implements java.io.Serializable {

	private Integer idUsuario;
	private Empleado empleado;
	private String usuario;
	private String password;
	private Date fecha;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set perfilUsuarios = new HashSet(0);

	public Usuario() {
	}

	public Usuario(Empleado empleado, Date fecha, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.empleado = empleado;
		this.fecha = fecha;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Usuario(Empleado empleado, String usuario, String password, Date fecha, int activo, boolean fdl, int cbu,
			Date cat, int luu, Date uat, Set perfilUsuarios) {
		this.empleado = empleado;
		this.usuario = usuario;
		this.password = password;
		this.fecha = fecha;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
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

	public boolean isFdl() {
		return this.fdl;
	}

	public void setFdl(boolean fdl) {
		this.fdl = fdl;
	}

	public int getCbu() {
		return this.cbu;
	}

	public void setCbu(int cbu) {
		this.cbu = cbu;
	}

	public Date getCat() {
		return this.cat;
	}

	public void setCat(Date cat) {
		this.cat = cat;
	}

	public int getLuu() {
		return this.luu;
	}

	public void setLuu(int luu) {
		this.luu = luu;
	}

	public Date getUat() {
		return this.uat;
	}

	public void setUat(Date uat) {
		this.uat = uat;
	}

	public Set getPerfilUsuarios() {
		return this.perfilUsuarios;
	}

	public void setPerfilUsuarios(Set perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}

}
