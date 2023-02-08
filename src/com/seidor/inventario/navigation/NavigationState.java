package com.seidor.inventario.navigation;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class NavigationState {
	
	private String title;
	private String uri;
	private String menuUri;
	private Object searchCriteria;
	private Object detailIdentifier;
	private Object detailSupportIdentifier;
	private Integer searchType;
	private Integer activePage;
	private Integer pageSize;
	private ArrayList detailList;
	private ArrayList<String> detailLabels;
	private Integer detailIndex;
	private Boolean sidebarCollapsed;
	private ArrayList<BreadCrumb> breadCrumbs = new ArrayList<BreadCrumb>();
	
	public NavigationState() {
		this.searchType = 0;
		this.activePage = 0;
		this.setPageSize(10);
	}
	
	public NavigationState(String uri) {
		this();
		this.uri = uri;
	}
	
	public NavigationState(String uri, String breadCrumb) {
		this();
		this.uri = uri;
		this.breadCrumbs.add(new BreadCrumb(breadCrumb));
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public Object getSearchCriteria() {
		return searchCriteria;
	}
	
	public void setSearchCriteria(Object searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	public Object getDetailIdentifier() {
		return detailIdentifier;
	}
	
	public void setDetailIdentifier(Object detailIdentifier) {
		this.detailIdentifier = detailIdentifier;
	}
	
	public Object getDetailSupportIdentifier() {
		return detailSupportIdentifier;
	}
	
	public void setDetailSupportIdentifier(Object detailSupportIdentifier) {
		this.detailSupportIdentifier = detailSupportIdentifier;
	}
	
	public Integer getSearchType() {
		return searchType;
	}
	
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	
	public Integer getActivePage() {
		return activePage;
	}
	
	public void setActivePage(Integer activePage) {
		this.activePage = activePage;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public ArrayList getDetailList() {
		return detailList;
	}
	
	public void setDetailList(ArrayList detailList) {
		this.detailList = detailList;
	}
	
	public Integer getDetailIndex() {
		return detailIndex;
	}
	
	public void setDetailIndex(Integer detailIndex) {
		this.detailIndex = detailIndex;
	}
	
	public ArrayList<String> getDetailLabels() {
		return detailLabels;
	}

	public void setDetailLabels(ArrayList<String> detailLabels) {
		this.detailLabels = detailLabels;
	}
	
	public void cleanDetailInfo(){
		this.detailIdentifier = null;
		this.detailIndex = null;
		this.detailLabels = null;
		this.detailList = null;
		this.detailSupportIdentifier = null;
	}
	
	public void cleanListInfo(){
		this.searchType = null;
		this.pageSize = 10;
		this.activePage = 0;
	}
	
	public String getMenuUri() {
		if (this.menuUri == null){
			return "/WEB-INF/zul/home/menubar.zul";
		}
		
		return menuUri;
	}
	
	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}
	
	public Boolean getSidebarCollapsed() {
		return sidebarCollapsed;
	}
	
	public void setSidebarCollapsed(Boolean sidebarCollapsed) {
		this.sidebarCollapsed = sidebarCollapsed;
	}
	
	public void startBreadCrumbsPath(String breadCrumb) {
		BreadCrumb bc = new BreadCrumb(breadCrumb);
		this.breadCrumbs.clear();
		this.breadCrumbs.add(bc);
	}
	
	public void startBreadCrumbsPathFromHome(String breadCrumb) {
		BreadCrumb bc = new BreadCrumb(breadCrumb);
		BreadCrumb home = this.breadCrumbs.get(0);
		this.breadCrumbs.clear();
		this.breadCrumbs.add(home);
		this.breadCrumbs.add(bc);
	}
	
	public void appendBreadCrumbsPath(String breadCrumb) {
		this.breadCrumbs.add(new BreadCrumb(breadCrumb));
	}
	
	public ArrayList<BreadCrumb> getBreadCrumbs() {
		return this.breadCrumbs;
	}
	
	public void setBreadCrumbs(ArrayList<BreadCrumb> breadCrumbs) {
		this.breadCrumbs = breadCrumbs;
	}
	
	public void removeLastBreadCrumbs() {
		if (this.breadCrumbs.size() > 0) {
			this.breadCrumbs.remove(this.breadCrumbs.size() -1);
		}
	}
	
	public BreadCrumb getLastBreadCrumbs() {
		if (this.breadCrumbs.size() > 0) {
			return this.breadCrumbs.get(this.breadCrumbs.size() -1);
		}
		return null;
	}
	
}