package com.seidor.inventario.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.seidor.inventario.adapter.search.TransactionSearchAdapter;
import com.seidor.inventario.dao.TransactionDAO;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Folios;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;

public class TransactionManager {
	
	@Autowired
	private TransactionDAO transactionDao;
	
	public TransactionDAO getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDAO transactionDao) {
		this.transactionDao = transactionDao;
	}

	//Business logic
	public Movimientos get(Integer id){
		return this.transactionDao.get(id);
	}
	
	public ArrayList<Movimientos> getAll(){
		return this.transactionDao.getAll();
	}
	
	public void save (Movimientos m) {
		this.transactionDao.save(m);
	}
	
	public void update (Movimientos m) {
		this.transactionDao.update(m);
	}
	
	public void delete (Movimientos m) {
		this.transactionDao.delete(m);
	}

	public ArrayList<Movimientos> searchEntry(TransactionSearchAdapter tsa) {
		return this.transactionDao.searchEntry (tsa);
	}

	public ArrayList<Movimientos> searchInvoiceEntry(Integer idFactura) {
		return this.transactionDao.searchInvoiceEntry (idFactura);
	}

	public void saveEntrada(Factura factura, Movimientos movimiento,
			ArrayList<DetalleMovimiento> listDetailTransactionENT, Folios fte, ArrayList<Entrada> listEntrada, ArrayList<Producto> listProducto) {
		this.transactionDao.saveEntrada(factura, movimiento, listDetailTransactionENT, fte, listEntrada, listProducto);
		
	}

	public ArrayList<Movimientos> searchOutput(TransactionSearchAdapter tsa) {
		return this.transactionDao.searchOutput (tsa);
	}

	public void saveSalida(Movimientos movimientos, ArrayList<DetalleMovimiento> listDetailTransactionSAL, Folios fte,
			ArrayList<Salida> listSalida, ArrayList<Producto> listProducto) {
		this.transactionDao.saveSalida(movimientos, listDetailTransactionSAL, fte, listSalida, listProducto);
	}

	public Movimientos getSalida(Integer idMovimiento) {
		return this.transactionDao.getSalida (idMovimiento);
	}

	public void saveDevoluciones(ArrayList<Producto> listProducto, Movimientos movimientoEntradaStock, ArrayList<DetalleMovimiento> listEntradaStock,
			Folios fes, Movimientos movimientoSalidaReasignacion, ArrayList<DetalleMovimiento> listSalidaReasignacion, Folios fsr) {
		this.transactionDao.saveDevoluciones (listProducto, movimientoEntradaStock, listEntradaStock, fes, movimientoSalidaReasignacion, listSalidaReasignacion, fsr);
		
	}


}
