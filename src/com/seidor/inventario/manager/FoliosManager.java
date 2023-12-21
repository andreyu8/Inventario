package com.seidor.inventario.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.seidor.inventario.dao.FoliosDAO;
import com.seidor.inventario.model.Folios;

public class FoliosManager {
	
	@Autowired
	private FoliosDAO foliosDao;
	
	public FoliosDAO getFoliosDao() {
		return foliosDao;
	}

	public void setFoliosDao(FoliosDAO foliosDao) {
		this.foliosDao = foliosDao;
	}

	//Business logic
	public Folios get(Integer id){
		return this.foliosDao.get(id);
	}
	
	public ArrayList<Folios> getAll(){
		return this.foliosDao.getAll();
	}
	
	public void save (Folios f) {
		this.foliosDao.save(f);
	}
	
	public void update (Folios f) {
		this.foliosDao.update(f);
	}
	
	public void delete (Folios f) {
		this.foliosDao.delete(f);
	}

	public Folios getFolioMax(Integer idTipoMovimiento) {
		return this.foliosDao.getFolioMax(idTipoMovimiento);
	}

}
