package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.UnitMeasureSearchAdapter;
import com.seidor.inventario.dao.UnitMeasureDAO;
import com.seidor.inventario.model.UnidadMedida;

public class UnitMeasureManager {

	private UnitMeasureDAO unitMeasureDao;

	public UnitMeasureDAO getUnitMeasureDao() {
		return unitMeasureDao;
	}

	public void setUnitMeasureDao(UnitMeasureDAO unitMeasureDao) {
		this.unitMeasureDao = unitMeasureDao;
	}
	
	
	//Business logic
	public UnidadMedida get(Integer id){
		return this.unitMeasureDao.get(id);
	}
	
	public ArrayList<UnidadMedida> getAll(){
		return this.unitMeasureDao.getAll();
	}
	
	public void save (UnidadMedida um) {
		this.unitMeasureDao.save(um);
	}
	
	public void update (UnidadMedida um) {
		this.unitMeasureDao.update(um);
	}
	
	public void delete (UnidadMedida um) {
		this.unitMeasureDao.delete(um);
	}

	public ArrayList<UnidadMedida> search(UnitMeasureSearchAdapter usa) {
		return this.unitMeasureDao.search (usa);
	}
	
	
}
