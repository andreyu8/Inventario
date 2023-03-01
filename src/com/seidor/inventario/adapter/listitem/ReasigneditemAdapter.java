package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.ReasignedBean;

public class ReasigneditemAdapter {

	private ReasignedBean reasignedBean;

	public ReasigneditemAdapter(ReasignedBean reasignedBean) {
		super();
		this.reasignedBean = reasignedBean;
	}

	public ReasignedBean getReasignedBean() {
		return reasignedBean;
	}

	public void setReasignedBean(ReasignedBean reasignedBean) {
		this.reasignedBean = reasignedBean;
	}

	public static ArrayList<ReasigneditemAdapter> getArray(ArrayList<ReasignedBean> array) {
		ArrayList<ReasigneditemAdapter> result = new ArrayList<ReasigneditemAdapter>();
		Integer size = array.size();
		for (int i = 0; i < size; i++) {
			ReasignedBean obj = array.get(i);
			result.add(new ReasigneditemAdapter(obj));
		}
		return result;
	}

}
