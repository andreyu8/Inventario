package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.model.DetalleMovimiento;


public class DetailTransactionitemAdapter {
	
	private DetalleMovimiento detalleMovimiento;


	public DetailTransactionitemAdapter(DetalleMovimiento detalleMovimiento) {
		super();
		this.detalleMovimiento = detalleMovimiento;
	}
	
	public DetalleMovimiento getDetalleMovimiento() {
		return detalleMovimiento;
	}

	public void setDetalleMovimiento(DetalleMovimiento detalleMovimiento) {
		this.detalleMovimiento = detalleMovimiento;
	}

	public static ArrayList<DetailTransactionitemAdapter> getArray (ArrayList<DetalleMovimiento> array) {
		ArrayList<DetailTransactionitemAdapter> result = new ArrayList<DetailTransactionitemAdapter>();
		Integer size =  array.size();
		for (int i = 0; i < size; i++) {
			DetalleMovimiento obj = array.get(i);
			result.add(new DetailTransactionitemAdapter(obj));
		}
		return result;
	}

}
