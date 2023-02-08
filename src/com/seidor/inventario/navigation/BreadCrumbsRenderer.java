package com.seidor.inventario.navigation;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Span;

public class BreadCrumbsRenderer {
	
	private NavigationControl navigationControl;
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}

	public void render(Component comp, NavigationState state) {
		Div navbar = (Div)comp.getDesktop().getPageIfAny("indexPage").getFellowIfAny("navbar");
		Div breadcrumbs = (Div)navbar.getFellowIfAny("breadcrumbs");
		Div content = (Div)breadcrumbs.getFellowIfAny("breadcrumbs-content");
		Label titlelabel = (Label)comp.getDesktop().getPageIfAny("indexPage").getFellowIfAny("titlelabel");
		
		ArrayList<BreadCrumb> crumbs = state.getBreadCrumbs();
		
		// clear breadcrumbs
		if (content != null) content.setParent(null);
		
		// redraw breadcrumbs
		content = new Div();
		content.setId("breadcrumbs-content");
		breadcrumbs.appendChild(content);
		
		//Append the initial home crumb
		Span homeicon = new Span();
		homeicon.setSclass("home-icon z-icon-home");
		content.appendChild(homeicon);
		
		//Title
		String title = state.getTitle();
		
		for (BreadCrumb crumb : crumbs) {
			Label link = new Label(crumb.getText());
			content.appendChild(link);
			if (crumbs.indexOf(crumb) < crumbs.size() -1) {
				if (crumb.getBookmark() != null) {
					link.setClass("bclink");
					link.setAttribute("bookmark", crumb.getBookmark());
					link.addEventListener("onClick", new EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							Executions.sendRedirect("#" + event.getTarget().getAttribute("bookmark"));
						}
					});
				}
				else {
					link.setClass("bclabel");
				}
				
				Span sep = new Span();
				sep.setSclass("z-icon-angle-double-right bclabel");
				sep.setStyle("padding: 0 5px 0 0;");
				content.appendChild(sep);
			}
			else {
				link.setClass("lastcrumb");
				if (title == null || title.length() == 0) title = crumb.getText();
			}
		}
		
		titlelabel.setValue(title);
	}
}
