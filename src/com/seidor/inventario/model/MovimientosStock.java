package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;

/**
 * MovimientosStock generated by hbm2java
 */
public class MovimientosStock implements java.io.Serializable {

	private Integer idMovimientoStock;
	private Producto producto;
	private Proyecto proyecto;
	private Date fecha;
	private Integer tipo;
	private Double cantidad;
	private Integer estatus;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;

	public MovimientosStock() {
	}

	public MovimientosStock(boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public MovimientosStock(Producto producto, Proyecto proyecto, Date fecha, Integer tipo, Double cantidad,
			Integer estatus, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.producto = producto;
		this.proyecto = proyecto;
		this.fecha = fecha;
		this.tipo = tipo;
		this.cantidad = cantidad;
		this.estatus = estatus;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Integer getIdMovimientoStock() {
		return this.idMovimientoStock;
	}

	public void setIdMovimientoStock(Integer idMovimientoStock) {
		this.idMovimientoStock = idMovimientoStock;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
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

}
