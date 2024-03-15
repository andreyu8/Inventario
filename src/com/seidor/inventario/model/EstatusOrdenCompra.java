package com.seidor.inventario.model;
// Generated 4 mar. 2024 12:06:49 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * EstatusOrdenCompra generated by hbm2java
 */
public class EstatusOrdenCompra implements java.io.Serializable {

	private Integer idEstatusOrdenCompra;
	private String estatusOrdenCompra;
	private String descripcion;
	private Integer activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set ordenCompras = new HashSet(0);

	public EstatusOrdenCompra() {
	}

	public EstatusOrdenCompra(boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public EstatusOrdenCompra(String estatusOrdenCompra, String descripcion, Integer activo, boolean fdl, int cbu,
			Date cat, int luu, Date uat, Set ordenCompras) {
		this.estatusOrdenCompra = estatusOrdenCompra;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.ordenCompras = ordenCompras;
	}

	public Integer getIdEstatusOrdenCompra() {
		return this.idEstatusOrdenCompra;
	}

	public void setIdEstatusOrdenCompra(Integer idEstatusOrdenCompra) {
		this.idEstatusOrdenCompra = idEstatusOrdenCompra;
	}

	public String getEstatusOrdenCompra() {
		return this.estatusOrdenCompra;
	}

	public void setEstatusOrdenCompra(String estatusOrdenCompra) {
		this.estatusOrdenCompra = estatusOrdenCompra;
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

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

}
