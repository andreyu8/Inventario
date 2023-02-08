package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.dao.ProviderDAO;
import com.seidor.inventario.model.Proveedor;

public class ProviderManager {
	
	private ProviderDAO providerDao;
	

	public ProviderDAO getProviderDao() {
		return providerDao;
	}

	public void setProviderDao(ProviderDAO providerDao) {
		this.providerDao = providerDao;
	}

	//Business logic
	public Proveedor get(Integer id){
		return this.providerDao.get(id);
	}
	
	public ArrayList<Proveedor> getAll(){
		return this.providerDao.getAll();
	}
	
	public void save (Proveedor p) {
		this.providerDao.save(p);
	}
	
	public void update (Proveedor p) {
		this.providerDao.update(p);
	}
	
	public void delete (Proveedor p) {
		this.providerDao.delete(p);
	}

	public ArrayList<Proveedor> search(ProviderSearchAdapter psa) {
		return this.providerDao.search (psa);
	}

}
