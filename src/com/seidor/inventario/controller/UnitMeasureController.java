package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.render.UnitMeasureComboitemRenderer;
import com.seidor.inventario.adapter.search.CategorySearchAdapter;
import com.seidor.inventario.adapter.search.UnitMeasureSearchAdapter;
import com.seidor.inventario.manager.UnitMeasureManager;
import com.seidor.inventario.model.Categoria;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationState;

public class UnitMeasureController {
	
	@Autowired
	private UnitMeasureManager unitMeasureManager; 
	

	public UnitMeasureManager getUnitMeasureManager() {
		return unitMeasureManager;
	}

	public void setUnitMeasureManager(UnitMeasureManager unitMeasureManager) {
		this.unitMeasureManager = unitMeasureManager;
	}


	public void loadUnitMeasure(Combobox combo) {
		ArrayList<UnidadMedida> unitMeasures = this.unitMeasureManager.getAll();
		if (unitMeasures != null) {
			ListModelList<UnidadMedida> model = new ListModelList<UnidadMedida>(unitMeasures);
			combo.setItemRenderer(new UnitMeasureComboitemRenderer());
			combo.setModel(model);
		}
	}

	
	// logic search
	public void search(Listbox lb, UnitMeasureSearchAdapter usa, NavigationState state){
		ArrayList<UnidadMedida> unidadMedida = this.unitMeasureManager.search(usa);
		
		ListModelList<UnidadMedida> model = new ListModelList<UnidadMedida>(unidadMedida);
		lb.setModel(model);
	}
		
	
}
