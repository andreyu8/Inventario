package com.seidor.inventario.model;
// Generated 12 abr. 2024 12:10:56 by Hibernate Tools 4.3.5.Final

import java.util.Date;

/**
 * Salida generated by hbm2java
 */
public class Salida implements java.io.Serializable {

	private Integer idSalida;
	private Area area;
	private Empleado empleado;
	private Movimientos movimientos;
	private Producto producto;
	private Proyecto proyecto;
	private TipoTrabajo tipoTrabajo;
	private UnidadMedida unidadMedida;
	private String ordenTrabajo;
	private String modeloMueble;
	private Double cantidad;
	private Date fecha;
	private Integer estatus;
	private boolean fdl;
	private int cbu;
	private Date cat;
	private int luu;
	private Date uat;

	public Salida() {
	}

	public Salida(Producto producto, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.producto = producto;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Salida(Area area, Empleado empleado, Movimientos movimientos, Producto producto, Proyecto proyecto,
			TipoTrabajo tipoTrabajo, UnidadMedida unidadMedida, String ordenTrabajo, String modeloMueble,
			Double cantidad, Date fecha, Integer estatus, boolean fdl, int cbu, Date cat, int luu, Date uat) {
		this.area = area;
		this.empleado = empleado;
		this.movimientos = movimientos;
		this.producto = producto;
		this.proyecto = proyecto;
		this.tipoTrabajo = tipoTrabajo;
		this.unidadMedida = unidadMedida;
		this.ordenTrabajo = ordenTrabajo;
		this.modeloMueble = modeloMueble;
		this.cantidad = cantidad;
		this.fecha = fecha;
		this.estatus = estatus;
		this.fdl = fdl;
		this.cbu = cbu;
		this.cat = cat;
		this.luu = luu;
		this.uat = uat;
	}

	public Integer getIdSalida() {
		return this.idSalida;
	}

	public void setIdSalida(Integer idSalida) {
		this.idSalida = idSalida;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
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

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoTrabajo getTipoTrabajo() {
		return this.tipoTrabajo;
	}

	public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	public UnidadMedida getUnidadMedida() {
		return this.unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getOrdenTrabajo() {
		return this.ordenTrabajo;
	}

	public void setOrdenTrabajo(String ordenTrabajo) {
		this.ordenTrabajo = ordenTrabajo;
	}

	public String getModeloMueble() {
		return this.modeloMueble;
	}

	public void setModeloMueble(String modeloMueble) {
		this.modeloMueble = modeloMueble;
	}

	public Double getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
