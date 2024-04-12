package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Etapa generated by hbm2java
 */
public class Etapa implements java.io.Serializable {

	private Integer idEtapa;
	private String etapa;
	private String descripcion;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set ordenCompras = new HashSet(0);

	public Etapa() {
	}

	public Etapa(String etapa, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.etapa = etapa;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Etapa(String etapa, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat,
			Set ordenCompras) {
		this.etapa = etapa;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.ordenCompras = ordenCompras;
	}

	public Integer getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(Integer idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getEtapa() {
		return this.etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
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

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

}
