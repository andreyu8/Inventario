package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.OutBean;

public class OutputitemAdapter {
	
	private OutBean outBean;


	public OutputitemAdapter(OutBean outBean) {
		super();
		this.outBean = outBean;
	}
	
	public OutBean getOutBean() {
		return outBean;
	}

	public void setOutBean(OutBean outBean) {
		this.outBean = outBean;
	}


	public static ArrayList<OutputitemAdapter> getArray (ArrayList<OutBean> array) {
		ArrayList<OutputitemAdapter> result = new ArrayList<OutputitemAdapter>();
		Integer size =  array.size();
		for (int i = 0; i < size; i++) {
			OutBean obj = array.get(i);
			result.add(new OutputitemAdapter(obj));
		}
		return result;
	}

}
