package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.ClientComboitemRenderer;
import com.seidor.inventario.manager.ClientManager;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.navigation.NavigationControl;

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
	
	
}
