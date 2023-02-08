package com.seidor.inventario.adapter;

import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.Producto;

public class StockAdapter {

	private Entrada entrada;
	private MovimientosStock movimientosStock;
	private Producto producto;
	
	

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}

	public MovimientosStock getMovimientosStock() {
		return movimientosStock;
	}

	public void setMovimientosStock(MovimientosStock movimientosStock) {
		this.movimientosStock = movimientosStock;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
