package com.seidor.inventario.model;
// Generated 25 ene. 2023 18:48:44 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Proyecto generated by hbm2java
 */
public class Proyecto implements java.io.Serializable {

	private Integer idProyecto;
	private EstatusProyecto estatusProyecto;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFinal;
	private Set salidas = new HashSet(0);
	private Set entradas = new HashSet(0);
	private Set movimientosStocks = new HashSet(0);
	private Set ordenCompras = new HashSet(0);

	public Proyecto() {
	}

	public Proyecto(EstatusProyecto estatusProyecto, String nombre) {
		this.estatusProyecto = estatusProyecto;
		this.nombre = nombre;
	}

	public Proyecto(EstatusProyecto estatusProyecto, String nombre, Date fechaInicio, Date fechaFinal, Set salidas,
			Set entradas, Set movimientosStocks, Set ordenCompras) {
		this.estatusProyecto = estatusProyecto;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
		this.salidas = salidas;
		this.entradas = entradas;
		this.movimientosStocks = movimientosStocks;
		this.ordenCompras = ordenCompras;
	}

	public Integer getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public EstatusProyecto getEstatusProyecto() {
		return this.estatusProyecto;
	}

	public void setEstatusProyecto(EstatusProyecto estatusProyecto) {
		this.estatusProyecto = estatusProyecto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

	public Set getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Set entradas) {
		this.entradas = entradas;
	}

	public Set getMovimientosStocks() {
		return this.movimientosStocks;
	}

	public void setMovimientosStocks(Set movimientosStocks) {
		this.movimientosStocks = movimientosStocks;
	}

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

}
