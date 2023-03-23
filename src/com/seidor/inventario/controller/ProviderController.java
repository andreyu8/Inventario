package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.InvoiceAdapter;
import com.seidor.inventario.adapter.ProviderAdapter;
import com.seidor.inventario.adapter.render.ProveedorComboitemRenderer;
import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.manager.ProviderManager;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;

public class ProviderController {

	
	private ProviderManager providerManager;
	private NavigationControl navigationControl;

	public ProviderManager getProviderManager() {
		return providerManager;
	}

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
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
	
	
	public ProviderAdapter readForNew () {
		ProviderAdapter p = new ProviderAdapter();	
		p.setProveedor(new Proveedor());
		return p;
	}
	
	
	public void save(ProviderAdapter pa, NavigationState state, Component win){
		
		pa.getProveedor().setActivo(1);
		
		this.providerManager.save(pa.getProveedor());
		
		state.setDetailIdentifier(pa.getProveedor().getIdProveedor());
		state.setUri("/WEB-INF/zul/provider/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getProveedor().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	
	public void update(ProviderAdapter pa, NavigationState state, Component win){
		
		
		if (pa.getFalgActive())
			pa.getProveedor().setActivo(1);
		else
			pa.getProveedor().setActivo(0);
		
		this.providerManager.update(pa.getProveedor());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getProveedor().getNombre());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getProveedor().getNombre());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Proveedor proveedor = (Proveedor)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(proveedor.getIdProveedor());
			state.setUri("/WEB-INF/zul/provider/detail.zul");
			state.appendBreadCrumbsPath(proveedor.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Proveedor p = (Proveedor)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProveedor());
				detailLabels.add(p.getNombre());
				if (p.getIdProveedor().equals(proveedor.getIdProveedor())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
		
	
	public ProviderAdapter read(Integer providerId){
		ProviderAdapter pa = new ProviderAdapter();
	
		Proveedor p = this.providerManager.get(providerId);
		pa.setProveedor(p);
		
		if (pa.getProveedor().getActivo() == 1)
			pa.setFalgActive(Boolean.TRUE);
		else
			pa.setFalgActive(Boolean.FALSE);
		
		return pa;
	}
	
	
	public ProviderAdapter readForEdit(Integer providerId){
		ProviderAdapter pa = new ProviderAdapter();
		
		Proveedor p = this.providerManager.get(providerId);
		pa.setProveedor(p);
		
		if (pa.getProveedor().getActivo() == 1)
			pa.setFalgActive(Boolean.TRUE);
		else
			pa.setFalgActive(Boolean.FALSE);

		
		return pa;
	}
	
	public void delete(ProviderAdapter pa, NavigationState state, Component win){
		this.providerManager.delete(pa.getProveedor());
		
		state.setUri("/WEB-INF/zul/provider/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
}
