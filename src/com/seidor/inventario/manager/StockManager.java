package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.StockAdapter;
import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.dao.StockDAO;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;

public class StockManager {
	
	private StockDAO stockDao;

	public StockDAO getStockDao() {
		return stockDao;
	}

	public void setStockDao(StockDAO stockDao) {
		this.stockDao = stockDao;
	}

	
	
	//logic
	public void save (Entrada e, MovimientosStock s, Producto p) {
		this.stockDao.save(e, s, p);
	}
	
	public void update (MovimientosStock s) {
		this.stockDao.update(s);
	}

	public ArrayList<Producto> search(ProductSearchAdapter psa) {
		return this.stockDao.search (psa);
	}

	public MovimientosStock get(Integer idProducto) {
		return this.stockDao.get(idProducto);
	}

	public MovimientosStock getIdProjectProduct(Integer idProyecto, Integer idProducto) {
		return this.stockDao.getIdProjectProduct(idProyecto, idProducto);
	}

	public void saveEntryStock(MovimientosStock movSctock, Salida salida, Producto product) {
		this.stockDao.saveEntryStock (movSctock, salida, product);
	}

}
