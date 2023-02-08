package com.seidor.inventario.util;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Window;

import com.seidor.inventario.navigation.Action;

public class NavigationUtil {
	
	public static void hideApplication(Component comp){
		SessionUtil.destroy();
		comp.getDesktop().setBookmark("");
		Executions.sendRedirect("/");
	}
	
	public static void openModalWindowDirectly(Component comp, String zul, Map<String, Object> args){
		final Window win = (Window)Executions.getCurrent().createComponentsDirectly(zul, "zul", comp, args);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void openModalWindow(Component comp, String uri, Map<String, Object> args){
		final Window win = (Window) Executions.getCurrent().createComponents(uri, comp, args);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void openModalWindowWithMessage(String message){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		final Window win = (Window) Executions.getCurrent().createComponents("/WEB-INF/zul/common/msg/message.zul", null, map);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}

	public static void openModalWindowWithMessage(String menssage, String image){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("message", menssage);
		map.put("image", image);
		final Window win = (Window) Executions.getCurrent().createComponents("/WEB-INF/zul/common/msg/preViewDiagram.zul", null, map);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void openModalWindowWithMessage(String message, Action action){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("action", action);
		map.put("message", message);
		final Window win = (Window) Executions.getCurrent().createComponents("/WEB-INF/zul/common/msg/confirm.zul", null, map);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void openOptionalModalWindowWithMessage(String message, Action actionYes, Action actionNo){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("actionYes", actionYes);
		map.put("actionNo", actionNo);
		map.put("message", message);
		final Window win = (Window) Executions.getCurrent().createComponents("/WEB-INF/zul/common/msg/option.zul", null, map);
		win.setMaximizable(false);
		try {
			win.doModal();
		} catch (SuspendNotAllowedException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void loadComponent(String path, Component parent){
		try {
			Executions.getCurrent().createComponents(path, parent, null);
		} catch (Exception e) { e.printStackTrace();}
	}
	
	public static void openNewBrowser(String url){
		Executions.getCurrent().sendRedirect(url, "_blank");
	}
	
	public static void openExtLinkNewBrowser(String url){
		if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;
		Executions.getCurrent().sendRedirect(url, "_blank");
	}
	
}
