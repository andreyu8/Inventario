package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.util.DaoUtil;

public class OutputDAO extends HibernateDaoSupport{

	
	public void update (Salida s) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(s);
		
		session.flush();
		session.close();
	}

	public void save(Salida s, Producto p) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
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
	
	

	@SuppressWarnings("unchecked")
	public ArrayList<Salida> getOutpuAll(Integer projectId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Salida.class);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("tipoTrabajo", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("ordenTrabajo", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("proyecto.idProyecto", projectId));
		
		List<Salida> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Salida>(result);
	}
		
}
