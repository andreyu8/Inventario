package com.seidor.inventario.adapter;

//import com.seidor.officereserv.model.UserProfile;
import com.seidor.inventario.model.Usuario;

import java.util.ArrayList;

import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;


public class UserAdapter {
	
	private Usuario usuario;
	private String nameComplete;
	private Perfil role;
	private ArrayList<PerfilUsuario> profiles = new ArrayList<PerfilUsuario>();
	private ArrayList<Perfil> allProfiles = new ArrayList<Perfil>();

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

	public ArrayList<Perfil> getAllProfiles() {
		return allProfiles;
	}

	public void setAllProfiles(ArrayList<Perfil> allProfiles) {
		this.allProfiles = allProfiles;
	}

	//	public ArrayList<UserProfile> getProfiles() {
//		return profiles;
//	}
//	
//	public void setProfiles(ArrayList<UserProfile> profiles) {
//		this.profiles = profiles;
//	}
//	
//	public void loadProfiles(Listbox lb){
//		if (this.profiles != null) {
//			lb.setModel(new ListModelList<UserProfile>(this.profiles, true));
//		}
//		else {
//			lb.setModel(new ListModelList<UserProfile>(new ArrayList<UserProfile>(), true));
//		}
//	}
//	
//	public ArrayList<Profile> getAllProfiles() {
//		return allProfiles;
//	}
//
//	public void setAllProfiles(ArrayList<Profile> allProfiles) {
//		this.allProfiles = allProfiles;
//	}
//	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addProfile(Listbox lb1, Listbox lb2){
		if (lb1.getSelectedIndex() >= 0) {
			ListModelList<PerfilUsuario> model2 = (ListModelList)lb2.getModel();
			Perfil key = (Perfil)lb1.getSelectedItem().getValue();
			Boolean band = true;
			for (PerfilUsuario added : this.profiles){
				if (added.getPerfil().getIdPerfil().equals(key.getIdPerfil())){
					band = false;
					added.setActivo(1);
				}
			}
			
			PerfilUsuario up = new PerfilUsuario();
			up.setPerfil(key);
			up.setUsuario(this.usuario);
			up.setActivo(1);
			if (band) {
				this.profiles.add(up);
			}
			
			ListModelList<Perfil> model1 = (ListModelList)lb1.getModel();
			model1.remove(key);
			model2.add(up);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeProfile(Listbox lb1, Listbox lb2){
		if (lb2.getSelectedIndex() >= 0) {
			ListModelList<PerfilUsuario> model2 = (ListModelList)lb2.getModel();
			PerfilUsuario up = (PerfilUsuario)model2.getElementAt(lb2.getSelectedIndex());
			Perfil key = up.getPerfil();
			if (up.getIdPerfilUsuario() != null) up.setActivo(0);
			else 
			this.profiles.remove(up);
			model2.remove(lb2.getSelectedIndex());
			
			ListModelList<Perfil> model1 = (ListModelList)lb1.getModel();
			model1.add(key);
		}
	}
	
	public void loadProfilesEdit(Listbox lb){
		ArrayList<PerfilUsuario> array = new ArrayList<PerfilUsuario>();
		array.addAll(this.profiles);
		ListModelList<PerfilUsuario> model = new ListModelList<PerfilUsuario>(array);
		lb.setModel(model);
	}
	
	public void loadMissingProfiles(Listbox lb){
		ArrayList<Perfil> missing = new ArrayList<Perfil>();
		for (Perfil available : this.allProfiles){
			Boolean b = false;
			for (PerfilUsuario added : this.profiles){
				if (added.getPerfil().getIdPerfil().equals(available.getIdPerfil())){
					b = true;
					break;
				}
			}
			if (!b) {
				missing.add(available);
			}
		}
		
		for (Perfil p : missing) {
			System.out.println(p.getNombre());
		}
		
		ListModelList<Perfil> lml = new ListModelList<Perfil>(missing);
		lb.setModel(lml);
	}
	
	public Boolean hasAnyProfile(){
		for (PerfilUsuario profile : this.profiles){
			if (profile.getActivo() != 0){
				return true;
			}
		}
		return false;
	}
	
}