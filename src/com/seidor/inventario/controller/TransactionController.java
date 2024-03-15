package com.seidor.inventario.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.google.gson.Gson;
import com.seidor.inventario.adapter.TransactionAdapter;
import com.seidor.inventario.adapter.beans.CuentasBean;
import com.seidor.inventario.adapter.beans.DetailProductBean;
import com.seidor.inventario.adapter.beans.ProjectReportBean;
import com.seidor.inventario.adapter.listitem.DetailTransactionitemAdapter;
import com.seidor.inventario.adapter.render.DetailTransactionListitemRenderer;
import com.seidor.inventario.adapter.render.DetailTransactionOutputListitemRenderer;
import com.seidor.inventario.adapter.render.InvoiceComboitemRenderer;
import com.seidor.inventario.adapter.render.PurchaseOrderComboitemRenderer;
import com.seidor.inventario.adapter.search.TransactionSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.manager.DatosBancariosManager;
import com.seidor.inventario.manager.DetailOCManager;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.FoliosManager;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.manager.TransactionDetailManager;
import com.seidor.inventario.manager.TransactionManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Folios;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.model.TiposMovimiento;
import com.seidor.inventario.model.Ubicacion;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.DateFormatUtil;
import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.util.ReportUtil;
import com.seidor.inventario.util.SessionUtil;


public class TransactionController {
	
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private InvoiceManager invoiceManager;
	
	@Autowired
	private PurchaseOrderManager purchaseOrderManager;
	
	@Autowired
	private DetailOCManager detailOCManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private EntryManager entryManager;
	
	@Autowired
	private ProductManager productManager;
		
	@Autowired
	private FoliosManager foliosManager;
	
	@Autowired
	private TransactionDetailManager transactionDetailManager;
	
	@Autowired
	private DatosBancariosManager datosBancariosManager;
	
	@Autowired
	private ProjectManager projectManager;
	
	
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
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
		
	public DetailOCManager getDetailOCManager() {
		return detailOCManager;
	}

	public void setDetailOCManager(DetailOCManager detailOCManager) {
		this.detailOCManager = detailOCManager;
	}
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}
	
	public FoliosManager getFoliosManager() {
		return foliosManager;
	}

	public void setFoliosManager(FoliosManager foliosManager) {
		this.foliosManager = foliosManager;
	}
	
	public TransactionDetailManager getTransactionDetailManager() {
		return transactionDetailManager;
	}

	public void setTransactionDetailManager(TransactionDetailManager transactionDetailManager) {
		this.transactionDetailManager = transactionDetailManager;
	}
	
	public DatosBancariosManager getDatosBancariosManager() {
		return datosBancariosManager;
	}

	public void setDatosBancariosManager(DatosBancariosManager datosBancariosManager) {
		this.datosBancariosManager = datosBancariosManager;
	}
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	//logic bussines Entry
	public void searchEntry(Listbox lb, TransactionSearchAdapter tsa, NavigationState state){
		
		if (tsa.getNumeroFactura() != null) {
			Factura fa = this.invoiceManager.getNoFactura(tsa.getNumeroFactura());
			
			System.out.println("factura:"+fa);
			if (fa != null)
				tsa.setIdFactura(fa.getIdFactura());
			else
				tsa.setIdFactura(0);
		}	
		
		ArrayList<Movimientos> movimientos = this.transactionManager.searchEntry(tsa);
		
		ListModelList<Movimientos> model = new ListModelList<Movimientos>(movimientos);
		lb.setModel(model);

		tsa = null;
		
	}

	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Movimientos movimiento = (Movimientos)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(movimiento.getIdMovimiento());
			state.setUri("/WEB-INF/zul/inWarehouse/detail.zul");
			state.appendBreadCrumbsPath(movimiento.getFolio());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Movimientos in = (Movimientos)lb.getModel().getElementAt(i);
				detailList.add(in.getIdMovimiento());
				detailLabels.add(in.getFolio());
				if (in.getIdMovimiento().equals(movimiento.getIdMovimiento())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	public TransactionAdapter read (Integer idMovimiento) {
		TransactionAdapter ta= new TransactionAdapter();
		
		ta.setMovimientos(transactionManager.get(idMovimiento));
		ta.setDetalleMovimientos(transactionDetailManager.getDetails (ta.getMovimientos().getIdMovimiento()));
		ta.setFactura(invoiceManager.get(ta.getMovimientos().getFactura().getIdFactura()));
		ta.setOrdenCompra(purchaseOrderManager.get(ta.getMovimientos().getOrdenCompra().getIdOrdenCompra()));
		ta.setDetalleOrdenCompra(detailOCManager.getIdOCAll(ta.getMovimientos().getOrdenCompra().getIdOrdenCompra()));
		
		return ta;
	}
	
	public TransactionAdapter readForEdit (Integer idMovimiento) {
		TransactionAdapter ta= new TransactionAdapter();
		
		ta.setMovimientos(transactionManager.get(idMovimiento));
		ta.setDetalleMovimientos(transactionDetailManager.getDetails (ta.getMovimientos().getIdMovimiento()));
		ta.setFactura(invoiceManager.get(ta.getMovimientos().getFactura().getIdFactura()));
		ta.setOrdenCompra(purchaseOrderManager.get(ta.getMovimientos().getOrdenCompra().getIdOrdenCompra()));
		ta.setDetalleOrdenCompra(detailOCManager.getIdOCAll(ta.getMovimientos().getOrdenCompra().getIdOrdenCompra()));
		
		return ta;
	}
	
	public TransactionAdapter readForEditSalida (Integer idMovimiento) {
		TransactionAdapter ta= new TransactionAdapter();
		
		ta.setMovimientos(transactionManager.get(idMovimiento));
		
		return ta;
	}
	
	public void getDetailMovimientos (TransactionAdapter ta, Listbox lb, Label total) {
		ListModelList<DetalleMovimiento> model = new ListModelList<DetalleMovimiento>(ta.getDetalleMovimientos());
		lb.setModel(model);
		
		total.setValue(getDetalleTotal(ta.getDetalleMovimientos()));
	
	}
	
	private String getDetalleTotal(ArrayList<DetalleMovimiento> detalleMovimientos) {
		StringBuilder sb= new StringBuilder();

		BigDecimal total= new BigDecimal(0.0);
		BigDecimal totalTmp= new BigDecimal(0.0);
		
		for (DetalleMovimiento p: detalleMovimientos) {
			totalTmp = new BigDecimal(p.getCantidad()).multiply(p.getPrecioUnitario());
			total = total.add(totalTmp);
		}
		
		sb.append("SubTotal: $ " +NumberFormatUtil.format(total, 2));
		sb.append("\n");
		sb.append("IVA: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(0.16)), 2));
		sb.append("\n");
		sb.append("Total: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(1.16)), 2));
		
		return sb.toString();
	}

	public TransactionAdapter readForNew() {
		
		TransactionAdapter ta= new TransactionAdapter();

		ta.setMovimientos(new Movimientos());
		ta.setDetalleMovimientos(new ArrayList<DetalleMovimiento>());
		ta.setFactura(new Factura());
		ta.setOrdenCompra(new OrdenCompra());
		ta.setDetalleOrdenCompra(new ArrayList<DetalleOrdenCompra>());
		
		return ta;
	}
	
	public void loadPurchaseOrder(Combobox combo) {
		ArrayList<OrdenCompra> purchaseOrders = this.purchaseOrderManager.getAll();
		
		if (purchaseOrders != null) {
			ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(purchaseOrders);
			combo.setItemRenderer(new PurchaseOrderComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
	public void loadPurchaseOrderFact(Combobox parent, Combobox child){
		OrdenCompra parentCatalgs= parent.getSelectedItem().getValue();
		this.clearCombo(child, null, true);
		Integer id_almacen= SessionUtil.getSucursalId();
		ArrayList<Factura> munis = this.invoiceManager.getByParentId(parentCatalgs.getIdOrdenCompra(), id_almacen);
		ListModelList<Factura> model = new ListModelList<Factura>(munis);
		
		child.setItemRenderer(new InvoiceComboitemRenderer());
		child.setModel(model);
	}
	
	public void clearCombo(Combobox combo, String value, Boolean cleanModel){
		if (combo != null) {
			Constraint cons = combo.getConstraint();
			combo.setConstraint((Constraint)null);
			if (value != null && value.trim().length() > 0) combo.setValue(value);
			else combo.setValue(null);
			if (cleanModel != null && cleanModel) combo.setModel(new ListModelList<Object>());
			combo.setConstraint(cons);
		}
	}
	
	
	public void searchInvoice(TransactionAdapter ta, Component win) {
		Factura factura = this.invoiceManager.getNoFactura(ta.getFactura().getNumeroFactura());
		OrdenCompra ordenCompra = this.purchaseOrderManager.get(factura.getOrdenCompra().getIdOrdenCompra());
		ArrayList<DetalleOrdenCompra> detalleOrdenCompra= this.detailOCManager.getIdOCAll(ordenCompra.getIdOrdenCompra());
		
		
		ta.setFactura(factura);
		ta.setOrdenCompra(ordenCompra);
		ta.setDetalleOrdenCompra(detalleOrdenCompra);
		
		
		 Label ocPro = (Label) win.getFellowIfAny("ocPro");
		 ocPro.setValue(ta.getOrdenCompra().getProyecto().getNombre());
		 
		 Label ocCli = (Label) win.getFellowIfAny("ocCli");
		 ocCli.setValue(ta.getOrdenCompra().getCliente().getRfc()+" "+ta.getOrdenCompra().getCliente().getNombre());
		
		 Label ocEta = (Label) win.getFellowIfAny("ocEta");
		 ocEta.setValue(ta.getOrdenCompra().getEtapa().getEtapa());
		 
		 Label ocAre = (Label) win.getFellowIfAny("ocAre");
		 ocAre.setValue(ta.getOrdenCompra().getArea().getArea());
		 
		 Label ocTO = (Label) win.getFellowIfAny("ocTO");
		 ocTO.setValue(ta.getOrdenCompra().getTipoOrdenCompra().getOrdenCompra());
		
		 Div sectionOrden = (Div) win.getFellowIfAny("sectionOrden");
		 sectionOrden.setVisible(true);
		 
		 Listbox lb = (Listbox) win.getFellowIfAny("movlb");
		 
		 
		 
		ListModelList<DetailTransactionitemAdapter> model = new ListModelList<DetailTransactionitemAdapter>(DetailTransactionitemAdapter.getArray(getListDetalleMovimiento(detalleOrdenCompra)));
		lb.setModel(model);
		lb.setItemRenderer(new DetailTransactionListitemRenderer(SystemConstants.ENTRADA_COMPRA));
		
	}
	
	public void obtenerOrdenCompra (TransactionAdapter ta,  Component win) {
		
		Combobox cbOrdenCompra = (Combobox) win.getFellowIfAny("cboc");
		if (cbOrdenCompra != null &&cbOrdenCompra.getSelectedItem()!=null ) {
			ta.getFactura().setOrdenCompra((OrdenCompra) cbOrdenCompra.getSelectedItem().getValue());
		
			OrdenCompra oc=  purchaseOrderManager.get(ta.getFactura().getOrdenCompra().getIdOrdenCompra());
			ta.setOrdenCompra(oc);
			
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
			 
			 Listbox lb = (Listbox) win.getFellowIfAny("movlb");
			 
			ArrayList<DetalleOrdenCompra> detalleOrdenCompra= detailOCManager.getOC(ta.getFactura().getOrdenCompra().getIdOrdenCompra());
			ArrayList<DetalleMovimiento> detalleMovimiento=   getListDetalleMovimiento(detalleOrdenCompra);
			
			 ListModelList<DetailTransactionitemAdapter> model = new ListModelList<DetailTransactionitemAdapter>(DetailTransactionitemAdapter.getArray(detalleMovimiento));
				lb.setModel(model);
				lb.setItemRenderer(new DetailTransactionListitemRenderer(SystemConstants.ENTRADA_COMPRA));
			 
			//listDetail movimiento entrada
			SessionUtil.setSessionAttribute("listDetailTransactionENT", detalleMovimiento);	
		}
		
	}
	
	public void obtenerOrdenCompraSalida (TransactionAdapter ta,  Component win) {
		
		Combobox cbOrdenCompra = (Combobox) win.getFellowIfAny("cboc");
		if (cbOrdenCompra != null &&cbOrdenCompra.getSelectedItem()!=null ) {
			ta.getFactura().setOrdenCompra((OrdenCompra) cbOrdenCompra.getSelectedItem().getValue());
		
			OrdenCompra oc=  purchaseOrderManager.get(ta.getFactura().getOrdenCompra().getIdOrdenCompra());
			ta.setOrdenCompra(oc);
			
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

	private ArrayList<DetalleMovimiento> getListDetalleMovimiento(ArrayList<DetalleOrdenCompra> detalleOrdenCompra) {

		DetalleMovimiento mv = new DetalleMovimiento();
		ArrayList<DetalleMovimiento> lmv = new ArrayList<DetalleMovimiento>();
		
		for (DetalleOrdenCompra doc: detalleOrdenCompra) {
			mv = new DetalleMovimiento();
			
			if (doc.getCantidadFactura() < doc.getCantidad()) {
				mv.setIdDetalleMovimiento(0);
				mv.setCantidadTotal(doc.getCantidad());
				mv.setCantidad(doc.getCantidadFactura());
				mv.setPrecioUnitario(doc.getPrecioUnitario());
				mv.setProducto(doc.getProducto());
				mv.setFecha(new Date());
				mv.setEstatus(1);
				mv.setDetalleOrdenCompra(doc);
				mv.setTipoMoneda(doc.getTipoMoneda());
				mv.setPrecioUnitarioMxn(doc.getPrecioUnitarioMxn());
				
				lmv.add(mv);
			}	
			
		}
		
		
		return lmv;
	}

	

	@SuppressWarnings("unchecked")
	public void saveOrUpdateEntradasAlmacen(EditableListitem listitem, Integer idStatusMovimiento) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailTransactionitemAdapter adapter = (DetailTransactionitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DetalleMovimiento doc = adapter.getDetalleMovimiento();
		
		Component comp = listitem.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableDoublebox quantitybox = (IREditableDoublebox) devCant.getFirstChild();
		
		System.out.println("cantidad a ingresar: "+quantitybox.getValue() +" cantidad: "+doc.getCantidad());
		System.out.println("id del producto: "+doc.getProducto().getIdProducto());
		
		
		double compCant = doc.getCantidadTotal() - (doc.getCantidad() + quantitybox.getValue());
		Boolean flagUpdateOC = Boolean.FALSE; 
		
		if(compCant >= 0) {
			flagUpdateOC = Boolean.TRUE;
		} else
		if (compCant < 0) {
			throw new WrongValueException(quantitybox, "La cantidad de entrada a almacen tiene que ser menor o igual a la cantidad");
		}	
		
	    if (flagUpdateOC) {
	    	
	    	if (quantitybox.getValue() > 0) {
				doc.setCantidad(quantitybox.getValue());
				
				System.out.println("Realiza entrada");
				
				//actualizar el detalle de las movimientos
				if (doc.getIdDetalleMovimiento() == 0) {
					
					//listDetailTransaction
					ArrayList<DetalleMovimiento> listDetailTransactionENT = (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionENT");
					listDetailTransactionENT.set(selectedIndex, doc);
					SessionUtil.setSessionAttribute("listDetailTransactionENT", listDetailTransactionENT);	
					
				}	
				else {
					transactionDetailManager.update(doc);
				}	
	    	}	
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void saveTransactionEntry(TransactionAdapter ta, NavigationState state, Component win){
		
		Combobox occb = (Combobox) win.getFellowIfAny("cboc");
		if (occb != null && occb.getSelectedItem()!=null )
			ta.getFactura().setOrdenCompra((OrdenCompra) occb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(occb, "Debe de seleccionar una orden de compra");
		
		ta.getFactura().setEstatus(SystemConstants.FACTURA_ACTIVA);
		
		ta.getFactura().setSubtotal(new BigDecimal(0.0));
		ta.getFactura().setIva(new BigDecimal(0.0));
		ta.getFactura().setTotal(new BigDecimal(0.0));
		ta.getFactura().setDescripcion("");
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		ta.getFactura().setAlmacen(almacen);
		//se guarda la factura relacionada a la orden de compra
		
		//se genera el movimiento el detalle y se actualiza el folio
		TiposMovimiento tm= new TiposMovimiento();
		tm.setIdTipoMovimiento(SystemConstants.ENTRADA_COMPRA);
		ta.getMovimientos().setTiposMovimiento(tm);
		ta.getMovimientos().setFactura(ta.getFactura());
		ta.getMovimientos().setAlmacen(almacen);
		ta.getMovimientos().setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		ta.getMovimientos().setFecha(new Date());
		ta.getMovimientos().setOrdenCompra(ta.getFactura().getOrdenCompra());
		ta.getMovimientos().setProyecto(ta.getFactura().getOrdenCompra().getProyecto());
		ta.getMovimientos().setArea(ta.getFactura().getOrdenCompra().getArea());
		ta.getMovimientos().setEmpleado(ta.getFactura().getOrdenCompra().getEmpleado());
		
		Folios fte= foliosManager.getFolioMax (SystemConstants.ENTRADA_COMPRA);
		
		ta.getMovimientos().setFolio(fte.getAbrev()+"-"+fte.getConsecutivo());
		
		ArrayList<DetalleMovimiento> listDetailTransactionENT =  (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionENT");
		
		ArrayList<Entrada> listEntrada= new ArrayList<Entrada>();
		ArrayList<Producto> listProducto= new ArrayList<Producto>();
		Entrada e= new Entrada();
		Producto p= new Producto();
		
		for (DetalleMovimiento doc : listDetailTransactionENT) {
			
			e= new Entrada();
			p = productManager.get(doc.getProducto().getIdProducto());	
			 
			e.setIdEntrada(0);
			e.setCantidad(doc.getCantidad());
			e.setEmpleado(SessionUtil.getEmpleadoId());
			e.setFactura(ta.getFactura());
			e.setAlmacen(doc.getProducto().getAlmacen());
			e.setOrdenCompra(ta.getOrdenCompra());
			System.out.println("oc id: "+ta.getOrdenCompra().getIdOrdenCompra());
			Proyecto proyecto= new Proyecto();
			proyecto.setIdProyecto(ta.getOrdenCompra().getProyecto().getIdProyecto());
			e.setProyecto(proyecto);
			e.setProducto(p);
			Ubicacion u= new Ubicacion();
			u.setIdUbicacion(SessionUtil.getSucursalId());
			e.setUbicacion(u);
			UnidadMedida um= new UnidadMedida();
			um.setIdUnidadMedida(p.getUnidadMedida().getIdUnidadMedida());
			e.setUnidadMedida(um);
			e.setFecha(new Date());
			e.setPrecioUnitario(doc.getPrecioUnitario());
			e.setEstatus(SystemConstants.ENTRADA_POR_COMPRA);
			
			p.setCantidad(p.getCantidad() + doc.getCantidad());
			p.setPrecioCompra(doc.getPrecioUnitario());
			
			listEntrada.add(e);
			listProducto.add(p);
		}
		
		this.transactionManager.saveEntrada(ta.getFactura(), ta.getMovimientos(), listDetailTransactionENT, fte, listEntrada, listProducto);
		
		SessionUtil.setSessionAttribute("listDetailTransactionENT", new ArrayList<DetalleMovimiento>());
		
		state.setDetailIdentifier(ta.getMovimientos().getFolio());
		state.setUri("/WEB-INF/zul/inWarehouse/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ta.getMovimientos().getFolio());
		this.navigationControl.changeView(win, state);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void updateSalida(TransactionAdapter ta, NavigationState state, Component win){
		
		this.transactionManager.update(ta.getMovimientos());
			
		state.setDetailIdentifier(ta.getMovimientos().getFolio());
		state.setUri("/WEB-INF/zul/outWarehouse/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ta.getMovimientos().getFolio());
		this.navigationControl.changeView(win, state);
	}
		
	
	
	//reporte Entrada
	public void createReportData(TransactionAdapter ta, Component mtwin){
		
		ProjectReportBean pBeanReport= new ProjectReportBean();
		
		ArrayList<DetailProductBean> listadpb = new ArrayList<DetailProductBean>();
		DetailProductBean dpb = new DetailProductBean();
		int i=1;
		BigDecimal subtotal= new BigDecimal(0.0);
		BigDecimal subtotalTmp= new BigDecimal(0.0);
	
		for (DetalleMovimiento doc : ta.getDetalleMovimientos()) {
			dpb = new DetailProductBean();
			
			dpb.setItem(""+i);
			dpb.setArticulo(doc.getProducto().getCodigo());
			dpb.setDescripcion(doc.getProducto().getNombre());
			dpb.setCantidad(""+doc.getCantidad());
			dpb.setUnidad(doc.getProducto().getUnidadMedida().getDescripcion());
			dpb.setPrecioUnitario(NumberFormatUtil.format(doc.getPrecioUnitario(),2));
			dpb.setSubtotal(NumberFormatUtil.format(doc.getPrecioUnitario().multiply(new BigDecimal(doc.getCantidad())),2));
			
			subtotal = subtotalTmp.add(doc.getPrecioUnitario().multiply(new BigDecimal(doc.getCantidad())));
			
			listadpb.add(dpb);
			i++;
			subtotalTmp = subtotal;
		}
		
		pBeanReport.setSubtotal(NumberFormatUtil.format(subtotal,2));
		pBeanReport.setIva(NumberFormatUtil.format(subtotal.multiply(new BigDecimal(0.16)),2));
		pBeanReport.setTotal(NumberFormatUtil.format(subtotal.multiply(new BigDecimal(1.16)),2));
		
		
		CuentasBean ctasBean = new CuentasBean();
		ArrayList<CuentasBean> listCtasBean = new ArrayList <CuentasBean>();
		
		ArrayList<DatosBancarios> listDataBank = datosBancariosManager.getDatosBancarios(ta.getFactura().getOrdenCompra().getProveedor().getIdProveedor());
		
		for (DatosBancarios d : listDataBank ) {
			ctasBean = new CuentasBean();
			
			ctasBean.setBanco(d.getBanco());
			ctasBean.setCuenta(d.getCta());
			ctasBean.setClabe(d.getClabe());
			
			listCtasBean.add(ctasBean);
		}
		
		
		
		Gson gson = new Gson();
		pBeanReport.setJsonListProducts(gson.toJson(listadpb));
		pBeanReport.setJsonListCtas(gson.toJson(listCtasBean));	
		
		pBeanReport.setOrdenCompra(ta.getOrdenCompra());
		pBeanReport.setCliente(ta.getOrdenCompra().getCliente());
		pBeanReport.setProveedor(ta.getOrdenCompra().getProveedor());
		pBeanReport.setProyecto(ta.getOrdenCompra().getProyecto());
		pBeanReport.setFactura(ta.getFactura());
				
		
		exportToPdfEntrada (pBeanReport, ta);
	}	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void exportToPdfEntrada(ProjectReportBean pBeanReport, TransactionAdapter ta) {
	 
    	HashMap<String, Object> parametters = new HashMap<String, Object>();
		parametters.put("REPORT_TITLE", "ENTRADA ALMACÉN");
		parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
		
		parametters.put("folioMovimiento", ta.getMovimientos().getFolio());
		parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(pBeanReport.getJsonListProducts()));
		parametters.put("fecha", DateFormatUtil.getFormatedDate(pBeanReport.getOrdenCompra().getFecha(), true));
		parametters.put("nombre_proveedor", pBeanReport.getProveedor().getNombre());
		parametters.put("contacto_proveedor", "" );
		parametters.put("email_proveedor", pBeanReport.getProveedor().getEMail() );
		parametters.put("direccion_proveedor", pBeanReport.getProveedor().getDireccion());
		parametters.put("rfc_proveedor", pBeanReport.getProveedor().getRfc());
		parametters.put("telefono_proveedor", pBeanReport.getProveedor().getTelefono());
		
		parametters.put("nombre_cliente", pBeanReport.getCliente().getNombre());
		parametters.put("contacto_cliente", "");
		parametters.put("email_cliente", pBeanReport.getCliente().getEMail() );
		parametters.put("direccion_cliente", pBeanReport.getCliente().getDireccion());
		parametters.put("rfc_cliente", pBeanReport.getCliente().getRfc());
		parametters.put("telefono_cliente", pBeanReport.getCliente().getTelefono());
		
		
		parametters.put("subtotal", pBeanReport.getSubtotal());
		parametters.put("iva", pBeanReport.getIva());
		parametters.put("total", pBeanReport.getTotal());
		
		parametters.put("proyecto", pBeanReport.getProyecto().getNombre());
		parametters.put("cliente", pBeanReport.getCliente().getNombre());
		parametters.put("presupuesto", pBeanReport.getProyecto().getNumeroPresupuesto());
		parametters.put("etapa", pBeanReport.getOrdenCompra().getEtapa().getEtapa());
		parametters.put("area", pBeanReport.getOrdenCompra().getArea().getArea());
		parametters.put("noRequisicion", pBeanReport.getOrdenCompra().getNoRequisicion() );
		parametters.put("tipoCompra", pBeanReport.getOrdenCompra().getTipoOrdenCompra().getOrdenCompra());
		parametters.put("metodoPago", pBeanReport.getProveedor().getTipoPago().getTipoPago());
		parametters.put("diasCredito", pBeanReport.getProveedor().getDiasCredito() == null ? "0" : ""+pBeanReport.getProveedor().getDiasCredito());
		parametters.put("condicionesPago", "" );
		parametters.put("lugarEntrega", pBeanReport.getOrdenCompra().getLugarEntrega());
		parametters.put("observaciones", pBeanReport.getOrdenCompra().getDescripcion());
		parametters.put("responsable", pBeanReport.getOrdenCompra().getEmpleado().getNombre()+" "+pBeanReport.getOrdenCompra().getEmpleado().getAPaterno()+" "+pBeanReport.getOrdenCompra().getEmpleado().getAMaterno());
		
		parametters.put("noOrdenCompra", ""+pBeanReport.getOrdenCompra().getIdOrdenCompra());
		parametters.put("noFactura", pBeanReport.getFactura().getNumeroFactura());
		
		try {
			List dataSource = new ArrayList();
			dataSource.add(pBeanReport);
			String tname = "reporteEntrada";
			Media mediaReport = ReportUtil.generateReport(dataSource, "entradaReporte.jasper", ReportUtil.TASK_PDF, parametters, tname);
			org.zkoss.zul.Filedownload.save(mediaReport);
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	public static List<HashMap<String, Object>> getListFromJsonGeneric(String json) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
		try {
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
				String jsondata = jsonarray.get(i).toString();
				HashMap<String, Object> map = new HashMap<String, Object>();
				JSONObject jObject = new JSONObject(jsondata);
				Iterator<?> keys = jObject.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();
					String value = jObject.getString(key);
					map.put(key, value);
				}
				array.add(map);
			}
		} catch (Exception ex) {
			ex.getCause();
		}
		return array;
	}
	
	//logic bussines Output
	public void searchOutput(Listbox lb, TransactionSearchAdapter tsa, NavigationState state){
		ArrayList<Movimientos> movimientos = this.transactionManager.searchOutput(tsa);
		
		ListModelList<Movimientos> model = new ListModelList<Movimientos>(movimientos);
		lb.setModel(model);
	}

	public void showOutput(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Movimientos movimiento = (Movimientos)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(movimiento.getIdMovimiento());
			state.setUri("/WEB-INF/zul/outWarehouse/detail.zul");
			state.appendBreadCrumbsPath(movimiento.getFolio());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Movimientos in = (Movimientos)lb.getModel().getElementAt(i);
				detailList.add(in.getIdMovimiento());
				detailLabels.add(in.getFolio());
				if (in.getIdMovimiento().equals(movimiento.getIdMovimiento())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	
	public TransactionAdapter readForNewS() {
		
		TransactionAdapter ta= new TransactionAdapter();

		ta.setMovimientos(new Movimientos());
		ta.getMovimientos().setProyecto(new Proyecto());
		ta.getMovimientos().setArea(new Area());
		ta.getMovimientos().setEmpleado(new Empleado());
		ta.setDetalleMovimientos(new ArrayList<DetalleMovimiento>());
		ta.setFactura(new Factura());
		ta.setOrdenCompra(new OrdenCompra());
		ta.setDetalleOrdenCompra(new ArrayList<DetalleOrdenCompra>());
		
		ta.setEntrada(new Entrada());
		ta.setSalida(new Salida());
		ta.setProducto(new Producto());
		ta.setProductos(new ArrayList<Producto>());
		
		return ta;
	}
	
	public void validaProducto (TransactionAdapter ta, NavigationState state, Component win){
	
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) {
			ta.getSalida().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		
			Proyecto pr=  this.projectManager.get(ta.getSalida().getProyecto().getIdProyecto());
			
			Entrada e = getCantidadEntrada(entryManager.getIdProjectProduct (pr.getIdProyecto(), ta.getProducto().getIdProducto()));
			
			ta.setEntrada(e);
			
			
			if (e == null) 
				throw new WrongValueException(prcb, "No existe el producto en la entrada del proyecto!");
		
		}
		
	}	
	
	private Entrada getCantidadEntrada(ArrayList<Entrada> idProjectProduct) {
		double cantidad =0 ;
		Entrada entrada = new Entrada();
		
		for (Entrada e : idProjectProduct) {
			cantidad = cantidad + e.getCantidad();
			entrada = e;
		}
		entrada.setCantidad(cantidad);
		return entrada;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProduct(Combobox pcb,  Listbox lbr) {
		
		if (pcb == null || pcb.getSelectedItem() == null) {			
			throw new WrongValueException(pcb, "Debes de seleccionar un producto.");
		} else {
			
			Producto productSelect = (Producto) pcb.getSelectedItem().getValue();
			
			DetalleMovimiento  dm= new DetalleMovimiento();
			dm.setIdDetalleMovimiento(0);
			dm.setProducto(productSelect);
			dm.setCantidadTotal(productSelect.getCantidad());
			dm.setCantidad(0.0);
			dm.setPrecioUnitario(productSelect.getPrecioCompra());
			
			
			ListModelList<DetailTransactionitemAdapter> model = (ListModelList) lbr.getModel();
			DetailTransactionitemAdapter adapter = new DetailTransactionitemAdapter(dm);
			model.add(adapter);
			
		}	
									
	}
	
	
	public void loadProducts(Listbox lb, Label idtotalcoin, boolean edit){
		
		ArrayList<DetalleMovimiento> ldm= new ArrayList<DetalleMovimiento>();
		DetalleMovimiento dm= new DetalleMovimiento();
		ldm.add(dm);
		
		SessionUtil.setSessionAttribute("listDetailTransactionSAL", new ArrayList<DetalleMovimiento>());
		
		ListModelList<DetailTransactionitemAdapter> lml = new ListModelList<DetailTransactionitemAdapter>();
		if(edit) {
			lb.setItemRenderer(new DetailTransactionOutputListitemRenderer(SystemConstants.SALIDA_VALE));
		}
		lb.setModel(lml);
		
	}

	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateSalidasAlmacen(EditableListitem listitem, Integer idStatusMovimiento) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailTransactionitemAdapter adapter = (DetailTransactionitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DetalleMovimiento doc = adapter.getDetalleMovimiento();
		
		Component comp = listitem.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableDoublebox quantitybox = (IREditableDoublebox) devCant.getFirstChild();
		
		System.out.println("cantidad salida: "+quantitybox.getValue() +" cantidad: "+doc.getCantidad());
		System.out.println("id del producto: "+doc.getProducto().getIdProducto());
		
		
		double compCant = doc.getCantidadTotal() - (doc.getCantidad() + quantitybox.getValue());
		Boolean flagUpdateOC = Boolean.FALSE; 
		
		if(compCant >= 0) {
			flagUpdateOC = Boolean.TRUE;
		} else
		if (compCant < 0) {
			throw new WrongValueException(quantitybox, "La cantidad de salida a almacen tiene que ser menor o igual a la cantidad existente");
		}	
		
	    if (flagUpdateOC) {
			doc.setCantidad(quantitybox.getValue());
			doc.setFecha(new Date());
			doc.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
			
			System.out.println("Realiza salida");
			
			//actualizar el detalle de las movimientos
			if (doc.getIdDetalleMovimiento() == 0) {
				
				//listDetailTransaction
				ArrayList<DetalleMovimiento> listDetailTransactionSAL = (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionSAL");
				listDetailTransactionSAL.add(doc);
				SessionUtil.setSessionAttribute("listDetailTransactionSAL", listDetailTransactionSAL);	
				
			}	
			else {
				transactionDetailManager.update(doc);
			}	
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void saveTransactionOutput(TransactionAdapter ta, NavigationState state, Component win){
					
		Combobox areacb = (Combobox) win.getFellowIfAny("areacb");
		if (areacb != null && areacb.getSelectedItem()!=null )
			ta.getMovimientos().setArea((Area) areacb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areacb, "Debe de seleccionar un area");
		
		Combobox areaempcb = (Combobox) win.getFellowIfAny("areaempcb");
		if (areaempcb != null && areaempcb.getSelectedItem()!=null )
			ta.getMovimientos().setEmpleado((Empleado) areaempcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areaempcb, "Debe de seleccionar un empleado");
		
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null )
			ta.getMovimientos().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prcb, "Debe de seleccionar un proyecto");
		
					
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		
		
		//se genera el movimiento el detalle y se actualiza el folio
		TiposMovimiento tm= new TiposMovimiento();
		tm.setIdTipoMovimiento(SystemConstants.SALIDA_VALE);
		ta.getMovimientos().setTiposMovimiento(tm);
		ta.getMovimientos().setAlmacen(almacen);
		ta.getMovimientos().setProyecto(ta.getMovimientos().getProyecto());
		ta.getMovimientos().setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		ta.getMovimientos().setFecha(new Date());
						
		Folios fte= foliosManager.getFolioMax (SystemConstants.SALIDA_VALE);
		
		ta.getMovimientos().setFolio(fte.getAbrev()+"-"+fte.getConsecutivo());
		
		ArrayList<DetalleMovimiento> listDetailTransactionSAL =  (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionSAL");
		
		ArrayList<Salida> listSalida= new ArrayList<Salida>();
		ArrayList<Producto> listProducto= new ArrayList<Producto>();
		Salida s= new Salida();
		Producto p= new Producto();
		
		for (DetalleMovimiento doc : listDetailTransactionSAL) {
			
			s= new Salida();
			p = productManager.get(doc.getProducto().getIdProducto());	
			 
			s.setIdSalida(0);
			s.setCantidad(doc.getCantidad());
			s.setEmpleado(SessionUtil.getEmpleadoId());
			Proyecto proyecto= new Proyecto();
			proyecto.setIdProyecto(ta.getMovimientos().getProyecto().getIdProyecto());
			s.setProyecto(proyecto);
			s.setProducto(p);
			UnidadMedida um= new UnidadMedida();
			um.setIdUnidadMedida(p.getUnidadMedida().getIdUnidadMedida());
			s.setUnidadMedida(um);
			s.setFecha(new Date());
			s.setTipoTrabajo(null);
			s.setEstatus(SystemConstants.SALIDA_POR_VALE);
			
			p.setCantidad(p.getCantidad() - doc.getCantidad());
			p.setPrecioCompra(doc.getPrecioUnitario());
			
			listSalida.add(s);
			listProducto.add(p);
		}
		
		this.transactionManager.saveSalida(ta.getMovimientos(), listDetailTransactionSAL, fte, listSalida, listProducto);
		
		SessionUtil.setSessionAttribute("listDetailTransactionSAL", new ArrayList<DetalleMovimiento>());
		
		state.setDetailIdentifier(ta.getMovimientos().getFolio());
		state.setUri("/WEB-INF/zul/outWarehouse/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ta.getMovimientos().getFolio());
		this.navigationControl.changeView(win, state);
	}
	
	
	public TransactionAdapter readSalidas (Integer idMovimiento) {
		TransactionAdapter ta= new TransactionAdapter();
		
		ta.setMovimientos(transactionManager.getSalida(idMovimiento));
		ta.setDetalleMovimientos(transactionDetailManager.getDetails (ta.getMovimientos().getIdMovimiento()));
		
		return ta;
	}
	
	public void createReportValeSalida (TransactionAdapter ta, Component mtwin){
		
		ProjectReportBean pBeanReport= new ProjectReportBean();
		
		ArrayList<DetailProductBean> listadpb = new ArrayList<DetailProductBean>();
		DetailProductBean dpb = new DetailProductBean();
		int i=1;
		BigDecimal subtotal= new BigDecimal(0.0);
		BigDecimal subtotalTmp= new BigDecimal(0.0);
	
		for (DetalleMovimiento doc : ta.getDetalleMovimientos()) {
			dpb = new DetailProductBean();
			
			dpb.setItem(""+i);
			dpb.setArticulo(doc.getProducto().getCodigo());
			dpb.setDescripcion(doc.getProducto().getNombre());
			dpb.setCantidad(""+doc.getCantidad());
			dpb.setUnidad(doc.getProducto().getUnidadMedida().getDescripcion());
			dpb.setPrecioUnitario(NumberFormatUtil.format(doc.getPrecioUnitario(),2));
			dpb.setSubtotal(NumberFormatUtil.format(doc.getPrecioUnitario().multiply(new BigDecimal(doc.getCantidad())),2));
			
			subtotal = subtotalTmp.add(doc.getPrecioUnitario().multiply(new BigDecimal(doc.getCantidad())));
			
			listadpb.add(dpb);
			i++;
			subtotalTmp = subtotal;
		}
		
		pBeanReport.setSubtotal(NumberFormatUtil.format(subtotal,2));
		pBeanReport.setIva(NumberFormatUtil.format(subtotal.multiply(new BigDecimal(0.16)),2));
		pBeanReport.setTotal(NumberFormatUtil.format(subtotal.multiply(new BigDecimal(1.16)),2));
		
		
		
		Gson gson = new Gson();
		pBeanReport.setJsonListProducts(gson.toJson(listadpb));
		
		pBeanReport.setProyecto(ta.getOrdenCompra().getProyecto());
				
		
		exportToPdfSalida (pBeanReport, ta);
	}	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void exportToPdfSalida(ProjectReportBean pBeanReport, TransactionAdapter ta) {
	 
    	HashMap<String, Object> parametters = new HashMap<String, Object>();
		parametters.put("REPORT_TITLE", "SALIDA DE MATERIAL");
		parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
		
		parametters.put("folioMovimiento", ta.getMovimientos().getFolio());
		parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(pBeanReport.getJsonListProducts()));
		parametters.put("fecha", DateFormatUtil.getFormatedDate(ta.getMovimientos().getFecha(), true));
		
		parametters.put("ordenTrabajo", ta.getMovimientos().getOrdenTrabajo());
		parametters.put("cveMueble", ta.getMovimientos().getCveMueble());
		
		
		parametters.put("subtotal", pBeanReport.getSubtotal());
		parametters.put("iva", pBeanReport.getIva());
		parametters.put("total", pBeanReport.getTotal());
		
		parametters.put("proyecto", ta.getMovimientos().getProyecto().getNombre());
		parametters.put("area", ta.getMovimientos().getArea().getArea());
		parametters.put("responsable", ta.getMovimientos().getEmpleado().getNombre()+" "+ta.getMovimientos().getEmpleado().getAPaterno()+" "+ta.getMovimientos().getEmpleado().getAMaterno());
		
		
		try {
			List dataSource = new ArrayList();
			dataSource.add(pBeanReport);
			String tname = "reporteSalida";
			Media mediaReport = ReportUtil.generateReport(dataSource, "valeMaterial.jasper", ReportUtil.TASK_PDF, parametters, tname);
			org.zkoss.zul.Filedownload.save(mediaReport);
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
