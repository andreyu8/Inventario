package com.seidor.inventario.actionimages;

import org.zkoss.zul.Button;

public class UploadButton extends Button {
	
	private static final long serialVersionUID = 1L;

	public UploadButton(){
		setSclass("btn btn-transparent");		
		setIconSclass("z-icon-upload");
		setUpload("true");
		setStyle("margin-right: 3px; margin-left: 3px;");		
	}
	

}
