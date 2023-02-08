package com.seidor.inventario.navigation;

import java.util.HashMap;

import com.seidor.inventario.util.BeanUtil;

public class NavigationStates {
	
	private HashMap<String, NavigationState> map = new HashMap<String, NavigationState>();
	private String lastBookmark;
	private NavigationState last;
	private NavigationState previous;
	
	public NavigationStates() {};
	
	public void addState(String bookmark, NavigationState state) {
		this.map.put(bookmark, state);
		this.previous = this.last;
		this.last = state;
		this.lastBookmark = bookmark;
	}

	public NavigationState getState(String bookmark) {
		this.last = (NavigationState)this.map.get(bookmark);
		this.lastBookmark = bookmark;
		return last;
	}
	
	public NavigationState getLast() {
		NavigationState retValue = this.last;
		
		if (retValue != null) {
			retValue = (NavigationState)BeanUtil.createCopy(this.last);
		}
		
		return retValue;
	}
	
	public NavigationState getLastOriginal() {
		return this.last;
	}
	
	public NavigationState getPrevious() {
		NavigationState retValue = this.previous;
		
		if (retValue != null) {
			retValue = (NavigationState)BeanUtil.createCopy(this.previous);
		}
		
		return retValue;
	}
	
	public NavigationState getPreviousOriginal() {
		return this.previous;
	}
	
	public String getLastBookmark() {
		return this.lastBookmark;
	}
	
	public String generateBookmark() {
		String bookmark = null;
		do {
			bookmark = Long.toHexString(Double.doubleToLongBits(Math.random()));
		} while (this.map.containsKey(bookmark));
		return bookmark;
	}
	
	public void clear() {
		this.map.clear();
		this.last = null;
		this.lastBookmark = null;
	}
	
}