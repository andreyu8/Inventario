package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.model.DatosBancarios;

public class DataBankListitemAdapter {
	
	private DatosBancarios datosBancarios;
	
	public DataBankListitemAdapter(DatosBancarios datosBancarios) {
		super();
		this.datosBancarios = datosBancarios;
		
	}
		
	public DatosBancarios getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancarios datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public static ArrayList<DataBankListitemAdapter> getArray(ArrayList<DatosBancarios> array){
		ArrayList<DataBankListitemAdapter> result = new ArrayList<DataBankListitemAdapter>();
		Integer size = array.size();
		for (int i = 0; i < size; i++){
			DatosBancarios obj = array.get(i);
			result.add(new DataBankListitemAdapter(obj));
		}
		return result;
	}

}
