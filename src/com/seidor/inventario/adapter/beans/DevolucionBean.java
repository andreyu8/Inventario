package com.seidor.inventario.adapter.beans;

import com.seidor.inventario.model.Proyecto;

public class DevolucionBean {

	private Proyecto proyecto;

	private String familia;
	private int idProducto;
	private String codigo;
	private String producto;
	private String unidadMedida;
	private double cantidadEntrada;
	private double cantidadSalida;

	private double diferencia;
	private double devoluciones;

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public double getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(double cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}
	
	public double getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(double cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public double getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(double diferencia) {
		this.diferencia = diferencia;
	}

	public double getDevoluciones() {
		return devoluciones;
	}

	public void setDevoluciones(double devoluciones) {
		this.devoluciones = devoluciones;
	}

}
