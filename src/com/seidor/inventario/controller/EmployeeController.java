package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.render.EmployeeComboitemRenderer;
import com.seidor.inventario.adapter.search.EmployeeSearchAdapter;
import com.seidor.inventario.manager.EmployeeManager;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;

public class EmployeeController {

	@Autowired
	private EmployeeManager employeeManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	
	public void loadEmployee(Combobox combo) {
		ArrayList<Empleado> empleados = this.employeeManager.getAll();
		if (empleados != null) {
			ListModelList<Empleado> model = new ListModelList<Empleado>(empleados);
			combo.setItemRenderer(new EmployeeComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public void search(Listbox lb, EmployeeSearchAdapter esa, NavigationState state){
		ArrayList<Empleado> employee = this.employeeManager.search(esa);
		
		ListModelList<Empleado> model = new ListModelList<Empleado>(employee);
		lb.setModel(model);
	}
	
}
