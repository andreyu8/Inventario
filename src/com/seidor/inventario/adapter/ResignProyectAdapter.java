package com.seidor.inventario.adapter;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.CloseBean;
import com.seidor.inventario.adapter.beans.OutBean;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Proyecto;

public class ResignProyectAdapter {

	private Proyecto proyecto;
	private Proyecto proyectoDestino;
	private ArrayList<OutBean> salida;
	private ArrayList<Entrada> entrada;
	private CloseBean reasignBean;

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Proyecto getProyectoDestino() {
		return proyectoDestino;
	}

	public void setProyectoDestino(Proyecto proyectoDestino) {
		this.proyectoDestino = proyectoDestino;
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

	public CloseBean getReasignBean() {
		return reasignBean;
	}

	public void setReasignBean(CloseBean reasignBean) {
		this.reasignBean = reasignBean;
	}

}
