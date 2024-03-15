package com.seidor.inventario.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.seidor.inventario.dao.TransactionDetailDAO;
import com.seidor.inventario.model.DetalleMovimiento;

public class TransactionDetailManager {

	@Autowired
	private TransactionDetailDAO transactionDetailDao;
	
	public TransactionDetailDAO getTransactionDetailDao() {
		return transactionDetailDao;
	}

	public void setTransactionDetailDao(TransactionDetailDAO transactionDetailDao) {
		this.transactionDetailDao = transactionDetailDao;
	}

	//Business logic
	public DetalleMovimiento get(Integer id){
		return this.transactionDetailDao.get(id);
	}
	
	public ArrayList<DetalleMovimiento> getAll(){
		return this.transactionDetailDao.getAll();
	}
	
	public void save (DetalleMovimiento dm) {
		this.transactionDetailDao.save(dm);
	}
	
	public void update (DetalleMovimiento dm) {
		this.transactionDetailDao.update(dm);
	}
	
	public void delete (DetalleMovimiento dm) {
		this.transactionDetailDao.delete(dm);
	}

	public ArrayList<DetalleMovimiento> getDetails(Integer idMovimiento) {
		return this.transactionDetailDao.getDetails (idMovimiento);
	}

	
	
}
