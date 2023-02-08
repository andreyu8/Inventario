package com.seidor.inventario.navigation;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;

public class PagingControl {
	
	public static void loadPaging(NavigationState state, Listbox lb){
		PagingControl.loadPageSize(state, lb);
		PagingControl.loadActivePage(state, lb);
	}
	
	public static void loadActivePage(NavigationState state, Listbox lb){
		if (lb.getMold().equals("paging")) {
			try {
				lb.setActivePage(state.getActivePage());
			} catch (Exception ex){
				lb.setActivePage(0);
			}
		}
	}
	
	public static void changeActivePage(NavigationState state, Listbox lb){
		Integer activePage = lb.getActivePage();
		if (activePage < 0) activePage = 0;
		if (state != null) state.setActivePage(activePage);
	}
	
	public static void resetActivePage(NavigationState state){
		state.setActivePage(0);
	}
	
	public static void loadPageSize(NavigationState state, Listbox lb){
		Integer pageSize = state.getPageSize();
		//Integer height = (pageSize * 33) + 70;
		
		try {
			Div pags = (Div)lb.getNextSibling().getNextSibling();
			if (pageSize.equals(0)){
				lb.setMold("default");
				pags.setStyle("");
			}
			else {
				lb.setMold("paging");
				lb.setPageSize(pageSize);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void changePageSize(NavigationState state, Integer pageSize, Component comp){
		Listbox lb = (Listbox)comp.getPreviousSibling();
		
		if (pageSize == null) pageSize = 10;
		if (state != null) {
			state.setPageSize(pageSize);
			PagingControl.loadPageSize(state, lb);
			PagingControl.resetActivePage(state);
			PagingControl.loadActivePage(state, lb);
		}
	}
	
}
