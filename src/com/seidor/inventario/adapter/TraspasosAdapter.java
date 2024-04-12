package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.Producto;

public class TraspasosAdapter {

	private int idTraspaso;
	private int almacenOrigen;
	private int almacenDestino;
	
	private String desAlmacenOrigen;
	private String desAlmacenDestino;
	
	private Movimientos movimiento = new Movimientos();
	private ArrayList<DetalleMovimiento> detalleMovimientos = new ArrayList<DetalleMovimiento>();

	private Producto producto;
	private ArrayList<Producto> productos;

	public int getIdTraspaso() {
		return idTraspaso;
	}

	public void setIdTraspaso(int idTraspaso) {
		this.idTraspaso = idTraspaso;
	}

	public Movimientos getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimientos movimiento) {
		this.movimiento = movimiento;
	}

	public ArrayList<DetalleMovimiento> getDetalleMovimientos() {
		return detalleMovimientos;
	}

	public void setDetalleMovimientos(ArrayList<DetalleMovimiento> detalleMovimientos) {
		this.detalleMovimientos = detalleMovimientos;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public int getAlmacenOrigen() {
		return almacenOrigen;
	}

	public void setAlmacenOrigen(int almacenOrigen) {
		this.almacenOrigen = almacenOrigen;
	}

	public int getAlmacenDestino() {
		return almacenDestino;
	}

	public void setAlmacenDestino(int almacenDestino) {
		this.almacenDestino = almacenDestino;
	}

	public String getDesAlmacenOrigen() {
		return desAlmacenOrigen;
	}

	public void setDesAlmacenOrigen(String desAlmacenOrigen) {
		this.desAlmacenOrigen = desAlmacenOrigen;
	}

	public String getDesAlmacenDestino() {
		return desAlmacenDestino;
	}

	public void setDesAlmacenDestino(String desAlmacenDestino) {
		this.desAlmacenDestino = desAlmacenDestino;
	}
	
	
}
