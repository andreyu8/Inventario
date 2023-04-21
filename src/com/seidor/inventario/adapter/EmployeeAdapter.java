package com.seidor.inventario.adapter;

import com.seidor.inventario.model.Empleado;

public class EmployeeAdapter {
	
	private Empleado empleado;
	private Boolean falgActive;

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado employee) {
		this.empleado = employee;
	}

	public Boolean getFalgActive() {
		return falgActive;
	}

	public void setFalgActive(Boolean falgActive) {
		this.falgActive = falgActive;
	}
}
