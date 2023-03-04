package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.TipoOrdenCompra;
import com.seidor.inventario.util.DaoUtil;

public class TypeOrderDAO extends HibernateDaoSupport {

	public TipoOrdenCompra get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(TipoOrdenCompra.class);
		criteria.add(Restrictions.eq("idTipoOrdenCompra", id));
		TipoOrdenCompra result = (TipoOrdenCompra)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoOrdenCompra> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TipoOrdenCompra.class);
		criteria.addOrder(Order.asc("idTipoOrdenCompra"));
		List<TipoOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TipoOrdenCompra>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(TipoOrdenCompra toc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, TipoOrdenCompra.class);
		session.save(toc);
		
		session.flush();
		session.close();
	}
	
	public void update(TipoOrdenCompra toc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(toc);
		session.flush();
		session.close();
	}
	
	public void delete(TipoOrdenCompra toc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(toc);
		session.flush();
		session.close();
	}
	
}
