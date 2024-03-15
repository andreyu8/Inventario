package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.CoinComboitemRenderer;
import com.seidor.inventario.adapter.render.StatusTypeOrderComboitemRenderer;
import com.seidor.inventario.manager.CoinManager;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.TipoMoneda;
import com.seidor.inventario.navigation.NavigationControl;

public class CoinController {
	
	@Autowired
	private CoinManager coinManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	public CoinManager getCoinManager() {
		return coinManager;
	}

	public void setCoinManager(CoinManager coinManager) {
		this.coinManager = coinManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}


	public void	loadTypeCion(Combobox combo) {
		ArrayList<TipoMoneda> coinType = this.coinManager.getAll();
		if (coinType != null) {
			ListModelList<TipoMoneda> model = new ListModelList<TipoMoneda>(coinType);
			combo.setItemRenderer(new CoinComboitemRenderer());
			combo.setModel(model);
		}
	}
	

}
