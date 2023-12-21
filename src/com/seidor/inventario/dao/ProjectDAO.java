package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.ProjectSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.model.EstatusProyecto;
import com.seidor.inventario.model.Proyecto;
import com.seidor.inventario.util.DaoUtil;

public class ProjectDAO extends HibernateDaoSupport {
	
	public Proyecto get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Proyecto.class);
		criteria.setFetchMode("estatusProyecto", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.add(Restrictions.eq("idProyecto", id));
		Proyecto result = (Proyecto)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Proyecto> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Proyecto.class);
		criteria.setFetchMode("estatusProyecto", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.addOrder(Order.asc("fechaInicio"));
		List<Proyecto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Proyecto>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(Proyecto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Proyecto.class);
		criteria.add(Restrictions.eq("nombre", p.getNombre().toUpperCase().trim()));
		List<Proyecto> result = criteria.list();
		if (result.size() == 0) { 
			DaoUtil.prepareToSave(p);
			session.save(p);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(Proyecto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(p);
		session.update(p);
		session.flush();
		session.close();
	}
	
	public void delete(Proyecto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(p);
		session.update(p);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Proyecto> search(ProjectSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Proyecto.class);
		
		criteria.setFetchMode("estatusProyecto", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		
		if (psa.getNombre() != null && psa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getNombre(), MatchMode.ANYWHERE));
		}
		
		
		//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Proyecto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Proyecto>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<EstatusProyecto> getEstatus() {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, EstatusProyecto.class);
		criteria.addOrder(Order.asc("idEstatusProyecto"));
		List<EstatusProyecto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<EstatusProyecto>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Proyecto> getAllOpen() {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Proyecto.class);
		criteria.setFetchMode("estatusProyecto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("estatusProyecto.idEstatusProyecto", SystemConstants.PROYECTO_ABIERTO));
		criteria.addOrder(Order.asc("fechaInicio"));
		List<Proyecto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Proyecto>(result);
	}

}
