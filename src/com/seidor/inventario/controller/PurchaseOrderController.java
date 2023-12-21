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
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.google.gson.Gson;
import com.seidor.inventario.adapter.ProductAdapter;
import com.seidor.inventario.adapter.PurcharseAdapter;
import com.seidor.inventario.adapter.beans.CuentasBean;
import com.seidor.inventario.adapter.beans.DetailProductBean;
import com.seidor.inventario.adapter.beans.ProjectReportBean;
import com.seidor.inventario.adapter.listitem.DataBankListitemAdapter;
import com.seidor.inventario.adapter.listitem.DetailOCitemAdapter;
import com.seidor.inventario.adapter.listitem.ProductListitemAdapter;
import com.seidor.inventario.adapter.render.DetailOCListitemRenderer;
import com.seidor.inventario.adapter.render.ProductEditableListitemRenderer;
import com.seidor.inventario.adapter.render.PurchaseOrderComboitemRenderer;
import com.seidor.inventario.adapter.render.StatusTypeOrderComboitemRenderer;
import com.seidor.inventario.adapter.render.TypePaymentComboitemRenderer;
import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.exception.BusinessException;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableCombobox;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.inroweditablecomps.IREditableTextbox;
import com.seidor.inventario.manager.DatosBancariosManager;
import com.seidor.inventario.manager.DetailOCManager;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.ProviderManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.Etapa;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.TipoOrdenCompra;
import com.seidor.inventario.model.TipoPago;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.DateFormatUtil;
import com.seidor.inventario.util.DateUtil;
import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.util.ReportUtil;
import com.seidor.inventario.util.ResourceServletClient;
import com.seidor.inventario.util.SessionUtil;

public class PurchaseOrderController {

	@Autowired
	private PurchaseOrderManager purchaseOrderManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private DetailOCManager detailOCManager;
	
	@Autowired
	private InvoiceManager invoiceManager;
	
	@Autowired
	private DatosBancariosManager datosBancariosManager;
	
	@Autowired
	private ProjectManager projectManager;
	
	@Autowired
	private ProviderManager providerManager;
	
	
	public PurchaseOrderManager getPurchaseOrderManager() {
		return purchaseOrderManager;
	}
	
	public void setPurchaseOrderManager(PurchaseOrderManager purchaseOrderManager) {
		this.purchaseOrderManager = purchaseOrderManager;
	}
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}


	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	public DetailOCManager getDetailOCManager() {
		return detailOCManager;
	}

	public void setDetailOCManager(DetailOCManager detailOCManager) {
		this.detailOCManager = detailOCManager;
	}

	public ProductManager getProductManager() {
		return productManager;
	}


	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public InvoiceManager getInvoiceManager() {
		return invoiceManager;
	}

	public void setInvoiceManager(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
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
	
	public ProviderManager getProviderManager() {
		return providerManager;
	}

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}

	//business logic 
	public void search(Listbox lb, PurchaseOrderSearchAdapter psa, NavigationState state){
		ArrayList<OrdenCompra> oc = this.purchaseOrderManager.search(psa);
		ArrayList<OrdenCompra> lisOC = new ArrayList<OrdenCompra>();
		
		for (OrdenCompra o: oc) {
			ArrayList<DetalleOrdenCompra> doc = this.detailOCManager.getOC(o.getIdOrdenCompra());
			if (doc.size() > 0)
				o.setDescripcion(doc.get(0).getProducto().getNombre());
			
			lisOC.add(o);
		}
		
		ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(lisOC);
		lb.setModel(model);
	}
	

	public void loadPurchaseOrder(Combobox combo) {
		ArrayList<OrdenCompra> purchaseOrders = this.purchaseOrderManager.getAll();
		Factura f= null;
		
		if (purchaseOrders != null) {
			
			/*for (OrdenCompra oc : purchaseOrders) {
				
				f= this.invoiceManager.getOC(oc.getIdOrdenCompra());
				if (f != null)
					purchaseOrders.remove(oc);
				
			}*/
			
			ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(purchaseOrders);
			combo.setItemRenderer(new PurchaseOrderComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	

	public PurcharseAdapter readForNew () {
		PurcharseAdapter p = new PurcharseAdapter();	
		
		OrdenCompra oc = new OrdenCompra();
		oc.setArea(new Area());
		oc.setCliente(new Cliente());
		oc.setEmpleado(new Empleado());
		oc.setEtapa(new Etapa());
		oc.setProveedor(new Proveedor());
		oc.setProyecto(new Proyecto());
		oc.setTipoOrdenCompra(new TipoOrdenCompra());
		oc.setEstatusOrdenCompra(new EstatusOrdenCompra());
		oc.setAlmacen(new Almacen());
		
		ArrayList<DetalleOrdenCompra> doc = new ArrayList<DetalleOrdenCompra>();
		
		p.setOrderCompra(oc);
		p.setDetailtOC(doc);
		
		return p;
	}
	
	
	public void getNoPresupuesto (PurcharseAdapter pa, Component win) {
		
		Combobox proy = (Combobox) win.getFellowIfAny("prcb");
		if (proy != null && proy.getSelectedItem()!=null )
			pa.getOrderCompra().setProyecto((Proyecto) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un proyecto");
		
		Proyecto p=  projectManager.get(pa.getOrderCompra().getProyecto().getIdProyecto());
		
		pa.getOrderCompra().setNoPresupuesto(p.getNumeroPresupuesto());
		
		Textbox propp = (Textbox) win.getFellowIfAny("propp");
		propp.setValue(p.getNumeroPresupuesto());
		
	}
	
	
	public void save(PurcharseAdapter pa, NavigationState state, Component win){
		
		
		Combobox cli = (Combobox) win.getFellowIfAny("clicb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getOrderCompra().setCliente((Cliente) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un cliente");
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			pa.getOrderCompra().setProveedor((Proveedor) prov.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prov, "Debe de seleccionar un proveedor");
					
		
		Combobox etapa = (Combobox) win.getFellowIfAny("etapcb");
		if (etapa != null && etapa.getSelectedItem()!=null )
			pa.getOrderCompra().setEtapa((Etapa) etapa.getSelectedItem().getValue());
		else 
			throw new WrongValueException(etapa, "Debe de seleccionar una etapa");
		
		Combobox area = (Combobox) win.getFellowIfAny("areacb");
		if (area != null && area.getSelectedItem()!=null )
			pa.getOrderCompra().setArea((Area) area.getSelectedItem().getValue());
		else 
			throw new WrongValueException(area, "Debe de seleccionar una area");
		
		Combobox toc = (Combobox) win.getFellowIfAny("toccb");
		if (toc != null && toc.getSelectedItem()!=null )
			pa.getOrderCompra().setTipoOrdenCompra((TipoOrdenCompra) toc.getSelectedItem().getValue());
		else 
			throw new WrongValueException(toc, "Debe de seleccionar un tipo de orden de compra");
		
		Combobox empl = (Combobox) win.getFellowIfAny("areaempcb");
		if (empl != null && empl.getSelectedItem()!=null )
			pa.getOrderCompra().setEmpleado((Empleado) empl.getSelectedItem().getValue());
		else 
			throw new WrongValueException(empl, "Debe de seleccionar un responsable");
		
		
		Combobox proy = (Combobox) win.getFellowIfAny("prcb");
		if (proy != null && proy.getSelectedItem()!=null )
			pa.getOrderCompra().setProyecto((Proyecto) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un proyecto");
		
		/*Combobox soccb = (Combobox) win.getFellowIfAny("soccb");
		if (soccb != null && soccb.getSelectedItem()!=null )
			pa.getOrderCompra().setEstatusOrdenCompra((EstatusOrdenCompra) soccb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un estatus de la orden de pago");*/
				
		pa.getOrderCompra().setFecha(new Date());
		pa.getOrderCompra().setFechaRecepAlmacen(new Date());
		
		EstatusOrdenCompra eoc = new EstatusOrdenCompra();
		eoc.setIdEstatusOrdenCompra(1);
		
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		pa.getOrderCompra().setAlmacen(almacen);
		
		pa.getOrderCompra().setEstatusOrdenCompra(eoc);
		
		this.purchaseOrderManager.save(pa.getOrderCompra());
		
		
		
		/*Listbox lb = (Listbox) win.getFellowIfAny("prolb");
		ListModelList<ProductListitemAdapter> lml = (ListModelList) lb.getModel();*/
		
		ArrayList<Producto> productsocl = (ArrayList<Producto>) SessionUtil.getSessionAttribute("listProductOC");
		
		Producto pe = new Producto();
		DetalleOrdenCompra e = new DetalleOrdenCompra();
		
		for (Producto p : productsocl ) {
			//ProductListitemAdapter p= (ProductListitemAdapter) lml.getElementAt(i);
			
			pe= new Producto();
        	e = new DetalleOrdenCompra();
        	
        	
			pe = productManager.getCodigo(p.getCodigo(), SessionUtil.getSucursalId());
			System.out.println("id_producto:"+p.getCodigo());
			
			
        	e.setIdDetalleOc(0);
        	e.setProducto(pe);
        	e.setOrdenCompra(pa.getOrderCompra());
        	Almacen a = new Almacen();
        		a.setIdAlmacen(SessionUtil.getSucursalId());
        	e.setAlmacen(a);
        	e.setCantidad(p.getCantidad());
        	e.setPrecioUnitario(p.getPrecioCompra());
        	e.setEstatus(SystemConstants.ENTRADA_POR_COMPRA);
        	e.setFecha(new Date());
        	e.setOrdenCompra(pa.getOrderCompra());
        	
        	//suma a productos la cantidad
        	pe.setCantidad(pe.getCantidad() + e.getCantidad());
    		pe.setPrecioCompra(e.getPrecioUnitario());
    		
        	
        	detailOCManager.save(e);
			
		}
		
		SessionUtil.setSessionAttribute("listProductOC", new ArrayList<Producto>());
		
		
		state.setDetailIdentifier(pa.getOrderCompra().getIdOrdenCompra());
		state.setUri("/WEB-INF/zul/oc/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getOrderCompra().getDescripcion());
		this.navigationControl.changeView(win, state);
	}
	
	
	@SuppressWarnings("unchecked")
	public void update(PurcharseAdapter pa, NavigationState state, Component win){
		
		Combobox cli = (Combobox) win.getFellowIfAny("clicb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getOrderCompra().setCliente((Cliente) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un cliente");
		
		Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			pa.getOrderCompra().setProveedor((Proveedor) prov.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prov, "Debe de seleccionar un proveedor");
		
		Combobox etapa = (Combobox) win.getFellowIfAny("etapcb");
		if (etapa != null && etapa.getSelectedItem()!=null )
			pa.getOrderCompra().setEtapa((Etapa) etapa.getSelectedItem().getValue());
		else 
			throw new WrongValueException(etapa, "Debe de seleccionar una etapa");
		
		
		
		Combobox areacb = (Combobox) win.getFellowIfAny("areacb");
		if (areacb != null)
			pa.getOrderCompra().setArea((Area) areacb.getSelectedItem().getValue());

		Combobox empl = (Combobox) win.getFellowIfAny("areaempcb");
		if (empl != null && empl.getSelectedItem() != null) {
			pa.getOrderCompra().setEmpleado((Empleado) empl.getSelectedItem().getValue());
		}
		

		Combobox toc = (Combobox) win.getFellowIfAny("toccb");
		if (toc != null && toc.getSelectedItem()!=null )
			pa.getOrderCompra().setTipoOrdenCompra((TipoOrdenCompra) toc.getSelectedItem().getValue());
		else 
			throw new WrongValueException(toc, "Debe de seleccionar un tipo de orden de compra");
		
		
		Combobox proy = (Combobox) win.getFellowIfAny("prcb");
		if (proy != null && proy.getSelectedItem()!=null )
			pa.getOrderCompra().setProyecto((Proyecto) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un proyecto");
		
		/*Combobox soccb = (Combobox) win.getFellowIfAny("soccb");
		if (soccb != null && soccb.getSelectedItem()!=null )
			pa.getOrderCompra().setEstatusOrdenCompra((EstatusOrdenCompra) soccb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un estatus de la orden de pago");*/
		
		/*Combobox tipoPago = (Combobox) win.getFellowIfAny("tpcb");
		if (tipoPago != null && tipoPago.getSelectedItem()!=null )
			pa.getProveedor().setTipoPago((TipoPago) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un tipo de pago");*/
		
					
		pa.getOrderCompra().setFecha(new Date());
				
		this.purchaseOrderManager.update(pa.getOrderCompra());
		
		
		ArrayList<DetalleOrdenCompra> detalleOC = (ArrayList<DetalleOrdenCompra>) SessionUtil.getSessionAttribute("listDetailOC");
		System.out.println("size idDetalleOC: "+detalleOC.size());
		
		for (DetalleOrdenCompra d : detalleOC ) {
			
			d.setOrdenCompra(pa.getOrderCompra());
			Almacen a= new Almacen();
			a.setIdAlmacen(SessionUtil.getSucursalId());
			d.setAlmacen(a);
			d.setFecha(new Date());
			d.setEstatus(1);
			
			
        	if (d.getIdDetalleOc() == 0)
        		detailOCManager.save(d);
        	else 
        		detailOCManager.update(d);
        		
		}
		
		SessionUtil.setSessionAttribute("listDetailOC", new ArrayList<Producto>());
		
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getOrderCompra().getDescripcion());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getOrderCompra().getDescripcion());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	
	private Integer getIdDetalleOC(Integer idProducto, ArrayList<DetalleOrdenCompra> detailtOC) {
		
		int id= 0;
		
		for (DetalleOrdenCompra doc :detailtOC ) {
			
			if (doc.getProducto().getIdProducto() == idProducto) {
				id= doc.getIdDetalleOc();	
				break;
			}	
			
		}
		
		return id;
		
	}

	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			OrdenCompra oc = (OrdenCompra)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(oc.getIdOrdenCompra());
			state.setUri("/WEB-INF/zul/oc/detail.zul");
			state.appendBreadCrumbsPath(oc.getDescripcion());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				OrdenCompra o = (OrdenCompra)lb.getModel().getElementAt(i);
				detailList.add(o.getIdOrdenCompra());
				detailLabels.add(o.getDescripcion());
				if (o.getIdOrdenCompra().equals(oc.getIdOrdenCompra())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
		
	
	public PurcharseAdapter read(Integer ocId){
		PurcharseAdapter pa = new PurcharseAdapter();
	
		OrdenCompra oc = this.purchaseOrderManager.get(ocId);
		ArrayList<DetalleOrdenCompra> doc = this.detailOCManager.getOC(ocId);
		
		pa.setOrderCompra(oc);
		pa.setDetailtOC(doc);
		
		return pa;
	}
	
		
	public PurcharseAdapter readForEdit(Integer ocId){
		PurcharseAdapter pa = new PurcharseAdapter();
		
		OrdenCompra oc = this.purchaseOrderManager.get(ocId);
		ArrayList<DetalleOrdenCompra> doc = this.detailOCManager.getOC(ocId);
		
		if (oc.getProveedor() == null)
			oc.setProveedor(new Proveedor());
		
		pa.setOrderCompra(oc);
		pa.setDetailtOC(doc);
		
		return pa;
	}
	
	
	//catalog
	public void loadStateTypeOrder(Combobox combo) {
		ArrayList<EstatusOrdenCompra> stateOrderType = this.purchaseOrderManager.getAllTypeOrder();
		if (stateOrderType != null) {
			ListModelList<EstatusOrdenCompra> model = new ListModelList<EstatusOrdenCompra>(stateOrderType);
			combo.setItemRenderer(new StatusTypeOrderComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public void loadTypePayment(Combobox combo) {
		ArrayList<TipoPago> typePayment = this.purchaseOrderManager.getAllTypePayment();
		if (typePayment != null) {
			ListModelList<TipoPago> model = new ListModelList<TipoPago>(typePayment);
			combo.setItemRenderer(new TypePaymentComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProduct(Listbox lbr) {
		
		ProductAdapter product = new ProductAdapter();
		Producto p= new Producto();
		p.setCantidad(0.0);
		product.setProducto(p);
	
		ListModelList<ProductListitemAdapter> model = (ListModelList) lbr.getModel();
		ProductListitemAdapter adapter = new ProductListitemAdapter(new Producto());
		model.add(adapter);
									
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProduct(Combobox pcb,  Listbox lbr) {
		
		if (pcb == null || pcb.getSelectedItem() == null) {			
			throw new WrongValueException(pcb, "Debes de seleccionar un producto.");
		} else {
			
			Producto productSelect = (Producto) pcb.getSelectedItem().getValue();
			
			ProductAdapter product = new ProductAdapter();
			product.setProducto(productSelect);
			product.getProducto().setCantidad(0.0);
			product.getProducto().setPrecioCompra(new BigDecimal(0.0));
						
			ListModelList<ProductListitemAdapter> model = (ListModelList) lbr.getModel();
			ProductListitemAdapter adapter = new ProductListitemAdapter(product.getProducto());
			model.add(adapter);
		}	
									
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addEditProduct(Combobox pcb,  Listbox lbr) {
		
		if (pcb == null || pcb.getSelectedItem() == null) {			
			throw new WrongValueException(pcb, "Debes de seleccionar un producto.");
		} else {
			
			Producto productSelect = (Producto) pcb.getSelectedItem().getValue();
			
			ProductAdapter product = new ProductAdapter();
			product.setProducto(productSelect);
			product.getProducto().setCantidad(0.0);
			product.getProducto().setPrecioCompra(new BigDecimal(0.0));
						
			DetalleOrdenCompra detailoc= new DetalleOrdenCompra();
			detailoc.setProducto(product.getProducto());
			detailoc.setIdDetalleOc(0);
		
			ListModelList<DetailOCitemAdapter> model = (ListModelList) lbr.getModel();
			DetailOCitemAdapter adapter = new DetailOCitemAdapter(detailoc);
			model.add(adapter);
			
		}	
									
	}
	
	public void loadProducts(Listbox lb, Label idtotalcoin, boolean edit){
		
		ArrayList<Producto> pl= new ArrayList<Producto>();
		Producto products= new Producto();
		pl.add(products);
		
		SessionUtil.setSessionAttribute("listProductOC", new ArrayList<Producto>());
		
		ListModelList<ProductListitemAdapter> lml = new ListModelList<ProductListitemAdapter>();
		if(edit) {
			lb.setItemRenderer(new ProductEditableListitemRenderer(idtotalcoin));
		}
		lb.setModel(lml);
		
	}


	@SuppressWarnings("unchecked")
	public void recordProduct(Listitem listitem, Label total) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		ProductListitemAdapter adapter = (ProductListitemAdapter) lb.getModel().getElementAt(selectedIndex);
		Producto p = adapter.getProduct();
		
		Component comp = listitem.getFirstChild();
		IREditableCombobox descriptionbox = (IREditableCombobox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
		
		IREditableDoublebox cantidad = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableCombobox unidadMedida = (IREditableCombobox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		
		IREditableDoublebox precioUnitario = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
				
		System.out.println(descriptionbox.getCombobox().getValue());
		
		p.setCodigo(descriptionbox.getCombobox().getValue());
		p.setCantidad(cantidad.getValue());
		p.setPrecioCompra(new BigDecimal(precioUnitario.getValue()));
		if (p.getUnidadMedida().getIdUnidadMedida() != ((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue()).getIdUnidadMedida()) {
			Producto pOriginal= productManager.get(p.getIdProducto());
			pOriginal.setUnidadMedida((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue());
			productManager.update(pOriginal);
		}
		p.setUnidadMedida((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue());
		
		
		ArrayList<Producto> listProductOC = (ArrayList<Producto>) SessionUtil.getSessionAttribute("listProductOC");
		
		Producto pedit = null;
		
		System.out.println("List productos: "+ listProductOC.size()+ " "+selectedIndex);
		
		if (listProductOC.size() > 0 && selectedIndex < listProductOC.size())
		   pedit = listProductOC.get(selectedIndex);
		
		if (pedit == null)	
			listProductOC.add(p);
		else
			listProductOC.set(selectedIndex, p);
		
		
		SessionUtil.setSessionAttribute("listProductOC", listProductOC);
		
		total.setValue(getTotal (listProductOC));
	}
	
	@SuppressWarnings("unchecked")
	public void deleteProductOC(Listitem listitem, Label totalCoin) {
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		ProductListitemAdapter adapter = (ProductListitemAdapter) lb.getModel().getElementAt(selectedIndex);
		Producto p = adapter.getProduct();
	
		ArrayList<Producto> listProductOC = (ArrayList<Producto>) SessionUtil.getSessionAttribute("listProductOC");
		listProductOC.remove(p);
		SessionUtil.setSessionAttribute("listProductOC", listProductOC);
		
		totalCoin.setValue(getTotal (listProductOC));
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateProduct(Listitem listitem, Label total) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailOCitemAdapter adapter = (DetailOCitemAdapter) lb.getModel().getElementAt(selectedIndex);
		
		DetalleOrdenCompra doc = adapter.getDetalleOrdenCompra();
		
		Component comp = listitem.getFirstChild();
		IREditableCombobox descriptionbox = (IREditableCombobox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
		
		IREditableDoublebox cantidad = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableCombobox unidadMedida = (IREditableCombobox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		
		IREditableDoublebox precioUnitario = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
				
		System.out.println(descriptionbox.getCombobox().getValue());
		
		doc.getProducto().setCodigo(descriptionbox.getCombobox().getValue());
		doc.setCantidad(cantidad.getValue());
		doc.setPrecioUnitario(new BigDecimal(precioUnitario.getValue()));
		
		if (doc.getProducto().getUnidadMedida().getIdUnidadMedida() != ((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue()).getIdUnidadMedida()) {
			Producto pOriginal= productManager.get(doc.getProducto().getIdProducto());
			pOriginal.setUnidadMedida((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue());
			productManager.update(pOriginal);
		}
		doc.getProducto().setUnidadMedida((UnidadMedida) unidadMedida.getCombobox().getSelectedItem().getValue());
				
		ArrayList<DetalleOrdenCompra> listDetailOC = (ArrayList<DetalleOrdenCompra>) SessionUtil.getSessionAttribute("listDetailOC");
		
		if (doc.getIdDetalleOc() == 0)
			listDetailOC.add(doc);
		else 
		if (doc.getIdDetalleOc() > 0) {
			listDetailOC.set(selectedIndex, doc);
		}
		
		SessionUtil.setSessionAttribute("listDetailOC", listDetailOC);
		
				
		total.setValue(getTotalEdit (listDetailOC));
	}

	private String getTotalEdit(ArrayList<DetalleOrdenCompra> listDetailOC) {
		StringBuilder sb= new StringBuilder();
		BigDecimal total= new BigDecimal(0.0);
		BigDecimal totalTmp= new BigDecimal(0.0);
		
		for (DetalleOrdenCompra doc: listDetailOC) {
			System.out.println("productos"+doc.getCantidad()+"*"+doc.getPrecioUnitario());
			totalTmp = new BigDecimal(doc.getCantidad()).multiply(doc.getPrecioUnitario());
			total = total.add(totalTmp);
		}
		
		sb.append("SubTotal: $ " +NumberFormatUtil.format(total, 2));
		sb.append("\n");
		sb.append("IVA: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(0.16)), 2));
		sb.append("\n");
		sb.append("Total: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(1.16)), 2));
		
		return sb.toString();
	}

	private String  getTotal(ArrayList<Producto> listProductOC) {
		
		StringBuilder sb= new StringBuilder();
		BigDecimal total= new BigDecimal(0.0);
		BigDecimal totalTmp= new BigDecimal(0.0);
		
		for (Producto p: listProductOC) {
			System.out.println("productos"+p.getCantidad()+"*"+p.getPrecioCompra());
			totalTmp = new BigDecimal(p.getCantidad()).multiply(p.getPrecioCompra());
			total = total.add(totalTmp);
		}
		
		sb.append("SubTotal: $ " +NumberFormatUtil.format(total, 2));
		sb.append("\n");
		sb.append("IVA: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(0.16)), 2));
		sb.append("\n");
		sb.append("Total: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(1.16)), 2));
		
		return sb.toString();
	}
	
	
	public void loadEditProducts(Listbox lb, PurcharseAdapter purcharseAdapter, Label idtotalcoin, boolean edit){
		
		ArrayList<DetalleOrdenCompra> doc = new ArrayList<DetalleOrdenCompra>(purcharseAdapter.getDetailtOC());
		
		SessionUtil.setSessionAttribute("listDetailOC", doc);
		
		ListModelList<DetailOCitemAdapter> lml= new ListModelList<DetailOCitemAdapter>(DetailOCitemAdapter.getArray(doc));
		if(edit) {
			lb.setItemRenderer(new DetailOCListitemRenderer(idtotalcoin));
		}
		lb.setModel(lml);
		
		idtotalcoin.setValue(getDetalleTotal(doc));
		
	}
	
	public void obtieneDOC (PurcharseAdapter purcharseAdapter, Listbox lb, Label total) {
		ListModelList<DetalleOrdenCompra> model = new ListModelList<DetalleOrdenCompra>(purcharseAdapter.getDetailtOC());
		lb.setModel(model);
		
		total.setValue(getDetalleTotal(purcharseAdapter.getDetailtOC()) );
	}

	private String getDetalleTotal(ArrayList<DetalleOrdenCompra> detailtOC) {
		StringBuilder sb= new StringBuilder();

		BigDecimal total= new BigDecimal(0.0);
		BigDecimal totalTmp= new BigDecimal(0.0);
		
		for (DetalleOrdenCompra p: detailtOC) {
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
	
	
	
	public void createReportData(PurcharseAdapter purcharseAdapter, Component mtwin){

		ProjectReportBean pBeanReport= new ProjectReportBean();
		
		//ArrayList<DetalleOrdenCompra>  listOC =  detailOCManager.getIdOCAll(purcharseAdapter.getOrderCompra().getIdOrdenCompra());
		
		ArrayList<DetailProductBean> listadpb = new ArrayList<DetailProductBean>();
		DetailProductBean dpb = new DetailProductBean();
		int i=1;
		BigDecimal subtotal= new BigDecimal(0.0);
		BigDecimal subtotalTmp= new BigDecimal(0.0);
		BigDecimal iva= new BigDecimal(0.0);
		BigDecimal total= new BigDecimal(0.0);
		
		for (DetalleOrdenCompra doc : purcharseAdapter.getDetailtOC()) {
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
		
		ArrayList<DatosBancarios> listDataBank = datosBancariosManager.getDatosBancarios(purcharseAdapter.getOrderCompra().getProveedor().getIdProveedor());
		
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
		
		pBeanReport.setOrdenCompra(purcharseAdapter.getOrderCompra());
		pBeanReport.setCliente(purcharseAdapter.getOrderCompra().getCliente());
		pBeanReport.setProveedor(purcharseAdapter.getOrderCompra().getProveedor());
		pBeanReport.setProyecto(purcharseAdapter.getOrderCompra().getProyecto());
				
		
		exportToPdfOrdeCompra (pBeanReport);
		
		
		/*byte[] dataReport= this.createPDFProductRegistry(purcharseAdapter);
		
		if(dataReport!=null){
			Media mediaReport = new AMedia(
				"Report_" + purcharseAdapter.getOrderCompra().getNoPresupuesto() + ".pdf", "pdf",
				"application/pdf", dataReport);
			
			HashMap<String, Object> parameters= new HashMap<String, Object>();
			parameters.put("odenCompraId", 1);			
			parameters.put("media",mediaReport);
			ResourceServletClient.openModalStylesResource("/zul/common/customcomps/pdfViewer.zul", mtwin, parameters);
			//org.zkoss.zul.Filedownload.save(mediaReport);

		}else{
			throw new BusinessException(
					"Hubo un error y no se pudo generar el reporte");
		}*/
	}
	
	/*
	 * @SuppressWarnings("rawtypes")
	public byte[] createPDFProductRegistry(PurcharseAdapter purcharseAdapter) {
		HashMap<String, Object> parametters = new HashMap<String, Object>();
		parametters.put("REPORT_TITLE", "Registro de Producto");
		parametters.put("REPORT_DATE", DateFormatUtil.getLongFormatedDate(new Date(), true));
		parametters.put("ListFromJsonGeneric[PRODUCTS]", getListFromJsonGeneric(purcharseAdapter.getOrderCompra()));
		
		try {
			List dataSource = new ArrayList();
			
			
			dataSource.add(purcharseAdapter.getDetailtOC());
			String tname = "Registro de producto";
			return ReportUtil.getReportBytes(dataSource, "ordenCompra.jasper", ReportUtil.TASK_PDF,
					parametters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 */
	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
		private void exportToPdfOrdeCompra(ProjectReportBean pBeanReport) {
		 
	    	HashMap<String, Object> parametters = new HashMap<String, Object>();
			parametters.put("REPORT_TITLE", "ORDEN COMPRA");
			parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
			
			parametters.put("odenCompraId", pBeanReport.getOrdenCompra().getIdOrdenCompra());
			parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(pBeanReport.getJsonListProducts()));
			parametters.put("ListFromJsonGeneric[jsonListCtas]", getListFromJsonGeneric(pBeanReport.getJsonListCtas()));
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
			
			
			
			
			try {
				List dataSource = new ArrayList();
				dataSource.add(pBeanReport);
				String tname = "OrdenCompra"+pBeanReport.getOrdenCompra().getIdOrdenCompra();
				Media mediaReport = ReportUtil.generateReport(dataSource, "ordenCompra.jasper", ReportUtil.TASK_PDF, parametters, tname);
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
	

	

	@SuppressWarnings("unchecked")
	public void deleteProduct(EditableListitem listitem, Label total) {
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailOCitemAdapter adapter = (DetailOCitemAdapter) lb.getModel().getElementAt(selectedIndex);
		
		DetalleOrdenCompra doc = adapter.getDetalleOrdenCompra();
		
		Component comp = listitem.getFirstChild();
		IREditableCombobox descriptionbox = (IREditableCombobox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
		
		IREditableDoublebox cantidad = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		
		comp = comp.getNextSibling();
		
		
		IREditableDoublebox precioUnitario = (IREditableDoublebox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
				
				
		doc.getProducto().setCodigo(descriptionbox.getCombobox().getValue());
		doc.setCantidad(cantidad.getValue());
		doc.setPrecioUnitario(new BigDecimal(precioUnitario.getValue()));
				
			
		ArrayList<DetalleOrdenCompra> listDetailOC = (ArrayList<DetalleOrdenCompra>) SessionUtil.getSessionAttribute("listDetailOC");
		
		if (doc.getIdDetalleOc() == 0)
			listDetailOC.remove(doc);
		else 
		if (doc.getIdDetalleOc() > 0) {
			detailOCManager.delete(doc);
			listDetailOC.remove(doc);
		}
		
		SessionUtil.setSessionAttribute("listDetailOC", listDetailOC);
		
		total.setValue(getTotalEdit (listDetailOC));
		
	}
	
	
	/*
	  // 1. compile template ".jrxml" file
        JasperReport jasperReport = getJasperReport();

        // 2. parameters "empty"
        Map<String, Object> parameters = getParameters();

        // 3. datasource "java object"
        JRDataSource dataSource = getDataSource();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, 
                parameters, 
                dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);

    }

    private static JasperReport getJasperReport() throws FileNotFoundException, JRException {
        File template = ResourceUtils.getFile("classpath:report.jrxml");
        return JasperCompileManager.compileReport(template.getAbsolutePath());
    }
    private static Map<String, Object> getParameters(){
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "hmkcode");
        return parameters;
    }

    private static JRDataSource getDataSource(){

        List<Country> countries = new LinkedList<>();

        countries.add(new Country("IS", "Iceland", 
            "https://i.pinimg.com/originals/72/b4/49/72b44927f220151547493e528a332173.png"));

        // 9 other countries in GITHUB

        return new JRBeanCollectionDataSource(countries);
    }
	  */
	
	public void getDataProvider (PurcharseAdapter ocSave, Component win) {
	
		Combobox cbProveedor = (Combobox) win.getFellowIfAny("provcb");
		if (cbProveedor != null && cbProveedor.getSelectedItem()!=null ) {
			ocSave.setProveedor((Proveedor) cbProveedor.getSelectedItem().getValue());
		
			Proveedor prov=  providerManager.get(ocSave.getProveedor().getIdProveedor());
			ocSave.setProveedor(prov);
			ocSave.getOrderCompra().setProveedor(prov);
			
			 Label tpProv = (Label) win.getFellowIfAny("tpProv");
			 tpProv.setValue(prov.getTipoPago().getDescripcion());
			 
			 Label diasProv = (Label) win.getFellowIfAny("diasProv");
			 diasProv.setValue(prov.getDiasCredito() == null ? "" : ""+prov.getDiasCredito());
			 
			 Div divProv = (Div) win.getFellowIfAny("sectionProv");
			 divProv.setVisible(true);
	
		}
		

	}


	
	
}
