package com.seidor.inventario.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.seidor.inventario.adapter.beans.CierreBean;
import com.seidor.inventario.adapter.beans.CloseBean;
import com.seidor.inventario.adapter.beans.EntradasProyectoBean;
import com.seidor.inventario.adapter.beans.ProveedoresBean;
import com.seidor.inventario.adapter.beans.ReasignedBean;
import com.seidor.inventario.adapter.beans.ReportCostoInventario;
import com.seidor.inventario.adapter.beans.ReportCostoInventarioGBean;
import com.seidor.inventario.adapter.beans.SalidaProyectoBean;
import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.util.DaoUtil;

public class ProductDAO extends HibernateDaoSupport{

	public Producto get(Integer id){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		
		criteria.add(Restrictions.eq("idProducto", id));
		Producto result = (Producto)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Producto> getAll(){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		criteria.addOrder(Order.asc("nombre"));
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Producto> getAll(Integer almacenId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("almacen.idAlmacen", almacenId));
		
		criteria.addOrder(Order.asc("nombre"));
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}
	
	@SuppressWarnings("unchecked")
	public void save(Producto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		criteria.add(Restrictions.eq("nombre", p.getNombre()));
		List<Producto> result = criteria.list();
		if (result.size() == 0) { 
			DaoUtil.prepareToSave(p);
			session.save(p);
		}
		
		session.flush();
		session.close();
	}
	
	public void update(Producto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToUpdate(p);
		session.update(p);
		session.flush();
		session.close();
	}
	
	public void delete(Producto p){
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		DaoUtil.prepareToDelete(p);
		session.update(p);
		session.flush();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Producto> search(ProductSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		if (psa.getNombre() != null && psa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getCodigo() != null && psa.getCodigo().trim().length() > 0){
			criteria.add(Restrictions.ilike("codigo", psa.getCodigo().trim(), MatchMode.ANYWHERE));
		}
		
		criteria.add(Restrictions.eq("almacen.idAlmacen", psa.getIdAlmacen()));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Producto> searchRP(ProductSearchAdapter psa) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		if (psa.getNombre() != null && psa.getNombre().trim().length() > 0){
			criteria.add(Restrictions.ilike("nombre", psa.getNombre().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getCodigo() != null && psa.getCodigo().trim().length() > 0){
			criteria.add(Restrictions.ilike("codigo", psa.getCodigo().trim(), MatchMode.ANYWHERE));
		}
		
		if (psa.getIdCategoria() > 0){
			criteria.add(Restrictions.eq("categoria.idCategoria", psa.getIdCategoria()));
		}
		
		//if el usuaio != 1 tomo el almacen
		//criteria.add(Restrictions.eq("almacen.idAlmacen", criteria));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<CierreBean> getCierreProyecto (Integer projectId) {
		
		
		ArrayList<CierreBean> result = new ArrayList<CierreBean>();
		CierreBean cierreBean = new CierreBean();
		
		List<Object[]> rows = new ArrayList<Object[]>();
		
		StringBuilder sb = new  StringBuilder(); 
		sb.append("SELECT  " );
		sb.append("a.Familia AS Familia, a.id_producto, a.codigo,a.nombre, a.unidad_medida, a.sum_entra , ");
		sb.append("b.Familia AS Familia_s , b.id_producto AS id_producto_s, b.codigo AS codigo_s, b.nombre AS nombre_s, b.unidad_medida AS unidad_medida_s ,b.sum_salida,   ");
		sb.append(" IFNULL ((a.sum_entra-b.sum_salida),a.sum_entra)  as diferencia ");
		sb.append(" FROM ( ");
		sb.append(" SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida,SUM(e.cantidad) AS sum_entra  ");
		sb.append("FROM entrada e , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE e.id_proyecto = :project  ");
		sb.append("AND e.id_producto = p.id_producto  ");
		sb.append("AND e.id_unidad_medida = u.id_unidad_medida ");
		sb.append("AND p.id_categoria = c.id_categoria ");
		sb.append("GROUP BY c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") a  ");
		sb.append("LEFT JOIN ( ");
		sb.append("SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida, SUM(s.cantidad) AS sum_salida   ");
		sb.append("FROM salida s , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE s.id_proyecto = :project  ");
		sb.append("AND s.id_producto = p.id_producto  ");
		sb.append("AND s.id_unidad_medida = u.id_unidad_medida  ");
		sb.append( "AND p.id_categoria = c.id_categoria  ");
		sb.append("GROUP BY  c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") b  ");
		sb.append("ON  a.codigo =  b.codigo");
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("id_producto", IntegerType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("nombre", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("sum_entra", IntegerType.INSTANCE);
		
		query.addScalar("Familia_s", StringType.INSTANCE);
		query.addScalar("id_producto_s", IntegerType.INSTANCE);
		query.addScalar("codigo_s", StringType.INSTANCE);
		query.addScalar("nombre_s", StringType.INSTANCE);
		query.addScalar("unidad_medida_s", StringType.INSTANCE);
		query.addScalar("sum_salida", IntegerType.INSTANCE);
		query.addScalar("diferencia", IntegerType.INSTANCE);
		
		query.setParameter("project", projectId);
		
		rows = query.list();
		
		for (Object[] cp : rows) {
			
			cierreBean = new CierreBean();
				
			cierreBean.setFamilia(cp[0] == null ? "" : (String) cp[0]);
			cierreBean.setIdProducto((int) cp[1]);
			cierreBean.setCodigo(cp[2] == null ? "" : (String) cp[2]);
			cierreBean.setProducto(cp[3] == null ? "" : (String) cp[3]);
			cierreBean.setUnidadMedida(cp[4] == null ? "" : (String) cp[4]);
			cierreBean.setCantidadEntrada((int) cp[5]);
			
			cierreBean.setFamilia_s(cp[6] == null ? "" : (String) cp[6]);
			cierreBean.setIdProducto_s(cp[7] == null ? 0 : (int) cp[7]);
			cierreBean.setCodigo_s(cp[8] == null ? "" : (String) cp[8]);
			cierreBean.setProducto_s(cp[9] == null ? "" : (String) cp[9]);
			cierreBean.setUnidadMedida_s(cp[10] == null ? "" : (String) cp[10]);
			cierreBean.setCantidadSalida(cp[11] == null ? 0 : (int) cp[11] );
			
			cierreBean.setDiferencia((int) cp[12]);
			
			
			
			result.add(cierreBean);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
		
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<CloseBean> getCloseProyecto (Integer projectId) {
		
		
		ArrayList<CloseBean> result = new ArrayList<CloseBean>();
		CloseBean cierreBean = new CloseBean();
		
		List<Object[]> rows = new ArrayList<Object[]>();
		
		StringBuilder sb = new  StringBuilder(); 
		sb.append("SELECT  " );
		sb.append("a.Familia AS Familia, a.id_producto, a.codigo,a.nombre, a.unidad_medida, a.sum_entra , ");
		sb.append("b.Familia AS Familia_s , b.id_producto AS id_producto_s, b.codigo AS codigo_s, b.nombre AS nombre_s, b.unidad_medida AS unidad_medida_s ,b.sum_salida,   ");
		sb.append(" IFNULL ((a.sum_entra-b.sum_salida),a.sum_entra)  as diferencia ");
		sb.append(" FROM ( ");
		sb.append(" SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida,SUM(e.cantidad) AS sum_entra  ");
		sb.append("FROM entrada e , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE e.id_proyecto = :project  ");
		sb.append("AND e.id_producto = p.id_producto  ");
		sb.append("AND e.id_unidad_medida = u.id_unidad_medida ");
		sb.append("AND p.id_categoria = c.id_categoria ");
		sb.append("GROUP BY c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") a  ");
		sb.append("LEFT JOIN ( ");
		sb.append("SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida, SUM(s.cantidad) AS sum_salida   ");
		sb.append("FROM salida s , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE s.id_proyecto = :project  ");
		sb.append("AND s.id_producto = p.id_producto  ");
		sb.append("AND s.id_unidad_medida = u.id_unidad_medida  ");
		sb.append( "AND p.id_categoria = c.id_categoria  ");
		sb.append("GROUP BY  c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") b  ");
		sb.append("ON  a.codigo =  b.codigo");
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("id_producto", IntegerType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("nombre", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("sum_entra", IntegerType.INSTANCE);
		
		query.addScalar("Familia_s", StringType.INSTANCE);
		query.addScalar("id_producto_s", IntegerType.INSTANCE);
		query.addScalar("codigo_s", StringType.INSTANCE);
		query.addScalar("nombre_s", StringType.INSTANCE);
		query.addScalar("unidad_medida_s", StringType.INSTANCE);
		query.addScalar("sum_salida", IntegerType.INSTANCE);
		query.addScalar("diferencia", IntegerType.INSTANCE);
		
		query.setParameter("project", projectId);
		
		rows = query.list();
		
		for (Object[] cp : rows) {
			
			cierreBean = new CloseBean();
				
			cierreBean.setFamilia(cp[0] == null ? "" : (String) cp[0]);
			cierreBean.setIdProducto((int) cp[1]);
			cierreBean.setCodigo(cp[2] == null ? "" : (String) cp[2]);
			cierreBean.setProducto(cp[3] == null ? "" : (String) cp[3]);
			cierreBean.setUnidadMedida(cp[4] == null ? "" : (String) cp[4]);
			cierreBean.setCantidadEntrada((int) cp[5]);
			
			cierreBean.setFamilia_s(cp[6] == null ? "" : (String) cp[6]);
			cierreBean.setIdProducto_s(cp[7] == null ? 0 : (int) cp[7]);
			cierreBean.setCodigo_s(cp[8] == null ? "" : (String) cp[8]);
			cierreBean.setProducto_s(cp[9] == null ? "" : (String) cp[9]);
			cierreBean.setUnidadMedida_s(cp[10] == null ? "" : (String) cp[10]);
			cierreBean.setCantidadSalida(cp[11] == null ? 0 : (int) cp[11] );
			
			cierreBean.setDiferencia((int) cp[12]);
						
			result.add(cierreBean);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReasignedBean> getReasignedProyecto (Integer projectId) {
		
		
		ArrayList<ReasignedBean> result = new ArrayList<ReasignedBean>();
		ReasignedBean reasignedBean = new ReasignedBean();
		
		List<Object[]> rows = new ArrayList<Object[]>();
		
		StringBuilder sb = new  StringBuilder(); 
		sb.append("SELECT  " );
		sb.append("a.Familia AS Familia, a.id_producto, a.codigo,a.nombre, a.unidad_medida, a.sum_entra , ");
		sb.append("b.Familia AS Familia_s , b.id_producto AS id_producto_s, b.codigo AS codigo_s, b.nombre AS nombre_s, b.unidad_medida AS unidad_medida_s ,b.sum_salida,   ");
		sb.append(" IFNULL ((a.sum_entra-b.sum_salida),a.sum_entra)  as diferencia ");
		sb.append(" FROM ( ");
		sb.append(" SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida,SUM(e.cantidad) AS sum_entra  ");
		sb.append("FROM entrada e , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE e.id_proyecto = :project  ");
		sb.append("AND e.id_producto = p.id_producto  ");
		sb.append("AND e.id_unidad_medida = u.id_unidad_medida ");
		sb.append("AND p.id_categoria = c.id_categoria ");
		sb.append("GROUP BY c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") a  ");
		sb.append("LEFT JOIN ( ");
		sb.append("SELECT c.categoria AS Familia, p.id_producto, p.codigo,p.nombre, u.unidad_medida, SUM(s.cantidad) AS sum_salida   ");
		sb.append("FROM salida s , producto p, unidad_medida u, categoria c ");
		sb.append("WHERE s.id_proyecto = :project  ");
		sb.append("AND s.id_producto = p.id_producto  ");
		sb.append("AND s.id_unidad_medida = u.id_unidad_medida  ");
		sb.append( "AND p.id_categoria = c.id_categoria  ");
		sb.append("GROUP BY  c.categoria, p.id_producto, p.codigo,p.nombre, u.unidad_medida  ");
		sb.append(") b  ");
		sb.append("ON  a.codigo =  b.codigo");
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("id_producto", IntegerType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("nombre", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("sum_entra", IntegerType.INSTANCE);
		
		query.addScalar("Familia_s", StringType.INSTANCE);
		query.addScalar("id_producto_s", IntegerType.INSTANCE);
		query.addScalar("codigo_s", StringType.INSTANCE);
		query.addScalar("nombre_s", StringType.INSTANCE);
		query.addScalar("unidad_medida_s", StringType.INSTANCE);
		query.addScalar("sum_salida", IntegerType.INSTANCE);
		query.addScalar("diferencia", IntegerType.INSTANCE);
		
		query.setParameter("project", projectId);
		
		rows = query.list();
		
		for (Object[] cp : rows) {
			
			reasignedBean = new ReasignedBean();
				
			reasignedBean.setFamilia(cp[0] == null ? "" : (String) cp[0]);
			reasignedBean.setIdProducto((int) cp[1]);
			reasignedBean.setCodigo(cp[2] == null ? "" : (String) cp[2]);
			reasignedBean.setProducto(cp[3] == null ? "" : (String) cp[3]);
			reasignedBean.setUnidadMedida(cp[4] == null ? "" : (String) cp[4]);
			reasignedBean.setCantidadEntrada((int) cp[5]);
			
			reasignedBean.setFamilia_s(cp[6] == null ? "" : (String) cp[6]);
			reasignedBean.setIdProducto_s(cp[7] == null ? 0 : (int) cp[7]);
			reasignedBean.setCodigo_s(cp[8] == null ? "" : (String) cp[8]);
			reasignedBean.setProducto_s(cp[9] == null ? "" : (String) cp[9]);
			reasignedBean.setUnidadMedida_s(cp[10] == null ? "" : (String) cp[10]);
			reasignedBean.setCantidadSalida(cp[11] == null ? 0 : (int) cp[11] );
			
			reasignedBean.setDiferencia((int) cp[12]);
						
			result.add(reasignedBean);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ReportCostoInventarioGBean> getReportInventarioCI () {
	
		
		ArrayList<ReportCostoInventarioGBean> result = new ArrayList<ReportCostoInventarioGBean>();
		ReportCostoInventarioGBean repInventario = new ReportCostoInventarioGBean();
		
		List<Object[]> rows = new ArrayList<Object[]>();
	
		
		String queryReport= "SELECT 	"
				+ "	c.categoria as familia, "
				+ "	sum(p.precio_compra * p.cantidad) as costo_total "
				+ "	from "
				+ "	producto p "
				+ "		INNER JOIN categoria c on "
				+ "			p.id_categoria= c.id_categoria "
				+ "group BY p.id_categoria ";
		
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(queryReport);
		query.addScalar("familia", StringType.INSTANCE);
		query.addScalar("costo_total", BigDecimalType.INSTANCE);
		
		rows = query.list();
		
		for (Object[] ri : rows) {
			
			repInventario = new ReportCostoInventarioGBean();
				
			repInventario.setFamilia((String) ri[0]);
			repInventario.setCosto_total(ri[1] == null ? null : new BigDecimal(ri[1].toString()));
			
			
			result.add(repInventario);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
		
	}



	@SuppressWarnings("unchecked")
	public ArrayList<ReportCostoInventario> getReportInventario () {
	
		
		ArrayList<ReportCostoInventario> result = new ArrayList<ReportCostoInventario>();
		ReportCostoInventario repInventario = new ReportCostoInventario();
		
		List<Object[]> rows = new ArrayList<Object[]>();
		
		String queryReport= " SELECT 	"
				+ "		 c.categoria as familia, "
				+ "		 count(p.id_categoria) as no_articulos, "
				+ "		 sum(p.cantidad) as inventario_final, "
				+ "		 (	SELECT SUM(ee.cantidad) FROM entrada ee, categoria cc , producto pp "
				+ "		 WHERE ee.id_producto = pp.id_producto "
				+ "		 AND pp.id_categoria = cc.id_categoria "
				+ "		 AND pp.id_categoria = p.id_categoria) AS total_entrada, "
				+ "		 (	SELECT SUM(ee.cantidad) FROM salida ee, categoria cc , producto pp "
				+ "		 WHERE ee.id_producto = pp.id_producto "
				+ "		 AND pp.id_categoria = cc.id_categoria  "
				+ "		 AND pp.id_categoria = p.id_categoria) AS total_salida, "
				+ "		 		sum(p.precio_compra * p.cantidad) as costo_total  ,"
				+ "		 		sum(p.precio_compra * p.cantidad)* 0.16 AS IVAS,"
				+ "		 		sum(p.precio_compra * p.cantidad)* 1.16 AS TOTALIVAS"
				+ "		 	from  "
				+ "		 	producto p "
				+ "		 INNER JOIN categoria c on "
				+ "		 		p.id_categoria= c.id_categoria "
				+ "		 group BY p.id_categoria; ";
		
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(queryReport);
		query.addScalar("familia", StringType.INSTANCE);
		query.addScalar("no_articulos", IntegerType.INSTANCE);
		query.addScalar("inventario_final", IntegerType.INSTANCE);
		query.addScalar("total_entrada", IntegerType.INSTANCE);
		query.addScalar("total_salida", IntegerType.INSTANCE);
		query.addScalar("costo_total", BigDecimalType.INSTANCE);
		query.addScalar("IVAS", BigDecimalType.INSTANCE);
		query.addScalar("TOTALIVAS", BigDecimalType.INSTANCE);
				
		rows = query.list();
		
		for (Object[] ri : rows) {
			
			repInventario = new ReportCostoInventario();
				
			repInventario.setFamilia((String) ri[0]);
			repInventario.setNoArticulos((int) ri[1]);
			repInventario.setInventario_final((int) ri[2]);
			repInventario.setTotalEntrada(ri[3] == null ? 0 : (int) ri[3]);
			repInventario.setTotalSalida(ri[4] == null ? 0 : (int) ri[4]);
			repInventario.setCosto_total(ri[5] == null ? null : new BigDecimal(ri[5].toString()));
			repInventario.setIva(ri[6] == null ? null : new BigDecimal(ri[6].toString()));
			repInventario.setTotal(ri[7] == null ? null : new BigDecimal(ri[7].toString()));
			
			result.add(repInventario);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
		
	}	
	
	
	
	//Por  proyecto  entrada
	@SuppressWarnings("unchecked")
	public ArrayList<EntradasProyectoBean> getEntradaProyecto (int idProyecto) {
	
		List<Object[]> rows = new ArrayList<Object[]>();
		StringBuilder sb = new StringBuilder();
	
		
		sb.append("	SELECT  tp.nombre AS proyecto, c.categoria AS Familia, p.codigo, p.nombre AS producto, u.unidad_medida , s.cantidad, s.fecha ");
		sb.append("	FROM entrada s , producto p, unidad_medida u, categoria c, proyecto tp ");
		sb.append("	WHERE s.id_proyecto = tp.id_proyecto ");
		sb.append("	AND s.id_producto = p.id_producto ");
		sb.append("	AND s.id_unidad_medida = u.id_unidad_medida ");
		sb.append("	AND p.id_categoria = c.id_categoria ");
		sb.append("	AND tp.id_proyecto  = :idProject ");
		sb.append("	ORDER BY p.codigo ");
		
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("proyecto", StringType.INSTANCE);
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("producto", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("cantidad", IntegerType.INSTANCE);
		query.addScalar("fecha", DateType.INSTANCE);
		query.setParameter("idProject", idProyecto);
		
		rows = query.list();
		
		
		ArrayList<EntradasProyectoBean> result = new ArrayList<EntradasProyectoBean>();
		EntradasProyectoBean entrada = new EntradasProyectoBean();
		
		for (Object[] ep : rows) {
			
			entrada = new EntradasProyectoBean();
			
			entrada.setProyecto(ep[0] == null ? "" : (String) ep[0]);
			entrada.setFamilia(ep[1] == null ? "" : (String) ep[1]);
			entrada.setCodigo(ep[2] == null ? "" : (String) ep[2]);
			entrada.setProducto(ep[3] == null ? "" : (String) ep[3]);
			entrada.setUnidadMedida(ep[4] == null ? "" : (String) ep[4]);
			entrada.setCantidad((int) ep[5]);
			entrada.setFecha((Date) ep[6]);
			result.add(entrada);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<SalidaProyectoBean> getSalidasProyecto (int idProyecto){
		
		List<Object[]> rows = new ArrayList<Object[]>();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("	SELECT  tp.nombre AS proyecto, c.categoria AS Familia, p.codigo, p.nombre AS producto, u.unidad_medida , s.cantidad, s.fecha ");
		sb.append("	FROM salida s , producto p, unidad_medida u, categoria c, proyecto tp  ");
		sb.append("	WHERE s.id_proyecto = tp.id_proyecto ");
		sb.append("	AND s.id_producto = p.id_producto ");
		sb.append("	AND s.id_unidad_medida = u.id_unidad_medida ");
		sb.append("	AND p.id_categoria = c.id_categoria ");
		sb.append("	AND tp.id_proyecto  = :idProject ");
		sb.append("	ORDER BY p.codigo ");

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("proyecto", StringType.INSTANCE);
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("producto", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("cantidad", IntegerType.INSTANCE);
		query.addScalar("fecha", DateType.INSTANCE);
		query.setParameter("idProject", idProyecto);
		
		rows = query.list();
		
		
		ArrayList<SalidaProyectoBean> result = new ArrayList<SalidaProyectoBean>();
		SalidaProyectoBean salida = new SalidaProyectoBean();
		
		for (Object[] sp : rows) {
			
			salida = new SalidaProyectoBean();
			
			salida.setProyecto(sp[0] == null ? "" : (String) sp[0]);
			salida.setFamilia(sp[1] == null ? "" : (String) sp[1]);
			salida.setCodigo(sp[2] == null ? "" : (String) sp[2]);
			salida.setProducto(sp[3] == null ? "" : (String) sp[3]);
			salida.setUnidadMedida(sp[4] == null ? "" : (String) sp[4]);
			salida.setCantidad((int) sp[5]);
			salida.setFecha((Date) sp[6]);
			result.add(salida);
		}	
		
		session.flush();
		session.close();
		
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<ProveedoresBean>  getReporteProveedores () {
	
		List<Object[]> rows = new ArrayList<Object[]>();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(" SELECT  pr.nombre, c.categoria AS Familia, p.codigo, p.nombre AS producto, u.unidad_medida , s.cantidad, s.precio_unitario, s.fecha "
				+ " FROM entrada s , producto p, unidad_medida u, categoria c, proveedor pr, orden_compra oc, factura fa "
				+ " WHERE "
				+ "	s.id_producto = p.id_producto "
				+ " AND s.id_unidad_medida = u.id_unidad_medida "
				+ " AND p.id_categoria = c.id_categoria "
				+ " AND s.id_orden_compra =  oc.id_orden_compra "
				+ " AND oc.id_factura = fa.id_factura "
				+ " AND fa.id_proveedor = pr.id_proveedor "
				+ " ORDER BY pr.id_proveedor, c.categoria, p.nombre,s.fecha ");

		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		
		SQLQuery query = session.createSQLQuery(sb.toString());
		query.addScalar("nombre", StringType.INSTANCE);
		query.addScalar("Familia", StringType.INSTANCE);
		query.addScalar("codigo", StringType.INSTANCE);
		query.addScalar("producto", StringType.INSTANCE);
		query.addScalar("unidad_medida", StringType.INSTANCE);
		query.addScalar("cantidad", IntegerType.INSTANCE);
		query.addScalar("precio_unitario", BigDecimalType.INSTANCE);
		query.addScalar("fecha", DateType.INSTANCE);
		
		rows = query.list();
		
		
		ArrayList<ProveedoresBean> result = new ArrayList<ProveedoresBean>();
		ProveedoresBean salida = new ProveedoresBean();
		
		for (Object[] sp : rows) {
			
			salida = new ProveedoresBean();
			
			salida.setNombre(sp[0] == null ? "" : (String) sp[0]);
			salida.setFamilia(sp[1] == null ? "" : (String) sp[1]);
			salida.setCodigo(sp[2] == null ? "" : (String) sp[2]);
			salida.setProducto(sp[3] == null ? "" : (String) sp[3]);
			salida.setUnidadMedida(sp[4] == null ? "" : (String) sp[4]);
			salida.setCantidad((int) sp[5]);
			salida.setPrecioUnitario( sp[6] == null ? new BigDecimal(0.0) : new BigDecimal(sp[6].toString()));
			salida.setFecha((Date) sp[7]);
			result.add(salida);
		}	
		
		session.flush();
		session.close();
		
		
		return result;

			
	}

	public Producto getCodigo(String codigo, Integer sucursalId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		criteria.setFetchMode("almacen", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.add(Restrictions.eq("almacen.idAlmacen", sucursalId));
		Producto result = (Producto)criteria.uniqueResult();
		session.flush();
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Producto> getAllCodigo(Integer almacenId) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = DaoUtil.getCriteria(session, Producto.class);
		
		criteria.setFetchMode("categoria", FetchMode.JOIN);
		criteria.setFetchMode("unidadMedida", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("almacen.idAlmacen", almacenId));
		
		criteria.addOrder(Order.asc("nombre"));
		List<Producto> result = criteria.list();
		session.flush();
		session.close();
		
		return new ArrayList<Producto>(result);
	}

	
	
	
}
