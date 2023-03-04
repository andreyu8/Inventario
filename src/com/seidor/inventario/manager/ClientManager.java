package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.search.ClientSearchAdapter;
import com.seidor.inventario.dao.ClientDAO;
import com.seidor.inventario.model.Cliente;

public class ClientManager {

	private ClientDAO clientDao;
	
	
	//Spring getters and setters
	public ClientDAO getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDAO clientDao) {
		this.clientDao = clientDao;
	}

	
	//Business logic
	
	public Cliente get(Integer id){
		return this.clientDao.get(id);
	}
	
	public ArrayList<Cliente> getAll(){
		return this.clientDao.getAll();
	}
	
	public void save(Cliente c){
		this.clientDao.save(c);
	}
	
	public void update(Cliente c){
		this.clientDao.update(c);
	}
	
	public void delete(Cliente c){
		this.clientDao.delete(c);
	}

	public ArrayList<Cliente> search(ClientSearchAdapter csa) {
		return this.clientDao.search(csa);
	}

	
	
}
