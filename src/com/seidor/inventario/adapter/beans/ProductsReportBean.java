package com.seidor.inventario.adapter.beans;

import java.util.ArrayList;

public class ProductsReportBean {

	
	private ArrayList<DetailProductsBeans> listProducts;
	private String jsonListProducts;

	public ArrayList<DetailProductsBeans> getListProducts() {
		return listProducts;
	}

	public void setListProducts(ArrayList<DetailProductsBeans> listProducts) {
		this.listProducts = listProducts;
	}

	public String getJsonListProducts() {
		return jsonListProducts;
	}

	public void setJsonListProducts(String jsonListProducts) {
		this.jsonListProducts = jsonListProducts;
	}
	

}
