package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.EmployeeAdapter;
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
	
	//buscar
	public void search(Listbox lb, EmployeeSearchAdapter esa, NavigationState state){
		ArrayList<Empleado> employee = this.employeeManager.search(esa);
		
		ListModelList<Empleado> model = new ListModelList<Empleado>(employee);
		lb.setModel(model);
	}
	
	//mostrar
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Empleado employee = (Empleado)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(employee.getIdEmpleado());
			state.setUri("/WEB-INF/zul/employee/detail.zul");
			state.appendBreadCrumbsPath(employee.getNombre());
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Empleado u = (Empleado)lb.getModel().getElementAt(i);
				detailLabels.add(u.getNombre());
				if (u.getIdEmpleado().equals(employee.getIdEmpleado())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	//leer
	public EmployeeAdapter read(Integer employeeId){
		EmployeeAdapter u = new EmployeeAdapter();
		
		Empleado employee = this.employeeManager.get(employeeId);
		//u.setEmpleado(employee.getIdEmpleado()+" "+(employee.getNombre()));
		u.setEmpleado(employee);
			
		return u;
	}
	
	//leer para editar
	public EmployeeAdapter readForEdit(Integer employeeId){
		EmployeeAdapter u = new EmployeeAdapter();
		
		Empleado employee = this.employeeManager.get(employeeId);
		u.setEmpleado(employee);
			
		return u;
	}
	
	public EmployeeAdapter readForNew(){
		EmployeeAdapter u = new EmployeeAdapter();
		
		Empleado employee = new Empleado();
		employee.setActivo(1);
		
		u.setEmpleado(employee);

		return u;
	}
	
	//eliminar
	public void delete(EmployeeAdapter u, NavigationState state, Component win){		
		this.employeeManager.delete(u.getEmpleado());
		
		state.setUri("/WEB-INF/zul/employee/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
}
