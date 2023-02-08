package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.render.InvoiceComboitemRenderer;
import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.navigation.NavigationState;

public class InvoiceController {

	
	private InvoiceManager invoiceManager;
	
	public InvoiceManager getInvoiceManager() {
		return invoiceManager;
	}

	public void setInvoiceManager(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
	}


	public void search(Listbox lb, InvoiceSearchAdapter isa, NavigationState state){
		ArrayList<Factura> factura = this.invoiceManager.search(isa);
		
		ListModelList<Factura> model = new ListModelList<Factura>(factura);
		lb.setModel(model);
	}
	

	public void loadInvoice(Combobox combo) {
		ArrayList<Factura> invoices = this.invoiceManager.getAll();
		if (invoices != null) {
			ListModelList<Factura> model = new ListModelList<Factura>(invoices);
			combo.setItemRenderer(new InvoiceComboitemRenderer());
			combo.setModel(model);
		}
	}
	
}
