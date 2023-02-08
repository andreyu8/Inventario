package com.seidor.inventario.actionimages;

import org.zkoss.zul.A;

public class Accept extends A {

	private static final long serialVersionUID = 1L;
	
	public Accept(){
		super();
		this.setIconSclass("z-icon-check");
		this.setSclass("acceptIcon");
		this.setStyle("margin-right: 3px; margin-left: 3px;");
		this.setTooltiptext("Aceptar");
	}
	
}
