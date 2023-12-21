package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.DatosBancariosDAO;
import com.seidor.inventario.model.DatosBancarios;

public class DatosBancariosManager {

	private DatosBancariosDAO datosBancariosDao;

	public DatosBancariosDAO getDatosBancariosDao() {
		return datosBancariosDao;
	}

	public void setDatosBancariosDao(DatosBancariosDAO datosBancariosDao) {
		this.datosBancariosDao = datosBancariosDao;
	}
	
	//
	public DatosBancarios get(Integer id){
		return this.datosBancariosDao.get(id);
	}
	
	public ArrayList<DatosBancarios> getAll(){
		return this.datosBancariosDao.getAll();
	}
	
	public void save (DatosBancarios db) {
		this.datosBancariosDao.save(db);
	}
	
	public void update (DatosBancarios db) {
		this.datosBancariosDao.update(db);
	}
	
	public void delete (DatosBancarios db) {
		this.datosBancariosDao.delete(db);
	}

	public ArrayList<DatosBancarios> getDatosBancarios(Integer providerId) {
		return this.datosBancariosDao.getDatosBancarios (providerId);
	}

	
	
}
