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

import com.google.gson.Gson;
import com.seidor.inventario.adapter.TraspasosAdapter;
import com.seidor.inventario.adapter.beans.DetailProductBean;
import com.seidor.inventario.adapter.beans.ProjectReportBean;
import com.seidor.inventario.adapter.listitem.DetailTransactionitemAdapter;
import com.seidor.inventario.adapter.render.DetailTransactionTrasListitemRenderer;
import com.seidor.inventario.adapter.search.TraspasoSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.manager.FoliosManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.TransactionDetailManager;
import com.seidor.inventario.manager.TransactionManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Empleado;
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

public class TraspasosController {
	
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private ProductManager productManager;
	
	
	@Autowired
	private ProjectManager projectManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private FoliosManager foliosManager;
	
	@Autowired
	private TransactionDetailManager transactionDetailManager;
	
	
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
	public TraspasosAdapter read () {
		
		TraspasosAdapter tra = new TraspasosAdapter ();
		
		Movimientos m= new Movimientos();
		m.setArea(new Area());
		m.setEmpleado(new Empleado());
		tra.setMovimiento(m);
		tra.setDetalleMovimientos(new ArrayList<DetalleMovimiento>());
		

		tra.setAlmacenOrigen(SessionUtil.getSucursalId());
		tra.setDesAlmacenOrigen(SessionUtil.getSucursaldUserId());
		
		if (SystemConstants.ALMACEN_TOLUCA == tra.getAlmacenOrigen()) {
			tra.setAlmacenDestino(SystemConstants.ALMACEN_TEPALCATES);
			tra.setDesAlmacenDestino("TEPALCATES");
		}	
		else {
			tra.setAlmacenDestino(SystemConstants.ALMACEN_TOLUCA);
			tra.setDesAlmacenDestino("TOLUCA");
		}	
		
		return tra;
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
		
		SessionUtil.setSessionAttribute("listDetailTransactionTRAS", new ArrayList<DetalleMovimiento>());
		
		ListModelList<DetailTransactionitemAdapter> lml = new ListModelList<DetailTransactionitemAdapter>();
		if(edit) {
			lb.setItemRenderer(new DetailTransactionTrasListitemRenderer(SystemConstants.TRASPASO_ALMACEN));
		}
		lb.setModel(lml);
		
	}


	@SuppressWarnings("unchecked")
	public void saveOrUpdateTraspasoAlmacen(EditableListitem listitem, Integer idStatusMovimiento) {
		
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
		
		System.out.println("cantidad traspaso: "+quantitybox.getValue() +" cantidad: "+doc.getCantidad());
		System.out.println("id del producto: "+doc.getProducto().getIdProducto());
		
		
		double compCant = doc.getCantidadTotal() - (doc.getCantidad() + quantitybox.getValue());
		Boolean flagUpdateOC = Boolean.FALSE; 
		
		if(compCant >= 0) {
			flagUpdateOC = Boolean.TRUE;
		} else
		if (compCant < 0) {
			throw new WrongValueException(quantitybox, "La cantidad de traspaso entre almacenes tiene que ser menor o igual a la cantidad existente");
		}	
		
	    if (flagUpdateOC) {
			doc.setCantidad(quantitybox.getValue());
			doc.setFecha(new Date());
			doc.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
			
			System.out.println("Realiza traspaso");
			
			//actualizar el detalle de las movimientos
			if (doc.getIdDetalleMovimiento() == 0) {
				
				//listDetailTransaction
				ArrayList<DetalleMovimiento> listDetailTransactionDEV = (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionTRAS");
				listDetailTransactionDEV.add(doc);
				SessionUtil.setSessionAttribute("listDetailTransactionTRAS", listDetailTransactionDEV);	
				
			}	
			
			
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void saveTransactionTras(TraspasosAdapter ta, NavigationState state, Component win){
					
		Combobox areacb = (Combobox) win.getFellowIfAny("areacb");
		if (areacb != null && areacb.getSelectedItem()!=null )
			ta.getMovimiento().setArea((Area) areacb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areacb, "Debe de seleccionar un area");
		
		Combobox areaempcb = (Combobox) win.getFellowIfAny("areaempcb");
		if (areaempcb != null && areaempcb.getSelectedItem()!=null )
			ta.getMovimiento().setEmpleado((Empleado) areaempcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(areaempcb, "Debe de seleccionar un empleado");
		
		Almacen almacen= new Almacen();
		almacen.setIdAlmacen(SessionUtil.getSucursalId());
		
		
		//se genera el movimiento el detalle y se actualiza el folio
		TiposMovimiento tm= new TiposMovimiento();
		tm.setIdTipoMovimiento(SystemConstants.TRASPASO_ALMACEN);
		ta.getMovimiento().setTiposMovimiento(tm);
		ta.getMovimiento().setAlmacen(almacen);
		ta.getMovimiento().setProyecto(null);
		ta.getMovimiento().setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_ACTIVO);
		ta.getMovimiento().setFecha(new Date());
						
		Folios fte= foliosManager.getFolioMax (SystemConstants.TRASPASO_ALMACEN);
		
		ta.getMovimiento().setFolio(fte.getAbrev()+"-"+fte.getConsecutivo());
		
		ArrayList<DetalleMovimiento> listDetailTransactionSAL =  (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransactionTRAS");
		
		ArrayList<Producto> listProducto= new ArrayList<Producto>();
		Producto p= new Producto();
		Producto pDestino= new Producto();
		
		for (DetalleMovimiento doc : listDetailTransactionSAL) {
			p = productManager.get(doc.getProducto().getIdProducto());	
			pDestino = productManager.getCodigo(p.getCodigo(), ta.getAlmacenDestino());	
			
			UnidadMedida um= new UnidadMedida();
			um.setIdUnidadMedida(p.getUnidadMedida().getIdUnidadMedida());
						
			p.setCantidad(p.getCantidad() - doc.getCantidad());
			p.setPrecioCompra(doc.getPrecioUnitario());
			
			pDestino.setCantidad(pDestino.getCantidad() + doc.getCantidad());
			
			listProducto.add(p);
			listProducto.add(pDestino);
		}
		
		this.transactionManager.saveDevolucion(ta.getMovimiento(), listDetailTransactionSAL, fte, listProducto);
		
		SessionUtil.setSessionAttribute("listDetailTransactionTRAS", new ArrayList<DetalleMovimiento>());
		
		state.setDetailIdentifier(ta.getMovimiento().getFolio());
		state.setUri("/WEB-INF/zul/traspasos/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ta.getMovimiento().getFolio());
		this.navigationControl.changeView(win, state);
	}
	
	public TraspasosAdapter readTraspasos (Integer idMovimiento) {
		TraspasosAdapter ta= new TraspasosAdapter();
		
		ta.setMovimiento(transactionManager.getSalida(idMovimiento));
		ta.setDetalleMovimientos(transactionDetailManager.getDetails (ta.getMovimiento().getIdMovimiento()));
		
		return ta;
	}
	
	public void getDetailMovimientos (TraspasosAdapter ta, Listbox lb, Label total) {
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

	
	public void createReportTraspasos (TraspasosAdapter ta, Component mtwin){
		
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
		
				
		
		exportToPdfTraspaso (pBeanReport, ta);
	}	
	
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private void exportToPdfTraspaso(ProjectReportBean pBeanReport, TraspasosAdapter ta) {
	 
    	HashMap<String, Object> parametters = new HashMap<String, Object>();
		parametters.put("REPORT_TITLE", "VALE DE TRASPASO ALMACEN");
		parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
		
		parametters.put("folioMovimiento", ta.getMovimiento().getFolio());
		parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(pBeanReport.getJsonListProducts()));
		parametters.put("fecha", DateFormatUtil.getFormatedDate(ta.getMovimiento().getFecha(), true));
		
		parametters.put("subtotal", pBeanReport.getSubtotal());
		parametters.put("iva", pBeanReport.getIva());
		parametters.put("total", pBeanReport.getTotal());
		
		parametters.put("proyecto", "");
		parametters.put("area", ta.getMovimiento().getArea().getArea());
		parametters.put("responsable", ta.getMovimiento().getEmpleado().getNombre()+" "+ta.getMovimiento().getEmpleado().getAPaterno()+" "+ta.getMovimiento().getEmpleado().getAMaterno());
		
		if (SystemConstants.ALMACEN_TOLUCA == SessionUtil.getSucursalId()) {
			parametters.put("almacenOrigen", "TOLUCA");
			parametters.put("almacenDestino", "TEPALCETES");
		}	
		else {
			parametters.put("almacenOrigen", "TEPALCETES");
			parametters.put("almacenDestino", "TOLUCA");
			
		}
				
		
		try {
			List dataSource = new ArrayList();
			dataSource.add(pBeanReport);
			String tname = "reporteTraspaso";
			Media mediaReport = ReportUtil.generateReport(dataSource, "valeTraspaso.jasper", ReportUtil.TASK_PDF, parametters, tname);
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
	 
	 
	 public void search(Listbox lb, TraspasoSearchAdapter dsa, NavigationState state){
		ArrayList<Movimientos> movimientos = this.transactionManager.searchTraspasos(dsa);
		
		ListModelList<Movimientos> model = new ListModelList<Movimientos>(movimientos);
		lb.setModel(model);
	}

	public void showTras(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Movimientos movimiento = (Movimientos)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(movimiento.getIdMovimiento());
			state.setUri("/WEB-INF/zul/traspasos/detail.zul");
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
