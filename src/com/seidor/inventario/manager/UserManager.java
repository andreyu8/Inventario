package com.seidor.inventario.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.seidor.inventario.adapter.UserAdapter;
import com.seidor.inventario.adapter.search.UserSearchAdapter;
import com.seidor.inventario.dao.UserDAO;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.util.DaoUtil;

public class UserManager {
	
	private UserDAO userDao;
	
	//Spring getters and setters
	public UserDAO getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	//Business logic
	public Usuario get(Integer id){
		return this.userDao.get(id);
	}
	
	public ArrayList<Usuario> getList(ArrayList<Integer> ids){
		return this.userDao.get(ids);
	}
	
	public HashMap<Integer, Usuario> get(ArrayList<Integer> ids){
		ArrayList<Usuario> users = this.userDao.get(ids);
		HashMap<Integer, Usuario> map = new HashMap<Integer, Usuario>();
		for (Usuario u : users) {
			map.put(u.getIdUsuario(), u);
		}
		return map;
	}
	
	public Usuario get(String username) {
		return this.userDao.get(username);
	}
	
	public void update(Usuario user){
		DaoUtil.update(user, this.userDao);
	}
	
	public ArrayList<Usuario> search(UserSearchAdapter usa) {
		return this.userDao.search(usa);
	}
	
	public void save(UserAdapter ua){
		this.userDao.save(ua);
	}
	
	public void update(UserAdapter ua){
		DaoUtil.prepareToUpdate(ua.getUsuario());
		
		
//		ArrayList<UserProfile> profiles = ua.getProfiles();
//		ArrayList<UserProfile> profilesDeleted = new ArrayList<UserProfile>();
//		for (UserProfile p : profiles){
//			if (p.getId() == null) {
//				DaoUtil.prepareToSave(p);
//			}
//			else if (p.getFdl().equals(true)){
//				DaoUtil.prepareToDelete(p);
//				profilesDeleted.add(p);
//			}
//			else {
//				DaoUtil.prepareToUpdate(p);
//			}
//		}
//		profiles.removeAll(profilesDeleted);
		
		this.userDao.update(ua.getUsuario());
	}
	
	public void delete(Usuario u){
		DaoUtil.delete(u, this.userDao);
	}

}