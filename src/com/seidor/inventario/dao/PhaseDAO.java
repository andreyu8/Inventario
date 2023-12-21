package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Etapa;
import com.seidor.inventario.util.DaoUtil;

public class PhaseDAO  extends HibernateDaoSupport {

	public Etapa get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Etapa.class);
		criteria.add(Restrictions.eq("idEtapa", id));
		Etapa result = (Etapa)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Etapa> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Etapa.class);
		criteria.addOrder(Order.asc("idEtapa"));
		List<Etapa> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Etapa>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Etapa e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Etapa.class);
		DaoUtil.prepareToSave(e);
		session.save(e);
		
		session.flush();
		session.close();
	}
	
	public void update(Etapa e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(e);
		session.update(e);
		session.flush();
		session.close();
	}
	
	public void delete(Etapa e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(e);
		session.update(e);
		session.flush();
		session.close();
	}

}	