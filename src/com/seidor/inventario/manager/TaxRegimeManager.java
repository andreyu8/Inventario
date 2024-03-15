package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.TaxRegimeDAO;
import com.seidor.inventario.model.RegimenFiscal;

public class TaxRegimeManager {
	
	private TaxRegimeDAO taxRegimeDao;
	
	public TaxRegimeDAO getTaxRegimeDao() {
		return taxRegimeDao;
	}

	public void setTaxRegimeDao(TaxRegimeDAO taxRegimeDao) {
		this.taxRegimeDao = taxRegimeDao;
	}

	//Business logic
	public RegimenFiscal get(Integer id){
		return this.taxRegimeDao.get(id);
	}
	
	public ArrayList<RegimenFiscal> getAll(){
		return this.taxRegimeDao.getAll();
	}
	
	public void save (RegimenFiscal rf) {
		this.taxRegimeDao.save(rf);
	}
	
	public void update (RegimenFiscal rf) {
		this.taxRegimeDao.update(rf);
	}
	
	public void delete (RegimenFiscal rf) {
		this.taxRegimeDao.delete(rf);
	}

}
