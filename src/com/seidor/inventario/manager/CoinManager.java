package com.seidor.inventario.manager;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.seidor.inventario.dao.CoinDAO;
import com.seidor.inventario.model.TipoMoneda;

public class CoinManager {
	
	@Autowired
	private CoinDAO coinDao;
	
	public CoinDAO getCoinDao() {
		return coinDao;
	}

	public void setCoinDao(CoinDAO coinDao) {
		this.coinDao = coinDao;
	}

	//Business logic
	public TipoMoneda get(Integer id){
		return this.coinDao.get(id);
	}
	
	public ArrayList<TipoMoneda> getAll(){
		return this.coinDao.getAll();
	}
	
	public void save (TipoMoneda m) {
		this.coinDao.save(m);
	}
	
	public void update (TipoMoneda m) {
		this.coinDao.update(m);
	}
	
	public void delete (TipoMoneda m) {
		this.coinDao.delete(m);
	}

}
