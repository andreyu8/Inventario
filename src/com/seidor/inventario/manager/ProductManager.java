package com.seidor.inventario.manager;

import java.util.ArrayList;

import com.seidor.inventario.adapter.beans.CierreBean;
import com.seidor.inventario.adapter.beans.CloseBean;
import com.seidor.inventario.adapter.beans.DevolucionBean;
import com.seidor.inventario.adapter.beans.EntradasProyectoBean;
import com.seidor.inventario.adapter.beans.ProveedoresBean;
import com.seidor.inventario.adapter.beans.ReasignedBean;
import com.seidor.inventario.adapter.beans.ReportCostoInventario;
import com.seidor.inventario.adapter.beans.ReportCostoInventarioGBean;
import com.seidor.inventario.adapter.beans.SalidaProyectoBean;
import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.dao.ProductDAO;
import com.seidor.inventario.model.Producto;

public class ProductManager {
	
	private ProductDAO productDao;
	
	
	//Spring getters and setters
	public ProductDAO getProductDao() {
		return productDao;
	}
	
	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}	

	
	//Business logic
	public Producto get(Integer id){
		return this.productDao.get(id);
	}
	
	public ArrayList<Producto> getAll(){
		return this.productDao.getAll();
	}
	
	public ArrayList<Producto> getAll(Integer almacenId){
		return this.productDao.getAll(almacenId);
	}
	
	public void save(Producto p){
		this.productDao.save(p);
	}
	
	public void update(Producto p){
		this.productDao.update(p);
	}
	
	public void delete(Producto p){
		this.productDao.delete(p);
	}

	public ArrayList<Producto> search(ProductSearchAdapter psa) {
		return this.productDao.search(psa);
	}
	
	public ArrayList<Producto> searchRP(ProductSearchAdapter psa) {
		return this.productDao.searchRP(psa);
	}
	
	
	public ArrayList<ReportCostoInventarioGBean> getReportInventarioCI () {
		return this.productDao.getReportInventarioCI();
	}
	
	public ArrayList<ReportCostoInventario> getReportInventario () {
		return this.productDao.getReportInventario();
	}
	
	public ArrayList<ProveedoresBean>  getReporteProveedores () {
		return this.productDao.getReporteProveedores();
	}
	
	public ArrayList<CierreBean> getCierreProyecto (Integer projectId) {
		return this.productDao.getCierreProyecto (projectId);
	}
	
	public ArrayList<CloseBean> getCloseProyecto (Integer projectId) {
		return this.productDao.getCloseProyecto (projectId);
	}
	
	public ArrayList<DevolucionBean> getDevolucionesProyecto (Integer projectId, Integer almacenId) {
		return this.productDao.getDevolucionesProyecto(projectId, almacenId);
	}
	
	public ArrayList<DevolucionBean> getDevolucionesProyectoSalidas (Integer projectId, Integer almacenId) {
		return this.productDao.getDevolucionesProyectoSalidas(projectId, almacenId);
	}
	
	public ArrayList<ReasignedBean> getReasignedProyecto (Integer projectId) {
		return this.productDao.getReasignedProyecto (projectId);
	}
	
	public ArrayList<EntradasProyectoBean> getEntradaProyecto (int idProyecto) {
		return this.productDao.getEntradaProyecto (idProyecto);
	}
	
	public ArrayList<SalidaProyectoBean> getSalidasProyecto (int idProyecto){
		return this.productDao.getSalidasProyecto (idProyecto);
	}

	public Producto getCodigo(String codigo, Integer sucursalId) {
		return this.productDao.getCodigo(codigo, sucursalId);
	}

	public  ArrayList<Producto> getAllCodigo(Integer almacenId) {
		return this.productDao.getAllCodigo(almacenId);
	}

	public String getMaxProductCode(Integer idCategoria) {
		return this.productDao.getMaxProductCode (idCategoria);
	}

	public ArrayList<Producto> getCodigoProduct(String codigo) {
		return this.productDao.getCodigoProduct (codigo);	
	}

	public ArrayList<Producto> getAllProduct() {
		return this.productDao.getAllProduct ();
	}
	

}
