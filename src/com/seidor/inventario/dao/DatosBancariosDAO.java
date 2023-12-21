package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.constants.SystemConstants;
import com.seidor.inventario.model.DatosBancarios;
import com.seidor.inventario.util.DaoUtil;

public class DatosBancariosDAO extends HibernateDaoSupport{
	
	public DatosBancarios get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(DatosBancarios.class);
		
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("idDatosBancarios", id));
		DatosBancarios result = (DatosBancarios)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<DatosBancarios> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DatosBancarios.class);
		
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		
		criteria.addOrder(Order.asc("idDatosBancarios"));
		List<DatosBancarios> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DatosBancarios>(result);
	}
	
	public void save(DatosBancarios db){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		DaoUtil.prepareToSave(db);
		session.save(db);
		
		session.flush();
		session.close();
	}
	
	public void update(DatosBancarios db){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(db);
		session.update(db);
		session.flush();
		session.close();
	}
	
	public void delete(DatosBancarios db){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(db);
		session.update(db);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<DatosBancarios> getDatosBancarios(Integer providerId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, DatosBancarios.class);
		
		criteria.setFetchMode("proveedor", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("proveedor.idProveedor", providerId));
		criteria.add(Restrictions.eq("estatus", SystemConstants.DATOSBANCARIOS_ACTIVA));
		
		criteria.addOrder(Order.asc("idDatosBancarios"));
		List<DatosBancarios> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<DatosBancarios>(result);
	}
	
}
