package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.UnitMeasureSearchAdapter;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.util.DaoUtil;

public class UnitMeasureDAO extends HibernateDaoSupport {
	
	public UnidadMedida get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(UnidadMedida.class);
		criteria.add(Restrictions.eq("idUnidadMedida", id));
		UnidadMedida result = (UnidadMedida)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<UnidadMedida> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, UnidadMedida.class);
		criteria.addOrder(Order.asc("unidadMedida"));
		List<UnidadMedida> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<UnidadMedida>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(UnidadMedida um){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, UnidadMedida.class);
		criteria.add(Restrictions.eq("unidadMedida", um.getUnidadMedida()));
		List<UnidadMedida> result = criteria.list();
		if (result.size() == 0) { 
			session.save(um);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(UnidadMedida um){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(um);
		session.flush();
		session.close();
	}
	
	public void delete(UnidadMedida um){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.delete(um);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<UnidadMedida> search(UnitMeasureSearchAdapter usa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, UnidadMedida.class);
		
		if (usa.getNombre() != null && usa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("unidadMedida", usa.getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<UnidadMedida> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<UnidadMedida>(result);
	}

}
