package com.seidor.inventario.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import com.seidor.inventario.adapter.ProductAdapter;
import com.seidor.inventario.adapter.beans.ProductReporBean;
import com.seidor.inventario.adapter.beans.ProveedoresBean;
import com.seidor.inventario.adapter.beans.ReportCostoInventario;
import com.seidor.inventario.adapter.beans.ReportCostoInventarioGBean;
import com.seidor.inventario.adapter.search.ProductSearchAdapter;
import com.seidor.inventario.manager.ProductManager;
import com.seidor.inventario.model.Almacen;
import com.seidor.inventario.model.Categoria;
import com.seidor.inventario.model.Producto;
import com.seidor.inventario.model.UnidadMedida;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.ReportUtil;
import com.seidor.inventario.util.SessionUtil;

public class ProductController {
	
	@Autowired
	private ProductManager productManager;
	private NavigationControl navigationControl;
	
	
	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public NavigationControl getNavigationControl() {
		return navigationControl;
	}

	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	
	
	// logic search
	public void search(Listbox lb, ProductSearchAdapter psa, NavigationState state){
	
		psa.setIdCategoria(0);
		psa.setIdAlmacen(SessionUtil.getSucursalId());
		
		ArrayList<Producto> products = this.productManager.search(psa);
		
		ListModelList<Producto> model = new ListModelList<Producto>(products);
		lb.setModel(model);
	}
	
	
	public void searchProductReport(Listbox lb, ProductSearchAdapter psa, NavigationState state, Component win){
		
		if (psa.getIdCategoria() == null)
			psa.setIdCategoria(0);
		
		System.out.println("idCategoria: "+psa.getIdCategoria());
		
		ArrayList<Producto> products = this.productManager.searchRP(psa);
		
		ListModelList<Producto> model = new ListModelList<Producto>(products);
		lb.setModel(model);
	}
	
	public void searchReportProv(Listbox lb,  NavigationState state){
		
		ArrayList<ProveedoresBean> products = this.productManager.getReporteProveedores();
		
		ListModelList<ProveedoresBean> model = new ListModelList<ProveedoresBean>(products);
		lb.setModel(model);
	}
	
	
	//Metodo para mostrar el detalle 
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Producto product = (Producto)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(product.getIdProducto());
			state.setUri("/WEB-INF/zul/product/detail.zul");
			state.appendBreadCrumbsPath(product.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Producto p = (Producto)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProducto());
				detailLabels.add(p.getNombre());
				if (p.getIdProducto().equals(product.getIdProducto())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	public ProductAdapter read(Integer productId){
		ProductAdapter pa = new ProductAdapter();
	
		Producto product = this.productManager.get(productId);
		pa.setProducto(product);
		
		return pa;
	}
	
	
	public ProductAdapter readForEdit(Integer productId){
		ProductAdapter pa = new ProductAdapter();
		
		Producto product = this.productManager.get(productId);
		pa.setProducto(product);
		
		return pa;
	}
	
	
	public ProductAdapter readForNew(){
		ProductAdapter pa = new ProductAdapter();
		
		Producto product = new Producto();
		product.setCategoria(new Categoria());
		product.setUnidadMedida(new UnidadMedida());
		
		pa.setProducto(product);
		
		
		return pa;
	}
	
	
	public void save(ProductAdapter pa, NavigationState state, Component win){
	
		Combobox cbCategory = (Combobox) win.getFellowIfAny("catcb");
		if (cbCategory != null && cbCategory.getSelectedItem()!=null )
			pa.getProducto().setCategoria((Categoria) cbCategory.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbCategory, "Debe de seleccionar una categoría");
		
		
		Combobox cbUnitMeasure = (Combobox) win.getFellowIfAny("umcb");
		if (cbUnitMeasure != null && cbUnitMeasure.getSelectedItem()!=null )
			pa.getProducto().setUnidadMedida((UnidadMedida) cbUnitMeasure.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbCategory, "Debe de seleccionar una unidad de medida");
		
		
		Almacen a = new Almacen();
		a.setIdAlmacen(SessionUtil.getSucursalId());
		a.setAlmacen(SessionUtil.getSucursaldUserId());
		
		pa.getProducto().setAlmacen(a);
		pa.getProducto().setCantidad(0);
		
		this.productManager.save(pa.getProducto());
		
		state.setDetailIdentifier(pa.getProducto().getIdProducto());
		state.setUri("/WEB-INF/zul/product/main.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(pa.getProducto().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(ProductAdapter pa, NavigationState state, Component win){
		
		Combobox cbCategory = (Combobox) win.getFellowIfAny("catcb");
		if (cbCategory != null && cbCategory.getSelectedItem()!=null )
			pa.getProducto().setCategoria((Categoria) cbCategory.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbCategory, "Debe de seleccionar una categoría");
		
		
		Combobox cbUnitMeasure = (Combobox) win.getFellowIfAny("umcb");
		if (cbUnitMeasure != null && cbUnitMeasure.getSelectedItem()!=null )
			pa.getProducto().setUnidadMedida((UnidadMedida) cbUnitMeasure.getSelectedItem().getValue());
		else 
			throw new WrongValueException(cbCategory, "Debe de seleccionar una unidad de medida");
		
		
		this.productManager.update(pa.getProducto());
			
			
		NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
		NavigationState prev = navStates.getPreviousOriginal();
		prev.removeLastBreadCrumbs();
		prev.appendBreadCrumbsPath(pa.getProducto().getNombre());
		if (prev.getDetailLabels() != null) {
			prev.getDetailLabels().set(prev.getDetailIndex(), pa.getProducto().getNombre());
		}
		this.navigationControl.changeToPrevious(win);
		
	}
	
	public void delete(ProductAdapter pa, NavigationState state, Component win){		
		this.productManager.delete(pa.getProducto());
		
		state.setUri("/WEB-INF/zul/product/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
	
	

	public void inProduct(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Producto product = (Producto)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(product.getIdProducto());
			state.setUri("/WEB-INF/zul/product/inOut/inProduct.zul");
			state.appendBreadCrumbsPath(product.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Producto p = (Producto)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProducto());
				detailLabels.add(p.getNombre());
				if (p.getIdProducto().equals(product.getIdProducto())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	
	public void outProduct(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Producto product = (Producto)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(product.getIdProducto());
			state.setUri("/WEB-INF/zul/product/inOut/outProduct.zul");
			state.appendBreadCrumbsPath(product.getNombre());
			
			
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Producto p = (Producto)lb.getModel().getElementAt(i);
				detailList.add(p.getIdProducto());
				detailLabels.add(p.getNombre());
				if (p.getIdProducto().equals(product.getIdProducto())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}

	
	
	
	//reporte
	public void searchReport(Listbox lb, NavigationState state){
		
		ArrayList<ReportCostoInventario> reportProducts = this.productManager.getReportInventario();
		
		ListModelList<ReportCostoInventario> model = new ListModelList<ReportCostoInventario>(reportProducts);
		lb.setModel(model);
	}

	
	//reporte
	public void searchReportCI(Listbox lb, NavigationState state){
		
		ArrayList<ReportCostoInventarioGBean> reportProducts = this.productManager.getReportInventarioCI();
		
		ListModelList<ReportCostoInventarioGBean> model = new ListModelList<ReportCostoInventarioGBean>(reportProducts);
		lb.setModel(model);
	}
	
	
	
	public void printReport (Component win) {
		
		
		ProductReporBean pb=  new ProductReporBean();
		
		pb.setAlmacen("Almacen 1");
				
		/*ArrayList<ListDerivativesBean> ldjson = new ArrayList<ListDerivativesBean>();
		ListDerivativesBean ld = new ListDerivativesBean ();
		
		for (Object[] d : detail) {
			ld = new ListDerivativesBean ();
			ld.setClaveCatastral(d[0] == null ? "" : (String) d[0].toString());
			ld.setManzana(d[1] == null ? "" : (String) d[1].toString());
			ld.setPredio(d[2] == null ? "" : (String) d[2].toString());
			ld.setNombre(d[3] == null ? "" : (String) d[3].toString());
			ld.setColonia(d[4] == null ? "" : (String) d[4].toString());
			ld.setDireccion(d[5] == null ? "" : (String) d[5].toString());
			ld.setArea(NumberFormatUtil.format(new BigDecimal(d[6] == null ? 0.0 : Double.parseDouble((String)d[6].toString())), 2));
			ld.setStatus(d[7] == null ? "" : (String) d[7].toString());
			ld.setFechaVencimiento(d[8] == null ? "" : DateFormatUtil.convertDateToFormattedString((Date) d[8], "dd-MM-yyyy"));
			
			ldjson.add(ld);
		}
		
		Gson gson = new Gson();
		derivative.setJsonListDerivatives(gson.toJson(ldjson)); */
		
		
		exportToPdf(pb);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportToPdf(ProductReporBean productBean) {
		HashMap<String, Object> parametters = new HashMap<String, Object>();
		//parametters.put("REPORT_TITLE", "TRÁMITES Y SERVICIOS");
		//parametters.put("REPORT_DATE", DateFormatUtil.getFormatedDate(new Date(), false));
			
		
		//parametters.put("ListFromJsonGeneric[jsonListProducts]", getListFromJsonGeneric(productBean.getJsonListProductss()));
		
		try {
			List dataSource = new ArrayList();
			dataSource.add(productBean);
			String tname = "Notificación";
			Media mediaReport = ReportUtil.generateReport(dataSource, "listaProductos.jasper", ReportUtil.TASK_PDF, parametters, tname);
			org.zkoss.zul.Filedownload.save(mediaReport);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static List<HashMap<String, Object>> getListFromJsonGeneric(String json) {
		List<HashMap<String, Object>> array = new ArrayList<HashMap<String, Object>>();
		try {
			JSONArray jsonarray = new JSONArray(json);
			for (int i = 0; i < jsonarray.length(); i++) {
				String jsondata = jsonarray.get(i).toString();
				HashMap<String, Object> map = new HashMap<String, Object>();
				JSONObject jObject = new JSONObject(jsondata);
				Iterator<?> keys = jObject.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();
					String value = jObject.getString(key);
					map.put(key, value);
				}
				array.add(map);
			}
		} catch (Exception ex) {
			ex.getCause();
		}
		return array;
	}
	
	
}
