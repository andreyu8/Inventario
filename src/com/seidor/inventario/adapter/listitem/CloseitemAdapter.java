package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.CloseBean;


public class CloseitemAdapter {
	
	private CloseBean closeBean;


	public CloseitemAdapter(CloseBean closeBean) {
		super();
		this.closeBean = closeBean;
	}
	
	public CloseBean getCloseBean() {
		return closeBean;
	}

	public void setCloseBean(CloseBean closeBean) {
		this.closeBean = closeBean;
	}

	public static ArrayList<CloseitemAdapter> getArray (ArrayList<CloseBean> array) {
		ArrayList<CloseitemAdapter> result = new ArrayList<CloseitemAdapter>();
		Integer size =  array.size();
		for (int i = 0; i < size; i++) {
			CloseBean obj = array.get(i);
			result.add(new CloseitemAdapter(obj));
		}
		return result;
	}

}
