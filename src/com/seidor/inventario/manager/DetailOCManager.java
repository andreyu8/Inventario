package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.dao.DetailOCDAO;
import com.seidor.inventario.model.DetalleOrdenCompra;

public class DetailOCManager {
	
	private DetailOCDAO detailOCDao;
	
	public DetailOCDAO getDetailOCDao() {
		return detailOCDao;
	}

	public void setDetailOCDao(DetailOCDAO detailOCDao) {
		this.detailOCDao = detailOCDao;
	}

	//Business logic
	public DetalleOrdenCompra get(Integer id){
		return this.detailOCDao.get(id);
	}
	
	
	public ArrayList<DetalleOrdenCompra> getOC(Integer id){
		return this.detailOCDao.getOC(id);
	}
	
	public ArrayList<DetalleOrdenCompra> getAll(){
		return this.detailOCDao.getAll();
	}
	
	public void save (DetalleOrdenCompra e) {
		this.detailOCDao.save(e);
	}
	
	public void update (DetalleOrdenCompra e) {
		this.detailOCDao.update(e);
	}
	
	public void delete (DetalleOrdenCompra oc) {
		this.detailOCDao.delete(oc);
	}

	public ArrayList<DetalleOrdenCompra> search(ProductSearchAdapter psa) {
		return this.detailOCDao.search(psa);
	}

	public ArrayList<DetalleOrdenCompra> getIdOCAll(Integer idOrdenCompra) {
		return this.detailOCDao.getIdOCAll(idOrdenCompra);
	}

}
