package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;
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
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set movimientoses = new HashSet(0);
	private Set ordenCompras = new HashSet(0);
	private Set salidas = new HashSet(0);
	private Set empleados = new HashSet(0);

	public Area() {
	}

	public Area(String area, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.area = area;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Area(String area, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu, Date uat,
			Set movimientoses, Set ordenCompras, Set salidas, Set empleados) {
		this.area = area;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.movimientoses = movimientoses;
		this.ordenCompras = ordenCompras;
		this.salidas = salidas;
		this.empleados = empleados;
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

	public Set getMovimientoses() {
		return this.movimientoses;
	}

	public void setMovimientoses(Set movimientoses) {
		this.movimientoses = movimientoses;
	}

	public Set getOrdenCompras() {
		return this.ordenCompras;
	}

	public void setOrdenCompras(Set ordenCompras) {
		this.ordenCompras = ordenCompras;
	}

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

	public Set getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(Set empleados) {
		this.empleados = empleados;
	}

}
