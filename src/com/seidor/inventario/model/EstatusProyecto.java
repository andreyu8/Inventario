package com.seidor.inventario.model;
// Generated 22 feb. 2023 19:54:18 by Hibernate Tools 4.3.5.Final

import java.util.HashSet;
import java.util.Set;

/**
 * EstatusProyecto generated by hbm2java
 */
public class EstatusProyecto implements java.io.Serializable {

	private Integer idEstatusProyecto;
	private String descripcion;
	private Set proyectos = new HashSet(0);

	public EstatusProyecto() {
	}

	public EstatusProyecto(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstatusProyecto(String descripcion, Set proyectos) {
		this.descripcion = descripcion;
		this.proyectos = proyectos;
	}

	public Integer getIdEstatusProyecto() {
		return this.idEstatusProyecto;
	}

	public void setIdEstatusProyecto(Integer idEstatusProyecto) {
		this.idEstatusProyecto = idEstatusProyecto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set proyectos) {
		this.proyectos = proyectos;
	}

}
