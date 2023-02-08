package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.ProjectAdapter;
import com.seidor.inventario.adapter.render.EstatusComboItemRenderer;
import com.seidor.inventario.adapter.render.ProjectComboitemRenderer;
import com.seidor.inventario.adapter.search.ProjectSearchAdapter;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;

public class ProjectController {

	private ProjectManager projectManager;
	private NavigationControl navigationControl;
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	
	
	// logic search
	public void search(Listbox lb, ProjectSearchAdapter psa, NavigationState state){
		ArrayList<Proyecto> projects = this.projectManager.search(psa);
		
		ListModelList<Proyecto> model = new ListModelList<Proyecto>(projects);
		lb.setModel(model);
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Proyecto project = (Proyecto)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(project.getIdProyecto());
			state.setUri("/WEB-INF/zul/project/detail.zul");
			state.appendBreadCrumbsPath(project.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Proyecto p = (Proyecto)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProyecto());
				detailLabels.add(p.getNombre());
				if (p.getIdProyecto().equals(project.getIdProyecto())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	public ProjectAdapter read(Integer projectId){
		ProjectAdapter pa = new ProjectAdapter();
	
		Proyecto project = this.projectManager.get(projectId);
		pa.setProyecto(project);
		
		return pa;
	}
	
	
	public ProjectAdapter readForEdit(Integer projectId){
		ProjectAdapter pa = new ProjectAdapter();
		
		Proyecto project = this.projectManager.get(projectId);
		pa.setProyecto(project);
		
		return pa;
	}
	
	
	public ProjectAdapter readForNew(){
		ProjectAdapter pa = new ProjectAdapter();
		
		Proyecto project = new Proyecto();
		EstatusProyecto s = new EstatusProyecto();
		pa.setProyecto(project);
		pa.getProyecto().setEstatusProyecto(s);
		
		
		return pa;
	}
	
	
	public void save(ProjectAdapter pa, NavigationState state, Component win){
		
		Combobox escb = (Combobox) win.getFellowIfAny("escb");
		if (escb != null && escb.getSelectedItem()!=null )
			pa.getProyecto().setEstatusProyecto((EstatusProyecto) escb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(escb, "Debe de seleccionar una estatus");
		
		
		this.projectManager.save(pa.getProyecto());
		
		state.setDetailIdentifier(pa.getProyecto().getIdProyecto());
		state.setUri("/WEB-INF/zul/project/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getProyecto().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(ProjectAdapter pa, NavigationState state, Component win){
		
		Combobox escb = (Combobox) win.getFellowIfAny("escb");
		if (escb != null && escb.getSelectedItem()!=null )
			pa.getProyecto().setEstatusProyecto((EstatusProyecto) escb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(escb, "Debe de seleccionar una estatus");
		
		
		this.projectManager.update(pa.getProyecto());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getProyecto().getNombre());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getProyecto().getNombre());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	public void delete(ProjectAdapter pa, NavigationState state, Component win){		
		this.projectManager.delete(pa.getProyecto());
		
		state.setUri("/WEB-INF/zul/project/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
	
	
	public void loadProjects(Combobox combo) {
		ArrayList<Proyecto> projects = this.projectManager.getAll();
		if (projects != null) {
			ListModelList<Proyecto> model = new ListModelList<Proyecto>(projects);
			combo.setItemRenderer(new ProjectComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public void loadOpenProjects(Combobox combo) {
		ArrayList<Proyecto> projects = this.projectManager.getAllOpen();
		if (projects != null) {
			ListModelList<Proyecto> model = new ListModelList<Proyecto>(projects);
			combo.setItemRenderer(new ProjectComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
	public void loadEstatus(Combobox combo) {
		ArrayList<EstatusProyecto> estate = this.projectManager.getEstatus();
		if (estate != null) {
			ListModelList<EstatusProyecto> model = new ListModelList<EstatusProyecto>(estate);
			combo.setItemRenderer(new EstatusComboItemRenderer());
			combo.setModel(model);
		}
	}
	
}
