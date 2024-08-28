package com.seidor.inventario.adapter.render;

import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.A;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;



import com.seidor.inventario.adapter.listitem.ProductListitemAdapter;
import com.seidor.inventario.controller.ProviderController;
import com.seidor.inventario.controller.PurchaseOrderController;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.EditableListitemRenderer;
import com.seidor.inventario.inroweditablecomps.IREditableCombobox;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.inroweditablecomps.IREditableIntbox;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.manager.UnitMeasureManager;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.Action;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.util.SessionUtil;
import com.seidor.inventario.validation.*;

public class ProductEditableListitemRenderer  extends EditableListitemRenderer {
	private ProductManager productManager;
	private UnitMeasureManager unitMeasureManager;
	private ListModelList<Producto> codigo;
	private ListModelList<UnidadMedida> unidad;
	private Label totalCoin;
	
	
	public ProductEditableListitemRenderer() {
		super();
		productManager = (ProductManager) SpringUtil.getBean("productManager");
		codigo = new ListModelList<Producto>(productManager.getAllCodigo(SessionUtil.getSucursalId()));
		unitMeasureManager = (UnitMeasureManager) SpringUtil.getBean("unitMeasureManager");
		unidad = new ListModelList<UnidadMedida>(unitMeasureManager.getAll());
	}
	
	public ProductEditableListitemRenderer(Label idtotalcoin) {
		super();
		productManager = (ProductManager) SpringUtil.getBean("productManager");
		codigo = new ListModelList<Producto>(productManager.getAllCodigo(SessionUtil.getSucursalId()));
		unitMeasureManager = (UnitMeasureManager) SpringUtil.getBean("unitMeasureManager");
		unidad = new ListModelList<UnidadMedida>(unitMeasureManager.getAll());
		totalCoin =  idtotalcoin;
	}
	
	
	
	@Override
	public void render(Listitem listitem, Object data, int index) throws Exception {
		ProductListitemAdapter clad = (ProductListitemAdapter)data;
		Producto product= clad.getProduct();
		final EditableListitem editListitem = (EditableListitem) listitem;
		
		String c = product.getCodigo()!=null? product.getCodigo() : "";
		IREditableCombobox codigoCombobox = new IREditableCombobox(c, codigo, new ProductComboitemRenderer(), new ComboboxMandatoryValidator());
		codigoCombobox.setParent(this.newListcell(editListitem));

		
		Label descrp= new Label((product.getNombre() == null ? "" : ""+product.getNombre()));
		descrp.setParent(this.newListcell(editListitem));
		
		IREditableDoublebox catidad= new IREditableDoublebox(product.getCantidad(), "###,###,###.00", new DoubleboxMandatoryValidator());
		catidad.setParent(this.newListcell(editListitem));
		
		
		String u = product.getUnidadMedida()!=null? product.getUnidadMedida().getDescripcion() : "";
		IREditableCombobox unidadCombobox = new IREditableCombobox(u, unidad, new UnitMeasureComboitemRenderer(), new ComboboxMandatoryValidator());
		unidadCombobox.setParent(this.newListcell(editListitem));
		
		IREditableDoublebox precioCompra = new IREditableDoublebox(product.getPrecioCompra() == null ? 0.0 : product.getPrecioCompra().doubleValue(), "###,###,###.00", new DoubleboxMandatoryValidator());
		precioCompra.setParent(this.newListcell(editListitem));
				
		
		IREditableDoublebox subtotal= new IREditableDoublebox(product.getPrecioCompra() == null ? 0.0 : product.getCantidad() * product.getPrecioCompra().doubleValue(), "###,###,###.00", new DoubleboxMandatoryValidator());
		subtotal.setParent(this.newListcell(editListitem));
		
		
		evidenceAction (codigoCombobox.getCombobox(), descrp);
		addCatPrecio (catidad.getDoublebox(), precioCompra.getDoublebox(), subtotal.getDoublebox());
		
		Action action = new Action() {
			public void execute() {
				PurchaseOrderController controller = (PurchaseOrderController)SpringUtil.getBean("purchaseOrderController");
				controller.recordProduct(editListitem, totalCoin );
			}
		};
		
		EventListener<Event> deleteEvent = new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem li = (Listitem)event.getTarget().getParent().getParent().getParent();
				Listbox lb = li.getListbox();
				lb.setSelectedItem(li);
				Action deleteAction = new Action() {
					public void execute(){
						PurchaseOrderController controller = (PurchaseOrderController)SpringUtil.getBean("purchaseOrderController");
						controller.deleteProductOC(editListitem, totalCoin);
						lb.removeItemAt(li.getIndex());
					}
				};
				NavigationControl.openModalWindowWithMessage("¿Realmente desea borrar el producto seleccionado?", deleteAction);
			}
		};
		
		if (product.getCantidad() == 0) {
			this.createControlButtons(editListitem, action, deleteEvent, true);
		} else {
			this.createControlButtons(editListitem, action, deleteEvent, false);
		}
		
		
	}
	

	private void addCatPrecio(Doublebox catidad, Doublebox precio, Doublebox subtotal) {
		precio.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Doublebox target = (Doublebox)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent().getParent();
				
				System.out.println(catidad.getValue());
				System.out.println(precio.getValue());
				
				subtotal.setValue(catidad.getValue() * precio.getValue());
				
			}
		});
		
	}


	private void evidenceAction(Combobox c, final Label descrp) {
		c.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Combobox target = (Combobox)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent().getParent();
				
				descrp.setValue(((Producto) c.getSelectedItem().getValue()).getNombre());
				//unidadamedida.setValue(((Producto) c.getSelectedItem().getValue()).getUnidadMedida().getUnidadMedida());
				System.out.println(""+((Producto) c.getSelectedItem().getValue()).getNombre());
				
			}
		});
		
	}


	private void sendAction(A send){
		send.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				A target = (A)event.getTarget();
				Listitem li = (Listitem)target.getParent().getParent();
				
				//ProspectionController controller = (ProspectionController)SpringUtil.getBean("prospectionController");
				//controller.showSendProvidersModal(li);
			}
		});
	}
	
	
		

}
