package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.util.DaoUtil;

public class TransactionDetailDAO extends HibernateDaoSupport{
	
	public DetalleMovimiento get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(DetalleMovimiento.class);
		
		criteria.setFetchMode("movimientos", FetchMode.JOIN);
		criteria.setFetchMode("detalleOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("producto.almacen", FetchMode.JOIN);
		
		
		criteria.add(Restrictions.eq("idDetalleMovimiento", id));
		DetalleMovimiento result = (DetalleMovimiento)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DetalleMovimiento> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleMovimiento.class);
		
		criteria.setFetchMode("movimientos", FetchMode.JOIN);
		criteria.setFetchMode("detalleOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("producto.almacen", FetchMode.JOIN);
		
		criteria.addOrder(Order.asc("idDetalleMovimiento"));
		List<DetalleMovimiento> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleMovimiento>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(DetalleMovimiento dm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, DetalleMovimiento.class);
		DaoUtil.prepareToSave(dm);
		session.save(dm);
		
		session.flush();
		session.close();
	}
	
	public void update(DetalleMovimiento dm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(dm);
		session.update(dm);
		session.flush();
		session.close();
	}
	
	public void delete(DetalleMovimiento dm){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(dm);
		session.update(dm);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DetalleMovimiento> getDetails(Integer idMovimiento) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleMovimiento.class);

		criteria.setFetchMode("movimientos", FetchMode.JOIN);
		criteria.setFetchMode("detalleOrdenCompra", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("producto.almacen", FetchMode.JOIN);
		
		
		criteria.add(Restrictions.eq("movimientos.idMovimiento", idMovimiento));
		criteria.addOrder(Order.asc("idDetalleMovimiento"));
		List<DetalleMovimiento> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleMovimiento>(result);
	}

}
