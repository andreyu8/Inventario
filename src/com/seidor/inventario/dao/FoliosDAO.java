package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Folios;
import com.seidor.inventario.util.DaoUtil;

public class FoliosDAO extends HibernateDaoSupport{
	
	public Folios get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Folios.class);
		criteria.add(Restrictions.eq("idFolio", id));
		Folios result = (Folios)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Folios> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Folios.class);
		criteria.addOrder(Order.asc("idFolio"));
		List<Folios> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Folios>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Folios f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Folios.class);
		DaoUtil.prepareToSave(f);
		session.save(f);
		
		session.flush();
		session.close();
	}
	
	public void update(Folios f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(f);
		session.update(f);
		session.flush();
		session.close();
	}
	
	public void delete(Folios f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(f);
		session.update(f);
		session.flush();
		session.close();
	}

	public Folios getFolioMax(Integer idTipoMovimiento) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Folios.class);
		criteria.add(Restrictions.eq("tiposMovimiento.idTipoMovimiento", idTipoMovimiento));
		Folios result = (Folios)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}

}
