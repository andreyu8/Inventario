package com.seidor.inventario.adapter.search;

public class TransactionSearchAdapter {

	private String name;
	private Integer idFactura;
	private String numeroFactura;
	private String numeroFolio;
	private Integer numeroOC;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroFolio() {
		return numeroFolio;
	}

	public void setNumeroFolio(String numeroFolio) {
		this.numeroFolio = numeroFolio;
	}

	public Integer getNumeroOC() {
		return numeroOC;
	}

	public void setNumeroOC(Integer numeroOC) {
		this.numeroOC = numeroOC;
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}
	
}
