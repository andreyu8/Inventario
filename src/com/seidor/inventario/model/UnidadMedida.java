package com.seidor.inventario.model;
// Generated 21 jun. 2024 15:15:13 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UnidadMedida generated by hbm2java
 */
public class UnidadMedida implements java.io.Serializable {

	private Integer idUnidadMedida;
	private String unidadMedida;
	private String descripcion;
	private String abrev;
	private int activo;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;
	private Set entradas = new HashSet(0);
	private Set productos = new HashSet(0);
	private Set salidas = new HashSet(0);

	public UnidadMedida() {
	}

	public UnidadMedida(String unidadMedida, String descripcion, int activo, boolean fdl, int cbu, Date cat, int luu,
			Date uat) {
		this.unidadMedida = unidadMedida;
		this.descripcion = descripcion;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public UnidadMedida(String unidadMedida, String descripcion, String abrev, int activo, boolean fdl, int cbu,
			Date cat, int luu, Date uat, Set entradas, Set productos, Set salidas) {
		this.unidadMedida = unidadMedida;
		this.descripcion = descripcion;
		this.abrev = abrev;
		this.activo = activo;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
		this.entradas = entradas;
		this.productos = productos;
		this.salidas = salidas;
	}

	public Integer getIdUnidadMedida() {
		return this.idUnidadMedida;
	}

	public void setIdUnidadMedida(Integer idUnidadMedida) {
		this.idUnidadMedida = idUnidadMedida;
	}

	public String getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbrev() {
		return this.abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
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

	public Set getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Set entradas) {
		this.entradas = entradas;
	}

	public Set getProductos() {
		return this.productos;
	}

	public void setProductos(Set productos) {
		this.productos = productos;
	}

	public Set getSalidas() {
		return this.salidas;
	}

	public void setSalidas(Set salidas) {
		this.salidas = salidas;
	}

}
