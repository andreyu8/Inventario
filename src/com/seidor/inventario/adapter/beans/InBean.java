package com.seidor.inventario.adapter.beans;

import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Producto;

public class InBean {

	private Producto producto;
	private Entrada entrada;
	
	public Producto getProducto() {
		return producto;
	}
	
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public Entrada getEntrada() {
		return entrada;
	}
	
	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	
	
}
