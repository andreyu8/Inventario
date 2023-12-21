package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.Proveedor;

public class ProviderAdapter {

	private Proveedor proveedor;
	private Boolean falgActive;
	private ArrayList<DatosBancarios> datosBancarios;

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Boolean getFalgActive() {
		return falgActive;
	}

	public void setFalgActive(Boolean falgActive) {
		this.falgActive = falgActive;
	}

	public ArrayList<DatosBancarios> getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(ArrayList<DatosBancarios> datosBancarios) {
		this.datosBancarios = datosBancarios;
	}
	
	
}
