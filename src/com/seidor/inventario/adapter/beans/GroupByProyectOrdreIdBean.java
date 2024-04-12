package com.seidor.inventario.adapter.beans;

public class GroupByProyectOrdreIdBean {

	private int ordenId;
	private int almacenId;
	private int productoId;
	private int countProduct;

	public int getOrdenId() {
		return ordenId;
	}

	public void setOrdenId(int ordenId) {
		this.ordenId = ordenId;
	}

	public int getAlmacenId() {
		return almacenId;
	}

	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public int getCountProduct() {
		return countProduct;
	}

	public void setCountProduct(int countProduct) {
		this.countProduct = countProduct;
	}

}
