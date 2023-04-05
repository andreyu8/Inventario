package com.seidor.inventario.adapter;

//import com.seidor.officereserv.model.UserProfile;
import com.seidor.inventario.model.Usuario;

import java.util.ArrayList;

import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;


public class UserAdapter {
	
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
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void addProfile(Listbox lb1, Listbox lb2){
//		if (lb1.getSelectedIndex() >= 0) {
//			ListModelList<UserProfile> model2 = (ListModelList)lb2.getModel();
//			Profile key = (Profile)lb1.getSelectedItem().getValue();
//			Boolean band = true;
//			for (UserProfile added : this.profiles){
//				if (added.getProfile().getId().equals(key.getId())){
//					band = false;
//					added.setFdl(false);
//				}
//			}
//			
//			UserProfile up = new UserProfile();
//			up.setProfile(key);
//			up.setCatUsuario(this.user);
//			up.setFdl(false);
//			if (band) {
//				this.profiles.add(up);
//			}
//			
//			ListModelList<Profile> model1 = (ListModelList)lb1.getModel();
//			model1.remove(key);
//			model2.add(up);
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public void removeProfile(Listbox lb1, Listbox lb2){
//		if (lb2.getSelectedIndex() >= 0) {
//			ListModelList<UserProfile> model2 = (ListModelList)lb2.getModel();
//			UserProfile up = (UserProfile)model2.getElementAt(lb2.getSelectedIndex());
//			Profile key = up.getProfile();
//			if (up.getId() != null) up.setFdl(true);
//			else this.profiles.remove(up);
//			model2.remove(lb2.getSelectedIndex());
//			
//			ListModelList<Profile> model1 = (ListModelList)lb1.getModel();
//			model1.add(key);
//		}
//	}
//	
//	public void loadProfilesEdit(Listbox lb){
//		ArrayList<UserProfile> array = new ArrayList<UserProfile>();
//		array.addAll(this.profiles);
//		ListModelList<UserProfile> model = new ListModelList<UserProfile>(array);
//		lb.setModel(model);
//	}
//	
//	public void loadMissingProfiles(Listbox lb){
//		ArrayList<Profile> missing = new ArrayList<Profile>();
//		for (Profile available : this.allProfiles){
//			Boolean b = false;
//			for (UserProfile added : this.profiles){
//				if (added.getProfile().getId().equals(available.getId())){
//					b = true;
//					break;
//				}
//			}
//			if (!b) {
//				missing.add(available);
//			}
//		}
//		
//		ListModelList<Profile> lml = new ListModelList<Profile>(missing);
//		lb.setModel(lml);
//	}
//	
//	public Boolean hasAnyProfile(){
//		for (UserProfile profile : this.profiles){
//			if (!profile.getFdl()){
//				return true;
//			}
//		}
//		return false;
//	}
	
}