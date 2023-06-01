package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.PurcharseAdapter;
import com.seidor.inventario.adapter.render.PurchaseOrderComboitemRenderer;
import com.seidor.inventario.adapter.render.StatusTypeOrderComboitemRenderer;
import com.seidor.inventario.adapter.render.TypePaymentComboitemRenderer;
import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.manager.EmployeeManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.model.Area;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.Etapa;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.model.TipoOrdenCompra;
import com.seidor.inventario.model.TipoPago;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.SessionUtil;

public class PurchaseOrderController {

	private PurchaseOrderManager purchaseOrderManager;
	private NavigationControl navigationControl;
	
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

	
	

	//business logic 
	public void search(Listbox lb, PurchaseOrderSearchAdapter psa, NavigationState state){
		ArrayList<OrdenCompra> provider = this.purchaseOrderManager.search(psa);
		
		ListModelList<OrdenCompra> model = new ListModelList<OrdenCompra>(provider);
		lb.setModel(model);
	}
	

	public void loadPurchaseOrder(Combobox combo) {
		ArrayList<OrdenCompra> purchaseOrders = this.purchaseOrderManager.getAll();
		if (purchaseOrders != null) {
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
		oc.setFactura(new Factura());
		oc.setProyecto(new Proyecto());
		oc.setTipoOrdenCompra(new TipoOrdenCompra());
		oc.setEstatusOrdenCompra(new EstatusOrdenCompra());
		oc.setTipoPago(new TipoPago());
		p.setOrderCompra(oc);
		
		return p;
	}
	
	
	public void save(PurcharseAdapter pa, NavigationState state, Component win){
		
		
		Combobox cli = (Combobox) win.getFellowIfAny("clicb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getOrderCompra().setCliente((Cliente) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un cliente");
		
		Combobox fa = (Combobox) win.getFellowIfAny("invcb");
		if (fa != null && fa.getSelectedItem()!=null )
			pa.getOrderCompra().setFactura((Factura) fa.getSelectedItem().getValue());
		else 
			throw new WrongValueException(fa, "Debe de seleccionar una factura");
		
		if (pa.getOrderCompra().getFactura().getIdFactura() == 0)
			pa.getOrderCompra().setFactura(null);
			
		
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
		
		/*Combobox empl = (Combobox) win.getFellowIfAny("cbemp");
		if (empl != null && empl.getSelectedItem()!=null )
			pa.getOrderCompra().setEmpleado((Empleado) empl.getSelectedItem().getValue());
		else 
			throw new WrongValueException(empl, "Debe de seleccionar un empleado");*/
		pa.getOrderCompra().setEmpleado((Empleado)SessionUtil.getEmpleadoId());
		
		Combobox proy = (Combobox) win.getFellowIfAny("prcb");
		if (proy != null && proy.getSelectedItem()!=null )
			pa.getOrderCompra().setProyecto((Proyecto) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un proyecto");
		
		Combobox soccb = (Combobox) win.getFellowIfAny("soccb");
		if (soccb != null && soccb.getSelectedItem()!=null )
			pa.getOrderCompra().setEstatusOrdenCompra((EstatusOrdenCompra) soccb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un estatus de la orden de pago");
		
		Combobox tpcb = (Combobox) win.getFellowIfAny("tpcb");
		if (tpcb != null && tpcb.getSelectedItem()!=null )
			pa.getOrderCompra().setTipoPago((TipoPago) tpcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un tipo de pago");
				
			
		pa.getOrderCompra().setFechaRecepAlmacen(new Date());
		
		
		this.purchaseOrderManager.save(pa.getOrderCompra());
		
		state.setDetailIdentifier(pa.getOrderCompra().getIdOrdenCompra());
		state.setUri("/WEB-INF/zul/oc/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getOrderCompra().getDescripcion());
		this.navigationControl.changeView(win, state);
	}
	
	
	public void update(PurcharseAdapter pa, NavigationState state, Component win){
		
		Combobox cli = (Combobox) win.getFellowIfAny("clicb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getOrderCompra().setCliente((Cliente) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un cliente");
		
		Combobox fa = (Combobox) win.getFellowIfAny("invcb");
		if (fa != null && fa.getSelectedItem()!=null )
			pa.getOrderCompra().setFactura((Factura) fa.getSelectedItem().getValue());
		else 
			throw new WrongValueException(fa, "Debe de seleccionar una factura");
		
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
		
		/*Combobox empl = (Combobox) win.getFellowIfAny("cbemp");
		if (empl != null && empl.getSelectedItem()!=null )
			pa.getOrderCompra().setEmpleado((Empleado) empl.getSelectedItem().getValue());
		else 
			throw new WrongValueException(empl, "Debe de seleccionar un empleado");*/
		pa.getOrderCompra().setEmpleado((Empleado)SessionUtil.getEmpleadoId());
		
		Combobox proy = (Combobox) win.getFellowIfAny("prcb");
		if (proy != null && proy.getSelectedItem()!=null )
			pa.getOrderCompra().setProyecto((Proyecto) proy.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un proyecto");
		
		Combobox soccb = (Combobox) win.getFellowIfAny("soccb");
		if (soccb != null && soccb.getSelectedItem()!=null )
			pa.getOrderCompra().setEstatusOrdenCompra((EstatusOrdenCompra) soccb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un estatus de la orden de pago");
		
		Combobox tpcb = (Combobox) win.getFellowIfAny("tpcb");
		if (tpcb != null && tpcb.getSelectedItem()!=null )
			pa.getOrderCompra().setTipoPago((TipoPago) tpcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(proy, "Debe de seleccionar un tipo de pago");
				
		
		this.purchaseOrderManager.update(pa.getOrderCompra());
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getOrderCompra().getDescripcion());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getOrderCompra().getDescripcion());
		}
		this.navigationControl.changeToPrevious(win);
		
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
		
	
	public PurcharseAdapter read(Integer providerId){
		PurcharseAdapter pa = new PurcharseAdapter();
	
		OrdenCompra oc = this.purchaseOrderManager.get(providerId);
		pa.setOrderCompra(oc);
		
		return pa;
	}
	
	
	public PurcharseAdapter readForEdit(Integer providerId){
		PurcharseAdapter pa = new PurcharseAdapter();
		
		OrdenCompra oc = this.purchaseOrderManager.get(providerId);
		
		if (oc.getFactura() == null)
			oc.setFactura(new Factura());
		
		pa.setOrderCompra(oc);
		
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
	
}
