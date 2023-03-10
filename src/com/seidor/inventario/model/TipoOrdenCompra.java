package com.seidor.inventario.model;
// Generated 22 feb. 2023 19:54:18 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * TipoOrdenCompra generated by hbm2java
 */
public class TipoOrdenCompra implements java.io.Serializable {

	private Integer idTipoOrdenCompra;
	private String ordenCompra;
	private String descripcion;
	private int activo;
	private Set ordenCompras = new HashSet(0);

	public TipoOrdenCompra() {
	}

	public TipoOrdenCompra(String ordenCompra, int activo) {
		this.ordenCompra = ordenCompra;
		this.activo = activo;
	}

	public TipoOrdenCompra(String ordenCompra, String descripcion, int activo, Set ordenCompras) {
		this.ordenCompra = ordenCompra;
		this.descripcion = descripcion;
		this.activo = activo;
		this.ordenCompras = ordenCompras;
	}

	public Integer getIdTipoOrdenCompra() {
		return this.idTipoOrdenCompra;
	}

	public void setIdTipoOrdenCompra(Integer idTipoOrdenCompra) {
		this.idTipoOrdenCompra = idTipoOrdenCompra;
	}

	public String getOrdenCompra() {
		return this.ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
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

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

}
