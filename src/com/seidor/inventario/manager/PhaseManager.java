package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.dao.PhaseDAO;
import com.seidor.inventario.model.Etapa;

public class PhaseManager {

	private PhaseDAO phaseDao;
	
	public PhaseDAO getPhaseDao() {
		return phaseDao;
	}

	public void setPhaseDao(PhaseDAO phaseDao) {
		this.phaseDao = phaseDao;
	}

	//Business logic
	public Etapa get(Integer id){
		return this.phaseDao.get(id);
	}
	
	public ArrayList<Etapa> getAll(){
		return this.phaseDao.getAll();
	}
	
	public void save (Etapa p) {
		this.phaseDao.save(p);
	}
	
	public void update (Etapa p) {
		this.phaseDao.update(p);
	}
	
	public void delete (Etapa p) {
		this.phaseDao.delete(p);
	}


}
