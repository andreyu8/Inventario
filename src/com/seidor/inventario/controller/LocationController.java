package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;

import com.seidor.inventario.adapter.render.LocationComboitemRenderer;
import com.seidor.inventario.manager.LocationManager;
import com.seidor.inventario.model.Ubicacion;

public class LocationController {
	
	
	private LocationManager locationManager;
	
	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}





	public void loadLocation(Combobox combo) {
		ArrayList<Ubicacion> locations = this.locationManager.getAll();
		if (locations != null) {
			ListModelList<Ubicacion> model = new ListModelList<Ubicacion>(locations);
			combo.setItemRenderer(new LocationComboitemRenderer());
			combo.setModel(model);
		}
	}

}
