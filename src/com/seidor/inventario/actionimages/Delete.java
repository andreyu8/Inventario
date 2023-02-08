package com.seidor.inventario.actionimages;

import org.zkoss.zul.A;

public class Delete extends A {

	private static final long serialVersionUID = 1L;
	
	public Delete(){
		super();
		this.setIconSclass("z-icon-trash-o");
		this.setSclass("deleteIcon");
		this.setStyle("margin-right: 3px; margin-left: 3px;");
		this.setTooltiptext("Eliminar");
	}
	
}
