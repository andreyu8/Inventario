package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.search.DevolcionSearchAdapter;
import com.seidor.inventario.adapter.search.TransactionSearchAdapter;
import com.seidor.inventario.adapter.search.TraspasoSearchAdapter;
import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.model.DetalleMovimiento;
import com.seidor.inventario.model.DetalleOrdenCompra;
import com.seidor.inventario.model.Entrada;
import com.seidor.inventario.model.Factura;
import com.seidor.inventario.model.Folios;
import com.seidor.inventario.model.Movimientos;
import com.seidor.inventario.model.OrdenCompra;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.Salida;
import com.seidor.inventario.util.DaoUtil;
import com.seidor.inventario.util.SessionUtil;

public class TransactionDAO extends HibernateDaoSupport{
	
	public Movimientos get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		
		
		criteria.add(Restrictions.eq("idMovimiento", id));
		Movimientos result = (Movimientos)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Movimientos> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		
		criteria.addOrder(Order.asc("idArea"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Movimientos m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		DaoUtil.prepareToSave(m);
		session.save(m);
		
		session.flush();
		session.close();
	}
	
	public void update(Movimientos m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(m);
		session.update(m);
		session.flush();
		session.close();
	}
	
	public void delete(Movimientos m){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(m);
		session.update(m);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movimientos> searchEntry(TransactionSearchAdapter tsa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		
		if (tsa.getNumeroFolio() != null)
			criteria.add(Restrictions.ilike("folio", tsa.getNumeroFolio(), MatchMode.ANYWHERE));
		
		if (tsa.getNumeroOC() != null)
			criteria.add(Restrictions.eq("ordenCompra.idOrdenCompra", tsa.getNumeroOC()));
		
		if (tsa.getIdFactura() != null)
			criteria.add(Restrictions.eq("factura.idFactura", tsa.getIdFactura()));
		
		criteria.add(Restrictions.eq("tiposMovimiento.idTipoMovimiento", SystemConstants.ENTRADA_COMPRA));
		criteria.add(Restrictions.eq("almacen.idAlmacen", SessionUtil.getSucursalId()));
		
		criteria.addOrder(Order.asc("idMovimiento"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Movimientos> searchInvoiceEntry(Integer idFactura) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("factura.numeroFactura", idFactura));
		
		criteria.addOrder(Order.asc("idMovimiento"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}

	//se guarda la entrada
	public void saveEntrada(Factura factura, Movimientos movimiento, ArrayList<DetalleMovimiento> listDetailTransactionENT, Folios fte, ArrayList<Producto> listProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			
			DaoUtil.prepareToSave(factura);
			session.save(factura);
			
			DaoUtil.prepareToSave(movimiento);
			movimiento.setFactura(factura);
			session.save(movimiento);
			
			for (DetalleMovimiento dm : listDetailTransactionENT) {
				if (dm.getCantidad() > 0.0) {
					dm.setMovimientos(movimiento);
					DaoUtil.prepareToSave(dm);
					session.save(dm);
					
					//se actualiza el valor sumandole el que tenia por default
					dm.getDetalleOrdenCompra().setCantidadFactura(dm.getDetalleOrdenCompra().getCantidadFactura() + dm.getCantidad());
					session.update(dm.getDetalleOrdenCompra());
				}	
			}
			
			fte.setConsecutivo(fte.getConsecutivo()+1);
			DaoUtil.prepareToUpdate(fte);
			session.update(fte);
			
			for (Producto p : listProducto) {
				DaoUtil.prepareToUpdate(p);
				session.update(p);
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
	public ArrayList<Movimientos> searchOutput(TransactionSearchAdapter tsa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);

		
		if (tsa.getNumeroFolio() != null)
			criteria.add(Restrictions.ilike("folio", tsa.getNumeroFolio(), MatchMode.ANYWHERE));
		
		criteria.add(Restrictions.eq("tiposMovimiento.idTipoMovimiento", SystemConstants.SALIDA_POR_VALE));
		criteria.add(Restrictions.eq("almacen.idAlmacen", SessionUtil.getSucursalId()));
		
		criteria.addOrder(Order.asc("idMovimiento"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}

	//save de la salida
	public void saveSalida(Movimientos movimiento, ArrayList<DetalleMovimiento> listDetailTransactionSAL, Folios fte, ArrayList<Producto> listProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			
			
			DaoUtil.prepareToSave(movimiento);
			session.save(movimiento);
			
			for (DetalleMovimiento dm : listDetailTransactionSAL) {
				dm.setMovimientos(movimiento);
				DaoUtil.prepareToSave(dm);
				session.save(dm);
			}
			
			fte.setConsecutivo(fte.getConsecutivo()+1);
			DaoUtil.prepareToUpdate(fte);
			session.update(fte);
			
			for (Producto p : listProducto) {
				DaoUtil.prepareToUpdate(p);
				session.update(p);
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

	public Movimientos getSalida(Integer idMovimiento) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("idMovimiento", idMovimiento));
		Movimientos result = (Movimientos)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}

	public void saveDevoluciones(ArrayList<Producto> listProducto, Movimientos movimientoEntradaStock, ArrayList<DetalleMovimiento> listEntradaStock,
			Folios fes, Movimientos movimientoSalidaReasignacion, ArrayList<DetalleMovimiento> listSalidaReasignacion, Folios fsr) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {
			
			for (Producto p : listProducto) {
				DaoUtil.prepareToUpdate(p);
				session.update(p);
			}
			
			DaoUtil.prepareToSave(movimientoEntradaStock);
			session.save(movimientoEntradaStock);
			
			for (DetalleMovimiento dm : listEntradaStock) {
				dm.setMovimientos(movimientoEntradaStock);
				DaoUtil.prepareToSave(dm);
				session.save(dm);
			}
			
			fes.setConsecutivo(fes.getConsecutivo()+1);
			DaoUtil.prepareToUpdate(fes);
			session.update(fes);
			
		
			DaoUtil.prepareToSave(movimientoSalidaReasignacion);
			session.save(movimientoSalidaReasignacion);
			
			for (DetalleMovimiento dm : listSalidaReasignacion) {
				dm.setMovimientos(movimientoSalidaReasignacion);
				DaoUtil.prepareToSave(dm);
				session.save(dm);
			}
			
			fsr.setConsecutivo(fsr.getConsecutivo()+1);
			DaoUtil.prepareToUpdate(fsr);
			session.update(fsr);
			
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
	public ArrayList<Movimientos> searchDevolucion(DevolcionSearchAdapter dsa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);

		
		if (dsa.getNumeroFolio() != null)
			criteria.add(Restrictions.ilike("folio", dsa.getNumeroFolio(), MatchMode.ANYWHERE));
		
		criteria.add(Restrictions.eq("tiposMovimiento.idTipoMovimiento", SystemConstants.DEVOLUCION));
		criteria.add(Restrictions.eq("almacen.idAlmacen", SessionUtil.getSucursalId()));
		
		criteria.addOrder(Order.asc("idMovimiento"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}

	public void saveDevolucion(Movimientos movimiento, ArrayList<DetalleMovimiento> listDetailTransactionDEV, Folios fte, ArrayList<Producto> listProducto) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		try {

			DaoUtil.prepareToSave(movimiento);
			session.save(movimiento);
			
			for (DetalleMovimiento dm : listDetailTransactionDEV) {
				dm.setMovimientos(movimiento);
				DaoUtil.prepareToSave(dm);
				session.save(dm);
			}
			
			fte.setConsecutivo(fte.getConsecutivo()+1);
			DaoUtil.prepareToUpdate(fte);
			session.update(fte);
			
			for (Producto p : listProducto) {
				DaoUtil.prepareToUpdate(p);
				session.update(p);
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
	public ArrayList<Movimientos> searchTraspasos(TraspasoSearchAdapter dsa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.setFetchMode("factura", FetchMode.JOIN);
		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		criteria.setFetchMode("tiposMovimiento", FetchMode.JOIN);
		criteria.setFetchMode("proyecto", FetchMode.JOIN);
		criteria.setFetchMode("area", FetchMode.JOIN);
		criteria.setFetchMode("empleado", FetchMode.JOIN);

		
		if (dsa.getNumeroFolio() != null)
			criteria.add(Restrictions.ilike("folio", dsa.getNumeroFolio(), MatchMode.ANYWHERE));
		
		criteria.add(Restrictions.eq("tiposMovimiento.idTipoMovimiento", SystemConstants.TRASPASO_ALMACEN));
		criteria.add(Restrictions.eq("almacen.idAlmacen", SessionUtil.getSucursalId()));
		
		criteria.addOrder(Order.asc("idMovimiento"));
		List<Movimientos> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Movimientos>(result);
	}

	public Movimientos getExistOC(Integer idOrdenCompra) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Movimientos.class);

		criteria.setFetchMode("ordenCompra", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("ordenCompra.idOrdenCompra", idOrdenCompra));
		
		Movimientos result = (Movimientos)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}

	public void deleteEntrada(Movimientos movimiento, ArrayList<DetalleMovimiento> detalleMovimientos,
			Factura factura, OrdenCompra ordenCompra, ArrayList<DetalleOrdenCompra> detalleOrdenCompra) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Boolean flagSalida = Boolean.FALSE;
		
		try {

			DaoUtil.prepareToDelete(movimiento);
			session.update(movimiento);
			
			for (DetalleMovimiento dm : detalleMovimientos) {
				dm.setMovimientos(movimiento);
				DaoUtil.prepareToDelete(dm);
				session.update(dm);
				
				dm.getProducto().setCantidad(dm.getProducto().getCantidad() - dm.getCantidad());
				DaoUtil.prepareToUpdate(dm.getProducto());
				session.update(dm.getProducto());	
				
				if (dm.getProducto().getCantidad() < 0)
					flagSalida= Boolean.TRUE;
				
			}
			
			DaoUtil.prepareToDelete(factura);
			session.update(factura);
			
			DaoUtil.prepareToUpdate(ordenCompra);
			session.update(ordenCompra);
			
			for (DetalleOrdenCompra doc : detalleOrdenCompra) {
				
				for (DetalleMovimiento dm : detalleMovimientos) {
					
					if (doc.getProducto().getIdProducto().equals(dm.getProducto().getIdProducto())) {
						doc.setCantidadFactura(doc.getCantidadFactura() - dm.getCantidad());
						
						DaoUtil.prepareToUpdate(doc);
						session.update(doc);
						
						break;
					}
					
				}
				
			}
			
			if (!flagSalida) {
				transaction.commit();
				session.flush();
				session.close();
			} else {
				transaction.rollback();
				session.close();
			} 
			
			
			
		} catch (Exception ex) {
			transaction.rollback();
			session.close();
			throw new RuntimeException(ex);
		}	
	}
	

}
