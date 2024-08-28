package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.PurchaseOrderSearchAdapter;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.EstatusOrdenCompra;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.TipoPago;
import com.seidor.inventario.util.DaoUtil;
import com.seidor.inventario.util.SessionUtil;

public class PurchaseOrderDAO extends HibernateDaoSupport{
	
	public OrdenCompra get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		
		criteria.setFetchMode("cliente", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("etapa", FetchMode.JOIN);
		criteria.setFetchMode("tipoOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("proveedor.tipoPago", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("estatusOrdenCompra", FetchMode.JOIN);
		
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
		criteria.add(Restrictions.eq("almacen.idAlmacen", SessionUtil.getSucursalId()));
		criteria.addOrder(Order.asc("fecha"));
		List<OrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<OrdenCompra>(result);
	}
	
	
	public void save(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToSave(oc);
		session.save(oc);
		
		session.flush();
		session.close();
	}
	
	public void update(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(oc);
		session.update(oc);
		session.flush();
		session.close();
	}
	
	public void delete(OrdenCompra oc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(oc);
		session.update(oc);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<OrdenCompra> search(PurchaseOrderSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
	
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		
		criteria.setFetchMode("cliente", FetchMode.JOIN);
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (psa.getName() != null && psa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getNoOrden() != null && psa.getNoOrden().trim().length() > 0){
			criteria.add(Restrictions.eq("idOrdenCompra", Integer.parseInt((String) psa.getNoOrden().trim())));
		}
		
		criteria.addOrder(Order.desc("idOrdenCompra"));
		
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

	@SuppressWarnings("unchecked")
	public ArrayList<OrdenCompra> getProviderExist(Integer idProveedor) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("proveedor.idProveedor", idProveedor));
		
		List<OrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<OrdenCompra>(result);
	}

	public ArrayList<OrdenCompra> getAllnotComplete() {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(OrdenCompra orderCompra, ArrayList<DetalleOrdenCompra> listDE) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			
			DaoUtil.prepareToSave(orderCompra);
			session.save(orderCompra);
			
			for (DetalleOrdenCompra doc : listDE) {
				DaoUtil.prepareToSave(doc);
				session.save(doc);
			}
			
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			transaction.rollback();
			session.close();
			throw new RuntimeException(ex);
		}	
		
	}

	public void update(OrdenCompra orderCompra, ArrayList<DetalleOrdenCompra> detalleOCsave,
			ArrayList<DetalleOrdenCompra> detalleOCupdate) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			
			DaoUtil.prepareToUpdate(orderCompra);
			session.update(orderCompra);
			
			for (DetalleOrdenCompra doc : detalleOCsave) {
				DaoUtil.prepareToSave(doc);
				session.save(doc);
			}
			
			
			for (DetalleOrdenCompra doc : detalleOCupdate) {
				DaoUtil.prepareToUpdate(doc);
				session.update(doc);
			}
			
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			transaction.rollback();
			session.close();
			throw new RuntimeException(ex);
		}	
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<OrdenCompra> searchNew(PurchaseOrderSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DetachedCriteria subquery = DetachedCriteria.forClass(Movimientos.class)
				.setFetchMode("ordenCompra", FetchMode.JOIN)
			    .add(Restrictions.eq("fdl", Boolean.FALSE))
			    .add(Restrictions.isNotNull("ordenCompra.idOrdenCompra"))
			    .setProjection(Projections.property("ordenCompra.idOrdenCompra"));
		
		Criteria criteria = DaoUtil.getCriteria(session, OrdenCompra.class);
		
		criteria.setFetchMode("cliente", FetchMode.JOIN);
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (psa.getName() != null && psa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getNoOrden() != null && psa.getNoOrden().trim().length() > 0){
			criteria.add(Restrictions.eq("idOrdenCompra", Integer.parseInt((String) psa.getNoOrden().trim())));
		}
		
		//criteria.add(Subqueries.notIn("idOrdenCompra", subquery));
		criteria.addOrder(Order.desc("idOrdenCompra"));
		criteria.setMaxResults(100);
		
		List<OrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<OrdenCompra>(result);
	}

}
