package com.seidor.inventario.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.google.gson.Gson;
import com.seidor.inventario.adapter.InvoiceAdapter;
import com.seidor.inventario.adapter.PurcharseAdapter;
import com.seidor.inventario.adapter.beans.CuentasBean;
import com.seidor.inventario.adapter.beans.DetailProductBean;
import com.seidor.inventario.adapter.beans.ProjectReportBean;
import com.seidor.inventario.adapter.listitem.DetailOCitemAdapter;
import com.seidor.inventario.adapter.render.DetailOCAlmacenListitemRenderer;
import com.seidor.inventario.adapter.render.InvoiceComboitemRenderer;
import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.manager.DatosBancariosManager;
import com.seidor.inventario.manager.DetailOCManager;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.InvoiceManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.Ubicacion;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.DateFormatUtil;
import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.util.ReportUtil;
import com.seidor.inventario.util.SessionUtil;

public class InvoiceController {

	
	private InvoiceManager invoiceManager;
	private NavigationControl navigationControl;
	private PurchaseOrderManager purchaseOrderManager;
	private DetailOCManager detailOCManager;
	private EntryManager entryManager;
	private ProductManager productManager;
	private ProjectManager projectManager;
	private DatosBancariosManager datosBancariosManager;
	
	public InvoiceManager getInvoiceManager() {
		return invoiceManager;
	}

	public void setInvoiceManager(InvoiceManager invoiceManager) {
		this.invoiceManager = invoiceManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
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
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	
	public DatosBancariosManager getDatosBancariosManager() {
		return datosBancariosManager;
	}

	public void setDatosBancariosManager(DatosBancariosManager datosBancariosManager) {
		this.datosBancariosManager = datosBancariosManager;
	}

	//bussines logic
	public void search(Listbox lb, InvoiceSearchAdapter isa, NavigationState state){
		ArrayList<Factura> factura = this.invoiceManager.search(isa);
		
		ListModelList<Factura> model = new ListModelList<Factura>(factura);
		lb.setModel(model);
	}
	

	public void loadInvoice(Combobox combo) {
		ArrayList<Factura> invoices = this.invoiceManager.getAll();
		
		if (invoices != null) {
			ArrayList<Factura> invoicesFinal = new ArrayList<Factura>();
			
			Factura f = new Factura ();
			f.setIdFactura(0);
			f.setNumeroFactura("N/A");
			f.setDescripcion("NO CUENTA CON FACTURA");
			invoicesFinal.add(f);
			
			for (Factura fa : invoices) {
				invoicesFinal.add(fa);
			}
			
			ListModelList<Factura> model = new ListModelList<Factura>(invoicesFinal);
			combo.setItemRenderer(new InvoiceComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public InvoiceAdapter readForNew () {
		InvoiceAdapter inv = new InvoiceAdapter();	
		
		inv.setFactura(new Factura());
		inv.getFactura().setOrdenCompra(new OrdenCompra());
		OrdenCompra oc= new OrdenCompra ();
		EstatusOrdenCompra eoc = new EstatusOrdenCompra();
		oc.setEstatusOrdenCompra(eoc);
		inv.setOrdenCompra(oc);
		inv.setDoc(new ArrayList<DetalleOrdenCompra>());
		
		return inv;
	}
	
	
	public void save(InvoiceAdapter ia, NavigationState state, Component win){
		
		Combobox occb = (Combobox) win.getFellowIfAny("cboc");
		if (occb != null && occb.getSelectedItem()!=null )
			ia.getFactura().setOrdenCompra((OrdenCompra) occb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(occb, "Debe de seleccionar una orden de compra");
		
		ia.getFactura().setEstatus(SystemConstants.FACTURA_ACTIVA);
		
		ia.getFactura().setSubtotal(new BigDecimal(0.0));
		ia.getFactura().setIva(new BigDecimal(0.0));
		ia.getFactura().setTotal(new BigDecimal(0.0));
		ia.getFactura().setDescripcion("");
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		ia.getFactura().setAlmacen(almacen);
		
		this.invoiceManager.save(ia.getFactura());
		
		ia.setDoc(detailOCManager.getIdOCAll(ia.getFactura().getOrdenCompra().getIdOrdenCompra()));
		 
		
		Entrada e= new Entrada();
		Producto p= new Producto();
		OrdenCompra oc = new OrdenCompra();
		
		
		for (DetalleOrdenCompra doc : ia.getDoc()) {
			
			e= new Entrada();
			p = productManager.get(doc.getProducto().getIdProducto());	
			 
			e.setIdEntrada(0);
			e.setCantidad(doc.getCantidadFactura());
			e.setEmpleado(SessionUtil.getEmpleadoId());
			e.setFactura(ia.getFactura());
			e.setAlmacen(doc.getProducto().getAlmacen());
			oc = purchaseOrderManager.get(doc.getOrdenCompra().getIdOrdenCompra());
			e.setOrdenCompra(oc);
			System.out.println("oc id: "+doc.getOrdenCompra().getIdOrdenCompra());
			Proyecto proyecto= new Proyecto();
			proyecto.setIdProyecto(oc.getProyecto().getIdProyecto());
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
			
			p.setCantidad(p.getCantidad() + doc.getCantidadFactura());
			p.setPrecioCompra(doc.getPrecioUnitario());
			
			this.entryManager.save(e, p);
			
		}
		
		
		
		state.setDetailIdentifier(ia.getFactura().getIdFactura());
		state.setUri("/WEB-INF/zul/invoice/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ia.getFactura().getNumeroFactura());
		this.navigationControl.changeView(win, state);
	}
	
	
	public void update(InvoiceAdapter ia, NavigationState state, Component win){
		
		/*Combobox prov = (Combobox) win.getFellowIfAny("provcb");
		if (prov != null && prov.getSelectedItem()!=null )
			ia.getFactura().setOrdenCompra((OrdenCompra) prov.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prov, "Debe de seleccionar una orden compra");*/
		
		
		this.invoiceManager.update(ia.getFactura());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(ia.getFactura().getNumeroFactura());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), ia.getFactura().getNumeroFactura());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Factura invoice = (Factura)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(invoice.getIdFactura());
			state.setUri("/WEB-INF/zul/invoice/detail.zul");
			state.appendBreadCrumbsPath(invoice.getNumeroFactura());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Factura in = (Factura)lb.getModel().getElementAt(i);
				detailList.add(in.getIdFactura());
				detailLabels.add(in.getNumeroFactura());
				if (in.getIdFactura().equals(invoice.getIdFactura())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
		
	
	public InvoiceAdapter read(Integer invoiceId){
		InvoiceAdapter in = new InvoiceAdapter();
	
		Factura invoice = this.invoiceManager.get(invoiceId);
		OrdenCompra oc = this.purchaseOrderManager.get(invoice.getOrdenCompra().getIdOrdenCompra());
		ArrayList<DetalleOrdenCompra> doc= this.detailOCManager.getIdOCAll(invoice.getOrdenCompra().getIdOrdenCompra());
		
		in.setFactura(invoice);
		in.setOrdenCompra(oc);
		in.setDoc(doc);
		
		return in;
	}
	
	
	public InvoiceAdapter readForEdit(Integer invoiceId){
		InvoiceAdapter in = new InvoiceAdapter();
		
		Factura invoice = this.invoiceManager.get(invoiceId);
		OrdenCompra oc = this.purchaseOrderManager.get(invoice.getOrdenCompra().getIdOrdenCompra());
		ArrayList<DetalleOrdenCompra> doc= this.detailOCManager.getIdOCAll(invoice.getOrdenCompra().getIdOrdenCompra());
		
		in.setFactura(invoice);
		in.setOrdenCompra(oc);
		in.setDoc(doc);
		
		return in;
	}
	
	
	public void obtenerOrdenCompra (InvoiceAdapter ia,  Component win) {
		
		Combobox cbOrdenCompra = (Combobox) win.getFellowIfAny("cboc");
		if (cbOrdenCompra != null &&cbOrdenCompra.getSelectedItem()!=null ) {
			ia.getFactura().setOrdenCompra((OrdenCompra) cbOrdenCompra.getSelectedItem().getValue());
		
			OrdenCompra oc=  purchaseOrderManager.get(ia.getFactura().getOrdenCompra().getIdOrdenCompra());
			ia.setOrdenCompra(oc);
			
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
			 
			 Listbox lb = (Listbox) win.getFellowIfAny("oclb");
			
			ListModelList<DetailOCitemAdapter> model = 
					new ListModelList<DetailOCitemAdapter>(DetailOCitemAdapter.getArray(this.detailOCManager.getOC(ia.getFactura().getOrdenCompra().getIdOrdenCompra())));
			lb.setModel(model);
			lb.setItemRenderer(new DetailOCAlmacenListitemRenderer());
			
			//listDetailOC
			ArrayList<DetailOCitemAdapter> listDetailOC = new ArrayList<DetailOCitemAdapter>();
			SessionUtil.setSessionAttribute("listDetailOC", listDetailOC);	
			 
			 
		}
		
	}
	
	public void obtenerOrdenCompraEdit (InvoiceAdapter ia,  Listbox lb) {
			
		ListModelList<DetailOCitemAdapter> model = 
				new ListModelList<DetailOCitemAdapter>(DetailOCitemAdapter.getArray(ia.getDoc()));
		lb.setModel(model);
		lb.setItemRenderer(new DetailOCAlmacenListitemRenderer());
		
		//listDetailOC
		ArrayList<DetailOCitemAdapter> listDetailOC = new ArrayList<DetailOCitemAdapter>();
		SessionUtil.setSessionAttribute("listDetailOC", listDetailOC);	
					
	}
		

	public void agregarEntradasAlmacen(EditableListitem listitem) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailOCitemAdapter adapter = (DetailOCitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DetalleOrdenCompra doc = adapter.getDetalleOrdenCompra();
		
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
		
		
		double compCant = doc.getCantidad() - quantitybox.getValue();
		Boolean flagUpdateOC = Boolean.FALSE; 
		
		if(compCant >= 0) {
			flagUpdateOC = Boolean.TRUE;
		} else
		if (compCant < 0) {
			throw new WrongValueException(quantitybox, "La cantidad de entrada a almacen tiene que ser menor o igual a la cantidad");
		}	
		
	    if (flagUpdateOC) {
			doc.setCantidadFactura(quantitybox.getValue());
			System.out.println("Realiza entrada");
			
			//actualizar el detalle de las oc 
			detailOCManager.update(doc);
			
		}
	
	}
	
	
	public void obtieneDOC (InvoiceAdapter ia, Listbox lb, Label total) {
		ListModelList<DetalleOrdenCompra> model = new ListModelList<DetalleOrdenCompra>(ia.getDoc());
		lb.setModel(model);
		
		total.setValue(getDetalleTotal(ia.getDoc()));
	
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
	
	
	
	public void createReportData(InvoiceAdapter invoicceAdapter, Component mtwin){

		PurcharseAdapter purcharseAdapter =  new PurcharseAdapter ();
		purcharseAdapter.setOrderCompra(invoicceAdapter.getOrdenCompra());
		purcharseAdapter.setDetailtOC(invoicceAdapter.getDoc());
		
		
		ProjectReportBean pBeanReport= new ProjectReportBean();
		
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
	}	
	
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
			
			
			
			try {
				List dataSource = new ArrayList();
				dataSource.add(pBeanReport);
				String tname = "OrdenCompra";
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
	
}
