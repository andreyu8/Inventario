package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.WerehouseComboitemRenderer;
import com.seidor.inventario.manager.WarehouseManager;
import com.seidor.inventario.model.Almacen;

public class WarehouseController {
	
	
	private WarehouseManager warehouseManager;
	
	public WarehouseManager getWarehouseManager() {
		return warehouseManager;
	}

	public void setWarehouseManager(WarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}


	public void loadAlmacen(Combobox combo) {
		ArrayList<Almacen> almacenes = this.warehouseManager.getAll();
		if (almacenes != null) {
			ListModelList<Almacen> model = new ListModelList<Almacen>(almacenes);
			combo.setItemRenderer(new WerehouseComboitemRenderer());
			combo.setModel(model);
		}
	}

}
