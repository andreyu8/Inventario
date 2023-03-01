package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.AreaComboitemRenderer;
import com.seidor.inventario.manager.AreaManager;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.navigation.NavigationControl;

public class AreaController {

	
	@Autowired
	private AreaManager areaManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	public AreaManager getAreaManager() {
		return areaManager;
	}

	public void setAreaManager(AreaManager areaManager) {
		this.areaManager = areaManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	//read the provider
	public void loadArea(Combobox combo) {
		ArrayList<Area> area = this.areaManager.getAll();
		if (area != null) {
			ListModelList<Area> model = new ListModelList<Area>(area);
			combo.setItemRenderer(new AreaComboitemRenderer());
			combo.setModel(model);
		}
	}
	
}
