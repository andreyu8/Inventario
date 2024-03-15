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

import com.seidor.inventario.adapter.search.ProviderSearchAdapter;
import com.seidor.inventario.model.Proveedor;
import com.seidor.inventario.util.DaoUtil;

public class ProviderDAO extends HibernateDaoSupport {

		public Proveedor get(Integer id){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = session.createCriteria(Proveedor.class);
			criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
			criteria.setFetchMode("tipoPago", FetchMode.JOIN);
			criteria.add(Restrictions.eq("idProveedor", id));
			Proveedor result = (Proveedor)criteria.uniqueResult();
			session.flush();
			session.close();
			return result;
		}
		
		@SuppressWarnings("unchecked")
		public ArrayList<Proveedor> getAll(){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = DaoUtil.getCriteria(session, Proveedor.class);
			criteria.setFetchMode("tipoPago", FetchMode.JOIN);
			criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
			criteria.addOrder(Order.asc("idProveedor"));
			List<Proveedor> result = criteria.list();
			session.flush();
			session.close();
			
			return new ArrayList<Proveedor>(result);
		}
		
		@SuppressWarnings("unused")
		public void save(Proveedor p){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			
			Criteria criteria = DaoUtil.getCriteria(session, Proveedor.class);
			DaoUtil.prepareToSave(p);
			session.save(p);
			
			session.flush();
			session.close();
		}
		
		public void update(Proveedor p){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			
			DaoUtil.prepareToUpdate(p);
			session.update(p);
			session.flush();
			session.close();
		}
		
		public void delete(Proveedor p){
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			
			DaoUtil.prepareToDelete(p);
			
			session.update(p);
			session.flush();
			session.close();
		}

		@SuppressWarnings("unchecked")
		public ArrayList<Proveedor> search(ProviderSearchAdapter psa) {
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Criteria criteria = DaoUtil.getCriteria(session, Proveedor.class);
			
			criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
			criteria.setFetchMode("tipoPago", FetchMode.JOIN);
			
			if (psa.getName() != null && psa.getName().trim().length() > 0){
				criteria.add(Restrictions.ilike("nombre", psa.getName().trim(), MatchMode.ANYWHERE));
			}
			
			List<Proveedor> result = criteria.list();
			session.flush();
			session.close();
			
			return new ArrayList<Proveedor>(result);
		}

}
