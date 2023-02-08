package com.seidor.inventario.actionimages;

import org.zkoss.zul.A;

public class Cancel extends A {

	private static final long serialVersionUID = 1L;
	
	public Cancel(){
		super();
		this.setIconSclass("z-icon-times");
		this.setSclass("cancelIcon");
		this.setStyle("margin-right: 3px; margin-left: 3px;");
		this.setTooltiptext("Cancelar");
	}
	
}
