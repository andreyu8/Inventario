package com.seidor.inventario.adapter;

import com.seidor.inventario.model.Cliente;

public class ClientAdapter {
	
	private Cliente cliente;
	private Boolean falgActive;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Boolean getFalgActive() {
		return falgActive;
	}

	public void setFalgActive(Boolean falgActive) {
		this.falgActive = falgActive;
	}
}	
