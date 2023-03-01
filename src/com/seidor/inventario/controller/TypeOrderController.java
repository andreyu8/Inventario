package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.TypeOrderComboitemRenderer;
import com.seidor.inventario.manager.TypeOrderManager;
import com.seidor.inventario.model.TipoOrdenCompra;
import com.seidor.inventario.navigation.NavigationControl;

public class TypeOrderController {

	@Autowired
	private TypeOrderManager typeOrderManager;
	
	@Autowired
	private NavigationControl navigationControl;
	

	public TypeOrderManager getTypeOrderManager() {
		return typeOrderManager;
	}

	public void setTypeOrderManager(TypeOrderManager typeOrderManager) {
		this.typeOrderManager = typeOrderManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	//read the provider
	public void loadTOC(Combobox combo) {
		ArrayList<TipoOrdenCompra> toc = this.typeOrderManager.getAll();
		if (toc != null) {
			ListModelList<TipoOrdenCompra> model = new ListModelList<TipoOrdenCompra>(toc);
			combo.setItemRenderer(new TypeOrderComboitemRenderer());
			combo.setModel(model);
		}
	}
	
}
