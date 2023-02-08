package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.ProjectSearchAdapter;
import com.seidor.inventario.dao.ProjectDAO;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.Proyecto;

public class ProjectManager {

	private ProjectDAO projectDao;
	
	
	//Spring getters and setters
	public ProjectDAO getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDAO projectDao) {
		this.projectDao = projectDao;
	}
	
	
	//Business logic
	
	public Proyecto get(Integer id){
		return this.projectDao.get(id);
	}
	
	public ArrayList<Proyecto> getAll(){
		return this.projectDao.getAll();
	}
	
	public void save(Proyecto p){
		this.projectDao.save(p);
	}
	
	public void update(Proyecto p){
		this.projectDao.update(p);
	}
	
	public void delete(Proyecto p){
		this.projectDao.delete(p);
	}

	public ArrayList<Proyecto> search(ProjectSearchAdapter psa) {
		return this.projectDao.search(psa);
	}

	public ArrayList<EstatusProyecto> getEstatus() {
		return this.projectDao.getEstatus();
	}

	public ArrayList<Proyecto> getAllOpen() {
		return this.projectDao.getAllOpen();
	} 
	
	
}
