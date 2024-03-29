package com.seidor.inventario.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.UserAdapter;
import com.seidor.inventario.manager.RoleManager;
import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.util.SessionUtil;

public class RoleController {
	
	@Autowired
	private RoleManager roleManager;

	@Autowired
	private NavigationControl navigationControl;


	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	

	public void search(Listbox lb, NavigationState state){
		ArrayList<Perfil> role = this.roleManager.getAll();
		
		ListModelList<Perfil> model = new ListModelList<Perfil>(role);
		lb.setModel(model);
		lb.setMultiple(true);
		lb.setCheckmark(true);
	}
	
	public void readSearch(Listbox lb, UserAdapter userDetail, NavigationState state){
		ArrayList<Perfil> role = new ArrayList<Perfil> ();
		
		for (PerfilUsuario pu : userDetail.getProfiles()) {
			role.add(pu.getPerfil());
		}

		ListModelList<Perfil> model = new ListModelList<Perfil>(role);
		lb.setModel(model);
	}
	
	
	public Boolean checkRoles(String profileList) {
		String[] profiles = profileList.split(",");
		Boolean hasRole = false;
		for (String profile : profiles){
			hasRole = userHasRole(profile);
			if (hasRole) break;
		}
		return hasRole;
	}
	
	public Boolean userHasRole(String profile){
		Boolean hasRole = false;
		ArrayList<PerfilUsuario> userRoles = SessionUtil.getUserRoles();
		if (userRoles == null) return false;
		for (PerfilUsuario r: userRoles) {
			if (r.getPerfil().getNombre().equals(profile)) {
				hasRole = true;
			}	
		}
		
		return hasRole;
	}

}
