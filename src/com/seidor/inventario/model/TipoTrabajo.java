package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TipoTrabajo generated by hbm2java
 */
public class TipoTrabajo implements java.io.Serializable {

	private Integer idTipoTrabajo;
	private String tipoTrabajo;
	private String descripcion;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set salidas = new HashSet(0);

	public TipoTrabajo() {
	}

	public TipoTrabajo(String tipoTrabajo, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.tipoTrabajo = tipoTrabajo;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public TipoTrabajo(String tipoTrabajo, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu,
			Date uat, Set salidas) {
		this.tipoTrabajo = tipoTrabajo;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.salidas = salidas;
	}

	public Integer getIdTipoTrabajo() {
		return this.idTipoTrabajo;
	}

	public void setIdTipoTrabajo(Integer idTipoTrabajo) {
		this.idTipoTrabajo = idTipoTrabajo;
	}

	public String getTipoTrabajo() {
		return this.tipoTrabajo;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
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

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

}
