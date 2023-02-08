package com.seidor.inventario.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Div;

import com.seidor.inventario.util.BeanUtil;
import com.seidor.inventario.util.NavigationUtil;
import com.seidor.inventario.util.SessionUtil;

public class NavigationControl {
	
	private BreadCrumbsRenderer breadcrumbsRenderer;
	private NavigationRenderer navigationRenderer;
	
	public BreadCrumbsRenderer getBreadcrumbsRenderer() {
		return this.breadcrumbsRenderer;
	}
	
	public void setBreadcrumbsRenderer(BreadCrumbsRenderer breadcrumbsRenderer) {
		this.breadcrumbsRenderer = breadcrumbsRenderer;
	}
	
	public NavigationRenderer getNavigationRenderer() {
		return this.navigationRenderer;
	}
	
	public void setNavigationRenderer(NavigationRenderer navigationRenderer) {
		this.navigationRenderer = navigationRenderer;
	}
	
	public static void hideApplication(Component comp){
		NavigationUtil.hideApplication(comp);
	}
	
	@SuppressWarnings("rawtypes")
	public void showApplication(Component component){
		Div login = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("login");
		Div navbar = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("navbar");
		Div titlediv = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("titlediv");
		Div content = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("content");
		Div header = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("header");
		Div footer = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("footer");
		
		if (SessionUtil.getLoggedUserId() != null) {
			header.setVisible(true);
			footer.setVisible(true);
			titlediv.setVisible(true);
			content.setVisible(true);
			login.setVisible(false);
			
			List loginChildren = login.getChildren();
			if (loginChildren.size() > 0) ((Component)loginChildren.get(0)).detach();
			
			NavigationState state = new NavigationState("/WEB-INF/zul/common/main.zul");
			//state.startBreadCrumbsPath("Inicio ");
			state.startBreadCrumbsPath("Sucursal: "+ SessionUtil.getSucursaldUserId());
			
			
			Executions.getCurrent().createComponents("/WEB-INF/zul/common/footer.zul", footer, null);
			Executions.getCurrent().createComponents("/WEB-INF/zul/common/navbar.zul", navbar, null);
			NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
			if (navStates.getLast() == null) {
				this.changeView(content, state);
			}
			
			NavigationState stateClone = (NavigationState)BeanUtil.createCopy(state);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("NAVSTATE", stateClone);
			Executions.getCurrent().createComponents("/WEB-INF/zul/common/header.zul", header, params);
		}
		else {
			List mainChildren = content.getChildren();
			if (mainChildren.size() > 0) ((Component)mainChildren.get(0)).detach();
			List headerChildren = header.getChildren();
			if (headerChildren.size() > 0) ((Component)headerChildren.get(0)).detach();
			
			header.setVisible(false);
			footer.setVisible(false);
			titlediv.setVisible(false);
			content.setVisible(false);
			login.setVisible(true);
			
			NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
			navStates.clear();
			Executions.getCurrent().createComponents("/WEB-INF/zul/login/main.zul", login, null);
		}
	}
	
	public void sendRedirect(String page, String target){
		if (target != null) {
			Executions.getCurrent().sendRedirect(page, target);
		}
		else {
			Executions.getCurrent().sendRedirect(page);
		}
	}
	
	public void showHome(Component component){
		NavigationState state = new NavigationState("/WEB-INF/zul/common/main.zul");
		state.startBreadCrumbsPath("Inicio");
		this.changeView(component, state);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void goTo(String bookmark, Component comp) {
		if (SessionUtil.getLoggedUserId() != null) {
			Div main = (Div)comp.getFellowIfAny("content");
			
			NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
			NavigationState state = navStates.getState(bookmark);
			if (state == null) {
				state = new NavigationState("/WEB-INF/zul/common/main.zul");
				state.startBreadCrumbsPath("Inicio");
			}
			
			NavigationState stateClone = (NavigationState)BeanUtil.createCopy(state);
			HashMap params = new HashMap();
			params.put("NAVSTATE", stateClone);
			
			this.breadcrumbsRenderer.render(main, stateClone);
			this.navigationRenderer.render(main, stateClone);
			
			if (main.getChildren().size() > 0) {
				Component oldMainWin = (Component)main.getChildren().get(0);
				main.removeChild(oldMainWin); 
			}
			
			Executions.getCurrent().createComponents(stateClone.getUri(), main, params);
		}
	}
	
	public NavigationState changeView(Component component, NavigationState state) {
		Div main = (Div)component.getDesktop().getPageIfAny("indexPage").getFellowIfAny("content");
		
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		String bookmark = navStates.generateBookmark();
		
		ArrayList<BreadCrumb> breadCrumbs = state.getBreadCrumbs();
		if (breadCrumbs.size() > 0) {
			BreadCrumb crumb = (BreadCrumb)breadCrumbs.get(breadCrumbs.size() - 1);
			if (crumb.getBookmark() == null) {
				crumb.setBookmark(bookmark);
			}
		}
		
		navStates.addState(bookmark, state);
		main.getDesktop().setBookmark(bookmark);
		
		NavigationState stateClone = (NavigationState)BeanUtil.createCopy(state);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("NAVSTATE", stateClone);
		
		this.breadcrumbsRenderer.render(main, stateClone);
		this.navigationRenderer.render(main, stateClone);
		
		if (main.getChildren().size() > 0) {
			Component oldMainWin = (Component)main.getChildren().get(0);
			main.removeChild(oldMainWin); 
		}
		
		Executions.getCurrent().createComponents(stateClone.getUri(), main, params);
		
		return stateClone;
	}
	
	public void refresh(Component comp) {
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState last = navStates.getLast();
		if (last == null) last = new NavigationState();
		changeView(comp, last);
	}
	
	public void changeToPrevious(Component component) {
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState state = navStates.getPrevious();
		if (state != null) {
			changeView(component, state);
		}
	}
	
	public static void openModalWindow(Component comp, String uri, Map<String, Object> args){
		NavigationUtil.openModalWindow(comp, uri, args);
	}
	
	public static void openModalWindowWithMessage(String message){
		NavigationUtil.openModalWindowWithMessage(message);
	}
	
	public static void openModalWindowWithMessage(String message, Action action){
		NavigationUtil.openModalWindowWithMessage(message, action);
	}
	
	public static void openOptionalModalWindowWithMessage(String message, Action actionYes, Action actionNo){
		NavigationUtil.openOptionalModalWindowWithMessage(message, actionYes, actionNo);
	}
	
	public static void loadComponent(String path, Component parent){
		NavigationUtil.loadComponent(path, parent);
	}
	
	public static void openNewBrowser(String url){
		NavigationUtil.openNewBrowser(url);
	}

}
