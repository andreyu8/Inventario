package com.seidor.inventario.adapter.beans;

import java.math.BigDecimal;

import com.seidor.inventario.util.NumberFormatUtil;

public class ReportCostoInventarioGBean {

	private String familia;
	private BigDecimal costo_total;

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public BigDecimal getCosto_total() {
		return costo_total;
	}

	public void setCosto_total(BigDecimal costo_total) {
		this.costo_total = costo_total;
	}
	
	public String getCosto_total_f() {
		return NumberFormatUtil.format(costo_total,2);
	}

}
