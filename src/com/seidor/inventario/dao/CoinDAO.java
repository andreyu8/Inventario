package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.TipoMoneda;
import com.seidor.inventario.util.DaoUtil;

public class CoinDAO extends HibernateDaoSupport {
	
	public TipoMoneda get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(TipoMoneda.class);
		criteria.add(Restrictions.eq("idMoneda", id));
		TipoMoneda result = (TipoMoneda)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoMoneda> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TipoMoneda.class);
		criteria.addOrder(Order.asc("idMoneda"));
		List<TipoMoneda> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TipoMoneda>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(TipoMoneda m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, TipoMoneda.class);
		DaoUtil.prepareToSave(m);
		session.save(m);
		
		session.flush();
		session.close();
	}
	
	public void update(TipoMoneda m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(m);
		session.update(m);
		session.flush();
		session.close();
	}
	
	public void delete(TipoMoneda m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(m);
		session.update(m);
		session.flush();
		session.close();
	}

}
