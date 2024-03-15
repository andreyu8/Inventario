package com.seidor.inventario.adapter.listitem;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.DevolucionBean;

public class DevolucionitemAdapter {
	
	private DevolucionBean devolucionBean;


	public DevolucionitemAdapter(DevolucionBean devolucionBean) {
		super();
		this.devolucionBean = devolucionBean;
	}
	
	public DevolucionBean getDevolucionBean() {
		return devolucionBean;
	}

	public void setDevolucionBean(DevolucionBean devolucionBean) {
		this.devolucionBean = devolucionBean;
	}

	public static ArrayList<DevolucionitemAdapter> getArray (ArrayList<DevolucionBean> array) {
		ArrayList<DevolucionitemAdapter> result = new ArrayList<DevolucionitemAdapter>();
		Integer size =  array.size();
		for (int i = 0; i < size; i++) {
			DevolucionBean obj = array.get(i);
			result.add(new DevolucionitemAdapter(obj));
		}
		return result;
	}

}
