package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Ubicacion;
import com.seidor.inventario.util.DaoUtil;

public class LocationDAO extends HibernateDaoSupport{
	
	
	public Ubicacion get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Ubicacion.class);
		criteria.add(Restrictions.eq("idUbicacion", id));
		Ubicacion result = (Ubicacion)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Ubicacion> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Ubicacion.class);
		criteria.addOrder(Order.asc("ubicacion"));
		List<Ubicacion> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Ubicacion>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(Ubicacion u){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Ubicacion.class);
		criteria.add(Restrictions.eq("categoria", u.getUbicacion()));
		List<Ubicacion> result = criteria.list();
		if (result.size() == 0) { 
			session.save(u);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(Ubicacion u){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(u);
		session.flush();
		session.close();
	}
	
	public void delete(Ubicacion u){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(u);
		session.flush();
		session.close();
	}
	

}
