package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.IvaDAO;
import com.seidor.inventario.model.TasaCuotaIva;

public class IvaManager {
	
	private IvaDAO ivaDao;
	
	public IvaDAO getIvaDao() {
		return ivaDao;
	}

	public void setIvaDao(IvaDAO ivaDao) {
		this.ivaDao = ivaDao;
	}

	//Business logic
	public TasaCuotaIva get(Integer id){
		return this.ivaDao.get(id);
	}
	
	public ArrayList<TasaCuotaIva> getAll(){
		return this.ivaDao.getAll();
	}
	
	public void save (TasaCuotaIva i) {
		this.ivaDao.save(i);
	}
	
	public void update (TasaCuotaIva i) {
		this.ivaDao.update(i);
	}
	
	public void delete (TasaCuotaIva i) {
		this.ivaDao.delete(i);
	}

}
