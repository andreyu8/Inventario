package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import com.seidor.inventario.adapter.EmployeeAdapter;
import com.seidor.inventario.adapter.InvoiceAdapter;
import com.seidor.inventario.adapter.UserAdapter;
import com.seidor.inventario.adapter.render.EmployeeComboitemRenderer;
import com.seidor.inventario.manager.EmployeeManager;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.EncryptUtil;

public class EmployeeController {

	
	private EmployeeManager employeeManager;
	private NavigationControl navigationControl;
	
	
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
	
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Empleado empleados = (Empleado)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(empleados.getIdEmpleado());
			state.setUri("/WEB-INF/zul/user/detail.zul");
			state.appendBreadCrumbsPath(empleados.getNombre());
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Empleado u = (Empleado)lb.getModel().getElementAt(i);
				detailList.add(u.getIdEmpleado());
				detailLabels.add(u.getNombre());
				if (u.getIdEmpleado().equals(empleados.getIdEmpleado())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	public EmployeeAdapter read(Integer userId){
		EmployeeAdapter ua = new EmployeeAdapter();
	
		Empleado empleados = this.employeeManager.get(userId);
		ua.setEmpleado(empleados);
		
		return ua;
	}
	
	public EmployeeAdapter readForEdit(Integer userId){
		EmployeeAdapter ua = new EmployeeAdapter();
		
		Empleado empleados = this.employeeManager.get(userId);
		ua.setEmpleado(empleados);
		
		
		return ua;
	}
	
	public EmployeeAdapter readForNew(){
		EmployeeAdapter ua = new EmployeeAdapter();
		
		Empleado empleados = new Empleado();
		empleados.setActivo(1);
		ua.setEmpleado(empleados);
		
		return ua;
	}
	
	public void save(EmployeeAdapter ua, NavigationState state, Component win){
		Textbox pstb = (Textbox)win.getFellowIfAny("pstb");
		if (pstb.getValue() != null && pstb.getValue().length() > 0) {
			if (pstb.getValue().length() >= 6) {
				Textbox pctb = (Textbox)win.getFellowIfAny("pctb");
				if (pctb.getValue() != null && pstb.getValue().equals(pctb.getValue())){
					String pass = EncryptUtil.encryptSHA(pstb.getValue());
					ua.getEmpleado().setNombre(null);;
				}
				else {
					throw new WrongValueException(pctb, "Confirmación de nombre incorrecto");
				}
			}	
		}
		
		this.employeeManager.save(null);;
		
		state.setDetailIdentifier(ua.getEmpleado().getIdEmpleado());
		state.setUri("/WEB-INF/zul/employee/detail.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ua.getEmpleado().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	public void delete(EmployeeAdapter ua, NavigationState state, Component win){		
		this.employeeManager.delete(ua.getEmpleado());
		
		state.setUri("/WEB-INF/zul/employee/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
}
