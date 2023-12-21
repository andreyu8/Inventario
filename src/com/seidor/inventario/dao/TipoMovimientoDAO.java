package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.TiposMovimiento;
import com.seidor.inventario.util.DaoUtil;

public class TipoMovimientoDAO extends HibernateDaoSupport {

	public TiposMovimiento get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(TiposMovimiento.class);
		criteria.add(Restrictions.eq("idTipoMovimiento", id));
		TiposMovimiento result = (TiposMovimiento)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TiposMovimiento> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TiposMovimiento.class);
		criteria.addOrder(Order.asc("idArea"));
		List<TiposMovimiento> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TiposMovimiento>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(TiposMovimiento tm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, TiposMovimiento.class);
		DaoUtil.prepareToSave(tm);
		session.save(tm);
		
		session.flush();
		session.close();
	}
	
	public void update(TiposMovimiento tm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(tm);
		session.update(tm);
		session.flush();
		session.close();
	}
	
	public void delete(TiposMovimiento tm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(tm);
		session.update(tm);
		session.flush();
		session.close();
	}
	
}
