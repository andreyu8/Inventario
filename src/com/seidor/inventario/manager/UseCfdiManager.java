package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.UseCfdiDAO;
import com.seidor.inventario.model.UsoCfdi;

public class UseCfdiManager {
	
	private UseCfdiDAO useCfdiDao;
	
	public UseCfdiDAO getUseCfdiDao() {
		return useCfdiDao;
	}

	public void setUseCfdiDao(UseCfdiDAO useCfdiDao) {
		this.useCfdiDao = useCfdiDao;
	}

	//Business logic
	public UsoCfdi get(Integer id){
		return this.useCfdiDao.get(id);
	}
	
	public ArrayList<UsoCfdi> getAll(){
		return this.useCfdiDao.getAll();
	}
	
	public void save (UsoCfdi uc) {
		this.useCfdiDao.save(uc);
	}
	
	public void update (UsoCfdi uc) {
		this.useCfdiDao.update(uc);
	}
	
	public void delete (UsoCfdi uc) {
		this.useCfdiDao.delete(uc);
	}

}
