package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.OrdenCompra;

public class TransactionAdapter {

	private Factura factura = new Factura();
	private OrdenCompra ordenCompra = new OrdenCompra();
	private ArrayList<DetalleOrdenCompra> detalleOrdenCompra = new ArrayList<DetalleOrdenCompra>();
	
	private int idEntrada;
	private int idFacttura;
	private Movimientos movimientos = new Movimientos();
	private ArrayList<DetalleMovimiento> detalleMovimientos = new ArrayList<DetalleMovimiento>();

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public ArrayList<DetalleOrdenCompra> getDetalleOrdenCompra() {
		return detalleOrdenCompra;
	}

	public void setDetalleOrdenCompra(ArrayList<DetalleOrdenCompra> detalleOrdenCompra) {
		this.detalleOrdenCompra = detalleOrdenCompra;
	}

	public int getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public int getIdFacttura() {
		return idFacttura;
	}

	public void setIdFacttura(int idFacttura) {
		this.idFacttura = idFacttura;
	}

	public Movimientos getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Movimientos movimientos) {
		this.movimientos = movimientos;
	}

	public ArrayList<DetalleMovimiento> getDetalleMovimientos() {
		return detalleMovimientos;
	}

	public void setDetalleMovimientos(ArrayList<DetalleMovimiento> detalleMovimientos) {
		this.detalleMovimientos = detalleMovimientos;
	}
	

}
