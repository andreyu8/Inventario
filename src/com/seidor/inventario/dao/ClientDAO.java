package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.ClientSearchAdapter;
import com.seidor.inventario.model.Cliente;
import com.seidor.inventario.util.DaoUtil;

public class ClientDAO extends HibernateDaoSupport {

	public Cliente get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Cliente.class);
		criteria.add(Restrictions.eq("idCliente", id));
		Cliente result = (Cliente)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Cliente.class);
		criteria.addOrder(Order.asc("idCliente"));
		List<Cliente> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Cliente>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Cliente c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Cliente.class);
		session.save(c);
		
		session.flush();
		session.close();
	}
	
	public void update(Cliente c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(c);
		session.flush();
		session.close();
	}
	
	public void delete(Cliente c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(c);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Cliente> search(ClientSearchAdapter csa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Cliente.class);
		
		if (csa.getName() != null && csa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", csa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		List<Cliente> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Cliente>(result);
	}

}