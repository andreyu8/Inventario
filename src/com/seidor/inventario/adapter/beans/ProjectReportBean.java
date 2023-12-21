package com.seidor.inventario.adapter.beans;

import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.model.Proyecto;

public class ProjectReportBean {

	private OrdenCompra ordenCompra;
	private Proveedor proveedor;
	private Cliente cliente;
	private Proyecto proyecto;
	private String jsonListProducts;
	private String jsonListCtas;
	private String subtotal;
	private String iva;
	private String total;
	

	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getJsonListProducts() {
		return jsonListProducts;
	}

	public void setJsonListProducts(String jsonListProducts) {
		this.jsonListProducts = jsonListProducts;
	}

	public String getJsonListCtas() {
		return jsonListCtas;
	}

	public void setJsonListCtas(String jsonListCtas) {
		this.jsonListCtas = jsonListCtas;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
	

}
