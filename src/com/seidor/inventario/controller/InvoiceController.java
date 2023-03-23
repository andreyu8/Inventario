package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.ClientAdapter;
import com.seidor.inventario.adapter.InvoiceAdapter;
import com.seidor.inventario.adapter.render.InvoiceComboitemRenderer;
import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;

public class InvoiceController {

	
	private InvoiceManager invoiceManager;
	private NavigationControl navigationControl;
	
	public InvoiceManager getInvoiceManager() {
		return invoiceManager;
	}

	public void setInvoiceManager(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	
	//bussines logic
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
	
	public InvoiceAdapter readForNew () {
		InvoiceAdapter inv = new InvoiceAdapter();	
		
		inv.setFactura(new Factura());
		inv.getFactura().setProveedor(new Proveedor());
		
		return inv;
	}
	
	
	public void save(InvoiceAdapter ia, NavigationState state, Component win){
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			ia.getFactura().setProveedor((Proveedor) prov.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prov, "Debe de seleccionar un proveedor");
		
		
		this.invoiceManager.save(ia.getFactura());
		
		state.setDetailIdentifier(ia.getFactura().getIdFactura());
		state.setUri("/WEB-INF/zul/invoice/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ia.getFactura().getNumeroFactura());
		this.navigationControl.changeView(win, state);
	}
	
	
	public void update(InvoiceAdapter ia, NavigationState state, Component win){
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			ia.getFactura().setProveedor((Proveedor) prov.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prov, "Debe de seleccionar un proveedor");
		
		
		this.invoiceManager.update(ia.getFactura());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(ia.getFactura().getNumeroFactura());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), ia.getFactura().getNumeroFactura());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Factura invoice = (Factura)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(invoice.getIdFactura());
			state.setUri("/WEB-INF/zul/invoice/detail.zul");
			state.appendBreadCrumbsPath(invoice.getNumeroFactura());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Factura in = (Factura)lb.getModel().getElementAt(i);
				detailList.add(in.getIdFactura());
				detailLabels.add(in.getNumeroFactura());
				if (in.getIdFactura().equals(invoice.getIdFactura())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
		
	
	public InvoiceAdapter read(Integer invoiceId){
		InvoiceAdapter in = new InvoiceAdapter();
	
		Factura invoice = this.invoiceManager.get(invoiceId);
		in.setFactura(invoice);
		
		return in;
	}
	
	
	public InvoiceAdapter readForEdit(Integer invoiceId){
		InvoiceAdapter in = new InvoiceAdapter();
		
		Factura invoice = this.invoiceManager.get(invoiceId);
		in.setFactura(invoice);
		
		return in;
	}
	
	public void delete(InvoiceAdapter ia, NavigationState state, Component win){
		this.invoiceManager.delete(ia.getFactura());
		
		state.setUri("/WEB-INF/zul/invoice/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
}
