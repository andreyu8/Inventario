package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.util.DaoUtil;

public class WarehouseDAO extends HibernateDaoSupport {
	
	public Almacen get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Almacen.class);
		criteria.add(Restrictions.eq("idAlmacen", id));
		Almacen result = (Almacen)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Almacen> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Almacen.class);
		criteria.addOrder(Order.asc("almacen"));
		List<Almacen> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Almacen>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(Almacen a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Almacen.class);
		criteria.add(Restrictions.eq("almacen", a.getAlmacen()));
		List<Almacen> result = criteria.list();
		if (result.size() == 0) { 
			session.save(a);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(Almacen a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(a);
		session.flush();
		session.close();
	}
	
	public void delete(Almacen a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(a);
		session.flush();
		session.close();
	}

}
