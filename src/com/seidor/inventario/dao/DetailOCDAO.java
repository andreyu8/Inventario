package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.util.DaoUtil;

public class DetailOCDAO extends HibernateDaoSupport {
	
	public DetalleOrdenCompra get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("idDetalleOc", id));
		DetalleOrdenCompra result = (DetalleOrdenCompra)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DetalleOrdenCompra> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		
		criteria.addOrder(Order.asc("idDetalleOc"));
		List<DetalleOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleOrdenCompra>(result);
	}
	
	public void save(DetalleOrdenCompra doc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToSave(doc);
		session.save(doc);
		
		session.flush();
		session.close();
	}
	
	public void update(DetalleOrdenCompra doc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(doc);
		session.update(doc);
		session.flush();
		session.close();
	}
	
	public void delete(DetalleOrdenCompra doc){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(doc);
		/*doc.setFdl(true);
		doc.setLuu(Integer.parseInt((String) SessionUtil.getLoggedUserId().toString()));
		doc.setUat(new Date());
		System.out.println(doc.getUat());*/
		session.update(doc);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DetalleOrdenCompra> search(ProductSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (psa.getNombre() != null && psa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getCodigo() != null && psa.getCodigo().trim().length() > 0){
			criteria.add(Restrictions.ilike("codigo", psa.getCodigo().trim(), MatchMode.ANYWHERE));
		}
		
		criteria.add(Restrictions.eq("almacen.idAlmacen", psa.getIdAlmacen()));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<DetalleOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleOrdenCompra>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DetalleOrdenCompra> getOC(Integer id) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("ordenCompra.idOrdenCompra", id));
		
		/*ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.sum("cantidad"));
	    projectionList.add(Projections.sum("precioUnitario"));
	    projectionList.add(Projections.sum("cantidadFactura"));
	    projectionList.add(Projections.groupProperty("producto"));
	    criteria.setProjection(projectionList);*/
		
		criteria.addOrder(Order.desc("producto"));
		List<DetalleOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleOrdenCompra>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DetalleOrdenCompra> getIdOCAll(Integer idOrdenCompra) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("ordenCompra.idOrdenCompra", idOrdenCompra));
		List<DetalleOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleOrdenCompra>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DetalleOrdenCompra> getProductCode(Integer[] idsProduct) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DetalleOrdenCompra.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tipoMoneda", FetchMode.JOIN);
		criteria.setFetchMode("producto", FetchMode.JOIN);
		criteria.setFetchMode("producto.unidadMedida", FetchMode.JOIN);
		
		criteria.add(Restrictions.in("producto.idProducto", idsProduct));
		List<DetalleOrdenCompra> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DetalleOrdenCompra>(result);
	}
	
	

}
