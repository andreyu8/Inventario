package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.dao.InvoiceDAO;
import com.seidor.inventario.model.Factura;

public class InvoiceManager {
	
	private InvoiceDAO InvoiceDao;
	
	public InvoiceDAO getInvoiceDao() {
		return InvoiceDao;
	}

	public void setInvoiceDao(InvoiceDAO invoiceDao) {
		InvoiceDao = invoiceDao;
	}

	//Business logic
	public Factura get(Integer id){
		return this.InvoiceDao.get(id);
	}
	
	public ArrayList<Factura> getAll(){
		return this.InvoiceDao.getAll();
	}
	
	public void save (Factura f) {
		this.InvoiceDao.save(f);
	}
	
	public void update (Factura f) {
		this.InvoiceDao.update(f);
	}
	
	public void delete (Factura f) {
		this.InvoiceDao.delete(f);
	}

	public ArrayList<Factura> search(InvoiceSearchAdapter isa) {
		return this.InvoiceDao.search(isa);
	}

}
