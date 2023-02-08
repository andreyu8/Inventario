package com.seidor.inventario.model;
// Generated 25 ene. 2023 18:48:44 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Area generated by hbm2java
 */
public class Area implements java.io.Serializable {

	private Integer idArea;
	private String area;
	private String descripcion;
	private int activo;
	private Set ordenCompras = new HashSet(0);

	public Area() {
	}

	public Area(String area, String descripcion, int activo) {
		this.area = area;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public Area(String area, String descripcion, int activo, Set ordenCompras) {
		this.area = area;
		this.descripcion = descripcion;
		this.activo = activo;
		this.ordenCompras = ordenCompras;
	}

	public Integer getIdArea() {
		return this.idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
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
