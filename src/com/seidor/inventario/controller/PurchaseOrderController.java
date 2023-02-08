package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.render.PurchaseOrderComboitemRenderer;
import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.navigation.NavigationState;

public class PurchaseOrderController {

	private PurchaseOrderManager purchaseOrderManager;
	
	public PurchaseOrderManager getPurchaseOrderManager() {
		return purchaseOrderManager;
	}

	
	public void readForNew () {
		
	}
	
	public void setPurchaseOrderManager(PurchaseOrderManager purchaseOrderManager) {
		this.purchaseOrderManager = purchaseOrderManager;
	}


	public void search(Listbox lb, PurchaseOrderSearchAdapter psa, NavigationState state){
		ArrayList<OrdenCompra> provider = this.purchaseOrderManager.search(psa);
		
		ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(provider);
		lb.setModel(model);
	}
	

	public void loadPurchaseOrder(Combobox combo) {
		ArrayList<OrdenCompra> purchaseOrders = this.purchaseOrderManager.getAll();
		if (purchaseOrders != null) {
			ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(purchaseOrders);
			combo.setItemRenderer(new PurchaseOrderComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
}
