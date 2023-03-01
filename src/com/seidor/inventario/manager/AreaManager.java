package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.AreaDAO;
import com.seidor.inventario.model.Area;

public class AreaManager {

	private AreaDAO areaDao;
	
	public AreaDAO getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(AreaDAO areaDao) {
		this.areaDao = areaDao;
	}

	//Business logic
	public Area get(Integer id){
		return this.areaDao.get(id);
	}
	
	public ArrayList<Area> getAll(){
		return this.areaDao.getAll();
	}
	
	public void save (Area a) {
		this.areaDao.save(a);
	}
	
	public void update (Area a) {
		this.areaDao.update(a);
	}
	
	public void delete (Area a) {
		this.areaDao.delete(a);
	}

	
}
