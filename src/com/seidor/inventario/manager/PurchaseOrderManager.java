package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.dao.PurchaseOrderDAO;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.TipoPago;

public class PurchaseOrderManager {
	
	private PurchaseOrderDAO purchaseOrderDao;
	
	public PurchaseOrderDAO getPurchaseOrderDao() {
		return purchaseOrderDao;
	}

	public void setPurchaseOrderDao(PurchaseOrderDAO purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}

	//Business logic
	public OrdenCompra get(Integer id){
		return this.purchaseOrderDao.get(id);
	}
	
	public ArrayList<OrdenCompra> getAll(){
		return this.purchaseOrderDao.getAll();
	}
	
	public void save (OrdenCompra oc) {
		this.purchaseOrderDao.save(oc);
	}
	
	public void update (OrdenCompra oc) {
		this.purchaseOrderDao.update(oc);
	}
	
	public void delete (OrdenCompra oc) {
		this.purchaseOrderDao.delete(oc);
	}

	public ArrayList<OrdenCompra> search(PurchaseOrderSearchAdapter psa) {
		return this.purchaseOrderDao.search(psa);
	}
	
	public ArrayList<EstatusOrdenCompra> getAllTypeOrder() {
		return this.purchaseOrderDao.getAllTypeOrder();
	}
	
	public ArrayList<TipoPago> getAllTypePayment() {
		return this.purchaseOrderDao.getAllTypePayment();
	}

	public ArrayList<OrdenCompra> getProviderExist(Integer idProveedor) {
		return this.purchaseOrderDao.getProviderExist(idProveedor);
	}

	public void save(OrdenCompra orderCompra, ArrayList<DetalleOrdenCompra> listDE) {
		this.purchaseOrderDao.save(orderCompra, listDE);
	}

	public void update(OrdenCompra orderCompra, ArrayList<DetalleOrdenCompra> detalleOCsave,
			ArrayList<DetalleOrdenCompra> detalleOCupdate) {
		this.purchaseOrderDao.update (orderCompra, detalleOCsave, detalleOCupdate);
	}

	public ArrayList<OrdenCompra> searchNew(PurchaseOrderSearchAdapter psa) {
		return this.purchaseOrderDao.searchNew(psa);
	}

	

}
