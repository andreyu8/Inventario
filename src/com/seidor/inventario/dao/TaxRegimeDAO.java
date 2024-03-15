package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.RegimenFiscal;
import com.seidor.inventario.util.DaoUtil;

public class TaxRegimeDAO extends HibernateDaoSupport{
	
	public RegimenFiscal get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(RegimenFiscal.class);
		criteria.add(Restrictions.eq("idRegimenFiscal", id));
		RegimenFiscal result = (RegimenFiscal)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<RegimenFiscal> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, RegimenFiscal.class);
		criteria.addOrder(Order.asc("idRegimenFiscal"));
		List<RegimenFiscal> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<RegimenFiscal>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(RegimenFiscal rf){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, RegimenFiscal.class);
		DaoUtil.prepareToSave(rf);
		session.save(rf);
		
		session.flush();
		session.close();
	}
	
	public void update(RegimenFiscal rf){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(rf);
		session.update(rf);
		session.flush();
		session.close();
	}
	
	public void delete(RegimenFiscal rf){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(rf);
		session.update(rf);
		session.flush();
		session.close();
	}

}
