package com.seidor.inventario.manager;

import java.util.ArrayList;


import com.seidor.inventario.dao.RoleDAO;
import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;

public class RoleManager {
	
	private RoleDAO roleDao;
	
	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	//Business logic
	public Perfil get(Integer id){
		return this.roleDao.get(id);
	}
	
	public ArrayList<Perfil> getAll(){
		return this.roleDao.getAll();
	}
	
	public void save (Perfil p) {
		this.roleDao.save(p);
	}
	
	public void update (Perfil p) {
		this.roleDao.update(p);
	}
	
	public void delete (Perfil p) {
		this.roleDao.delete(p);
	}

	public ArrayList<PerfilUsuario> getProfileUser(Usuario usuario) {
		return this.roleDao.getProfileUser (usuario);
	}
	

}
