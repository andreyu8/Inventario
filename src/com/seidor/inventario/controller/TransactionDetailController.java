package com.seidor.inventario.controller;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.seidor.inventario.adapter.listitem.DetailTransactionitemAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.inroweditablecomps.EditableListitem;
import com.seidor.inventario.inroweditablecomps.IREditableCombobox;
import com.seidor.inventario.inroweditablecomps.IREditableDoublebox;
import com.seidor.inventario.inroweditablecomps.IREditableTextbox;
import com.seidor.inventario.manager.TransactionDetailManager;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.util.NumberFormatUtil;
import com.seidor.inventario.util.SessionUtil;

public class TransactionDetailController {
	
	@Autowired
	private TransactionDetailManager transactionDetailManager;
	
	public TransactionDetailManager getTransactionDetailManager() {
		return transactionDetailManager;
	}

	public void setTransactionDetailManager(TransactionDetailManager transactionDetailManager) {
		this.transactionDetailManager = transactionDetailManager;
	}


	//logic
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void deleteProduct(EditableListitem listitem) {
	
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		
		ListModelList<DetailTransactionitemAdapter> lml = (ListModelList) lb.getModel();
		DetailTransactionitemAdapter msr = lml.getElementAt(lb.getSelectedIndex());
		DetalleMovimiento dm =  msr.getDetalleMovimiento();
	
		dm.setEstatus(SystemConstants.ESTATUS_MOVIMIENTO_INACTIVO);
		
		if (dm.getIdDetalleMovimiento() > 0) {
			transactionDetailManager.delete(dm);	
		}
		
		lml.remove(lb.getSelectedIndex());
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateProduct(Listitem listitem, Label total) {
		
		Listbox lb = (Listbox) listitem.getParent();	
		Integer selectedIndex = lb.getIndexOfItem(listitem);
		DetailTransactionitemAdapter adapter = (DetailTransactionitemAdapter) lb.getModel().getElementAt(selectedIndex);
		
		DetalleMovimiento dm = adapter.getDetalleMovimiento();
		
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
				
		System.out.println(descriptionbox.getCombobox().getValue());
		
		dm.getProducto().setCodigo(descriptionbox.getCombobox().getValue());
		dm.setCantidad(cantidad.getValue());
		dm.setPrecioUnitario(new BigDecimal(precioUnitario.getValue()));
				
		ArrayList<DetalleMovimiento> listDetailTransaction = (ArrayList<DetalleMovimiento>) SessionUtil.getSessionAttribute("listDetailTransaction");
		
		if (dm.getIdDetalleMovimiento() == 0)
			listDetailTransaction.add(dm);
		else 
		if (dm.getIdDetalleMovimiento() > 0) {
			listDetailTransaction.set(selectedIndex, dm);
		}
		
		SessionUtil.setSessionAttribute("listDetailTransaction", listDetailTransaction);
		
				
		total.setValue(getTotalEdit (listDetailTransaction));
	}

	private String getTotalEdit(ArrayList<DetalleMovimiento> listDetailTransaction) {
		StringBuilder sb= new StringBuilder();
		BigDecimal total= new BigDecimal(0.0);
		BigDecimal totalTmp= new BigDecimal(0.0);
		
		for (DetalleMovimiento dm: listDetailTransaction) {
			System.out.println("productos"+dm.getCantidad()+"*"+dm.getPrecioUnitario());
			totalTmp = new BigDecimal(dm.getCantidad()).multiply(dm.getPrecioUnitario());
			total = total.add(totalTmp);
		}
		
		sb.append("SubTotal: $ " +NumberFormatUtil.format(total, 2));
		sb.append("\n");
		sb.append("IVA: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(0.16)), 2));
		sb.append("\n");
		sb.append("Total: $ " +NumberFormatUtil.format(total.multiply(new BigDecimal(1.16)), 2));
		
		return sb.toString();
	}

}
