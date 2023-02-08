package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.CategorySearchAdapter;
import com.seidor.inventario.dao.CategoryDAO;
import com.seidor.inventario.model.Categoria;


public class CategoryManager {

	private CategoryDAO categoryDao;
	
	
	//Spring getters and setters
	public CategoryDAO getCategoryDao() {
		return categoryDao;
	}

	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	
	//Business logic
	public Categoria get(Integer id){
		return this.categoryDao.get(id);
	}
	
	public ArrayList<Categoria> getAll(){
		return this.categoryDao.getAll();
	}
	
	public void save(Categoria c){
		this.categoryDao.save(c);
	}
	
	public void update(Categoria c){
		this.categoryDao.update(c);
	}
	
	public void delete(Categoria c){
		this.categoryDao.delete(c);
	}

	public ArrayList<Categoria> search(CategorySearchAdapter csa) {
		return this.categoryDao.search(csa);
	}
	
	
}
