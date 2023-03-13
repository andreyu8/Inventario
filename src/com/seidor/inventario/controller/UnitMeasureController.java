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
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;

public class UnitMeasureController {
	
	@Autowired
	private UnitMeasureManager unitMeasureManager;
	
	@Autowired
	private NavigationControl navigationControl; 
	

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
	
	public void loadInvoice(Combobox combo) {
		ArrayList<UnidadMedida> unidadMedida = this.unitMeasureManager.getAll();
		if (unidadMedida != null) {
			ListModelList<UnidadMedida> model = new ListModelList<UnidadMedida>(unidadMedida);
			combo.setItemRenderer(new UnitMeasureComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public UnitMeasureAdapter readForNew () {
		UnitMeasureAdapter p = new UnitMeasureAdapter();	
		p.setUnidadMedida(new UnidadMedida());
		return p;
	}
	
	public void save(UnitMeasureAdapter pa, NavigationState state, Component win){
		
		pa.getUnidadMedida().setActivo(1);
		
		this.unitMeasureManager.save(pa.getUnidadMedida());
		
		state.setDetailIdentifier(pa.getUnidadMedida().getIdUnidadMedida());
		state.setUri("/WEB-INF/zul/measure/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getUnidadMedida().getDescripcion());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(UnitMeasureAdapter pa, NavigationState state, Component win){
	
		this.unitMeasureManager.update(pa.getUnidadMedida());
		
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getUnidadMedida().getDescripcion());
		if (prev.getDetailLabels() != null) {
		prev.getDetailLabels().set(prev.getDetailIndex(), pa.getUnidadMedida().getDescripcion());
	}
	
		this.navigationControl.changeToPrevious(win);
	}
	
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			UnidadMedida cliente = (UnidadMedida)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(cliente.getIdUnidadMedida());
			state.setUri("/WEB-INF/zul/measure/detail.zul");
			state.appendBreadCrumbsPath(cliente.getUnidadMedida());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				UnidadMedida p = (UnidadMedida)lb.getModel().getElementAt(i);
				detailList.add(p.getIdUnidadMedida());
				detailLabels.add(p.getDescripcion());
				if (p.getIdUnidadMedida().equals(cliente.getIdUnidadMedida())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	public UnitMeasureAdapter read(Integer unitMeasureId){
		UnitMeasureAdapter pa = new UnitMeasureAdapter();
	
		UnidadMedida p = this.unitMeasureManager.get(unitMeasureId);
		pa.setUnidadMedida(p);
		
		return pa;
	}
	
	public UnitMeasureAdapter readForEdit(Integer unitMeasureId){
		UnitMeasureAdapter pa = new UnitMeasureAdapter();
	
		UnidadMedida p = this.unitMeasureManager.get(unitMeasureId);
		pa.setUnidadMedida(p);
		
		return pa;
	}
	
	public void delete(UnitMeasureAdapter ca, NavigationState state, Component win){
		//ca.getCliente().setActivo(0);
		this.unitMeasureManager.delete(ca.getUnidadMedida());
		
		state.setUri("/WEB-INF/zul/measure/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
}
