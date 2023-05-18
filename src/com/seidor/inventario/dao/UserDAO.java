package com.seidor.inventario.dao;

import com.seidor.inventario.adapter.UserAdapter;
import com.seidor.inventario.adapter.search.UserSearchAdapter;
import com.seidor.inventario.exception.BusinessException;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.util.DaoUtil;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDAO extends HibernateDaoSupport {
	
	@SuppressWarnings("unchecked")
	public void save(UserAdapter ua){
		SessionImpl session = (SessionImpl)this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Criteria criteria = DaoUtil.getCriteria(session, Usuario.class);
			criteria.add(Restrictions.eq("usuario", ua.getUsuario().getEmpleado().getNombre()));
			List<Usuario> result = criteria.list();
			if (result.size() == 0) { 
				session.save(ua.getUsuario());
			}
			else {
				throw new BusinessException("El usuario espcificado no esta disponible");
			}
			
			
			ArrayList<PerfilUsuario> profiles = ua.getProfiles();
			for (PerfilUsuario p : profiles){
				session.save(p);
			}
			

			transaction.commit();
//			session.afterTransactionCompletion(transaction, true);
			session.flush();
			session.close();
		} catch (BusinessException ex) {
			transaction.rollback();
//			session.afterTransactionCompletion(false, transaction);
			session.close();
			throw ex;
		} catch (Exception ex) {
			transaction.rollback();
//			session.afterTransactionCompletion(false, transaction);
			session.close();
			throw new RuntimeException(ex);
		}
	}
	
	public void update2(Usuario u){
		
		SessionImpl session = (SessionImpl)this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			session.update(u);
			
			//Profiles
//			for (UserProfile p : profiles){
//				session.saveOrUpdate(p);
//			}
//			for (UserProfile p : profilesToDelete){
//				session.update(p);
//			}
			
			transaction.commit();
//			session.afterTransactionCompletion(true, transaction);
			session.flush();
			session.close();
		} catch (Exception ex) {
			transaction.rollback();
//			session.afterTransactionCompletion(false, transaction);
			session.close();
			throw new RuntimeException(ex);
		}
	}
	
	public void update(Usuario u){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(u);
		session.flush();
		session.close();
	}
	
	public void delete(Usuario u){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		session.update(u);
		session.flush();
		session.close();
	}
	
	public Usuario get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Usuario.class);
		criteria.add(Restrictions.eq("idUsuario", id));
		
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("empleado.almacen", FetchMode.JOIN);
		
		Usuario result = (Usuario)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> get(ArrayList<Integer> ids){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Usuario.class);
		criteria.add(Restrictions.in("id", ids));
		
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("empleado.almacen", FetchMode.JOIN);

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Usuario> result = criteria.list();
		session.flush();
		session.close();
		return new ArrayList<Usuario>(result);
	}
	
	@SuppressWarnings("unchecked")
	public Usuario get(String username){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Usuario.class);
		criteria.add(Restrictions.eq("usuario", username));
		
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("empleado.almacen", FetchMode.JOIN);
		
				
		List<Usuario> result = criteria.list();
		session.flush();
		session.close();
		ArrayList<Usuario> users = new ArrayList<Usuario>(result);
		for (Usuario user : users) {
			if(user.getUsuario().equals(username)){
				return user;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Usuario> search(UserSearchAdapter usa){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Usuario.class);
		
		criteria.setFetchMode("empleado", FetchMode.JOIN);
		criteria.setFetchMode("empleado.almacen", FetchMode.JOIN);
				
		
		if (usa.getName() != null && usa.getName().trim().length() > 0){
			criteria.add(Restrictions.ilike("empleado.nombre", usa.getName().trim(), MatchMode.ANYWHERE));
		}
		
		if (usa.getUser() != null && usa.getUser().trim().length() > 0){
			criteria.add(Restrictions.ilike("empleado.EMail", usa.getUser(), MatchMode.ANYWHERE));
		}
		
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Usuario> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Usuario>(result);
	}

	public void insertProfile(ArrayList<PerfilUsuario> profilesAdd) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		for (PerfilUsuario p: profilesAdd) {
			session.save(p);
		}	
		session.flush();
		session.close();
		
	}

	public void deleteProfile(ArrayList<PerfilUsuario> profilesDeleted) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		for (PerfilUsuario p: profilesDeleted) {
			session.update(profilesDeleted);
		}	
		session.flush();
		session.close();
	}
	
//	@SuppressWarnings("unchecked")
//	public ArrayList<UserProfile> getProfilesForUser(Integer userId){
//		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
//		Criteria criteria = DaoUtil.getCriteria(session, UserProfile.class);
//		DaoUtil.getCriteria(criteria, "users").add(Restrictions.eq("id", userId));
//		
//		criteria.setFetchMode("profile", FetchMode.JOIN);
//		
//		List<UserProfile> result = criteria.list();
//		session.flush();
//		session.close();
//		
//		return new ArrayList<UserProfile>(result);
//	}
	
}