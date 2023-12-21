package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.EmployeeSearchAdapter;
import com.seidor.inventario.dao.EmployeeDAO;
import com.seidor.inventario.model.Empleado;

public class EmployeeManager {

	private EmployeeDAO employeeDao;
	
	public EmployeeDAO getEmployeeDao() {
		return employeeDao;
	}

	public void setEmployeeDao(EmployeeDAO employeeDao) {
		this.employeeDao = employeeDao;
	}

	//Business logic
	public Empleado get(Integer id){
		return this.employeeDao.get(id);
	}
	
	public ArrayList<Empleado> getAll(){
		return this.employeeDao.getAll();
	}
	
	public void save (Empleado e) {
		this.employeeDao.save(e);
	}
	
	public void update (Empleado e) {
		this.employeeDao.update(e);
	}
	
	public void delete (Empleado oc) {
		this.employeeDao.delete(oc);
	}

	public ArrayList<Empleado> search(EmployeeSearchAdapter esa) {
		return 	this.employeeDao.search (esa);
	}

	public ArrayList<Empleado> getAllProject() {
		return this.employeeDao.getAllProject();
	}

	public ArrayList<Empleado> getByParentId(Integer idArea, Integer id_almacen) {
		return this.employeeDao.getByParentId(idArea, id_almacen);
	}
	
	
}
