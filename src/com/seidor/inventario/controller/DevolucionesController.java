package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.DevolucionesProjectAdapter;
import com.seidor.inventario.adapter.beans.DevolucionBean;
import com.seidor.inventario.adapter.beans.OutBean;
import com.seidor.inventario.adapter.listitem.DevolucionitemAdapter;
import com.seidor.inventario.adapter.render.DevolucionListitemRenderer;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.manager.EntryManager;
import com.seidor.inventario.manager.FoliosManager;
import com.seidor.inventario.manager.OutputManager;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.ProjectManager;
import com.seidor.inventario.manager.StockManager;
import com.seidor.inventario.manager.TransactionManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.Folios;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.TiposMovimiento;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
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

	//logic
	public DevolucionesProjectAdapter read () {
		
		DevolucionesProjectAdapter cpa = new DevolucionesProjectAdapter ();
		
		cpa.setProyecto(new Proyecto ());
		cpa.setEntrada(new ArrayList<Entrada>());
		cpa.setSalida(new ArrayList<OutBean>());
		cpa.setDevolucionBean(new DevolucionBean());
		
		return cpa;
	}
	
	
	//devolciones de productos por proyecto entradas y salidas
	public void loadInOutputProject (Listbox lb, DevolucionesProjectAdapter da, Component win) {
		
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
	public void save (DevolucionesProjectAdapter ea, NavigationState state, Component win) {
		
		
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
		tm.setIdTipoMovimiento(SystemConstants.ENTRADA_STOCK);
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
		
		Folios fes= foliosManager.getFolioMax (SystemConstants.ENTRADA_STOCK);
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

}
