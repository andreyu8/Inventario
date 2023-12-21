package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleOrdenCompra;


public class DetailOCitemAdapter {
	
	private DetalleOrdenCompra detalleOrdenCompra;


	public DetailOCitemAdapter(DetalleOrdenCompra detalleOrdenCompra) {
		super();
		this.detalleOrdenCompra = detalleOrdenCompra;
	}
	
	
	public DetalleOrdenCompra getDetalleOrdenCompra() {
		return detalleOrdenCompra;
	}


	public void setDetalleOrdenCompra(DetalleOrdenCompra detalleOrdenCompra) {
		this.detalleOrdenCompra = detalleOrdenCompra;
	}


	public static ArrayList<DetailOCitemAdapter> getArray (ArrayList<DetalleOrdenCompra> array) {
		ArrayList<DetailOCitemAdapter> result = new ArrayList<DetailOCitemAdapter>();
		Integer size =  array.size();
		for (int i = 0; i < size; i++) {
			DetalleOrdenCompra obj = array.get(i);
			result.add(new DetailOCitemAdapter(obj));
		}
		return result;
	}

}
