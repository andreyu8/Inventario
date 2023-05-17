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

import com.seidor.inventario.adapter.search.EmployeeSearchAdapter;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.util.DaoUtil;

public class EmployeeDAO extends HibernateDaoSupport{
	
	public Empleado get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Empleado.class);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.add(Restrictions.eq("idEmpleado", id));
		Empleado result = (Empleado)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Empleado> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Empleado.class);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		criteria.addOrder(Order.asc("nombre"));
		List<Empleado> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Empleado>(result);
	}
	
	
	public void save(Empleado e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		session.save(e);
		
		session.flush();
		session.close();
	}
	
	public void update(Empleado e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(e);
		session.flush();
		session.close();
	}
	
	public void delete(Empleado e){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.delete(e);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Empleado> search(EmployeeSearchAdapter esa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Empleado.class);
		
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (esa.getName() != null && esa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", esa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		List<Empleado> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Empleado>(result);
	}

}
