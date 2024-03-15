package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.WarehouseDAO;
import com.seidor.inventario.model.Almacen;

public class WarehouseManager {
	
	private WarehouseDAO warehouseDao;
	
	public WarehouseDAO getWarehouseDao() {
		return warehouseDao;
	}

	public void setWarehouseDao(WarehouseDAO warehouseDao) {
		this.warehouseDao = warehouseDao;
	}

	//Business logic
	public Almacen get(Integer id){
		return this.warehouseDao.get(id);
	}
	
	public ArrayList<Almacen> getAll(){
		return this.warehouseDao.getAll();
	}
	
	public void save (Almacen a) {
		this.warehouseDao.save(a);
	}
	
	public void update (Almacen a) {
		this.warehouseDao.update(a);
	}
	
	public void delete (Almacen a) {
		this.warehouseDao.delete(a);
	}

	public ArrayList<Almacen> getAlmacenes(Integer idAlmacen) {
		return this.warehouseDao.getAlmacenes (idAlmacen);
	}
	

}
