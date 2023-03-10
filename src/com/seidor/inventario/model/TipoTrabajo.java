package com.seidor.inventario.model;
// Generated 22 feb. 2023 19:54:18 by Hibernate Tools 4.3.5.Final

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
	private Set salidas = new HashSet(0);

	public TipoTrabajo() {
	}

	public TipoTrabajo(String tipoTrabajo, int activo) {
		this.tipoTrabajo = tipoTrabajo;
		this.activo = activo;
	}

	public TipoTrabajo(String tipoTrabajo, String descripcion, int activo, Set salidas) {
		this.tipoTrabajo = tipoTrabajo;
		this.descripcion = descripcion;
		this.activo = activo;
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

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

}
