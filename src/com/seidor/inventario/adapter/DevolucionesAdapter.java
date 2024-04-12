package com.seidor.inventario.adapter;

import java.util.ArrayList;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;

public class DevolucionesAdapter {

	private int idDevolucion;
	private Proyecto proyecto;
	private Movimientos movimiento = new Movimientos();
	private ArrayList<DetalleMovimiento> detalleMovimientos = new ArrayList<DetalleMovimiento>();

	private Producto producto;
	private ArrayList<Producto> productos;

	public int getIdDevolucion() {
		return idDevolucion;
	}

	public void setIdDevolucion(int idDevolucion) {
		this.idDevolucion = idDevolucion;
	}
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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

}
