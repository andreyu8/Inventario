package com.seidor.inventario.adapter.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.seidor.inventario.util.DateUtil;
import com.seidor.inventario.util.NumberFormatUtil;

public class ProveedoresBean {

	private String nombre;
	private String familia;
	private String codigo;
	private String producto;
	private String unidadMedida;
	private int cantidad;
	private BigDecimal precioUnitario;
	private Date fecha;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	//FORMATOS
	public String getPrecioUnitarioS() {
		return  NumberFormatUtil.format(precioUnitario, 2);
	}
		

}
