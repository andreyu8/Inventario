package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.model.Producto;

public class ProductListitemAdapter {
	
	private Producto product;
	private Double price;

	public ProductListitemAdapter(Producto product) {
		super();
		this.product = product;
		
	}
	
	public Producto getProduct() {
		return product;
	}

	public void setProduct(Producto product) {
		this.product = product;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
		
	public Double getPrice() {
		return price;
	}

	public static ArrayList<ProductListitemAdapter> getArray(ArrayList<Producto> array){
		ArrayList<ProductListitemAdapter> result = new ArrayList<ProductListitemAdapter>();
		Integer size = array.size();
		for (int i = 0; i < size; i++){
			Producto obj = array.get(i);
			result.add(new ProductListitemAdapter(obj));
		}
		return result;
	}

}
