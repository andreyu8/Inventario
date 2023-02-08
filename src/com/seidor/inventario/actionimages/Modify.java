package com.seidor.inventario.actionimages;

import org.zkoss.zul.A;

public class Modify extends A {

	private static final long serialVersionUID = 1L;
	
	public Modify(){
		
		super();
		this.setIconSclass("z-icon-pencil");
//		this.setSclass("editIcon");
		this.setStyle("text-align: left; padding-bottom: 2px; margin-bottom: 2px;font-size: 18px;");
		this.setTooltiptext("Modificar");
	}
	
}
