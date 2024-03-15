package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Impuestos;
import com.seidor.inventario.util.DaoUtil;

public class TaxDAO extends HibernateDaoSupport {
	
	public Impuestos get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(Impuestos.class);
		criteria.add(Restrictions.eq("idImpuesto", id));
		Impuestos result = (Impuestos)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Impuestos> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Impuestos.class);
		criteria.addOrder(Order.asc("idImpuesto"));
		List<Impuestos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Impuestos>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Impuestos i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Impuestos.class);
		DaoUtil.prepareToSave(i);
		session.save(i);
		
		session.flush();
		session.close();
	}
	
	public void update(Impuestos i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(i);
		session.update(i);
		session.flush();
		session.close();
	}
	
	public void delete(Impuestos i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(i);
		session.update(i);
		session.flush();
		session.close();
	}

}
