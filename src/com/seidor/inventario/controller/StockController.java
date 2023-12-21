package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.EntryAdapter;
import com.seidor.inventario.adapter.StockAdapter;
import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.manager.EmployeeManager;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.manager.StockManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.Ubicacion;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.DateFormatUtil;
import com.seidor.inventario.util.NumberFormatUtil;

public class StockController {
	

	@Autowired
	private EntryManager entryManager;
	
	@Autowired
	private StockManager stockManager;
	
	@Autowired
	private ProductManager productManager; 
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private ProjectManager projectManager;
	
	@Autowired
	private EmployeeManager employeeManager;
	
	@Autowired
	private InvoiceManager invoiceManager;
	
	@Autowired
	private PurchaseOrderManager purchaseOrderManager;
	
	
	
	
	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}

	public StockManager getStockManager() {
		return stockManager;
	}

	public void setStockManager(StockManager stockManager) {
		this.stockManager = stockManager;
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
	

	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}

	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}

	public InvoiceManager getInvoiceManager() {
		return invoiceManager;
	}

	public void setInvoiceManager(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
	}

	public PurchaseOrderManager getPurchaseOrderManager() {
		return purchaseOrderManager;
	}

	public void setPurchaseOrderManager(PurchaseOrderManager purchaseOrderManager) {
		this.purchaseOrderManager = purchaseOrderManager;
	}

	// logic search
	public void search(Listbox lb, ProductSearchAdapter psa, NavigationState state){
		ArrayList<Producto> entriestock = this.stockManager.search(psa);
		
		ListModelList<Producto> model = new ListModelList<Producto>(entriestock);
		lb.setModel(model);
	}
	
	
	public void inProduct(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Producto product = (Producto)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(product.getIdProducto());
			state.setUri("/WEB-INF/zul/stock/inProduct.zul");
			state.appendBreadCrumbsPath(product.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Producto p = (Producto)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProducto());
				detailLabels.add(p.getNombre());
				if (p.getIdProducto().equals(product.getIdProducto())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	
	
	//logic
	public StockAdapter read(Integer productId){
		StockAdapter sa = new StockAdapter();
	
		Producto product = this.productManager.get(productId);
		sa.setProducto(product);
		Entrada entrada= new Entrada ();
		entrada.setProducto(product);
		entrada.setAlmacen(new Almacen());
		entrada.setUbicacion(new Ubicacion());
		entrada.setUnidadMedida(new UnidadMedida());
		entrada.setOrdenCompra(new OrdenCompra());
		entrada.setEmpleado(new Empleado());
		entrada.setFactura(new Factura());
		sa.setEntrada(entrada);
		MovimientosStock ms= new MovimientosStock();
		ms.setProducto(product);
		ms.setProyecto(new Proyecto());
		sa.setMovimientosStock(ms);
		
		return sa;
	}
	
	
	
	
	public void save(StockAdapter sa, NavigationState state, Component win){
		
		Combobox cbUnidad = (Combobox) win.getFellowIfAny("cbum");
		if (cbUnidad != null && cbUnidad.getSelectedItem()!=null )
			sa.getEntrada().setUnidadMedida((UnidadMedida) cbUnidad.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbUnidad, "Debe de seleccionar un Unidad de medida");
		
		Combobox cbAlmacen = (Combobox) win.getFellowIfAny("cbal");
		if (cbAlmacen != null && cbAlmacen.getSelectedItem()!=null )
			sa.getEntrada().setAlmacen((Almacen) cbAlmacen.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbAlmacen, "Debe de seleccionar un almacén");
				
		Combobox cbEmpleado = (Combobox) win.getFellowIfAny("cbemp");
		if (cbEmpleado != null && cbEmpleado.getSelectedItem()!=null )
			sa.getEntrada().setEmpleado((Empleado) cbEmpleado.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbEmpleado, "Debe de seleccionar un empleado");
		
		Combobox cbFactura = (Combobox) win.getFellowIfAny("cbfa");
		if (cbFactura != null && cbFactura.getSelectedItem()!=null )
			sa.getEntrada().setFactura((Factura) cbFactura.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbFactura, "Debe de seleccionar una factura");
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null )  {
			sa.getEntrada().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
			sa.getMovimientosStock().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		}else 
			throw new WrongValueException(cbFactura, "Debe de seleccionar un proyecto");
			
			
		Combobox cbOrdenCompra = (Combobox) win.getFellowIfAny("cboc");
		if (cbOrdenCompra != null &&cbOrdenCompra.getSelectedItem()!=null )
			sa.getEntrada().setOrdenCompra((OrdenCompra) cbOrdenCompra.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbOrdenCompra, "Debe de seleccionar una orden de compra");
		
		Combobox cbUbicacion = (Combobox) win.getFellowIfAny("cbub");
		if (cbUbicacion != null && cbUbicacion.getSelectedItem()!=null )
			sa.getEntrada().setUbicacion((Ubicacion) cbUbicacion.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbUbicacion, "Debe de seleccionar una ubicación");
		
		sa.getEntrada().setFecha(new Date());
		sa.getEntrada().setEstatus(SystemConstants.ENTRADA_POR_REASIGNACION);
		
		//suma a productos la cantidad
		sa.getProducto().setStock(sa.getProducto().getStock() - sa.getEntrada().getCantidad());
		sa.getProducto().setCantidad(sa.getProducto().getCantidad() + sa.getEntrada().getCantidad());
		
		sa.getMovimientosStock().setFecha(new Date());
		sa.getMovimientosStock().setTipo(SystemConstants.TIPO_SALIDA);
		sa.getMovimientosStock().setCantidad(sa.getEntrada().getCantidad());
		sa.getMovimientosStock().setProducto(sa.getProducto());
		sa.getMovimientosStock().setEstatus(SystemConstants.ESTATUS_STOCK_ACTIVO);
		
		this.stockManager.save(sa.getEntrada(), sa.getMovimientosStock(), sa.getProducto());
		
		
		state.setDetailIdentifier(sa.getProducto().getIdProducto());
		state.setUri("/WEB-INF/zul/stock/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(sa.getProducto().getNombre());
		this.navigationControl.changeView(win, state);
	}
		
		
	
	
	
	
	public void getProyecto (StockAdapter sa,  Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) {
			sa.getEntrada().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		
			Proyecto pr=  projectManager.get(sa.getEntrada().getProyecto().getIdProyecto());
			
			 Label ocPro = (Label) win.getFellowIfAny("nomPro");
			 ocPro.setValue(pr.getNombre());
			 
			
			 Div sectionOrden = (Div) win.getFellowIfAny("sectionProyecto");
			 sectionOrden.setVisible(true);
			
		
		}
		
	}
	
	
	public void getDataEmployee (StockAdapter ea,  Component win) {
		
		Combobox cbEmpleado = (Combobox) win.getFellowIfAny("cbemp");
		if (cbEmpleado != null && cbEmpleado.getSelectedItem()!=null ) {
			ea.getEntrada().setEmpleado((Empleado) cbEmpleado.getSelectedItem().getValue());
		
		
			Empleado e= employeeManager.get(ea.getEntrada().getEmpleado().getIdEmpleado());
			
			 Div divDataEmployee = (Div) win.getFellowIfAny("sectionEmployee");
			 divDataEmployee.setVisible(true);
			 			 
			 
			 Label labelNom = (Label) win.getFellowIfAny("empName");
			 labelNom.setValue(e.getNombre().trim()+" "+e.getAPaterno().trim()+" "+e.getAMaterno().trim());
			 
			  Label empRFC = (Label) win.getFellowIfAny("empRFC");
			  empRFC.setValue(e.getRfc());
			  
			  
			  Label empCURP = (Label) win.getFellowIfAny("empCURP");
			  empCURP.setValue(e.getCurp());
			  
			  
			  Label empNoEmpleado = (Label) win.getFellowIfAny("empNoEmpleado");
			  empNoEmpleado.setValue(e.getNumeroEmpleado());
			  
			  
			  Label empTelefono = (Label) win.getFellowIfAny("empTelefono");
			  empTelefono.setValue(e.getTelefono()+" Ext. "+e.getExtension());
			  
			  Label empCel = (Label) win.getFellowIfAny("empCel");
			  empCel.setValue(e.getCelular());
			  
			  Label empEmail = (Label) win.getFellowIfAny("empEmail");
			  empEmail.setValue(e.getEMail());
			  
			  
			  Label empNoSS = (Label) win.getFellowIfAny("empNoSS");
			  empNoSS.setValue(e.getNumeroSegSocial());
		}
		
		
	}
	
	
	
	public void getDataInvoice (StockAdapter ea,  Component win) {
		
		Combobox cbFactura = (Combobox) win.getFellowIfAny("cbfa");
		if (cbFactura != null && cbFactura.getSelectedItem()!=null ) {
			ea.getEntrada().setFactura((Factura) cbFactura.getSelectedItem().getValue());
		
		
			 Factura fa= invoiceManager.get(ea.getEntrada().getFactura().getIdFactura());
			 
							 
			 Label faDesc = (Label) win.getFellowIfAny("faDesc");
			 faDesc.setValue(fa.getDescripcion());
			 
			 
			 Label faFecha = (Label) win.getFellowIfAny("faFecha");
			 faFecha.setValue(DateFormatUtil.getFormatedDate(fa.getFecha(), Boolean.FALSE));
			 
			 /*Label faRFC = (Label) win.getFellowIfAny("faRFC");
			 faRFC.setValue(fa.getProveedor().getRfc());
			 
			 Label faNom = (Label) win.getFellowIfAny("faNom");
			 faNom.setValue(fa.getProveedor().getNombre());*/
			 
			 Label faTotal = (Label) win.getFellowIfAny("faTotal");
			 faTotal.setValue(NumberFormatUtil.format(fa.getTotal(), 2));
			
			 
			 Div divDataInvoice = (Div) win.getFellowIfAny("sectionFactura");
			 divDataInvoice.setVisible(true);
			
		}	 
		 
	}
	
	
	public void getOrdenCompra (StockAdapter ea,  Component win) {
		
		Combobox cbOrdenCompra = (Combobox) win.getFellowIfAny("cboc");
		if (cbOrdenCompra != null &&cbOrdenCompra.getSelectedItem()!=null ) {
			ea.getEntrada().setOrdenCompra((OrdenCompra) cbOrdenCompra.getSelectedItem().getValue());
		
			OrdenCompra oc=  purchaseOrderManager.get(ea.getEntrada().getOrdenCompra().getIdOrdenCompra());
			
			 Label ocPro = (Label) win.getFellowIfAny("ocPro");
			 ocPro.setValue(oc.getProyecto().getNombre());
			 
			 Label ocCli = (Label) win.getFellowIfAny("ocCli");
			 ocCli.setValue(oc.getCliente().getRfc()+" "+oc.getCliente().getNombre());
			
			 Label ocEta = (Label) win.getFellowIfAny("ocEta");
			 ocEta.setValue(oc.getEtapa().getEtapa());
			 
			 Label ocAre = (Label) win.getFellowIfAny("ocAre");
			 ocAre.setValue(oc.getArea().getArea());
			 
			 Label ocTO = (Label) win.getFellowIfAny("ocTO");
			 ocTO.setValue(oc.getTipoOrdenCompra().getOrdenCompra());
			
			 Div sectionOrden = (Div) win.getFellowIfAny("sectionOrden");
			 sectionOrden.setVisible(true);
			
		
		}
		
	}
	
	
	



}
