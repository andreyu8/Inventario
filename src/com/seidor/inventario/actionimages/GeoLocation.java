package com.seidor.inventario.actionimages;

import org.zkoss.zul.A;

public class GeoLocation extends A {

	private static final long serialVersionUID = 1L;
	
	public GeoLocation(){
		super();
		this.setIconSclass("z-icon-map-marker");
//		this.setSclass("showIcon");
		this.setStyle("cursor:pointer;");
		this.setTooltiptext("modificar");
	}
	
}
