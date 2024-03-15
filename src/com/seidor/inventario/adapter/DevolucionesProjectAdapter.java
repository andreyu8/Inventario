package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.DevolucionBean;
import com.seidor.inventario.adapter.beans.OutBean;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Proyecto;

public class DevolucionesProjectAdapter {

	private Proyecto proyecto;
	private ArrayList<OutBean> salida;
	private ArrayList<Entrada> entrada;
	private DevolucionBean devolucionBean;
	
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public ArrayList<OutBean> getSalida() {
		return salida;
	}

	public void setSalida(ArrayList<OutBean> salida) {
		this.salida = salida;
	}

	public ArrayList<Entrada> getEntrada() {
		return entrada;
	}

	public void setEntrada(ArrayList<Entrada> entrada) {
		this.entrada = entrada;
	}

	public DevolucionBean getDevolucionBean() {
		return devolucionBean;
	}

	public void setDevolucionBean(DevolucionBean devolucionBean) {
		this.devolucionBean = devolucionBean;
	}
	

}
