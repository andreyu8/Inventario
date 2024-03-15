package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.TasaCuotaIva;
import com.seidor.inventario.util.DaoUtil;

public class IvaDAO extends HibernateDaoSupport {
	
	public TasaCuotaIva get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(TasaCuotaIva.class);
		criteria.add(Restrictions.eq("idTasaCuotaIva", id));
		TasaCuotaIva result = (TasaCuotaIva)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TasaCuotaIva> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TasaCuotaIva.class);
		criteria.addOrder(Order.asc("idTasaCuotaIva"));
		List<TasaCuotaIva> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TasaCuotaIva>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(TasaCuotaIva i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, TasaCuotaIva.class);
		DaoUtil.prepareToSave(i);
		session.save(i);
		
		session.flush();
		session.close();
	}
	
	public void update(TasaCuotaIva i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(i);
		session.update(i);
		session.flush();
		session.close();
	}
	
	public void delete(TasaCuotaIva i){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(i);
		session.update(i);
		session.flush();
		session.close();
	}

}
