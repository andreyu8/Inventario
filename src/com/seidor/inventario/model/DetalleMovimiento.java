package com.seidor.inventario.model;
// Generated 21 dic. 2023 9:34:58 by Hibernate Tools 4.3.5.Final

import java.math.BigDecimal;
import java.util.Date;

/**
 * DetalleMovimiento generated by hbm2java
 */
public class DetalleMovimiento implements java.io.Serializable {

	private Integer idDetalleMovimiento;
	private DetalleOrdenCompra detalleOrdenCompra;
	private Movimientos movimientos;
	private Producto producto;
	private Date fecha;
	private Double cantidadTotal;
	private double cantidad;
	private BigDecimal precioUnitario;
	private int estatus;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;

	public DetalleMovimiento() {
	}

	public DetalleMovimiento(Producto producto, Date fecha, double cantidad, int estatus, boolean fdl, int cbu,
			Date cat, int luu, Date uat) {
		this.producto = producto;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.estatus = estatus;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public DetalleMovimiento(DetalleOrdenCompra detalleOrdenCompra, Movimientos movimientos, Producto producto,
			Date fecha, Double cantidadTotal, double cantidad, BigDecimal precioUnitario, int estatus, boolean fdl,
			int cbu, Date cat, int luu, Date uat) {
		this.detalleOrdenCompra = detalleOrdenCompra;
		this.movimientos = movimientos;
		this.producto = producto;
		this.fecha = fecha;
		this.cantidadTotal = cantidadTotal;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.estatus = estatus;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Integer getIdDetalleMovimiento() {
		return this.idDetalleMovimiento;
	}

	public void setIdDetalleMovimiento(Integer idDetalleMovimiento) {
		this.idDetalleMovimiento = idDetalleMovimiento;
	}

	public DetalleOrdenCompra getDetalleOrdenCompra() {
		return this.detalleOrdenCompra;
	}

	public void setDetalleOrdenCompra(DetalleOrdenCompra detalleOrdenCompra) {
		this.detalleOrdenCompra = detalleOrdenCompra;
	}

	public Movimientos getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Movimientos movimientos) {
		this.movimientos = movimientos;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getCantidadTotal() {
		return this.cantidadTotal;
	}

	public void setCantidadTotal(Double cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public int getEstatus() {
		return this.estatus;
	}

	public void setEstatus(int estatus) {
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