package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.EntryAdapter;
import com.seidor.inventario.dao.EntryDAO;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;

public class EntryManager {
	
	private EntryDAO entryDao;

	public EntryDAO getEntryDao() {
		return entryDao;
	}

	public void setEntryDao(EntryDAO entryDao) {
		this.entryDao = entryDao;
	}
	
	
	//logic
	
	public void save (Entrada e, Producto p) {
		this.entryDao.save(e, p);
	}
	
	public void update (Entrada e) {
		this.entryDao.update(e);
	}

	public ArrayList<Entrada> search(EntryAdapter ea) {
		return this.entryDao.search (ea);
	}

	public Entrada get(Integer idProducto) {
		return this.getEntryDao().get(idProducto);
	}

	public Entrada getIdProjectProduct(Integer idProyecto, Integer idProducto) {
		return this.getEntryDao().getIdProjectProduct(idProyecto, idProducto);
	}

	public void saveReasignedEntryProyect(Entrada entrada, Salida salida, Producto product) {
		this.entryDao.saveReasignedEntryProyect(entrada, salida, product);
	}


	
}
