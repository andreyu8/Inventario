package com.seidor.inventario.adapter.beans;

import com.seidor.inventario.model.Proyecto;

public class CierreBean {

	private Proyecto proyecto;
	
	private String familia;
	private int idProducto;
	private String codigo;
	private String producto;
	private String unidadMedida;
	private int cantidadEntrada;
	
	private String familia_s;
	private int idProducto_s;
	private String codigo_s;
	private String producto_s;
	private String unidadMedida_s;
	private int cantidadSalida;
	
	private int diferencia;
	private int devoluciones;

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

	public int getCantidadEntrada() {
		return cantidadEntrada;
	}

	public void setCantidadEntrada(int cantidadEntrada) {
		this.cantidadEntrada = cantidadEntrada;
	}

	public String getFamilia_s() {
		return familia_s;
	}

	public void setFamilia_s(String familia_s) {
		this.familia_s = familia_s;
	}

	public int getIdProducto_s() {
		return idProducto_s;
	}

	public void setIdProducto_s(int idProducto_s) {
		this.idProducto_s = idProducto_s;
	}

	public String getCodigo_s() {
		return codigo_s;
	}

	public void setCodigo_s(String codigo_s) {
		this.codigo_s = codigo_s;
	}

	public String getProducto_s() {
		return producto_s;
	}

	public void setProducto_s(String producto_s) {
		this.producto_s = producto_s;
	}

	public String getUnidadMedida_s() {
		return unidadMedida_s;
	}

	public void setUnidadMedida_s(String unidadMedida_s) {
		this.unidadMedida_s = unidadMedida_s;
	}

	public int getCantidadSalida() {
		return cantidadSalida;
	}

	public void setCantidadSalida(int cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public int getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(int diferencia) {
		this.diferencia = diferencia;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public int getDevoluciones() {
		return devoluciones;
	}

	public void setDevoluciones(int devoluciones) {
		this.devoluciones = devoluciones;
	}
	
		

}
