package com.seidor.inventario.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.EntryAdapter;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.util.DaoUtil;

public class EntryDAO extends HibernateDaoSupport{
	
	
	public void save (Entrada e) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.save(e);
		
		session.flush();
		session.close();
	}
	
	public void update (Entrada e) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(e);
		
		session.flush();
		session.close();
	}

	public void save(Entrada e, Producto p) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
			session.save(e);
			session.update(p);
		
			session.getTransaction().commit();
			
		} catch (HibernateException ex) {
			ex.getStackTrace();
			session.getTransaction().rollback();
			
		} finally {
			session.flush();
			session.close();
		}
		
				
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Entrada> search(EntryAdapter ea) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Entrada.class);
		
		criteria.setFetchMode("producto", FetchMode.JOIN);
		
		if (ea.getEntrada().getProducto().getNombre() != null && ea.getEntrada().getProducto().getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("producto.nombre", ea.getEntrada().getProducto().getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Entrada> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Entrada>(result);
	}

	@SuppressWarnings("unchecked")
	public Entrada get(Integer idProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Entrada.class);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("producto.idProducto", idProducto));
		
		List<Entrada> result = criteria.list();
		session.flush();
		session.close();
		return  new ArrayList<Entrada>(result).get(0);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Entrada> getIdProjectProduct(Integer idProyecto, Integer idProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Entrada.class);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("proyecto.idProyecto", idProyecto));
		criteria.add(Restrictions.eq("producto.idProducto", idProducto));		
		
		List<Entrada> result = criteria.list();
		session.flush();
		session.close();
		return  new ArrayList<Entrada>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Salida> getIdProjectProductS(Integer idProyecto, Integer idProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Salida.class);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("proyecto.idProyecto", idProyecto));
		criteria.add(Restrictions.eq("producto.idProducto", idProducto));		
		
		List<Salida> result = criteria.list();
		session.flush();
		session.close();
		return  new ArrayList<Salida>(result);
	}
	
	
	public void saveReasignedEntryProyect(Entrada e, Salida s, Producto p) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
			session.save(e);
			session.save(s);
			session.update(p);
		
			session.getTransaction().commit();
			
		} catch (HibernateException ex) {
			ex.getStackTrace();
			session.getTransaction().rollback();
			
		} finally {
			session.flush();
			session.close();
		}
		
	}
	
	

}
