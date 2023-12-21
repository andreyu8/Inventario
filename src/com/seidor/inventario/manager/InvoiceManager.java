package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.dao.InvoiceDAO;
import com.seidor.inventario.model.Factura;

public class InvoiceManager {
	
	private InvoiceDAO invoiceDao;
	

	public InvoiceDAO getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(InvoiceDAO invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	//Business logic
	public Factura get(Integer id){
		return this.invoiceDao.get(id);
	}
	
	public ArrayList<Factura> getAll(){
		return this.invoiceDao.getAll();
	}
	
	public void save (Factura f) {
		this.invoiceDao.save(f);
	}
	
	public void update (Factura f) {
		this.invoiceDao.update(f);
	}
	
	public void delete (Factura f) {
		this.invoiceDao.delete(f);
	}

	public ArrayList<Factura> search(InvoiceSearchAdapter isa) {
		return this.invoiceDao.search(isa);
	}

	public Factura getOC(Integer idOrdenCompra) {
		return this.invoiceDao.getOC(idOrdenCompra);
	}

	public Factura getNoFactura(String numeroFactura) {
		return this.invoiceDao.getNoFactura(numeroFactura);
	}

}
