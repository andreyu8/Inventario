package com.seidor.inventario.model;
// Generated 25 ene. 2023 18:48:44 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Ubicacion generated by hbm2java
 */
public class Ubicacion implements java.io.Serializable {

	private Integer idUbicacion;
	private String ubicacion;
	private String descripcion;
	private int activo;
	private Set entradas = new HashSet(0);

	public Ubicacion() {
	}

	public Ubicacion(String ubicacion, String descripcion, int activo) {
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public Ubicacion(String ubicacion, String descripcion, int activo, Set entradas) {
		this.ubicacion = ubicacion;
		this.descripcion = descripcion;
		this.activo = activo;
		this.entradas = entradas;
	}

	public Integer getIdUbicacion() {
		return this.idUbicacion;
	}

	public void setIdUbicacion(Integer idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
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

	public Set getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Set entradas) {
		this.entradas = entradas;
	}

}
