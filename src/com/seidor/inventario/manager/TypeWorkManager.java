package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.TypeWorkDAO;
import com.seidor.inventario.model.TipoTrabajo;

public class TypeWorkManager {

	private TypeWorkDAO typeWorkDao;

	public TypeWorkDAO getTypeWorkDao() {
		return typeWorkDao;
	}

	public void setTypeWorkDao(TypeWorkDAO typeWorkDao) {
		this.typeWorkDao = typeWorkDao;
	}
	
	//logic
	
	public TipoTrabajo get (Integer id) {
		return this.typeWorkDao.get(id);
	}
	
	public ArrayList<TipoTrabajo> getAll () {
		return this.typeWorkDao.getAll();
	}
	
}
