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
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.google.gson.Gson;
import com.seidor.inventario.adapter.DevolucionesAdapter;
import com.seidor.inventario.adapter.beans.DetailProductBean;
import com.seidor.inventario.adapter.beans.DevolucionBean;
import com.seidor.inventario.adapter.beans.ProjectReportBean;
import com.seidor.inventario.adapter.listitem.DetailTransactionitemAdapter;
import com.seidor.inventario.adapter.listitem.DevolucionitemAdapter;
import com.seidor.inventario.adapter.render.DetailTransactionDevListitemRenderer;
import com.seidor.inventario.adapter.render.DevolucionListitemRenderer;
import com.seidor.inventario.adapter.search.DevolcionSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.FoliosManager;
import com.seidor.inventario.manager.OutputManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.TransactionDetailManager;
import com.seidor.inventario.manager.TransactionManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.Folios;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.TiposMovimiento;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.DateFormatUtil;
import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.util.ReportUtil;
import com.seidor.inventario.util.SessionUtil;

public class DevolucionesController {
	

	@Autowired
	private OutputManager outputManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private ProductManager productManager;
	
	@Autowired
	private EntryManager entryManager;
	
	@Autowired
	private ProjectManager projectManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private FoliosManager foliosManager;
	
	
	@Autowired
	private TransactionDetailManager transactionDetailManager;
	
	public OutputManager getOutputManager() {
		return outputManager;
	}

	public void setOutputManager(OutputManager outputManager) {
		this.outputManager = outputManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public EntryManager getEntryManager() {
		return entryManager;
	}

	public void setEntryManager(EntryManager entryManager) {
		this.entryManager = entryManager;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
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

	//logic
	public DevolucionesAdapter read () {
		
		DevolucionesAdapter cpa = new DevolucionesAdapter ();
		
		cpa.setProyecto(new Proyecto());
		Movimientos m= new Movimientos();
		m.setProyecto(new Proyecto());
		m.setArea(new Area());
		m.setEmpleado(new Empleado());
		cpa.setMovimiento(m);
		cpa.setDetalleMovimientos(new ArrayList<DetalleMovimiento>());
		
		return cpa;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProduct(Combobox pcb,  Listbox lbr) {
		
		if (pcb == null || pcb.getSelectedItem() == null) {			
			throw new WrongValueException(pcb, "Debes de seleccionar un producto.");
		} else {
			
			Producto productSelect = (Producto) pcb.getSelectedItem().getValue();
			
			//realizar la busqueda del producto en detalle_movimientos con salidas
			
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
		
		SessionUtil.setSessionAttribute("listDetailTransactionDEV", new ArrayList<DetalleMovimiento>());
		
		ListModelList<DetailTransactionitemAdapter> lml = new ListModelList<DetailTransactionitemAdapter>();
		if(edit) {
			lb.setItemRenderer(new DetailTransactionDevListitemRenderer(SystemConstants.DEVOLUCION));
		}
		lb.setModel(lml);
		
	}


	@SuppressWarnings("unchecked")
	public void saveOrUpdateDevolucionAlmacen(EditableListitem listitem, Integer idStatusMovimiento) {
		
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
		
		System.out.println("cantidad devolucion: "+quantitybox.getValue() +" cantidad: "+doc.getCantidad());
		System.out.println("id del producto: "+doc.getProducto().getIdProducto());
		
		
		//double compCant = doc.getCantidadTotal() - (doc.getCantidad() + quantitybox.getValue());
		Boolean flagUpdateOC = Boolean.FALSE; 
		
		if(quantitybox.getValue() > 0) {
			flagUpdateOC = Boolean.TRUE;
		} else
		if (quantitybox.getValue() ==  0) {
			throw new WrongValueException(quantitybox, "La cantidad de devolucion a almacen tiene que ser mayor a 0");
		}	
		
	    if (flagUpdateOC) {
			doc.setCantidad(quantitybox.getValue());
			doc.setFecha(new Date());
			doc.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
			
			System.out.println("Realiza devolucion");
			
			//actualizar el detalle de las movimientos
			if (doc.getIdDetalleMovimiento() == 0) {
				
				//listDetailTransaction
				ArrayList<DetalleMovimiento> listDetailTransactionDEV = (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionDEV");
				listDetailTransactionDEV.add(doc);
				SessionUtil.setSessionAttribute("listDetailTransactionDEV", listDetailTransactionDEV);	
				
			}	
			
			
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void saveTransactionDev(DevolucionesAdapter ta, NavigationState state, Component win){
					
		Combobox areacb = (Combobox) win.getFellowIfAny("areacb");
		if (areacb != null && areacb.getSelectedItem()!=null )
			ta.getMovimiento().setArea((Area) areacb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areacb, "Debe de seleccionar un area");
		
		/*Combobox areaempcb = (Combobox) win.getFellowIfAny("areaempcb");
		if (areaempcb != null && areaempcb.getSelectedItem()!=null )
			ta.getMovimiento().setEmpleado((Empleado) areaempcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areaempcb, "Debe de seleccionar un empleado");*/
		
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null )
			ta.getMovimiento().setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(prcb, "Debe de seleccionar un proyecto");
		
					
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		
		ta.getMovimiento().setEmpleado(SessionUtil.getEmpleadoId());
		
		//se genera el movimiento el detalle y se actualiza el folio
		TiposMovimiento tm= new TiposMovimiento();
		tm.setIdTipoMovimiento(SystemConstants.DEVOLUCION);
		ta.getMovimiento().setTiposMovimiento(tm);
		ta.getMovimiento().setAlmacen(almacen);
		ta.getMovimiento().setProyecto(ta.getMovimiento().getProyecto());
		ta.getMovimiento().setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		ta.getMovimiento().setFecha(new Date());
						
		Folios fte= foliosManager.getFolioMax (SystemConstants.DEVOLUCION);
		
		ta.getMovimiento().setFolio(fte.getAbrev()+"-"+fte.getConsecutivo());
		
		ArrayList<DetalleMovimiento> listDetailTransactionSAL =  (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionDEV");
		
		ArrayList<Producto> listProducto= new ArrayList<Producto>();
		Producto p= new Producto();
		
		for (DetalleMovimiento doc : listDetailTransactionSAL) {
			p = productManager.get(doc.getProducto().getIdProducto());	
						
			Proyecto proyecto= new Proyecto();
			proyecto.setIdProyecto(ta.getMovimiento().getProyecto().getIdProyecto());
			UnidadMedida um= new UnidadMedida();
			um.setIdUnidadMedida(p.getUnidadMedida().getIdUnidadMedida());
						
			p.setCantidad(p.getCantidad() + doc.getCantidad());
			p.setPrecioCompra(doc.getPrecioUnitario());
			
			listProducto.add(p);
		}
		
		this.transactionManager.saveDevolucion(ta.getMovimiento(), listDetailTransactionSAL, fte, listProducto);
		
		SessionUtil.setSessionAttribute("listDetailTransactionDEV", new ArrayList<DetalleMovimiento>());
		
		state.setDetailIdentifier(ta.getMovimiento().getFolio());
		state.setUri("/WEB-INF/zul/devoluciones/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ta.getMovimiento().getFolio());
		this.navigationControl.changeView(win, state);
	}
	
	public DevolucionesAdapter readDevolucion (Integer idMovimiento) {
		DevolucionesAdapter ta= new DevolucionesAdapter();
		
		ta.setMovimiento(transactionManager.getSalida(idMovimiento));
		ta.setDetalleMovimientos(transactionDetailManager.getDetails (ta.getMovimiento().getIdMovimiento()));
		
		return ta;
	}
	
	public void getDetailMovimientos (DevolucionesAdapter ta, Listbox lb, Label total) {
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

	
	public void createReportDevolucion (DevolucionesAdapter ta, Component mtwin){
		
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
		
		pBeanReport.setProyecto(ta.getProyecto());
				
		
		exportToPdfDevolucion (pBeanReport, ta);
	}	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void exportToPdfDevolucion(ProjectReportBean pBeanReport, DevolucionesAdapter ta) {
	 
    	HashMap<String, Object> parametters = new HashMap<String, Object>();
		parametters.put("REPORT_TITLE", "VALE DE DEVOLUCION");
		parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
		
		parametters.put("folioMovimiento", ta.getMovimiento().getFolio());
		parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(pBeanReport.getJsonListProducts()));
		parametters.put("fecha", DateFormatUtil.getFormatedDate(ta.getMovimiento().getFecha(), true));
		
		parametters.put("subtotal", pBeanReport.getSubtotal());
		parametters.put("iva", pBeanReport.getIva());
		parametters.put("total", pBeanReport.getTotal());
		
		parametters.put("proyecto", ta.getMovimiento().getProyecto().getNombre());
		parametters.put("area", ta.getMovimiento().getArea().getArea());
		parametters.put("observaciones", ta.getMovimiento().getObservaciones() == null ? "" : ta.getMovimiento().getObservaciones().trim());
		parametters.put("responsable", ta.getMovimiento().getEmpleado().getNombre()+" "+ta.getMovimiento().getEmpleado().getAPaterno()+" "+ta.getMovimiento().getEmpleado().getAMaterno());
		
		
		try {
			List dataSource = new ArrayList();
			dataSource.add(pBeanReport);
			String tname = "reporteDevolucion";
			Media mediaReport = ReportUtil.generateReport(dataSource, "valeDevolucion.jasper", ReportUtil.TASK_PDF, parametters, tname);
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
	
	////-----
	
	
	
	


	
	//devolciones de productos por proyecto entradas y salidas
	public void loadInOutputProject (Listbox lb, DevolucionesAdapter da, Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			da.getProyecto().setIdProyecto(( (Proyecto) prcb.getSelectedItem().getValue()).getIdProyecto());

		ArrayList<DevolucionBean> listdb=  this.productManager.getDevolucionesProyecto(da.getProyecto().getIdProyecto(), SessionUtil.getSucursalId());
		ArrayList<DevolucionBean> listsdb=  this.productManager.getDevolucionesProyectoSalidas(da.getProyecto().getIdProyecto(), SessionUtil.getSucursalId());
		
		for (DevolucionBean ld : listsdb) {
			listdb.add(ld);	
		}
		
		ListModelList<DevolucionitemAdapter> model = 
				new ListModelList<DevolucionitemAdapter>(DevolucionitemAdapter.getArray(listdb));
		lb.setModel(model);
		lb.setItemRenderer(new DevolucionListitemRenderer());
		
		//almancenar las devoluciones
		ArrayList<DevolucionBean> listDevoluciones = new ArrayList<DevolucionBean>();
		SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);	
				
	}
	
	@SuppressWarnings("unchecked")
	public void reintegroProductosClose (Listitem listitem) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DevolucionitemAdapter adapter = (DevolucionitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DevolucionBean outBean = adapter.getDevolucionBean();
		
		Component comp = listitem.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableDoublebox quantitybox = (IREditableDoublebox) devCant.getFirstChild();
		
		System.out.println("cantidad entrada: "+outBean.getCantidadEntrada());
		System.out.println("cantidad salida: "+outBean.getCantidadSalida());
		System.out.println("diferencia: "+outBean.getDiferencia());
		System.out.println("cantidad a devolver: "+quantitybox.getValue());
		System.out.println("id del producto: "+outBean.getIdProducto());
		
		if (quantitybox.getValue() > outBean.getDiferencia()) { 
			throw new WrongValueException(quantitybox, "La cantidad a devolver tiene que ser menor a la cantidad total de la diferencia");
		}
		else {
			outBean.setDevoluciones(quantitybox.getValue());
			System.out.println("Realiza salida");
			
			ArrayList<DevolucionBean> listDevoluciones = (ArrayList<DevolucionBean>) SessionUtil.getSessionAttribute("listDevoluciones");
			listDevoluciones.add(outBean);
			SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void save (DevolucionesAdapter ea, NavigationState state, Component win) {
		
		
		//Listbox lb = (Listbox) win.getFellowIfAny("oplb");
		Listbox lb = (Listbox) win.getFellowIfAny("cplb");
		ListModelList<DevolucionitemAdapter> lml = (ListModelList) lb.getModel();
		
		for (int i=0; i< lb.getItemCount(); i++ ) {
			DevolucionitemAdapter ouia= (DevolucionitemAdapter) lml.getElementAt(i);
			
			if (ouia.getDevolucionBean().getDevoluciones()  > 0) {
				System.out.println("id_producto:"+ouia.getDevolucionBean().getIdProducto());
				System.out.println("cantidad modificada:"+ouia.getDevolucionBean().getDevoluciones());
				System.out.println("cantidad entrada: "+ouia.getDevolucionBean().getCantidadEntrada());
				System.out.println("cantidad salida: "+ouia.getDevolucionBean().getCantidadSalida());
			}	
			
		}
		
		
		System.out.println("-------------------- ");
		
		ArrayList<DevolucionBean> listDevoluciones = (ArrayList<DevolucionBean>) SessionUtil.getSessionAttribute("listDevoluciones");
			
		
		double stockdefault = 0.0;
		double sobranteProyecto = 0.0;
		double stock = 0.0;
		double cantidad = 0.0;
		
		//se genera el movimiento de entrada srock y se actualiza el folio
		Movimientos movimientoEntradaStock= new Movimientos();
		
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		TiposMovimiento tm= new TiposMovimiento();
		//tm.setIdTipoMovimiento(SystemConstants.ENTRADA_STOCK);
		movimientoEntradaStock.setAlmacen(almacen);
		movimientoEntradaStock.setTiposMovimiento(tm);
		movimientoEntradaStock.setFactura(null);
		movimientoEntradaStock.setAlmacen(almacen);
		movimientoEntradaStock.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		movimientoEntradaStock.setFecha(new Date());
		movimientoEntradaStock.setOrdenCompra(null);
		movimientoEntradaStock.setProyecto(ea.getProyecto());
		movimientoEntradaStock.setArea(null);
		movimientoEntradaStock.setEmpleado(SessionUtil.getEmpleadoId());
		
		Folios fes= foliosManager.getFolioMax (SystemConstants.DEVOLUCION);
		movimientoEntradaStock.setFolio(fes.getAbrev()+"-"+fes.getConsecutivo());
		
		ArrayList<DetalleMovimiento> listEntradaStock= new ArrayList<DetalleMovimiento>();
		DetalleMovimiento entradaStock = new DetalleMovimiento ();
		
		
		//salida del almacen por reasignacion
		Movimientos movimientoSalidaReasignacion= new Movimientos();
		tm= new TiposMovimiento();
		tm.setIdTipoMovimiento(SystemConstants.SALIDA_REASIGNACION);
		movimientoSalidaReasignacion.setAlmacen(almacen);
		movimientoSalidaReasignacion.setTiposMovimiento(tm);
		movimientoSalidaReasignacion.setFactura(null);
		movimientoSalidaReasignacion.setAlmacen(almacen);
		movimientoSalidaReasignacion.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		movimientoSalidaReasignacion.setFecha(new Date());
		movimientoSalidaReasignacion.setOrdenCompra(null);
		movimientoSalidaReasignacion.setProyecto(ea.getProyecto());
		movimientoSalidaReasignacion.setArea(null);
		movimientoSalidaReasignacion.setEmpleado(SessionUtil.getEmpleadoId());
		
		Folios fsr= foliosManager.getFolioMax (SystemConstants.SALIDA_REASIGNACION);
		movimientoSalidaReasignacion.setFolio(fsr.getAbrev()+"-"+fsr.getConsecutivo());

		
		ArrayList<DetalleMovimiento> listSalidaReasignacion= new ArrayList<DetalleMovimiento>();
		DetalleMovimiento salidaReasignacion = new DetalleMovimiento ();
		
		
		ArrayList<Producto> listProducto= new ArrayList<Producto>();
		
		for (DevolucionBean ob : listDevoluciones ) {
			System.out.println("id_producto:"+ob.getIdProducto());
			System.out.println("Cantidad: "+ob.getCantidadSalida());
			System.out.println("Devolucion: "+ob.getDevoluciones());
		
			
			if (ob.getDevoluciones() > 0) {
			
				Producto product = this.productManager.get(ob.getIdProducto());
				//devolucion + el stock real = stock del proyecto
				stock = ob.getDevoluciones() + (product.getStock() == null ? 0 : product.getStock());
				
				/////////////////
				//cantidad = cantidad - devolucion proyecto
				cantidad = product.getCantidad() - ob.getDevoluciones();
				
				
				entradaStock = new DetalleMovimiento ();
				entradaStock.setProducto(product);
				//lo que se tenia en stock 
				entradaStock.setCantidadTotal(product.getStock()); 
				entradaStock.setCantidad(ob.getDevoluciones());
				entradaStock.setTipoMoneda(null);
				entradaStock.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
				entradaStock.setFecha(new Date());
				
				listEntradaStock.add(entradaStock);
				
				
				salidaReasignacion = new DetalleMovimiento ();
				salidaReasignacion.setProducto(product);
				salidaReasignacion.setCantidadTotal(product.getCantidad());
				salidaReasignacion.setCantidad(ob.getDevoluciones());
				salidaReasignacion.setFecha(new Date());
				salidaReasignacion.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
				
				listSalidaReasignacion.add(salidaReasignacion);
				
				//producto
				product.setStock(stock);
				product.setCantidad(cantidad);
				
				listProducto.add(product);
			}
			
		}
		
		this.transactionManager.saveDevoluciones (listProducto, movimientoEntradaStock, listEntradaStock, fes, movimientoSalidaReasignacion, listSalidaReasignacion, fsr);
		 
		EstatusProyecto estatus = new EstatusProyecto();
		/*estatus.setIdEstatusProyecto(SystemConstants.PROYECTO_CERRADO);
		ea.getProyecto().setEstatusProyecto(estatus);
		ea.getProyecto().setFechaFinal(new Date());
		
		this.projectManager.update(ea.getProyecto());*/ 
			
		state.setUri("/WEB-INF/zul/devoluciones/devoluciones.zul");
		state.startBreadCrumbsPathFromHome("Devoluciones Proyectos");
		navigationControl.changeView(win, state);
		
	}
	
	
	public void searchDevolucion(Listbox lb, DevolcionSearchAdapter dsa, NavigationState state){
		ArrayList<Movimientos> movimientos = this.transactionManager.searchDevolucion(dsa);
		
		ListModelList<Movimientos> model = new ListModelList<Movimientos>(movimientos);
		lb.setModel(model);
	}

	public void showDev(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Movimientos movimiento = (Movimientos)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(movimiento.getIdMovimiento());
			state.setUri("/WEB-INF/zul/devoluciones/detail.zul");
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
	
	

}
