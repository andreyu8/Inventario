package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.LocationDAO;
import com.seidor.inventario.model.Ubicacion;

public class LocationManager {
	
	private LocationDAO locationDao;

	public LocationDAO getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(LocationDAO locationDao) {
		this.locationDao = locationDao;
	}

	//Business logic
	public Ubicacion get(Integer id){
		return this.locationDao.get(id);
	}
	
	public ArrayList<Ubicacion> getAll(){
		return this.locationDao.getAll();
	}
	
	public void save (Ubicacion u) {
		this.locationDao.save(u);
	}
	
	public void update (Ubicacion u) {
		this.locationDao.update(u);
	}
	
	public void delete (Ubicacion u) {
		this.locationDao.delete(u);
	}

}
