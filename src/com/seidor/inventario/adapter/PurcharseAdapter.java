package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Proveedor;

public class PurcharseAdapter {

	private OrdenCompra orderCompra;
	private Proveedor proveedor;
	private ArrayList<DetalleOrdenCompra> detailtOC;

	public OrdenCompra getOrderCompra() {
		return orderCompra;
	}

	public void setOrderCompra(OrdenCompra orderCompra) {
		this.orderCompra = orderCompra;
	}
	
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public ArrayList<DetalleOrdenCompra> getDetailtOC() {
		return detailtOC;
	}

	public void setDetailtOC(ArrayList<DetalleOrdenCompra> detailtOC) {
		this.detailtOC = detailtOC;
	}
	
	
		
}
