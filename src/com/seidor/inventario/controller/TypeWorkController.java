package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.TypeWorkComboitemRenderer;
import com.seidor.inventario.manager.TypeWorkManager;
import com.seidor.inventario.model.TipoTrabajo;

public class TypeWorkController {
	
	private TypeWorkManager typeWorkManager;
	
	public TypeWorkManager getTypeWorkManager() {
		return typeWorkManager;
	}

	public void setTypeWorkManager(TypeWorkManager typeWorkManager) {
		this.typeWorkManager = typeWorkManager;
	}




	public void loadTypeWork(Combobox combo) {
		ArrayList<TipoTrabajo> typeworks = this.typeWorkManager.getAll();
		if (typeworks != null) {
			ListModelList<TipoTrabajo> model = new ListModelList<TipoTrabajo>(typeworks);
			combo.setItemRenderer(new TypeWorkComboitemRenderer());
			combo.setModel(model);
		}
	}

}
