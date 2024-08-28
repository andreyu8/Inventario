package com.seidor.inventario.model;
// Generated 21 jun. 2024 15:15:13 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Perfil generated by hbm2java
 */
public class Perfil implements java.io.Serializable {

	private Integer idPerfil;
	private String nombre;
	private String descripcion;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set perfilUsuarios = new HashSet(0);

	public Perfil() {
	}

	public Perfil(String nombre, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Perfil(String nombre, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat,
			Set perfilUsuarios) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.perfilUsuarios = perfilUsuarios;
	}

	public Integer getIdPerfil() {
		return this.idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
