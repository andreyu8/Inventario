package com.seidor.inventario.model;
// Generated 21 jun. 2024 15:15:13 by Hibernate Tools 4.3.5.Final

import java.util.Date;

/**
 * PerfilUsuario generated by hbm2java
 */
public class PerfilUsuario implements java.io.Serializable {

	private Integer idPerfilUsuario;
	private Perfil perfil;
	private Usuario usuario;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;

	public PerfilUsuario() {
	}

	public PerfilUsuario(Perfil perfil, Usuario usuario, int activo, boolean fdl, int cbu, Date cat, int luu,
			Date uat) {
		this.perfil = perfil;
		this.usuario = usuario;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Integer getIdPerfilUsuario() {
		return this.idPerfilUsuario;
	}

	public void setIdPerfilUsuario(Integer idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

	public Perfil getPerfil() {
		return this.perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

}
