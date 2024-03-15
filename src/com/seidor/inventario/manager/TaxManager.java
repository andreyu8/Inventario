package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.TaxDAO;
import com.seidor.inventario.model.Impuestos;

public class TaxManager {
	
	private TaxDAO taxDao;
	
	public TaxDAO getTaxDao() {
		return taxDao;
	}

	public void setTaxDao(TaxDAO taxDao) {
		this.taxDao = taxDao;
	}

	//Business logic
	public Impuestos get(Integer id){
		return this.taxDao.get(id);
	}
	
	public ArrayList<Impuestos> getAll(){
		return this.taxDao.getAll();
	}
	
	public void save (Impuestos i) {
		this.taxDao.save(i);
	}
	
	public void update (Impuestos i) {
		this.taxDao.update(i);
	}
	
	public void delete (Impuestos i) {
		this.taxDao.delete(i);
	}

}
