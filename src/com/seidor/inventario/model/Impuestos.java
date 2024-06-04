package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * Impuestos generated by hbm2java
 */
public class Impuestos implements java.io.Serializable {

	private Integer idImpuesto;
	private String impuesto;
	private String descripcion;
	private BigDecimal porcentaje;
	private Boolean fdl;
	private Integer cbu;
	private Date cat;
	private Integer luu;
	private Date uat;

	public Impuestos() {
	}

	public Impuestos(String impuesto, String descripcion, BigDecimal porcentaje, Boolean fdl, Integer cbu, Date cat,
			Integer luu, Date uat) {
		this.impuesto = impuesto;
		this.descripcion = descripcion;
		this.porcentaje = porcentaje;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Integer getIdImpuesto() {
		return this.idImpuesto;
	}

	public void setIdImpuesto(Integer idImpuesto) {
		this.idImpuesto = idImpuesto;
	}

	public String getImpuesto() {
		return this.impuesto;
	}

	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Boolean getFdl() {
		return this.fdl;
	}

	public void setFdl(Boolean fdl) {
		this.fdl = fdl;
	}

	public Integer getCbu() {
		return this.cbu;
	}

	public void setCbu(Integer cbu) {
		this.cbu = cbu;
	}

	public Date getCat() {
		return this.cat;
	}

	public void setCat(Date cat) {
		this.cat = cat;
	}

	public Integer getLuu() {
		return this.luu;
	}

	public void setLuu(Integer luu) {
		this.luu = luu;
	}

	public Date getUat() {
		return this.uat;
	}

	public void setUat(Date uat) {
		this.uat = uat;
	}

}