package com.seidor.inventario.adapter;


import com.seidor.inventario.model.Usuario;

import java.util.ArrayList;

import org.springframework.context.annotation.Profile;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;


public class UserAdapter<UserProfile> {
	
	private Usuario usuario;
	private String nameComplete;
	private Perfil role;
	private ArrayList<PerfilUsuario> profiles = new ArrayList<PerfilUsuario>();

	public UserAdapter(){
		
	}
	
	public UserAdapter(Usuario usuario){
		this.usuario = usuario;
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNameComplete() {
		return nameComplete;
	}

	public void setNameComplete(String nameComplete) {
		this.nameComplete = nameComplete;
	}

	public ArrayList<PerfilUsuario> getProfiles() {
		return profiles;
	}

	public void setProfiles(ArrayList<PerfilUsuario> profiles) {
		this.profiles = profiles;
	}


	public Perfil getRole() {
		return role;
	}

	public void setRole(Perfil role) {
		this.role = role;
	}
	
	public void loadProfile(Listbox lb) {
		if (this.profiles != null) {
			lb.setModel(new ListModelList<PerfilUsuario>(this.profiles, true));
		}
		else {
			lb.setModel(new ListModelList<PerfilUsuario>(new ArrayList<PerfilUsuario>(), true));
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProfile(Listbox lb1, Listbox lb2){
		if (lb1.getSelectedIndex() >= 0) {
			ListModelList<PerfilUsuario> model2 = (ListModelList)lb2.getModel();
			Perfil key = (Perfil)lb1.getSelectedItem().getValue();
			Boolean band = true;
			for (PerfilUsuario added : this.profiles){
				if (added.getPerfil().getIdPerfil().equals(lb2)) {
					band = false;
					added.setFdl(false);
				}
			}
			
			PerfilUsuario pu = new PerfilUsuario();
			pu.setPerfil(role);
			pu.setUsuario(usuario);
			pu.setFdl(false);
			if (band) {
				this.profiles.add(pu);
			}
			
			ListModelList<Profile> model1 = (ListModelList)lb1.getModel();
			model1.remove(key);
			model2.add(null);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeProfile(Listbox lb1, Listbox lb2){
		if (lb2.getSelectedIndex() >= 0) {
			ListModelList<PerfilUsuario> model2 = (ListModelList)lb2.getModel();
			PerfilUsuario pu = (PerfilUsuario)model2.getElementAt(lb2.getSelectedIndex());
			Perfil key = pu.getPerfil();
			if (pu.getIdPerfilUsuario() != null) pu.setFdl(true);
			else this.profiles.remove(pu);
			model2.remove(lb2.getSelectedIndex());
			
			ListModelList<Profile> model1 = (ListModelList)lb1.getModel();
			model1.add(null);
		}
	}
	
	
}