package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.TipoTrabajo;
import com.seidor.inventario.util.DaoUtil;

public class TypeWorkDAO extends HibernateDaoSupport{

	public TipoTrabajo get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(TipoTrabajo.class);
		
		criteria.add(Restrictions.eq("idTipoTrabajo", id));
		TipoTrabajo result = (TipoTrabajo)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoTrabajo> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TipoTrabajo.class);
	
		criteria.addOrder(Order.asc("tipoTrabajo"));
		List<TipoTrabajo> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TipoTrabajo>(result);
	}
	
	
}
