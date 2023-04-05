package com.seidor.inventario.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.util.DaoUtil;

public class RoleDAO extends HibernateDaoSupport {

	public Perfil get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Perfil.class);
		criteria.add(Restrictions.eq("idPerfil", id));
		Perfil result = (Perfil)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Perfil> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Perfil.class);
		criteria.addOrder(Order.asc("idPerfil"));
		List<Perfil> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Perfil>(result);
	}
	
	@SuppressWarnings("unused")
	public void save(Perfil p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Perfil.class);
		session.save(p);
		
		session.flush();
		session.close();
	}
	
	public void update(Perfil p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(p);
		session.flush();
		session.close();
	}
	
	public void delete(Perfil p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(p);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<PerfilUsuario> getProfileUser(Usuario usuario) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, PerfilUsuario.class);
		criteria.setFetchMode("perfil", FetchMode.JOIN);
		criteria.setFetchMode("usuario", FetchMode.JOIN);
		criteria.addOrder(Order.asc("perfil.idPerfil"));
		criteria.add(Restrictions.eq("usuario.idUsuario", usuario.getIdUsuario()));
		List<PerfilUsuario> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<PerfilUsuario>(result);
	}
	
}	