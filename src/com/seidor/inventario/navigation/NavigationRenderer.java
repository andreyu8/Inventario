package com.seidor.inventario.navigation;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;

public class NavigationRenderer {
	
	private NavigationControl navigationControl;
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}
	
	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	@SuppressWarnings("rawtypes")
	public void render(Component comp, NavigationState state) {
		Div navigation = (Div)comp.getDesktop().getPageIfAny("indexPage").getFellowIfAny("navigator");
		Component firstChild = navigation.getFirstChild();
		if (firstChild != null) {
			firstChild.setParent(null);
		}
		
		ArrayList detailList = state.getDetailList();
		Integer detailIndex = state.getDetailIndex();
		
		if (detailList != null && detailList.size() > 1) {
			navigation.setVisible(true);
			Div container = new Div();
			container.setSclass("pull-right");
			
			navigation.appendChild(container);
			
			Hlayout hla = new Hlayout();
			hla.setStyle("vertical-align: middle; padding: 5px;");
			container.appendChild(hla);
			
			Button first = new Button();
			first.setZclass("none");
			first.setSclass("btn btn-default");
			first.setAutodisable("self");
			first.setIconSclass("z-icon-angle-double-left");
			
			Button prev = new Button();
			prev.setZclass("none");
			prev.setSclass("btn btn-default");
			prev.setAutodisable("self");
			prev.setIconSclass("z-icon-angle-left");
			
			Label index = new Label();
			index.setValue((detailIndex + 1) + "/" + detailList.size());
			index.setSclass("sysField");
			Div idiv = new Div();
			idiv.setStyle("padding-top: 5px;");
			idiv.appendChild(index);
			
			Button next = new Button();
			next.setZclass("none");
			next.setSclass("btn btn-default");
			next.setAutodisable("self");
			next.setIconSclass("z-icon-angle-right");
			
			Button last = new Button();
			last.setZclass("none");
			last.setSclass("btn btn-default");
			last.setAutodisable("self");
			last.setIconSclass("z-icon-angle-double-right");
			
			hla.appendChild(first);
			hla.appendChild(prev);
			hla.appendChild(idiv);
			hla.appendChild(next);
			hla.appendChild(last);
			
			if (detailIndex > 0) {
				first.addEventListener("onClick", new FirstEvent(state));
				prev.addEventListener("onClick", new PrevEvent(state));
			}
			else {
				first.setDisabled(true);
				prev.setDisabled(true);
			}

			if (detailIndex < detailList.size() - 1) {
				next.addEventListener("onClick", new NextEvent(state));
				last.addEventListener("onClick", new LastEvent(state));
			}
			else {
				next.setDisabled(true);
				last.setDisabled(true);
			}
		}
		else {
			navigation.setVisible(false);
		}
	}
	
	class FirstEvent implements EventListener<Event> {
		private NavigationState state;
		
		public FirstEvent(NavigationState state) {
			this.state = state;
		}
		
		public void onEvent(Event event) throws Exception {
			Integer newIndex = 0;
			this.state.setDetailIndex(newIndex);
			Object newDetailId = this.state.getDetailList().get(newIndex);
			String newBreadcrumb = this.state.getDetailLabels().get(newIndex);
			this.state.setDetailIdentifier(newDetailId);
			this.state.removeLastBreadCrumbs();
			this.state.appendBreadCrumbsPath(newBreadcrumb);
			navigationControl.changeView(event.getTarget(), this.state);
		}
	}
	
	class PrevEvent implements EventListener<Event> {
		private NavigationState state;
		
		public PrevEvent(NavigationState state) {
			this.state = state;
		}
		
		public void onEvent(Event event) throws Exception {
			Integer newIndex = this.state.getDetailIndex() - 1;
			this.state.setDetailIndex(newIndex);
			Object newDetailId = this.state.getDetailList().get(newIndex);
			String newBreadcrumb = this.state.getDetailLabels().get(newIndex);
			this.state.setDetailIdentifier(newDetailId);
			this.state.removeLastBreadCrumbs();
			this.state.appendBreadCrumbsPath(newBreadcrumb);
			navigationControl.changeView(event.getTarget(), this.state);
		}
	}
	
	class NextEvent implements EventListener<Event> {
		private NavigationState state;
		
		public NextEvent(NavigationState state) {
			this.state = state;
		}
		
		public void onEvent(Event event) throws Exception {
			int newIndex = this.state.getDetailIndex() + 1;
			this.state.setDetailIndex(newIndex);
			Object newDetailId = this.state.getDetailList().get(newIndex);
			String newBreadcrumb = this.state.getDetailLabels().get(newIndex);
			this.state.setDetailIdentifier(newDetailId);
			this.state.removeLastBreadCrumbs();
			this.state.appendBreadCrumbsPath(newBreadcrumb);
			navigationControl.changeView(event.getTarget(), this.state);
		}
	}
	
	class LastEvent implements EventListener<Event> {
		private NavigationState state;
		
		public LastEvent(NavigationState state) {
			this.state = state;
		}
		
		public void onEvent(Event event) throws Exception {
			Integer newIndex = this.state.getDetailList().size() - 1;
			this.state.setDetailIndex(newIndex);
			Object newDetailId = this.state.getDetailList().get(newIndex);
			String newBreadcrumb = this.state.getDetailLabels().get(newIndex);
			this.state.setDetailIdentifier(newDetailId);
			this.state.removeLastBreadCrumbs();
			this.state.appendBreadCrumbsPath(newBreadcrumb);
			navigationControl.changeView(event.getTarget(), this.state);
		}
	}
}