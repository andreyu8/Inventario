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

import com.seidor.inventario.adapter.search.InvoiceSearchAdapter;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.util.DaoUtil;

public class InvoiceDAO extends HibernateDaoSupport{
	
	public Factura get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.add(Restrictions.eq("idFactura", id));
		Factura result = (Factura)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	
	public Factura getNoFactura(String noFactura){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.add(Restrictions.eq("numeroFactura", noFactura));
		Factura result = (Factura)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Factura> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Factura.class);
		criteria.addOrder(Order.desc("fecha"));
		List<Factura> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Factura>(result);
	}
	
	
	public void save(Factura f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToSave(f);
		session.save(f);
		
		session.flush();
		session.close();
	}
	
	public void update(Factura f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToUpdate(f);
		session.update(f);
		session.flush();
		session.close();
	}
	
	public void delete(Factura f){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToDelete(f);
		session.update(f);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Factura> search(InvoiceSearchAdapter isa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Factura.class);
		
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (isa.getName() != null && isa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("numeroFactura", isa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		criteria.addOrder(Order.desc("idFactura"));
		
		List<Factura> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Factura>(result);
	}

	public Factura getOC(Integer idOrdenCompra) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Factura.class);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.add(Restrictions.eq("ordenCompra.idOrdenCompra", idOrdenCompra));
		Factura result = (Factura)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	

}
