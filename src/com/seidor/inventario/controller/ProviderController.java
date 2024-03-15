package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.ProviderAdapter;
import com.seidor.inventario.adapter.listitem.DataBankListitemAdapter;
import com.seidor.inventario.adapter.render.DataBanckEditableListitemRenderer;
import com.seidor.inventario.adapter.render.ProveedorComboitemRenderer;
import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.IREditableTextbox;
import com.seidor.inventario.manager.DatosBancariosManager;
import com.seidor.inventario.manager.ProviderManager;
import com.seidor.inventario.manager.PurchaseOrderManager;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.model.TipoMoneda;
import com.seidor.inventario.model.TipoPago;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.SessionUtil;

public class ProviderController {

	
	private ProviderManager providerManager;
	private DatosBancariosManager datosBancariosManager;
	private PurchaseOrderManager purchaseOrderManager;
	private NavigationControl navigationControl;

	public ProviderManager getProviderManager() {
		return providerManager;
	}

	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	
	public DatosBancariosManager getDatosBancariosManager() {
		return datosBancariosManager;
	}

	public void setDatosBancariosManager(DatosBancariosManager datosBancariosManager) {
		this.datosBancariosManager = datosBancariosManager;
	}
	
	public PurchaseOrderManager getPurchaseOrderManager() {
		return purchaseOrderManager;
	}

	public void setPurchaseOrderManager(PurchaseOrderManager purchaseOrderManager) {
		this.purchaseOrderManager = purchaseOrderManager;
	}

	//read the provider
	public void loadProvider(Combobox combo) {
		ArrayList<Proveedor> provider = this.providerManager.getAll();
		if (provider != null) {
			ListModelList<Proveedor> model = new ListModelList<Proveedor>(provider);
			combo.setItemRenderer(new ProveedorComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	public void search(Listbox lb, ProviderSearchAdapter psa, NavigationState state){
		ArrayList<Proveedor> provider = this.providerManager.search(psa);
		
		ListModelList<Proveedor> model = new ListModelList<Proveedor>(provider);
		lb.setModel(model);
	}
	

	public void loadInvoice(Combobox combo) {
		ArrayList<Proveedor> proveedores = this.providerManager.getAll();
		if (proveedores != null) {
			ListModelList<Proveedor> model = new ListModelList<Proveedor>(proveedores);
			combo.setItemRenderer(new ProveedorComboitemRenderer());
			combo.setModel(model);
		}
	}
	
	
	public ProviderAdapter readForNew () {
		ProviderAdapter p = new ProviderAdapter();	
		p.setProveedor(new Proveedor());
		p.getProveedor().setTipoPago(new TipoPago());
		p.getProveedor().setTipoMoneda(new TipoMoneda());
		return p;
	}
	
	
	@SuppressWarnings("unchecked")
	public void save(ProviderAdapter pa, NavigationState state, Component win){
		
		Combobox cli = (Combobox) win.getFellowIfAny("tpcb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getProveedor().setTipoPago((TipoPago) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un tipo de pago");
		
		Combobox tmcb = (Combobox) win.getFellowIfAny("tmcb");
		if (tmcb != null && tmcb.getSelectedItem()!=null )
			pa.getProveedor().setTipoMoneda((TipoMoneda) tmcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(tmcb, "Debe de seleccionar un tipo de moneda");
		
		if (pa.getProveedor().getDiasCredito() == null)
			pa.getProveedor().setDiasCredito(0);
		
		
		pa.getProveedor().setActivo(SystemConstants.PROVEEDOR_ACTIVA);
		
		this.providerManager.save(pa.getProveedor());
		
		ArrayList<DatosBancarios> listDataBank = (ArrayList<DatosBancarios>) SessionUtil.getSessionAttribute("listDataBank");
		
		for (DatosBancarios db : listDataBank ) {

			db.setProveedor(pa.getProveedor());
			db.setEstatus(SystemConstants.DATOSBANCARIOS_ACTIVA);
			datosBancariosManager.save(db);        	
        	
		}
		
		state.setDetailIdentifier(pa.getProveedor().getIdProveedor());
		state.setUri("/WEB-INF/zul/provider/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getProveedor().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	
	@SuppressWarnings("unchecked")
	public void update(ProviderAdapter pa, NavigationState state, Component win){
		
		
		if (pa.getFalgActive())
			pa.getProveedor().setActivo(SystemConstants.PROVEEDOR_ACTIVA);
		else
			pa.getProveedor().setActivo(SystemConstants.PROVEEDOR_INACTIVA);
		
		Combobox cli = (Combobox) win.getFellowIfAny("tpcb");
		if (cli != null && cli.getSelectedItem()!=null )
			pa.getProveedor().setTipoPago((TipoPago) cli.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cli, "Debe de seleccionar un tipo de pago");
		
		Combobox tmcb = (Combobox) win.getFellowIfAny("tmcb");
		if (tmcb != null && tmcb.getSelectedItem()!=null )
			pa.getProveedor().setTipoMoneda((TipoMoneda) tmcb.getSelectedItem().getValue());
		else 
			throw new WrongValueException(tmcb, "Debe de seleccionar un tipo de moneda");
		
		if (pa.getProveedor().getDiasCredito() == null)
			pa.getProveedor().setDiasCredito(0);
		
		this.providerManager.update(pa.getProveedor());
		
		ArrayList<DatosBancarios> listDataBank = (ArrayList<DatosBancarios>) SessionUtil.getSessionAttribute("listDataBank");
		
		for (DatosBancarios db : listDataBank ) {

			db.setProveedor(pa.getProveedor());
			
			if (db.getIdDatosBancarios() == null) {
				db.setEstatus(SystemConstants.DATOSBANCARIOS_ACTIVA);
				datosBancariosManager.save(db); 
			} else {
				datosBancariosManager.update(db); 
			}
			
        	
		}
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getProveedor().getNombre());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getProveedor().getNombre());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Proveedor proveedor = (Proveedor)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(proveedor.getIdProveedor());
			state.setUri("/WEB-INF/zul/provider/detail.zul");
			state.appendBreadCrumbsPath(proveedor.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Proveedor p = (Proveedor)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProveedor());
				detailLabels.add(p.getNombre());
				if (p.getIdProveedor().equals(proveedor.getIdProveedor())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
		
	
	public ProviderAdapter read(Integer providerId){
		ProviderAdapter pa = new ProviderAdapter();
	
		Proveedor p = this.providerManager.get(providerId);
		ArrayList<DatosBancarios> dbp= this.datosBancariosManager.getDatosBancarios(providerId);
		pa.setProveedor(p);
		pa.setDatosBancarios(dbp);
		
		if (pa.getProveedor().getActivo() == 1)
			pa.setFalgActive(Boolean.TRUE);
		else
			pa.setFalgActive(Boolean.FALSE);
		
		return pa;
	}
			
	public ProviderAdapter readForEdit(Integer providerId){
		ProviderAdapter pa = new ProviderAdapter();
		
		Proveedor p = this.providerManager.get(providerId);
		if (p.getTipoMoneda() == null)
			p.setTipoMoneda(new TipoMoneda());	
		ArrayList<DatosBancarios> dbp= this.datosBancariosManager.getDatosBancarios(providerId);
		pa.setProveedor(p);
		pa.setDatosBancarios(dbp);
		
		if (pa.getProveedor().getActivo() == 1)
			pa.setFalgActive(Boolean.TRUE);
		else
			pa.setFalgActive(Boolean.FALSE);

		
		return pa;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addCta(Listbox lbcp) {
		
		ProviderAdapter provider = new ProviderAdapter();
		Proveedor p= new Proveedor();
		provider.setProveedor(p);
		provider.setDatosBancarios(new ArrayList<DatosBancarios>());
	
		ListModelList<DataBankListitemAdapter> model = (ListModelList) lbcp.getModel();
		DataBankListitemAdapter adapter = new DataBankListitemAdapter(new DatosBancarios());
		model.add(adapter);
									
	}
	
	public void loadDataBank(Listbox lb, boolean edit){
		
		ArrayList<DatosBancarios> pl= new ArrayList<DatosBancarios>();
		DatosBancarios db= new DatosBancarios();
		pl.add(db);
		
		SessionUtil.setSessionAttribute("listDataBank", new ArrayList<DatosBancarios>());
		
		ListModelList<DataBankListitemAdapter> lml = new ListModelList<DataBankListitemAdapter>();
		if(edit) {
			lb.setItemRenderer(new DataBanckEditableListitemRenderer());
		}
		lb.setModel(lml);
		
	}
	
	public void loadEditDataBank (Listbox lb, ProviderAdapter providerEdit, boolean edit){
		
		if (providerEdit.getDatosBancarios() != null) {
			ListModelList<DataBankListitemAdapter> lml = new ListModelList<DataBankListitemAdapter>(DataBankListitemAdapter.getArray(providerEdit.getDatosBancarios()));
			
			SessionUtil.setSessionAttribute("listDataBank", providerEdit.getDatosBancarios());
			
			if(edit) {
				lb.setItemRenderer(new DataBanckEditableListitemRenderer());
			}
			lb.setModel(lml);
		}
		
	}
	
	
	public void obtieneDBP (ProviderAdapter providerDetail, Listbox lb) {
		ListModelList<DatosBancarios> model = new ListModelList<DatosBancarios>(providerDetail.getDatosBancarios());
		lb.setModel(model);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void recordCtaBancaria(Listitem listitem) {
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DataBankListitemAdapter adapter = (DataBankListitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DatosBancarios db = adapter.getDatosBancarios();
		
		Component comp = listitem.getFirstChild();
		IREditableTextbox banco = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableTextbox cuenta = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableTextbox clabe = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
				
		db.setBanco(banco.getValue());
		db.setCta(cuenta.getValue());
		db.setClabe(clabe.getValue());
	
		ArrayList<DatosBancarios> listDataBank = (ArrayList<DatosBancarios>) SessionUtil.getSessionAttribute("listDataBank");
		
		if (db.getIdDatosBancarios() == null)
			listDataBank.add(db);
		else 
		if (db.getIdDatosBancarios() > 0) {
			listDataBank.set(selectedIndex, db);
		}
		
		System.out.println("tamaño de la lista "+listDataBank.size());
		
		SessionUtil.setSessionAttribute("listDataBank", listDataBank);
		
	}
	
	@SuppressWarnings("unchecked")
	public void deleteCtaBancaria(Listitem listitem) {
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DataBankListitemAdapter adapter = (DataBankListitemAdapter) lb.getModel().getElementAt(selectedIndex);
		DatosBancarios db = adapter.getDatosBancarios();
		
		Component comp = listitem.getFirstChild();
		IREditableTextbox banco = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableTextbox cuenta = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
		IREditableTextbox clabe = (IREditableTextbox) comp.getFirstChild();
		comp = comp.getNextSibling();
		
				
		db.setBanco(banco.getValue());
		db.setCta(cuenta.getValue());
		db.setClabe(clabe.getValue());
		db.setEstatus(SystemConstants.DATOSBANCARIOS_INACTIVA);
	
		ArrayList<DatosBancarios> listDataBank = (ArrayList<DatosBancarios>) SessionUtil.getSessionAttribute("listDataBank");
		
		if (db.getIdDatosBancarios() == null && db.getIdDatosBancarios() == 0)
			listDataBank.remove(db);
		else 
		if (db.getIdDatosBancarios() > 0) {
			listDataBank.set(selectedIndex, db);
		}
		
		SessionUtil.setSessionAttribute("listDataBank", listDataBank);
		
	}
	
	public void delete (ProviderAdapter pa, NavigationState state, Component win){		
		
		Boolean flagExist=  purchaseOrderManager.getProviderExist(pa.getProveedor().getIdProveedor()).size() > 0 ? Boolean.TRUE : Boolean.FALSE;
	
		if (!flagExist)
			this.providerManager.delete(pa.getProveedor());
		
		state.setUri("/WEB-INF/zul/provider/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
	
}
