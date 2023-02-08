package com.seidor.inventario.adapter.beans;

import java.math.BigDecimal;

import com.seidor.inventario.util.NumberFormatUtil;

public class ReportCostoInventario {

	private String familia;
	private int noArticulos;
	private int inventario_final;
	private int totalEntrada;
	private int totalSalida;
	private BigDecimal costo_total;
	private BigDecimal iva;
	private BigDecimal total;

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public int getNoArticulos() {
		return noArticulos;
	}

	public void setNoArticulos(int noArticulos) {
		this.noArticulos = noArticulos;
	}

	public int getInventario_final() {
		return inventario_final;
	}

	public void setInventario_final(int inventario_final) {
		this.inventario_final = inventario_final;
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

	public int getTotalEntrada() {
		return totalEntrada;
	}

	public void setTotalEntrada(int totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public int getTotalSalida() {
		return totalSalida;
	}

	public void setTotalSalida(int totalSalida) {
		this.totalSalida = totalSalida;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
