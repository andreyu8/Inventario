package com.seidor.inventario.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.SelectionControl;

import com.seidor.inventario.adapter.UserAdapter;
import com.seidor.inventario.adapter.UserChangeKeyAdapter;
import com.seidor.inventario.adapter.listitem.CloseitemAdapter;
import com.seidor.inventario.adapter.search.UserSearchAdapter;
import com.seidor.inventario.exception.BusinessException;
import com.seidor.inventario.manager.RoleManager;
import com.seidor.inventario.manager.UserManager;
import com.seidor.inventario.model.Empleado;
import com.seidor.inventario.model.Perfil;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;
import com.seidor.inventario.navigation.NavigationControl;
import com.seidor.inventario.navigation.NavigationState;
import com.seidor.inventario.navigation.NavigationStates;
import com.seidor.inventario.util.EncryptUtil;
import com.seidor.inventario.util.SessionUtil;

public class UserController {
	
	@Autowired
	private UserManager userManager;
	
	@Autowired
	private NavigationControl navigationControl;
	
	@Autowired
	private RoleManager roleManager;

	
	
	//Spring getters and setters
	public UserManager getUserManager() {
		return userManager;
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public NavigationControl getNavigationControl() {
		return navigationControl;
	}
	
	public void setNavigationControl(NavigationControl navigationControl) {
		this.navigationControl = navigationControl;
	}
	
	public RoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}

	//Business logic
	public void login(Component win){
		Textbox ustb = (Textbox)win.getFellowIfAny("ustb");
		if (ustb.getValue() == null || ustb.getValue().length() == 0)
			throw new WrongValueException(ustb, "Debe ingresar un nombre de usuario");
		Textbox pstb = (Textbox)win.getFellowIfAny("pstb");
		if (pstb.getValue() == null || pstb.getValue().length() == 0) 
			throw new WrongValueException(pstb, "Debe ingresar la contraseña");
		
		Usuario user = this.userManager.get(ustb.getValue());
		if (user != null){
			String pass = EncryptUtil.encryptSHA(pstb.getValue());
			if (!pass.equals(user.getPassword())){
				user = null;
			}
		}
		
		if (user == null) {
			throw new BusinessException("No es posible realizar el login");
		}
		SessionUtil.setLoggedUserId(user.getIdUsuario());
		
		SessionUtil.setSucursaldUserId(user.getEmpleado().getAlmacen().getAlmacen());
		
		SessionUtil.setSucursalId(user.getEmpleado().getAlmacen().getIdAlmacen());
		
		SessionUtil.setEmpleadoId (user.getEmpleado());
		
		ArrayList<PerfilUsuario> roles = roleManager.getProfileUser(user);
		SessionUtil.setUserRoles (roles);
		
			
		this.navigationControl.showApplication(win);
	}
	
	public void search(Listbox lb, UserSearchAdapter tsa, NavigationState state){
		ArrayList<Usuario> users = this.userManager.search(tsa);
		
		ListModelList<Usuario> model = new ListModelList<Usuario>(users);
		lb.setModel(model);
	}
	
	public void show(Listbox lb, NavigationState state, Component win){
		if (lb.getSelectedIndex() >= 0) {
			Usuario user = (Usuario)lb.getModel().getElementAt(lb.getSelectedIndex());
			
			state.setDetailIdentifier(user.getIdUsuario());
			state.setUri("/WEB-INF/zul/user/detail.zul");
			state.appendBreadCrumbsPath(user.getEmpleado().getNombre());
			ArrayList<Integer> detailList = new ArrayList<Integer>();
			ArrayList<String> detailLabels = new ArrayList<String>();
			for (int i = 0; i < lb.getModel().getSize(); i++) {
				Usuario u = (Usuario)lb.getModel().getElementAt(i);
				detailList.add(u.getIdUsuario());
				detailLabels.add(u.getEmpleado().getNombre());
				if (u.getIdUsuario().equals(user.getIdUsuario())) state.setDetailIndex(detailList.size() - 1);
			}
			state.setDetailList(detailList);
			state.setDetailLabels(detailLabels);
			this.navigationControl.changeView(win, state);
		}
	}
	
	public UserAdapter read(Integer userId){
		UserAdapter ua = new UserAdapter();
	
		Usuario user = this.userManager.get(userId);
		ua.setNameComplete(user.getEmpleado().getNombre()+" "+(user.getEmpleado().getAPaterno() == null ? "" :  user.getEmpleado().getAPaterno() ) +" "+(user.getEmpleado().getAMaterno() == null ? "" :  user.getEmpleado().getAMaterno()));
		ua.setUsuario(user);
		ua.setProfiles(this.roleManager.getProfileUser(user));
		
		return ua;
	}
	
	public UserAdapter readForEdit(Integer userId){
		UserAdapter ua = new UserAdapter();
		
		Usuario user = this.userManager.get(userId);
		ua.setNameComplete(user.getEmpleado().getNombre()+" "+(user.getEmpleado().getAPaterno() == null ? "" :  user.getEmpleado().getAPaterno() ) +" "+(user.getEmpleado().getAMaterno() == null ? "" :  user.getEmpleado().getAMaterno()));
		ua.setUsuario(user);
		
		ArrayList<Perfil> allProfiles = this.roleManager.getAll();
		ua.setAllProfiles(allProfiles);

	
		ArrayList<PerfilUsuario> profiles = this.roleManager.getProfileUser(user);
		ua.setProfiles(profiles);
		
		return ua;
	}
	
	public UserAdapter readForNew(){
		UserAdapter ua = new UserAdapter();
		
		Usuario user = new Usuario();
		user.setActivo(1);
		user.setFecha(new Date());
		user.setEmpleado(new Empleado());
		ua.setRole(new Perfil());
		ua.setProfiles(new ArrayList<PerfilUsuario>());
		ua.setUsuario(user);

		return ua;
	}
	
	
	public UserChangeKeyAdapter  readUserKeyPass () {
		
		UserChangeKeyAdapter uck = new UserChangeKeyAdapter();
		int userId = SessionUtil.getLoggedUserId();
		
		Usuario user = this.userManager.get(userId);
			
		uck.setUsuario(user);
		uck.setPasswordNew("");
		uck.setPasswordNew2("");
		
		return uck;
		
	}
	
	
	
	public void updatePassword(UserChangeKeyAdapter ua, NavigationState state, Component win){
	
		Textbox pstb = (Textbox)win.getFellowIfAny("pstb");
		if (pstb.getValue() != null && pstb.getValue().length() > 0) {
			if (pstb.getValue().length() >= 6) {
		
				Textbox pctb = (Textbox)win.getFellowIfAny("pctb");
				if (pctb.getValue() != null && pstb.getValue().equals(pctb.getValue())){
					String pass = EncryptUtil.encryptSHA(pstb.getValue());
					ua.getUsuario().setPassword(pass);
					
					userManager.update(ua.getUsuario());
					
				}
				else {
					throw new WrongValueException(pctb, "Confirmación de contraseña incorrecta");
				}
			}
			else {
				throw new WrongValueException(pstb, "La contraseña debe tener al menos 6 caracteres");
			}
		}
		else {
			throw new WrongValueException(pstb, "Debe indicar una contraseña para el usuario");
		}
		
		
		state.setDetailIdentifier(ua.getUsuario().getIdUsuario());
		state.setUri("/WEB-INF/zul/user/detail.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ua.getUsuario().getEmpleado().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void save(UserAdapter ua, NavigationState state, Component win){
		Textbox pstb = (Textbox)win.getFellowIfAny("pstb");
		if (pstb.getValue() != null && pstb.getValue().length() > 0) {
			if (pstb.getValue().length() >= 6) {
				Textbox pctb = (Textbox)win.getFellowIfAny("pctb");
				if (pctb.getValue() != null && pstb.getValue().equals(pctb.getValue())){
					String pass = EncryptUtil.encryptSHA(pstb.getValue());
					ua.getUsuario().setPassword(pass);
				}
				else {
					throw new WrongValueException(pctb, "Confirmación de contraseña incorrecta");
				}
			}
			else {
				throw new WrongValueException(pstb, "La contraseña debe tener al menos 6 caracteres");
			}
		}
		else {
			throw new WrongValueException(pstb, "Debe indicar una contraseña para el usuario");
		}
		
		
		Combobox employee = (Combobox) win.getFellowIfAny("cbemp");
		if (employee != null &&employee.getSelectedItem()!=null )
			ua.getUsuario().setEmpleado((Empleado) employee.getSelectedItem().getValue());
		else 
			throw new WrongValueException(employee, "Debe de seleccionar un empleado");
		
		ua.getUsuario().setActivo(1);
		ua.getUsuario().setFecha(new Date());
		
		Listbox lb = (Listbox) win.getFellowIfAny("usrlb");
		ListModelList<Perfil> lml = (ListModelList) lb.getModel();
		Set<Perfil> selectedCars = ((ListModelList<Perfil>)lml).getSelection();
		
		PerfilUsuario pub = new PerfilUsuario();
		
        for (Perfil p: selectedCars) {
        	pub= new PerfilUsuario();
        	System.out.println("id:"+p.getIdPerfil());
			System.out.println("nombre: "+p.getNombre());
			pub.setPerfil(p);
			pub.setUsuario(ua.getUsuario());
			pub.setActivo(1);
			ua.getProfiles().add(pub);
        }
		
				
		if (ua.getProfiles().size() == 0){
			throw new WrongValueException(win.getFellowIfAny("usrlb"), "Debe agregar al menos un perfil al usuario");
		} else {
			this.userManager.save(ua);
		}
		
		
		
		state.setDetailIdentifier(ua.getUsuario().getIdUsuario());
		state.setUri("/WEB-INF/zul/user/detail.zul");
		state.removeLastBreadCrumbs();
		state.appendBreadCrumbsPath(ua.getUsuario().getEmpleado().getNombre());
		this.navigationControl.changeView(win, state);
	}
	
	public void update(UserAdapter ua, NavigationState state, Component win){
		Boolean wrong = false;
		Textbox optb = (Textbox)win.getFellowIfAny("optb");
		Textbox pstb = (Textbox)win.getFellowIfAny("pstb");
		
		if (pstb.getValue() != null && pstb.getValue().length() > 0) {
			wrong = true;
			if (optb.getValue() != null && optb.getValue().length() > 0) {
				//Check old password
				Usuario u = this.userManager.get(ua.getUsuario().getIdUsuario());
				String oep = EncryptUtil.encryptSHA(optb.getValue());
				if (oep.equals(u.getPassword())) {
					if (pstb.getValue().length() >= 6) {
						Textbox pctb = (Textbox)win.getFellowIfAny("pctb");
						if (pctb.getValue() != null && pstb.getValue().equals(pctb.getValue())){
							String pass = EncryptUtil.encryptSHA(pstb.getValue());
							ua.getUsuario().setPassword(pass);
							wrong = false;
						}
						else {
							throw new WrongValueException(pctb, "Confirmación de contraseña incorrecta");
						}
					}
					else {
						throw new WrongValueException(pstb, "La contraseña debe tener al menos 6 caracteres");
					}
				}
				else {
					throw new WrongValueException(optb, "Contraseña anterior incorrecta");
				}
			}
			else {
				throw new WrongValueException(optb, "Debe indicar la contraseña anterior");
			}
		}
		else {
			wrong = false;
		}
		
		
		if (!wrong) {
			if (!ua.hasAnyProfile()){
				throw new WrongValueException(win.getFellowIfAny("prolb"), "Debe agregar al menos un perfil al usuario");
			}
			
			this.userManager.update(ua);
			
			if (ua.getUsuario().getIdUsuario().equals(SessionUtil.getLoggedUserId())) {
				Component header = win.getDesktop().getPageIfAny("indexPage").getFellowIfAny("header");
				Component footer = win.getDesktop().getPageIfAny("indexPage").getFellowIfAny("footer");
			}
			
			NavigationStates navStates = (NavigationStates)SpringUtil.getBean("navigationStates");
			NavigationState prev = navStates.getPreviousOriginal();
			prev.removeLastBreadCrumbs();
			prev.appendBreadCrumbsPath(ua.getUsuario().getEmpleado().getNombre());
			if (prev.getDetailLabels() != null) {
				prev.getDetailLabels().set(prev.getDetailIndex(), ua.getUsuario().getEmpleado().getNombre());
			}
			this.navigationControl.changeToPrevious(win);
		}
	}
	
	public void delete(UserAdapter ua, NavigationState state, Component win){		
		this.userManager.delete(ua.getUsuario());
		
		state.setUri("/WEB-INF/zul/user/main.zul");
		state.setDetailIdentifier(null);
		state.removeLastBreadCrumbs();
		state.removeLastBreadCrumbs();
		this.navigationControl.changeView(win, state);
	}
	
	
	
	
	
}