package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.render.ProveedorComboitemRenderer;
import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.manager.ProviderManager;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.navigation.NavigationState;

public class ProviderController {

	
	private ProviderManager providerManager;
	

	public ProviderManager getProviderManager() {
		return providerManager;
	}

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}


	//read the provider
	public void loadProvider(Combobox combo) {
		ArrayList<Proveedor> provider = this.providerManager.getAll();
		if (provider != null) {
			ListModelList<Proveedor> model = new ListModelList<Proveedor>(provider);
			combo.setItemRenderer(new ProveedorComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public void search(Listbox lb, ProviderSearchAdapter psa, NavigationState state){
		ArrayList<Proveedor> provider = this.providerManager.search(psa);
		
		ListModelList<Proveedor> model = new ListModelList<Proveedor>(provider);
		lb.setModel(model);
	}
	

	public void loadInvoice(Combobox combo) {
		ArrayList<Proveedor> proveedores = this.providerManager.getAll();
		if (proveedores != null) {
			ListModelList<Proveedor> model = new ListModelList<Proveedor>(proveedores);
			combo.setItemRenderer(new ProveedorComboitemRenderer());
			combo.setModel(model);
		}
	}
	
}
