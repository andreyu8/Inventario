package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.CategoryAdapter;
import com.seidor.inventario.adapter.render.CategoryComboitemRenderer;
import com.seidor.inventario.adapter.search.CategorySearchAdapter;
import com.seidor.inventario.manager.CategoryManager;
import com.seidor.inventario.model.Categoria;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;

public class CategoryController {
	
	private CategoryManager categoryManager;
	private NavigationControl navigationControl;
	
	
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}

	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
	
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	
	
	// logic search
	public void search(Listbox lb, CategorySearchAdapter csa, NavigationState state){
		ArrayList<Categoria> categorys = this.categoryManager.search(csa);
		
		ListModelList<Categoria> model = new ListModelList<Categoria>(categorys);
		lb.setModel(model);
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Categoria category = (Categoria)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(category.getIdCategoria());
			state.setUri("/WEB-INF/zul/category/detail.zul");
			state.appendBreadCrumbsPath(category.getCategoria());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Categoria c = (Categoria)lb.getModel().getElementAt(i);
				detailList.add(c.getIdCategoria());
				detailLabels.add(c.getCategoria());
				if (c.getIdCategoria().equals(category.getIdCategoria())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	public CategoryAdapter read(Integer categoryId){
		CategoryAdapter ca = new CategoryAdapter();
	
		Categoria category = this.categoryManager.get(categoryId);
		ca.setCategoria(category);
		
		return ca;
	}
	
	
	public CategoryAdapter readForEdit(Integer categoryId){
		CategoryAdapter ca = new CategoryAdapter();
		
		Categoria category = this.categoryManager.get(categoryId);
		ca.setCategoria(category);
		
		return ca;
	}
	
	
	public CategoryAdapter readForNew(){
		CategoryAdapter ca = new CategoryAdapter();
		
		Categoria category = new Categoria();
		category.setActivo(1);

		ca.setCategoria(category);
		
		
		return ca;
	}
	
	
	public void save(CategoryAdapter ca, NavigationState state, Component win){
		
		this.categoryManager.save(ca.getCategoria());
		
		state.setDetailIdentifier(ca.getCategoria().getIdCategoria());
		state.setUri("/WEB-INF/zul/category/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ca.getCategoria().getCategoria());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(CategoryAdapter ca, NavigationState state, Component win){
		
		this.categoryManager.update(ca.getCategoria());
			
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(ca.getCategoria().getCategoria());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), ca.getCategoria().getCategoria());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	public void delete(CategoryAdapter ca, NavigationState state, Component win){		
		this.categoryManager.delete(ca.getCategoria());
		
		state.setUri("/WEB-INF/zul/category/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
	
	
	public void loadCategory(Combobox combo) {
		ArrayList<Categoria> categorys = this.categoryManager.getAll();
		if (categorys != null) {
			ListModelList<Categoria> model = new ListModelList<Categoria>(categorys);
			combo.setItemRenderer(new CategoryComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
}
