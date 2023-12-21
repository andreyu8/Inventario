package com.seidor.inventario.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.seidor.inventario.dao.TipoMovimientoDAO;
import com.seidor.inventario.model.TiposMovimiento;

public class TipoMovimientoManager {
	
	@Autowired
	private TipoMovimientoDAO tipoMovimientoDao;
	
	public TipoMovimientoDAO getTipoMovimientoDao() {
		return tipoMovimientoDao;
	}

	public void setTipoMovimientoDao(TipoMovimientoDAO tipoMovimientoDao) {
		this.tipoMovimientoDao = tipoMovimientoDao;
	}

	//Business logic
	public TiposMovimiento get(Integer id){
		return this.tipoMovimientoDao.get(id);
	}
	
	public ArrayList<TiposMovimiento> getAll(){
		return this.tipoMovimientoDao.getAll();
	}
	
	public void save (TiposMovimiento tm) {
		this.tipoMovimientoDao.save(tm);
	}
	
	public void update (TiposMovimiento tm) {
		this.tipoMovimientoDao.update(tm);
	}
	
	public void delete (TiposMovimiento tm) {
		this.tipoMovimientoDao.delete(tm);
	}

}
