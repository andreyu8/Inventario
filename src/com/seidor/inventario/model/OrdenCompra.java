package com.seidor.inventario.model;
// Generated 22 feb. 2023 19:54:18 by Hibernate Tools 4.3.5.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * OrdenCompra generated by hbm2java
 */
public class OrdenCompra implements java.io.Serializable {

	private Integer idOrdenCompra;
	private Area area;
	private Cliente cliente;
	private Empleado empleado;
	private Etapa etapa;
	private Factura factura;
	private Proyecto proyecto;
	private TipoOrdenCompra tipoOrdenCompra;
	private String descripcion;
	private Date fecha;
	private Date fechaRecepAlmacen;
	private Set entradas = new HashSet(0);

	public OrdenCompra() {
	}

	public OrdenCompra(Area area, Cliente cliente, Empleado empleado, Etapa etapa, TipoOrdenCompra tipoOrdenCompra) {
		this.area = area;
		this.cliente = cliente;
		this.empleado = empleado;
		this.etapa = etapa;
		this.tipoOrdenCompra = tipoOrdenCompra;
	}

	public OrdenCompra(Area area, Cliente cliente, Empleado empleado, Etapa etapa, Factura factura, Proyecto proyecto,
			TipoOrdenCompra tipoOrdenCompra, String descripcion, Date fecha, Date fechaRecepAlmacen, Set entradas) {
		this.area = area;
		this.cliente = cliente;
		this.empleado = empleado;
		this.etapa = etapa;
		this.factura = factura;
		this.proyecto = proyecto;
		this.tipoOrdenCompra = tipoOrdenCompra;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.fechaRecepAlmacen = fechaRecepAlmacen;
		this.entradas = entradas;
	}

	public Integer getIdOrdenCompra() {
		return this.idOrdenCompra;
	}

	public void setIdOrdenCompra(Integer idOrdenCompra) {
		this.idOrdenCompra = idOrdenCompra;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Etapa getEtapa() {
		return this.etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public TipoOrdenCompra getTipoOrdenCompra() {
		return this.tipoOrdenCompra;
	}

	public void setTipoOrdenCompra(TipoOrdenCompra tipoOrdenCompra) {
		this.tipoOrdenCompra = tipoOrdenCompra;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaRecepAlmacen() {
		return this.fechaRecepAlmacen;
	}

	public void setFechaRecepAlmacen(Date fechaRecepAlmacen) {
		this.fechaRecepAlmacen = fechaRecepAlmacen;
	}

	public Set getEntradas() {
		return this.entradas;
	}

	public void setEntradas(Set entradas) {
		this.entradas = entradas;
	}

}
