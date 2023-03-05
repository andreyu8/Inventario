package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.ClientAdapter;
import com.seidor.inventario.adapter.ProviderAdapter;
import com.seidor.inventario.adapter.render.ClientComboitemRenderer;
import com.seidor.inventario.adapter.render.ProveedorComboitemRenderer;
import com.seidor.inventario.adapter.search.ClientSearchAdapter;
import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.manager.ClientManager;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
=======
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.ClientComboitemRenderer;
import com.seidor.inventario.manager.ClientManager;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.navigation.NavigationControl;
>>>>>>> 297b8c16a21d4043176898c8785467f62c2d96c1

public class ClientController {

	@Autowired
	private ClientManager clientManager;
	
	@Autowired
	private NavigationControl navigationControl;

	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	//read the provider
	public void loadClient(Combobox combo) {
		ArrayList<Cliente> client = this.clientManager.getAll();
		if (client != null) {
			ListModelList<Cliente> model = new ListModelList<Cliente>(client);
			combo.setItemRenderer(new ClientComboitemRenderer());
			combo.setModel(model);
		}
	}
	
<<<<<<< HEAD
	public void search(Listbox lb, ClientSearchAdapter psa, NavigationState state){
		ArrayList<Cliente> client = this.clientManager.search(psa);
		
		ListModelList<Cliente> model = new ListModelList<Cliente>(client);
		lb.setModel(model);
	}
	
	public void loadInvoice(Combobox combo) {
		ArrayList<Cliente> clientes = this.clientManager.getAll();
		if (clientes != null) {
			ListModelList<Cliente> model = new ListModelList<Cliente>(clientes);
			combo.setItemRenderer(new ClientComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public ClientAdapter readForNew () {
		ClientAdapter p = new ClientAdapter();	
		p.setCliente(new Cliente());
		return p;
	}
	
	public void save(ClientAdapter pa, NavigationState state, Component win){
		
		pa.getCliente().setActivo(1);
		
		this.clientManager.save(pa.getCliente());
		
		state.setDetailIdentifier(pa.getCliente().getIdCliente());
		state.setUri("/WEB-INF/zul/client/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getCliente().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(ClientAdapter pa, NavigationState state, Component win){
		
		
		if (pa.getFalgActive())
			pa.getCliente().setActivo(1);
		else
			pa.getCliente().setActivo(0);
		
		this.clientManager.update(pa.getCliente());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getCliente().getNombre());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getCliente().getNombre());
		}
		
		this.navigationControl.changeToPrevious(win);
	}
	
	//Metodo para mostrar el cliente 
		public void show(Listbox lb, NavigationState state, Component win){
			if (lb.getSelectedIndex() >= 0) {
				Cliente cliente = (Cliente)lb.getModel().getElementAt(lb.getSelectedIndex());
				
				state.setDetailIdentifier(cliente.getIdCliente());
				state.setUri("/WEB-INF/zul/client/detail.zul");
				state.appendBreadCrumbsPath(cliente.getNombre());
				
				
				ArrayList<Integer> detailList = new ArrayList<Integer>();
				ArrayList<String> detailLabels = new ArrayList<String>();
				for (int i = 0; i < lb.getModel().getSize(); i++) {
					Cliente p = (Cliente)lb.getModel().getElementAt(i);
					detailList.add(p.getIdCliente());
					detailLabels.add(p.getNombre());
					if (p.getIdCliente().equals(cliente.getIdCliente())) state.setDetailIndex(detailList.size() - 1);
				}
				state.setDetailList(detailList);
				state.setDetailLabels(detailLabels);
				this.navigationControl.changeView(win, state);
			}
		}
		
		public ClientAdapter read(Integer clientId){
			ClientAdapter pa = new ClientAdapter();
		
			Cliente p = this.clientManager.get(clientId);
			pa.setCliente(p);
			
			if (pa.getCliente().getActivo() == 1)
				pa.setFalgActive(Boolean.TRUE);
			else
				pa.setFalgActive(Boolean.FALSE);
			
			return pa;
		}
		
		public ClientAdapter readForEdit(Integer clientId){
			ClientAdapter pa = new ClientAdapter();
			
			Cliente p = this.clientManager.get(clientId);
			pa.setCliente(p);
			
			if (pa.getCliente().getActivo() == 1)
				pa.setFalgActive(Boolean.TRUE);
			else
				pa.setFalgActive(Boolean.FALSE);

			
			return pa;
		}
		
=======
>>>>>>> 297b8c16a21d4043176898c8785467f62c2d96c1
	
}
