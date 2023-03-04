package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.CloseProjectAdapter;
import com.seidor.inventario.adapter.ResignProyectAdapter;
import com.seidor.inventario.adapter.beans.CierreBean;
import com.seidor.inventario.adapter.beans.CloseBean;
import com.seidor.inventario.adapter.beans.EntradasProyectoBean;
import com.seidor.inventario.adapter.beans.OutBean;
import com.seidor.inventario.adapter.beans.ReasignedBean;
import com.seidor.inventario.adapter.beans.ReportCostoInventario;
import com.seidor.inventario.adapter.beans.SalidaProyectoBean;
import com.seidor.inventario.adapter.listitem.CloseitemAdapter;
import com.seidor.inventario.adapter.listitem.OutputitemAdapter;
import com.seidor.inventario.adapter.listitem.ReasigneditemAdapter;
import com.seidor.inventario.adapter.render.CloseListitemRenderer;
import com.seidor.inventario.adapter.render.OutputListitemRenderer;
import com.seidor.inventario.adapter.render.ReasignedListitemRenderer;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableCheckbox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.OutputManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.StockManager;
import com.seidor.inventario.model.Categoria;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.model.TipoTrabajo;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.SessionUtil;

public class CloseProjectController {

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
	private StockManager stockManager;
	
	
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
	
	public StockManager getStockManager() {
		return stockManager;
	}

	public void setStockManager(StockManager stockManager) {
		this.stockManager = stockManager;
	}

	//logic
	public CloseProjectAdapter read () {
		
		CloseProjectAdapter cpa = new CloseProjectAdapter ();
		
		cpa.setProyecto(new Proyecto ());
		cpa.setEntrada(new ArrayList<Entrada>());
		cpa.setSalida(new ArrayList<OutBean>());
		cpa.setCloseBean(new CloseBean());
		
		return cpa;
	}
	
	
	//logic
	public ResignProyectAdapter readAsigned () {
		
		ResignProyectAdapter rpa = new ResignProyectAdapter ();
		
		rpa.setProyecto(new Proyecto ());
		rpa.setProyectoDestino(new Proyecto());
		rpa.setEntrada(new ArrayList<Entrada>());
		rpa.setSalida(new ArrayList<OutBean>());
		rpa.setReasignBean(new CloseBean());
		
		return rpa;
	}

	
	public void loadOutputProject (Listbox lb, CloseProjectAdapter ca, Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			ca.setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		
		ListModelList<OutputitemAdapter> model = 
				new ListModelList<OutputitemAdapter>(OutputitemAdapter.getArray(getAllOutputProject(outputManager.getOutputAll(ca.getProyecto().getIdProyecto()))));
		lb.setModel(model);
		lb.setItemRenderer(new OutputListitemRenderer());
		
		//almancenar las devoluciones
		ArrayList<OutBean> listDevoluciones = new ArrayList<OutBean>();
		SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);
		
				
	}
	
	//cierre del proyecto entradas y salidas
	public void loadInOutputProject (Listbox lb, CloseProjectAdapter ca, Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			ca.getProyecto().setIdProyecto(( (Proyecto) prcb.getSelectedItem().getValue()).getIdProyecto());

		ListModelList<CloseitemAdapter> model = 
				new ListModelList<CloseitemAdapter>(CloseitemAdapter.getArray(this.productManager.getCloseProyecto(ca.getProyecto().getIdProyecto())));
		lb.setModel(model);
		lb.setItemRenderer(new CloseListitemRenderer());
		
		//almancenar las devoluciones
		ArrayList<CloseBean> listDevoluciones = new ArrayList<CloseBean>();
		SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);	
				
	}
	
	

	//listado de devoluciones manuales
	public void loadDevolucionProject (Listbox lb, CloseProjectAdapter ca, Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			ca.setProyecto((Proyecto) prcb.getSelectedItem().getValue());
		
	}	
	

	private ArrayList<OutBean> getAllOutputProject(ArrayList<Salida> outputAll) {
		
		
		ArrayList<OutBean> outBean = new ArrayList<OutBean>();
		
		for (Salida s : outputAll ) {
			OutBean ob= new OutBean ();
			
			ob.setSalida(s);
			ob.setCantidad(0);
			
			outBean.add(ob);
			
		}
		
		return outBean;
	}
	
	
	@SuppressWarnings("unchecked")
	public void reintegroProductos (Listitem listitem) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		OutputitemAdapter adapter = (OutputitemAdapter) lb.getModel().getElementAt(selectedIndex);
		OutBean outBean = adapter.getOutBean();
		
		Component comp = listitem.getFirstChild();
		
		Hlayout validHL= (Hlayout) comp.getFirstChild();
		IREditableCheckbox valid = (IREditableCheckbox) validHL.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableIntbox quantitybox = (IREditableIntbox) devCant.getFirstChild();
		
		System.out.println("cantidad a devolver: "+valid.getValue());
		System.out.println("cantidad a devolver: "+quantitybox.getValue());
		System.out.println("id del producto: "+outBean.getSalida().getProducto().getIdProducto());
		
		if (outBean.getSalida().getCantidad() < quantitybox.getValue() ) { 
			throw new WrongValueException(valid, "La cantidad a devolver tiene que ser menor a la cantidad de la salida");
		}
		else {
			outBean.setCantidad(quantitybox.getValue());
			System.out.println("Realiza salida");
			
			ArrayList<OutBean> listDevoluciones = (ArrayList<OutBean>) SessionUtil.getSessionAttribute("listDevoluciones");
			listDevoluciones.add(outBean);
			SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);
			
		}
	
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void reintegroProductosClose (Listitem listitem) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		CloseitemAdapter adapter = (CloseitemAdapter) lb.getModel().getElementAt(selectedIndex);
		CloseBean outBean = adapter.getCloseBean();
		
		Component comp = listitem.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableIntbox quantitybox = (IREditableIntbox) devCant.getFirstChild();
		
		System.out.println("cantidad a devolver: "+quantitybox.getValue());
		System.out.println("id del producto: "+outBean.getIdProducto());
		
		if (outBean.getCantidadSalida() < quantitybox.getValue() ) { 
			throw new WrongValueException(quantitybox, "La cantidad a devolver tiene que ser menor a la cantidad de la salida");
		}
		else {
			outBean.setDevoluciones(quantitybox.getValue());
			System.out.println("Realiza salida");
			
			ArrayList<CloseBean> listDevoluciones = (ArrayList<CloseBean>) SessionUtil.getSessionAttribute("listDevoluciones");
			listDevoluciones.add(outBean);
			SessionUtil.setSessionAttribute("listDevoluciones", listDevoluciones);
			
		}
		
	}
	
	
	

	@SuppressWarnings("unchecked")
	public void save (CloseProjectAdapter ea, NavigationState state, Component win) {
		
		
		Listbox lb = (Listbox) win.getFellowIfAny("oplb");
		ListModelList<CloseitemAdapter> lml = (ListModelList) lb.getModel();
		
		for (int i=0; i< lb.getItemCount(); i++ ) {
			CloseitemAdapter ouia= (CloseitemAdapter) lml.getElementAt(i);
			
			if (ouia.getCloseBean().getDevoluciones()  > 0) {
				System.out.println("id_producto:"+ouia.getCloseBean().getIdProducto());
				System.out.println("cantidad modificada:"+ouia.getCloseBean().getDevoluciones());
				System.out.println("cantidad entrada: "+ouia.getCloseBean().getCantidadEntrada());
				System.out.println("cantidad salida: "+ouia.getCloseBean().getCantidadSalida());
			}	
			
		}
		
		
		System.out.println("-------------------- ");
		
		ArrayList<CloseBean> listDevoluciones = (ArrayList<CloseBean>) SessionUtil.getSessionAttribute("listDevoluciones");
			
		
		int stockdefault = 0;
		int sobranteProyecto = 0;
		int stock = 0;
		int cantidad = 0;
		
		for (CloseBean ob : listDevoluciones ) {
			System.out.println("id_producto:"+ob.getIdProducto());
			System.out.println("Cantidad: "+ob.getCantidadSalida());
			System.out.println("Devolucion: "+ob.getDevoluciones());
			
			Producto product = this.productManager.get(ob.getIdProducto());
			Entrada entrada= this.entryManager.get(product.getIdProducto());
			MovimientosStock movSctock = new MovimientosStock();

			//la suma de las entradas del proyecto - la suma de las salidas del proyecto =  me dan el (stock defaul del proyecto )
			stockdefault = ob.getCantidadEntrada() - ob.getCantidadSalida();

			//la suma del stock default + devolucion procto = sobrante total del proyecto
			sobranteProyecto = stockdefault + ob.getDevoluciones();
			
			//la suma del sobfante total del proyecto + el stock real = stock del proyecto
			stock = sobranteProyecto + (product.getStock() == null ? 0 : product.getStock());
			
			/////////////////
			//cantidad = cantidad +devolucion proyecto
			cantidad = product.getCantidad() + ob.getDevoluciones();
			
			
			//producto
			product.setStock(stock);
			product.setCantidad(cantidad);
			
			
			movSctock.setProducto(product);
			movSctock.setProyecto(entrada.getProyecto());
			movSctock.setCantidad(ob.getDevoluciones());
			movSctock.setTipo(SystemConstants.TIPO_ENTRADA);
			movSctock.setEstatus(SystemConstants.ESTATUS_STOCK_ACTIVO);
			movSctock.setFecha(new Date());
			
			
			Salida salida = new Salida ();
			TipoTrabajo tt = new TipoTrabajo();
			salida.setCantidad(ob.getDevoluciones());
			salida.setProducto(product);
			salida.setProyecto(entrada.getProyecto());
			salida.setEmpleado(entrada.getEmpleado());
			tt.setIdTipoTrabajo(3);
			salida.setTipoTrabajo(tt);
			salida.setUnidadMedida(entrada.getUnidadMedida());
			salida.setClaveMueble("");
			salida.setFecha(new Date());
			salida.setEstatus(SystemConstants.SALIDA_POR_REASIGNACION);
			
			this.stockManager.saveEntryStock(movSctock, salida, product);
			
		}
		 
		EstatusProyecto estatus = new EstatusProyecto();
		estatus.setIdEstatusProyecto(SystemConstants.PROYECTO_CERRADO);
		ea.getProyecto().setEstatusProyecto(estatus);
		ea.getProyecto().setFechaFinal(new Date());
		
		this.projectManager.update(ea.getProyecto()); 
			
	
		/*
		 * 
		 * ListModelList<BudgetHListitemAdapter> lml = (ListModelList)lb.getModel();
		BudgetHListitemAdapter c = (BudgetHListitemAdapter)lml.getElementAt(lb.getSelectedIndex());
		
		 * if (lb.getSelectedIndex() >= 0) {
			BudgetHListitemAdapter bol = (BudgetHListitemAdapter) lb.getModel()
					.getElementAt(lb.getSelectedIndex());
			BudgetHAdapter budgetHAdapter= bol.getBudgetHAdapter();
			
			ArrayList<BudgetHHistoric> historic = this.budgetHManager.getHistoricById(budgetHAdapter.getBudgetH().getId());
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("historic", historic);
			NavigationControl.openModalWindow(null, "/WEB-INF/zul/budget/modalhistorical.zul", map);
		}
		 * 
		 * */
		
		state.setUri("/WEB-INF/zul/reports/cierreProyecto.zul");
		state.startBreadCrumbsPathFromHome("Cierre de Proyectos");
		navigationControl.changeView(win, state);
		 
		
	}
	
	
		
	public void searchReportClose(CierreBean cierreBean, Listbox lb, NavigationState state, Component win){
		
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) {
			cierreBean.setProyecto(((Proyecto) prcb.getSelectedItem().getValue()));
		
		
			ArrayList<CierreBean> reportProducts = this.productManager.getCierreProyecto(cierreBean.getProyecto().getIdProyecto());
			
			ListModelList<CierreBean> model = new ListModelList<CierreBean>(reportProducts);
			lb.setModel(model);
		}
		
	}
	
	public void loadReportES (Listbox lb, EntradasProyectoBean e, Listbox lbs, SalidaProyectoBean s, Component win) {
		
		int idProyeto= 0;
		
		Label choice1 = (Label) win.getFellowIfAny("choice1");

		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			idProyeto = ((Proyecto) prcb.getSelectedItem().getValue()).getIdProyecto();
		
		
		if (choice1.getValue().equals("1")) {
			
			ArrayList<EntradasProyectoBean> ep = this.productManager.getEntradaProyecto(idProyeto);
			ListModelList<EntradasProyectoBean> model = new ListModelList<EntradasProyectoBean>(ep);
			lb.setModel(model);
			
			lb.setVisible(true);
			lbs.setVisible(false);
		}
		
		if (choice1.getValue().equals("2")) {
			ArrayList<SalidaProyectoBean> sp = this.productManager.getSalidasProyecto(idProyeto);
			ListModelList<SalidaProyectoBean> models = new ListModelList<SalidaProyectoBean>(sp);
			lbs.setModel(models);
			
			lb.setVisible(false);
			lbs.setVisible(true);
		}
		
	}
	
	
	//seagignacion de proyecto entradas y salidas
	public void loadInOutputProjectR (Listbox lb, ResignProyectAdapter ca, Component win) {
		
		Combobox prcb = (Combobox) win.getFellowIfAny("prcb");
		if (prcb != null && prcb.getSelectedItem()!=null ) 
			ca.getProyecto().setIdProyecto(( (Proyecto) prcb.getSelectedItem().getValue()).getIdProyecto());
		
		ListModelList<ReasigneditemAdapter> model = 
				new ListModelList<ReasigneditemAdapter>(ReasigneditemAdapter.getArray(this.productManager.getReasignedProyecto(ca.getProyecto().getIdProyecto())));
		lb.setModel(model);
		lb.setItemRenderer(new ReasignedListitemRenderer());
		
		//almancenar las reasignaciones de productos
		ArrayList<ReasignedBean> listReasignacion = new ArrayList<ReasignedBean>();
		SessionUtil.setSessionAttribute("listReasignacion", listReasignacion);	
				
	}

	//reasignacion de productos a proyectos
	@SuppressWarnings("unchecked")
	public void reasignaProductos(Listitem listitem) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		ReasigneditemAdapter adapter = (ReasigneditemAdapter) lb.getModel().getElementAt(selectedIndex);
		ReasignedBean outBean = adapter.getReasignedBean();
		
		Component comp = listitem.getFirstChild();
		
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		comp = comp.getNextSibling();
		
		Hlayout devCant= (Hlayout) comp.getFirstChild();
		IREditableIntbox quantitybox = (IREditableIntbox) devCant.getFirstChild();
		
		System.out.println("cantidad a devolver: "+quantitybox.getValue());
		System.out.println("id del producto: "+outBean.getIdProducto());
		
		if (outBean.getCantidadSalida() < quantitybox.getValue() ) { 
			throw new WrongValueException(quantitybox, "La cantidad a devolver tiene que ser menor a la cantidad de la salida");
		}
		else {
			outBean.setReasignacion(quantitybox.getValue());
			System.out.println("Realiza salida");
			
			ArrayList<ReasignedBean> listReasignacion = (ArrayList<ReasignedBean>) SessionUtil.getSessionAttribute("listReasignacion");
			listReasignacion.add(outBean);
			SessionUtil.setSessionAttribute("listReasignacion", listReasignacion);
			
		}
		
	}
	
	
	
	//reasignacion de proyecto
	@SuppressWarnings("unchecked")
	public void saveReasign (ResignProyectAdapter rpa, NavigationState state, Component win) {
		
		
		Listbox lb = (Listbox) win.getFellowIfAny("rplb");
		ListModelList<ReasigneditemAdapter> lml = (ListModelList) lb.getModel();
		
		Combobox pDestino = (Combobox) win.getFellowIfAny("prcbd");
		if (pDestino != null && pDestino.getSelectedItem()!=null )
			rpa.getProyectoDestino().setIdProyecto(((Proyecto) pDestino.getSelectedItem().getValue()).getIdProyecto());
		else 
			throw new WrongValueException(pDestino, "Debe de seleccionar el proyecto destino");
		
		
		for (int i=0; i< lb.getItemCount(); i++ ) {
			ReasigneditemAdapter ouia= (ReasigneditemAdapter) lml.getElementAt(i);
			
			if (ouia.getReasignedBean().getReasignacion()  > 0) {
				System.out.println("id_producto:"+ouia.getReasignedBean().getIdProducto());
				System.out.println("cantidad modificada:"+ouia.getReasignedBean().getReasignacion());
				System.out.println("cantidad entrada: "+ouia.getReasignedBean().getCantidadEntrada());
				System.out.println("cantidad salida: "+ouia.getReasignedBean().getCantidadSalida());
			}	
			
		}
		
		
		System.out.println("-------------------- ");
		
		ArrayList<ReasignedBean> listReasignacion = (ArrayList<ReasignedBean>) SessionUtil.getSessionAttribute("listReasignacion");
			/*
			 * listReasignacion
			 * */
		
		int stockdefault = 0;
		int sobranteProyecto = 0;
		int cantidad = 0;
		
		for (ReasignedBean ob : listReasignacion ) {
			System.out.println("id_producto:"+ob.getIdProducto());
			System.out.println("Cantidad: "+ob.getCantidadSalida());
			System.out.println("Devolucion: "+ob.getReasignacion());
			
			Producto product = this.productManager.get(ob.getIdProducto());
			Entrada entrada= this.entryManager.get(product.getIdProducto());
			

			//la suma de las entradas del proyecto - la suma de las salidas del proyecto =  me dan el (stock defaul del proyecto )
			stockdefault = ob.getCantidadEntrada() - ob.getCantidadSalida();

			//la suma del stock default + devolucion procto = sobrante total del proyecto
			sobranteProyecto = stockdefault + ob.getReasignacion();
			
			
			/////////////////
			//cantidad = cantidad +devolucion proyecto
			cantidad = product.getCantidad() + ob.getReasignacion();
			
			
			//producto
			//product.setStock(stock);
			product.setCantidad(cantidad);
			
			Salida salida = new Salida ();
			TipoTrabajo tt = new TipoTrabajo();
			salida.setCantidad(ob.getReasignacion());
			salida.setProducto(product);
			salida.setProyecto(entrada.getProyecto());
			salida.setEmpleado(entrada.getEmpleado());
			tt.setIdTipoTrabajo(3);
			salida.setTipoTrabajo(tt);
			salida.setUnidadMedida(entrada.getUnidadMedida());
			salida.setClaveMueble("");
			salida.setFecha(new Date());
			salida.setEstatus(SystemConstants.SALIDA_POR_REASIGNACION);
			
			
			entrada.setProducto(product);
			entrada.setProyecto(rpa.getProyectoDestino());
			entrada.setCantidad(ob.getReasignacion());
			entrada.setEstatus(SystemConstants.ENTRADA_POR_REASIGNACION);
			entrada.setFecha(new Date());
			
			
			this.entryManager.saveReasignedEntryProyect(entrada, salida, product);
			
		}
		 
		 
		
		state.setUri("/WEB-INF/zul/product/changeProduct.zul");
		state.startBreadCrumbsPathFromHome("Reasignación de productos");
		navigationControl.changeView(win, state);
	}
		
	

}