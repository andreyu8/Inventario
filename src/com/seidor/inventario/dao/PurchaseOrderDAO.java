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

import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.TipoPago;
import com.seidor.inventario.util.DaoUtil;

public class PurchaseOrderDAO extends HibernateDaoSupport{
	
	public OrdenCompra get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(OrdenCompra.class);
		
		criteria.setFetchMode("cliente", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("etapa", FetchMode.JOIN);
		criteria.setFetchMode("tipoOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("estatusOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoPago", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("idOrdenCompra", id));
		OrdenCompra result = (OrdenCompra)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<OrdenCompra> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		criteria.addOrder(Order.asc("fecha"));
		List<OrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<OrdenCompra>(result);
	}
	
	
	public void save(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		session.save(oc);
		
		session.flush();
		session.close();
	}
	
	public void update(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(oc);
		session.flush();
		session.close();
	}
	
	public void delete(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(oc);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<OrdenCompra> search(PurchaseOrderSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		
		criteria.setFetchMode("cliente", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		
		if (psa.getName() != null && psa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		List<OrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<OrdenCompra>(result);
	}
	
	//catalogs of the purchaseorder
	@SuppressWarnings("unchecked")
	public ArrayList<EstatusOrdenCompra> getAllTypeOrder(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, EstatusOrdenCompra.class);
		criteria.add(Restrictions.eq("activo", 1));
		List<EstatusOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<EstatusOrdenCompra>(result);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TipoPago> getAllTypePayment(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, TipoPago.class);
		criteria.add(Restrictions.eq("activo", 1));
		List<TipoPago> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<TipoPago>(result);
	}

}
