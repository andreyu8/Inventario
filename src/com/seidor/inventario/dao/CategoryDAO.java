package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.CategorySearchAdapter;
import com.seidor.inventario.model.Categoria;
import com.seidor.inventario.util.DaoUtil;

public class CategoryDAO extends HibernateDaoSupport {

	public Categoria get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Categoria.class);
		criteria.add(Restrictions.eq("idCategoria", id));
		Categoria result = (Categoria)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Categoria> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Categoria.class);
		criteria.addOrder(Order.asc("categoria"));
		List<Categoria> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Categoria>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(Categoria c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Categoria.class);
		criteria.add(Restrictions.eq("categoria", c.getCategoria().toUpperCase().trim()));
		List<Categoria> result = criteria.list();
		if (result.size() == 0) { 
			DaoUtil.prepareToSave(c);
			session.save(c);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(Categoria c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(c);
		session.update(c);
		session.flush();
		session.close();
	}
	
	public void delete(Categoria c){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(c);
		session.update(c);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Categoria> search(CategorySearchAdapter csa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Categoria.class);
		
		//criteria.setFetchMode("empleado", FetchMode.JOIN);
		
		if (csa.getName() != null && csa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("categoria", csa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		
		//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Categoria> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Categoria>(result);
	}
	
	
}
