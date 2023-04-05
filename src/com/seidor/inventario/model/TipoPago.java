package com.seidor.inventario.model;
// Generated 29 mar. 2023 21:04:16 by Hibernate Tools 4.3.5.Final

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
	private Set ordenCompras = new HashSet(0);

	public TipoPago() {
	}

	public TipoPago(String tipoPago, String descripcion, Integer activo, Set ordenCompras) {
		this.tipoPago = tipoPago;
		this.descripcion = descripcion;
		this.activo = activo;
		this.ordenCompras = ordenCompras;
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

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

}
