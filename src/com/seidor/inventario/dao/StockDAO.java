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

import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.MovimientosStock;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.util.DaoUtil;

public class StockDAO extends HibernateDaoSupport{
	
	public void save (MovimientosStock s) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.save(s);
		
		session.flush();
		session.close();
	}
	
	public void update (MovimientosStock s) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(s);
		
		session.flush();
		session.close();
	}

	public void save(Entrada e, MovimientosStock s, Producto p) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
			session.save(s);
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
	public ArrayList<Producto> search(ProductSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		if (psa.getNombre() != null && psa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		criteria.add(Restrictions.gt("stock", 0));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}

	@SuppressWarnings("unchecked")
	public MovimientosStock get(Integer idProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(MovimientosStock.class);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("producto.idProducto", idProducto));
		
		List<MovimientosStock> result = criteria.list();
		session.flush();
		session.close();
		return  new ArrayList<MovimientosStock>(result).get(0);
	}

	@SuppressWarnings("unchecked")
	public MovimientosStock getIdProjectProduct(Integer idProyecto, Integer idProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(MovimientosStock.class);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.add(Restrictions.eq("proyecto.idProyecto", idProyecto));
		criteria.add(Restrictions.eq("producto.idProducto", idProducto));		
		
		List<MovimientosStock> result = criteria.list();
		session.flush();
		session.close();
		return  new ArrayList<MovimientosStock>(result).size() > 0 ?  new ArrayList<MovimientosStock>(result).get(0) : null;
	}

	public void saveEntryStock(MovimientosStock movSctock, Salida salida, Producto product) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
			session.save(movSctock);
			session.save(salida);
			session.update(product);
		
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
