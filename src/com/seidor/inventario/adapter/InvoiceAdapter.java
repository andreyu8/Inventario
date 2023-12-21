package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.OrdenCompra;

public class InvoiceAdapter {
	
	private Factura factura;
	private OrdenCompra ordenCompra;
	private ArrayList<DetalleOrdenCompra> doc;
	
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public ArrayList<DetalleOrdenCompra> getDoc() {
		return doc;
	}

	public void setDoc(ArrayList<DetalleOrdenCompra> doc) {
		this.doc = doc;
	}
	

}
