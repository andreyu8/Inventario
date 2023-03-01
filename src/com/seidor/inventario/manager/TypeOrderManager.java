package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.TypeOrderDAO;
import com.seidor.inventario.model.TipoOrdenCompra;

public class TypeOrderManager {
	
	private TypeOrderDAO typeOrderDao;
	
	public TypeOrderDAO getTypeOrderDao() {
		return typeOrderDao;
	}

	public void setTypeOrderDao(TypeOrderDAO typeOrderDao) {
		this.typeOrderDao = typeOrderDao;
	}

	//Business logic
	public TipoOrdenCompra get(Integer id){
		return this.typeOrderDao.get(id);
	}
	
	public ArrayList<TipoOrdenCompra> getAll(){
		return this.typeOrderDao.getAll();
	}
	
	public void save (TipoOrdenCompra toc) {
		this.typeOrderDao.save(toc);
	}
	
	public void update (TipoOrdenCompra toc) {
		this.typeOrderDao.update(toc);
	}
	
	public void delete (TipoOrdenCompra toc) {
		this.typeOrderDao.delete(toc);
	}
	

}
