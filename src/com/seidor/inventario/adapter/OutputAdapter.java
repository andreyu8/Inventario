package com.seidor.inventario.adapter;

import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;

public class OutputAdapter {
	
	private Salida salida;
	private Producto producto;
	private Entrada entrada;
	
	public Salida getSalida() {
		return salida;
	}
	
	public void setSalida(Salida salida) {
		this.salida = salida;
	}
	
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
