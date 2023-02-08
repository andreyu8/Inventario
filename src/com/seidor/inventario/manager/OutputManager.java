package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.OutputDAO;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;

public class OutputManager {
	
	private OutputDAO outputDao;

	public OutputDAO getOutputDao() {
		return outputDao;
	}

	public void setOutputDao(OutputDAO outputDao) {
		this.outputDao = outputDao;
	}

	
	
	//logic
	public void save (Salida s, Producto p) {
		this.outputDao.save(s, p);
	}
	
	public void update (Salida s) {
		this.outputDao.update(s);
	}

	public ArrayList<Salida> getOutputAll(Integer projectId) {
		return  this.outputDao.getOutpuAll(projectId);		
	}

}
