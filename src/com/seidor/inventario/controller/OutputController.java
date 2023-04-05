package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;

import com.seidor.inventario.adapter.OutputAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.OutputManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.model.TipoTrabajo;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;

public class OutputController {
	
	
	@Autowired
	private OutputManager outputManager;
	
	@Autowired
	private ProductManager productManager; 
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private ProjectManager projectManager;
	
	@Autowired
	private EntryManager entryManager;

	
	public OutputManager getOutputManager() {
		return outputManager;
	}

	public void setOutputManager(OutputManager outputManager) {
		this.outputManager = outputManager;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}
	

	//logic
	public OutputAdapter read(Integer productId){
		OutputAdapter o = new OutputAdapter();
	
		Producto product = this.productManager.get(productId);
		o.setProducto(product);
		Salida salida = new Salida ();
		salida.setProyecto(new Proyecto());
		salida.setProducto(product);
		salida.setTipoTrabajo(new TipoTrabajo());
		o.setEntrada(new Entrada());
		o.setSalida(salida);
				
		return o;
	}
	
	
	
	public void save(OutputAdapter oa, NavigationState state, Component win){
		
		Combobox cbTipotrabajo = (Combobox) win.getFellowIfAny("cbtt");
		if (cbTipotrabajo != null && cbTipotrabajo.getSelectedItem()!=null )
			oa.getSalida().setTipoTrabajo((TipoTrabajo) cbTipotrabajo.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbTipotrabajo, "Debe de seleccionar una ubicación");
		
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null )
			oa.getSalida().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbTipotrabajo, "Debe de seleccionar un proyecto");
		
		
		oa.getSalida().setEmpleado(oa.getEntrada().getEmpleado());
		oa.getSalida().setUnidadMedida(oa.getEntrada().getUnidadMedida());
		oa.getSalida().setFecha(new Date());
		oa.getSalida().setEstatus(SystemConstants.SALIDA_POR_VALE);
		
		Intbox ibcantidad = (Intbox) win.getFellowIfAny("ibcantidad");
		
		
		Entrada entryProduct = getCantidadEntrada(entryManager.getIdProjectProduct(oa.getSalida().getProyecto().getIdProyecto(),  oa.getSalida().getProducto().getIdProducto()));
		Salida outputProduct = getCantidadSalida (entryManager.getIdProjectProductS(oa.getSalida().getProyecto().getIdProyecto(),  oa.getSalida().getProducto().getIdProducto()));
		
		int cantidadProducto = entryProduct.getCantidad() - outputProduct.getCantidad();

		//resta a productos la cantidad
		oa.getSalida().getProducto().setCantidad(cantidadProducto - oa.getSalida().getCantidad());
				
		
		System.out.println("Cantidad entrada: "+cantidadProducto);
		System.out.println("Cantidad salida: "+oa.getSalida().getProducto().getCantidad());
		
		if (ibcantidad.getValue() <= cantidadProducto) 
			this.outputManager.save(oa.getSalida(), oa.getProducto());
		else
			throw new WrongValueException(ibcantidad, "No puede sacar mas productos de los existentes!!!!!");
		
		/*
		 * Se notifica por correo
		 * if (oa.getSalida().getProducto().getCantidad() < oa.getSalida().getProducto().getMinimo()) 
			throw new WrongValueException(ibcantidad, "Se ha revasado el minimo requerido favor de validar!");*/
		
		
		state.setDetailIdentifier(oa.getProducto().getIdProducto());
		state.setUri("/WEB-INF/zul/product/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(oa.getProducto().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	
	
	private Salida getCantidadSalida(ArrayList<Salida> idProjectProduct) {
		int cantidad =0 ;
		Salida salida = new Salida();
		
		for (Salida s : idProjectProduct) {
			cantidad = cantidad + s.getCantidad();
			salida = s;
		}
		salida.setCantidad(cantidad);
		return salida;
	}

	private Entrada getCantidadEntrada(ArrayList<Entrada> idProjectProduct) {
		int cantidad =0 ;
		Entrada entrada = new Entrada();
		
		for (Entrada e : idProjectProduct) {
			cantidad = cantidad + e.getCantidad();
			entrada = e;
		}
		entrada.setCantidad(cantidad);
		return entrada;
	}

	public void validaProducto (OutputAdapter oa, NavigationState state, Component win){
	
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) {
			oa.getSalida().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		
			Proyecto pr=  this.projectManager.get(oa.getSalida().getProyecto().getIdProyecto());
			
			Entrada e = getCantidadEntrada(entryManager.getIdProjectProduct (pr.getIdProyecto(), oa.getProducto().getIdProducto()));
			
			oa.setEntrada(e);
			
			
			if (e == null) 
				throw new WrongValueException(prcb, "No existe el producto en la entrada del proyecto!");
		
		}
		
	}	

}
