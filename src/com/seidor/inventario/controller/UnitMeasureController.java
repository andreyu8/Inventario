package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.UnitMeasureAdapter;
import com.seidor.inventario.adapter.render.UnitMeasureComboitemRenderer;
import com.seidor.inventario.adapter.search.UnitMeasureSearchAdapter;
import com.seidor.inventario.manager.UnitMeasureManager;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.navigation.NavigationControl;

public class UnitMeasureController {
	
	@Autowired
	private UnitMeasureManager unitMeasureManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

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
	
	public UnitMeasureAdapter readForEdit(Integer unitMeasureId){
		UnitMeasureAdapter um = new UnitMeasureAdapter();
		
		UnidadMedida unidadMedida = this.unitMeasureManager.get(unitMeasureId);
		um.setUnidadMedida(unidadMedida);
		
		return um;
	}
	
	public UnitMeasureAdapter read(Integer unitMeasureId){
		UnitMeasureAdapter um = new UnitMeasureAdapter();
		
		UnidadMedida unidadMedida = this.unitMeasureManager.get(unitMeasureId);
		um.setUnidadMedida(unidadMedida);
		
		return um;
	}
	
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			UnidadMedida unidadMedida = (UnidadMedida)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(unidadMedida.getIdUnidadMedida());
			state.setUri("/WEB-INF/zul/mesure/detail.zul");
			state.appendBreadCrumbsPath(unidadMedida.getUnidadMedida());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				UnidadMedida um = (UnidadMedida)lb.getModel().getElementAt(i);
				detailList.add(um.getIdUnidadMedida());
				detailLabels.add(um.getUnidadMedida());
				if (um.getIdUnidadMedida().equals(unidadMedida.getIdUnidadMedida())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	public void update(UnitMeasureAdapter um, NavigationState state, Component win){
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			um.getUnidadMedida().setUnidadMedida(null);		
		
		this.unitMeasureManager.update(um.getUnidadMedida());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(um.getUnidadMedida().getUnidadMedida());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), um.getUnidadMedida().getUnidadMedida());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	public void save(UnitMeasureAdapter um, NavigationState state, Component win){
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			um.getUnidadMedida().setIdUnidadMedida(null);
		
		this.unitMeasureManager.save(um.getUnidadMedida());
		
		state.setDetailIdentifier(um.getUnidadMedida().getIdUnidadMedida());
		state.setUri("/WEB-INF/zul/mesure/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(um.getUnidadMedida().getUnidadMedida());
		this.navigationControl.changeView(win, state);
	}
	
	public UnitMeasureAdapter readForNew () {
		UnitMeasureAdapter um = new UnitMeasureAdapter();	
		
		um.setUnidadMedida(new UnidadMedida());
		
		return um;
	}
	
	public void delete(UnitMeasureAdapter um, NavigationState state, Component win){		
		this.unitMeasureManager.delete(um.getUnidadMedida());
		
		state.setUri("/WEB-INF/zul/mesure/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}	
}
