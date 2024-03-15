package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.UsoCfdi;
import com.seidor.inventario.util.DaoUtil;

public class UseCfdiDAO extends HibernateDaoSupport {
	
	public UsoCfdi get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(UsoCfdi.class);
		criteria.add(Restrictions.eq("idUsoCfdi", id));
		UsoCfdi result = (UsoCfdi)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<UsoCfdi> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, UsoCfdi.class);
		criteria.addOrder(Order.asc("idUsoCfdi"));
		List<UsoCfdi> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<UsoCfdi>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(UsoCfdi uc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, UsoCfdi.class);
		DaoUtil.prepareToSave(uc);
		session.save(uc);
		
		session.flush();
		session.close();
	}
	
	public void update(UsoCfdi uc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(uc);
		session.update(uc);
		session.flush();
		session.close();
	}
	
	public void delete(UsoCfdi uc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(uc);
		session.update(uc);
		session.flush();
		session.close();
	}

}
