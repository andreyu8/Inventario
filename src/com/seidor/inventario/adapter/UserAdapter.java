package com.seidor.inventario.adapter;

import com.seidor.inventario.model.Usuario;

import java.util.ArrayList;

import com.seidor.inventario.model.PerfilUsuario;


public class UserAdapter {
	
	private Usuario usuario;
	private String nameComplete;
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
	
}