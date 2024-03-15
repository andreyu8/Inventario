package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Area;
import com.seidor.inventario.util.DaoUtil;

public class AreaDAO  extends HibernateDaoSupport {

	public Area get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria =  session.createCriteria(Area.class);
		criteria.add(Restrictions.eq("idArea", id));
		Area result = (Area)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Area> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Area.class);
		criteria.addOrder(Order.asc("idArea"));
		List<Area> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Area>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Area a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Area.class);
		DaoUtil.prepareToSave(a);
		session.save(a);
		
		session.flush();
		session.close();
	}
	
	public void update(Area a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(a);
		session.update(a);
		session.flush();
		session.close();
	}
	
	public void delete(Area a){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(a);
		session.update(a);
		session.flush();
		session.close();
	}
	
}	