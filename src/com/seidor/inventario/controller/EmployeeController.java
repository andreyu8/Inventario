package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.EmployeeComboitemRenderer;
import com.seidor.inventario.manager.EmployeeManager;
import com.seidor.inventario.model.Empleado;

public class EmployeeController {

	
	private EmployeeManager employeeManager;
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}



	public void loadEmployee(Combobox combo) {
		ArrayList<Empleado> empleados = this.employeeManager.getAll();
		if (empleados != null) {
			ListModelList<Empleado> model = new ListModelList<Empleado>(empleados);
			combo.setItemRenderer(new EmployeeComboitemRenderer());
			combo.setModel(model);
		}
	}
	
}
