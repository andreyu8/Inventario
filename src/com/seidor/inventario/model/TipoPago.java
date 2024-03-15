package com.seidor.inventario.model;
// Generated 4 mar. 2024 12:06:49 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TipoPago generated by hbm2java
 */
public class TipoPago implements java.io.Serializable {

	private Integer idTipoPago;
	private String tipoPago;
	private String descripcion;
	private Integer activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set proveedors = new HashSet(0);

	public TipoPago() {
	}

	public TipoPago(boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public TipoPago(String tipoPago, String descripcion, Integer activo, boolean fdl, int cbu, Date cat, int luu,
			Date uat, Set proveedors) {
		this.tipoPago = tipoPago;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.proveedors = proveedors;
	}

	public Integer getIdTipoPago() {
		return this.idTipoPago;
	}

	public void setIdTipoPago(Integer idTipoPago) {
		this.idTipoPago = idTipoPago;
	}

	public String getTipoPago() {
		return this.tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getActivo() {
		return this.activo;
	}

	public void setActivo(Integer activo) {
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

	public Set getProveedors() {
		return this.proveedors;
	}

	public void setProveedors(Set proveedors) {
		this.proveedors = proveedors;
	}

}
