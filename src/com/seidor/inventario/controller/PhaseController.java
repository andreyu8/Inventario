package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.PhaseComboitemRenderer;
import com.seidor.inventario.manager.PhaseManager;
import com.seidor.inventario.model.Etapa;
import com.seidor.inventario.navigation.NavigationControl;

public class PhaseController {
	
	@Autowired
	private PhaseManager phaseManager;
	
	@Autowired
	private NavigationControl navigationControl;
	

	public PhaseManager getPhaseManager() {
		return phaseManager;
	}

	public void setPhaseManager(PhaseManager phaseManager) {
		this.phaseManager = phaseManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	//read the provider
	public void loadPhase(Combobox combo) {
		ArrayList<Etapa> etapa = this.phaseManager.getAll();
		if (etapa != null) {
			ListModelList<Etapa> model = new ListModelList<Etapa>(etapa);
			combo.setItemRenderer(new PhaseComboitemRenderer());
			combo.setModel(model);
		}
	}

}
